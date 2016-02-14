from PiCom.Servers.LAN_Server import start_lan_server

__version__ = '1.6'
__author__ = "Dylan Coss <dylancoss1@gmail.com>"


class LANServer:
    def __init__(self, handler, ip_address="0.0.0.0", port=8000, execution_role="NoRole",
                 name="Unknown", delegation_event_whitelist: list = None, delegator: bool = True):
        self.handler = handler
        self.ip = ip_address
        self.port = port
        self.role = execution_role
        self.name = name
        self.dwl = delegation_event_whitelist
        self.is_delegator = delegator

    def start(self):
        start_lan_server(self.handler, self.ip, self.port,
                         self.role, self.name, self.dwl, self.is_delegator)

    def set_name(self, name: str):
        self.name = name

    def set_exec_role(self, role: str):
        self.role = role

    def set_is_delegator(self, delegator: bool):
        self.is_delegator = delegator

    def set_whitelist(self, list_of_events: list):
        assert isinstance(list_of_events, list)
        self.dwl = list_of_events


class WebSocket:
    def __init__(self):
        # TODO: Implement
        print("Not Implemented")
