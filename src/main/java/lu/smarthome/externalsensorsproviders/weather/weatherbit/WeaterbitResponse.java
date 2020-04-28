package lu.smarthome.externalsensorsproviders.weather.weatherbit;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lu.smarthome.externalsensorsproviders.exception.ProviderException;
import lu.smarthome.externalsensorsproviders.weather.WeatherResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WeaterbitResponse implements WeatherResponse {

    private List<Datum> data;
    private Integer count;
    private Map<String, Object> additionalProperties = new HashMap<>();

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String getTemp() {
        if(count < 1) {
            throw new ProviderException("Could not get enough data");
        }

        return data.get(0).getTemp().toString();
    }
}