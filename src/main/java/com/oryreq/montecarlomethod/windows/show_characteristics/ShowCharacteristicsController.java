package com.oryreq.montecarlomethod.windows.show_characteristics;

import com.google.common.eventbus.Subscribe;
import com.oryreq.montecarlomethod.models.CharacteristicsData;
import com.oryreq.montecarlomethod.services.EventBusFactory;
import com.oryreq.montecarlomethod.windows.CustomWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowCharacteristicsController implements Initializable {

    @FXML
    private TableView<CharacteristicsData> characteristics;

    private ShowCharacteristicsService service;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventBusFactory.getEventBus().register(this);
        CustomWindow.setUnfocusToAllElements(this);
        this.characteristics.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        this.service = new ShowCharacteristicsService(characteristics);
    }

    @Subscribe
    public void handleDrawData(CharacteristicsData characteristicsData) {
        this.service.clearTable();
        this.service.buildTable(characteristicsData);
    }
}
