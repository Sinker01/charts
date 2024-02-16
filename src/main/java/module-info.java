module com {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com to javafx.fxml;
    exports com;
    exports com.files;
    opens com.files to javafx.fxml;
    exports com.graph;
    opens com.graph to javafx.fxml;
    exports com.flasche;
    opens com.flasche to javafx.fxml;
}