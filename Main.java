package sample;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.InetAddress;

public class Main extends Application {
//det er her vores scene i GUI køre. vi sætter vores GUI op
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("UDP Sender | ip:"+ InetAddress.getLocalHost());
        primaryStage.setScene(new Scene(root, 800, 675));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
