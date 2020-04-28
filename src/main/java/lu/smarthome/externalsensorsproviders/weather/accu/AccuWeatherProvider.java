package lu.smarthome.externalsensorsproviders.weather.accu;

import lu.smarthome.externalsensors.config.properties.AccuWeatherProperties;
import lu.smarthome.externalsensors.exception.ExternalSensorException;
import lu.smarthome.externalsensorsproviders.weather.WeatherProvider;

//@Component

public class AccuWeatherProvider implements WeatherProvider {

    private final AccuWeatherProperties properties;

    private final RestTemplate restTemplate;

    public AccuWeatherProvider(AccuWeatherProperties properties, @Qualifier("accuweather") RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    @Override
    public String getName() {
        return "accuweather";
    }

    @Override
    public AccuWeatherResponse retrieve() {

        UriComponentsBuilder getParams = UriComponentsBuilder
                .fromHttpUrl(properties.getUrlWithLocation())
                .queryParam("apikey", properties.getApiKey())
                .queryParam("language", properties.getLanguage())
                .queryParam("details", properties.getDetails())
                .queryParam("metric", properties.getMetric());

        ResponseEntity<AccuWeatherResponse> response = restTemplate
                .getForEntity(
                        getParams.toUriString(),
                        AccuWeatherResponse.class
                );
        if (response.getStatusCode().is2xxSuccessful()) {

            return response.getBody();
        }

        throw new ExternalSensorException(response.getStatusCode());
    }

    @Override
    public boolean isHealthy() {
        if(properties.getApiKey() == null){
            return false;
        }

        return true;
    }
}
