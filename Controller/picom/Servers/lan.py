import socket
import threading

from picom.Clients.lan import Client
from picom.Data.PayloadObj import\
    PayloadEventMessages,\
    Payload, \
    send_payload, to_string
from picom.Data.Structure import\
    EventTypes,\
    PayloadType,\
    PayloadEvent,\
    BLANK_FIELD,\
    WILDCARD,\
    EventDomain
from picom.Delegation import PiDiscovery
from picom.Servers.SystemControllerUtils import\
    Responder,\
    SYS_Handler
from picom.Servers.WebConnect import ClientTask

"""
    :SERVER
        This is the multi threaded server for handling all communications to the units on
        the local network. For every new client that connects, a new thread is spawned.

    :PAYLOAD PROCESSING
        As the payloads are being received, their 'role' parameter is being compared
        to that of the current servers 'role', if the role is'nt intended for the
        receiving server, the payload will be ignored and a 'NotIntended' event will
        be replied. However, if delegation is enabled. Then the server will ID the
        correct server and relay both the request and response back to the client
        from the intended source.

        If the payload is intended for the current server, the payload is interpreted.
        If however its a instruction for the hardware, its sent to the handler.

    :HANDLER
        On creation of the server, a handler needs to be set, the server will fire events
        to that handler as they are received, the callback from the handler will feature as
        the response to the client (APP/WEB).

            |===PI===| ----->----->----- *+++++++++*     * ============== *
        <** | SERVER |     CALLBACK      | HANDLER |-<  >- IMPLEMENTATION *
            |________| -----<-----<----- *++++++++++     * ============== *



"""
__version__ = '2.5'
__author__ = "Dylan Coss <dylancoss1@gmail.com>"

IS_DELEGATOR = True
Responses = None
ROLE = None
NAME = None
TOKEN = "PiToken"
REQUEST_TYPES = EventTypes.REQUEST_TYPES.value
HARDWARE_TYPES = EventTypes.HARDWARE_EVENTS.value
SOFTWARE_TYPES = EventTypes.SOFTWARE_EVENT.value
SYSTEM_TYPES = EventTypes.SYSTEM_EVENTS.value


