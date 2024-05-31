package studentgroup.demobarometer;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.Objects;

//Main page class
public class HelloApplication extends Application {

    public DatabaseConnection dbconn = new DatabaseConnection();
    Connection conn = dbconn.getConnection();
    @Override
    public void start(Stage stage) throws IOException {
        try {
            //Loading page, setting up resolution and outputing it
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
            Scene scene = new Scene(root, 700, 580);
            stage.setTitle("IoT Barometer");
            stage.setScene(scene);
            stage.show();


        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}

