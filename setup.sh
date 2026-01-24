#!/bin/bash
echo '---- 拉取最新代码'
git pull
#read -p '---- 拉取最新代码完成,回车执行clean'
echo '---- 开始执行clean'
mvn clean -f pom.xml
#read -p  '---- clean完成,回车执行构建'
echo '---- 构建开始'
mvn package -f pom.xml
echo '---- 构建完成'
echo '---- 开始复制jar包'
cp -rf ./backend/target/system-mk-backend.jar /home/jars/system-mk-backend.jar
cp -rf ./backend/target/system-mk-front.jar /home/jars/system-mk-front.jar

echo '---- 复制完成'