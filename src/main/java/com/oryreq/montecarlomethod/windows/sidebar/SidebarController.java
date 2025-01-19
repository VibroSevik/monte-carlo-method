package com.oryreq.montecarlomethod.windows.sidebar;

import com.oryreq.montecarlomethod.models.StringEvent;
import com.oryreq.montecarlomethod.services.EventBusFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ResourceBundle;


public class SidebarController implements Initializable {

    @FXML
    protected ImageView logo;

    @FXML
    protected AnchorPane sidebarContainer;

    @FXML
    private ImageView binomialDistribution;

    @FXML
    private ImageView uniformDistribution;

    @FXML
    private ImageView normalDistribution;

    @FXML
    private ImageView error;

    @FXML
    protected Pane activeButton;

    @FXML
    private Label activeButtonText;

    @FXML
    protected Pane binomialDistributionContainer;

    @FXML
    protected Pane uniformDistributionContainer;

    @FXML
    protected Pane normalDistributionContainer;

    @FXML
    protected Pane errorContainer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var image = new Image(String.valueOf(getClass().getResource("/logo.png")));
        //var image = new Image("file:./src/main/resources/com/oryreq/montecarlomethod/logo.png");
        logo.setImage(image);
        logo.setFitHeight(40);
        logo.setFitWidth(40);
        logo.setLayoutX(20);
        logo.setLayoutY(20);

        var image2 = new Image(String.valueOf(getClass().getResource("/binomial_distribution.png")));
        //var image2 = new Image("file:./src/main/resources/com/oryreq/montecarlomethod/binomial_distribution.png");
        binomialDistribution.setImage(image2);
        binomialDistribution.setFitHeight(30);
        binomialDistribution.setFitWidth(30);
        binomialDistribution.setX(25);
        binomialDistribution.setY(175);

        binomialDistributionContainer.setPrefHeight(70);
        binomialDistributionContainer.setPrefWidth(80);
        binomialDistributionContainer.setLayoutX(1);
        binomialDistributionContainer.setLayoutY(160);

        activeButton.setMinHeight(70);
        activeButton.setMinWidth(100);
        activeButton.setLayoutX(1);
        activeButton.setLayoutY(160);

        activeButtonText.setLayoutX(60);
        activeButtonText.setLayoutY(187);

        var uniformDistributionImage = new Image(String.valueOf(getClass().getResource("/uniform_distribution.png")));
        //var uniformDistributionImage = new Image("file:./src/main/resources/com/oryreq/montecarlomethod/uniform_distribution.png");
        uniformDistribution.setImage(uniformDistributionImage);
        uniformDistribution.setFitHeight(35);
        uniformDistribution.setFitWidth(35);
        uniformDistribution.setX(25);
        uniformDistribution.setY(255);

        uniformDistributionContainer.setPrefHeight(70);
        uniformDistributionContainer.setPrefWidth(80);
        uniformDistributionContainer.setLayoutX(1);
        uniformDistributionContainer.setLayoutY(240);

        var normalDistributionImage = new Image(String.valueOf(getClass().getResource("/normal_distribution.png")));
        normalDistribution.setImage(normalDistributionImage);
        normalDistribution.setFitHeight(35);
        normalDistribution.setFitWidth(35);
        normalDistribution.setX(25);
        normalDistribution.setY(335);

        normalDistributionContainer.setPrefHeight(70);
        normalDistributionContainer.setPrefWidth(80);
        normalDistributionContainer.setLayoutX(1);
        normalDistributionContainer.setLayoutY(320);

        var errorImage = new Image(String.valueOf(getClass().getResource("/error.png")));
        error.setImage(errorImage);
        error.setFitHeight(35);
        error.setFitWidth(35);
        error.setX(25);
        error.setY(410);

        errorContainer.setPrefHeight(70);
        errorContainer.setPrefWidth(80);
        errorContainer.setLayoutX(1);
        errorContainer.setLayoutY(400);

        var fields = this.getClass().getDeclaredFields();
        for (var field : fields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
                try {
                    Node node = (Node) field.get(this);
                    sidebarContainer.getChildren().remove(node);
                    sidebarContainer.getChildren().add(node);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

    @FXML
    public void binomialDistributionOnClick() {
        activeButton.setLayoutY(160);
        activeButtonText.setLayoutX(60);
        activeButtonText.setLayoutY(187);
        activeButtonText.setText("BIN");
        var event = new StringEvent(StringEvent.EventTypes.OPEN_BINOMIAL_WINDOW, "");
        EventBusFactory.getEventBus().post(event);

    }

    @FXML
    public void uniformDistributionOnClick() {
        activeButton.setLayoutY(240);
        activeButtonText.setLayoutX(60);
        activeButtonText.setLayoutY(267);
        activeButtonText.setText("UNI");
        var event = new StringEvent(StringEvent.EventTypes.OPEN_UNIFORM_WINDOW, "");
        EventBusFactory.getEventBus().post(event);
    }

    @FXML
    public void normalDistributionOnClick() {
        activeButton.setLayoutY(320);
        activeButtonText.setLayoutX(60);
        activeButtonText.setLayoutY(347);
        activeButtonText.setText("NOR");
        var event = new StringEvent(StringEvent.EventTypes.OPEN_NORMAL_WINDOW, "");
        EventBusFactory.getEventBus().post(event);
    }

    @FXML
    public void errorOnClick() {
        activeButton.setLayoutY(400);
        activeButtonText.setLayoutX(60);
        activeButtonText.setLayoutY(427);
        activeButtonText.setText("ERR");
        var event = new StringEvent(StringEvent.EventTypes.OPEN_ERROR_WINDOW, "");
        EventBusFactory.getEventBus().post(event);
    }
}
