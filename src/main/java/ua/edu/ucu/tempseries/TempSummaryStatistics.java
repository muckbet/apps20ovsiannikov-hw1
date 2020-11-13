package ua.edu.ucu.tempseries;

public class TempSummaryStatistics {
    private double avgTemp, devTemp, minTemp, maxTemp;

    TempSummaryStatistics(double avgTemp, double devTemp,
                          double minTemp, double maxTemp) {
        this.avgTemp = avgTemp;
        this.devTemp = devTemp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    private boolean equalTemp(double tempOne, double tempTwo) {
        return Math.abs(tempOne - tempTwo) < TemperatureSeriesAnalysis.DELTA;
    }

    @Override
    public int hashCode() {
        return Double.valueOf(avgTemp + minTemp + maxTemp + devTemp).hashCode();
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TempSummaryStatistics)) {
            return false;
        }
        TempSummaryStatistics other = (TempSummaryStatistics) o;
        if (hashCode() != other.hashCode()) {
            return false;
        }
        return equalTemp(avgTemp, other.getAvgTemp())
                && equalTemp(devTemp, other.getDevTemp())
                && equalTemp(minTemp, other.getMinTemp())
                && equalTemp(maxTemp, other.getMaxTemp());
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public double getDevTemp() {
        return devTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }
}
