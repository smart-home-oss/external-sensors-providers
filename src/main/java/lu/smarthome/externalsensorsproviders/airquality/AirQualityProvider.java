package lu.smarthome.externalsensorsproviders.airquality;

public interface AirQualityProvider {

    String getName();

    AirQualityResponse retrieve();

    boolean supports();
}