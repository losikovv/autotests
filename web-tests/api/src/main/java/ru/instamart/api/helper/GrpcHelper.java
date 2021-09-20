package ru.instamart.api.helper;

import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.TextFormat;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.qameta.allure.Step;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GrpcHelper {

    @Step
    @NonNull
    public ManagedChannel createChannel(final String name, final Integer port) {
        log.debug("Creating channel: " + name);
        return ManagedChannelBuilder.forAddress(name, port).build();
    }

    @NonNull
    private String getStringWithCyrillic(final MessageOrBuilder message) {
        return TextFormat.printer().escapingNonAscii(false).printToString(message);
    }

    @Step("Request")
    public void showRequestInAllure(final MessageOrBuilder request) {
    }

    @Step("Response")
    private void responseStep(final String response) {
    }

    public void showResponseInAllure(final MessageOrBuilder response) {
        responseStep(getStringWithCyrillic(response));
    }
}
