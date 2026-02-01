package org.gpiste.opendata;

public class WorkData {
    private int year;

    private double percentage;

    public WorkData (int y, double p) {
        year = y;
        percentage = p;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
