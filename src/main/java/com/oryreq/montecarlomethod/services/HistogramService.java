package com.oryreq.montecarlomethod.services;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.List;
import java.util.Map;
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

    private final BarChart<T, U> histogram;


    public HistogramService(BarChart<T, U> histogram) {
        this.histogram = histogram;
    }


                            /*------------------*
                             *     Builders     *
                             *------------------*/
    public HistogramService<T, U> build(String name, Map<T, U> data) {
        var x = data.keySet().stream().toList();
        var y = data.values().stream().toList();
        return build(name, x, y);
    }

    public HistogramService<T, U> build(String name, List<T> x, List<U> y) {
        XYChart.Series<T, U> series = new XYChart.Series<>();
        series.setName(name);

        IntStream.range(0, x.size()).forEach(i -> {
            var data = new XYChart.Data<T, U>(x.get(i), y.get(i));
            series.getData().add(data);
        });

        this.histogram.setAnimated(false);
        this.histogram.getData().add(series);
        return this;
    }


                     /*--------------------------------*
                      *     Histogram data getters     *
                      *--------------------------------*/
    public ObservableList<XYChart.Data<T, U>> getColumns() {
        return this.histogram.getData().get(0).getData();
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

}
