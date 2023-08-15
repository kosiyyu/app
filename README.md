
# Docs

## 1.TO DO

## 2. Database

### 2.1 Docker

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