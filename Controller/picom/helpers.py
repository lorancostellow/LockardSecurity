import json
import os


def save_collection(filename: str, obj: dict or list):
    with open(filename, 'w') as f:
        f.write(encode_to_json(obj))
    return obj


def load_collection(filename):
    if os.path.isfile(filename):
        with open(filename, 'r') as f:
            return decode_from_json(f.read())
    raise FileNotFoundError


def encode_to_json(data):
    return json.dumps(data)


def decode_from_json(json_str: str):
    try:
        if len(json_str) > 0:
            return json.loads(json_str)
    except Exception:
        pass
    return ""

