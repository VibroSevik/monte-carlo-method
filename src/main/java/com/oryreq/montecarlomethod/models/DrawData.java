package com.oryreq.montecarlomethod.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class realizes object so-called <strong>DrawData</strong>. <br>
 * It is used to link draw data with <strong>TableView</strong> from <strong>javaFX</strong> lib. <br>
 *
 * @Date: 29.09.2024
 * @Author: Vsevolod @Oryreq Ashihmin
 */
public class DrawData {

    private final SimpleDoubleProperty number;
    private final SimpleIntegerProperty dropsCount;

    public DrawData(double number, int dropsCount) {
        this.number = new SimpleDoubleProperty(number);
        this.dropsCount = new SimpleIntegerProperty(dropsCount);
    }

    public DrawData(String number, int dropsCount) {
        this.number = new SimpleDoubleProperty(Double.parseDouble(number));
        this.dropsCount = new SimpleIntegerProperty(dropsCount);
    }

    public double getNumber() {
        return number.get();
    }

    public void setNumber(double number) {
        this.number.set(number);
    }

    public double getDropsCount() {
        return dropsCount.get();
    }

    public void setDropsCount(int dropsCount) {
        this.dropsCount.set(dropsCount);
    }

}
