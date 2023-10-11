
# API fidelización clientes:
### Este proyecto pretende crear un backend para que la empresa UniSabana pueda manejar los puntos de sus clientes.

## Proyecto hecho con:
[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/what-is-corretto-17.html)
[![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://docs.spring.io/spring-framework/reference/index.html)
[![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)](https://dev.mysql.com/doc/)
[![Intellij IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)](https://www.jetbrains.com/idea/)

## Prerequisitos
 - Java JRE1.8 o JDK 17.
 - Navegador Web.

## Como correr
### 1. Haga clone de este proyecto en la carpeta deseada
```
gh repo clone juangomezru/puntosUnisabana
```
### 2. Muevase hasta la carpeta libs
```
./puntosUnisabana\build\libs
```
### 3. Abra una terminal en esta carpeta.

### 4. Corra el siguiente código.
```
java -jar .\puntosUnisabana-0.0.1-SNAPSHOT.jar
```
### 5. Por defecto la aplicación se iniciará en el puerto 8080, puede usar el postman en docs para probarla

## API

### La api y su documentación podrá ser revisada y probada con Swagger una vez corriendo el proyecto se ingrese a la siguiente dirección: http://localhost:8080/swagger-ui/index.html#/

#### Agregar Cliente

```http
  POST /cliente/agregar
```

| Parametro | Tipo     | Descripción                      |
|:----------|:---------|:---------------------------------|
| `cedula`  | `int`    | **Required**. Cedula del cliente |
| `nombre`  | `String` | **Required**. Nombre del cliente |
| `email`   | `String` | **Required**. Email del cliente  |

#### Buscar Cliente: Devuelve un cliente

```http
  GET /cliente/{cedulaCliente}
```

| Parametro | Tipo  | Descripción                                  |
|:----------|:------|:---------------------------------------------|
| `cedula`  | `int` | **Required**. Cedula de un cliente existente |

#### Afiliar cliente: Inscribirlo en los puntos

```http
  POST /cliente/afiliacion/{cedula}
```

| Parametro | tipo  | Description                                  |
|:----------|:------|:---------------------------------------------|
| `cedula`  | `int` | **Required**. Cedula de un cliente existente |

#### Realizar compra: Realizar una compra para acumular puntos

```http
  PUT /cliente/compra/puntos?cedulaCliente={cedula}&valorCompra={valorCompra}
```

| Parametro     | tipo  | Description                                  |
|:--------------|:------|:---------------------------------------------|
| `cedula`      | `int` | **Required**. Cedula de un cliente existente |
| `valorCompra` | `int` | **Required**. Valor de la compra realizada   |


#### Agregar beneficios: Agregar beneficios a redimir
```http
  POST /beneficio/agregar
```

| Parametro          | tipo     | Description                                    |
|:-------------------|:---------|:-----------------------------------------------|
| `nombreBeneficio`  | `String` | **Required**. Nombre del beneficio             |
| `puntosRequeridos` | `int`    | **Required**. Puntos requeridos para redimirlo |

#### Buscar los beneficios: Devuelve todos los beneficios existentes con su ID
```http
  GET /beneficios
```


#### Redimir beneficios: Agregar beneficios a un cliente
```http
  PUT /cliente/redimir?cedulaCliente={cedula}&idBeneficio={id}
```

| Parametro       | tipo  | Description                                  |
|:----------------|:------|:---------------------------------------------|
| `cedulaCliente` | `int` | **Required**. Cedula de un cliente existente |
| `id`            | `int` | **Required**. Id del beneficio a redimir     |


#### Buscar transacciones: Devuelve todas las transacciones hechas para redimir puntos de todos los clientes
```http
  GET /transacciones
```

#### Buscar transacciones: Devuelve todas las transacciones de un cliente
```http
  GET /transacciones/clientes/buscar?cedula={cedulaCliente}
```

| Parametro       | tipo  | Description                                  |
|:----------------|:------|:---------------------------------------------|
| `cedulaCliente` | `int` | **Required**. Cedula de un cliente existente |





## Diagramas

### Componentes
<img src = "\docs\Componentes.png">

### Paquetes
<img src = "\docs\Paquetes.png">

### ERD
<img src = "\docs\ERM.png">




