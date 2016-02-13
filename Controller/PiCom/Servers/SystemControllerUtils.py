"""
 Represents the data structures used when integrating with the hardware.
 Here's all of the helpers, classes and handlers for translation of the payloads
 instructions to physical events on the hardware
"""
from PiCom.Data.Structure import EventDomain

__version__ = '0.1'
__author__ = 'Dylan Coss <dylancoss1@gmail.com>'

from PiCom.Data import PayloadEvent, Payload

"""
The responder is used to callback to the server.
    Callbacks include

    1. Signal_Success   -       When the instruction was successful, the server will
                                then tell the client.

    2. Signal_Failed    -       When the instruction was unsuccessful, the server will
                                then tell the client.

    3. respond                  In some cases, a entire payload may need to be constructed
                                or mutated

"""


class Responder:
    def signal_success(self):
        print("Not Implemented!")

    def signal_failed(self):
        print("Not Implemented!")

    def respond(self, payload: Payload):
        print("Not Implemented!")


"""
This is handler to for implementation of GPIO based on
information sent by the clients.
"""


class SYS_Handler(Responder):
    def instruction(self, data, event: PayloadEvent, domain: EventDomain):
        pass

