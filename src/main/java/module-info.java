module studentgroup.demobarometer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens studentgroup.demobarometer to javafx.fxml;
    exports studentgroup.demobarometer;
}