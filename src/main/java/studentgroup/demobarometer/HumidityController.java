package studentgroup.demobarometer;

import javafx.animation.*;
import javafx.application.Platform;
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
import java.util.Objects;
//import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

//Class to initialize HumidityChart
public class HumidityController implements Initializable {
    @FXML
    private LineChart<Double, Double> TemperatureChart;
    private XYChart.Series<Double, Double> series;
    private int time = 0;
    //private Random random = new Random();

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
        TemperatureChart.setStyle("CHART_COLOR_1: #0000FF ;");
        xAxis.setAutoRanging(false);
        xAxis.setTickUnit(1);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(120);

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);
        series = new XYChart.Series<>();
        TemperatureChart.getData().add(series);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> updateChart()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                TempChartClock.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))),
                new KeyFrame(Duration.seconds(1))
        );

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    //Function to convert type of time variable
    Double timeToDouble(LocalDateTime time){
        String str = time.toString();
        String[] stra = str.split("T");
        String[] strb = stra[1].split(":");
        String a = strb[1];
        String b = "0." + strb[2];
        System.out.println();
        return Double.parseDouble(a) + Double.parseDouble(b);
    }

    //Function to update HumidityChart
    public void updateChart() {
        time++;
        Measurement lastItem = dbconn.getLastItem();

        //int value = random.nextInt(100)-50;
        //XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(time, value);
        XYChart.Data<Double, Double> dataPoint = new XYChart.Data<>(time * 1.0, lastItem.getHumidity());
        series.getData().add(dataPoint);

        Platform.runLater(() -> {
            Node node = dataPoint.getNode();
            if (node != null) {
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), node);
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);
                fadeTransition.play();

                TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), node);
                translateTransition.setFromY(-10);
                translateTransition.setToY(0);
                translateTransition.play();
            }
        });

        if (time > 120) {
            animateXAxisRange(time - 115, time + 5);
        }
    }

    //Function to apply animations on HumidityChart
    private void animateXAxisRange(double newLowerBound, double newUpperBound) {
        Timeline animation = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(xAxis.lowerBoundProperty(), xAxis.getLowerBound()),
                        new KeyValue(xAxis.upperBoundProperty(), xAxis.getUpperBound())),
                new KeyFrame(Duration.seconds(0.1),
                        new KeyValue(xAxis.lowerBoundProperty(), newLowerBound, Interpolator.EASE_BOTH),
                        new KeyValue(xAxis.upperBoundProperty(), newUpperBound, Interpolator.EASE_BOTH)
                )
        );

        animation.play();
    }

    //Definition of variables required to execute scene
    private Stage stage;
    private Parent root;
    private Scene scene;


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

    //Function to switch to Real-time subpage
    public void SwitchToRealTime(ActionEvent event) throws IOException, InterruptedException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HumidityChart.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }

    //Function to switch to Week subpage
    public void SwitchToWeek(ActionEvent event) throws IOException, InterruptedException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HumidityChartWeek.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }

    //Function to switch to Month subpage
    public void SwitchToMonth(ActionEvent event) throws IOException, InterruptedException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HumidityChartMonth.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }

    //Setting up chart function
    public void loadDataChart() {
        Platform.runLater(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}