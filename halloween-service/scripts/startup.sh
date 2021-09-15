#!/bin/bash
nohup java -jar /home/pi/halloween/halloween-service.jar > /home/pi/halloween/log.txt 2>&1 &
echo $! > /home/pi/halloween/pid.file