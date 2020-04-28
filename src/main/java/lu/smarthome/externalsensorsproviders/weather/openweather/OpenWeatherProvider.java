package lu.smarthome.externalsensorsproviders.weather.openweather;


import lu.smarthome.externalsensorsproviders.FormatMode;
import lu.smarthome.externalsensorsproviders.UnitsFormat;
import lu.smarthome.externalsensorsproviders.exception.ExternalSensorException;
import lu.smarthome.externalsensorsproviders.properties.OpenWeatherProperties;
import lu.smarthome.externalsensorsproviders.weather.WeatherProvider;
import lu.smarthome.externalsensorsproviders.weather.WeatherResponse;

//@Component
public class OpenWeatherProvider implements WeatherProvider {

    private final OpenWeatherProperties properties;

    private final RestTemplate restTemplate;

    public OpenWeatherProvider(OpenWeatherProperties properties, @Qualifier("generic") RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }


    @Override
    public String getName() {
        return "openweather";
    }

    @Override
    public WeatherResponse retrieve() {


        String url = UriComponentsBuilder
                .fromHttpUrl(properties.getUrl())
                .queryParam("q", properties.getCityStateAndCountry())
                .queryParam("units", UnitsFormat.METRIC)
                .queryParam("mode", FormatMode.JSON)
                .queryParam("lang", properties.getLang())
                .queryParam("appid", properties.getApiKey())
                .encode()
                .toUriString();

        ResponseEntity<OpenWeatherResponse> response = restTemplate
                .getForEntity(
                        url,
                        OpenWeatherResponse.class
                );
        if (response.getStatusCode().is2xxSuccessful()) {

            return response.getBody();
        }

        throw new ExternalSensorException(response.getStatusCode());
    }

    @Override
    public boolean isHealthy() {

        if (properties.getApiKey() == null) {
            return false;
        }
        return true;
    }
}
