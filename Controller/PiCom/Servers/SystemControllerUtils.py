"""
 Represents the data structures used when integrating with the hardware.
 Here's all of the helpers, classes and handlers for translation of the payloads
 instructions to physical events on the hardware
"""
__version__ = '0.1'
__author__ = 'Dylan Coss <dylancoss1@gmail.com>'

from PiCom.Payload import PayloadEvent, Payload

"""
The responder is used to callback to the server.
    Callbacks include

    1. Signal_Success   -       When the instruction was successful, the server will
                                then tell the client.

    2. Signal_Failed    -       When the instruction was unsuccessful, the server will
                                then tell the client.

    3. Respond          -       When data other than simple signals are to be responded
                                to, these may include temperatures, arguments etc..

    4. respond_with_payload     In some cases, a entire payload may need to be constructed
                                or mutated

"""


class GPIO_Responder:
    def signal_success(self):
        raise NotImplemented("Not Implemented!")

    def signal_failed(self):
        raise NotImplemented("Not Implemented!")

    def respond(self, data, event: PayloadEvent):
        raise NotImplemented("Not Implemented!")

    def respond_with_payload(self, payload: Payload):
        raise NotImplemented("Not Implemented!")


"""
This is handler to for implementation of GPIO based on
information sent by the clients.
"""


class GPIO_Handler(GPIO_Responder):
    def instruction(self, data, event: PayloadEvent):
        pass
