import threading

import zmq

context = zmq.Context()
socket = context.socket(zmq.PAIR)
socket.connect('tcp://autosafe.ddns.net:8009')

def handle_message(message):
    print(message + " - recieved at client")

class ClientTask(threading.Thread):
    """ClientTask"""
    def __init__(self):
        threading.Thread.__init__ (self)

    def send(self,jsonData):
        socket.send(jsonData)

    def run(self):
        while True:
            recieved = socket.recv()
            handle_message(recieved.decode("UTF-8"))


client  = ClientTask()
client.start()
