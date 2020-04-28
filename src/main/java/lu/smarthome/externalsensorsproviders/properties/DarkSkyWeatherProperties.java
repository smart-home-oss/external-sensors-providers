package lu.smarthome.externalsensorsproviders.properties;

//@ConfigurationProperties("app.provider.weather.darksky")
//@Component
public class DarkSkyWeatherProperties {


    private String apiUrl;
    private String appId;
    private String latitude = "49.797955";
    private String longitude = "6.681294";

    public String getLocation() {
        return latitude.concat(",").concat(longitude);
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getLatitude() { return latitude; }

    public void setLatitude(String latitude) { this.latitude = latitude; }

    public String getLongitude() { return longitude; }

    public void setLongitude(String longitude) { this.longitude = longitude; }

    public String getUrlWithLocation(){ return apiUrl + appId + "/" + getLocation(); }

    public String getUnits() { return "auto"; }

}
