package com.oryreq.montecarlomethod.services;

import com.oryreq.montecarlomethod.Controller;
import com.oryreq.montecarlomethod.models.DrawData;
import com.oryreq.montecarlomethod.utils.ExtendedMath;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class realizes object so-called <strong>Service</strong>. <br>
 * It is class-service for <strong>Controller</strong>. <br>
 * It is used to build histogram and table through <strong>HistogramService</strong> and <strong>TableService</strong>. <br>
 * Also can draw some numbers by Monte-Carlo method through <strong>MonteCarloCroupier</strong>. <br>
 * Also can manage some elements in controller like a reset focus from all elements.
 *
 * @Date: 29.09.2024
 * @Author: Vsevolod @Oryreq Ashihmin
 */
public class Service {

    private final MonteCarloCroupier croupier = new MonteCarloCroupier();
    private final HistogramService<String, Integer> histogramService;
    private final TableService<DrawData, Double> tableService;


    public Service(BarChart<String, Integer> histogram, TableView<DrawData> tableView) {
        this.histogramService = new HistogramService<>(histogram)
                .setAutoRanging(false)
                .setLowerBound(0)
                .setUpperBound(500)
                .setTickUnit(50);

        this.tableService = new TableService<>(tableView);
        this.tableService.setMaxWidth(250);
        this.tableService.setMaxHeight(250);
    }


    public Map<String, Integer> getNumbers(int n, double p, int draws) {
        List<Double> probabilities = IntStream.range(0, n)
                .mapToObj(m -> ExtendedMath.binomialDistribution(m, n, p))
                .toList();

        List<Double> points = new ArrayList<>();
        points.add(probabilities.get(0));
        for (int i = 1; i < probabilities.size(); i++) {
            double point = points.get(points.size() - 1) + probabilities.get(i);
            points.add(point);
        }

        var roundedDigits = 5;
        var numbers = this.croupier.play(draws, points, roundedDigits);
        return prepareHistogramData(numbers);
    }

    public void buildHistogram(Map<String, Integer> data) {
        var histogramName = "Count of drops";
        this.histogramService.build(histogramName, data);
        var columns = this.histogramService.getColumns();
        fillHistogramTooltips(columns);
    }

    public void fillHistogramTooltips(ObservableList<XYChart.Data<String, Integer>> columns) {
        columns.forEach(column -> {
            Tooltip.install(column.getNode(), new Tooltip(column.getXValue() + "\n" + column.getYValue()));
        });
    }

    public void buildTable(Map<String, Integer> numbers) {
        ObservableList<DrawData> items = FXCollections.observableArrayList(
                numbers.keySet()
                        .stream()
                        .map(number -> new DrawData(number,(numbers.get(number))))
                        .toList()
        );

        var columns = List.of("Number", "Count of drops");
        var propertyNames = List.of("number", "dropsCount");
        this.tableService.build(items, columns, propertyNames);
    }

    private Map<String, Integer> prepareHistogramData(Map<Double, Integer> points) {
        var entries = points.entrySet().stream();
        var preparedData = entries.collect(
                Collectors.toMap(
                        entry -> String.valueOf(entry.getKey()),
                        entry -> entry.getValue())
        );
        return new TreeMap<>(preparedData);
    }

    public void setUnfocusToAllElements(Field[] fields, Controller controller) {
        Arrays.stream(fields).forEach(field -> {
            field.setAccessible(true);
            try {
                var object = field.get(controller);
                if (object instanceof Node) {
                    ((Node) object).setFocusTraversable(false);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
