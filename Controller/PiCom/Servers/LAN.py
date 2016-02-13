import socket
import sys
import threading

from PiCom.Clients.LANClient import LANClient
from PiCom.Payload import print_payload, Payload, build_payload, send_payload
from PiCom.Payload.Fields import PayloadType, PayloadEvent
from PiCom.Servers.SystemControllerUtils import GPIO_Handler, GPIO_Responder

from PiCom.Delegation import PiDiscovery

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
__version__ = '1.5'
__author__ = "Dylan Coss <dylancoss1@gmail.com>"

IS_DELEGATOR = True
ROLE = None
NAME = None


class Client(threading.Thread, GPIO_Responder):
    def __init__(self, ip: str, port: int, client_soc: socket, handler: GPIO_Handler,
                 delegation_event_whitelist=None):
        threading.Thread.__init__(self)
        if delegation_event_whitelist is None:
            delegation_event_whitelist = []
        self.client_address = ip
        self.port = port
        self.request = client_soc
        self.handler = handler
        self.del_whitelist = delegation_event_whitelist
        print("[+] Connection to {} Established: ".format(self.client_address))

    """
    Handles all data received and returns the response
    """
    def data_handler(self, payload: Payload, delegation_event_whitelist=None):

        # 1. Determines if the payload is intended for the current unit

        if delegation_event_whitelist is None:
            delegation_event_whitelist = []

        if payload.role != ROLE and payload.type == PayloadType.REQ \
                and (payload.event not in delegation_event_whitelist):

            # 2.1 If the current payload is NOT intended for the current unit
            #     it will be delegated, and hopfully send it to the appropriate
            #     unit.
            if IS_DELEGATOR:
                # 2.2 If delegation is enabled, it then gets the ip address of the intended
                #     unit and sends the payload
                for address in PiDiscovery.get_ip_addresses(payload.role):
                    client = LANClient(address, self.port)
                    print("\n\t| **> %s\n\t|\t\tRelaying -> %s" % (print_payload(payload), address))
                    return client.send(payload)
            else:
                # 2.3 If delegation is disabled, then a response will be sent saying that the request
                #     failed.
                return Payload("Sorry...Wrong Pi im afraid")

        print("| <** %s\n" % print_payload(payload))
        return payload

    """

    """
    def process_payload(self, payload: Payload):

        print("\n**> Processing {0}".format(print_payload(payload)))

        # Handles Probe Request
        if payload.type is PayloadType.REQ and payload.event is PayloadEvent.PROBE:
            payload.data = {'name': NAME, 'role': ROLE, 'isDelegator': IS_DELEGATOR}
            payload.type = PayloadType.RSP
            return payload
        # Handles Probe Response
        if payload.event is PayloadEvent.PROBE and payload.type is PayloadType.RSP:
            print("Probe Found!" + payload.data)
            return payload

        # ------------------HARDWARE HANDLING------------------------
        return self.handler.instruction(payload.data, payload.event)

    def run(self):
        res_data = None
        run = True

        while run:

            req_data = self.request.recv(1024)
            req_data = build_payload(req_data.decode("utf8"))

            if isinstance(req_data, Payload):
                run = req_data.type is not PayloadType.END
                # Sends the received data to be manage by a helper function
                res_data = self.data_handler(req_data, self.del_whitelist)

                print("Data after processing\n Sending %s -> %s" %
                      (print_payload(res_data), self.client_address))

                # Only sends the response if the connection hasn't
                # been requested to close
                if run:
                    send_payload(self.request, res_data)
            else:
                print("[!] Raw data retrieved: {}".format(res_data))
        print("\n[-] Connection to {} Closed \n".format(self.client_address))


def start_lan_server(handler: GPIO_Handler, ip_address="0.0.0.0", port=8000, execution_role="Test Server",
                     name="Unknown", delegation_event_whitelist: list = None, delegator=True):
    global ROLE, NAME, IS_DELEGATOR
    ROLE = execution_role
    NAME = name
    IS_DELEGATOR = delegator

    tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    tcp_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    tcp_socket.bind((ip_address, port))

    print("[!] Server Running {0}:{1}\n".format(ip_address, port))

    client_threads = []

    while True:
        tcp_socket.listen(5)
        (client_socket, (ip, port)) = tcp_socket.accept()
        print("New Connection")
        spawned_thread = Client(ip, port, client_socket, handler, delegation_event_whitelist)
        spawned_thread.start()
        client_threads.append(spawned_thread)

    # Adds to the thread pool
    for thread in client_threads:
        thread.join()
    sys.exit()
