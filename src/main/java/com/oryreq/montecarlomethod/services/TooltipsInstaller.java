package com.oryreq.montecarlomethod.services;

import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;

import java.util.stream.IntStream;


public class TooltipsInstaller {

    public TooltipsInstaller installHistogramTooltips(XYChart<String, Integer> histogram) {
        var columns = histogram.getData().get(0).getData();
        var text = columns.stream().map(column -> column.getXValue() + "\n" + column.getYValue()).toList();
        IntStream.range(0, columns.size()).forEach(i -> {
            Tooltip.install(columns.get(i).getNode(), new Tooltip(text.get(i)));
        });
        return this;
    }

    public TooltipsInstaller installHistogramTooltips2(XYChart<String, Double> histogram) {
        var columns = histogram.getData().get(0).getData();
        var text = columns.stream().map(column -> column.getXValue() + "\n" + column.getYValue()).toList();
        IntStream.range(0, columns.size()).forEach(i -> {
            Tooltip.install(columns.get(i).getNode(), new Tooltip(text.get(i)));
        });
        return this;
    }

}
