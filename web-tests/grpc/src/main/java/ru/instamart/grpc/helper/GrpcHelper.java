package ru.instamart.grpc.helper;

import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.TextFormat;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.qameta.allure.Step;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import navigation.Navigation;

@Slf4j
public class GrpcHelper {

    @Step
    @NonNull
    public ManagedChannel createChannel(final String name, final Integer port) {
        log.debug("Creating channel: " + name);
        return ManagedChannelBuilder.forAddress(name, port).build();
    }

    @NonNull
    public String getStringWithCyrillic(final MessageOrBuilder message) {
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

    /**
     * Ищем категорию по id в списке категорий в микросервисе Navigation
     */
    public boolean findCategoryById(java.util.List<Navigation.MenuCategory> categories, String id) {
        for (var category : categories) {
            if (category.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
