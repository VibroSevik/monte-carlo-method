package com.oryreq.montecarlomethod.services;

import com.oryreq.montecarlomethod.models.DrawData;

import java.util.ArrayList;
import java.util.List;

public class SampleCharacteristicsService {

    public static double average(List<Double> values, int power) {
        var sum = values.stream()
                        .mapToDouble(value -> Math.pow(value, power))
                        .sum();
        return sum / values.size();
    }

    public static double mathExpectation(List<Double> values) {
        return average(values, 1);
    }

    public static double biasVariance(List<Double> values) {
        var averageSquares = average(values, 2);
        return averageSquares - Math.pow(mathExpectation(values), 2);
    }

    public static double unbiasVariance(List<Double> values) {
        double n = values.size();
        return n / (n - 1) * biasVariance(values);
    }

    public static ArrayList<Double> fromDrawDataToSelection(List<DrawData> drawData) {
        ArrayList<Double> selection = new ArrayList<>();
        for (int i = 0; i < drawData.size(); i++) {
            var dropsCount = Integer.parseInt(drawData.get(i).getDropsCount());
            for (int j = 0; j < dropsCount; j++) {
                selection.add(Double.parseDouble(drawData.get(i).getNumber()));
            }
        }
        return selection;
    }

    public static ArrayList<Double> fromUniformDrawDataToSelection(List<DrawData> drawData) {
        ArrayList<Double> selection = new ArrayList<>();
        for (int i = 0; i < drawData.size(); i++) {
            var dropsCount = Integer.parseInt(drawData.get(i).getDropsCount());
            for (int j = 0; j < dropsCount; j++) {
                var begin = Double.parseDouble(drawData.get(i).getNumber().split(",")[0].split("\\(")[1]);
                var end = Double.parseDouble(drawData.get(i).getNumber().split(",")[1].split("\\)")[0]);
                selection.add((begin + end) / 2);
            }
        }
        return selection;
    }

}
