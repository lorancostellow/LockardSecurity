import re
import subprocess

from picom.Clients.lan import\
    Client

from picom.Data.PayloadObj import\
    Payload

from picom.Data.Structure import\
    WILDCARD,\
    BLANK_FIELD,\
    PayloadEvent,\
    PayloadType

from picom.helpers import\
    save_collection

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
    out = "(192.168.1.200)|5c:f9:dd:6b:33:06\n"
    for line in prc.stdout:
        out += (line.decode("UTF-8"))
    return re.findall('\((.*?)\)\|(.*)', out)


def probe(units: list):
    global devices
    devices = []

    for unit in units:
        # sudo arp-scan --interface=eth0 --localnet
        if not str(unit[1]).__contains__('<'):
            l = Client(unit[0], 8000, timeout=.5, ignore_error=True)
            response = l.send(Payload(BLANK_FIELD, PayloadEvent.S_PROBE, PayloadType.REQ))
            if isinstance(response, Payload) and response.event is PayloadEvent.S_PROBE:
                response.data['mac'] = unit[1]
                devices.append(response.data)

    print("[i] Found %d nodes" % len(devices))
    return save_collection(DEVICES_FILE, devices)


def update_devices():
    probe(get_units())


def get_ip_addresses(filter_by_role: str = WILDCARD):

    global devices
    addresses = []
    for device in devices:
        mac, role = device['mac'], device['role']
        if filter_by_role == WILDCARD:
            addresses.append(get_ip_address(mac))
        elif filter_by_role == role:
            addresses.append(get_ip_address(mac))
    return addresses


def get_ip_address(mac: str):
    units = get_units()
    for u in units:
        if u[1] == mac:
            return u[0]
    return None
