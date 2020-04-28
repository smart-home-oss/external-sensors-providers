package lu.smarthome.externalsensorsproviders.httpwrapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class RequestAttributes<R> {
    private final Optional<String> body;
    private final Optional<Map<String, String>> headers;
    private R responseType;
}
