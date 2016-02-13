"""
Example Client to be implemented by android and the pi
"""

import socket

from PiCom.Payload import PayloadEvent, PayloadType, Payload, send_payload, receive_payload

TIMEOUT = None


class LANClientHandler:
    def received(self, req_payload: Payload, res_payload: Payload):
        pass


class LANClient:
    def __init__(self, host: str, port: int, handler: LANClientHandler = None):
        self.HANDLER = handler
        self.HOST = host
        self.PORT = port
        self.sock = None
        self.open_connection()

    def close_connection(self):
        if not self.is_open():
            print("[!] Already Closed")
            return
        send_payload(self.sock, Payload("Goodbye",
                                        PayloadEvent.SYSTEM,
                                        PayloadType.END))
        self.sock.close()

    def open_connection(self):
        if self.is_open():
            print("[!] Already Open!")
            return
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        try:
            self.sock.settimeout(TIMEOUT)
            self.sock.connect((self.HOST, self.PORT))
        except Exception:
            self.sock.close()

    def send(self, payloads):
        if not self.is_open():
            self.open_connection()

        if isinstance(payloads, list):

            for payload in payloads:
                send_payload(self.sock, payload)
                if self.HANDLER is None:
                    raise SyntaxError("When sending multiple payloads, the handler must be specified.")
                self.HANDLER.received(self, payload, receive_payload(self.sock))

    def is_open(self):
        if self.sock is None:
            return False
        return not self.sock._closed
