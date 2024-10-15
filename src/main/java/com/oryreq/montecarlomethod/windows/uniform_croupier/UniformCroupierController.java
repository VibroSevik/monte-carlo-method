package com.oryreq.montecarlomethod.windows.uniform_croupier;

import com.google.common.eventbus.Subscribe;
import com.oryreq.montecarlomethod.Application;
import com.oryreq.montecarlomethod.models.StringEvent;
import com.oryreq.montecarlomethod.services.EventBusFactory;
import com.oryreq.montecarlomethod.windows.CustomWindow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ResourceBundle;

public class UniformCroupierController implements Initializable {


    /*---------------------------------------------*
     *            Sidebar menu elements            *
     *---------------------------------------------*/
    @FXML
    protected Pane activeButton;

    @FXML
    protected Label activeButtonText;


    /*---------------------------------------------*
     *           Current window elements           *
     *---------------------------------------------*/
    @FXML
    private AnchorPane activeWindow;

    @FXML
    protected Label version;

    @FXML
    protected Pane versionCircle;

    @FXML
    protected Label overviewButton;

    @FXML
    protected Label welcomeBackText;

    @FXML
    protected Label checkOverview;

    @FXML
    protected Label drawsText;

    @FXML
    protected Label drawsHundred;

    @FXML
    protected Label drawsThousand;

    @FXML
    protected Label drawsMillion;

    @FXML
    protected Label drawsCustom;

    @FXML
    private StackedBarChart<String, Integer> histogram;


    /*----------------------------------*
     *               Data               *
     *----------------------------------*/
    private double a = -3;
    private double b = -1;
    private int draws;

    private UniformCroupierService uniformCroupierService;


    /*------------------------------------*
     *          Callable windows          *
     *------------------------------------*/
    private CustomWindow inputDataWindow;
    private CustomWindow variationsWindow;

