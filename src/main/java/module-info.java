module com.sqlsquad.onlinestore {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.formsfx;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    opens com.sqlsquad.onlinestore to javafx.fxml;
    opens com.sqlsquad.onlinestore.modelo to org.hibernate.orm.core;
    exports com.sqlsquad.onlinestore;
    exports com.sqlsquad.onlinestore.modelo;
    exports com.sqlsquad.onlinestore.controlador;
    opens com.sqlsquad.onlinestore.controlador to javafx.fxml;
}