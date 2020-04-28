package lu.smarthome.externalsensorsproviders.properties;

//@Component
//@ConfigurationProperties("app")
public class AppProperties {

    private String weatherProvider;
    private String airQualityProvider;

    public String getAirQualityProvider() {
        return airQualityProvider;
    }

    public void setAirQualityProvider(String airQualityProvider) {
        this.airQualityProvider = airQualityProvider;
    }

    public String getWeatherProvider() {
        return weatherProvider;
    }

    public void setWeatherProvider(String weatherProvider) {
        this.weatherProvider = weatherProvider;
    }
}
