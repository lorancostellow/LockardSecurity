from picom.Delegation.PiDiscovery import\
    set_devices,\
    DEVICES_FILE,\
    update_devices

from picom.helpers import load_collection

__version__ = '0.1'
__author__ = 'Dylan Coss <dylancoss1@gmail.com>'

"""""

This package is responsible for 'Delegating' (Like a DNS server) the requests and responses
in the local network. There will typically be one Server acting as a sort of DNS server, relaying
payloads from the LAN Clients (On Android/WebSocket Server) to specific Units based on the
MAC address of the hardware, and identified by their 'role' name.


            RECEIVED PAYLOAD                |       ROLE          |         MAC                         IP
__________________________________________________________________________________________________________________
REQUEST [Role = Bathroom Controller]  -->   | Bedroom1 Controller | > b8:27:eb:df:8b:dc
                                            | Hallway Controller  | > b8:27:eb:ef:26:ad
                                            | Living Room         | > b8:27:eb:dc:6b:ce
                                         **>| Bathroom Controller | > b8:27:eb:af:35:fc **> ARP > XXX.XXX.XXX.XXX
                                            | Kitchen Controller  | > b8:27:eb:fc:5b:ec


The returned IP address (ARP) is then used by the LANClient to send the payload to the intended unit.
If the request originated outside of the local network, the source IP is that of the Delegating
unit. This is because it has already established a connection the the server that is located
outside of the local network in order to have received the request. The interface for LAN to WAN
is Websockets.

"""""

print("\n[i] Loaded Delegation (Version %s)\n" % __version__)


try:
    set_devices(load_collection(DEVICES_FILE))
except FileNotFoundError:
    update_devices()
