package ru.instamart.reforged.core.provider;

import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.HttpCommandExecutor;

import java.net.URL;
import java.util.Map;

public final class HttpCommandExecutorExtension extends HttpCommandExecutor {

    public HttpCommandExecutorExtension(
            Map<String, CommandInfo> additionalCommands,
            URL addressOfRemoteServer) {
        super(additionalCommands, addressOfRemoteServer);
    }
}
