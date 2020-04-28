package lu.smarthome.externalsensorsproviders.airquality.waqi;

import lu.smarthome.externalsensorsproviders.airquality.AirQualityProvider;

//@Component("waqi-airquality")
public class WaqiProvider implements AirQualityProvider {

    private final WaqiAirQualityProperties properties;

    private final RestTemplate restTemplate;

    public WaqiProvider(WaqiAirQualityProperties properties, @Qualifier("generic") RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    @Override
    public String getName() {
        return "waqi";
    }

    @Override
    public WaqiResponse retrieve() {

        UriComponentsBuilder getParams = UriComponentsBuilder
                .fromHttpUrl(properties.getUrlWithLocation())
                .queryParam("token", properties.getApiKey());

        ResponseEntity<WaqiResponse> response;

        try {
            response = restTemplate
                    .getForEntity(
                            getParams.toUriString(),
                            WaqiResponse.class
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
