FROM openjdk

COPY /build/libs/puntosUnisabana-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "app.jar"]
