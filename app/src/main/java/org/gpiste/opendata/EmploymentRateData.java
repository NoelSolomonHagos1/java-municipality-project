package org.gpiste.opendata;


public class EmploymentRateData {
    private int year;
    private double percentage;

    public EmploymentRateData (int y, double p) {
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
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }


}
