# ITEMS ML

Aplicación para listar items provenientes de api de MercadoLibre

## Installación

Dentro de la carperta del proyecto ejecutar:

```bash
./gradlew clean build  
docker-compose up --build
```

El proyecto viene preparado para correr sobre un container de docker, incluye el docker compose para levantar los contenedores necesarios en el ambiente local

## Usage

La aplicación esta expuesta por defecto en el puerto 8080 y cuenta con dos apis públicas

```console
curl --location --request GET 'localhost:8080/items/{ItemId}'

curl --location --request GET 'localhost:8080/health'
```
## Stack
- Java 11
- Spring boot (singleton patter by default)
- Postgres DB

## Arquitectura

Se planteó un diseño de la arquitectura hexagonal, aislando el dominio de la 
capa de infraestructura.

## Aclaraciones

Se utilizó el stack recomendado en la descipción de la prueba, sin embargo considero 
que hay algunas mejores que pueden aplicar.

- La información de los items una vez se consulta fue almacenada una columna tipo Json en la BD.
- La información relacionada al health check se persisteen un mapa que vive en memoria, 
por lo que no es persistente entre diferentes instancias
- Las metricas del health fueron calculadas por la app, sin uso de ninguna librería, 
sin embargo no es la mejor forma de hacerlo (ver sugerencias)
- Algunos endpoints de actuator y Merics estan habilitados para tener mas visibilidad 
de la aplicación.

## Sugerencias

- Usar cache en vez de Bd: para el proposito indicado no es necesario contar con una bd relacional, menos aún cuando 
no se necesita persistir la data normalizada, sugerencia usar redis (o un noSql)
- Se implementó un POC de Metrics con Prometheus con un contador custom, para luego ser 
consumido por un api de monitoring como grafana (Ver controler)
- Aumentar el porcentaje de cobertura de test (Solo se hicieron test de servicio)
- Añadir CI/CD para el proyecto, de forma que nos brinde mas seguridad y facilidad 
al momento de llevar la app a producción. CircleCI es una buena opción.


## Linkedin
https://www.linkedin.com/in/jgmorah/