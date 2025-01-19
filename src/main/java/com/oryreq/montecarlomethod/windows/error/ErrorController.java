package com.oryreq.montecarlomethod.windows.error;

import com.oryreq.montecarlomethod.Application;
import com.oryreq.montecarlomethod.models.CharacteristicsData;
import com.oryreq.montecarlomethod.models.ErrorData;
import com.oryreq.montecarlomethod.services.EventBusFactory;
import com.oryreq.montecarlomethod.services.SampleCharacteristicsService;
import com.oryreq.montecarlomethod.windows.CustomWindow;
import com.oryreq.montecarlomethod.windows.normal_croupier.NormalCroupierService;
import com.oryreq.montecarlomethod.windows.uniform_croupier.UniformCroupierService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ResourceBundle;

public class ErrorController implements Initializable {


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
    protected TableView<ErrorData> table;


    /*----------------------------------*
     *               Data               *
     *----------------------------------*/
    private double mathExpectation = -0.5;
    private double standardError = 1;
    private int a = -3;
    private int b = -1;
    private int draws;

    private ErrorService errorService;
    private NormalCroupierService normalCroupierService;
    private UniformCroupierService uniformCroupierService;


    private void play() {
        normalCroupierService = new NormalCroupierService();
        uniformCroupierService = new UniformCroupierService();

        var normalDrawData = normalCroupierService.getDrawData(mathExpectation, standardError, draws);
        var uniformDrawData = uniformCroupierService.getDrawData(a, b, draws);

        double firstErrorCount1 = 0;
        int secondErrorCount1 = 0;
        double firstErrorCount2 = 0;
        int secondErrorCount2 = 0;
        int lineMean = (a + b) / 2;

        for (int i = 0; i < normalDrawData.size(); i++) {
            var begin = Double.parseDouble(normalDrawData.get(i).getNumber().split(",")[0].split("\\(")[1]);
            var end = Double.parseDouble(normalDrawData.get(i).getNumber().split(",")[1].split("\\)")[0]);
            double number = (begin + end) / 2;

            if (number >= a && number <= lineMean) {
                firstErrorCount1 += Integer.parseInt(normalDrawData.get(i).getDropsCount());
            }

            if (number >= a && number <= b) {
                firstErrorCount2 += Integer.parseInt(normalDrawData.get(i).getDropsCount());
            }
        }

        for (int i = 0; i < uniformDrawData.size(); i++) {
            var begin = Double.parseDouble(uniformDrawData.get(i).getNumber().split(",")[0].split("\\(")[1]);
            var end = Double.parseDouble(uniformDrawData.get(i).getNumber().split(",")[1].split("\\)")[0]);
            double number = (begin + end) / 2;

            if (number >= lineMean && number <= b) {
                secondErrorCount1 += Integer.parseInt(uniformDrawData.get(i).getDropsCount());
            }
        }

        double errorFrequency = (firstErrorCount1 + secondErrorCount1) / (2 * draws);

        var errorData = new ErrorData(2 * draws, firstErrorCount1, secondErrorCount1, errorFrequency);

        this.errorService.clearTable();
        this.errorService.buildTable(errorData);
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

        this.errorService = new ErrorService(table);
    }

    private void initializeElements() {
        // Sidebar menu
        this.activeButton.setPrefHeight(70);
        this.activeButton.setPrefWidth(155);
        this.activeButton.setLayoutX(-30);
        this.activeButton.setLayoutY(400);

        this.activeButtonText.setLayoutX(-5);
        this.activeButtonText.setLayoutY(427);

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
        this.drawsText.setLayoutX(150);
        this.drawsText.setLayoutY(250);

        this.drawsHundred.setLayoutX(150);
        this.drawsHundred.setLayoutY(290);

        this.drawsThousand.setLayoutX(250);
        this.drawsThousand.setLayoutY(290);

        this.drawsMillion.setLayoutX(350);
        this.drawsMillion.setLayoutY(290);

        //this.drawsCustom.setLayoutX(450);
        //this.drawsCustom.setLayoutY(290);

        //this.histogram.setLayoutX(130);
        //this.histogram.setLayoutY(350);
        this.table.setLayoutX(130);
        this.table.setLayoutY(350);
        this.table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    // установка звезд вверху
    private void setStars() {
        var image = new Image(String.valueOf(getClass().getResource("/star.png")));
        //var image = new Image("file:./src/main/resources/com/oryreq/montecarlomethod/star.png");

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

}
