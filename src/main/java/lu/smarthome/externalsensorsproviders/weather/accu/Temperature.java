package lu.smarthome.externalsensorsproviders.weather.accu;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class Temperature {

    private TemperatureValue minimum;
    private TemperatureValue maximum;

}