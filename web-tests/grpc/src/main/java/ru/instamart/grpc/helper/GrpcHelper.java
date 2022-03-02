package ru.instamart.grpc.helper;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.qameta.allure.Step;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import navigation.Navigation;
import navigation.NavigationServiceGrpc;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.grpc.interceptor.GrpcInterceptor;
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
        return ManagedChannelBuilder.forAddress(name, port).intercept(new GrpcInterceptor()).build();
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

    @Step("Получение категорий в {storeId} магазине, в {tenantId} тенанте")
    public List<Navigation.MenuCategory> getCategories(String storeId, String tenantId) {
        var channel = createChannel(GrpcContentHosts.PAAS_CONTENT_CATALOG_NAVIGATION);
        var client = NavigationServiceGrpc.newBlockingStub(channel);

        var request = Navigation.GetMenuTreeRequest
                .newBuilder()
                .setStoreId(storeId)
                .setTenantId(tenantId)
                .setTreeDepth(5)
                .build();

        var categories = client.getMenuTree(request).getCategoriesList();
        channel.shutdown();
        return categories;
    }
}
