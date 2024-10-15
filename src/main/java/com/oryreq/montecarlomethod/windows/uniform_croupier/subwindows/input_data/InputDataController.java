package com.oryreq.montecarlomethod.windows.uniform_croupier.subwindows.input_data;

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
    private TextField intervalBegin;

    @FXML
    private TextField intervalEnd;


    @FXML
    protected void onSubmitButtonClick() {
        int draws = Integer.parseInt(drawsCount.getText());
        double begin = Double.parseDouble(intervalBegin.getText());
        double end = Double.parseDouble(intervalEnd.getText());
        // close window and post data
        var event = new StringEvent(StringEvent.EventTypes.HANDLE_CONTINUOUS_INPUT, draws + " " + begin + " " + end);
        EventBusFactory.getEventBus().post(event);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        intervalBegin.setText("-3");
        intervalEnd.setText("-1");
    }

}
