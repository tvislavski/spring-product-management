# spring-product-management

## Building the application

- Position yourself in the root directory of this repository
- Make sure that your current user has permissions to run the `mvnw` process (or `mvnw.cmd` on Windows)
- Run `./mvnw clean install` (or `mvnw.cmd clean install`) to build the application

## Running the application

- On Unix run `./mvnw spring-boot:run`
- On Windows run `mvnw.cmd spring-boot:run`
- REST endpoints are available on `http://localhost:8080`
- Swagger documentation is available on `http://localhost:8080/swagger-ui.html`

## Database

Application uses a lightweight file-based database [JsonDB](http://jsondb.io), there is no need to install anything on your system as a prerequisite.
