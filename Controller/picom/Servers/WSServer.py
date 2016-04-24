import tornado.httpserver
import tornado.ioloop
import tornado.options
import tornado.web
import tornado.websocket

"""
Server to deal with async connections to the external WAN
servers.
"""


# Todo: Implement Code

class EventHandler(tornado.websocket.WebSocketHandler):
    def data_received(self, chunk):
        print(chunk)

    def open(self):
        print("Connection Opened")
        self.write_message("Connection Opened")

    def on_close(self):
        print("Connection Closed")

    def on_message(self, message):
        print("Message received: {}".format(message))
        self.write_message("Message received:")

    def check_origin(self, origin):
        return True


if __name__ == "__main__":
    tornado.options.parse_command_line()
    app = tornado.web.Application(handlers=[(r"/", EventHandler)])
    server = tornado.httpserver.HTTPServer(app)
    server.listen(8000)
    tornado.ioloop.IOLoop.instance().start()
