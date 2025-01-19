package com.oryreq.montecarlomethod.windows.show_characteristics;

import com.oryreq.montecarlomethod.models.CharacteristicsData;
import com.oryreq.montecarlomethod.services.TableService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.List;

public class ShowCharacteristicsService {

    private final TableService<CharacteristicsData, String> tableService;

    private final List<String> columnNames =
            List.of("Math expectation", "Bias variance", "Unbias variance", "Bias standard deviation", "Unbias standard deviation");

    private final List<String> propertyNames =
            List.of("mathExpectation", "biasVariance", "unbiasVariance", "biasStandardDeviation", "unbiasStandardDeviation");


    public ShowCharacteristicsService(TableView<CharacteristicsData> table) {
        this.tableService = new TableService<>(table);
        this.tableService.setMaxWidth(700);
        this.tableService.setMaxHeight(50);
    }

    public void clearTable() {
        this.tableService.clear();
    }

    public void buildTable(CharacteristicsData characteristicsData) {
        ObservableList<CharacteristicsData> data = FXCollections.observableArrayList(characteristicsData);
        this.tableService.build(data, columnNames, propertyNames);
    }

}
