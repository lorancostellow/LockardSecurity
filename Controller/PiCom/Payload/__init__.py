import json
import os
import socket
from enum import Enum

from PiCom.Payload.Structure import PayloadType, PayloadEvent, PayloadFields, BLANK_FIELD

__version__ = '0.1'
__author__ = 'Dylan Coss <dylancoss1@gmail.com>'

"""
     Represents the payload that is sent through a Websocket/Sockets connection.
"""


class PayloadEncoder(object):
    def content(self):
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

    def content(self):
        return {PayloadFields.PAYLOAD_DATA.value: self.data,
                PayloadFields.PAYLOAD_ROLE.value: self.role,
                PayloadFields.PAYLOAD_EVENT.value: self.event.name,
                PayloadFields.PAYLOAD_TYPE.value: self.type.name}


class PayloadEventMessages(Enum):
    END_CONNECTION = Payload("Goodbye", PayloadEvent.SYSTEM, PayloadType.REQ)
    WRONG_NODE = Payload({'message': "Request was not intended for the unit"},
                         PayloadEvent.CLIENT_ERROR, PayloadType.RSP)
    SERVER_ERROR = Payload({'message': "There was a unexpected server side error!"},
                           PayloadEvent.SERVER_ERROR, PayloadType.RSP)
    ERROR = Payload({'message': "There was a unexpected error..."},
                    PayloadEvent.UNKNOWN_ERROR, PayloadType.RSP)


def build_payload(data):
    if isinstance(data, str):
        data = decode_from_json(data)

    assert isinstance(data, dict)

    return Payload(data=data[PayloadFields.PAYLOAD_DATA.value],
                   event=PayloadEvent[data[PayloadFields.PAYLOAD_EVENT.value]],
                   requestype=PayloadType[data[PayloadFields.PAYLOAD_TYPE.value]],
                   role=data[PayloadFields.PAYLOAD_ROLE.value])


def save(filename: str, obj: dict or list):
    with open(filename, 'w') as f:
        f.write(encode_to_json(obj))
    return obj


def load(filename):
    if os.path.isfile(filename):
        with open(filename, 'r') as f:
            return decode_from_json(f.read())
    raise FileNotFoundError


def send_payload(sock: socket, payload: PayloadEncoder, address=None):
    assert payload is not None
    if address is None:
        sock.sendall(encode_to_json(payload.content()).encode("utf-8"))
    else:
        sock.sendto(encode_to_json(payload.content()).encode("utf-8"), address)


def receive_payload(sock: socket):
    received = decode_from_json(str(sock.recv(1024), "utf-8"))
    return build_payload(received)


def encode_to_json(data):
    return json.dumps(data)


def decode_from_json(json_str: str):
    try:
        if len(json_str) > 0:
            return json.loads(json_str)
    except Exception:
        pass
    return ""


def print_payload(payload_data):
    if isinstance(payload_data, Payload):
        return ("Payload: [ {0} | {1} | {2} | {3} ] ".format(payload_data.data,
                                                             payload_data.role,
                                                             payload_data.event,
                                                             payload_data.type))
    return payload_data
