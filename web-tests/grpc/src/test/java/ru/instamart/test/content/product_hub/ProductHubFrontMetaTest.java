package ru.instamart.test.content.product_hub;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import product_hub_front_meta.ProductHubFrontMetaGrpc;
import product_hub_front_meta.ProductHubFrontMetaOuterClass;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import io.qameta.allure.TmsLink;

import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("Product Hub Microservice")
@Feature("Product Hub Front Meta")
@Slf4j
public class ProductHubFrontMetaTest extends GrpcBase {
    private ProductHubFrontMetaGrpc.ProductHubFrontMetaBlockingStub client;
    private final Set<String> expectedCategoryIds = new HashSet<>();
    private final Set<String> expectedRetailerIds = new HashSet<>();
    private final Set<String> expectedAttributeKeys = new HashSet<>();
    private String expectedDictionaryKey;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_PRODUCT_HUB_FRONT);
        client = ProductHubFrontMetaGrpc.newBlockingStub(channel);
    }

    @TmsLink("186")
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

        response.getCategoriesList().forEach(category -> expectedCategoryIds.add(category.getId()));
        log.debug("expected category IDs:" + expectedCategoryIds);

        response.getCategoriesList().forEach(category -> expectedRetailerIds.addAll(category.getRetailerIdsList()));
        log.debug("expected retailer IDs:" + expectedRetailerIds);
    }

    @TmsLink("187")
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

    @TmsLink("188")
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

    @TmsLink("189")
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

    @TmsLink("190")
    @Test(  description = "Получение всех категорий ритейлеров по id категории",
            groups = "grpc-product-hub",
            dependsOnMethods = "getAllCategories")
    public void getCategoriesByCategoryIDs() {
        var request = ProductHubFrontMetaOuterClass
                .GetCategoriesByCategoryIDsRequest.newBuilder()
                .addAllCategoryIds(expectedCategoryIds)
                .build();
        var response = client.getCategoriesByCategoryIDs(request);

        Set<String> categoryIdsFromResponse = new HashSet<>();
        response.getCategoriesList().forEach(category -> categoryIdsFromResponse.add(category.getId()));
        log.debug("category IDs from response:" + categoryIdsFromResponse);

        SoftAssert softAssert = new SoftAssert();
        expectedCategoryIds.forEach(expected -> softAssert.assertTrue(categoryIdsFromResponse.contains(expected),
                "Одна из категорий не вернулась в ответе: " + expected));
        response.getCategoriesList().forEach(category -> softAssert.assertTrue(expectedCategoryIds.contains(category.getId()),
                "Лишняя категория в ответе: " + category.getId()));
        softAssert.assertAll();
    }

    @TmsLink("191")
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

    @TmsLink("192")
    @Test(  description = "Получение фильтров по id категории",
            groups = "grpc-product-hub",
            dependsOnMethods = "getAllCategories")
    public void getCategoryFiltersByCategoryIDs() {
        var request = ProductHubFrontMetaOuterClass
                .GetCategoryFiltersByCategoryIDsRequest.newBuilder()
                .addAllCategoryIds(expectedCategoryIds)
                .build();
        var response = client.getCategoryFiltersByCategoryIDs(request);

        SoftAssert softAssert = new SoftAssert();
        response.getCategoryFiltersList().forEach(categoryFilter -> softAssert.assertTrue(expectedCategoryIds.contains(categoryFilter.getCategoryId()),
                "Лишняя категория в ответе: " + categoryFilter.getCategoryId()));
        softAssert.assertAll();

        response.getCategoryFiltersList().forEach(categoryFilter -> expectedAttributeKeys.addAll(categoryFilter.getAttributeKeysList()));
        log.debug("expected attribute keys:" + expectedAttributeKeys);
    }

    @TmsLink("193")
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

    @TmsLink("194")
    @Test(  description = "Получение аттрибутов по ключу аттрибутов",
            groups = "grpc-product-hub",
            dependsOnMethods = "getCategoryFiltersByCategoryIDs")
    public void getAttributesByKeys() {
        var request = ProductHubFrontMetaOuterClass
                .GetAttributesByKeysRequest.newBuilder()
                .addAllAttributeKeys(expectedAttributeKeys)
                .build();
        var response = client.getAttributesByKeys(request);

        Set<String> attributeKeysFromResponse = new HashSet<>();
        response.getAttributesList().forEach(attribute -> attributeKeysFromResponse.add(attribute.getKey()));
        log.debug("attribute keys from response:" + attributeKeysFromResponse);

        SoftAssert softAssert = new SoftAssert();
        expectedAttributeKeys.forEach(expected -> softAssert.assertTrue(attributeKeysFromResponse.contains(expected),
                "Один из атрибутов не вернулся в ответе: " + expected));
        response.getAttributesList().forEach(attribute -> softAssert.assertTrue(expectedAttributeKeys.contains(attribute.getKey()),
                "Лишний атрибут в ответе: " + attribute.getKey()));
        softAssert.assertAll();
    }

    @TmsLink("195")
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

    @TmsLink("196")
    @Test(  description = "Получение всех аттрибутов в указанном диапазоне продуктов",
            groups = "grpc-product-hub")
    public void getAllAttributes() {
        var request = ProductHubFrontMetaOuterClass
                .GetAllAttributesRequest.newBuilder()
                .setLimit(10)
                .setOffset(0)
                .build();
        var response = client.getAllAttributes(request);

        assertEquals(response.getAttributesCount(), 10, "Вернулось другое количество атрибутов");
    }

    @TmsLink("197")
    @Test(  description = "Получение всех аттрибутов в указанном диапазоне продуктов без поля \"limit\"",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: limit should be greater than zero")
    public void getAllAttributesWithoutLimit() {
        var request = ProductHubFrontMetaOuterClass
                .GetAllAttributesRequest.newBuilder()
                .setOffset(0)
                .build();
        client.getAllAttributes(request);
    }

    @TmsLink("198")
    @Test(  description = "Получение всех словарей продуктов в заданном диапазоне",
            groups = "grpc-product-hub")
    public void getAllDictionaries() {
        var request = ProductHubFrontMetaOuterClass
                .GetAllDictionariesRequest.newBuilder()
                .setLimit(10)
                .setOffset(0)
                .build();
        var response = client.getAllDictionaries(request);

        assertEquals(response.getDictionariesCount(), 10, "Вернулось другое количество словарей");

        expectedDictionaryKey = response.getDictionaries(0).getKey();
    }

    @TmsLink("199")
    @Test(  description = "Получение всех словарей продуктов в заданном диапазоне без поля \"limit\"",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: limit should be greater than zero")
    public void getAllDictionariesWithoutLimit() {
        var request = ProductHubFrontMetaOuterClass
                .GetAllDictionariesRequest.newBuilder()
                .setOffset(0)
                .build();
        client.getAllDictionaries(request);
    }

    @TmsLink("200")
    @Test(  description = "Проверка получения словарных ценностей по ключу словаря",
            groups = "grpc-product-hub",
            dependsOnMethods = "getAllDictionaries")
    public void getAllDictionaryValues() {
        var request = ProductHubFrontMetaOuterClass
                .GetAllDictionaryValuesRequest.newBuilder()
                .setDictionaryKey(expectedDictionaryKey)
                .setLimit(10)
                .setOffset(0)
                .build();
        var response = client.getAllDictionaryValues(request);

        assertFalse(response.getDictionaryValuesList().isEmpty(), "Не вернулись словарные ценности");
    }

    @TmsLink("201")
    @Test(  description = "Проверка получения словарных ценностей по ключу словаря с пустым значением в поле \"dictionary_key\"",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: dictionary key can't be empty")
    public void getAllDictionaryValuesWithEmptyKey() {
        var request = ProductHubFrontMetaOuterClass
                .GetAllDictionaryValuesRequest.newBuilder()
                .setDictionaryKey("")
                .setLimit(1)
                .setOffset(0)
                .build();
        client.getAllDictionaryValues(request);
    }

    @TmsLink("202")
    @Test(  description = "Получение данных по магазинам с помощью id ритейлера",
            groups = "grpc-product-hub",
            dependsOnMethods = "getAllCategories")
    public void getRetailerStores() {
        var request = ProductHubFrontMetaOuterClass
                .GetRetailerStoresRequest.newBuilder()
                .addAllRetailerIds(expectedRetailerIds)
                .build();
        var response = client.getRetailerStores(request);

        SoftAssert softAssert = new SoftAssert();
        response.getRetailerStoresList().forEach(store ->
                softAssert.assertTrue(expectedRetailerIds.contains(store.getRetailerId()),
                "Лишний ритейлер в ответе: " + store.getRetailerId()));
        softAssert.assertAll();
    }

    @TmsLink("203")
    @Test(  description = "Получение данных по магазинам с помощью пустого значения в поле \"retailer_ids\"",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: retailer_id number 0 is empty")
    public void getRetailerStoresWithEmptyIds() {
        var request = ProductHubFrontMetaOuterClass
                .GetRetailerStoresRequest.newBuilder()
                .addRetailerIds("")
                .build();
        client.getRetailerStores(request);
    }
}
