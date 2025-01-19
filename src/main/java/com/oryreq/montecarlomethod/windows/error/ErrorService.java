package com.oryreq.montecarlomethod.windows.error;

import com.oryreq.montecarlomethod.models.ErrorData;
import com.oryreq.montecarlomethod.services.TableService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.List;

public class ErrorService {

    private final TableService<ErrorData, String> tableService;

    private final List<String> columnNames = List.of("Всего разыграно", "Ошибка 1го рода", "Ошибка 2го рода", "Частота ошибки");
    private final List<String> propertyNames = List.of("playedAll", "firstErrorCount", "secondErrorCount", "errorFrequency");


    public ErrorService(TableView<ErrorData> table) {
        this.tableService = new TableService<>(table);
        this.tableService.setMaxWidth(500);
        this.tableService.setMaxHeight(350);
    }

    public void clearTable() {
        this.tableService.clear();
    }

    public void buildTable(ErrorData errorData) {
        ObservableList<ErrorData> data = FXCollections.observableArrayList(errorData);
        this.tableService.build(data, columnNames, propertyNames);
    }

}
