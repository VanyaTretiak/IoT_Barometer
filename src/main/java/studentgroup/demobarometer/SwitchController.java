package studentgroup.demobarometer;

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

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.Math.cos;

public class SwitchController implements Initializable {
    //Declaring required objects
    static boolean Connected = false;
//    public Gauge humidityGauge;
//    public Gauge digitalTempGauge;
//    barometerGauge = GaugeBuilder.create().skinType(Gauge.SkinType.SECTION).needleColor(lcdFontColor)
//  .title("Atmospheric Pressure")
//  .unit(" mbar")
//  .unitColor(Color.WHITE)
//  .titleColor(Color.WHITE)
//  .valueVisible(true)
//  .valueColor(Color.WHITE)
//  .markersVisible(true)
//  .decimals(0)
//  .minValue(940)
//  .maxValue(1060)
//  .animated(true)
//  .knobColor(Color.FLORALWHITE)
//  .highlightSections(true)
//  .sections(
//            SectionBuilder.create()
//      .start(1040)
//      .stop(1060)
//      .text("VERY DRY")
//      .color(lcdBackgroundColor)
//      .highlightColor(Color.FLORALWHITE)
//      .textColor(Gauge.DARK_COLOR)
//      .build(),
//    SectionBuilder.create()
//            .start(1020)
//      .stop(1040)
//      .text("FAIR")
//      .color(lcdBackgroundColor)
//      .highlightColor(Color.FLORALWHITE)
//      .textColor(Gauge.DARK_COLOR)
//      .build(),
//    SectionBuilder.create()
//            .start(1000)
//      .stop(1020)
//      .text("CHANGE")
//      .color(lcdBackgroundColor)
//      .highlightColor(Color.FLORALWHITE)
//      .textColor(Gauge.DARK_COLOR)
//      .build(),
//    SectionBuilder.create()
//            .start(970)
//      .stop(1000)
//      .text("RAIN")
//      .color(lcdBackgroundColor)
//      .highlightColor(Color.FLORALWHITE)
//      .textColor(Gauge.DARK_COLOR)
//      .build(),
//    SectionBuilder.create()
//            .start(940)
//      .stop(970)
//      .text("STORMY")
//      .color(lcdBackgroundColor)
//      .highlightColor(Color.FLORALWHITE)
//      .textColor(Gauge.DARK_COLOR)
//      .build())
//            .build();
//
//    FGauge barometerFGauge = new FGauge(barometerGauge, GaugeDesign.TILTED_BLACK, GaugeDesign.GaugeBackground.WHITE);
//vBox.getChildren().addAll(barometerFGauge);
    private Stage stage;
    private Parent root;
    private Scene scene;
    mqttHandler Mqtt;
    private static mqttHandler mqtt = new mqttHandler();
    @Override
    public void initialize(URL url, ResourceBundle rb){
        if (!Connected){
            try {
                mqttTry();
                Connected = true;
            } catch (SocketException | UnknownHostException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
    //Switching to main page function
    private void mqttTry() throws SocketException, UnknownHostException, InterruptedException {
        System.out.println("Connecting...");
        mqtt.init_mqtt();
        mqtt.subscribe("Data");
    }
    public void SwitchToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }


    //Switching to graphoanalytics page function
    public void SwitchToGraphoAnalytics(ActionEvent event) throws IOException, InterruptedException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("graphoanalytics.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToTempChart(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TemperatureChart.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }
}
