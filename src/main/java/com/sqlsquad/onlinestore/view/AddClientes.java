package com.sqlsquad.onlinestore.view;

import com.sqlsquad.onlinestore.controlador.ClienteController;
import com.sqlsquad.onlinestore.modelo.entity.cliente.Cliente;
import com.sqlsquad.onlinestore.modelo.entity.cliente.ClienteEstandar;
import com.sqlsquad.onlinestore.modelo.entity.cliente.ClientePremium;
import com.sqlsquad.onlinestore.modelo.enums.TipoCliente;
import com.sqlsquad.onlinestore.util.AppService;
import com.sqlsquad.onlinestore.util.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddClientes {

    @FXML private TextField email, nombre, nif, domicilio;
    @FXML private Label emailError, nifError, nombreError, domicilioError;
    @FXML private ToggleGroup tipoClienteGroup;
    @FXML private RadioButton radioPremium, radioEstandar;
    @FXML private Button btnAdd;

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

        // Verificación de campos obligatorios
        if (nombre.getText().isEmpty()) {
            nombreError.setText("⚠ Campo obligatorio");
            error = true;
        } else {
            nombreError.setText("");
        }

        if (domicilio.getText().isEmpty()) {
            domicilioError.setText("⚠ Campo obligatorio");
            error = true;
        } else {
            domicilioError.setText("");
        }

        if (email.getText().isEmpty()) {
            emailError.setText("⚠ Campo obligatorio");
            error = true;
        } else if (!Validator.esEmailValido(email.getText())) {
            emailError.setText("⚠ Formato incorrecto");
            error = true;
        } else {
            emailError.setText("");
        }

        if (nif.getText().isEmpty()) {
            nifError.setText("⚠ Campo obligatorio");
            error = true;
        } else if (!Validator.esDniONieValido(nif.getText())) {
            nifError.setText("⚠ Formato incorrecto");
            error = true;
        } else {
            nifError.setText("");
        }

        // Si existen errores no se procesa el Cliente
        if (error) {
            return;
        }

        // Captamos los datos del formulario
        String emailText = email.getText();
        String nombreText = nombre.getText();
        String nifText = nif.getText();
        String domicilioText = domicilio.getText();

        // Obtener tipo de cliente
        String tipoClienteText = ((RadioButton) tipoClienteGroup.getSelectedToggle()).getText();
        TipoCliente tipoCliente = TipoCliente.valueOf(tipoClienteText.toUpperCase());

        Cliente nuevoCliente;
        // Creación de cliente
        if (tipoCliente == TipoCliente.PREMIUM) {
            nuevoCliente = new ClientePremium(nombreText, domicilioText, nifText, emailText);
        } else {
            nuevoCliente = new ClienteEstandar(nombreText, domicilioText, nifText, emailText);
        }

        // Verificar si el cliente ya existe antes de guardarlo
        if (!clienteController.existeCliente(nuevoCliente.getEmail())) {
                clienteController.addCliente(nuevoCliente);
                limpiarCampos();
                radioEstandar.setSelected(true);
        } else {
                mostrarError("Este Cliente ya existe");
        }
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Atención");
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
        email.setText("");
        nif.setText("");
        nombre.setText("");
        domicilio.setText("");
    }
}
