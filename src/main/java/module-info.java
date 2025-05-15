module com.sqlsquad.onlinestore {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.formsfx;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    opens com.sqlsquad.onlinestore to javafx.fxml;
    exports com.sqlsquad.onlinestore;
    exports com.sqlsquad.onlinestore.modelo;
}