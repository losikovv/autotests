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
import tagmanager.TagManagerGrpc;
import tagmanager.Tagmanager;

import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertTrue;

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

    @Step("Получение привязок по тэгу")
    public List<Tagmanager.TagBind> getBindsByTag(int tagId) {
        var channel = createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_TAG_MANAGER);
        var client = TagManagerGrpc.newBlockingStub(channel);

        var request = Tagmanager.GetBindsByTagRequest
                .newBuilder()
                .setTagId(tagId)
                .build();

        var binds = client.getBindsByTag(request).getBindsList();
        channel.shutdown();
        return binds;
    }

    @Step("Получение привязок по айтему")
    public List<Tagmanager.TagBind> getBindsByItem(int ownerId, String itemId) {
        var channel = createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_TAG_MANAGER);
        var client = TagManagerGrpc.newBlockingStub(channel);

        var request = Tagmanager.GetBindsByItemRequest
                .newBuilder()
                .setOwnerId(ownerId)
                .setItemId(itemId)
                .build();

        var binds = client.getBindsByItem(request).getBindsList();
        channel.shutdown();
        return binds;
    }

    @Step("Создание владельца тэгов")
    public int createOwner() {
        var channel = createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_TAG_MANAGER);
        var client = TagManagerGrpc.newBlockingStub(channel);

        var request = Tagmanager.CreateOwnerRequest
                .newBuilder()
                .setName(UUID.randomUUID().toString())
                .setDescription("ownerDescription")
                .build();

        var ownerId = client.createOwner(request).getId();

        channel.shutdown();
        return ownerId;
    }

    @Step("Создание тэга")
    public int createTag(int ownerId, String tagName) {
        var channel = createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_TAG_MANAGER);
        var client = TagManagerGrpc.newBlockingStub(channel);

        var request = Tagmanager.CreateTagRequest.newBuilder()
                .setName(tagName)
                .setOwnerId(ownerId)
                .build();

        var tagId = client.createTag(request).getId();
        channel.shutdown();
        return tagId;
    }

    @Step("Привязка тега")
    public void bindTag(int tagId, String itemId) {
        var channel = createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_TAG_MANAGER);
        var client = TagManagerGrpc.newBlockingStub(channel);

        var request = Tagmanager.BindTagRequest.newBuilder()
                .setTagId(tagId)
                .addBinds(Tagmanager.BindTagRequest.Bind.newBuilder()
                        .setItemId(itemId)
                        .setMeta("something")
                        .build())
                .build();

        assertTrue(client.bindTag(request).getOk(), "Тэг не привязался");
        channel.shutdown();
    }
}
