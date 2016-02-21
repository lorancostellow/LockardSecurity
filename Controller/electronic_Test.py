from tkinter import Tk, Button

import RPi.GPIO as GPIO


class GpioGui:
    def __init__(self, master):
        self.master = master
        master.title("A simple GUI")
        self.light_Button = Button(master, text="Light Off", command=self.lights)
        self.light_Button.pack()
        self.alarm_Button = Button(master, text="Alarm Off", command=self.alarm)
        self.alarm_Button.pack()
        self.light_Switch = False
        self.alarm_Switch = False

    def lights(self):
        print("light pressed")
        if self.light_Switch:
            self.light_Button.config(text="Light Off")
            GPIO.output(8,True) ## Turn on GPIO pin 7
            self.light_Switch = False
        else:
            self.light_Button.config(text="Light On")
            self.light_Switch = True


    def alarm(self):
   	    print("you pressed the alarm")
   	    if self.alarm_Switch:
   		    self.alarm_Button.config(text="Alarm Off")
   		    alarmSwitch = False
   	    else:
   		    self.alarm_Button.config(text="Alarm On")
   		    self.alarm_Switch = True

root = Tk()
GPIO.setmode(GPIO.BOARD)
my_gui = GpioGui(root)
GPIO.setup(8, GPIO.OUT) ## Setup GPIO Pin 7 to OUT
GPIO.output(8,True) ## Turn on GPIO pin 7
root.mainloop()