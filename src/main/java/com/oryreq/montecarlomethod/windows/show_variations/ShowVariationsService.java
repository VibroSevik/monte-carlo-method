package com.oryreq.montecarlomethod.windows.show_variations;

import com.oryreq.montecarlomethod.models.DrawData;
import com.oryreq.montecarlomethod.services.TableService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.List;

public class ShowVariationsService {

    private TableService<DrawData, String> tableService;

    private final List<String> columnNames = List.of("Number", "Probability", "Count of drops", "Frequency of drops");
    private final List<String> propertyNames = List.of("number", "probability", "dropsCount", "frequency");


    public ShowVariationsService(TableView<DrawData> table) {
        this.tableService = new TableService<>(table);
        this.tableService.setMaxWidth(500);
        this.tableService.setMaxHeight(350);
    }

    public void clearTable() {
        this.tableService.clear();
    }

    public void buildTable(List<DrawData> drawData) {
        ObservableList<DrawData> data = FXCollections.observableArrayList(drawData);
        this.tableService.build(data, columnNames, propertyNames);
    }

}
