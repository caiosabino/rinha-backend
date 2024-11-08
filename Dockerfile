# Etapa 1: Usar uma imagem base do Java
FROM openjdk:17-jdk-slim AS build

# Etapa 2: Configurar o diretório de trabalho no container
WORKDIR /app

RUN ./gradlew build
# Etapa 3: Copiar o JAR da aplicação (da sua máquina local ou build)
# Copie o arquivo JAR para dentro do container
COPY build/libs/rinha-backend-0.0.1-SNAPSHOT.jar /app/rinha-backend.jar

# Etapa 4: Expôr a porta que sua aplicação vai rodar
EXPOSE 8080

# Etapa 5: Definir o comando de execução da aplicação
ENTRYPOINT ["java", "-jar", "/app/rinha-backend.jar"]
