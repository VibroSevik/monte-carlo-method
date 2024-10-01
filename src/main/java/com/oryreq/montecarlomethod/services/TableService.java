package com.oryreq.montecarlomethod.services;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.IntStream;

/**
 * This class realizes object so-called <strong>TableService</strong>. <br>
 * It is used to management table building. <br>
 * It has some settings methods which are wrapping standard TableView methods. <br>
 * @param <T> the type for data store (example: class <strong>User</strong>).
 * @param <Content> the type of the content in all cells in table (usually <strong>String</strong>).
 *
 * @Date: 29.09.2024
 * @Author: Vsevolod @Oryreq Ashihmin
 */
public class TableService<T, Content> {

    private final TableView<T> table;


    public TableService(TableView<T> table) {
        this.table = table;
        this.applyStandardSettings();
    }


                            /*------------------*
                             *     Builders     *
                             *------------------*/
    public TableService<T, Content> build(ObservableList<T> items, List<String> columnNames, List<String> propertyNames) {
        this.table.setItems(items);

        IntStream.range(0, columnNames.size()).forEach(i -> {
            var column = new TableColumn<T, Content>(columnNames.get(i));
            column.setCellValueFactory(new PropertyValueFactory<>(propertyNames.get(i)));
            this.table.getColumns().add(column);
        });

        return this;
    }


                            /*------------------*
                             *     Settings     *
                             *------------------*/
    public TableService<T, Content> setMaxWidth(double maxWidth) {
        table.setMaxWidth(maxWidth);
        return this;
    }

    public TableService<T, Content> setMaxHeight(double maxHeight) {
        table.setMaxHeight(maxHeight);
        return this;
    }

    private TableService<T, Content> applyStandardSettings() {
        this.table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return this;
    }

}
