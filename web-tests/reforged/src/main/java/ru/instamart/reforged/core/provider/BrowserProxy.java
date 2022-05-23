package ru.instamart.reforged.core.provider;

import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import org.openqa.selenium.Proxy;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.core.config.BrowserProperties;

import static java.util.Objects.nonNull;

public enum BrowserProxy {

    INSTANCE;

    private static Proxy seleniumProxy;

    public synchronized Proxy getLocalProxy() {
        if (nonNull(seleniumProxy)) {
            return seleniumProxy;
        } else {
            final var browserMobProxy = new BrowserMobProxyServer();
            if (BrowserProperties.IGNORE_SSL) {
                browserMobProxy.setTrustAllServers(true);
            }
            browserMobProxy.start(0);

            browserMobProxy.addRequestFilter((request, contents, messageInfo) -> {
                request.headers().add("sbm-forward-feature-version-stf", EnvironmentProperties.PROXY_HEADER_FORWARD_TO);
                return null;
            });
            return seleniumProxy = ClientUtil.createSeleniumProxy(browserMobProxy);
        }
    }
}
