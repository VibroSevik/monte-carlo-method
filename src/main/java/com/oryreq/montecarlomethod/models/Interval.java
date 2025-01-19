package com.oryreq.montecarlomethod.models;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Interval implements Comparable<Interval> {

    private final Double begin;
    private final Double end;

    private final Double length;


    public Interval(Number begin, Number end) {
        this.begin = begin.doubleValue();
        this.end = end.doubleValue();
        this.length = end.doubleValue() - begin.doubleValue();
    }

    public List<Interval> uniformPartition(int partsCount) {
        double partLength = length / partsCount;
        var points = new ArrayList<>(
                IntStream.range(0, partsCount)
                        .mapToObj(i -> begin + i * partLength)
                        .toList()
        );
        points.add(this.end);
        return IntStream.range(0, points.size() - 1)
                            .mapToObj(i -> new Interval(points.get(i), points.get(i + 1)))
                            .toList();
    }

    public boolean contains(double number) {
        return this.begin <= number && number <= this.end;
    }

    public Interval applyRound(int rounding) {
        DecimalFormat formatter = new DecimalFormat("#." + "#".repeat(rounding));
        double begin = Double.parseDouble(formatter.format(this.begin).replace(",", "."));
        double end = Double.parseDouble(formatter.format(this.end).replace(",", "."));
        return new Interval(begin, end);
    }

    public Double begin() {
        return this.begin;
    }

    public Double end() {
        return this.end;
    }

    public Double length() {
        return this.length;
    }

    @Override
    public int compareTo(Interval interval) {
        return this.begin.compareTo(interval.begin);
    }

}
