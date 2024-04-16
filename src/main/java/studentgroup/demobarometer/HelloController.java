package studentgroup.demobarometer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;


public class HelloController implements Initializable {

    @FXML
    protected Button graphBtn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        graphBtn.setOnAction(event -> {
//            loadSceneAndSendMessage();
//        });
    }



    mqttHandler Mqtt;
    private static mqttHandler mqtt = new mqttHandler();
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws SocketException, UnknownHostException, InterruptedException {
        welcomeText.setText("Welcome to JavaFX Application!");
        System.out.println("Tryyy connect..............................");
        mqtt.init_mqtt();
//        mqtt.connect("IoTBar", "Iotbarometer1");
        mqtt.subscribe("Data");
        mqtt.publish("Hiii", "Data");

    }
    private Stage stage;
    private  Parent root;
    private Scene scene;
    public void graphSwitchWindows(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("graphoanalytics.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void TrySendMqtt() {
        System.out.println("Tryyy to send on mqtt..............................");
//        mqtt.publish("Hiii", "Data");
    }

    private void loadSceneAndSendMessage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("graphoanalytics.fxml"));
            Parent root = loader.load();

            //Get controller of scene2
            GraphoanalyticsController graphoanalyticsController = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Graphoanalytics");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}