class ConnectedClient(threading.Thread, Responder):

    def __init__(self, ip: str, port: int, client_soc: socket, handler: SYS_Handler,
                 delegation_event_whitelist=None):
        assert handler is not None
        threading.Thread.__init__(self)
        self.lock = threading.Lock()
        if delegation_event_whitelist is None:
            delegation_event_whitelist = []
        self.client_address = ip
        self.port = port
        self.request = client_soc
        self.handler = handler
        self.del_whitelist = delegation_event_whitelist
        print("[+] Connection to {} Established: ".format(self.client_address))

    def data_handler(self, payload: Payload, delegation_event_whitelist=None):
        print("Handling %s" % payload.data)
        if delegation_event_whitelist is None:
            delegation_event_whitelist = []
        notify_all = payload.role == WILDCARD
        correct_payload = (payload.role == ROLE) or notify_all
        is_request = payload.type in REQUEST_TYPES
        white_listed = payload.event in delegation_event_whitelist

        print("**> notifyALL: %s, correct_role: %s, isReq: %s, white: %s, dele: %s" %
              (notify_all, correct_payload, is_request, white_listed, IS_DELEGATOR))
        # 1. Determines if the payload is intended for the current unit
        if not correct_payload and is_request and not white_listed:
            # 2 If the current payload is NOT intended for the current unit
            #   it will be delegated, and hopefully send it to the appropriate
            #   unit.
            if IS_DELEGATOR and payload.role is not BLANK_FIELD:
                # 2.1 If delegation is enabled, it then gets the ip address of the intended
                #     unit and sends the payload, if the wildcard has been specified then all
                #     the units will be notified
                addresses = PiDiscovery.get_ip_addresses(payload.role)
                transactions = []
                res_payload = PayloadEventMessages.ADDRESS_NOT_FOUND.value
                for address in addresses:
                    client = Client(address, self.port, timeout=5, ignore_errors=True)
                    print("\t%s\n\t|\t\tRelaying -> %s:%s" % (to_string(payload), address, self.port))
                    res_payload = client.send(payload)
                    transactions.append((res_payload.data, res_payload.event.name))

                if len(transactions) > 1:
                    payload.data = transactions
                    payload.type = PayloadType.RSP
                    return payload
                return res_payload

                # return self.process_payload(payload)
            else:
                # 2.2 If delegation is disabled, and the role can't be handled, then
                #     a error response will be sent telling the client 'Wrong Node'.
                if notify_all:
                    return self.process_payload(payload)
                return PayloadEventMessages.WRONG_NODE

        return self.process_payload(payload)

    def process_payload(self, payload):
        res_payload = payload

        # -----------------SYSTEM JOB HANDLING------------------
        if payload.type is PayloadType.JOB:

            if payload.event is PayloadEvent.REMOVE:
                    jid = res_payload.data['id']
                    Responses.remove_job(jid)
                    res_payload.data = {'message': 'removed job [%s]' % jid}
            elif payload.event is PayloadEvent.LIST:
                    j_list = []

                    for j in Responses.list_jobs():
                        j_list.append(j.to_dict())
                    res_payload.data = j_list
            else:
                    Responses.add_job(payload)

            res_payload.type = PayloadType.ACK
            print("[i] Processing job request")
            return res_payload

        assert isinstance(payload, Payload)
        is_resp = payload.type not in REQUEST_TYPES
        is_soft = payload.event in SOFTWARE_TYPES
        is_hard = payload.event in HARDWARE_TYPES
        is_sys = payload.event in SYSTEM_TYPES

        print("[i] Processing %s %s (%s)" %
              ("Hardware" if is_hard else ("Software" if is_soft else ("System" if is_sys else "Unknown")),
               "Response" if is_resp else "Request",
               to_string(payload)))

        # --------------------SYSTEM HANDLING--------------------
        if is_sys:
            if not is_resp:
                # Request handling
                # Handles Probe Request
                if payload.event is PayloadEvent.S_PROBE or payload.event is PayloadEvent.CONNECT:
                    res_payload.data = {'name': NAME,
                                        'role': ROLE,
                                        'isDelegator': IS_DELEGATOR,
                                        'token': TOKEN,
                                        'auth': True}

                    res_payload.type = PayloadType.RSP


                    # TODO: Handle requests

                    # ------------------HARDWARE/SOFTWARE HANDLING------------------------
        if is_hard or is_soft:
            domain = EventDomain.SOFT if is_soft else (EventDomain.GPIO if is_hard else None)
            print("\t%s Instruction <%s --> %s>" % (domain.value, payload.data, payload.event))
            return self.handler.instruction(self, payload.data, payload.event, domain, payload)

        return res_payload

    def send_event_message(self, E:PayloadEventMessages, message: str = None):
        E.value.data = {'message': message}
        send_payload(self.request, E)

    def respond(self, payload: Payload):
        print("RETURN: %s" % payload.to_dict())
        if payload is None:
            send_payload(self.request, PayloadEventMessages.ADDRESS_NOT_FOUND)
        else:
            send_payload(self.request, payload)

    def run(self):
        res_data = PayloadEventMessages.SERVER_ERROR.value
        running = True
        self.lock.acquire()
        while running:
            try:
                req_data = self.request.recv(1024)
                if req_data is not None and (len(req_data) > 0):
                    print(req_data)
                    req_data = req_data.decode("utf8")
                    req_payload = Payload.from_dict(req_data)
                    if isinstance(req_payload, Payload):
                        if req_payload.type is PayloadType.JOB and req_payload.event not in SYSTEM_TYPES:
                            from picom.Delegation.jobs import JobPayload
                            req_payload = JobPayload.from_dict(req_data)

                        # Sends the received data to be manage by the data_handler
                        res_data = self.data_handler(req_payload, self.del_whitelist)

                        if running:
                            self.respond(res_data)
                    else:
                        print("[!] Raw data retrieved: {}".format(res_data))
                else:
                    self.send_event_message(PayloadEventMessages.ERROR,
                                            "There was no data received!")
            except socket.error as err:
                if err.errno not in [104, 32]:
                    raise
                running = False
        print("[-] Connection to {} Closed \n".format(self.client_address))


def start_lan_server(handler: SYS_Handler, ip_address="0.0.0.0", port=8000, execution_role="No Role",
                     name="Unknown", delegation_event_whitelist: list = None, delegator=True, job_poll_interval=1):
    global ROLE, NAME, IS_DELEGATOR, Responses
    ROLE = execution_role
    NAME = name
    IS_DELEGATOR = delegator
    if IS_DELEGATOR:
        client  = ClientTask()
        client.start()

    tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    tcp_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    tcp_socket.bind((ip_address, port))

    print("\n[!] %s %s (%s)\n\tListening on %s:%d\n" %
          (NAME, ("Delegator" if IS_DELEGATOR else "Node"), ROLE, ip_address, port))

    client_threads = []

    while True:
        tcp_socket.listen(10)
        (client_socket, client_ip) = tcp_socket.accept()
        spawned_thread = ConnectedClient(client_ip, port, client_socket, handler, delegation_event_whitelist)
        spawned_thread.start()
        client_threads.append(spawned_thread)
        client_threads = [client_threads.remove(t) for t in client_threads if not t.isAlive()]
        # Adds to the thread pool
    for thread in client_threads:
        thread.join()
