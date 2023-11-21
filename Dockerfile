FROM maven:latest
RUN mkdir /myapp
WORKDIR /myapp
COPY . .
EXPOSE 8080
CMD ["mvn", "spring-boot:run"]
