module com.example.tap2024 {
    requires javafx.controls;
    requires javafx.fxml;



    opens com.example.tap2024 to javafx.fxml;
    exports com.example.tap2024;
///conectar con mysql en java
    requires java.sql;
    requires mysql.connector.j;
    requires org.kordamp.bootstrapfx.core;

    //
    opens com.example.tap2024.modelos;
}