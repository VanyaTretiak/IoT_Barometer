package studentgroup.demobarometer;

import eu.hansolo.medusa.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// -----
import eu.hansolo.medusa.FGauge;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.GaugeDesign;
import eu.hansolo.medusa.SectionBuilder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
// -----

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.Math.cos;

//Class to initialize SwitchContoller
public class SwitchController implements Initializable {
    //Declaring required objects
    static boolean Connected = false;
    private Stage stage;
    private Parent root;
    private Scene scene;
    mqttHandler Mqtt;
    private static mqttHandler mqtt = new mqttHandler();

    @FXML
    VBox vBox;

    @FXML
    private Gauge humidityGauge;

    @FXML
    private Gauge digitalTempGauge;

    @FXML
    private Gauge barometerGauge;

    private final boolean simulatedMode = false; // if true, this allows you to run and app away from a Raspberry Pi.  It generates random readings in this mode.
    private double currentTemperature = 0;
    private double currentPressureMb = 0;
    private double currentHumidity = 0;

    DatabaseConnection dbconn = new DatabaseConnection();

    //Function to initialize MQTT connection
    @Override
    public void initialize(URL url, ResourceBundle rb){
        if (!Connected){
            try {
                mqttTry();
                Connected = true;
            }
            catch (SocketException | UnknownHostException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }

        buildAndDisplayBarometerGauge();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateDisplay(dbconn.getLastItem().getTemperature(), dbconn.getLastItem().getPressure(), dbconn.getLastItem().getHumidity())));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    //Function to try to establish connection between program and MQTT broker
    private void mqttTry() throws SocketException, UnknownHostException, InterruptedException {
        System.out.println("Connecting...");
        mqtt.init_mqtt();
        mqtt.subscribe("Data");
    }

    //Function to switch to Main page
    public void SwitchToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }

    //Function to switch to Graphics page
    public void SwitchToGraphoAnalytics(ActionEvent event) throws IOException, InterruptedException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("graphoanalytics.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }

    //Function to switch to TemperatureChart page
    public void SwitchToTempChart(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TemperatureChart.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }

    //Function to update display of measyrements
    private void updateDisplay(double temperature, double pressure, double humidity) {
        humidityGauge.setValue(humidity);
        digitalTempGauge.setValue(temperature);
        int intPressure = Double.valueOf(pressure).intValue();
        barometerGauge.setValue(intPressure);
        Timeline tempTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(digitalTempGauge.valueProperty(), currentTemperature)),
                new KeyFrame(Duration.seconds(1), new KeyValue(digitalTempGauge.valueProperty(), temperature))
        );
        Timeline humidityTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(humidityGauge.valueProperty(), currentHumidity)),
                new KeyFrame(Duration.seconds(1), new KeyValue(humidityGauge.valueProperty(), humidity))
        );
        tempTimeline.play();
        humidityTimeline.play();
        currentHumidity = humidity;
        currentTemperature = temperature;
        currentPressureMb = pressure;
    }

    //Function to create and display graphical devices
    private void buildAndDisplayBarometerGauge() {
        barometerGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.SECTION)
                .needleColor(Color.rgb(95,123,210)) // matches needle to other gauge font color
                .title("Atmosphere Barometer")
                .unit(" mm Hg.")
                .unitColor(Color.WHITE)
                .titleColor(Color.WHITE)
                .valueVisible(true)
                .valueColor(Color.WHITE)
                .markersVisible(true)
                .decimals(0)
                .minValue(700)
                .maxValue(800)
                .animated(true)
                .knobColor(Color.FLORALWHITE)
                .highlightSections(true)
                .sections(
                        SectionBuilder.create()
                                .start(770)
                                .stop(800)
                                .text("FAIR")
                                .color(Color.rgb(203, 215, 213))
                                .highlightColor(Color.FLORALWHITE)
                                .textColor(Gauge.DARK_COLOR)
                                .build(),
                        SectionBuilder.create()
                                .start(730)
                                .stop(770)
                                .text("VARIABLY")
                                .color(Color.rgb(203, 215, 213))
                                .highlightColor(Color.FLORALWHITE)
                                .textColor(Gauge.DARK_COLOR)
                                .build(),
                        SectionBuilder.create()
                                .start(700)
                                .stop(730)
                                .text("FALLOUT")
                                .color(Color.rgb(203, 215, 213))
                                .highlightColor(Color.FLORALWHITE)
                                .textColor(Gauge.DARK_COLOR)
                                .build())
                .build();

        FGauge barometerFGauge = new FGauge(barometerGauge, GaugeDesign.TILTED_BLACK, GaugeDesign.GaugeBackground.WHITE);
        vBox.getChildren().addAll(barometerFGauge);
    }
}