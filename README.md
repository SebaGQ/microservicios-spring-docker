# Microservicios con Spring y Docker

Ecosistema de microservicios desarrollado con **Spring Cloud** y contenerizado con **Docker**. Se han seguido los principios SOLID, además de buenas prácticas en estructura, código, manejo de excepciones y manejo de dependencias. El objetivo de este proyecto es demostrar dominio en el desarrollo de arquitecturas basadas en microservicios, manteniendo siempre dominio sobre las buenas prácticas.

> **Nota**: Este README se encuentra en progreso.

## Contenido

Cada microservicio está en su propia rama y cuenta con una documentación detallada. En ellos, se explican las buenas prácticas aplicadas y las razones por las que se tomó cada decisión. La documentación se puede encontrar en diversas capas de la aplicación, como en los `controllers`, `services`, `entities`, etc. También se documentan archivos clave de configuración como los `Dockerfile` o los `application.yml`.

### Microservicios Actuales

- **Eureka**: Servicio de descubrimiento.
- **Gateway**: Puerta de enlace para la comunicación.
- **Cliente-Mesa-APP**: API Rest.
- **Reservas-APP**: API Rest.

Las APIs Rest `Cliente-Mesa-APP` y `Reservas-APP` se comunican entre sí. En cambio, `Eureka` y `Gateway` son componentes clave del ecosistema desarrollado con Spring Cloud.

## Pendientes

Hay ciertas funcionalidades y características que no se implementaron, como:

- Servicio de centralización de configuraciones.
- Seguridad con Oauth2.
- Herramientas de metricas y monitoreo.

Se decidió no implementar estas características de momento para no añadir complejidad innecesaria, ya que la finalidad de este proyecto es mostrar conocimiento sobre microservicios y buenas practicas. Tal vez se agreguen en el futuro.
