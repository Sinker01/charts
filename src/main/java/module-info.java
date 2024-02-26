module sit.app.factory.charts {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens sit.app.factory.charts to javafx.fxml;
    exports sit.app.factory.charts;
    exports sit.app.factory.charts.files;
    opens sit.app.factory.charts.files to javafx.fxml;
    exports sit.app.factory.charts.bottle;
    opens sit.app.factory.charts.bottle to javafx.fxml;
    exports sit.app.factory.charts.config;
    opens sit.app.factory.charts.config to javafx.fxml;
}