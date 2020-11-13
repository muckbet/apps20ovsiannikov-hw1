package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {

    private static final double MIN_TEMP = -273.0;

    private double[] tempSeries;
    private int size;
    private int capacity;

    public TemperatureSeriesAnalysis() {
        tempSeries = new double[]{}; //series
        size = 0; //len
        capacity = 1;
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        valid(tempSeries);
        this.tempSeries = Arrays.copyOf(temperatureSeries,
                temperatureSeries.length);
        size = temperatureSeries.length;
        capacity = temperatureSeries.length;
    }

    private void valid(double... temps) throws InputMismatchException {
        for (double temp: temps) {
            if (temp < MIN_TEMP) {
                throw new InputMismatchException();
            }
        }
    }

    private void empty(double[] values) throws IllegalArgumentException {
        if (values.length == 0) {
            throw new IllegalArgumentException();
        }
    }

    public double average() {
        empty(tempSeries);
        double avg = 0;
        for (int i = 0; i < size; i++) {
            avg += tempSeries[i];
        }
        return avg / size;
    }

    public double deviation() throws IllegalArgumentException {
        empty(tempSeries);
        double mean = average();
        double dev = 0;
        for (int i = 0; i < size; i++) {
            dev += (tempSeries[i] - mean)
                    * (tempSeries[i] - mean);
        }
        dev /= size;
        return Math.pow(dev, 0.5);
    }

    public double min() {
        empty(tempSeries);
        double min = Double.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            if (min > tempSeries[i]) {
                min = tempSeries[i];
            }
        }
        return min;
    }
    public double max() {
        empty(tempSeries);
        double max = MIN_TEMP;
        for (int i = 0; i < size; i++) {
            if (max < tempSeries[i]) {
                max = tempSeries[i];
            }
        }
        return max;
    }
    public double findTempClosestToZero(){
        empty(tempSeries);
        double minD = Math.abs(tempSeries[0]);
        double closest = tempSeries[0];
        for (int i = 1; i < size; i++) {
            if (Math.abs(tempSeries[i]) < minD) {
                closest = tempSeries[i];
            }
        }
        return closest;
    }
    public double findTempClosestToValue(double tempValue) {
        empty(tempSeries);
        double closest = Double.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            if (Math.abs(tempSeries[i] - tempValue) < Math.abs(closest - tempValue)) {
                closest = tempSeries[i];
            }
        }
        return closest;
    }
    public double[] findTempsLessThen(double tempValue) {
        int arr = 0;
        for (double temp : tempSeries) {
            if (temp < tempValue) {
                arr += 1;
            }
        }
        double[] temps = new double[arr];
        int i = 0;
        for (double temp : tempSeries) {
            if (temp < tempValue) {
                temps[i] = temp;
                i += 1;
            }
        }
        return temps;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        int arr = 0;
        for (double temp : tempSeries) {
            if (temp < tempValue) {
                arr += 1;
            }
        }
        double[] temps = new double[arr];
        int i = 0;
        for (double temp : tempSeries) {
            if (temp < tempValue) {
                temps[i] = temp;
                i += 1;
            }
        }
        return temps;
    }

    public TempSummaryStatistics summaryStatistics() {
        empty(tempSeries);
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    int addTemps(double... temps) {
        valid(temps);
        while (capacity - size < temps.length) {
            capacity *= 2;
            tempSeries = Arrays.copyOf(tempSeries, capacity);
        }
        System.arraycopy(temps, 0, tempSeries, size, size
                + temps.length - size);
        return size + temps.length;
    }
}
