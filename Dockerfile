FROM openjdk:8
COPY target/ticket_reservation_system-0.0.1-SNAPSHOT.jar ticket_reservation_system-0.0.1.jar
ENTRYPOINT [ "java","-jar","/ticket_reservation_system-0.0.1.jar" ]
