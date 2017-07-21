FROM openjdk:8u131-jdk
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
COPY target/demo.jar /opt
CMD java -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Xmx4g -jar /opt/demo.jar