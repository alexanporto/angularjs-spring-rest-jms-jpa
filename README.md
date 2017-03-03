# angularjs-spring-rest-jms-jpa
Trata-se de um projeto maven para construção de uma aplicação java spa web utilizando spring boot, com a IDE Spring Tool Suite.

## Tecnologias
Maven
AngularJS e AngularJS Router
Spring Boot Web 
Spring Boot JMS
Spring Boot Data JPA
Spring Boot Thymeleaf
Spring Framework JMS
Apache Activemq Broker
MySQL Database

## Gerando a base de dados
<p>
Para rodar a aplicação importe o projeto mave na IDE, preferencialmente a Spring Tool Suite.
Um servidor mysql deve estar instalado, ative-o.
Rode o projeto como uma aplicação spring boot. 
Na primeira vez, o hibernate irá gerar o banco de dados vazio devido à propriedade spring.jpa.hibernate.ddl-auto estar setada com "create": 
<br/><b>spring.jpa.hibernate.ddl-auto=create</b><br/>
A partir daí comente esta propriedade para pode utilizar o banco de dados normalmente:<br/>
<b>#spring.jpa.hibernate.ddl-auto=create</b>
</p>

