package com.assignment.finalassignment;

public class Model {
    public String x, y, timeStamp;

    public Model() {

    }

    public Model(String x, String y, String timeStamp) {
        this.x = x;
        this.y = y;
        this.timeStamp = timeStamp;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
