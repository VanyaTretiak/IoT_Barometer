package studentgroup.demobarometer;

import java.time.LocalDateTime;

//Class to define Measurement object
public class Measurement {
    private int id;
    private double temperature;
    public double pressure;
    private double humidity;
    private double approxHeight;
    private LocalDateTime measurementDateTime;

    public Measurement(double temperature, double pressure, double humidity, double approxHeight, LocalDateTime measurementDateTime) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.approxHeight = approxHeight;
        this.measurementDateTime = measurementDateTime;
    }

    public Measurement() {
        this.temperature = 36.6;
        this.pressure = 36.6;
        this.humidity = 36.6;
        this.approxHeight = 36.6;
        this.measurementDateTime = LocalDateTime.parse("2018-05-05T11:50:55");;
    }

    //Getters
    public int getId() {
        return id;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPressure() {
        return pressure /  133.3223684;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getApproxHeight() {
        return approxHeight;
    }

    public LocalDateTime getMeasurementDateTime() {
        return measurementDateTime;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setApproxHeight(double approxHeight) {
        this.approxHeight = approxHeight;
    }

    public void setMeasurementDateTime(LocalDateTime measurementDateTime) {
        this.measurementDateTime = measurementDateTime;
    }
}