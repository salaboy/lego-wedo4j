Lego-wedo4j
===========

This is a small library to use the Lego WEDO educational kit from your java app

In order to use this project you will need to have the Lego WEDO HUB and a motor, a sensor or both.

The WEDO Hub is very limited, because you can plug two motors or two sensors only, which means that you will need
an USB Hub for each pair. 

http://www.active-robots.com/legor-wedotm-robotics-usb-hub.html

As you can see it is not a cheap thing to buy, but if you have a couple of those, you can
build a small robot with two motors and two sensors. 

I've copied most of the reverse engineering done by: Ian Daniher and his python project

https://github.com/itdaniher/WeDoMore

But in the Java version I've fixed some issues that he was having with sensing and interacting with the motors
at the same time. 

I've provided support for Distance and Tilt sensor as well as for the Medium Motor.

Medium Lego Motor: http://shop.lego.com/en-GB/LEGO-Power-Functions-M-Motor-8883

Motion/Distance Sensor: http://www.active-robots.com/legor-wedotm-robotics-motion-sensor.html

Tilt Sensor: http://www.active-robots.com/legor-wedotm-robotics-tilt-sensor.html


This project uses the HID Library to access USB devices from java, because this research was started
for the Rolo The Robot project, I've tested this lib inside the Raspberry Pi (which is an ARM board, not a PC) and 
for that reason, if you want to use the HID Lib inside ARM, you will need to compile the HID native libraries inside 
the Raspberry Pi.

I didn't find the HID Lib in any maven repo, and for that reason I've included the jar inside this
project resources files. In order to install this lib inside your local maven repo you need to run the following:

mvn install:install-file  -Dfile=hidapi-1.1.jar   -DgroupId=hid -DartifactId=hid -Dversion=1.0.0 -Dpackaging=jar

being inside the src/main/resources/lib directory of this project.

In order to compile the HID native libraries inside the Raspberry Pi I've did the following steps:

Clone JAVAHIDAPI with hg. We need to do this because the HIDAPI needs to be compiled for the arm7 platform

Install HG

hg clone https://code.google.com/p/javahidapi/

sudo apt-get install libusb-1.0-0-dev

sudo apt-get install libudev-dev

cd javahidapi/linux

Modify Makefile to contain the correct pointers for openjdk6-armhf headers

JAVA6HEADERS=-I/usr/lib/jvm/java-6-openjdk-armhf/include/ -I/usr/lib/jvm/java-6-openjdk-armhf/include/linux cd linux/ make

Run (to compile):

ant

Test: Before modify to Also change classpath to build instead of bin ant run

Plug an USB device and run

ant run

You should see the device being listed. The test is waiting for a PS3 controller

ant dist -> to get a jar, we need to manually install it inside the maven repo, so it can be picked up by maven,
This can be done in the same way that you install it from the src/main/resources/lib directory, just point to the new jar
