module workshop.javafx.jdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens workshop.javafx.jdbc to javafx.fxml;
    opens workshop.javafx.jdbc.model.entities to javafx.base;
    exports workshop.javafx.jdbc;
}
