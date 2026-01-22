#!/bin/bash
JAR_NAME="system-mk-backend.jar"
rm -f tpid
echo 'Start application now ......'
exec nohup java -server -Xms512m -Xmx512m -XX:SurvivorRatio=8 -jar $JAR_NAME >nohup.out 2>&1 &
echo $! > tpid
echo Start Success!
