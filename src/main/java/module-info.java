module studentgroup.demobarometer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.eclipse.paho.client.mqttv3;
    requires com.hivemq.client.mqtt;

    opens studentgroup.demobarometer to javafx.fxml;
    exports studentgroup.demobarometer;
}