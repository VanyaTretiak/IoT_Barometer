package studentgroup.demobarometer;
import javafx.animation.KeyFrame;
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
import javafx.scene.control.Button;

import javafx.util.Duration;
import studentgroup.demobarometer.ChartController;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.Math.abs;

public class GraphController implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;
    @FXML
    ProgressBar TemperatureProgressBar;
    @FXML
    ProgressBar HumidityProgressBar;
    @FXML
    ProgressBar PressureProgressBar;

    DatabaseConnection dbconn = new DatabaseConnection();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> updateProgressBars()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateProgressBars() {
        Measurement lastItem = dbconn.getLastItem();
        Double progressTemperature = (lastItem.getTemperature()+50) / 100;
        Double progressHumidity = lastItem.getHumidity() / 100;
        Double progressPressure = (lastItem.getPressure()-500) / (350);
        TemperatureProgressBar.setProgress(progressTemperature);
        HumidityProgressBar.setProgress(progressHumidity);
        PressureProgressBar.setProgress(progressPressure);
//        System.out.println(progressHumidity +" "+ progressTemperature +" "+ progressPressure +" "+ lastItem.getTemperature()+" "+ lastItem.getHumidity()+" "+ lastItem.getPressure());
    }

    //Switching to main page function

    public void SwitchToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }
    public void SwitchToGraphoAnalytics(ActionEvent event) throws IOException {
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
    public void SwitchToPressChart(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PressureChart.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }
    public void SwitchToHummChart(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HumidityChart.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }

}
