package ru.instamart.grpc.helper;

import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.TextFormat;
import io.qameta.allure.Step;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AllureHelper {

    @NonNull
    private String getStringWithCyrillic(final MessageOrBuilder message) {
        return TextFormat.printer().escapingNonAscii(false).printToString(message);
    }

    @Step("Request")
    private void requestStep(final String request) {
    }

    public void showRequest(final MessageOrBuilder request) {
        requestStep(getStringWithCyrillic(request));
    }

    @Step("Response")
    private void responseStep(final String response) {
    }

    public void showResponse(final MessageOrBuilder response) {
        responseStep(getStringWithCyrillic(response));
    }
}
