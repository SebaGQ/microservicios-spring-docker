# Microservicios con Spring y Docker

Ecosistema de microservicios desarrollado con **Spring Cloud** y contenerizado con **Docker**. Se han seguido los principios SOLID, además de buenas prácticas de desarrollo, tales como: código limpio, manejo robusto de excepciones y manejo eficiente de dependencias, entre otras. El objetivo de este proyecto es demostrar dominio en el desarrollo de arquitecturas sólidas basadas en microservicios.

Los contenedores Docker de estos microservicios están disponibles en el siguiente repositorio público: https://hub.docker.com/r/sebagq/microservicios-springcloud

El `docker-compose.yml` que se encuentra en el branch main puede usarse para cargar las imagenes desde ahí y ejecutar la aplicación fácilmente, en la sección **Cómo ejecutar** se encuentran los detalles.


> **Nota**: Este README no está terminado.

## Contenido

Cada microservicio está en su propia rama y cuenta con una documentación detallada. En ellos, se explican las buenas prácticas aplicadas y las razones por las que se tomó cada decisión. La documentación se puede encontrar en diversas capas de la aplicación, como en los `controllers`, `services`, `entities`, etc. También se documentan archivos clave de configuración como los `Dockerfile` o los `application.yml`.

### Microservicios Actuales

- **Eureka**: Servicio de descubrimiento.
- **Gateway**: Puerta de enlace para la comunicación.
- **Cliente-Mesa-APP**: API Rest.
- **Reservas-APP**: API Rest.

Los servicios `Cliente-Mesa-APP` y `Reservas-APP` son API Rest que funcionan en conjunto. En cambio, `Eureka` y `Gateway` son componentes clave del ecosistema Spring Cloud.

### Cómo ejecutar

Para ejecutar la aplicación basta con ejecutar el `docker-compose.yml` que se encuentra en el branch main.
Para ejecutarlo debemos tener instalado Docker, y GIT.
1. Ir a algún directorio donde queramos descargar el `docker-compose.yml` .
2. Ejecutar en la consola: **git clone -b main https://github.com/SebaGQ/microservicios-spring-docker.git**
3. Ir al directorio /microservicios-spring-docker que se acaba de crear  y ejecutar **docker-compose up** .
4. Esperar a que inicie la aplicación (puede demorar un poco en descargarse, depende del internet), es importante tener docker abierto.


## Pendientes

Hay ciertas funcionalidades y características que no se implementaron, como:

- Servicio de centralización de configuraciones.
- Seguridad con Oauth2.
- Herramientas de metricas y monitoreo.

Se decidió no implementar estas características de momento para no añadir complejidad innecesaria, ya que la finalidad de este proyecto es mostrar conocimiento sobre microservicios y buenas practicas. Tal vez se agreguen en el futuro.
