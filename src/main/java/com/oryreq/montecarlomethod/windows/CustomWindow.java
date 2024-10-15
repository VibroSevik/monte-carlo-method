package com.oryreq.montecarlomethod.windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;

public class CustomWindow {

    private final Stage stage;


    public CustomWindow(URL resource, URL styles, Stage stage) throws Exception {
        var loader = new FXMLLoader(resource);
        Scene scene = new Scene(loader.load());
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(styles.toExternalForm());

        this.stage = stage;
        this.stage.setScene(scene);
    }

    public CustomWindow(URL resource, URL styles, Stage stage, double width, double height) throws Exception {
        var loader = new FXMLLoader(resource);
        Scene scene = new Scene(loader.load(), width, height);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(styles.toExternalForm());

        this.stage = stage;
        this.stage.setScene(scene);
    }

    public void setInitOwner(Window owner) {
        this.stage.initOwner(owner);
    }

    public void show() {
        this.stage.show();
    }

    public void close() {
        this.stage.close();
    }

    public Stage getStage() {
        return this.stage;
    }

    public static void setUnfocusToAllElements(Object window) {
        var fields = window.getClass().getDeclaredFields();
        for (var field : fields) {
            field.setAccessible(true);
            try {
                var object = field.get(window);
                if (object instanceof Node) {
                    ((Node) object).setFocusTraversable(false);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
