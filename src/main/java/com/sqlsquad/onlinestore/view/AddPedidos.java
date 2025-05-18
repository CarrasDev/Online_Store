package com.sqlsquad.onlinestore.view;

import com.sqlsquad.onlinestore.MainViewController;
import com.sqlsquad.onlinestore.controlador.ClienteController;
import com.sqlsquad.onlinestore.controlador.PedidoController;
import com.sqlsquad.onlinestore.util.AppService;
import com.sqlsquad.onlinestore.util.Validator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddPedidos {

    @FXML private TextField emailCliente, codigoArticulo, cantidad;
    @FXML private Label emailError, articuloError, cantidadError;
    @FXML private Button btnAdd;

    private PedidoController pedidoController;
    private ClienteController clienteController;
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

        // TODO Verificar existencia del Cliente antes dde crear Pedido
        if (!clienteController.existeCliente(emailCliente.getText())) {
            // TODO LLamar a la vista que crea los clientes
            mainViewController.loadView("Add-Clientes.fxml");

        }

        // TODO Capturar los datos del formulario


    }


    // TODO Pendiente verificar








}
