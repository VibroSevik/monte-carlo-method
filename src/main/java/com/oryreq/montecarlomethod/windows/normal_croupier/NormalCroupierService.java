package com.oryreq.montecarlomethod.windows.normal_croupier;

import com.oryreq.montecarlomethod.models.DrawData;
import com.oryreq.montecarlomethod.models.Interval;
import com.oryreq.montecarlomethod.services.HistogramService;
import com.oryreq.montecarlomethod.services.MonteCarloCroupier;
import com.oryreq.montecarlomethod.services.TooltipsInstaller;
import com.oryreq.montecarlomethod.utils.ExtendedMath;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.*;


public class NormalCroupierService {

                        /*----------------------------*
                         *          Services          *
                         *----------------------------*/
    private final MonteCarloCroupier croupier = new MonteCarloCroupier();
    private final TooltipsInstaller tooltipsInstaller = new TooltipsInstaller();
    private final HistogramService<String, Double> histogramService;



    private Double yPos;
    private double x1, x2, y1, y2, lastDraws;
    private final List<Integer> currentMarks = new ArrayList<>();

    public NormalCroupierService() {
        this.histogramService = null;
    }

    public NormalCroupierService(StackedBarChart<String, Double> histogram) {
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
    public List<DrawData> getDrawData(double mathExpectation, double standardDeviation, int drawsCount) {
        var accumulator = 12;

        var intervalPoints = getIntervalPoints(-6, 6, drawsCount);
        var results = croupier.standardPlay(drawsCount, intervalPoints, accumulator, mathExpectation, standardDeviation);
        return prepareDrawData(results, drawsCount);
    }

    private List<Interval> getIntervalPoints(double begin, double end, int drawsCount) {
        int intervalsCount = (int) Math.floor(Math.log(drawsCount));
        return new Interval(begin, end)
                .uniformPartition(intervalsCount);
    }

    private List<DrawData> prepareDrawData(Map<Interval, Integer> data, int drawsCount) {
        var preparedData = new ArrayList<DrawData>();

        data.forEach((number, dropsCount) -> {
            double frequency = dropsCount.doubleValue() / drawsCount;
            preparedData.add(new DrawData(number, 0, dropsCount, frequency));
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
        var dropsCount = drawData.stream().map(item -> Double.parseDouble(item.getDropsCount())).toList();
        this.histogramService.build(histogramName, numbers, dropsCount);

        this.histogramService.addColumnsBorder(1, "white");

        changeXAxisScale(drawData, activeWindow);
        var maxDropsCount = dropsCount.stream().max(Comparator.naturalOrder()).get().intValue();
        changeYAxisScale(maxDropsCount);
        fillHistogramTooltips();

        //drawLine(activeWindow, draws);
    }

    private void clearOldHistogram(AnchorPane activeWindow) {
        this.histogramService.clear();

        this.currentMarks.clear();

        var oldLabels = activeWindow.getChildren().stream().filter(node -> node instanceof Label).filter(label -> label.getId().contains("label")).toList();
        activeWindow.getChildren().removeAll(oldLabels);

        var oldPath = activeWindow.getChildren().stream().filter(node -> node instanceof Path).toList();
        activeWindow.getChildren().removeAll(oldPath);

        var oldLines = activeWindow.getChildren().stream().filter(node -> node instanceof Line).toList();
        activeWindow.getChildren().removeAll(oldLines);

        var oldButtons = activeWindow.getChildren().stream().filter(node -> node instanceof Button).toList();
        activeWindow.getChildren().removeAll(oldButtons);
    }

    private void fillHistogramTooltips() {
        this.tooltipsInstaller.installHistogramTooltips2(this.histogramService.getHistogram());
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

            var label = new Label(labelText.substring(0, Math.min(labelText.length(), 4)));
            label.setId("label" + i);
            label.setLayoutX(xPos);
            label.setLayoutY(yPos);
            label.setStyle("-fx-text-fill: white; -fx-font-size: 10");
            labels.add(label);
            xPos -= (columnWidth - offset);
        }
        var last = labels.get(labels.size() - 1);
        last.setLayoutX(last.getLayoutX() - 4);

        var labelText = drawData.get(0).getNumber().split(",")[0].split("\\(")[1];

        var label = new Label(labelText);
        label.setId("label");
        label.setLayoutX(xPos);
        label.setLayoutY(yPos);
        label.setStyle("-fx-text-fill: white; -fx-font-size: 10");
        labels.add(label);
        return labels;
    }

    private void changeYAxisScale(int drawsCount) {
        double upperBound = ExtendedMath.roundToHundreds(drawsCount);
        double tickUnit = upperBound / 10;
        this.histogramService.changeYAxisScale(upperBound, tickUnit);
    }

    // чем больше y label в оси тем больше сдвиг самой гистограммы, каждый знак 5 пикселей + 1 пробел
    private double getLeftOffsetX(int draws) {
        return ExtendedMath.getDigitsCount(draws / 2) * (5 + 1);
    }

    public void drawLine(AnchorPane activeWindow, int draws) {
        var chart = this.histogramService.getHistogram().getData().get(0).getChart();

        var leftOffsetX = getLeftOffsetX(draws);
        var beginX = (int) (20.0 + 130 + 5 + 5 + leftOffsetX);
        var endX = (int) (28.0 + 130 + chart.getWidth() - 40);

        double beginY = 319 + 335;

        double lastX = beginX;
        double originalX = -6;

        var path = new Path();
        var points = new ArrayList<PathElement>();
        for (double histogramX = beginX; histogramX < endX; histogramX += 0.1) {
            if (histogramX - lastX > 11) {
                lastX = histogramX;
                originalX += 1 /3d;
            }
            var y = ExtendedMath.normalDistributionDensity(-0.5, 1, originalX);
            y = 654 - y * 600;
            var moveTo = new MoveTo(histogramX, beginY);
            var lineTo = new LineTo(histogramX, y);
            points.add(moveTo);
            points.add(lineTo);
            beginY = y;
            path.getElements().addAll(moveTo, lineTo);
        }

        var pointsGlassed = new ArrayList<PathElement>();
        for (int i = 0; i < points.size() - 3; i++) {
            if (points.get(i) instanceof MoveTo) {
                var moveTo = (MoveTo) points.get(i);
                var lineTo = (LineTo) points.get(i + 1);

                if (moveTo.getX() == lineTo.getX()) {
                    pointsGlassed.add(moveTo);
                    var moveTo2 = (MoveTo) points.get(i + 2);
                    var k = (moveTo.getY() - lineTo.getY()) / (moveTo2.getX() - moveTo.getX());
                    for (double x = moveTo.getX(); x < moveTo2.getX(); x += 0.2) {
                        var y = k * x;
                        pointsGlassed.add(new LineTo(x, moveTo.getY() - y));
                        pointsGlassed.add(new MoveTo(x + 0.1, moveTo.getY() - k * (x + 0.1)));
                    }
                }

                if (moveTo.getY() == lineTo.getY()) {

                }

            }

            if (points.get(i) instanceof LineTo) {
                var lineTo = (LineTo) points.get(i);
                var moveTo = (MoveTo) points.get(i + 1);

                if (lineTo.getX() == moveTo.getX()) {

                }

                if (lineTo.getY() == moveTo.getY()) {

                }
            }
        }
        var path2 = new Path();
        path2.getElements().addAll(pointsGlassed);
        path2.setStyle("-fx-stroke: red; -fx-stroke-width: 2px");
        path.setStyle("-fx-stroke: red; -fx-stroke-width: 2px");
        activeWindow.getChildren().add(path2);
    }

}
