module org.example.crud {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.crud.ui to javafx.fxml;
    exports org.example.crud;
}
