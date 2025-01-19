package com.oryreq.montecarlomethod.models;

import javafx.beans.property.SimpleStringProperty;

public class CharacteristicsData {

    private final SimpleStringProperty mathExpectation;
    private final SimpleStringProperty biasVariance;
    private final SimpleStringProperty unbiasVariance;
    private final SimpleStringProperty biasStandardDeviation;
    private final SimpleStringProperty unbiasStandardDeviation;

    public CharacteristicsData(double mathExpectation,
                               double biasVariance,
                               double unbiasVariance,
                               double biasStandardDeviation,
                               double unbiasStandardDeviation) {
        this.mathExpectation = new SimpleStringProperty(String.valueOf(mathExpectation));
        this.biasVariance = new SimpleStringProperty(String.valueOf(biasVariance));
        this.unbiasVariance = new SimpleStringProperty(String.valueOf(unbiasVariance));
        this.biasStandardDeviation = new SimpleStringProperty(String.valueOf(biasStandardDeviation));
        this.unbiasStandardDeviation = new SimpleStringProperty(String.valueOf(unbiasStandardDeviation));
    }


    public String getMathExpectation() {
        return mathExpectation.get();
    }

    public void setMathExpectation(double mathExpectation) {
        this.mathExpectation.set(String.valueOf(mathExpectation));
    }

    public String getBiasVariance() {
        return biasVariance.get();
    }

    public void setBiasVariance(double biasVariance) {
        this.biasVariance.set(String.valueOf(biasVariance));
    }

    public String getUnbiasVariance() {
        return unbiasVariance.get();
    }

    public void setUnbiasVariance(double unbiasVariance) {
        this.unbiasVariance.set(String.valueOf(unbiasVariance));
    }

    public String getBiasStandardDeviation() {
        return biasStandardDeviation.get();
    }

    public void setBiasStandardDeviation(double biasStandardDeviation) {
        this.biasStandardDeviation.set(String.valueOf(biasStandardDeviation));
    }

    public String getUnbiasStandardDeviation() {
        return unbiasStandardDeviation.get();
    }

    public void setUnbiasStandardDeviation(double unbiasStandardDeviation) {
        this.unbiasStandardDeviation.set(String.valueOf(unbiasStandardDeviation));
    }

}
