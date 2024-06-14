package studentgroup.demobarometer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GraphController implements Initializable {
    //Definition of variables required to execute scene
    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    ProgressBar TemperatureProgressBar;

    @FXML
    ProgressBar HumidityProgressBar;

    @FXML
    ProgressBar PressureProgressBar;

    //Establish connection to database
    DatabaseConnection dbconn = new DatabaseConnection();

    //Function to initialize progress bars
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> updateProgressBars()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    private double currentTemperature;
    private double currentHumidity;
    private double currentPressure;
    //Function to dynamically update progress bars
    private void updateProgressBars() {
        Measurement lastItem = dbconn.getLastItem();
        double progressTemperature = (lastItem.getTemperature() + 50) / 100;
        double progressHumidity = lastItem.getHumidity() / 100;
        double progressPressure = (lastItem.getPressure() - 500) / 350;
        Timeline tempTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(TemperatureProgressBar.progressProperty(), TemperatureProgressBar.getProgress())),
                new KeyFrame(Duration.seconds(1), new KeyValue(TemperatureProgressBar.progressProperty(), progressTemperature))
        );
        Timeline humidityTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(HumidityProgressBar.progressProperty(), HumidityProgressBar.getProgress())),
                new KeyFrame(Duration.seconds(1), new KeyValue(HumidityProgressBar.progressProperty(), progressHumidity))
        );
        Timeline pressureTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(PressureProgressBar.progressProperty(), PressureProgressBar.getProgress())),
                new KeyFrame(Duration.seconds(1), new KeyValue(PressureProgressBar.progressProperty(), progressPressure))
        );
        tempTimeline.play();
        humidityTimeline.play();
        pressureTimeline.play();

        currentTemperature = lastItem.getTemperature();
        currentHumidity = lastItem.getHumidity();
        currentPressure = lastItem.getPressure();
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
    public void SwitchToGraphoAnalytics(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("graphoanalytics.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }

    //Function to swith to TemperatureChart
    public void SwitchToTempChart(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TemperatureChart.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }

    //Function to swith to PressureChart
    public void SwitchToPressChart(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PressureChart.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }

    //Function to swith to HumidityChart
    public void SwitchToHummChart(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HumidityChart.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }
}