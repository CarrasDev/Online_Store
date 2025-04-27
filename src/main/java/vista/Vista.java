package vista;

import controlador.Controlador;
import java.util.Scanner;

public class Vista {
    Scanner scanner;
    Controlador controlador;

    // Constructor
    public Vista() {
        this.scanner = new Scanner(System.in);
    }

    // Actualiza el controlador y comienza el menú principal
    public void startVista(Controlador controlador) {
        this.controlador = controlador;
        menuPrincipal();
    }

    // Metodo genérico para mostrar por consola
    public void updateView(String string) {
        System.out.println(string);
    }

    // Muestra el nivel más alto de jerarquía del menú y controla el bucle.
    private void menuPrincipal() {
        int opcion;
        do {
            System.out.println("\nMenú Principal");
            System.out.println("1. Gestión de Artículos");
            System.out.println("2. Gestión de Clientes");
            System.out.println("3. Gestión de Pedidos");
            System.out.println("0. Salir");
            // Se valida que la opción ingresada sea un entero
            opcion = leerEntero("Seleccione una opción: ");
            // Conversión del switch tradicional a switch con reglas (Java 14+)
            switch (opcion) {
                case 1 -> menuGestionArticulos();
                case 2 -> menuGestionClientes();
                case 3 -> menuGestionPedidos();
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    // GESTIÓN DE ARTÍCULOS
    // Menú de opciones para la gestión de artículos
    private void menuGestionArticulos() {
        int opcion;
        do {
            System.out.println("\nGestión de Artículos");
            System.out.println("1. Añadir Artículos");
            System.out.println("2. Mostrar Artículos");
            System.out.println("0. Volver");
            // Validación de la opción ingresada
            opcion = leerEntero("Seleccione una opción: ");
            // Conversión del switch tradicional a switch con reglas
            switch (opcion) {
                case 1 -> anyadirArticulo();
                case 2 -> mostrarArticulos();
                case 0 -> {} // No hacer nada y salir del menú
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    // E es el tipo genérico de la lista, los objetos de la lista deben tener metodo toString().
    // Pide al controlador actualizar la vista con la lista de artículos
    private void mostrarArticulos() {
        controlador.mostrarArticulos();
    }

    // Inicia el proceso de alta de nuevo artículo
    private void anyadirArticulo() {
        // Se lee y valida que el código del artículo no esté vacío
        String codigoArticulo = leerCadena("Introduzca el código del articulo: ");
        controlador.nuevoCodigoArticulo(codigoArticulo);
    }

    // Pide el resto de datos al usuario
    public void pedirDatosArticulo(String codigoArticulo) {
        String descripcion;
        float precioVenta;
        float gastosEnvio;
        int tiempoPreparacion;

        // Se valida que la descripción no esté vacía
        descripcion = leerCadena("Introduzca la descripcion del articulo: ");

        // Se valida que el precio de venta sea un número positivo
        while (true) {
            precioVenta = leerFloat("Introduzca el precio de venta: ");
            if (precioVenta > 0) {
                break;
            } else {
                System.out.println("Error: el precio de venta debe ser mayor que 0.");
            }
        }

        // Se valida que los gastos de envío no sean negativos (se permite 0)
        while (true) {
            gastosEnvio = leerFloat("Introduzca los gastos de envio: ");
            if (gastosEnvio >= 0) {
                break;
            } else {
                System.out.println("Error: los gastos de envío no pueden ser negativos.");
            }
        }

        // Se valida que el tiempo de preparación no sea negativo
        while (true) {
            tiempoPreparacion = leerEntero("Introduzca el tiempo de preparacion: ");
            if (tiempoPreparacion >= 0) {
                break;
            } else {
                System.out.println("Error: el tiempo de preparación no puede ser negativo.");
            }
        }
        controlador.addArticulo(codigoArticulo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion);
    }

    // GESTIÓN DE CLIENTES
    private void menuGestionClientes() {
        int opcion;
        do {
            System.out.println("\nGestión de Clientes");
            System.out.println("1. Añadir Clientes");
            System.out.println("2. Mostrar Clientes");
            System.out.println("0. Volver");
            // Se valida que se ingrese un entero
            opcion = leerEntero("Seleccione una opción: ");
            // Conversión del switch tradicional a switch con reglas
            switch (opcion) {
                case 1 -> anyadirCliente();
                case 2 -> menuMostrarClientes();
                case 0 -> {}
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private void anyadirCliente() {
        // Se lee y valida que el email no esté vacío
        String emailCliente = leerCadena("Introduzca el e-mail del cliente: ");
        // Validación simple del formato del email (debe contener "@" y ".")
        while (!validarEmail(emailCliente)) {
            System.out.println("Error: el formato del email es inválido. por ejemplo : usuario@abx.com");
            emailCliente = leerCadena("Introduzca el e-mail del cliente: ");
        }
        controlador.esClienteNuevo(emailCliente);
    }

    public void pedirDatosCliente(String emailCliente) {
        // Long id;
        String nombre;
        String domicilio;
        String nif;
        int tipoCliente; //(1) Premium (2) Estandar

        // Se leen las cadenas y se valida que no estén vacías
        nombre = leerCadena("Introduzca el nombre del cliente: ");
        domicilio = leerCadena("Introduzca el domicilio del cliente: ");
        nif = leerCadena("Introduzca el nif del cliente: ");

        // Se valida que el tipo de cliente sea 1 o 2
        while (true) {
            tipoCliente = leerEntero("Elija el tipo de cliente: (1)Premium (2) Estandar : ");
            if (tipoCliente == 1 || tipoCliente == 2) {
                break;
            } else {
                System.out.println("Error: opción inválida. Seleccione 1 para Premium o 2 para Estandar.");
            }
        }

        controlador.addCliente(nombre, domicilio, nif, emailCliente, tipoCliente);
    }

    private void menuMostrarClientes() {
        int opcion;
        do {
            System.out.println("\nGestión de Clientes > Mostrar Clientes");
            System.out.println("1. Mostrar todos los clientes");
            System.out.println("2. Mostrar clientes Estandar");
            System.out.println("3. Mostrar clientes Premium");
            System.out.println("0. Volver");
            // Validación de la opción ingresada
            opcion = leerEntero("Seleccione una opción: ");
            // Conversión del switch tradicional a switch con reglas
            switch (opcion) {
                case 1 -> controlador.mostrarClientes();
                case 2 -> controlador.mostrarClientesEstandar();
                case 3 -> controlador.mostrarClientesPremium();
                case 0 -> {}
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    // GESTIÓN DE PEDIDOS
    private void menuGestionPedidos() {
        int opcion;
        do {
            System.out.println("\nGestión de Pedidos");
            System.out.println("1. Añadir Pedido");
            System.out.println("2. Eliminar Pedido");
            System.out.println("3. Mostrar Pedidos Pendientes");
            System.out.println("4. Mostrar Pedidos Enviados");
            System.out.println("0. Volver");
            // Se valida que se ingrese un entero
            opcion = leerEntero("Seleccione una opción: ");
            // Conversión del switch tradicional a switch con reglas
            switch (opcion) {
                case 1 -> anyadirPedido();
                case 2 -> eliminarPedido();
                case 3 -> mostrarPedidosPendientes();
                case 4 -> mostrarPedidosEnviados();
                case 0 -> {}
                default -> System.out.println("Opción no válida. Intenta de nuevo.");
            }
        } while (opcion != 0);
    }

    private void anyadirPedido() {
        String codigoArticulo;
        int cantidad;
        String emailCliente;

        // Se valida que el código del artículo no esté vacío
        codigoArticulo = leerCadena("Introduzca el código del artículo: ");

        // Se valida que la cantidad sea mayor que 0
        while (true) {
            cantidad = leerEntero("Introduzca la cantidad: ");
            if (cantidad > 0) {
                break;
            } else {
                System.out.println("Error: la cantidad ha de ser mayor que 0");
            }
        }

        // Se lee y valida el email del cliente
        emailCliente = leerCadena("Introduzca el e-mail del cliente: ");
        while (!validarEmail(emailCliente)) {
            System.out.println("Error: el formato del email es inválido. por ejemplo usuario@abx.com");
            emailCliente = leerCadena("Introduzca el e-mail del cliente: ");
        }
        controlador.esClienteNuevo(emailCliente);
        controlador.addPedido(codigoArticulo, cantidad, emailCliente);
    }

    private void eliminarPedido() {
        // Se valida que el número de pedido sea un entero positivo
        Integer numeroPedido = leerEntero("Introduzca el número del pedido: ");
        if (numeroPedido <= 0) {
            System.out.println("Error: el número del pedido debe ser mayor que 0.");
            return;
        }
        controlador.removePedido(numeroPedido);
    }

    private void mostrarPedidosPendientes() {
        String opcion;
        opcion = leerCadena("Si quieres filtrar por cliente escriba el correo electronico, si quiere ver todos los pedidos pendientes escriba T: ");
        // Se valida que la opción ingresada sea 'T' o un email válido
        if (!opcion.equalsIgnoreCase("T") && !validarEmail(opcion)) {
            System.out.println("Error: la entrada no es válida. Ha de ser 'T' o un correo electrónico válido. Se mostrarán todos los pedidos pendientes.");
            opcion = "T";
        }
        controlador.mostrarPedidosPendientes(opcion);
    }
    private void mostrarPedidosEnviados() {
        String opcion;
        do {
            opcion = leerCadena(
                    "Si quieres filtrar por cliente escribe el correo electrónico,\n" +
                            "si quieres ver todos los pedidos enviados escribe T: "
            );
            if (opcion.equalsIgnoreCase("T") || validarEmail(opcion)) {
                break;  // entrada válida
            }
            System.out.println(
                    "Error: la entrada no es válida. Ha de ser 'T'\n" +
                            "o un correo electrónico válido. Inténtalo de nuevo."
            );
        } while (true);
        controlador.mostrarPedidosEnviados(opcion);
    }

    // MÉTODOS AUXILIARES DE VALIDACIÓN

    /**
     * Lee y valida la entrada de un número entero.
     * Se repite la solicitud hasta que se ingresa un entero válido.
     * @param mensaje Mensaje a mostrar para solicitar la entrada.
     * @return El entero leído.
     */
    private int leerEntero(String mensaje) {
        int numero;
        while (true) {
            System.out.print(mensaje);
            if (scanner.hasNextInt()) {
                numero = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                return numero;
            } else {
                System.out.println("Error: debe ingresar un número entero.");
                scanner.nextLine(); // Consumir entrada inválida
            }
        }
    }

    /**
     * Lee y valida la entrada de un número flotante.
     * Se repite la solicitud hasta que se ingresa un flotante válido.
     * @param mensaje Mensaje a mostrar para solicitar la entrada.
     * @return El número flotante leído.
     */
    private float leerFloat(String mensaje) {
        float numero;
        while (true) {
            System.out.print(mensaje);
            if (scanner.hasNextFloat()) {
                numero = scanner.nextFloat();
                scanner.nextLine(); // Limpiar buffer
                return numero;
            } else {
                System.out.println("Error: Se ha ingresar un número decimal.");
                scanner.nextLine(); // Consumir entrada inválida
            }
        }
    }

    /**
     * Lee y valida la entrada de un número largo.
     * Se repite la solicitud hasta que se ingresa un número largo válido.
     * @param mensaje Mensaje a mostrar para solicitar la entrada.
     * @return El número largo leído.
     */
    private long leerLong(String mensaje) {
        long numero;
        while (true) {
            System.out.print(mensaje);
            if (scanner.hasNextLong()) {
                numero = scanner.nextLong();
                scanner.nextLine(); // Limpiar buffer
                return numero;
            } else {
                System.out.println("Error: Se ha ingresar un número entero largo.");
                scanner.nextLine(); // Consumir entrada inválida
            }
        }
    }

    /**
     * Lee y valida que la cadena ingresada no esté vacía.
     * Se repite la solicitud hasta que se ingresa una cadena no vacía.
     * @param mensaje Mensaje a mostrar para solicitar la entrada.
     * @return La cadena no vacía leída.
     */
    private String leerCadena(String mensaje) {
        String cadena;
        while (true) {
            System.out.print(mensaje);
            cadena = scanner.nextLine().trim();
            if (!cadena.isEmpty()) {
                return cadena;
            } else {
                System.out.println("Error: la entrada no puede estar vacía.");
            }
        }
    }

    /**
     * Realiza una validación simple del formato de un correo electrónico.
     * Verifica que la cadena contenga "@" y ".".
     * @param email La cadena a validar.
     * @return true si el correo tiene un formato básico válido, false en caso contrario.
     */
    private boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.contains("@") && email.contains(".");
    }
}
