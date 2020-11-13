package ua.edu.ucu.tempseries;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    static final int MIN_TEMP = -273;
    static final double DELTA = 1e-6;

    private double[] temperatures;
    private int capacity;
    private int temperaturesNum;

    TemperatureSeriesAnalysis() {
        temperatures = new double[1];
        capacity = 1;
        temperaturesNum = 0;
    }

    TemperatureSeriesAnalysis(double[] temperatureSeries) {
        this();
        for (double temperature : temperatureSeries) {
            if (temperature < MIN_TEMP) {
                throw new InputMismatchException();
            }
        }
        temperatures = temperatureSeries;
        capacity = temperatures.length;
        temperaturesNum = capacity;
    }

    double average() {
        if (temperaturesNum == 0) {
            throw new IllegalArgumentException();
        }
        double sum = 0;
        for (int i = 0; i < temperaturesNum; i++) {
            sum += temperatures[i];
        }
        return sum / temperaturesNum;
    }


    double deviation() {
        double mean = average();
        double quadraticSum = 0;
        for (int i = 0; i < temperaturesNum; i++) {
            quadraticSum += Math.abs(temperatures[i] - mean)
                    * Math.abs(temperatures[i] - mean);
        }
        return quadraticSum / temperaturesNum;
    }

    double min() {
        if (temperaturesNum == 0) {
            throw new IllegalArgumentException();
        }
        double m = temperatures[0];
        for (int i = 0; i < temperaturesNum; i++) {
            m = Math.min(m, temperatures[i]);
        }
        return m;
    }

    double max() {
        if (temperaturesNum == 0) {
            throw new IllegalArgumentException();
        }
        double m = temperatures[0];
        for (double temperature : temperatures) {
            m = Math.max(temperature, m);
        }
        return m;
    }

    double findTempClosestToZero() {
        return findTempClosestToValue(0.0);
    }

    double findTempClosestToValue(double tempValue) {
        if (temperaturesNum == 0) {
            throw new IllegalArgumentException();
        }

        double closestTemp = temperatures[0];
        double currentClosest = Math.abs(temperatures[0] - tempValue);
        for (int i = 0; i < temperaturesNum; i++) {
            if (Math.abs(currentClosest
                    - Math.abs(temperatures[i]
                    - tempValue)) <= DELTA
                    && temperatures[i] > tempValue) {
                closestTemp = temperatures[i];
            } else if (currentClosest > Math.abs(temperatures[i] - tempValue)) {
                currentClosest = Math.abs(temperatures[i] - tempValue);
                closestTemp = temperatures[i];
            }
        }
        return closestTemp;
    }


    private double[] findTempsWithCondition(boolean greater, double tempValue) {
        TemperatureSeriesAnalysis tempsLess = new TemperatureSeriesAnalysis();
        for (int i = 0; i < temperaturesNum; i++) {
            if (temperatures[i] > tempValue
                    && greater
                    || temperatures[i] < tempValue
                    && !greater) {
                tempsLess.addTemps(temperatures[i]);
            }
        }
        double[] res = new double[tempsLess.temperaturesNum];
        System.arraycopy(tempsLess.temperatures, 0, res, 0, res.length);
        return res;
    }

    double[] findTempsLessThan(double tempValue) {
        return findTempsWithCondition(false, tempValue);
    }

    double[] findTempsGreaterThan(double tempValue) {
        return findTempsWithCondition(true, tempValue);
    }

    private void addOneTemp(double temp) {
        if (capacity == temperaturesNum) {
            double[] newArr = new double[capacity * 2];
            System.arraycopy(temperatures, 0, newArr, 0, temperaturesNum);
            temperatures = newArr;
            capacity *= 2;
        }
        temperatures[temperaturesNum] = temp;
        temperaturesNum++;
    }

    TempSummaryStatistics summaryStatistics() {
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }


    int addTemps(double... temps) {
        for (double temp : temps) {
            if (temp < MIN_TEMP) {
                throw new InputMismatchException();
            }
        }
        for (double temp : temps) {
            addOneTemp(temp);
        }
        return temperaturesNum;
    }
}
