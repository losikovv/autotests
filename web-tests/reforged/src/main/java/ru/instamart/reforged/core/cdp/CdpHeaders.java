package ru.instamart.reforged.core.cdp;

import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v102.network.Network;
import org.openqa.selenium.devtools.v102.network.model.Headers;
import ru.instamart.reforged.core.Kraken;

import java.util.Map;

public final class CdpHeaders {

    public static void addHeader(final Map<String, Object> headers) {
        addHeader(headers, Kraken.getDevTools());
    }

    public static void addHeader(final Map<String, Object> headers, final DevTools devTools) {
        devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));
    }
}
