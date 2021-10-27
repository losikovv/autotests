package ru.instamart.test.content.product_hub;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import product_hub_back.ProductHubBackGrpc;
import product_hub_front_data.ProductHubFrontDataGrpc;
import product_hub_front_data.ProductHubFrontDataOuterClass;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcHosts;
import ru.instamart.kraken.data.Generate;

import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static product_hub_back.ProductHubBackOuterClass.*;

@Epic("Product Hub Microservice")
@Feature("Product Hub Back")
public class ProductHubBackTest extends GrpcBase {
    private ProductHubBackGrpc.ProductHubBackBlockingStub client;
    private final String categoryId = Generate.digitalString(9);
    private final long productSku = Generate.longInt(9);

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcHosts.PAAS_CONTENT_PRODUCT_HUB_BACK);
        client = ProductHubBackGrpc.newBlockingStub(channel);
    }

    @Story("Категории")
    @CaseId(7)
    @Test(  description = "Создание категории для продукта",
            groups = "grpc-product-hub")
    public void saveCategories() {
        SaveCategoriesRequest request = SaveCategoriesRequest
                .newBuilder()
                .addCategories(Category.newBuilder()
                        .setId(categoryId)
                        .setName("Autotest Category")
                        .setParentId("")
                        .addRetailerIds("3")
                        .addCategoryData(Data.newBuilder()
                                .setKey("suggested")
                                .setType(ValueType.BOOL)
                                .setIsMultiValue(false)
                                .addValues("true")
                                .build())
                        .addCategoryData(Data.newBuilder()
                                .setKey("is_promoted_category")
                                .setType(ValueType.BOOL)
                                .setIsMultiValue(false)
                                .addValues("false")
                                .build())
                        .addCategoryData(Data.newBuilder()
                                .setKey("is_duplicate_to_promoted_category")
                                .setType(ValueType.BOOL)
                                .setIsMultiValue(false)
                                .addValues("false")
                                .build())
                        .addCategoryData(Data.newBuilder()
                                .setKey("is_special_offer_category")
                                .setType(ValueType.BOOL)
                                .setIsMultiValue(false)
                                .addValues("false")
                                .build())
                        .addCategoryData(Data.newBuilder()
                                .setKey("hide_from_navigation")
                                .setType(ValueType.BOOL)
                                .setIsMultiValue(false)
                                .addValues("false")
                                .build())
                        .addCategoryData(Data.newBuilder()
                                .setKey("view_as_list")
                                .setType(ValueType.BOOL)
                                .setIsMultiValue(false)
                                .addValues("false")
                                .build())
                        .addCategoryData(Data.newBuilder()
                                .setKey("is_marketing_category")
                                .setType(ValueType.BOOL)
                                .setIsMultiValue(false)
                                .addValues("false")
                                .build())
                        .addCategoryData(Data.newBuilder()
                                .setKey("pass_in_feeds")
                                .setType(ValueType.BOOL)
                                .setIsMultiValue(false)
                                .addValues("false")
                                .build())
                        .addCategoryData(Data.newBuilder()
                                .setKey("position")
                                .setType(ValueType.INTEGER)
                                .setIsMultiValue(false)
                                .addValues("1")
                                .build())
                        .addCategoryData(Data.newBuilder()
                                .setKey("permalink")
                                .setType(ValueType.STRING)
                                .setIsMultiValue(false)
                                .addValues("link")
                                .build())
                        .addTenantIds("sbermarket")
                        .setStatus(Status.ENABLE)
                        .build())
                .build();
        allure.showRequest(request);

        SaveCategoriesResponse response = client.saveCategories(request);
        allure.showResponse(response);

        assertEquals(response.getSaveCategoriesCount(), 1,
                "Вернулось другое количество созданных категорий");
    }

    @Story("Категории")
    @CaseId(8)
    @Test(  description = "Создание категории для продукта без обязательного поля \"id\"",
            groups = "grpc-product-hub")
    public void saveCategoriesWithoutId() {
        SaveCategoriesRequest request = SaveCategoriesRequest.newBuilder()
                .addCategories(Category.newBuilder()
                        .setName("Autotest")
                        .setParentId("")
                        .addRetailerIds("3")
                        .addTenantIds("sbermarket")
                        .setStatus(Status.ENABLE)
                        .build())
                .build();
        allure.showRequest(request);

        SaveCategoriesResponse response = null;
        try {
            response = client.saveCategories(request);
        } catch (StatusRuntimeException e) {
            assertEquals(e.getMessage(), "INVALID_ARGUMENT: category number=0 has empty id",
                    "Некорректное сообщение об ошибке");
            Allure.step(e.getMessage());
        }
        assertNull(response, "Не вернулась ошибка");
    }

    @Story("Словари")
    @CaseId(9)
    @Test(  description = "Создание словарей для нового продукта",
            groups = "grpc-product-hub")
    public void saveDictionaries() {
        SaveDictionariesRequest request = SaveDictionariesRequest.newBuilder()
                .addDictionaries(Dictionary.newBuilder()
                        .setKey("Autotest_key")
                        .setName("Autotest Dictionary")
                        .setType(ValueType.STRING)
                        .addValues(DictionaryValue.newBuilder()
                                .setId("Autotest_value_id")
                                .setValue("тестовый коммент")
                                .addValueData(Data.newBuilder()
                                        .setKey("Autotest_value_key")
                                        .setType(ValueType.STRING)
                                        .setIsMultiValue(true)
                                        .addValues("ещё один тест")
                                        .build())
                                .addAdditionalDictionaryValues(DictionaryValue.AdditionalDictionaryValue.newBuilder()
                                        .setDictionaryKey("Autotest_additional_key")
                                        .setDictionaryValueId("Autotest_additional_id")
                                        .build())
                                .build())
                        .build())
                .build();
        allure.showRequest(request);

        SaveDictionariesResponse response = client.saveDictionaries(request);
        allure.showResponse(response);

        assertEquals(response.getSaveDictionariesCount(), 1,
                "Вернулось другое количество созданных словарей");
    }

    @Story("Словари")
    @CaseId(10)
    @Test(  description = "Создание словарей для нового продукта без обязательного поля \"key\"",
            groups = "grpc-product-hub")
    public void saveDictionariesWithoutKey() {
        SaveDictionariesRequest request = SaveDictionariesRequest.newBuilder()
                .addDictionaries(Dictionary.newBuilder()
                        .setName("Autotest Dictionary")
                        .setType(ValueType.STRING)
                        .build())
                .build();
        allure.showRequest(request);

        SaveDictionariesResponse response = null;
        try {
            response = client.saveDictionaries(request);
        } catch (StatusRuntimeException e) {
            assertEquals(e.getMessage(), "INVALID_ARGUMENT: dictionary number=0 has empty key",
                    "Некорректное сообщение об ошибке");
            Allure.step(e.getMessage());
        }
        assertNull(response, "Не вернулась ошибка");
    }

    @Story("Атрибуты")
    @CaseId(11)
    @Test(  description = "Создание аттрибутов для нового продукта",
            groups = "grpc-product-hub")
    public void saveAttributes() {
        SaveAttributesRequest request = SaveAttributesRequest.newBuilder()
                .addAttributes(Attribute.newBuilder()
                        .setKey("Autotest_key")
                        .setDictionaryKey("Autotest_key")
                        .setName("Autotest Attribute")
                        .setType(ValueType.INTEGER)
                        .setIsMultiValue(false)
                        .putFlags("show_as_characteristic", true)
                        .addAttributeData(Data.newBuilder()
                                .setKey("Autotest_data_key")
                                .setType(ValueType.STRING)
                                .setIsMultiValue(true)
                                .addValues("Коммент даты атрибута")
                                .build())
                        .build())
                .build();
        allure.showRequest(request);

        SaveAttributesResponse response = client.saveAttributes(request);
        allure.showResponse(response);

        assertEquals(response.getSaveAttributesCount(), 1,
                "Вернулось другое количество созданных атрибутов");
    }

    @Story("Атрибуты")
    @CaseId(12)
    @Test(  description = "Создание аттрибутов для нового продукта без обязательного поля \"key\"",
            groups = "grpc-product-hub")
    public void saveAttributesWithoutKey() {
        SaveAttributesRequest request = SaveAttributesRequest.newBuilder()
                .addAttributes(Attribute.newBuilder()
                        .setDictionaryKey("Autotest_key")
                        .setName("Autotest Attribute")
                        .setType(ValueType.INTEGER)
                        .setIsMultiValue(false)
                        .putFlags("show_as_characteristic", true)
                        .build())
                .build();
        allure.showRequest(request);

        SaveAttributesResponse response = null;
        try {
            response = client.saveAttributes(request);
        } catch (StatusRuntimeException e) {
            assertEquals(e.getMessage(), "INVALID_ARGUMENT: attribute number=0 has empty key",
                    "Некорректное сообщение об ошибке");
            Allure.step(e.getMessage());
        }
        assertNull(response, "Не вернулась ошибка");
    }

    @Story("Продукты")
    @CaseId(13)
    @Test(description = "Создание продукта",
            groups = {"grpc-product-hub"})
    public void saveProducts() {
        SaveProductsRequest request = SaveProductsRequest.newBuilder()
                .addProducts(Product
                        .newBuilder()
                        .setSku(productSku)
                        .setName("Autotest Product")
                        .addCategoryIds(categoryId)
                        .addAttributeValues(AttributeValue.newBuilder()
                                .setAttributeKey("product_unit")
                                .setValue("value")
                                .build())
                        .addAttributeValues(AttributeValue.newBuilder()
                                .setAttributeKey("permalink")
                                .setValue("value")
                                .build())
                        .addAttributeValues(AttributeValue.newBuilder()
                                .setAttributeKey("term_of_sale")
                                .setValue("value")
                                .build())
                        .addAttributeValues(AttributeValue.newBuilder()
                                .setAttributeKey("master_category_id")
                                .setValue("value")
                                .build())
                        .setStatus(Status.ENABLE)
                        .build())
                .build();
        allure.showRequest(request);

        SaveProductsResponse response = client.saveProducts(request);
        allure.showResponse(response);

        assertEquals(response.getSaveProductsCount(), 1,
                "Вернулось другое количество созданных продуктов");
    }

    @Story("Продукты")
    @CaseId(14)
    @Test(description = "Создание нового продукта в системе без обязательного поля \"sku\"",
            groups = {"grpc-product-hub"})
    public void saveProductsWithoutSKU() {
        SaveProductsRequest request = SaveProductsRequest.newBuilder()
                .addProducts(Product
                        .newBuilder()
                        .setName("Autotest Product")
                        .addCategoryIds(categoryId)
                        .setStatus(Status.ENABLE)
                        .build())
                .build();
        allure.showRequest(request);

        SaveProductsResponse response = null;
        try {
            response = client.saveProducts(request);
        } catch (StatusRuntimeException e) {
            assertEquals(e.getMessage(), "INVALID_ARGUMENT: product number=0 has empty sku",
                    "Некорректное сообщение об ошибке");
            Allure.step(e.getMessage());
        }
        assertNull(response, "Не вернулась ошибка");
    }

    @Story("Стоки")
    @CaseId(15)
    @Test(description = "Добавление стоков к созданному новому продукту",
            groups = {"grpc-product-hub"},
            dependsOnMethods = "saveProducts")
    public void saveStocks() {
        SaveStocksRequest request = SaveStocksRequest.newBuilder()
                .addStocks(Stock.newBuilder()
                        .setSku(productSku)
                        .setStoreId("1")
                        .setRetailerId("3")
                        .setRetailerSku("266353")
                        .setStock(666)
                        .addStockData(Data.newBuilder()
                                .setKey("max_stock")
                                .setType(ValueType.INTEGER)
                                .setIsMultiValue(false)
                                .addValues("1000")
                                .build())
                        .setStatus(Status.ENABLE)
                        .build())
                .build();
        allure.showRequest(request);

        SaveStocksResponse response = client.saveStocks(request);
        allure.showResponse(response);

        assertEquals(response.getSaveStocksCount(), 1,
                "Вернулось другое количество созданных стоков");
    }

    @Story("Стоки")
    @CaseId(16)
    @Test(description = "Добавление стоков к созданному новому продукту без поля \"sku\"",
            groups = {"grpc-product-hub"})
    public void saveStocksWithoutSKU() {
        SaveStocksRequest request = SaveStocksRequest.newBuilder()
                .addStocks(Stock.newBuilder()
                        .setStoreId("1")
                        .setRetailerId("3")
                        .setRetailerSku("266353")
                        .setStock(666)
                        .setStatus(Status.ENABLE)
                        .build())
                .build();
        allure.showRequest(request);

        SaveStocksResponse response = null;
        try {
            response = client.saveStocks(request);
        } catch (StatusRuntimeException e) {
            assertEquals(e.getMessage(), "INVALID_ARGUMENT: stock number=0 has empty sku",
                    "Некорректное сообщение об ошибке");
            Allure.step(e.getMessage());
        }
        assertNull(response, "Не вернулась ошибка");
    }

    @Story("Цены")
    @CaseId(17)
    @Test(description = "Добавление стоимости продукту",
            groups = {"grpc-product-hub"},
            dependsOnMethods = "saveProducts")
    public void savePrices() {
        SavePricesRequest request = SavePricesRequest.newBuilder()
                .addPrices(Price.newBuilder()
                        .setSku(productSku)
                        .setStoreId("1")
                        .setTenantId("sbermarket")
                        .setRetailerId("3")
                        .setRetailerSku("266353")
                        .setPrice(666)
                        .addPriceData(Data.newBuilder()
                                .setKey("vat_rate")
                                .addValues("20")
                                .setType(ValueType.INTEGER)
                                .build())
                        .addPriceData(Data.newBuilder()
                                .setKey("discount")
                                .addValues("100")
                                .setType(ValueType.DOUBLE)
                                .build())
                        .addPriceData(Data.newBuilder()
                                .setKey("offer_price")
                                .addValues("20")
                                .setType(ValueType.DOUBLE)
                                .build())
                        .addPriceData(Data.newBuilder()
                                .setKey("discount_starts_at")
                                .addValues(LocalDateTime.now().toString())
                                .setType(ValueType.DATETIME)
                                .build())
                        .addPriceData(Data.newBuilder()
                                .setKey("discount_ends_at")
                                .addValues(LocalDateTime.now().plusWeeks(1).toString())
                                .setType(ValueType.DATETIME)
                                .build())
                        .addPriceData(Data.newBuilder()
                                .setKey("retailer_price")
                                .addValues("566")
                                .setType(ValueType.DOUBLE)
                                .build())
                        .addPriceData(Data.newBuilder()
                                .setKey("retailer_offer_price")
                                .addValues("466")
                                .setType(ValueType.DOUBLE)
                                .build())
                        .setStatus(Status.ENABLE)
                        .build())
                .build();
        allure.showRequest(request);

        SavePricesResponse response = client.savePrices(request);
        allure.showResponse(response);

        assertEquals(response.getSavePricesCount(), 1,
                "Вернулось другое количество созданных цен");
    }

    @Story("Цены")
    @CaseId(18)
    @Test(description = "Добавление стоимости продукту",
            groups = {"grpc-product-hub"})
    public void savePricesWithoutSKU() {
        SavePricesRequest request = SavePricesRequest.newBuilder()
                .addPrices(Price.newBuilder()
                        .setStoreId("1")
                        .setTenantId("sbermarket")
                        .setRetailerId("3")
                        .setRetailerSku("266353")
                        .setPrice(666)
                        .setStatus(Status.ENABLE)
                        .build())
                .build();
        allure.showRequest(request);

        SavePricesResponse response = null;
        try {
            response = client.savePrices(request);
        } catch (StatusRuntimeException e) {
            assertEquals(e.getMessage(), "INVALID_ARGUMENT: price number=0 has empty sku",
                    "Некорректное сообщение об ошибке");
            Allure.step(e.getMessage());
        }
        assertNull(response, "Не вернулась ошибка");
    }

    @Story("Товарные предложения")
    @CaseId(19)
    @Test(description = "Добавление предложений к продукту",
            groups = {"grpc-product-hub"},
            dependsOnMethods = "saveProducts")
    public void saveOffers() {
        SaveOffersRequest request = SaveOffersRequest.newBuilder()
                .addOffers(Offer.newBuilder()
                        .setSku(productSku)
                        .setRetailerId("3")
                        .setRetailerSku("266353")
                        .setPricer(Pricer.PER_ITEM)
                        .addOfferData(Data.newBuilder()
                                .setKey("ean")
                                .setType(ValueType.STRING)
                                .setIsMultiValue(true)
                                .addValues("666666666")
                                .build())
                        .addOfferData(Data.newBuilder()
                                .setKey("origin_retailer_sku")
                                .addValues("66666")
                                .build())
                        .addOfferData(Data.newBuilder()
                                .setKey("offer_name")
                                .addValues("Autotest Offer")
                                .build())
                        .addOfferData(Data.newBuilder()
                                .setKey("popularity")
                                .addValues("20")
                                .setType(ValueType.INTEGER)
                                .build())
                        .setStatus(Status.ENABLE)
                        .build())
                .build();
        allure.showRequest(request);

        SaveOffersResponse response = client.saveOffers(request);
        allure.showResponse(response);

        assertEquals(response.getSaveOffersCount(), 1,
                "Вернулось другое количество созданных предложений");
    }

    @Story("Товарные предложения")
    @CaseId(20)
    @Test(description = "Добавление предложений к продукту без поля \"retailer_id\"",
            groups = {"grpc-product-hub"},
            dependsOnMethods = "saveProducts")
    public void saveOffersWithoutRetailerId() {
        SaveOffersRequest request = SaveOffersRequest.newBuilder()
                .addOffers(Offer.newBuilder()
                        .setSku(productSku)
                        .setRetailerSku("266353")
                        .setPricer(Pricer.PER_ITEM)
                        .setStatus(Status.ENABLE)
                        .build())
                .build();
        allure.showRequest(request);

        SaveOffersResponse response = null;
        try {
            response = client.saveOffers(request);
        } catch (StatusRuntimeException e) {
            assertEquals(e.getMessage(), "INVALID_ARGUMENT: offer number=0 has empty retailer_id",
                    "Некорректное сообщение об ошибке");
            Allure.step(e.getMessage());
        }
        assertNull(response, "Не вернулась ошибка");
    }

    @Story("Продукты")
    @CaseId(114)
    @Test(description = "Получение продукта по SKU",
            groups = {"grpc-product-hub"},
            dependsOnMethods = "saveProducts")
    public void getProductsBySKU() {
        var frontChannel = grpc.createChannel(GrpcHosts.PAAS_CONTENT_PRODUCT_HUB_FRONT);
        var frontClient = ProductHubFrontDataGrpc.newBlockingStub(frontChannel);

        var request = ProductHubFrontDataOuterClass
                .GetProductsBySKURequest
                .newBuilder()
                .addSku(productSku)
                .build();
        allure.showRequest(request);

        var response = frontClient.getProductsBySKU(request);
        allure.showResponse(response);

        frontChannel.shutdown();
    }
}
