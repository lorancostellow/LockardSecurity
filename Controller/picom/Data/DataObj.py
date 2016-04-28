from picom import decode_from_json
from picom.Data.PayloadObj import PayloadEncoder
from picom.Data.Structure import PayloadDataFields


class DataObj(PayloadEncoder):

    def __init__(self, id:str, data):
        self.id = id
        self.data = data

    @staticmethod
    def from_dict(data):

        if data is None:
            raise TypeError("The data needed for building the payload is Strings or Dicts.."
                            "\nInputted: %s" % type(data))
        if isinstance(data, str):
            data = decode_from_json(data)
        assert isinstance(data, dict)
        return DataObj(id=data[PayloadDataFields.PAYLOAD_SENSOR_ID.value],
                       data=data[PayloadDataFields.PAYLOAD_DATA_FIELD.value])

    def to_dict(self):
        return {
            PayloadDataFields.PAYLOAD_SENSOR_ID.value: self.id,
            PayloadDataFields.PAYLOAD_DATA_FIELD.value: self.data
        }






