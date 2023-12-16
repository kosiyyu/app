`WORK IN PROGRES ;))`
# ✨SciJourDex✨
> Science Journal Dex

> This is a REST API built on a Java-based Spring backend, coupled with PostgreSQL as the database. It is designed for the efficient filtering and management of scientific journals using data from the Polish Ministry of Education [^1]. The API offers file upload capabilities for physical storage, with metadata carefully managed within the database. Additionally, users can manage tags associated with the scientific journals. In the future, a web scraper will be added to include additional tags.

[^1]: https://www.gov.pl/web/edukacja-i-nauka/nowy-rozszerzony-wykaz-czasopism-naukowych-i-recenzowanych-materialow-z-konferencji-miedzynarodowych

## Table of Contents
- [Requirements](#requirements)
- [Quick Start](#quick-start)
- [Start Application](#start-application)
- [Stop Application](#stop-application)
- [Remove Application](#remove-application)
- [Configuration](#configuration)
- [API Documentation](#api-documentation)

## Requirements 

- Docker
- Network ports:
  - 8081: API
  - 5432: Database

## Quick Start
To start your Dockerized application for the first time:
```
docker-compose build
docker-compose up -d
```

## Start Application
To start your Dockerized application:
```
docker-compose start
```

## Stop Application
To stop your Dockerized application:
```
docker-compose stop
```

## Remove Application
To remove your Dockerized application :
```
docker-compose down -v
```

## Configuration

`TODO ;))`

## API Documentation

### Tag Controller
- **PUT**
  - `/api/v1/tag/edit`: Edit an existing tag.

- **POST**
  - `/api/v1/tag/upload`: Upload a new tag.

- **GET**
  - `/api/v1/tags/download`: Download all tags.

- **GET**
  - `/api/v1/tag/download/{tagId}`: Download a specific tag by ID.

- **DELETE**
  - `/api/v1/tag/delete/{tagId}`: Delete a tag by ID.

### Journal Controller
- **PUT**
  - `/api/v1/journal/edit`: Edit an existing journal.

- **POST**
  - `/api/v1/journals/tokenized/download`: Download tokenized journals based on a search token. Include the search token in the request body.

- **POST**
  - `/api/v1/journal/bundle/upload/`: Upload a bundle of journals. Supports both JSON data and form-data. Include parameters:
    - `journal` (JSON): The journal data.
    - `file` (File): Optional. Attach a file if necessary.

- **GET**
  - `/api/v1/journal/download/{journalId}`: Download a specific journal by ID.

- **DELETE**
  - `/api/v1/journal/delete/{journalId}`: Delete a journal by ID.

### CSV Controller
- **POST**
  - `/api/v1/csv/upload`: Upload a CSV file.

### File Metadata Controller
- **GET**
  - `/api/v1/filemetadata/download/{metadataId}`: Download file by metadata ID.
