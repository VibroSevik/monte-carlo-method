package com.oryreq.montecarlomethod.windows.normal_croupier.subwindows.input_data;

import com.oryreq.montecarlomethod.models.StringEvent;
import com.oryreq.montecarlomethod.services.EventBusFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class InputDataController implements Initializable {

    @FXML
    private TextField drawsCount;

    @FXML
    private TextField mathExpectation;

    @FXML
    private TextField standardError;


    @FXML
    protected void onSubmitButtonClick() {
        int draws = Integer.parseInt(drawsCount.getText());
        double mathExpectation = Double.parseDouble(this.mathExpectation.getText());
        double standardError = Double.parseDouble(this.standardError.getText());
        // close window and post data
        var event = new StringEvent(StringEvent.EventTypes.HANDLE_NORMAL_INPUT, draws + " " + mathExpectation + " " + standardError);
        EventBusFactory.getEventBus().post(event);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mathExpectation.setText("-0.5");
        standardError.setText("1");
    }

}
