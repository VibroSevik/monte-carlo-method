package com.oryreq.montecarlomethod.models;

import javafx.beans.property.SimpleStringProperty;


/**
 * This class realizes object so-called <strong>DrawData</strong>. <br>
 * It is used to link draw data with <strong>TableView</strong> from <strong>javaFX</strong> lib. <br>
 *
 * @Date: 29.09.2024
 * @Author: Vsevolod @Oryreq Ashihmin
 */
public class DrawData {

    private final SimpleStringProperty number;
    private final SimpleStringProperty probability;
    private final SimpleStringProperty dropsCount;
    private final SimpleStringProperty frequency;

    public DrawData(int number, double probability, int dropsCount, double frequency) {
        this.number = new SimpleStringProperty(String.valueOf(number));
        this.probability = new SimpleStringProperty(String.valueOf(probability));
        this.dropsCount = new SimpleStringProperty(String.valueOf(dropsCount));
        this.frequency = new SimpleStringProperty(String.valueOf(frequency));
    }

    public DrawData(String number, double probability, int dropsCount, double frequency) {
        this.number = new SimpleStringProperty(number);
        this.probability = new SimpleStringProperty(String.valueOf(probability));
        this.dropsCount = new SimpleStringProperty(String.valueOf(dropsCount));
        this.frequency = new SimpleStringProperty(String.valueOf(frequency));
    }

    public DrawData(String number, double probability, double dropsCount, double frequency) {
        this.number = new SimpleStringProperty(number);
        this.probability = new SimpleStringProperty(String.valueOf(probability));
        this.dropsCount = new SimpleStringProperty(String.valueOf(dropsCount));
        this.frequency = new SimpleStringProperty(String.valueOf(frequency));
    }

    public DrawData(Interval interval, double probability, int dropsCount, double frequency) {
        this.number = new SimpleStringProperty("(" + interval.begin() + "," + interval.end() + ")");
        this.probability = new SimpleStringProperty(String.valueOf(probability));
        this.dropsCount = new SimpleStringProperty(String.valueOf(dropsCount));
        this.frequency = new SimpleStringProperty(String.valueOf(frequency));
    }

    public String getNumber() {
        return this.number.get();
    }

    public void setNumber(int number) {
        this.number.set(String.valueOf(number));
    }

    public String getProbability() {
        return this.probability.get();
    }

    public void setProbability(double probability) {
        this.probability.set(String.valueOf(probability));
    }

    public String getDropsCount() {
        return this.dropsCount.get();
    }

    public void setDropsCount(int dropsCount) {
        this.dropsCount.set(String.valueOf(dropsCount));
    }

    public String getFrequency() {
        return this.frequency.get();
    }

    public void setFrequency(double frequency) {
        this.frequency.set(String.valueOf(frequency));
    }

}
