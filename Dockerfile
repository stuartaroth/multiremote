FROM openjdk:8
ADD build/libs/multiremote.jar /multiremote.jar
WORKDIR /
ADD entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
ENTRYPOINT /entrypoint.sh
