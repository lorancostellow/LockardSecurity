from enum import Enum

__version__ = '1.2'
__author__ = 'Dylan Coss <dylancoss1@gmail.com>'

"""
Represents the feature set of the payloads. Both clients and servers
need to implement the following fields to interpret the payloads

"""

WILDCARD = '<ALL>'
BLANK_FIELD = '<BLANK>'


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
    SERVER_ERROR = "Server Error"
    CLIENT_ERROR = "Client Error"
    UNKNOWN_ERROR = "Unknown Error"
    SUCCESS_SIG = "Event was successful"
    FAILED_SIG = "Event was unsuccessful"
    LIST = "List instruction"
    REMOVE = "Remove Instruction"

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
    JOB = "Job request"
    ACK = "Acknowledged"


class EventDomain(Enum):
    GPIO = "System IO Pin"
    SOFT = "Software Event"


class EventTypes(Enum):
    HARDWARE_EVENTS = [
        PayloadEvent.PANIC,
        PayloadEvent.H_ALARM,
        PayloadEvent.F_ALARM,
        PayloadEvent.C_ALARM,
        PayloadEvent.LOCK_STAT
    ]

    SOFTWARE_EVENT = [
        PayloadEvent.TEXT_ALERT,
        PayloadEvent.RSS_ALERT,
    ]

    SYSTEM_EVENTS = [
        PayloadEvent.S_PROBE,
        PayloadEvent.D_PROBE,
        PayloadEvent.REGSTN,
        PayloadEvent.REMOVE,
        PayloadEvent.LIST
    ]

    REQUEST_TYPES = [
        PayloadType.REQ,
        PayloadType.JOB
    ]
