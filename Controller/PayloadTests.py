import json
import unittest

from PiCom.Data import PayloadType, PayloadEvent, Payload, build_payload, BLANK_FIELD


class BuildTests(unittest.TestCase):
    def param_association(self, p: Payload, data, type, event, role):

        self.assertEqual(type, p.type)
        self.assertEqual(event, p.event)
        self.assertEqual(data, p.data)
        self.assertEqual(role, p.role)
        print("Param Association: Passed")
        return p

    def test_payload_build(self):
        type = PayloadType.UNK
        event = PayloadEvent.UNK
        data = "test_data"
        role = "test_role"
        p = Payload(data, event, type, role)
        x = build_payload(p.content())
        y = self.param_association(x, data, type, event, role)
        self.assertEqual(x.content(), y.content())
        print("Build: DICT :Passed")
        y = build_payload(json.dumps(x.content()))
        self.assertEqual(x.content(), y.content())
        print("Build: JSON :Passed")
        p = Payload(data, event, type)
        self.assertEqual(BLANK_FIELD, p.role)
        print("Build: Default Role :Passed")



if __name__ == '__main__':
    unittest.main()
