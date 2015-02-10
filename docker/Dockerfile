FROM ubuntu:latest

RUN DEBIAN_FRONTEND=noninteractive 

# Install java environment
RUN apt-get -qq update && apt-get -qq -y install default-jre default-jdk
#RUN export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64/
 
EXPOSE 8081

# By default, simply start the app
CMD java -classpath /myapp/lib/*:/myapp MyServer
