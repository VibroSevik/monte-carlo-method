package com.oryreq.montecarlomethod.services;

import javafx.collections.ObservableList;
import javafx.scene.chart.*;

import java.util.List;
import java.util.stream.IntStream;


/**
 * This class realizes object so-called <strong>HistogramService</strong>. <br>
 * It is used to management histogram building. <br>
 * It has some getters and settings methods which are wrapping standard BarChart methods. <br>
 * @param <T> the type for x-axis store (usually <strong>String</strong>).
 * @param <U> the type for y-axis store.
 *
 * @Date: 29.09.2024
 * @Author: Vsevolod @Oryreq Ashihmin
 */
public class HistogramService<T, U> {

    private final StackedBarChart<T, U> histogram;


    public HistogramService(StackedBarChart<T, U> histogram) {
        this.histogram = histogram;
        //this.histogram.setStyle("-fx-border-color: red");
    }


                            /*---------------------------*
                             *     Histogram actions     *
                             *---------------------------*/
    public HistogramService<T, U> build(String name, List<T> x, List<U> y) {
        XYChart.Series<T, U> series = new XYChart.Series<>();
        series.setName(name);

        IntStream.range(0, x.size()).forEach(i -> {
            var data = new XYChart.Data<>(x.get(i), y.get(i));
            series.getData().add(data);
        });

        this.histogram.setAnimated(false);
        this.histogram.getData().add(series);
        return this;
    }

    public HistogramService<T, U> clear() {
        this.histogram.getData().clear();
        return this;
    }


                         /*--------------------------------*
                          *     Histogram data getters     *
                          *--------------------------------*/
    public ObservableList<XYChart.Data<T, U>> getColumns() {
        return this.histogram.getData().get(0).getData();
    }

    public StackedBarChart<T, U> getHistogram() {
        return this.histogram;
    }


                            /*------------------*
                             *     Settings     *
                             *------------------*/
    public HistogramService<T, U> setAutoRanging(boolean autoRanging) {
        histogram.getYAxis().setAutoRanging(autoRanging);
        return this;
    }

    public HistogramService<T, U> setLowerBound(double lowerBound) {
        if (histogram.getYAxis() instanceof NumberAxis) {
            ((NumberAxis) histogram.getYAxis()).setLowerBound(lowerBound);
            return this;
        }
        throw new IllegalArgumentException("YAxis is of type " + histogram.getYAxis().getClass().getName() + " lower bound property available for NumberAxis");
    }

    public HistogramService<T, U> setUpperBound(double upperBound) {
        if (histogram.getYAxis() instanceof NumberAxis) {
            ((NumberAxis) histogram.getYAxis()).setUpperBound(upperBound);
            return this;
        }
        throw new IllegalArgumentException("YAxis is of type " + histogram.getYAxis().getClass().getName() + " upper bound property available for NumberAxis");
    }

    public HistogramService<T, U> setTickUnit(double tickUnit) {
        if (histogram.getYAxis() instanceof NumberAxis) {
            ((NumberAxis) histogram.getYAxis()).setTickUnit(tickUnit);
            return this;
        }
        throw new IllegalArgumentException("YAxis is of type " + histogram.getYAxis().getClass().getName() + " tick unit property available for NumberAxis");
    }

    public HistogramService<T, U> setMaxHeight(double maxHeight) {
        this.histogram.setMaxHeight(maxHeight);
        return this;
    }

    public HistogramService<T, U> setMaxWidth(double maxWidth) {
        this.histogram.setMaxWidth(maxWidth);
        return this;
    }

    public HistogramService<T, U> setCategoryGap(double gap) {
        this.histogram.setCategoryGap(gap);
        return this;
    }

    public HistogramService<T, U> setMargins(double margin) {
        ((CategoryAxis) this.histogram.getXAxis()).setStartMargin(5);
        ((CategoryAxis) this.histogram.getXAxis()).setEndMargin(margin);
        return this;
    }

    public HistogramService<T, U> changeYAxisScale(int upperBound, int tickUnit) {
        return this.setUpperBound(upperBound)
                .setTickUnit(tickUnit);
    }

    public HistogramService<T, U> changeYAxisScale(double upperBound, double tickUnit) {
        return this.setUpperBound(upperBound)
                .setTickUnit(tickUnit);
    }

    public HistogramService<T, U> addColumnsBorder(int width, String color) {
        var nodes = this.getColumns().stream().map(column -> column.getNode()).toList();
        nodes.forEach(node -> node.setStyle("-fx-border-width: " + width + "; -fx-border-color: " + color + ";"));
        return this;
    }

    public HistogramService<T, U> hideXAxis() {
        this.histogram.getXAxis().setOpacity(0);
        this.histogram.getXAxis().setTickLabelsVisible(false);
        return this;
    }

}
