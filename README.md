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


