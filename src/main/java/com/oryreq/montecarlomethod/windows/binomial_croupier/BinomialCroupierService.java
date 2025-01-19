package com.oryreq.montecarlomethod.windows.binomial_croupier;

import com.oryreq.montecarlomethod.models.DrawData;
import com.oryreq.montecarlomethod.services.HistogramService;
import com.oryreq.montecarlomethod.services.MonteCarloCroupier;
import com.oryreq.montecarlomethod.services.TooltipsInstaller;
import com.oryreq.montecarlomethod.utils.ExtendedMath;

import javafx.scene.chart.StackedBarChart;

import java.util.*;
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
public class BinomialCroupierService {

                        /*----------------------------*
                         *          Services          *
                         *----------------------------*/
    private final MonteCarloCroupier croupier = new MonteCarloCroupier();
    private final TooltipsInstaller tooltipInstaller = new TooltipsInstaller();
    private final HistogramService<String, Integer> histogramService;


    public BinomialCroupierService(StackedBarChart<String, Integer> histogram) {
        this.histogramService = new HistogramService<>(histogram)
                .setAutoRanging(false)
                .setLowerBound(0)
                .setUpperBound(500)
                .setTickUnit(50)
                .setMaxHeight(500)
                .setMaxWidth(500);
    }


                        /*------------------------------------*
                         *          DrawData actions          *
                         *------------------------------------*/
    public List<DrawData> getDrawData(int n, double p, int drawsCount) {
        var values = IntStream.range(0, n + 1).boxed().toList();

        List<Double> probabilities = values.stream()
                                            .map(value -> ExtendedMath.binomialDistribution(value, n, p))
                                              //.map(value -> ExtendedMath.puassonDistribution(2.88, value))
                                            .toList();

        var points = getIntervalPoints(probabilities);
        var numbers = this.croupier.discretePlay(drawsCount, points, values);
        return prepareDrawData(numbers, probabilities, drawsCount);
    }

    private List<Double> getIntervalPoints(List<Double> probabilities) {
        List<Double> points = new ArrayList<>();
        points.add(0d);
        for (var probability : probabilities) {
            double point = points.get(points.size() - 1) + probability;
            points.add(point);
        }
        points.add(1d);
        return points;
    }

    private List<DrawData> prepareDrawData(Map<Integer, Integer> points, List<Double> probabilities, int drawsCount) {
        var preparedData = new ArrayList<DrawData>();
        for (int number = 0; number < points.size(); number++) {
            var probability = probabilities.get(number);
            var dropsCount = points.get(number);
            var frequency = probability / (double) drawsCount;
            preparedData.add(new DrawData(number, probability, dropsCount, frequency));
        }
        return preparedData;
    }


                        /*-------------------------------------*
                         *          Histogram actions          *
                         *-------------------------------------*/
    public void buildHistogram(int draws, List<DrawData> drawData) {
        clearOldHistogram();

        var histogramName = "Count of drops";
        var numbers = drawData.stream().map(item -> item.getNumber()).toList();
        var dropsCount = drawData.stream().map(item -> Integer.parseInt(item.getDropsCount())).toList();

        this.histogramService.build(histogramName, numbers, dropsCount);

        changeYAxisScale(draws);
        fillHistogramTooltips();
    }

    private void clearOldHistogram() {
        this.histogramService.clear();
    }

    private void fillHistogramTooltips() {
        this.tooltipInstaller.installHistogramTooltips(this.histogramService.getHistogram());
    }

    private void changeYAxisScale(int drawsCount) {
        int upperBound = ExtendedMath.roundToHundreds(drawsCount / 2);
        int tickUnit = upperBound / 10;
        this.histogramService.changeYAxisScale(upperBound, tickUnit);
    }

}
