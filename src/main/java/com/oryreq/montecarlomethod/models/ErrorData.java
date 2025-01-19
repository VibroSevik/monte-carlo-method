package com.oryreq.montecarlomethod.models;

import javafx.beans.property.SimpleStringProperty;

public class ErrorData {

    private final SimpleStringProperty playedAll;
    private final SimpleStringProperty firstErrorCount;
    private final SimpleStringProperty secondErrorCount;
    private final SimpleStringProperty errorFrequency;

    public ErrorData(int playedAll, double firstErrorCount, int secondErrorCount, double errorFrequency) {
        this.playedAll = new SimpleStringProperty(String.valueOf(playedAll));
        this.firstErrorCount = new SimpleStringProperty(String.valueOf(firstErrorCount));
        this.secondErrorCount = new SimpleStringProperty(String.valueOf(secondErrorCount));
        this.errorFrequency = new SimpleStringProperty(String.valueOf(errorFrequency));
    }

    public String getPlayedAll() {
        return this.playedAll.get();
    }

    public void setPlayedAll(int playedAll) {
        this.playedAll.set(String.valueOf(playedAll));
    }

    public String getFirstErrorCount() {
        return this.firstErrorCount.get();
    }

    public void setFirstErrorCount(int firstErrorCount) {
        this.firstErrorCount.set(String.valueOf(firstErrorCount));
    }

    public String getSecondErrorCount() {
        return this.secondErrorCount.get();
    }

    public void setSecondErrorCount(int secondErrorCount) {
        this.secondErrorCount.set(String.valueOf(secondErrorCount));
    }

    public String getErrorFrequency() {
        return this.errorFrequency.get();
    }

    public void setErrorFrequency(int errorFrequency) {
        this.errorFrequency.set(String.valueOf(errorFrequency));
    }

}
