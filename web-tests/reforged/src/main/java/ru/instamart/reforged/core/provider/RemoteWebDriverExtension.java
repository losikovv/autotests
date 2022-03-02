package ru.instamart.reforged.core.provider;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.instamart.reforged.core.action.JsAction;

import java.util.Map;

public final class RemoteWebDriverExtension extends RemoteWebDriver {

    public RemoteWebDriverExtension(CommandExecutor executor, Capabilities capabilities) {
        super(executor, capabilities);
    }

    public <X> X getFullScreenshotAs(OutputType<X> outputType) {
        final var metrics = sendEvaluate();
        sendCommand("Emulation.setDeviceMetricsOverride", metrics);
        final var result = sendCommand("Page.captureScreenshot", ImmutableMap.of("format", "png", "fromSurface", true));
        sendCommand("Emulation.clearDeviceMetricsOverride", ImmutableMap.of());
        @SuppressWarnings("unchecked")
        final var base64EncodedPng = (String)((Map<String, ?>)result).get("data");
        return outputType.convertFromBase64Png(base64EncodedPng);
    }

    private Object sendCommand(String cmd, Object params) {
        return execute("sendCommand", ImmutableMap.of("cmd", cmd, "params", params)).getValue();
    }

    @SuppressWarnings("unchecked")
    private Object sendEvaluate() {
        final var response = sendCommand(
                "Runtime.evaluate", ImmutableMap.of("returnByValue", true, "expression", JsAction.JS_GET_PAGE_SIZE)
        );
        final var result = ((Map<String, ?>)response).get("result");
        return ((Map<String, ?>)result).get("value");
    }
}
