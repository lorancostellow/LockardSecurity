import json
import unittest

from PiCom.Payload import PayloadType, PayloadEvent, Payload, build_payload


class BuildTests(unittest.TestCase):
    def param_association(self):
        type = PayloadType.UNK
        event = PayloadEvent.UNK
        data = "test"
        role = "role"
        p = Payload(data, event, type, role)
        self.assertEqual(type, p.type)
        self.assertEqual(event, p.event)
        self.assertEqual(data, p.data)
        self.assertEqual(role, p.role)
        p = Payload(data, event, type)
        self.assertEqual(p.role, '<ALL>')
        print("Param Association: Passed")
        return p

    def test_payload_build(self):
        x = self.param_association()
        y = build_payload(x.content())
        self.assertEqual(x.content(), y.content())
        print("Build: DICT :Passed")
        y = build_payload(json.dumps(x.content()))
        self.assertEqual(x.content(), y.content())
        print("Build: JSON :Passed")


if __name__ == '__main__':
    unittest.main()
