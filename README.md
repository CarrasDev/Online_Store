# SQLSquad Online Store

Proyecto de tienda online desarrollado en Java utilizando JavaFX para la interfaz gráfica y Hibernate para la persistencia de datos en MySQL.<br>
Este proyecto se llevó a cabo como parte del ciclo formativo de grado superior de Desarrollo de Aplicaciones Multiplataforma (DAM) en JE-UOC. Como se puede observar existen multiples ramas en el repositorio, cada una de ellas corresponde a una entrega del proyecto.<br>
El proyecto está estructurado siguiendo el patrón de arquitectura MVC (Modelo-Vista-Controlador) y emplea el patrón DAO (Data Access Object) para la gestión de la persistencia de datos. Para lograr la aplicación final con interfaz gráfica pasamos por diferentes fases del desarrollo donde comprendimos la importancia de utilizar patrones de diseño y buenas prácticas de programación.

## Descripción

Esta aplicación permite la gestión de artículos, clientes y pedidos en una tienda online. Incluye funcionalidades para añadir, visualizar y eliminar registros, así como filtrado y validación de datos. El proyecto sigue una arquitectura MVC y utiliza patrones DAO para el acceso a datos.
- **El producto 1** (No se muestra en este repositorio) consistió en la fase de análisis y diseño del proyecto, donde se definieron los requisitos, casos de uso y diagramas UML (clases, casos de uso y secuencia). Esta fase fue crucial para sentar las bases del desarrollo posterior.
- **El producto 2** consistió en la codificación de las clases de modelo con almacenamiento en listas y sin persistencia. Todo ello con una interfaz de consola y una arquitectura MVC básica.
- **El producto 3** se centró en la implementación de la persistencia de datos utilizando JDBC y MySQL, permitiendo la gestión de artículos, clientes y pedidos de forma persistente sobre una base de datos que creamos para el proyecto. En esta fase se mejoró la estructura del proyecto y se añadieron validaciones básicas para evitar datos erróneos, así como ataques de inyección SQL.
- **El producto 4** consistió en implementar la persistencia mediante ORM (Hibernate), mejorando la gestión de las entidades y sus relaciones. Para ello, mapeamos nuestras clases de modelo y reicimos los DAOs para trabajar con Hibernate.
- **El producto 4.1** fue una fase de refactorización y mejora del modelo MVC, ya que no se habia implementado correctamente en las fases anteriores. Se crearon controladores específicos para cada entidad (artículos, clientes y pedidos) y se mejoró la organización del código.
- **El producto 5** fue la fase final donde se desarrolló una interfaz gráfica utilizando JavaFX y FXML. Se implementaron controladores específicos para cada vista y se mejoró la experiencia de usuario con una navegación dinámica entre las diferentes secciones de la aplicación.<br>
Este proceso de desarrollo por fases nos permitió aprender y aplicar conceptos fundamentales de desarrollo de software, desde la programación básica hasta la creación de aplicaciones con interfaces gráficas y persistencia avanzada. Viendo la importancia de separa la lógica de negocio, la presentación y el acceso a datos para lograr un código más mantenible y escalable.

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

## Generar la base de datos automáticamente

Es posible crear todas las tablas necesarias desde cero modificando la configuración de Hibernate. Para ello:

1. Abre el archivo `src/main/resources/hibernate.cfg.xml`.
2. Busca la propiedad:
   ```xml
   <property name="hibernate.hbm2ddl.auto">update</property>
   ```
3. Cambia el valor de `update` a `create` o `create-drop`:
   - `create`: Crea todas las tablas al iniciar la aplicación (elimina las existentes).
   - `create-drop`: Igual que `create`, pero elimina las tablas al cerrar la sesión de Hibernate.

   Ejemplo:
   ```xml
   <property name="hibernate.hbm2ddl.auto">create</property>
   ```

4. Guarda los cambios y ejecuta la aplicación. La base de datos y las tablas se generarán automáticamente según las entidades del proyecto.

> **Nota:** Usa esta opción solo en entornos de desarrollo, ya que se perderán los datos existentes cada vez que se reinicie la aplicación.

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

## Video de demostración

Dejo un video de demostración de gran parte de las funcionalidades de la aplicación:<br>
Se in

[![Ver video de demostración](https://img.youtube.com/vi/KmxfsbXsp5Y/0.jpg)](https://www.youtube.com/watch?v=KmxfsbXsp5Y)


## Errores cometidos y lecciones aprendidas

- Una vez terminado el proyecto, me di cuenta de que estabamos exponiendo nuestra contraseña de la base de datos en el archivo `hibernate.cfg.xml`. Para evitar esto, es recomendable utilizar variables de entorno o un archivo de configuración externo que no se incluya en el control de versiones. Esto mejora la seguridad y facilita el cambio de credenciales sin modificar el código fuente. **Para futuras ocasiones, implementaré esta práctica desde el inicio del proyecto.** - CarrasDev

## Créditos

Proyecto creado por::

- Vanesa Sierra ([SierraTrace](https://github.com/sierratrace))
- Daniel Carrasco ([CarrasDev](https://github.com/CarrasDev))

---

> Para cualquier duda o sugerencia, contacta con nosotros o abre un issue en el repositorio.

¡¡Gracias por visitar nuestro proyecto!! 😊