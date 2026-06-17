MS-Seguros

Microservicio encargado de la gestión de seguros asociados a las reservas de propiedades del sistema Home-Rent-Solution.

## Funcionalidades

* Registrar seguros
* Consultar seguros
* Actualizar seguros
* Eliminar seguros
* Buscar seguros por tipo
* Buscar seguros por reserva
* Consultar seguros mediante HATEOAS

Endpoints Principales

### Obtener todos los seguros

GET /api/v1/seguros

### Obtener seguro por ID

GET /api/v1/seguros/{id}

### Crear seguro

POST /api/v1/seguros

### Actualizar seguro

PUT /api/v1/seguros/{id}

### Eliminar seguro

DELETE /api/v1/seguros/{id}

### Buscar por tipo

GET /api/v1/seguros/tipo/{tipo}

### Buscar por reserva

GET /api/v1/seguros/reserva/{id}
-------------------------------------------
## HATEOAS

### Obtener todos los seguros con enlaces

GET /api/v2/seguros

### Obtener seguro por ID con enlaces

GET /api/v2/seguros/{id}
-------------------------------------------
## Integraciones

Este microservicio utiliza OpenFeign para comunicarse con:

- MS-Reservas
- MS-Pagos
--------------------------------------------------------------
## Tecnologías

* Java 25
* Spring Boot
* Spring Data JPA
* MySQL
* OpenAPI / Swagger
* OpenFeign
* HATEOAS
* Maven
