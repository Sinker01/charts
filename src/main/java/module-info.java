module com.example.charts {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.charts to javafx.fxml;
    exports com.example.charts;
    exports com.example.charts.files;
    opens com.example.charts.files to javafx.fxml;
}