# Prueba técnica - Spring Boot

Este repositorio contiene tres microservicios desarrollados con **Spring Boot** que simulan el proceso de un carrito de compra:

- **ms-products**: Gestiona los productos disponibles en la tienda.
- **ms-orders**: Maneja las órdenes de compra.
- **ms-payments**: Procesa los pagos de las órdenes.

Cada microservicio está configurado para registrarse en **Eureka Service Discovery** y utilizar **Spring Cloud Config Server** para la configuración centralizada.

## 🚀 Requisitos Previos

Requisitos de ejecución

- **Java 17** o superior
- **Maven 3.x**

## 📂 Estructura del Proyecto

```
├── api-gateway
│   ├── src
│   ├── pom.xml
│   ├── application.yml
│
├── config
│   ├── src
│   ├── pom.xml
│   ├── application.yml
│
├── eureka
│   ├── src
│   ├── pom.xml
│   ├── application.yml
├── orders-service
│   ├── src
│   ├── pom.xml
│   ├── application.yml
├── payment-service
│   ├── src
│   ├── pom.xml
│   ├── application.yml
├── postman
│   ├── src
│   ├── pom.xml
│   ├── application.yml
├── products-service
│   ├── src
│   ├── pom.xml
│   ├── application.yml
├── pom.xml
└── README.md
```

## 🛠 Configuración de los Microservicios

Cada microservicio tiene su propia configuración que se establecer por medio del config server y está registrado en **Eureka**.

Ejemplo de configuración de `ms-payments.yml`:

```yaml
server:
  port: 8082

spring:
  application:
    name: ms-payments
  config:
    import: optional:configserver:http://localhost:8888

  datasource:
    url: jdbc:h2:mem:testdb
    driverClassName: org.h2.Driver
    username: sa
    password:

  jpa:
    hibernate:
      ddl-auto: create
    database-platform: org.hibernate.dialect.H2Dialect

  h2:
    console:
      enabled: true
      path: /h2-console

eureka:
  instance:
    hostname: localhost
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

## 🚀 Orden de ejecución de los Microservicios

- **config**
- **eureka**
- **products-service**
- **orders-service**
- **payment-service**
- **api-gateway**


## 📡 Endpoints

### 🛒 **Productos** (`ms-products`)
- `GET /api/products` - Obtener todos los productos
- `GET /api/products/{id}` - Obtener un producto por ID

### 📦 **Órdenes** (`ms-orders`)
- `GET /api/orders` - Obtener todas las órdenes
- `GET /api/orders/{id}` - Obtener detalles de una orden
- `POST /api/orders` - Crear una nueva orden
- `PATCH /api/orders/{id}/items` - Agregar un ítem a la orden
- `PATCH /api/orders/{id}/cancel` - Cancelar una orden
- `DELETE /api/orders/{id}/items/product/{productId}` - Eliminar un ítem de una orden

### 💳 **Pagos** (`ms-payments`)
- `POST /api/payments` - Procesar un pago



## 🔥 Consola Eureka
```
http://localhost:8761/
```

## 📜 Notas Finales
- En la carpeta Postman en la raiz del proyecto, se encuentra la colección de **Postman** para probar los endpoints.


---
**Autor:** Pablo Tezó  
📅 9 de febrero de 2025