module workshop.javafx.jdbc {
    requires javafx.controls;
    requires javafx.fxml;

    opens workshop.javafx.jdbc to javafx.fxml;
    exports workshop.javafx.jdbc;
    opens workshop.javafx.jdbc.model.entities to javafx.base;
}
