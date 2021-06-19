#Download ubuntu 20.04.
#ubuntu official image from dockerhub : you need install everything!
FROM ubuntu:20.04

#Install all packages that are needed to run your program, such as vim, git, java 11, maven, etc.
ARG DEBIAN_FRONTEND="noninteractive"

RUN apt-get update
RUN apt-get -y install vim git openjdk-11-jdk maven curl mongodb

#Create /root/project folder and set it as WORKDIR.
RUN mkdir -p /root/project
WORKDIR /root/project

#Download and install Tomcat
RUN mkdir /opt/tomcat
RUN curl -LO http://mirror.23media.de/apache/tomcat/tomcat-8/v8.5.30/bin/apache-tomcat-8.5.30.tar.gz
RUN tar xvf apache-tomcat-8.5.30.tar.gz -C /opt/tomcat --strip-components=1

RUN groupadd tomcat
RUN useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat
RUN cd /opt/tomcat
RUN chgrp -R tomcat /opt/tomcat/conf
RUN chmod g+rwx /opt/tomcat/conf
RUN chmod g+r /opt/tomcat/conf/*
RUN rm -rf /opt/tomcat/webapps/ROOT

#Copy WAR file from the host machine to the proper directory in docker
COPY ROOT.war /opt/tomcat/webapps
RUN chmod 755 /opt/tomcat/webapps/ROOT.war

COPY run.sh /root/project
RUN chmod 755 run.sh

RUN echo '#!/bin/bash' >> /root/project/start.sh 
RUN echo 'mongod --fork --dbpath=/var/lib/mongodb --logpath=/var/log/mongod.log' >> /root/project/start.sh
RUN echo '/opt/tomcat/bin/catalina.sh run' >> /root/project/start.sh
RUN echo '/bin/bash' >> /root/project/start.sh
RUN chmod a+x /root/project/start.sh

CMD ["/root/project/start.sh"]
ENTRYPOINT ["/bin/bash", "-c"]

#Run /to/tomcat/catalina.sh start
#RUN /opt/tomcat/bin/catalina.sh start
