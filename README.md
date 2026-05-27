# Spring Boot : Application PetClinic

## Features

### 1. Unit Test  --> Tag v1.0.0

mvn test -Dspring.profiles.active=h2

## Run project

Use Maven Wrapper on Windows:

```powershell
cd C:\Users\Mauricio\Desktop\tecsupp\PETCLINIC10\petclinic_test
.\mvnw.cmd spring-boot:run
```

Default profile is `h2`.

## Generate MySQL schema + seed data (scripts in `data/`)

The project includes:

- `data/schema-mysql.sql`
- `data/data-mysql.sql`

Run both scripts with:

```powershell
cd C:\Users\Mauricio\Desktop\tecsupp\PETCLINIC10\petclinic_test
.\data\load-mysql.ps1 -User root -Password "YOUR_PASSWORD"
```

Optional parameters:

```powershell
.\data\load-mysql.ps1 -User root -Password "YOUR_PASSWORD" -DbHost localhost -Port 3306
```

After loading MySQL data, run app with mysql profile:

```powershell
set DB_PASSWORD=YOUR_PASSWORD
.\mvnw.cmd "-Dspring.profiles.active=mysql" spring-boot:run
```

## Run only `PetServiceTest`

In PowerShell, quote `-Dtest` value:

```powershell
.\mvnw.cmd "-Dtest=com.tecsup.petclinic.services.PetServiceTest" clean test
```

## Informe de lo agregado

### 1. Clases agregadas para `TypeService`

Se implementó una capa completa para el manejo de tipos de mascota:

- `src/main/java/com/tecsup/petclinic/entities/Type.java`
  - Entidad JPA para la tabla `types`.
- `src/main/java/com/tecsup/petclinic/dtos/TypeDTO.java`
  - DTO para transportar datos del tipo.
- `src/main/java/com/tecsup/petclinic/mappers/TypeMapper.java`
  - Mapper (MapStruct) entre `Type` y `TypeDTO`.
- `src/main/java/com/tecsup/petclinic/repositories/TypeRepository.java`
  - Repositorio JPA para `Type`.
  - Incluye query de reporte `getPetCountByType()` filtrando tipos activos (`active = true`).
- `src/main/java/com/tecsup/petclinic/services/TypeService.java`
  - Contrato del servicio (`create`, `update`, `findById`, `delete`, `getPetCountByType`).
- `src/main/java/com/tecsup/petclinic/services/TypeServiceImpl.java`
  - Implementación del servicio con lógica CRUD, borrado y reporte.
- `src/main/java/com/tecsup/petclinic/exceptions/TypeNotFoundException.java`
  - Excepción para registros no encontrados.
- `src/main/java/com/tecsup/petclinic/dtos/PetCountByTypeDTO.java`
  - DTO del reporte de cantidad de mascotas por tipo.

### 2. Pruebas Mockito agregadas

Archivo:

- `src/test/java/com/tecsup/petclinic/services/TypeServiceTest.java`

Se implementaron pruebas unitarias con Mockito para los tres integrantes:

- Integrante A — CRUD básico
  - `testCreateType`
  - `testUpdateType`
  - `testFindTypeById`
- Integrante B — Reporte de mascotas por tipo
  - `testGetPetCountByType`
  - `testGetPetCountByType_Empty`
  - `testDeleteType`
- Integrante C — Filtros y errores
  - `testGetPetCountByType_ExcludeInactive`
  - `testFindTypeById_NotFound`
  - `testDeleteType_NotFound`

### 3. Scripts y soporte de BD agregados

- `data/load-mysql.ps1`
  - Script para importar `schema-mysql.sql` y `data-mysql.sql` en MySQL.
- `data/schema-mysql.sql`
  - Script de creación de esquema.
- `data/data-mysql.sql`
  - Script de datos de ejemplo.

### 4. Comandos útiles para ejecutar pruebas Mockito de `TypeService`

Ejecutar todos los tests de `TypeServiceTest`:

```powershell
.\mvnw.cmd "-Dtest=com.tecsup.petclinic.services.TypeServiceTest" clean test
```

Ejecutar solo Integrante A:

```powershell
.\mvnw.cmd "-Dtest=com.tecsup.petclinic.services.TypeServiceTest#testCreateType+testUpdateType+testFindTypeById" clean test
```

Ejecutar solo Integrante B:

```powershell
.\mvnw.cmd "-Dtest=com.tecsup.petclinic.services.TypeServiceTest#testGetPetCountByType+testGetPetCountByType_Empty+testDeleteType" clean test
```

Ejecutar solo Integrante C:

```powershell
.\mvnw.cmd "-Dtest=com.tecsup.petclinic.services.TypeServiceTest#testGetPetCountByType_ExcludeInactive+testFindTypeById_NotFound+testDeleteType_NotFound" clean test
```
