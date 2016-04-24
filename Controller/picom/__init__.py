from .core import start
from .Delegation.PiDiscovery import *
from .Clients.lan import *
from .Servers.lan import start_lan_server
from .Servers.sender import Sender


def check_internal_compatibility():
    from .Data.Structure import __version__ as s_v
    from .Data.PayloadObj import __version__ as p_v
    assert (s_v == p_v), "Please ensure all the internal packages are compatible"
