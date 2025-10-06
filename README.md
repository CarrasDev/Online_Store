# SQLSquad Online Store

Proyecto de tienda online desarrollado en Java utilizando JavaFX para la interfaz gráfica y Hibernate para la persistencia de datos en MySQL.

## Descripción

Esta aplicación permite la gestión de artículos, clientes y pedidos en una tienda online. Incluye funcionalidades para añadir, visualizar y eliminar registros, así como filtrado y validación de datos. El proyecto sigue una arquitectura MVC y utiliza patrones DAO para el acceso a datos.

## Características principales

- **Gestión de Artículos:** Alta y listado de artículos con validación de campos.
- **Gestión de Clientes:** Alta y listado de clientes, diferenciando entre clientes estándar y premium.
- **Gestión de Pedidos:** Creación, listado y eliminación de pedidos, con actualización automática del estado según el tiempo de preparación.
- **Interfaz gráfica:** Desarrollada con JavaFX y FXML, con navegación dinámica entre vistas.
- **Persistencia:** Uso de Hibernate ORM y MySQL.
- **Validaciones:** Validación de emails, NIF/NIE y otros campos obligatorios.
- **Testing:** Pruebas unitarias con JUnit.

## Requisitos

- Java 22 o superior
- Maven 3.8+
- MySQL Server (con una base de datos llamada `onlinestore`)
- Acceso a internet para descargar dependencias

## Instalación

1. **Clona el repositorio:**
   ```sh
   git clone <URL-del-repositorio>
   cd SQLSquad
   ```

2. **Configura la base de datos:**
   - Crea una base de datos llamada `onlinestore` en tu servidor MySQL.
   - Ajusta el usuario y contraseña en `src/main/resources/hibernate.cfg.xml` si es necesario.

3. **Compila el proyecto:**
   ```sh
   ./mvnw clean install
   ```

4. **Ejecuta la aplicación:**
   ```sh
   ./mvnw javafx:run
   ```

## Estructura del proyecto

```
src/
  main/
    java/
      com/sqlsquad/onlinestore/
        MainAPP.java
        MainViewController.java
        controlador/
        DAO/
        modelo/
        util/
        view/
    resources/
      hibernate.cfg.xml
      com/sqlsquad/onlinestore/*.fxml
test/
  PedidoTest.java
```

- **controlador/**: Lógica de negocio y controladores MVC.
- **DAO/**: Acceso a datos (Hibernate).
- **modelo/**: Modelos de dominio y DTOs.
- **util/**: Utilidades generales (validación, servicios, Hibernate).
- **view/**: Controladores de vistas JavaFX.
- **resources/**: Archivos FXML y configuración de Hibernate.

## Uso

- Al iniciar, la aplicación muestra la vista de alta de artículos.
- Navega entre vistas usando el menú lateral.
- Añade, consulta y elimina artículos, clientes y pedidos.
- Los pedidos cambian automáticamente de estado según el tiempo de preparación.

## Pruebas

Para ejecutar los tests unitarios:

```sh
./mvnw test
```

Las pruebas principales están en [test/PedidoTest.java](test/PedidoTest.java).

## Créditos

Proyecto creado por el **Grupo 2 - SQL Squad_II**:

- Vanesa Sierra ([Sierra Trace](https://github.com/sierratrace))
- Daniel Carrasco ([CarrasDev](https://github.com/CarrasDev))

---

> Para cualquier duda o sugerencia, contacta con los autores o abre un issue en el repositorio.