import json

import RPi.GPIO as GPIO
GPIO.setmode(GPIO.BOARD)
mode = GPIO.getmode()

COMPONENT_LIST = []
PATH = "/home/dotcom/Desktop/configuration.json"

class ComponentObj:
    def __init__(self, id, gin, gout):
        self.id = id
        self.gin = gin
        self.gout = gout

    def turnon(id):
        for comp in COMPONENT_LIST:
            if(comp.id) == id:
                GPIO.setup(comp.gout, GPIO.OUT)
                GPIO.setup(comp.gin, GPIO.IN)
                GPIO.output(comp.gin,True)

    def turnoff(id):
        for comp in COMPONENT_LIST:
            if(comp.id) == id:
                GPIO.setup(comp.gout, GPIO.OUT)
                GPIO.setup(comp.gin, GPIO.IN)
                GPIO.output(comp.gin,True)


#Load the configuration file in and store the objects into an array
json_data=open(PATH).read()
data = json.loads(json_data)
for d in data:
    component = ComponentObj(d['id'],d['GPIO_IN'],d['GPIO_OUT'])
    COMPONENT_LIST.append(component)



#comp1 = ComponentObj(2841,2,14)
#comp2 = ComponentObj(8962,2,14)
#l = [comp1, comp2]

#json_string =json.dumps([o.__dict__ for o in l])

#c = json.loads(json_string)

#print(c)
#print(json_string)