package com.sqlsquad.onlinestore.view;

import com.sqlsquad.onlinestore.controlador.ClienteController;
import com.sqlsquad.onlinestore.modelo.entity.cliente.Cliente;
import com.sqlsquad.onlinestore.modelo.entity.cliente.ClienteEstandar;
import com.sqlsquad.onlinestore.modelo.entity.cliente.ClientePremium;
import com.sqlsquad.onlinestore.modelo.enums.TipoCliente;
import com.sqlsquad.onlinestore.util.AppService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddClientes {

    @FXML private TextField email, nombre, nif, domicilio;
    @FXML private ToggleGroup tipoClienteGroup;
    @FXML private RadioButton radioPremium, radioEstandar;
    @FXML private Button btnAdd;

    // TODO pendiente declarar más cosas
    private ClienteController clienteController;

    @FXML
    public void initialize() {
        // Obtenemos el controlador de la capa de servicios
        clienteController = AppService.getInstance().getClienteController();

        // Obtener valores de RadioButton
        tipoClienteGroup = new ToggleGroup();
        radioPremium.setToggleGroup(tipoClienteGroup);
        radioEstandar.setToggleGroup(tipoClienteGroup);
    }

    @FXML
    private void addCliente() {
        boolean error = false;

        String emailText = email.getText(); // Todo verificar que es un email valido y no existe en BBDD
        String nombreText = nombre.getText();
        String nifText = nif.getText();
        String domicilioText = domicilio.getText();

        // Gestión tipo cliente
        String tipoClienteText = ((RadioButton) tipoClienteGroup.getSelectedToggle()).getText();
        TipoCliente tipoCliente = TipoCliente.valueOf(tipoClienteText.toUpperCase());

        Cliente nuevoCliente;
        if (tipoCliente == TipoCliente.PREMIUM) {
            nuevoCliente = new ClientePremium(emailText, nombreText, nifText, domicilioText);
        } else {
            nuevoCliente = new ClienteEstandar(emailText, nombreText, nifText, domicilioText);
        }

        if(!error) {
            // Si no existe cliente lo guarda
            if (!clienteController.existeCliente(nuevoCliente.getEmail())) {
                // TODO Control de trazas, BBDD desconectada
                System.out.println("Cliente guardado: " + nuevoCliente);
                // clienteController.addCliente(nuevoCliente);
            } else {
                mostrarError("Este Cliente ya existe")
            }
        }
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        // Espera interacción del usuario y limpia los campos
        alerta.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                limpiarCampos();
            }
        });
    }

    private void limpiarCampos() {
        // TODO Adaptar campos a cliente
        email.setText("");
        nif.setText("");
        nombre.setText("");
        domicilio.setText("");
    }
}
