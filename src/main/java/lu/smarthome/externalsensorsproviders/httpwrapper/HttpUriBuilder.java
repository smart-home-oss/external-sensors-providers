package lu.smarthome.externalsensorsproviders.httpwrapper;

public class HttpUriBuilder {
    public static String toUrl(String url, String... queryparams) {
        String uri = "";

        for (int i = 0; i < queryparams.length; i++) {
            uri = uri.concat(queryparams[i])
                    .concat("=")
                    .concat(queryparams[i + 1])
                    .concat("&");
            i++;
        }

        return url.concat("?").concat(uri);
    }
}
