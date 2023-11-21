`WORK IN PROGRES ;))`
# ✨SciJourDex✨
> Science Journal Dex

> This is a REST API built on a Java-based Spring backend, coupled with PostgreSQL as the database. It is designed for the efficient filtering and management of scientific journals using data from the Polish Ministry of Education [^1]. The API offers file upload capabilities for physical storage, with metadata carefully managed within the database. Additionally, users can manage tags associated with the scientific journals. In the future, a web scraper will be added to include additional tags.

[^1]: https://www.gov.pl/web/edukacja-i-nauka/nowy-rozszerzony-wykaz-czasopism-naukowych-i-recenzowanych-materialow-z-konferencji-miedzynarodowych

## Table of Contents
- [Requirements](#requirements)
- [Installation](#installation)
- [Configuration](#configuration)
- [API Documentation](#api-documentation)

## Requirements 

`TODO ;))`

## Installation
```
docker pull ...
```

`TODO ;))`

## Configuration

`TODO ;))`

## API Documentation

`TODO ;))`

### FOR ME 

`DONT LOOK HERE ;))`

- Start working with postgres container
```
docker pull postgres:15.4
```

```
docker run --name postgres-15.4 -e POSTGRES_PASSWORD="Ala_Ma_K0ta" -p 5433:5432 -d postgres:15.4
```

```
docker stop postgres-15.4
```

```
docker start postgres-15.4
```

- Open command line within postgres

```
docker exec -it postgres-15.4 psql -U postgres
```

- Open command line & create database

```
docker exec -it postgres-15.4 psql -U postgres -c "CREATE DATABASE app;"
```
