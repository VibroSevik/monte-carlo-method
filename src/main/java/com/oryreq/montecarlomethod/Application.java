package com.oryreq.montecarlomethod;

import com.oryreq.montecarlomethod.windows.MainWindow;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Monte Carlo Croupier");
        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/logo.png"))));
        //primaryStage.getIcons().add(new Image("file:./src/main/resources/com/oryreq/montecarlomethod/logo.png"));
        primaryStage.setOpacity(0);
        primaryStage.show();

        var mainWindow = new MainWindow(primaryStage);
    }

    public static void main(String[] args) {
        launch();
    }

}