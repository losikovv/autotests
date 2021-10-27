package ru.instamart.grpc.helper;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.qameta.allure.Step;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import navigation.Navigation;
import ru.instamart.kraken.config.CoreProperties;

import java.util.List;

@Slf4j
public class GrpcHelper {

    public ManagedChannel createChannel(final String name) {
        return createChannel(name, CoreProperties.GRPC_PORT);
    }

    @Step
    @NonNull
    public ManagedChannel createChannel(final String name, final Integer port) {
        log.debug("Creating channel: " + name);
        return ManagedChannelBuilder.forAddress(name, port).build();
    }

    /**
     * Ищем категорию по id в списке категорий в микросервисе Navigation
     */
    public boolean findCategoryById(List<Navigation.MenuCategory> categories, String id) {
        log.debug("Ищем категорию по id {} в списке категорий", id);
        for (var category : categories) {
            if (category.getId().equals(id)) {
                log.debug("Найдена категория {}", category);
                return true;
            }
        }
        return false;
    }
}
