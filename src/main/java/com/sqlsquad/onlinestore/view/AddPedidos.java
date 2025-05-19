package com.sqlsquad.onlinestore.view;

import com.sqlsquad.onlinestore.MainViewController;
import com.sqlsquad.onlinestore.controlador.ArticuloController;
import com.sqlsquad.onlinestore.controlador.ClienteController;
import com.sqlsquad.onlinestore.controlador.PedidoController;
import com.sqlsquad.onlinestore.util.AppService;
import com.sqlsquad.onlinestore.util.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class AddPedidos {

    @FXML private TextField emailCliente, codigoArticulo, cantidad;
    @FXML private Label emailError, articuloError, cantidadError;
    @FXML private Button btnAdd;

    private PedidoController pedidoController;
    private ClienteController clienteController;
    private ArticuloController articuloController;
    private MainViewController mainViewController;

    // Heredar el controlador de la vista principal para poder cambiar a la vista "add-clientes.fxml"
    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    @FXML
    public void initialize() {
        // Obtenemos el controlador de la capa de servicios
        pedidoController = AppService.getInstance().getPedidoController();
        clienteController = AppService.getInstance().getClienteController();
        articuloController = AppService.getInstance().getArticuloController();
    }


    @FXML
    public void addPedido() {
        boolean error = false;

        // TODO Implementar validaciones de campos
        // Verificación de campos obligatorios
        if (emailCliente.getText().isEmpty()) {
            emailError.setText("⚠ Campo obligatorio");
            error = true;
        } else if (!Validator.esEmailValido(emailCliente.getText())) {
            emailError.setText("⚠ Formato incorrecto");
            error = true;
        } else {
            emailError.setText("");
        }

        if (codigoArticulo.getText().isEmpty()) {
            articuloError.setText("⚠ Campo obligatorio");
            error = true;
        } else {
            articuloError.setText("");
        }

        if (cantidad.getText().isEmpty()) {
            cantidadError.setText("⚠ Campo obligatorio");
        } else {
            cantidadError.setText("");
            try {
                Integer cantidadValor = Integer.parseInt(cantidad.getText());
                if (cantidadValor <= 0) {
                    cantidadError.setText("⚠ Valor incorrecto");
                    error = true;
                }
            } catch (NumberFormatException e) {
                cantidadError.setText("⚠ Valor incorrecto");
                error = true;
            }
        }

        // Si existen errores no se procesa el Pedido
        if (error) {
            return;
        }

        // Verificar existencia del Cliente antes de crear Pedido
        if (!clienteController.existeCliente(emailCliente.getText())) {
            // Mensaje emergente con opción a crear cliente
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Cliente no encontrado");
            alerta.setHeaderText("El cliente con email " + emailCliente.getText() + " no existe");
            alerta.setContentText("¿Desea crearlo?");

            //Obtener respuesta del usuario
            ButtonType botonSi = new ButtonType("Si");
            ButtonType botonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            alerta.getButtonTypes().setAll(botonSi, botonNo);

            //Mostrar alerta y esperar respuesta del usuario
            Optional<ButtonType> resultado = alerta.showAndWait();

            if (resultado.isPresent() && resultado.get() == botonSi) {
                // Cargar la nueva vista en el contenedor 'viewPane'
                mainViewController.loadView("Add-clientes.fxml");

            } else {
                emailError.setText("⚠ Email inexistente");
                return;
            }
        }

        // Control de existencia de Articulo
        if(!articuloController.existeArticulo(codigoArticulo.getText())) {
            articuloError.setText("⚠ Articulo inexistente");
            return;
        }

        // Capturar los datos del formulario
        String emailText = emailCliente.getText();
        String codigoArticuloText = codigoArticulo.getText();
        Integer cantidadText = Integer.parseInt(cantidad.getText());

        // Crear pedido
        pedidoController.addPedido(codigoArticuloText, cantidadText, emailText);
        limpiarCampos();
        // TODO Añadir confirmación

    }

    private void limpiarCampos() {
        emailCliente.setText("");
        codigoArticulo.setText("");
        cantidad.setText("");
    }
}
