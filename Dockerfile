FROM openjdk:17-slim as builder

# Establecer el directorio de trabajo
WORKDIR /workspace/app

# Se busca que las dependencias se descarguen en la etapa de construcción de la imagen, y no en la de ejecución.
# Esto mejora considerablemente el tiempo de ejecución, impacta positivamente el auto-scaling y disminuye el uso de recursos.

# Copiar el archivo pom.xml y descargar las dependencias
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline

# Copiar el código fuente y compilar el jar
COPY src src
RUN ./mvnw package -DskipTests

# Etapa de ejecución, crear la imagen final
FROM openjdk:17-slim

VOLUME /tmp

# Copiar el jar creado con mvnw
COPY --from=builder /workspace/app/target/eureka-0.0.1-SNAPSHOT.jar app.jar

#Exponer puerto
EXPOSE 8761

# Definir el punto de entrada.
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
# Al ser contenedores tienen poca entropía (ej: movimiento de mouse, pulsaciones de teclas, etc)
# lo que aumenta los tiempos de respuesta para generar valores aleatorios.
# Esto por que la JVM espera a que haya entropía suficiente para arrojar el valor.
# Por esto se usa /urandom , le dice a la JVM que no espere, que arroje valores aunque la entropía sea baja.