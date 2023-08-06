### *For me
- To start db use:
```
docker start mssql-2022
```

- To exec db use:
```
docker exec -d mssql-2022 /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Ala_Ma_K0ta
```

- To correctly turn off db:
```
docker exec -it mssql-2022 /bin/bash -c '/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Ala_Ma_K0ta -Q "SHUTDOWN;"'
```
