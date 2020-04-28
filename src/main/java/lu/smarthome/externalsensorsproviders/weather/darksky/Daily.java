package lu.smarthome.externalsensorsproviders.weather.darksky;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Daily {

    public String summary;
    public String icon;
    public List<Datum_> data = null;

}
