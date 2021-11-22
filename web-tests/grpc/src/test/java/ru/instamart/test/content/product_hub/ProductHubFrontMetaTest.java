package ru.instamart.test.content.product_hub;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import product_hub_front_meta.ProductHubFrontMetaGrpc;
import product_hub_front_meta.ProductHubFrontMetaOuterClass;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcHosts;

import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("Product Hub Microservice")
@Feature("Product Hub Front Meta")
@Slf4j
public class ProductHubFrontMetaTest extends GrpcBase {
    private ProductHubFrontMetaGrpc.ProductHubFrontMetaBlockingStub client;
    private final Set<String> categoryIds = new HashSet<>();
    private final Set<String> retailerIds = new HashSet<>();
    private final Set<String> attributeKeys = new HashSet<>();
    private final Set<String> dictionaryKeys = new HashSet<>();

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcHosts.PAAS_CONTENT_PRODUCT_HUB_FRONT);
        client = ProductHubFrontMetaGrpc.newBlockingStub(channel);
    }

    @CaseId(186)
    @Test(  description = "Получение всех категорий",
            groups = "grpc-product-hub")
    public void getAllCategories() {
        var request = ProductHubFrontMetaOuterClass
                .GetAllCategoriesRequest.newBuilder()
                .setLimit(100)
                .setOffset(20)
                .build();
        var response = client.getAllCategories(request);

        assertEquals(response.getCategoriesList().size(), 100, "Количество категорий вернулось больше или меньше лимита");

        response.getCategoriesList().forEach(category -> categoryIds.add(category.getId()));
        log.debug("expected category IDs:" + categoryIds);

        response.getCategoriesList().forEach(category -> retailerIds.addAll(category.getRetailerIdsList()));
        log.debug("expected retailer IDs:" + retailerIds);
    }

    @CaseId(187)
    @Test(  description = "Получение всех категорий без указания поля \"limit\"",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: limit should be greater than zero")
    public void getAllCategoriesWithoutLimit() {
        var request = ProductHubFrontMetaOuterClass
                .GetAllCategoriesRequest.newBuilder()
                .setOffset(20)
                .build();
        client.getAllCategories(request);
    }

    @CaseId(188)
    @Test(  description = "Получение всех магазинов по диапазону продуктов",
            groups = "grpc-product-hub")
    public void getAllCategoriesWithStores() {
        var request = ProductHubFrontMetaOuterClass
                .GetAllCategoriesWithStoresRequest.newBuilder()
                .setLimit(1)
                .setOffset(0)
                .build();
        var response = client.getAllCategoriesWithStores(request);

        assertEquals(response.getCategoriesList().size(), 1, "Количество категорий вернулось больше или меньше лимита");
    }

    @CaseId(189)
    @Test(  description = "Получение всех магазинов по диапазону продуктов без поля \"limit\"",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: limit should be greater than zero")
    public void getAllCategoriesWithStoresWithoutLimit() {
        var request = ProductHubFrontMetaOuterClass
                .GetAllCategoriesWithStoresRequest.newBuilder()
                .setOffset(0)
                .setLimit(0)
                .build();
        client.getAllCategoriesWithStores(request);
    }

    @CaseId(190)
    @Test(  description = "Получение всех категорий ритейлеров по id категории",
            groups = "grpc-product-hub",
            dependsOnMethods = "getAllCategories")
    public void getCategoriesByCategoryIDs() {
        var request = ProductHubFrontMetaOuterClass
                .GetCategoriesByCategoryIDsRequest.newBuilder()
                .addAllCategoryIds(categoryIds)
                .build();
        var response = client.getCategoriesByCategoryIDs(request);

        Set<String> categoryIdsFromResponse = new HashSet<>();
        response.getCategoriesList().forEach(category -> categoryIdsFromResponse.add(category.getId()));
        log.debug("category IDs from response:" + categoryIdsFromResponse);
        categoryIds.forEach(categoryId -> assertTrue(categoryIdsFromResponse.contains(categoryId),
                "Одна из категорий не вернулась в ответе"));

        response.getCategoriesList().forEach(category -> assertTrue(categoryIds.contains(category.getId()),
                "Лишняя категория в ответе"));
    }

    @CaseId(191)
    @Test(  description = "Получение всех категорий ритейлеров по id категории с пустым значением поля \"category_ids\"",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: category_ids is empty")
    public void getCategoriesByCategoryIDsWithoutCategoryID() {
        var request = ProductHubFrontMetaOuterClass
                .GetCategoriesByCategoryIDsRequest.newBuilder()
                .build();
        client.getCategoriesByCategoryIDs(request);
    }

    @CaseId(192)
    @Test(  description = "Получение фильтров по id категории",
            groups = "grpc-product-hub",
            dependsOnMethods = "getAllCategories")
    public void getCategoryFiltersByCategoryIDs() {
        var request = ProductHubFrontMetaOuterClass
                .GetCategoryFiltersByCategoryIDsRequest.newBuilder()
                .addAllCategoryIds(categoryIds)
                .build();
        var response = client.getCategoryFiltersByCategoryIDs(request);

        response.getCategoryFiltersList().forEach(categoryFilter -> assertTrue(categoryIds.contains(categoryFilter.getCategoryId()),
                "Лишняя категория в ответе"));

        response.getCategoryFiltersList().forEach(categoryFilter -> attributeKeys.addAll(categoryFilter.getAttributeKeysList()));
        log.debug("expected attribute keys:" + attributeKeys);
    }

    @CaseId(193)
    @Test(  description = "Получение фильтров по id категории с пустым значением поля \"category_ids\"",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: category ids array is empty")
    public void getCategoryFiltersByCategoryIDsWithoutCategoryID() {
        var request = ProductHubFrontMetaOuterClass
                .GetCategoryFiltersByCategoryIDsRequest.newBuilder()
                .build();
        client.getCategoryFiltersByCategoryIDs(request);
    }

    @CaseId(194)
    @Test(  description = "Получение аттрибутов по ключу аттрибутов",
            groups = "grpc-product-hub",
            dependsOnMethods = "getCategoryFiltersByCategoryIDs")
    public void getAttributesByKeys() {
        var request = ProductHubFrontMetaOuterClass
                .GetAttributesByKeysRequest.newBuilder()
                .addAllAttributeKeys(attributeKeys)
                .build();
        var response = client.getAttributesByKeys(request);

        Set<String> attributeKeysFromResponse = new HashSet<>();
        response.getAttributesList().forEach(attribute -> attributeKeysFromResponse.add(attribute.getKey()));
        log.debug("attribute keys from response:" + attributeKeysFromResponse);
        attributeKeys.forEach(categoryId -> assertTrue(attributeKeysFromResponse.contains(categoryId),
                "Один из атрибутов не вернулся в ответе"));


        response.getAttributesList().forEach(attribute -> assertTrue(attributeKeys.contains(attribute.getKey()),
                "Лишний атрибут в ответе"));

        response.getAttributesList().forEach(attribute -> dictionaryKeys.add(attribute.getDictionaryKey()));
        log.debug("expected dictionary keys:" + dictionaryKeys);
    }

    @CaseId(195)
    @Test(  description = "Получение аттрибутов по ключу аттрибутов без заданного значения в поле \"attribute_keys\"",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: attributes ids array is empty")
    public void getAttributesByKeysWithoutAttributeKeys() {
        var request = ProductHubFrontMetaOuterClass
                .GetAttributesByKeysRequest.newBuilder()
                .build();
        client.getAttributesByKeys(request);
    }
}
