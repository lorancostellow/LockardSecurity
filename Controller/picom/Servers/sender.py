import threading

import time

from picom.Clients.lan import Client


class Sender(threading.Thread, Client):
    global Responses

    def __init__(self, interval=1.0):
        super().__init__()
        self.interval = interval
        self.Responses = []
        self.job_count = 0
        print("[i] Started Sender [interval=%d second(s)]" % interval)

    def run(self):
        while True:
            self.job_count = len(self.Responses)
            if self.job_count > 0:
                for j in self.Responses:
                    print(j)

            time.sleep(self.interval)

    def add_response(self, response, client: Client):
        print("[+] Added response %s => %s" % (response, client.to_json()))
        self.Responses.append((client.to_json(), response))

    def get_response_count(self):
        return self.job_count

    def remove_response(self, id):
        for i in self.Responses:
            if i.id == id:
                self.Responses.remove(i)

    def list_responses(self):
        return self.Responses
