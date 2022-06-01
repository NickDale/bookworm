# BookWorm - könyvtár backend

### Project tulajdonságok
```
+ Spring boot 2.5.5
+ Java 11
+ maven
```

### Local environment

Docker-ben is el lehet indítani, csak kell egy docker host a gépedre.
https://docs.docker.com/get-docker/

Indítása a `compose.bat` program segítségével a következő paraméterekkel:
* `start` buildel és indítja a konténert
* `stop` leállítja a konténert
 
A docker az adatbázist a `localhost:3307`-es az applikációt a `localhost:8099`-es porton indítja el.

Ez mellett természetesen el lehet indítani magát az alkalmazást is, amit alapértelmezetten a `localhost:9999`-es porton fog futni.

Bár a szerver portja bármikor kedvünk szerint módosítható 
`src/main/resources/application.properties ` fájlban `server.port=9999` átírásával


### Swagger

A teljes végpontlista az applikáció indításást követően a következő url-en érhető el.


```
localhost:9999/library/api/swagger-ui/index.html

vagy docker esetén

http://localhost:8099/library/api/swagger-ui/index.html
```