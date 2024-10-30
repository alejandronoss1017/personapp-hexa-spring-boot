# personapp-hexa-spring-boot

## Stack Tecnológico
- JDK 11
- Spring Boot
- MongoDB y MariaDB
- REST y CLI
- Swagger 3

## Ejecución de la solución con Docker Compose

Para ejecutar la solución completa utilizando Docker Compose, sigue estos pasos:

1. Instalar JDK 11 para compilar el proyecto.

2. Clona el repositorio:
    ```sh
    git clone https://github.com/alejandronoss1017/personapp-hexa-spring-boot.git
    cd personapp-hexa-spring-boot
    ```
3. Construye y levanta los contenedores:
    ```sh
    docker-compose up --build
    ```
4. Ejecutar PersonAppRestAPi
5. Ejecutar PersonAppCli
6. Puede consultar el swagger en http://localhost:3000/swagger-ui.html
7. Para detener los contenedores:
    ```sh
    docker-compose down
    ```
## Autores

- [@alejandronoss1017](https://github.com/alejandronoss1017)
- [@carlosantiagorojas](https://github.com/carlosantiagorojas)
- [@StiivenOrtiz](https://github.com/StiivenOrtiz)
