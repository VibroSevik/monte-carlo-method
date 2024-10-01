package com.oryreq.montecarlomethod;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080,  720);
        scene.setFill(Color.TRANSPARENT);

        URL url = this.getClass().getResource("styles.css");
        String css = url.toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Monte Carlo Croupier");
        stage.getIcons().add(new Image("file:./src/main/resources/com/oryreq/montecarlomethod/logo.png"));

        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}