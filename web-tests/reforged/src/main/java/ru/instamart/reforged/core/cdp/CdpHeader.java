package ru.instamart.reforged.core.cdp;

import org.openqa.selenium.devtools.v109.network.Network;
import org.openqa.selenium.devtools.v109.network.model.Headers;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.reforged.core.Kraken;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public final class CdpHeader {

    private static final String LOGIN_PASS = CoreProperties.BASIC_AUTH_USERNAME + ":" + CoreProperties.BASIC_AUTH_PASSWORD;
    public static final Map<String, Object> BASIC_AUTH = Map.of(
            "authorization",
            "Basic " + Base64.getEncoder().encodeToString(LOGIN_PASS.getBytes(StandardCharsets.UTF_8))
    );

    public static void addHeader(final Map<String, Object> headers) {
        Kraken.getDevTools().send(Network.setExtraHTTPHeaders(new Headers(headers)));
    }
}
