package com.oryreq.montecarlomethod.services;

import java.util.List;
import java.util.stream.IntStream;

public class DistributionCharacteristicsService {

    public static double rawMoment(List<Double> values, List<Double> probabilities, int momentNumber) {
        return IntStream.range(0, values.size())
                        .mapToDouble(i -> Math.pow(values.get(i), momentNumber) * probabilities.get(i))
                        .sum();
    }

    public static double mathExpectation(List<Double> values, List<Double> probabilities) {
        return rawMoment(values, probabilities, 1);
    }

}
