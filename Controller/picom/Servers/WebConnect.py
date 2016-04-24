import threading

import zmq

#Author = william Barrett <william.barrett@mycit.ie>

from picom.Data.PayloadObj import Payload

context = zmq.Context()
socket = context.socket(zmq.PAIR)
socket.connect('tcp://autosafe.ddns.net:8009')

def handle_message(message):
    Payload.from_dict(message)

class ClientTask(threading.Thread):
    """ClientTask"""
    def __init__(self):
        threading.Thread.__init__ (self)

    def send(self,jsonData):
        socket.send(jsonData)

    def run(self):
        while True:
            print("Web Listner Starting")
            ############
            #testing
            message = "Request a payload"
            socket.send(message.encode("UTF-8"))
            ##############
            recieved = socket.recv()
            handle_message(recieved)


#  = ClientTask()
#client.start()
