package com.oryreq.montecarlomethod.windows.binomial_croupier.subwindows.input_data;

import com.oryreq.montecarlomethod.models.StringEvent;
import com.oryreq.montecarlomethod.services.EventBusFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class InputDataController implements Initializable {

    @FXML
    private TextField numbersCount;

    @FXML
    private TextField binomialProbability;

    @FXML
    private TextField drawsCount;


    @FXML
    protected void onSubmitButtonClick() {
        int n = Integer.parseInt(numbersCount.getText());
        double p = Double.parseDouble(binomialProbability.getText());
        int draws = Integer.parseInt(drawsCount.getText());
        // close window and post data
        var event = new StringEvent(StringEvent.EventTypes.HANDLE_DISCRETE_INPUT, n + " " + p + " " + draws);
        EventBusFactory.getEventBus().post(event);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numbersCount.setText("11");
        binomialProbability.setText("0.07");
    }

}
