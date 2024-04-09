FROM openjdk:17
WORKDIR /app
ARG JAR_FILE=build/libs/*.jar
ARG APM_AGENT_FILE=elastic-apm-agent-1.49.0.jar
COPY ${JAR_FILE} app.jar
COPY ${APM_AGENT_FILE} elastic-apm-agent.jar
EXPOSE 8080
CMD ["java","-jar","app.jar"]
ENTRYPOINT ["java", "-javaagent:elastic-apm-agent.jar", "-Delastic.apm.service_name=application-server", "-Delastic.apm.server_urls=http://13.125.23.24:8200", "-Delastic.apm.application_packages=com.example.pigonair", "-jar", "app.jar"]