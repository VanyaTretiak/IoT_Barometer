package studentgroup.demobarometer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;
import java.util.logging.Level;
import static studentgroup.demobarometer.DatabaseConnection.logger;

//Main page class
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            //Loading page, setting up resolution and outputing it
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
            Scene scene = new Scene(root, 700, 580);
            stage.setTitle("IoT Barometer");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception ex) {
            logger.log(Level.SEVERE, "An error occurred while connecting to the database", ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}