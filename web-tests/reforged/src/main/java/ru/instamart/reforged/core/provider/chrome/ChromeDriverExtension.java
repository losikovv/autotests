package ru.instamart.reforged.core.provider.chrome;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpMethod;
import ru.instamart.reforged.core.action.JsAction;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Slf4j
public final class ChromeDriverExtension extends ChromeDriver {

    public ChromeDriverExtension() {
        this(new ChromeOptions());
    }

    public ChromeDriverExtension(ChromeOptions options) {
        this(ChromeDriverService.createDefaultService(), options);
    }

    public ChromeDriverExtension(ChromeDriverService service, ChromeOptions options) {
        super(service, options);
        init();
    }

    private void init() {
        try {
            final var cmd = new CommandInfo("/session/:sessionId/chromium/send_command_and_get_result", HttpMethod.POST);
            final var defineCommand = HttpCommandExecutor.class.getDeclaredMethod("defineCommand", String.class, CommandInfo.class);
            defineCommand.setAccessible(true);
            defineCommand.invoke(super.getCommandExecutor(), "sendCommand", cmd);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            log.error("FATAL: Can't invoke method 'defineCommand'", e);
        }
    }

    public <X> X getFullScreenshotAs(OutputType<X> outputType) {
        sendCommand("Emulation.setDeviceMetricsOverride", sendEvaluate());
        final var result = sendCommand(
                "Page.captureScreenshot", ImmutableMap.of("format", "png", "fromSurface", true)
        );
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
