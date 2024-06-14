package studentgroup.demobarometer;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

//Class to initialize HumidityChart for last 7 days
public class HumidityChartWeek implements Initializable {
    @FXML
    private LineChart<Integer, Double> TemperatureChart;
    private XYChart.Series<Integer, Double> series;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Label TempChartClock;

    //Establish connection to database
    DatabaseConnection dbconn = new DatabaseConnection();

    //Function to initialize HumidityChart
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        //TemperatureChart.setStyle("CHART_COLOR_1: #0000FF ;");
        xAxis.setAutoRanging(false);
        xAxis.setTickUnit(1);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(8);

        yAxis.setAutoRanging(true);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);
        series = new XYChart.Series<>();
        TemperatureChart.getData().add(series);

        updateChart();

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                TempChartClock.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))),
                new KeyFrame(Duration.seconds(1))
        );

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    //Function to update HumidityChart
    public void updateChart() {
        ArrayList<Measurement> lastItems = dbconn.getLastItemsForMonth();
        int max = lastItems.size();

        for (int i = 0; i < 7; i++){
            if (i + 1 <= max){
                XYChart.Data<Integer, Double> dataPoint = new XYChart.Data<>(i + 1, lastItems.get(i).getHumidity());
                series.getData().add(dataPoint);
            }
            else {
                break;
            }
        }
    }

    //Definition of variables required to execute scene
    private Stage stage;
    private Parent root;
    private Scene scene;

    //Function to switch into Graphics page
    public void SwitchToGraphoAnalytics(ActionEvent event) throws IOException, InterruptedException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("graphoanalytics.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }

    //Function to switch into Real-time subpage
    public void SwitchToRealTime(ActionEvent event) throws IOException, InterruptedException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HumidityChart.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }

    //Function to switch into Week subpage
    public void SwitchToWeek(ActionEvent event) throws IOException, InterruptedException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HumidityChartWeek.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }

    //Function to switch into Month subpage
    public void SwitchToMonth(ActionEvent event) throws IOException, InterruptedException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HumidityChartMonth.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }
}