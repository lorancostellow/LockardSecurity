import re
import subprocess

from PiCom.Clients.LAN_Client import LANClientHandler, Client
from PiCom.Data import Payload, save, PayloadEvent, PayloadType, PayloadFields, BLANK_FIELD, print_payload

DEVICES_FILE = '/tmp/devices.json'
devices = []


def set_devices(d):
    global devices
    devices = d
    print("[i] #%d node(s) found" % len(devices))


def get_units():
    prc = subprocess.Popen("arp -na | awk '{print $2 \"|\" $4}'",
                           shell=True,
                           stdout=subprocess.PIPE)
    out = ""
    for line in prc.stdout:
        out += (line.decode("UTF-8"))
    return re.findall('\((.*?)\)\|(.*)', out)


def probe(units: list):
    global devices
    devices = []

    class ConnectionHandler(LANClientHandler):

        def received(self, req_payload: Payload, res_payload: Payload):
            print("[i] Unit Found! ({0})\n".format(res_payload.data['role']))

            # Adds mac address to the record
            res_payload.data['mac'] = unit[1]

            # Adds it to the list
            devices.append(res_payload.data)

    for unit in units:
        if not str(unit[1]).__contains__('<'):
            print("Probing: {0}".format(unit))

            l = Client(unit[0], 8000, ConnectionHandler, timeout=2)


            print(print_payload(l.send(Payload(BLANK_FIELD, PayloadEvent.S_PROBE, PayloadType.REQ))))
            l.close_connection()
            # l.send(Payload("Probing Scan", PayloadEvent.S_PROBE, PayloadType.REQ))
            # print(unit)
            # l.close_connection()

    return save(DEVICES_FILE, devices)


def update_devices():
    probe(get_units())


def get_ip_addresses(filter_by_role: str = None):
    print('Address Lookup %s' % filter_by_role)
    update_devices()
    global devices
    addresses = []
    for device in devices:
        if filter_by_role is None:
            addresses.append(get_ip_address(device['mac']))
            print(device)
        elif filter_by_role == device[PayloadFields.PAYLOAD_ROLE]:
            print(device['role'])
            addresses.append(get_ip_address(device['mac']))
    return addresses


def get_ip_address(mac: str):
    print(mac)
    units = get_units()
    for u in units:
        if u[1] == mac:
            return u[0]
    return None
