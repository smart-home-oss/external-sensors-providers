package lu.smarthome.externalsensorsproviders.weather.yahoo;


import lu.smarthome.externalsensorsproviders.httpwrapper.HttpUriBuilder;
import lu.smarthome.externalsensorsproviders.httpwrapper.HttpWrapper;
import lu.smarthome.externalsensorsproviders.httpwrapper.RequestAttributes;
import lu.smarthome.externalsensorsproviders.oauth.OauthHelper;
import lu.smarthome.externalsensorsproviders.properties.YahooWeatherProperties;
import lu.smarthome.externalsensorsproviders.weather.WeatherProvider;
import lu.smarthome.externalsensorsproviders.weather.WeatherResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

//@Component
public class YahooProvider implements WeatherProvider {

    private final HttpWrapper restTemplate;
    private final YahooWeatherProperties properties;
    private final OauthHelper oauthHelper;

    public YahooProvider(OauthHelper oauthHelper,
                         HttpWrapper restTemplate,
                         YahooWeatherProperties properties) {
        this.restTemplate = restTemplate;
        this.oauthHelper = oauthHelper;
        this.properties = properties;
    }

    @Override
    public String getName() {
        return "yahoo";
    }

    @Override
    public WeatherResponse retrieve() {

        // https://developer.yahoo.com/weather/documentation.html#code-example

        String url = getUrl();
        String nonce = oauthHelper.getNonce();
        String timestamp = oauthHelper.getNowTimestamp();
        String signatureString = getSignatureString(nonce, timestamp);
        String oauthSignature = getSignature(signatureString);

        Map<String, String> headers = getHttpHeaders(oauthSignature);
        RequestAttributes<String> request;
        request = new RequestAttributes<String>(Optional.empty(), Optional.of(headers));

//        ResponseEntity<String> response = restTemplate
//                .get(url, request, String.class, Collections.emptyMap());
//
//        if (response.getStatusCode().is2xxSuccessful()) {
//            return response::getBody;
//        }
//
//        throw new ExternalSensorException(response.getStatusCode());
//        
        String response = restTemplate.get(url, request);

        return () -> response;

    }

    @Override
    public boolean isHealthy() {
        return true;
    }

    String getUrl() {
        return HttpUriBuilder
                .toUrl(
                        properties.getApiUrl(),
                        "location",
                        properties.getLocation(),
                        "format",
                        "json"
                );
    }

    String getSignature(String signatureString) {
        byte[] hmac = oauthHelper.hmacSha1(signatureString, properties.getClientSecret() + "&");
        return oauthHelper.toBase64(hmac);
    }

    String getSignatureString(String nonce, String timestamp) {
        List<String> parameters = new ArrayList<>();
        parameters.add("oauth_consumer_key=" + properties.getConsumerKey());
        parameters.add("oauth_nonce=" + nonce);
        parameters.add("oauth_signature_method=" + properties.getOauthSignatureMethod());
        parameters.add("oauth_timestamp=" + timestamp);
        parameters.add("oauth_version=" + properties.getOauthVersion());
        try {
            parameters.add("location=" + URLEncoder.encode(properties.getLocation(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        parameters.add("format=json");
        Collections.sort(parameters);

        StringBuilder parametersList = new StringBuilder();
        for (int i = 0; i < parameters.size(); i++) {
            parametersList.append((i > 0) ? "&" : "").append(parameters.get(i));
        }

        try {
            return "GET&" +
                    URLEncoder.encode(properties.getApiUrl(), "UTF-8") + "&" +
                    URLEncoder.encode(parametersList.toString(), "UTF-8");
        } catch (Exception e) {

        }

        throw new RuntimeException();
    }

    Map<String, String> getHttpHeaders(String oauthSignature) {
        Map<String, String> headers = new HashMap<>();

        headers.put("ContentType", "MediaType.APPLICATION_JSON");
        headers.put("X-Yahoo-App-Id", properties.getAppId());
        headers.put(
                "Authorization",
                "OAuth "
                        .concat("oauth_consumer_key=\"").concat(properties.getClientId())
                        .concat("\", oauth_nonce=\"").concat(oauthHelper.getNonce())
                        .concat("\", oauth_timestamp=\"").concat(oauthHelper.getNowTimestamp())
                        .concat("\", oauth_signature_method=\"").concat(properties.getOauthSignatureMethod())
                        .concat("\", oauth_signature=\"").concat(oauthSignature)
                        .concat("\", oauth_version=\"").concat(properties.getOauthVersion())
                        .concat("\"")
        );

        return headers;
    }

}


