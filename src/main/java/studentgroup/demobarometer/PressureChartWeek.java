//package studentgroup.demobarometer;
//import javafx.animation.*;
//import javafx.application.Platform;
//import javafx.beans.property.SimpleDoubleProperty;
//import javafx.scene.chart.NumberAxis;
//import javafx.util.Duration;
//import studentgroup.demobarometer.ConnectionController;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.chart.LineChart;
//import javafx.scene.chart.XYChart;
//import javafx.stage.Stage;
//
//import java.time.LocalDateTime;
//import java.util.Random;
//import java.util.Timer;
//import java.util.concurrent.TimeUnit;
//import java.io.IOException;
//import java.net.URL;
//import java.util.Objects;
//import java.util.ResourceBundle;
//public class ChartController implements Initializable {
//    @FXML
//    private LineChart<Number, Double> TemperatureChart;
//    private XYChart.Series<Number, Double> series;
//    private int time = 0;
//    private Random random = new Random();
//    @FXML
//    private NumberAxis xAxis;
//
//    @FXML
//    private NumberAxis yAxis;
//
//    DatabaseConnection dbconn = new DatabaseConnection();
//    @FXML
//    public void initialize(URL url, ResourceBundle rb) {
//        TemperatureChart.setStyle("CHART_COLOR_1: #0000FF ;");
//        xAxis.setAutoRanging(false);
//        xAxis.setLowerBound(0);
//        xAxis.setUpperBound(35);
//        xAxis.setTickUnit(1);
//
//        yAxis.setAutoRanging(false);
//        yAxis.setLowerBound(-50);
//        yAxis.setUpperBound(50);
//        series = new XYChart.Series<>();
//        TemperatureChart.getData().add(series);
//
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> updateChart()));
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();
//    }
//
//
//
//    public void updateChart() {
//        time++;
//        Measurement lastItem = dbconn.getLastItem();
//
//
//        int value = random.nextInt(100)-50;
////        XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(time, value);
//        XYChart.Data<Number, Double> dataPoint = new XYChart.Data<>(time, lastItem.getTemperature());
//        series.getData().add(dataPoint);
//
//        Platform.runLater(() -> {
//            Node node = dataPoint.getNode();
//            if (node != null) {
//                FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), node);
//                fadeTransition.setFromValue(0);
//                fadeTransition.setToValue(1);
//                fadeTransition.play();
//
//                TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), node);
//                translateTransition.setFromY(-10);
//                translateTransition.setToY(0);
//                translateTransition.play();
//            }
//        });
//
//        if (series.getData().size() > 30) {
//            series.getData().remove(0);
//        }
//
//        if (time > 30) {
//            animateXAxisRange(time - 30, time + 5);
//        }
//    }
//
//    private void animateXAxisRange(double newLowerBound, double newUpperBound) {
//        Timeline animation = new Timeline(
//                new KeyFrame(Duration.ZERO,
//                        new javafx.animation.KeyValue(xAxis.lowerBoundProperty(), xAxis.getLowerBound()),
//                        new javafx.animation.KeyValue(xAxis.upperBoundProperty(), xAxis.getUpperBound())
//                ),
//                new KeyFrame(Duration.seconds(0.1),
//                        new javafx.animation.KeyValue(xAxis.lowerBoundProperty(), newLowerBound, Interpolator.EASE_BOTH),
//                        new javafx.animation.KeyValue(xAxis.upperBoundProperty(), newUpperBound, Interpolator.EASE_BOTH)
//                )
//        );
//        animation.play();
//    }
//
//    private Stage stage;
//    private Parent root;
//    private Scene scene;
//    public void SwitchToMain(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root, 700, 580);
//        stage.setScene(scene);
//        stage.show();
//    }
////    @FXML
////    private void HandleButtonAction(ActionEvent event) {
////        XYChart.Series series1 = (XYChart.Series)TemperatureChart.getData().get(0);
////        new animatefx.animation.Pulse(series1.getNode()).play();
////    }
//
//    //Setting up chart function
//    public  void loadDataChart() throws InterruptedException {
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//    }
//    public void mqttLoadData(){
//
//    }
//
//}
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

public class PressureChartWeek implements Initializable {
    @FXML
    private LineChart<Integer, Double> TemperatureChart;
    private XYChart.Series<Integer, Double> series;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private Label TempChartClock;

    DatabaseConnection dbconn = new DatabaseConnection();
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
//        TemperatureChart.setStyle("CHART_COLOR_1: #0000FF ;");
        xAxis.setAutoRanging(false);
        xAxis.setTickUnit(1);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(8);


        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(650);
        yAxis.setUpperBound(810);
        series = new XYChart.Series<>();
        TemperatureChart.getData().add(series);

        updateChart();
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                TempChartClock.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
        ),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }


    public void updateChart() {
        ArrayList<Measurement> lastItems = dbconn.getLastItemsForMonth();
        Integer max = lastItems.size();
        for (int i = 0; i < 7; i++){
            if (i+1 <= max){
                XYChart.Data<Integer, Double> dataPoint = new XYChart.Data<>(i+1, lastItems.get(i).getPressure());
                series.getData().add(dataPoint);
            } else {
                break;
            }
        }
    }


    private Stage stage;
    private Parent root;
    private Scene scene;
    public void SwitchToGraphoAnalytics(ActionEvent event) throws IOException, InterruptedException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("graphoanalytics.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }
    public void SwitchToRealTime(ActionEvent event) throws IOException, InterruptedException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PressureChart.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }public void SwitchToMonth(ActionEvent event) throws IOException, InterruptedException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PressureChartMonth.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }public void SwitchToWeek(ActionEvent event) throws IOException, InterruptedException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PressureChartWeek.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }


}


