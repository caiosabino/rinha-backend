# Etapa 1: Build
FROM gradle:7.5.1-jdk17 AS build
WORKDIR /app

# Copia todos os arquivos do projeto para o diretório de trabalho do contêiner
COPY . .
# Executa o build do Gradle para gerar o arquivo .jar
RUN gradle build --no-daemon

# Etapa 2: Runtime
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copia o .jar gerado na etapa de build para o diretório de trabalho do novo contêiner
COPY --from=build /app/build/libs/*.jar app.jar
# Expõe a porta que a aplicação irá usar (por exemplo, 8080)
EXPOSE 8080
# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]