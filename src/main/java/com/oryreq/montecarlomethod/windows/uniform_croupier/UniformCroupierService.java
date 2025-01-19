package com.oryreq.montecarlomethod.windows.uniform_croupier;

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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


public class UniformCroupierService {

                        /*----------------------------*
                         *          Services          *
                         *----------------------------*/
    private final MonteCarloCroupier croupier = new MonteCarloCroupier();
    private final TooltipsInstaller tooltipsInstaller = new TooltipsInstaller();
    private final HistogramService<String, Integer> histogramService;



    private Double yPos;
    private double x1, x2, y1, y2, lastDraws;
    private final List<Integer> currentMarks = new ArrayList<>();

    public UniformCroupierService() {
        this.histogramService = null;
    }
    
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
        var testA = ExtendedMath.convertFromBasicCoordinates(a, b, a);
        var testB = ExtendedMath.convertFromBasicCoordinates(a, b, b);

        var intervalPoints = getIntervalPoints(testA, testB, drawsCount);
        double probability = 1.0 / intervalPoints.size();

        var results = croupier.continuousPlay(drawsCount, intervalPoints);
        return prepareDrawData(a, b, results, probability, drawsCount);
    }

    private List<Interval> getIntervalPoints(double a, double b, int drawsCount) {
        int intervalsCount = (int) Math.floor(Math.log(drawsCount));
        return new Interval(a, b)
                .uniformPartition(intervalsCount);
    }

    private List<DrawData> prepareDrawData(double a, double b, Map<Interval, Integer> data, double probability, int drawsCount) {
        var preparedData = new ArrayList<DrawData>();

        data.forEach((interval, dropsCount) -> {
            double frequency = dropsCount.doubleValue() / drawsCount;
            var begin = ExtendedMath.convertToBasicCoordinates(a, b, interval.begin());
            var end = ExtendedMath.convertToBasicCoordinates(a, b, interval.end());
            //var roundedInterval = interval.applyRound(2);
            var roundedInterval = new Interval(begin, end).applyRound(2);
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

        drawLine(activeWindow, draws);
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
        int intervalsCount = (int) Math.floor(Math.log(drawsCount));
        int upperBound = ExtendedMath.roundToHundreds(drawsCount / intervalsCount);
        int tickUnit = upperBound / 10;
        this.histogramService.changeYAxisScale(upperBound, tickUnit);
        for (int i = 0; i <= upperBound; i += tickUnit) {
            this.currentMarks.add(i);
        }
    }

    public void drawLine(AnchorPane activeWindow, int draws) {
        var number = draws / this.histogramService.getColumns().size();
        var chart = this.histogramService.getHistogram().getData().get(0).getChart();
        //chart.setStyle("-fx-border-color: red");
        var children = chart.getYAxis().getChildrenUnmodifiable();
        // 18
        var coords = (Path) children.get(1);
        children = FXCollections.observableList(children.stream().filter(child -> child instanceof Text).toList());
        var lastDifference = Integer.MAX_VALUE;
        for (int i = 0; i < children.size(); i++) {
            var axisValue = this.currentMarks.get(i);
            var difference = Math.abs(number - axisValue);
            if (difference > lastDifference || (i + 1) == children.size()) {
                // линия в (a, b)
                var prevValue = this.currentMarks.get(i - 1);
                var prevDifference = number - prevValue;
                var offset = 32 * prevDifference / (this.currentMarks.get(i) - this.currentMarks.get(i - 1) - 1);
                var path = new Path();
                var begin1 = (MoveTo) coords.getElements().get(2 * (i - 1));
                var test = ExtendedMath.getDigitsCount(this.currentMarks.get(this.currentMarks.size() - 1));
                test = this.currentMarks.get(this.currentMarks.size() - 1) > 1000 ? test + 1 : test;
                begin1.setX(begin1.getX() + 130 + test * 5 + 5 + 5);
                begin1.setY(begin1.getY() + 350 + 15 - offset);
                var end1 = (LineTo) coords.getElements().get(2 * (i - 1) + 1);
                end1.setX(end1.getX() + 130 + chart.getWidth() - 40);
                end1.setY(end1.getY() + 350 + 15 - offset);
                if (lastDraws == draws) {
                    if (x1 == 0) {
                        x1 = begin1.getX();
                        x2 = end1.getX();
                        y1 = begin1.getY();
                        y2 = end1.getY();
                    }
                    begin1.setX(x1);
                    begin1.setY(y1);
                    end1.setX(x2);
                    end1.setY(y2);
                } else {
                    x1 = begin1.getX();
                    x2 = end1.getX();
                    y1 = begin1.getY();
                    y2 = end1.getY();
                }
                lastDraws = draws;
                path.getElements().addAll(begin1, end1);
                path.setStyle("-fx-stroke: #9191C4; -fx-stroke-width: 4px");
                activeWindow.getChildren().add(path);

                var button = new Button();
                button.setLayoutX(begin1.getX());
                button.setLayoutY(begin1.getY() - 8);
                button.setPrefWidth(end1.getX() - begin1.getX());
                button.setPadding(Insets.EMPTY);
                button.setMaxHeight(0.5);
                var tooltip = new Tooltip(String.valueOf(number));
                tooltip.setShowDelay(Duration.millis(10));
                Tooltip.install(button, tooltip);
                button.setOnMouseEntered(event -> {
                    this.histogramService.getColumns().forEach(column -> column.getNode().setOpacity(0.4));
                    activeWindow.getChildren().stream()
                            .filter(node -> node instanceof Path || node instanceof Line)
                            .forEach(activePath -> activePath.setStyle("-fx-stroke: #AFAFFF; -fx-stroke-width: 4px"));
                });
                button.setOnMouseExited(event -> {
                    this.histogramService.getColumns().forEach(column -> column.getNode().setOpacity(1));
                    activeWindow.getChildren().stream()
                            .filter(node -> node instanceof Path || node instanceof Line)
                            .forEach(activePath -> activePath.setStyle("-fx-stroke: #9191C4; -fx-stroke-width: 4px"));
                });
                button.setOpacity(0);
                activeWindow.getChildren().add(button);

                // линия от (x2, b) до (0, b)
                var x = end1.getX();
                var y = end1.getY();

                var offset2 = ((MoveTo) coords.getElements().get(0)).getY();
                var line2 = new Line(x, y, x, y + (i + 1) * 25);
                line2.getStrokeDashArray().addAll(2d, 10d);
                line2.setStyle("-fx-stroke: #9191C4; -fx-stroke-width: 3px");
                activeWindow.getChildren().add(line2);

                // линия от (x1, b) до (0, b)
                // #0DFF00 - green
                var line3 = new Line(begin1.getX(), y, begin1.getX(), y + (i + 1) * 25);
                line3.getStrokeDashArray().addAll(2d, 10d);
                line3.setStyle("-fx-stroke: #9191C4; -fx-stroke-width: 3px");
                activeWindow.getChildren().add(line3);
                return;
            }
            lastDifference = difference;
        }
    }

}
