package studentgroup.demobarometer;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.net.URL;

//Class to initialize ChartController
public class ChartController implements Initializable {
    @FXML
    private LineChart<Double, Double> TemperatureChart;
    private XYChart.Series<Double, Double> series;
    private int time = 0;
    //private final Random random = new Random();

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Label TempChartClock;

    //Establish connection to database
    DatabaseConnection dbconn = new DatabaseConnection();

    //Function to initialize and setup TemperatureChart
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        TemperatureChart.setStyle("CHART_COLOR_1: #0000FF ;");
        xAxis.setAutoRanging(false);
        xAxis.setTickUnit(1);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(120);

        yAxis.setAutoRanging(true);
        yAxis.setLowerBound(-50);
        yAxis.setUpperBound(50);
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
    Double timeToDouble(LocalDateTime time) {
        String str = time.toString();
        String[] stra = str.split("T");
        String[] strb = stra[1].split(":");
        String a = strb[1];
        String b = "0." + strb[2];
        System.out.println();

        return Double.parseDouble(a) + Double.parseDouble(b);
    }

    //Function to update TemperatureChart
    public void updateChart() {
        time++;
        Measurement lastItem = dbconn.getLastItem();

        XYChart.Data<Double, Double> dataPoint = new XYChart.Data<>(time * 1.0, lastItem.getTemperature());
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

    //Function to apply animations on TemperatireChart
    private void animateXAxisRange(double newLowerBound, double newUpperBound) {
        Timeline animation = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new javafx.animation.KeyValue(xAxis.lowerBoundProperty(), xAxis.getLowerBound()),
                        new javafx.animation.KeyValue(xAxis.upperBoundProperty(), xAxis.getUpperBound())
                ),
                new KeyFrame(Duration.seconds(0.1),
                        new javafx.animation.KeyValue(xAxis.lowerBoundProperty(), newLowerBound, Interpolator.EASE_BOTH),
                        new javafx.animation.KeyValue(xAxis.upperBoundProperty(), newUpperBound, Interpolator.EASE_BOTH)
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

    //Function to switch into Real-time subpage
    public void SwitchToRealTime(ActionEvent event) throws IOException, InterruptedException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TemperatureChart.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }

    //Function to switch into Week subpage
    public void SwitchToWeek(ActionEvent event) throws IOException, InterruptedException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TemperatureChartWeek.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 700, 580);
        stage.setScene(scene);
        stage.show();
    }

    //Function to switch into Month subpage
    public void SwitchToMonth(ActionEvent event) throws IOException, InterruptedException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("TemperatureChartMonth.fxml")));
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
            catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}