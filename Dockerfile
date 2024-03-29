FROM amazoncorretto:17
USER 1234
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENV SPRING_OUTPUT_ANSI_ENABLED="ALWAYS"
ENV JAVA_OPTS=""

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -noverify -XX:+AlwaysPreTouch -jar /app.jar"]
