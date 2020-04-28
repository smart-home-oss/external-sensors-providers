package lu.smarthome.externalsensorsproviders.airquality.airvisual;

import lu.smarthome.externalsensorsproviders.airquality.AirQualityProvider;
import lu.smarthome.externalsensorsproviders.properties.AirVisualAirQualityProperties;

//@Qualifier("air-quality")
//@Component("airvisual-airquality")
public class AirVisualProvider implements AirQualityProvider {

    private final AirVisualAirQualityProperties properties;

    private final RestTemplate restTemplate;

    public AirVisualProvider(AirVisualAirQualityProperties properties, @Qualifier("generic") RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    @Override
    public String getName() {
        return "airvisual";
    }

    @Override
    public AirQualityResponse retrieve() {

        UriComponentsBuilder getParams = UriComponentsBuilder
                .fromHttpUrl(properties.getUrl())
                .queryParam("key", properties.getApiKey())
                .queryParam("city", properties.getCity())
                .queryParam("state", properties.getState())
                .queryParam("country", properties.getCountry());

        ResponseEntity<AirVisualResponse> response;

        try {
            response = restTemplate
                    .getForEntity(
                            getParams.encode().build().toUri(),
                            AirVisualResponse.class
                    );
        } catch (HttpServerErrorException e) {
            throw new ExternalSensorException(e.getStatusCode());
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        throw new ExternalSensorException(response.getStatusCode());
    }

    @Override
    public boolean supports() {
        if (properties.getApiKey() == null) {
            return false;
        }

        return true;
    }
}
