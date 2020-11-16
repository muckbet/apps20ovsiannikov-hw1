package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {

    private static final double MIN_TEMP = -273.0;

    private double[] temperatureSeries;
    private int length;
    private int capacity;

    public TemperatureSeriesAnalysis() {
        temperatureSeries = new double[]{};
        this.length = 0;
        capacity = 1;
    }

    public TemperatureSeriesAnalysis(double[] tempSeries) {
        this.temperatureSeries = new double[tempSeries.length];
        System.arraycopy(tempSeries, 0,
                this.temperatureSeries, 0, tempSeries.length);
        this.length = temperatureSeries.length;
        capacity = temperatureSeries.length;
        for (double item : this.temperatureSeries) {
            if (item < MIN_TEMP) {
                throw new InputMismatchException();
            }
        }

    }

    public void isEmpty() {
        if (this.length == 0) {
            throw new IllegalArgumentException();
        }
    }

    public double average() {
        isEmpty();
        double averageValue = 0;
        for (double item : this.temperatureSeries) {
            averageValue += item;
        }
        return averageValue / this.length;
    }

    public double deviation() throws IllegalArgumentException {
        isEmpty();
        double minVal = average();
        double standartDev = 0;
        for (double item : this.temperatureSeries) {
            standartDev += (item - minVal) * (item - minVal);
        }
        standartDev /= this.length;
        return Math.sqrt(standartDev);
    }

    public double min() {
        isEmpty();
        double min = this.temperatureSeries[0];
        for (int i = 0; i < this.length; i++) {
            if (min > temperatureSeries[i]) {
                min = temperatureSeries[i];
            }
        }
        return min;
    }
    public double max() {
        isEmpty();
        double max = this.temperatureSeries[0];
        for (int i = 0; i < this.length; i++) {
            if (max < temperatureSeries[i]) {
                max = temperatureSeries[i];
            }
        }
        return max;
    }
    public double findTempClosestToZero(){
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        isEmpty();
        double closestToVal = this.temperatureSeries[0];
        for (int i = 0; i < this.length; i++) {
            if (Math.abs(temperatureSeries[i] - tempValue) < Math.abs(closestToVal - tempValue)) {
                closestToVal = this.temperatureSeries[i];
            }
        }
        return closestToVal;
    }

    public double[] findTempsLessThen(double tempValue) {
        double[] tempsLessThen = new double[this.length + 1];
        int j = 0;
        for (int i = 0; i < this.length; i++) {
            if (this.temperatureSeries[i] < tempValue) {
                tempsLessThen[j] = this.temperatureSeries[i];
                j++;
            }
        }
        return tempsLessThen;

    }

    public double[] findTempsGreaterThen(double tempValue) {
        double[] tempsGreaterThen = new double[this.length + 1];
        int j = 0;
        for (int i = 0; i < this.length; i++) {
            if (this.temperatureSeries[i] > tempValue) {
                tempsGreaterThen[j] = this.temperatureSeries[i];
                j++;
            }
        }
        return tempsGreaterThen;
    }

    public TempSummaryStatistics summaryStatistics() {
        isEmpty();
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        if (capacity - length < temps.length) {
            double[] newSeries = new double[this.length * 2];
            double[] oldSeries = this.temperatureSeries;
            if (this.length >= 0) {
                System.arraycopy(oldSeries, 0, newSeries, 0, this.length);
            }
            this.length = this.temperatureSeries.length + temps.length;
            this.temperatureSeries = newSeries;
        }
        int j = 0;
        for (int i = this.length; i < this.length + temps.length; i++) {
            this.temperatureSeries[i] = temps[j];
            j += 1;
        }
        this.capacity = this.temperatureSeries.length;
        this.length+=temps.length;
        return this.length;
    }
}
