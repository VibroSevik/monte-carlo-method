package com.oryreq.montecarlomethod.services;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * This class realizes object so-called <strong>MonteCarloCroupier</strong>. <br>
 * It is used to generate a some random numbers in a segment [0,1], then approximate them to the closest point in point list <br>
 * and write them to map by <strong>number - drop count</strong> rule. <br>
 *
 * @Date: 29.09.2024
 * @Author: Vsevolod @Oryreq Ashihmin
 */
public class MonteCarloCroupier {

    private final TreeMap<Double, Integer> results = new TreeMap<>();


    public Map<Double, Integer> play(int draws, List<Double> points) {
        return this.play(draws, points, 1);
    }

    public Map<Double, Integer> play(int draws, List<Double> points, int roundedDigits) {
        Function<Double, Double> approximateNumber =
                (number) -> points.stream().filter(point -> point > number).findFirst().get();

        int rounding = (int) Math.pow(10, roundedDigits);
        for (int i = 0; i < draws; i++) {
            double number = Math.random();
            double approximatedPoint = Math.round(approximateNumber.apply(number) * rounding) / (double) rounding;
            int count = results.getOrDefault(approximatedPoint, 0);
            results.put(Math.round(approximatedPoint * rounding) / (double) rounding, count + 1);
        }
        return results;
    }

}
