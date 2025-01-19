package com.oryreq.montecarlomethod.models;

public class StringEvent {

    private EventTypes type;
    private String value;

    public enum EventTypes {
        HANDLE_DISCRETE_INPUT,
        HANDLE_CONTINUOUS_INPUT,
        HANDLE_NORMAL_INPUT,
        OPEN_BINOMIAL_WINDOW,
        OPEN_UNIFORM_WINDOW,
        OPEN_NORMAL_WINDOW,
        OPEN_ERROR_WINDOW,
    }

    public StringEvent(EventTypes type, String value) {
        this.type = type;
        this.value = value;
    }

    public void setType(EventTypes type) {
        this.type = type;
    }

    public EventTypes getType() {
        return this.type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
