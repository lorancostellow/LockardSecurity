import picom

C = picom.Client("192.168.1.200", 8000)

S = picom.Sender()
S.add_response("hi", C)
S.start()