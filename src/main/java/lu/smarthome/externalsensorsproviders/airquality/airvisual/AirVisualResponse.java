package lu.smarthome.externalsensorsproviders.airquality.airvisual;

import lombok.Getter;
import lombok.Setter;
import lu.smarthome.externalsensorsproviders.airquality.AirQualityResponse;

@Getter
@Setter
public class AirVisualResponse implements AirQualityResponse {

    private String status; // "success" https://api-docs.airvisual.com/?version=latest#intro
    private Data data;

    @Override
    public String getIndex() {
        return data.getCurrent().getPollution().getAqius().toString();
    }
}