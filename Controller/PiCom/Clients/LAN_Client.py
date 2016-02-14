"""
Example Client to be implemented by android and the pi
"""

import socket

from PiCom.Data import Payload, send_payload, receive_payload


class LANClientHandler:
    def received(self, req_payload: Payload, res_payload: Payload):
        pass


class Client:
    def __init__(self, host: str, port: int, handler: LANClientHandler = None, timeout=None,
                 ignore_error: bool = False):
        print("[i] LAN Client %s:%s [timeout=%s] %s" %
              (host, port, (timeout if timeout is not None else "Infinite"),
              ("Ignoring Errors" if ignore_error else "")))
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.sock.settimeout(timeout)
        self.is_connected = False
        self.handler = handler
        self.host = host
        self.port = port

        self.ignore_errors = ignore_error

        self.open_connection()

    def transfer(self, payload: Payload):
        if self.is_connected:
            send_payload(self.sock, payload)
            res_payload = receive_payload(self.sock)
            return payload, res_payload

    def close_connection(self):
        if self.is_connected:
            self.sock.close()
        self.is_connected = False

    def open_connection(self):
        try:
            self.sock.connect((self.host, self.port))
            self.is_connected = True
        except socket.error as err:
            if err.errno is 111:
                print("[!] No Server")
            elif err is socket.socket.timeout:
                print("[!] Timeout")
                raise
            self.is_connected = False

    def send(self, payloads):
        res_list = []
        if self.is_connected:
            if isinstance(payloads, list):
                if self.handler is None:
                    self.close_connection()
                    raise SyntaxError("When sending multiple payloads, the handler must be specified.")

                for payload in payloads:
                    res = self.transfer(payload)
                    res_list.append(res)
                    self.handler.received(self, res[0], res[1])
                return res_list
            elif isinstance(payloads, Payload):
                res = self.transfer(payloads)
                if self.handler is not None:
                    self.handler.received(self, res[0], res[1])
                return res[1]
        else:
            if not self.ignore_errors:
                raise ConnectionError("\n[x] Lan Client was unable to connected to the server.\n"
                                      "\tClient: TCP on: %s:%s" % (self.host, self.port))

