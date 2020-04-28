package lu.smarthome.externalsensorsproviders.weather;

public interface WeatherProvider {

    String getName();

    WeatherResponse retrieve();

    boolean isHealthy();
}