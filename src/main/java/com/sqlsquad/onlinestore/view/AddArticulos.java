package com.sqlsquad.onlinestore.view;

import com.sqlsquad.onlinestore.controlador.ArticuloController;
import com.sqlsquad.onlinestore.modelo.entity.Articulo;
import com.sqlsquad.onlinestore.util.AppService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;


public class AddArticulos {

    @FXML
    private TextField codigo, descripcion, precio, gastos, tiempo;
    @FXML private Button btnAdd;

    private ArticuloController articuloController;
    Articulo articuloObj = new Articulo();

    @FXML
    public void initialize() {
        // Obtenemos el controlador de la capa de servicios
        articuloController = AppService.getInstance().getArticuloController();
    }

    // Metodo que maneja el botton "Añadir"
    @FXML
    private void AddArticulo() {
        boolean error = false;

        // TODO Buscar otra forma de presentar los errores
        // Captamos los datos del formulario
        // TODO Implementar control para los 2 primeros campos (codigo y descripcion)
        articuloObj.setCodigoArticulo(codigo.getText());
        articuloObj.setDescripcion(descripcion.getText());
        try {
            String precioText = precio.getText().replace(",", ".");   // Control de "," en el precio"
            articuloObj.setPrecioVenta(Float.parseFloat(precioText));
        } catch (NumberFormatException e) {
            precio.setText(precio.getText() + " (Valor incorrecto)");
            error = true;
        }
        try {
            String gastosText = gastos.getText().replace(",", ".");    // Control de "," en los gastos""
            articuloObj.setGastosEnvio(Float.parseFloat(gastosText));
        } catch (NumberFormatException e) {
            gastos.setText(gastos.getText() + " (Valor incorrecto)");
            error = true;
        }
        try {
            articuloObj.setTiempoPreparacion(Integer.parseInt(tiempo.getText()));
        } catch (NumberFormatException e) {
            tiempo.setText(tiempo.getText() + " (Valor incorrecto)");
            error = true;
        }

        // Si no existe error proseguimos
        if (!error) {
            // Si no existe articulo lo guarda
            if (!articuloController.existeArticulo(articuloObj.getCodigoArticulo())) {
                articuloController.addArticulo(articuloObj);
                limpiarCampos();
            } else {
                mostrarError("Este Artículo ya existe");
            }
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
        codigo.setText("");
        descripcion.setText("");
        precio.setText("");
        gastos.setText("");
        tiempo.setText("");
    }


}
