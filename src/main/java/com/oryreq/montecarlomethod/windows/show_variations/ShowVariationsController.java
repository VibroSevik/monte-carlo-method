package com.oryreq.montecarlomethod.windows.show_variations;

import com.google.common.eventbus.Subscribe;
import com.oryreq.montecarlomethod.models.DrawData;

import com.oryreq.montecarlomethod.services.EventBusFactory;
import com.oryreq.montecarlomethod.windows.CustomWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowVariationsController implements Initializable {

    @FXML
    private TableView<DrawData> table;

    private ShowVariationsService service;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventBusFactory.getEventBus().register(this);
        CustomWindow.setUnfocusToAllElements(this);
        this.table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        this.service = new ShowVariationsService(table);
    }

    @Subscribe
    public void handleDrawData(List<DrawData> drawData) {
        this.service.clearTable();
        this.service.buildTable(drawData);
    }

}
