import re
import subprocess

from PiCom.Clients.LANClient import LANClientHandler, LANClient
from PiCom.Payload import Payload, save, PayloadEvent, PayloadType, PayloadFields

DEVICES_FILE = '/tmp/devices.json'
devices = []


def set_devices(d):
    global devices
    devices = d
    print("[i] Delegating #%d client(s)" % len(devices))


def get_units():
    out = ""
    proc = subprocess.Popen("arp -na | awk '{print $2 \"|\" $4}'",
                            shell=True,
                            stdout=subprocess.PIPE)
    for line in proc.stdout:
        out += (line.decode("UTF-8"))
    return re.findall('\((.*?)\)\|(.*)', out)


def probe(units: list):
    global devices
    devices = []

    class Connection_Handler(LANClientHandler):

        def received(self, req_payload: Payload, res_payload: Payload):
            print("[i] Unit Found! ({0})\n".format(res_payload.data['role']))

            # Adds mac address to the record
            res_payload.data['mac'] = unit[1]

            # Adds it to the list
            devices.append(res_payload.data)

    for unit in units:
        if not str(unit[1]).__contains__('<'):
            print("Probing: {0}".format(unit))
            l = LANClient(unit[0], 8000, Connection_Handler)
            if l.is_open():
                l.send([Payload("Probing Scan", PayloadEvent.S_PROBE, PayloadType.REQ)])
                l.close_connection()

    return save(DEVICES_FILE, devices)


def update_devices():
    probe(get_units())


def get_ip_addresses(filter_by_role: str = None, ):
    global devices
    addresses = []
    for device in devices:
        if filter_by_role is None:
            addresses.append(get_ip_address(device['mac']))
        elif filter_by_role == device[PayloadFields.PAYLOAD_ROLE]:
            addresses.append(get_ip_address(device['mac']))
    return addresses


def get_ip_address(mac: str):
    units = get_units()
    for u in units:
        if u[1] == mac:
            return u[0]
    return None
