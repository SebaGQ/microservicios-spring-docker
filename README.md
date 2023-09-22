# microservicios-spring-docker
Ecosistema de microservicios desarrollado con Spring Cloud y contenerizado con Docker. Siguiendo principios SOLID y buenas prácticas de estructura, código, manejo de excepciones, manejo de dependencias y uso de docker.
La idea de este proyecto es demostrar dominio en el desarrollo de arquitecturas basadas en microservicios, siempre de la mano de las buenas prácticas.

[README EN PROGRESO]


Cada proyecto está en su propia branch, están detalladamente documentados y en ellos se explica las buenas prácticas aplicadas y el por qué de cada decisión tomada.
Se puede encontrar la documentación en las diversas capas de la aplicacion, como en los controller, services, entities, etc, así como en los archivos claves de configuración, como los Dockerfile o los application.yml.

Actualmente el proyecto está formado por Eureka, Gateway, Cliente-Mesa-APP , Reservas-APP.
Cliente-Mesa-APP y Reservas-APP consisten en API Rest comunicadas entre sí, mientras que Eureka y Gateway son parte clave de un ecosistema de microservicios desarrollado con Spring Cloud.
