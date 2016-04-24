"""
 Represents the data structures used when integrating with the hardware.
 Here's all of the helpers, classes and handlers for translation of the payloads
 instructions to physical events on the hardware
"""
from picom.Data.PayloadObj import PayloadEventMessages, Payload
from picom.Data.Structure import EventDomain, PayloadEvent

__version__ = '0.1'
__author__ = 'Dylan Coss <dylancoss1@gmail.com>'


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
    def respond(self, payload: Payload):
        return payload

    def signal_failed(self):
        return PayloadEventMessages.FAILED_SIGNAL

    def signal_success(self):
        return PayloadEventMessages.SUCCESS_SIGNAL


"""
This is handler to for implementation of GPIO based on
information sent by the clients.
"""


class SYS_Handler(Responder):
    def instruction(self, data, event: PayloadEvent, domain: EventDomain, payload: Payload):
        pass
