# Transaction Service

## Descripción General

El **Transaction Service** es un microservicio reactivo desarrollado con Spring Boot encargado de gestionar las transacciones bancarias dentro del Sistema de Microservicios Bancarios.

Proporciona APIs REST para crear, actualizar, consultar y eliminar transacciones, además de permitir búsquedas por cliente, cuenta bancaria, rango de fechas y obtener las últimas transacciones registradas.

---

## Tecnologías Utilizadas

* Java 17
* Spring Boot 3
* Spring WebFlux
* Spring Data Reactive MongoDB
* RxJava 3
* MongoDB
* Redis
* Apache Kafka
* Spring Cloud Config
* Netflix Eureka Server
* Spring Cloud Gateway
* Resilience4j (Circuit Breaker)
* Docker
* JUnit 5
* Mockito
* JaCoCo
* Checkstyle

---

## Arquitectura

Este microservicio forma parte de una arquitectura basada en microservicios e interactúa con los siguientes componentes:

* Config Server
* Eureka Server
* API Gateway
* MongoDB
* Redis
* Apache Kafka

---

## Funcionalidades

* Registrar una transacción.
* Consultar todas las transacciones.
* Consultar transacciones por cliente.
* Actualizar una transacción.
* Eliminar una transacción.
* Consultar transacciones por rango de fechas.
* Obtener las últimas 10 transacciones de una cuenta.
* Tolerancia a fallos mediante Resilience4j Circuit Breaker.

---

## Endpoints Disponibles

### Crear Transacción

**POST**

```text
/api/transactions
```

---

### Obtener Todas las Transacciones

**GET**

```text
/api/transactions
```

---

### Obtener Transacciones por Cliente

**GET**

```text
/api/transactions/customer/{customerId}
```

---

### Actualizar Transacción

**PUT**

```text
/api/transactions/{id}
```

---

### Eliminar Transacción

**DELETE**

```text
/api/transactions/{id}
```

---

### Obtener Transacciones por Rango de Fechas

**GET**

```text
/api/transactions/account/{accountId}/range?from={from}&to={to}
```

Ejemplo:

```text
/api/transactions/account/ACC001/range?from=2026-07-01T00:00:00&to=2026-07-31T23:59:59
```

---

### Obtener las Últimas 10 Transacciones

**GET**

```text
/api/transactions/account/{accountId}/last10
```

---

## Ejecución del Proyecto

### Compilación

```bash
mvn clean install
```

### Ejecución Local

```bash
mvn spring-boot:run
```

Si se ejecuta de manera independiente, el servicio estará disponible según el puerto configurado en el archivo de propiedades.

---

## Ejecución con Docker

El proyecto cuenta con un archivo **docker-compose.yml** ubicado en el repositorio de infraestructura.

Para iniciar todo el ecosistema de microservicios:

```bash
docker compose up --build
```

Este comando levantará:

* Config Server
* Eureka Server
* API Gateway
* Customer Service
* Account Service
* Credit Service
* Transaction Service

Una vez iniciados los servicios, las peticiones deben realizarse a través del API Gateway:

```text
http://localhost:8080/api/transactions
```

Si se presenta dificultades al iniciar con docker, puede abrir los microservicios con Intellig IDEA y lanzar en el siguiente orden:
-Config Server, Eureka Server, API Gateway, y los demás microservicios.
---

## Pruebas Unitarias

Ejecutar las pruebas:

```bash
mvn test
```

Generar reporte de cobertura:

```bash
mvn clean verify
```

El reporte de JaCoCo se genera en:

```text
target/site/jacoco/index.html
```

---

## Colección Postman

El repositorio incluye una colección de Postman con todos los endpoints disponibles para facilitar las pruebas y validaciones funcionales.


---

## Calidad de Código

El proyecto incorpora las siguientes herramientas de calidad:

* Checkstyle
* JaCoCo
* JUnit 5
* Mockito

---

## Consideraciones

* El servicio utiliza programación reactiva mediante Spring WebFlux y RxJava.
* El registro y consulta de información se realiza sobre MongoDB.
* Se implementan mecanismos de tolerancia a fallos mediante Circuit Breaker de Resilience4j.
* El descubrimiento de servicios se realiza mediante Eureka Server.
* La configuración centralizada se obtiene desde Config Server.

---

## Autor
Wilmer Alonso Romero Ampuero
