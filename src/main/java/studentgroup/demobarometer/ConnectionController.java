package studentgroup.demobarometer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class ConnectionController {

    private static mqttHandler mqtt = new mqttHandler();


    static void TrySendMqtt() {
        System.out.println("Trying to transmit message...");
        //mqtt.publish("Sent", "Data");
    }
    static public int mqttLoad(){


        return 0;
    }

}