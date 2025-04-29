package test;

import modelo.Articulo;
import modelo.cliente.Cliente;
import modelo.cliente.ClienteEstandar;
import modelo.cliente.ClientePremium;
import modelo.Pedido;
import modelo.enums.TipoEstado;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PedidoTest{

    /////////////////////   TESTS DE ESTADO DE PREPARACION   /////////////////

    @Test
    void testActualizarEstadoPreparacionCumpleTiempo() {

        //Articulo con 10 min de preparacion
        Articulo articulo = new Articulo("A111", "Artículo de prueba", 100.0f, 5.0f, 10);
        ClienteEstandar cliente = new ClienteEstandar("Maria", "Su casa", "11111111A", "maria@email.com");

        //Pedido con fecha de hace 15 min
        LocalDateTime fechaPedido = LocalDateTime.now().minusMinutes(15);
        Pedido pedido = new Pedido(1, articulo, 2, cliente, LocalDateTime.now(), TipoEstado.PENDIENTE);
        pedido.setFechaPedido(fechaPedido); //Introduccion manual de la fecha en Pedido

        // Llamada al metodo que vamos a testear
        pedido.actualizarEstadoPreparacion();

        //Verificar cambio de estado a "ENVIADO"
        assertEquals(TipoEstado.ENVIADO, pedido.getEstado());
    }

    @Test
    void testActualizarEstadoPreparacionNoCumpleTiempo() {

        //Artículo con 20 min de preparacion
        Articulo articulo = new Articulo("A111", "Artículo de prueba", 100.0f, 5.0f, 20);
        ClienteEstandar cliente = new ClienteEstandar("Maria", "Su casa", "11111111A", "maria@email.com");

        //Pedido con fecha de hace 10 min
        LocalDateTime fechaPedido = LocalDateTime.now().minusMinutes(10);
        Pedido pedido = new Pedido(articulo, 2, cliente, LocalDateTime.now(), TipoEstado.PENDIENTE);
        pedido.setFechaPedido(fechaPedido); // Introduccion manual de la fecha en pedido

        // Llamada al metodo que vamos a testear
        pedido.actualizarEstadoPreparacion();

        //Verificar que el estado se queda como "PENDIENTE"
        assertEquals(TipoEstado.PENDIENTE, pedido.getEstado());
    }

    //////////////////  TESTS DE CALCULO TOTAL //////////////////


    @Test
    void testCalcularTotalConClientePremium() {

        // Cliente premium por con descuento del 20%
        ClientePremium clientePremium = new ClientePremium("Maria", "Su casa", "11111111A", "maria@email.com");
        clientePremium.setDescuento(20);

        // Articulo con valor 100, gastos 50
        Articulo articulo = new Articulo("A111", "Artículo de prueba", 100.0f, 50.0f, 20);
        // Pedido con 2 artículos
        Pedido pedido = new Pedido(articulo, 2, clientePremium, LocalDateTime.now(), TipoEstado.PENDIENTE);

        // Cálculo esperado:
        double totalEsperado = (50 * (1.0 - (20 / 100.0))) + (100 * 2);

        // Prueba
        assertEquals(totalEsperado, pedido.calcularTotal(), 0.01);
    }

    @Test
    void testCalcularTotalconClienteEstandar() {

        // Cliente estandar
        Cliente clienteEstandar = new ClienteEstandar("Maria", "Su casa", "11111111A", "maria@email.com");

        // Articulo con valor 100, gastos 50
        Articulo articulo = new Articulo("A111", "Artículo de prueba", 100.0f, 50.0f, 20);
        // Pedido con 2 artículos
        Pedido pedido = new Pedido(articulo, 2, clienteEstandar, LocalDateTime.now(), TipoEstado.PENDIENTE);

        // Cálculo esperado:
        double totalEsperado = 50 + (100 * 2);

        // Prueba
        assertEquals(totalEsperado, pedido.calcularTotal(), 0.01);
    }

    @Test
    void testCalcularTotalconValoresNulos() {
        // Valores extremos: artículo con valores a 0.

        // Cliente estandar
        Cliente clienteEstandar = new ClienteEstandar("Maria", "Su casa", "11111111A", "maria@email.com");

        // Articulo con valor 0, gastos 0
        Articulo articulo = new Articulo("A111", "Artículo de prueba", 0.0f, 0.0f, 20);
        // Pedido con 0 artículos
        Pedido pedido = new Pedido(articulo, 0, clienteEstandar, LocalDateTime.now(), TipoEstado.PENDIENTE);

        // Prueba se espera que sea 0
        assertEquals(0.0, pedido.calcularTotal(), 0.01);
    }
}