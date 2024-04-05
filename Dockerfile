FROM openjdk:17
WORKDIR /app

# Add Pinpoint
ADD https://github.com/pinpoint-apm/pinpoint/releases/download/v2.5.2/pinpoint-agent-2.5.2.tar.gz /usr/local
RUN tar -zxvf /usr/local/pinpoint-agent-2.5.2.tar.gz -C /usr/local

# Update the Pinpoint configuration
RUN sed -i 's/profiler.transport.grpc.collector.ip=127.0.0.1/profiler.transport.grpc.collector.ip=52.78.141.125/g' /usr/local/pinpoint-agent-2.5.2/pinpoint-root.config
RUN sed -i 's/profiler.collector.ip=127.0.0.1/profiler.collector.ip=52.78.141.125/g' /usr/local/pinpoint-agent-2.5.2/pinpoint-root.config

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /app/app.jar
EXPOSE 8080
CMD ["java","-jar","app.jar"]
ENTRYPOINT ["java", \
 "-javaagent:/pinpoint-agent/pinpoint-bootstrap-2.5.3.jar", \
 "-Dpinpoint.agentId=aws-ec2-1", \
 "-Dpinpoint.applicationName=hh99finalproject", \
 "-Dspring.profiles.active=prod", \
 "-jar", "/app.jar"]