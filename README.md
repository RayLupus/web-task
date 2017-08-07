web Usage:
	maven>=3.3.1
	java>=1.7
	
mvn clean package
java -jar target/web-task-1.0-SNAPSHOT.jar

or

mvn spring-boot:run

WEB : src/main/java/jasonlu/com/ui/SampleWebUiApplication.java | default url is http://127.0.0.1:8080
Task I : src/main/java/jasonlu/com/ui/GameOfLife.java
Task II : src/main/java/jasonlu/com/ui/TicTacTone.java

1. Reason why you used specific frameworks and libraries for the front-end and backend

It's faster and more convenient with "mvn archetype:generate" when I need create a new java ee web project, I use a "org.springframework.boot:spring-boot-sample-web-ui-archetype" model.
It contains jquery for front-end, and spring-boot for backend, the mvc is Thymeleaf. For faster I just change code from the template, and I haven't used Thymeleaf before, and I found that it's same as velocity.
spring-boot is a micro-services framework, I use it to design bigger system, front-end jquery is enough, if data need change in time, maybe I will choose React, and it can run on smartphone friendly.

2. How the persistent layer could be implemented?

This project I use memory to store information, I choose redis and mysql if need.

3. How long did it take yo to create the code?

I spend about 0.5 hour to know about Thymeleaf for changing the code, and 1 hour coding, 0.5 hour testing