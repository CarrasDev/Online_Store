module com.sqlsquad.onlinestore {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.sqlsquad.onlinestore to javafx.fxml;
    exports com.sqlsquad.onlinestore;
}