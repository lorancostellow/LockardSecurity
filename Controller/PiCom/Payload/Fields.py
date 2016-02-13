from enum import Enum

__version__ = '0.1'
__author__ = 'Dylan Coss <dylancoss1@gmail.com>'

"""
Represents the feature set of the payloads. Both clients and servers
need to implement the following fields to interpret the payloads

"""


class PayloadFields(Enum):
    PAYLOAD_ROLE = "role"
    PAYLOAD_DATA = "data"
    PAYLOAD_TYPE = "type"
    PAYLOAD_EVENT = "event"


"""
Describes the "Action" of the request being made.
"""


class PayloadEvent(Enum):
    # System management set
    UNK = "Unknown Scheme"
    S_PROBE = "Pi Server Probe"
    D_PROBE = "User Device Probe"
    REGSTN = "Device Registration"
    SYSTEM = "System Message"

    # System feature set
    PANIC = "Panic Button"
    H_ALARM = "House Alarm"
    F_ALARM = "Fire Alarm"
    C_ALARM = "Carbon Monoxide"
    TEXT_ALERT = "Twitter Text Alert"
    RSS_ALERT = "RSS Feed Entry"
    LOCK_STAT = "Lock Status"


"""
Describes the "TypeOf" the request being made
"""


class PayloadType(Enum):
    UNK = "Unknown Type"
    REQ = "Request"
    RSP = "Response"
    END = "Close Connection"
    STA = "Send To All"
    ERR = "Error"
