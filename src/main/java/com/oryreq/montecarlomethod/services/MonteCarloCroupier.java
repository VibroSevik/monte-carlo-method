package com.oryreq.montecarlomethod.services;

import com.oryreq.montecarlomethod.models.Interval;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


/**
 * This class realizes object so-called <strong>MonteCarloCroupier</strong>. <br>
 * It is used to generate a some random numbers in a segment [0,1], then approximate them to the closest point in point list <br>
 * and write them to map by <strong>number - drop count</strong> rule. <br>
 *
 * @Date: 29.09.2024
 * @Author: Vsevolod @Oryreq Ashihmin
 */
public class MonteCarloCroupier {

                        /*------------------------------------*
                         *          Discrete playing          *
                        *------------------------------------*/
    public Map<Integer, Integer> discretePlay(int draws, List<Double> points, List<Integer> values) {
        Map<Integer, Integer> results = createDefaultMap(values);

        for (int i = 0; i < draws; i++) {
            double number = Math.random();
            var begin = getIntervalEnds(number, points).begin();
            var index = points.indexOf(begin);
            var value = values.get(index);
            int valueCount = results.get(value);
            results.put(value, valueCount + 1);
        }

        return results;
    }

    private Map<Integer, Integer> createDefaultMap(List<Integer> values) {
        return values.stream().collect(
                Collectors.toMap(
                        value -> value,
                        value -> 0)
        );
    }

    private Interval getIntervalEnds(double number, List<Double> points) {
        for (int i = 1; i < points.size(); i++) {
            if (points.get(i) > number) {
                return new Interval(points.get(i - 1), points.get(i));
            }
        }
        return null;
    }


                        /*--------------------------------------*
                         *          Continuous playing          *
                         *--------------------------------------*/
    public Map<Interval, Integer> continuousPlay(int draws, List<Interval> intervals) {
        Map<Interval, Integer> results = createDefaultContinuousMap(intervals);

        for (int i = 0; i < draws; i++) {
            double number = Math.random();
            var interval = getInterval(number, intervals);
            int valueCount = results.get(interval);
            results.put(interval, valueCount + 1);
        }

        return results;
    }

    private Map<Interval, Integer> createDefaultContinuousMap(List<Interval> intervals) {
        return new TreeMap<>(
                intervals.stream().collect(
                        Collectors.toMap(
                                interval -> interval,
                                interval -> 0))
        );
    }

    private Interval getInterval(double number, List<Interval> intervals) {
        return intervals.stream()
                .filter(interval -> interval.contains(number))
                .findFirst()
                .orElse(null);
    }

}
