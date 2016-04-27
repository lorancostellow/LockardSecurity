import threading
from time import sleep

import zmq

# Author = william Barrett <william.barrett@mycit.ie>

context = zmq.Context()
socket = context.socket(zmq.PAIR)
socket.connect('tcp://autosafe.ddns.net:8009')


def handle_message(message):
    print(message)


class ClientTask(threading.Thread):
    """ClientTask"""

    def __init__(self):
        threading.Thread.__init__(self)

    def send(self, jsonData):
        socket.send(jsonData)

    def run(self):
        while True:
            ############
            # testing
            message = '{"role":"<ALL>","data":\'{\"pdata\":"yolo",\"sid\":\"A1\"}\',"type":"REQ","event":"PANIC","token":"TOKEN"}'
            socket.send(message.encode("UTF-8"))
            sleep(1)
            ##############
            recieved = socket.recv()
            handle_message(recieved)


c = ClientTask()
c.start()
