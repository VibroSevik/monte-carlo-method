package com.oryreq.montecarlomethod.windows;

import com.google.common.eventbus.Subscribe;

import com.oryreq.montecarlomethod.Application;
import com.oryreq.montecarlomethod.models.StringEvent;
import com.oryreq.montecarlomethod.services.EventBusFactory;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainWindow {

    private double xOffset;
    private double yOffset;

    private final Stage applicationStage;

    private CustomWindow sidebar;
    private CustomWindow binomialCroupier;
    private CustomWindow uniformCroupier;


    public MainWindow(Stage applicationStage) throws Exception {
        EventBusFactory.getEventBus().register(this);
        this.applicationStage = applicationStage;

        createAllWindows();

        // с перемещением баги
        /*
        binomialCroupier.getStage().getScene().setOnMousePressed(event -> {
            xOffset = binomialCroupier.getStage().getX() - event.getScreenX();
            yOffset = binomialCroupier.getStage().getY() - event.getScreenY();
        });

        binomialCroupier.getStage().getScene().setOnMouseDragged(event -> {
            binomialCroupier.getStage().setX(event.getScreenX() + xOffset);
            binomialCroupier.getStage().setY(event.getScreenY() + yOffset);
            sidebar.getStage().setX(event.getScreenX() + xOffset - 80);
            sidebar.getStage().setY(event.getScreenY() + yOffset);
        });
         */


        binomialCroupier.show();
        sidebar.show();
    }

    private void createAllWindows() throws Exception {
        binomialCroupier = createBinomialDistributionWindow();
        binomialCroupier.setInitOwner(applicationStage);

        sidebar = createSidebar();
        sidebar.setInitOwner(applicationStage);

        uniformCroupier = createUniformDistributionWindow();
        uniformCroupier.setInitOwner(applicationStage);
    }


    private CustomWindow createSidebar() throws Exception {
        var resource = Application.class.getResource("windows/sidebar/sidebar.fxml");
        var styles = Application.class.getResource("styles.css");

        var width = 80;
        var height = 720;

        var stage = new Stage();
        stage.setTitle("Sidebar menu");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setX(370);
        stage.setY(180);

        return new CustomWindow(resource, styles, stage, width, height);
    }

    private CustomWindow createBinomialDistributionWindow() throws Exception {
        var resource = Application.class.getResource("windows/binomial_croupier/binomial-distribution.fxml");
        var styles = Application.class.getResource("styles.css");

        var width = 1080;
        var height = 720;

        var stage = new Stage();
        stage.setTitle("Binomial croupier");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setX(450);
        stage.setY(180);

        return new CustomWindow(resource, styles, stage, width, height);
    }

    private CustomWindow createUniformDistributionWindow() throws Exception {
        var resource = Application.class.getResource("windows/uniform_croupier/uniform-distribution.fxml");
        var styles = Application.class.getResource("styles.css");

        var width = 1080;
        var height = 720;

        var stage = new Stage();
        stage.setTitle("Uniform croupier");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setX(450);
        stage.setY(180);

        return new CustomWindow(resource, styles, stage, width, height);
    }

    @Subscribe
    public void handleUniformWindowOpen(StringEvent event) {
        switch (event.getType()) {
            case OPEN_BINOMIAL_WINDOW -> {
                binomialCroupier.show();
                uniformCroupier.close();
            }
            case OPEN_UNIFORM_WINDOW -> {
                uniformCroupier.show();
                binomialCroupier.close();
            }
        }
    }

}
