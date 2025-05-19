package com.sqlsquad.onlinestore.view;

import com.sqlsquad.onlinestore.controlador.ArticuloController;
import com.sqlsquad.onlinestore.modelo.entity.Articulo;
import com.sqlsquad.onlinestore.util.AppService;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class AddArticulos {

    @FXML private TextField codigo, descripcion, precio, gastos, tiempo;
    @FXML private Label codigoError, descripcionError, precioError, gastosError, tiempoError;
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

        // Verificación de campos obligatorios
        if (codigo.getText().isEmpty()) {
            codigoError.setText("⚠ Campo obligatorio");
            error = true;
        } else {
            codigoError.setText("");
        }

        if (descripcion.getText().isEmpty()) {
            descripcionError.setText("⚠ Campo obligatorio");
        } else {
            descripcionError.setText("");
        }

        if (precio.getText().isEmpty()) {
            precioError.setText("⚠ Campo obligatorio");
        } else {
            precioError.setText("");
            try {
                Float precioValor = Float.parseFloat(precio.getText().replace(",", "."));
                if (precioValor <= 0) {
                    precioError.setText("⚠ Valor incorrecto");
                    error = true;
                }
            } catch (NumberFormatException e) {
                precioError.setText("⚠ Valor incorrecto");
                error = true;
            }
        }

        if (gastos.getText().isEmpty()) {
            gastosError.setText("⚠ Campo obligatorio");
        } else {
            gastosError.setText("");
            try {
                Float gastosValor = Float.parseFloat(gastos.getText().replace(",", "."));
                if (gastosValor <= 0) {
                    gastosError.setText("⚠ Valor incorrecto");
                    error = true;
                }
            } catch (NumberFormatException e) {
                gastosError.setText("⚠ Valor incorrecto");
                error = true;
            }
        }

        if (tiempo.getText().isEmpty()) {
            tiempoError.setText("⚠ Campo obligatorio");
        } else {
            tiempoError.setText("");
            try {
                Integer tiempoValor = Integer.parseInt(tiempo.getText());
                if (tiempoValor <= 0) {
                    tiempoError.setText("⚠ Valor incorrecto");
                    error = true;
                }
            } catch (NumberFormatException e) {
                tiempoError.setText("⚠ Valor incorrecto");
                error = true;
            }
        }

        // Si existen errores no se procesa el Articulo
        if (error) {
            return;
        }

        // Captar valores del formulario
        articuloObj.setCodigoArticulo(codigo.getText());
        articuloObj.setDescripcion(descripcion.getText());
        articuloObj.setPrecioVenta(Float.parseFloat(precio.getText().replace(",", ".")));
        articuloObj.setTiempoPreparacion(Integer.parseInt(tiempo.getText()));
        articuloObj.setGastosEnvio(Float.parseFloat(gastos.getText().replace(",", ".")));


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
