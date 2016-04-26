import socket
import threading

import zmq

#Author = william Barrett <william.barrett@mycit.ie>
context = zmq.Context()
socketZMQ = context.socket(zmq.PAIR)
socketZMQ.connect('tcp://localhost:8080')

def handle_message(message):

    TCP_IP = '127.0.0.1'
    TCP_PORT = 8000
    BUFFER_SIZE = 1024

    stdSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    stdSocket.connect((TCP_IP, TCP_PORT))
    stdSocket.send(message)

    data = stdSocket.recv(BUFFER_SIZE)
    stdSocket.close()

class ClientTask(threading.Thread):
    """ClientTask- thread that probes network and waits"""
    def __init__(self):
        threading.Thread.__init__ (self)

    def send(self,jsonData):
        socketZMQ.send(jsonData)

    def run(self):
        while True:
            print("WebRouter Starting")
            socketZMQ.send("".encode("UTF-8"))
            recieved = socketZMQ.recv()
            print(recieved.decode("UTF-8") + "-recieved from server")
            handle_message(recieved)
