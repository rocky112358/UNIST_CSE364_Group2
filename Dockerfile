#Download ubuntu 20.04.
#ubuntu official image from dockerhub : you need install everything!
FROM ubuntu:20.04

#Install all packages that are needed to run your program, such as vim, git, java 11, maven, etc.
ARG DEBIAN_FRONTEND="noninteractive"

RUN apt-get update
RUN apt-get -y install vim git openjdk-11-jdk maven curl

#Create /root/project folder and set it as WORKDIR.
RUN mkdir -p /root/project
WORKDIR /root/project

#Add your run.sh file to /root/project in the docker container. 
#Your run.sh must include command lines that git clone your_repository, cd your_repository, mvn instll, and java command to run your code. 
#The details are given in Submission Instructions.
COPY run.sh /root/project
RUN chmod 755 run.sh

#The container should execute a bash shell by default when the built image is launched.
RUN /bin/bash
