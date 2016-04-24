import threading
import socket

from picom.helpers import \
    decode_from_json, \
    encode_to_json

from .Structure import \
    Enum, \
    PayloadEvent, \
    PayloadType, \
    BLANK_FIELD, \
    EventTypes, \
    EventDomain, \
    PayloadFields

lock = threading.Lock()
__version__ = "1.3"


class PayloadEncoder(object):
    def to_dict(self):
        pass

    @staticmethod
    def from_dict(data):
        pass


class Payload(PayloadEncoder):
    def __init__(self, data, event: PayloadEvent, requestype: PayloadType, role: str = None):
        assert isinstance(event, PayloadEvent) and isinstance(requestype, PayloadType)
        self.data = data
        self.event = event
        self.type = requestype

        if role is None:
            role = BLANK_FIELD

        self.role = role

    def to_dict(self):
        return {PayloadFields.PAYLOAD_DATA.value: self.data,
                PayloadFields.PAYLOAD_ROLE.value: self.role,
                PayloadFields.PAYLOAD_EVENT.value: self.event.name,
                PayloadFields.PAYLOAD_TYPE.value: self.type.name}

    @staticmethod
    def from_dict(data):
        if data is None:
            raise TypeError("The data needed for building the payload is Strings or Dicts.."
                            "\nInputted: %s" % type(data))
        if isinstance(data, str):
            data = decode_from_json(data)
        assert isinstance(data, dict)
        return Payload(data=data[PayloadFields.PAYLOAD_DATA.value],
                       event=PayloadEvent[data[PayloadFields.PAYLOAD_EVENT.value]],
                       requestype=PayloadType[data[PayloadFields.PAYLOAD_TYPE.value]],
                       role=data[PayloadFields.PAYLOAD_ROLE.value])


class PayloadEventMessages(Enum):
    WRONG_NODE = Payload({'message': "Request was not intended for the unit"},
                         PayloadEvent.CLIENT_ERROR, PayloadType.RSP)
    SERVER_ERROR = Payload({'message': "There was a unexpected server side error!"},
                           PayloadEvent.SERVER_ERROR, PayloadType.RSP)
    ERROR = Payload({'message': "There was a unexpected error..."},
                    PayloadEvent.UNKNOWN_ERROR, PayloadType.RSP)
    SUCCESS_SIGNAL = Payload(BLANK_FIELD, PayloadEvent.SUCCESS_SIG, PayloadType.RSP)

    FAILED_SIGNAL = Payload(BLANK_FIELD, PayloadEvent.FAILED_SIG, PayloadType.RSP)

    ADDRESS_NOT_FOUND = Payload({'message': "Unable to connect to client"},
                                PayloadEvent.CLIENT_ERROR, PayloadType.RSP)


def get_event_message(event_message: PayloadEventMessages, message: str = None):
    assert isinstance(event_message, PayloadEventMessages)
    payload = event_message.value
    if message is not None:
        payload.data = {'message': message}
    return payload


def get_event_domain(event: PayloadEvent):
    is_soft = event in EventTypes.SOFTWARE_EVENT.value
    is_hard = event in EventTypes.HARDWARE_EVENTS.value
    return EventDomain.SOFT if is_soft else (EventDomain.GPIO if is_hard else None)


def send_payload(sock: socket, payload: PayloadEncoder or PayloadEventMessages, address=None):
    if sock is None or sock._closed or payload is None:
        print("[x] Unable to send payload [Payload %s, Socket %s]" % (payload, socket))
        return
    if isinstance(payload, PayloadEventMessages):
        payload = payload.value

    if address is None:
        sock.sendall(encode_to_json(payload.to_dict()).encode("utf-8"))
    else:
        sock.sendto(encode_to_json(payload.to_dict()).encode("utf-8"), address)


def receive_payload(sock: socket):
    data = str(sock.recv(1024), "utf-8")
    if data is not None and len(data) > 0:
        received = decode_from_json(data)
        return Payload.from_dict(received)


def to_string(payload_data):
    if isinstance(payload_data, PayloadEncoder):
        payload_data = payload_data.to_dict()
        return ("Payload: [ {0} | {1} | {2} | {3} ] ".format(payload_data[PayloadFields.PAYLOAD_DATA.value],
                                                             payload_data[PayloadFields.PAYLOAD_ROLE.value],
                                                             payload_data[PayloadFields.PAYLOAD_EVENT.value],
                                                             payload_data[PayloadFields.PAYLOAD_TYPE.value]))
    return payload_data
