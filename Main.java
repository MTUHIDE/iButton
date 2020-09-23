package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("cocoLogin.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
        Button button1 = new Button();
        button1.setText("Button");
        button1.prefWidthProperty().bind(pane.widthProperty());
        button1.prefHeightProperty().bind(pane.heightProperty());

        pane.getChildren().add(button1);
        pane.setStyle("-fx-border-color:black;");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    StackPane pane = new StackPane();
    Scene scene = new Scene(pane, 100, 100);

    public static void main(String[] args) {
        launch(args);
    }

}