    private void play() {
        a = 0;
        b = 1;
        var drawData = uniformCroupierService.getDrawData(a, b, draws);
        uniformCroupierService.buildHistogram(draws, drawData, activeWindow);

        try {
            this.variationsWindow = this.createVariationsWindow();
            this.variationsWindow.setInitOwner(Application.primaryStage);
            EventBusFactory.getEventBus().post(drawData);
            this.variationsWindow.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*----------------------------------*
     *     Elements event listeners     *
     *----------------------------------*/
    @FXML
    protected void onHundredDrawsClicked() {
        drawsHundred.requestFocus();
        this.draws = 100;

        play();
    }

    @FXML
    protected void onThousandDrawsClicked() {
        drawsThousand.requestFocus();
        this.draws = 1000;

        play();
    }

    @FXML
    protected void onMillionDrawsClicked() {
        drawsMillion.requestFocus();
        this.draws = (int) Math.pow(10, 6);

        play();
    }

    @FXML
    protected void onCustomDrawsClicked() {
        drawsCustom.requestFocus();
        try {
            this.inputDataWindow = this.createInputWindow();
            this.inputDataWindow.setInitOwner(Application.primaryStage);
            this.inputDataWindow.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*-------------------------------------*
     *          Eventbus handlers          *
     *-------------------------------------*/
    @Subscribe
    public void handleInputCustomData(StringEvent event) {
        if (!event.getType().equals(StringEvent.EventTypes.HANDLE_CONTINUOUS_INPUT)) {
            return;
        }

        var data = event.getValue();
        this.draws = Integer.parseInt(data.split(" ")[0]);
        this.a = Double.parseDouble(data.split(" ")[1]);
        this.b = Double.parseDouble(data.split(" ")[2]);
        this.inputDataWindow.close();
        play();
    }


    /*--------------------------------------*
     *          Initialize actions          *
     *--------------------------------------*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventBusFactory.getEventBus().register(this);
        CustomWindow.setUnfocusToAllElements(this);
        initializeElements();
        setStars();
        updateElementsForStars();

        this.uniformCroupierService = new UniformCroupierService(histogram);
    }

    private void initializeElements() {
        // Sidebar menu
        this.activeButton.setPrefHeight(70);
        this.activeButton.setPrefWidth(155);
        this.activeButton.setLayoutX(-30);
        this.activeButton.setLayoutY(240);

        this.activeButtonText.setLayoutX(-2);
        this.activeButtonText.setLayoutY(267);

        // Current window elements
        this.version.setLayoutX(150);
        this.version.setLayoutY(10);
        this.versionCircle.setLayoutX(135);
        this.versionCircle.setLayoutY(17);

        this.overviewButton.setLayoutX(150);
        this.overviewButton.setLayoutY(50);

        this.welcomeBackText.setLayoutX(150);
        this.welcomeBackText.setLayoutY(100);

        this.checkOverview.setLayoutX(150);
        this.checkOverview.setLayoutY(125);

        // Histogram elements
        this.drawsText.setLayoutX(100);
        this.drawsText.setLayoutY(250);

        this.drawsHundred.setLayoutX(100);
        this.drawsHundred.setLayoutY(290);

        this.drawsThousand.setLayoutX(200);
        this.drawsThousand.setLayoutY(290);

        this.drawsMillion.setLayoutX(300);
        this.drawsMillion.setLayoutY(290);

        this.drawsCustom.setLayoutX(400);
        this.drawsCustom.setLayoutY(290);

        this.histogram.setLayoutX(80);
        this.histogram.setLayoutY(350);
    }

    // установка звезд вверху
    private void setStars() {
        var image = new Image("file:./src/main/resources/com/oryreq/montecarlomethod/star.png");

        double opacity = 1;

        // первые две линии меньше слева и справа из-за закругления окна
        int lineOffset = 0;
        for (int y = -1; y <= 5; y += 6) {
            for (int x = 17 - 2 * lineOffset; x < 1063 + lineOffset; x += 6) {
                ImageView imageView = new ImageView(image);
                imageView.setX(x);
                imageView.setY(y);
                imageView.setFitHeight(image.getHeight());
                imageView.setFitWidth(image.getWidth());
                activeWindow.getChildren().add(imageView);
            }
            lineOffset += 6;
        }

        for (int y = 11; y < 500; y += 6) {
            if (y > 250) {
                opacity -= 0.05;
            }
            for (int x = -1; x < 1074; x += 6) {
                ImageView imageView = new ImageView(image);
                imageView.setX(x);
                imageView.setY(y);
                imageView.setFitHeight(image.getHeight());
                imageView.setFitWidth(image.getWidth());
                imageView.setOpacity(opacity);
                activeWindow.getChildren().add(imageView);
            }
        }

    }

    private void updateElementsForStars() {
        var fields = this.getClass().getDeclaredFields();
        for (var field : fields) {
            if (Modifier.isProtected(field.getModifiers())) {
                field.setAccessible(true);
                try {
                    Node node = (Node) field.get(this);
                    activeWindow.getChildren().remove(node);
                    activeWindow.getChildren().add(node);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private CustomWindow createInputWindow() throws Exception {
        var resource = Application.class.getResource("windows/uniform_croupier/subwindows/input-distribution-data.fxml");
        var styles = Application.class.getResource("styles.css");

        var stage = new Stage();
        stage.setTitle("Input distribution data");
        stage.getIcons().add(new Image("file:./src/main/resources/com/oryreq/montecarlomethod/logo.png"));
        //this.stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        return new CustomWindow(resource, styles, stage);
    }

    private CustomWindow createVariationsWindow() throws Exception {
        var resource = Application.class.getResource("windows/show_variations/show-variations.fxml");
        var styles = Application.class.getResource("styles.css");

        var stage = new Stage();
        stage.setTitle("Variations");
        stage.getIcons().add(new Image("file:./src/main/resources/com/oryreq/montecarlomethod/logo.png"));
        //this.stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        return new CustomWindow(resource, styles, stage, 500, 350);
    }

}
