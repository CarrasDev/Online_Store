package com.sqlsquad.onlinestore.controlador;

import com.sqlsquad.onlinestore.modelo.entity.Articulo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class ArticuloViewController {

    @FXML
    private TextField codigo, descripcion, precio, gastos, tiempo;

    @FXML
    private Button btnAdd;

    Articulo articuloObj = new Articulo();

    // Metodo que maneja el botton "Añadir"
    @FXML
    private void handleAddArticulo() {
        boolean error = false;

        // TODO Buscar otra forma de presentar los errores
        // Captamos los datos del formulario
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
            System.out.println(articuloObj.toString());

            // TODO Verificar si existe el artículo
            // TODO si no existe crearlo en la BBDD
        }
    }
}
