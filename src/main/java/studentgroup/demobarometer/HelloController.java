package studentgroup.demobarometer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.SocketException;
import java.net.UnknownHostException;


public class HelloController {
    mqttHandler Mqtt;
    private static mqttHandler mqtt = new mqttHandler();
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws SocketException, UnknownHostException, InterruptedException {
        welcomeText.setText("Welcome to JavaFX Application!");
        System.out.println("Tryyy connect..............................");
        mqtt.balabala();
//        mqtt.connect("IoTBar", "Iotbarometer1");
        mqtt.subscribe("Data");
        mqtt.publish("Hiii", "Data");

    }
    @FXML
    protected void TrySendMqtt() {
        System.out.println("Tryyy to send on mqtt..............................");
//        mqtt.publish("Hiii", "Data");
    }
}