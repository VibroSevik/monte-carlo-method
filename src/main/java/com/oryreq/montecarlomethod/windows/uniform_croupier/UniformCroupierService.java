package com.oryreq.montecarlomethod.windows.uniform_croupier;

import com.oryreq.montecarlomethod.models.DrawData;
import com.oryreq.montecarlomethod.models.Interval;
import com.oryreq.montecarlomethod.services.HistogramService;
import com.oryreq.montecarlomethod.services.MonteCarloCroupier;
import com.oryreq.montecarlomethod.services.TooltipsInstaller;
import com.oryreq.montecarlomethod.utils.ExtendedMath;

import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UniformCroupierService {

                        /*----------------------------*
                         *          Services          *
                         *----------------------------*/
    private final MonteCarloCroupier croupier = new MonteCarloCroupier();
    private final TooltipsInstaller tooltipsInstaller = new TooltipsInstaller();
    private final HistogramService<String, Integer> histogramService;



    private Double yPos;

    public UniformCroupierService(StackedBarChart<String, Integer> histogram) {
        this.histogramService = new HistogramService<>(histogram)
                .setAutoRanging(false)
                .setLowerBound(0)
                .setUpperBound(500)
                .setTickUnit(50)
                .setMaxHeight(500)
                .setMaxWidth(500)
                .setCategoryGap(0)
                .setMargins(0);
    }


                        /*------------------------------------*
                         *          DrawData actions          *
                         *------------------------------------*/
    public List<DrawData> getDrawData(double a, double b, int drawsCount) {
        var intervalPoints = getIntervalPoints(a, b, drawsCount);
        double probability = 1.0 / intervalPoints.size();

        var results = croupier.continuousPlay(drawsCount, intervalPoints);
        return prepareDrawData(results, probability, drawsCount);
    }

    private List<Interval> getIntervalPoints(double a, double b, int drawsCount) {
        int intervalsCount = (int) Math.floor(Math.log(drawsCount));
        return new Interval(a, b)
                .uniformPartition(intervalsCount);
    }

    private List<DrawData> prepareDrawData(Map<Interval, Integer> data, double probability, int drawsCount) {
        var preparedData = new ArrayList<DrawData>();

        data.forEach((interval, dropsCount) -> {
            double frequency = dropsCount.doubleValue() / drawsCount;
            var roundedInterval = interval.applyRound(2);
            preparedData.add(new DrawData(roundedInterval, probability, dropsCount, frequency));
        });

        return preparedData;
    }


                        /*-------------------------------------*
                         *          Histogram actions          *
                         *-------------------------------------*/
    public void buildHistogram(int draws, List<DrawData> drawData, AnchorPane activeWindow) {
        clearOldHistogram(activeWindow);

        var histogramName = "Count of drops";
        var numbers = drawData.stream().map(item -> item.getNumber()).toList();
        var dropsCount = drawData.stream().map(item -> Integer.parseInt(item.getDropsCount())).toList();
        this.histogramService.build(histogramName, numbers, dropsCount);

        this.histogramService.addColumnsBorder(1, "white");

        changeXAxisScale(drawData, activeWindow);
        changeYAxisScale(draws);
        fillHistogramTooltips();
    }

    private void clearOldHistogram(AnchorPane activeWindow) {
        this.histogramService.clear();

        var oldLabels = activeWindow.getChildren().stream().filter(node -> node instanceof Label).filter(label -> label.getId().contains("label")).toList();
        activeWindow.getChildren().removeAll(oldLabels);
    }

    private void fillHistogramTooltips() {
        this.tooltipsInstaller.installHistogramTooltips(this.histogramService.getHistogram());
    }

    private void changeXAxisScale(List<DrawData> drawData, AnchorPane activeWindow) {
        this.histogramService.hideXAxis();

        double xPos = this.histogramService.getHistogram().getLayoutX() + this.histogramService.getHistogram().getWidth() - 15 - 12;
        if (yPos == null) {
            yPos = 350 + this.histogramService.getHistogram().getYAxis().getHeight() - 10;
        }

        var labels = createXAxisLabels(xPos, drawData);
        activeWindow.getChildren().addAll(labels);
    }

    private List<Label> createXAxisLabels(double xPos, List<DrawData> drawData) {
        double columnWidth = (this.histogramService.getHistogram().getWidth()) / this.histogramService.getColumns().size();
        var offset = columnWidth / 6.9;

        var labels = new ArrayList<Label>();

        for (int i = 0; i < drawData.size(); i++) {
            var labelText = drawData.get(drawData.size() - 1 - i).getNumber().split(",")[1].split("\\)")[0];

            var label = new Label(labelText);
            label.setId("label" + i);
            label.setLayoutX(xPos);
            label.setLayoutY(yPos);
            label.setStyle("-fx-text-fill: white; -fx-font-size: 10");
            labels.add(label);
            xPos -= (columnWidth - offset);
        }
        var last = labels.get(labels.size() - 1);
        last.setLayoutX(last.getLayoutX() - 4);
        return labels;
    }

    private void changeYAxisScale(int drawsCount) {
        int intervalsCount = (int) Math.floor(Math.log(drawsCount));
        int upperBound = ExtendedMath.roundToHundreds(drawsCount / intervalsCount);
        int tickUnit = upperBound / 10;
        this.histogramService.changeYAxisScale(upperBound, tickUnit);
    }

}
