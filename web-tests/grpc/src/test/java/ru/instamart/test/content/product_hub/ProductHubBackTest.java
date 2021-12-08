package ru.instamart.test.content.product_hub;

import io.grpc.StatusRuntimeException;
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
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.kraken.data.Generate;

import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;
import static product_hub_back.ProductHubBackOuterClass.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("Product Hub Microservice")
@Feature("Product Hub Back")
public class ProductHubBackTest extends GrpcBase {
    private ProductHubBackGrpc.ProductHubBackBlockingStub client;
    private final String categoryId = Generate.digitalString(9);
    private final long productSku = Generate.longInt(9);

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_PRODUCT_HUB_BACK);
        client = ProductHubBackGrpc.newBlockingStub(channel);
    }

    @Story("Категории")
    @CaseId(43)
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

        SaveCategoriesResponse response = client.saveCategories(request);

        assertEquals(response.getSaveCategoriesCount(), 1,
                "Вернулось другое количество созданных категорий");
    }

    @Story("Категории")
    @CaseId(44)
    @Test(  description = "Создание категории для продукта без обязательного поля \"id\"",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: category number=0 has empty id")
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

        client.saveCategories(request);
    }

    @Story("Словари")
    @CaseId(45)
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

        SaveDictionariesResponse response = client.saveDictionaries(request);

        assertEquals(response.getSaveDictionariesCount(), 1,
                "Вернулось другое количество созданных словарей");
    }

    @Story("Словари")
    @CaseId(46)
    @Test(  description = "Создание словарей для нового продукта без обязательного поля \"key\"",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: dictionary number=0 has empty key")
    public void saveDictionariesWithoutKey() {
        SaveDictionariesRequest request = SaveDictionariesRequest.newBuilder()
                .addDictionaries(Dictionary.newBuilder()
                        .setName("Autotest Dictionary")
                        .setType(ValueType.STRING)
                        .build())
                .build();

        client.saveDictionaries(request);
    }

    @Story("Атрибуты")
    @CaseId(47)
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

        SaveAttributesResponse response = client.saveAttributes(request);

        assertEquals(response.getSaveAttributesCount(), 1,
                "Вернулось другое количество созданных атрибутов");
    }

    @Story("Атрибуты")
    @CaseId(48)
    @Test(  description = "Создание аттрибутов для нового продукта без обязательного поля \"key\"",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: attribute number=0 has empty key")
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

        client.saveAttributes(request);
    }

    @Story("Продукты")
    @CaseId(49)
    @Test(  description = "Создание продукта",
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

        SaveProductsResponse response = client.saveProducts(request);

        assertEquals(response.getSaveProductsCount(), 1,
                "Вернулось другое количество созданных продуктов");
    }

    @Story("Продукты")
    @CaseId(50)
    @Test(  description = "Создание нового продукта в системе без обязательного поля \"sku\"",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: product number=0 has empty sku")
    public void saveProductsWithoutSKU() {
        SaveProductsRequest request = SaveProductsRequest.newBuilder()
                .addProducts(Product
                        .newBuilder()
                        .setName("Autotest Product")
                        .addCategoryIds(categoryId)
                        .setStatus(Status.ENABLE)
                        .build())
                .build();

        client.saveProducts(request);
    }

    @Story("Стоки")
    @CaseId(51)
    @Test(  description = "Добавление стоков к созданному новому продукту",
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

        SaveStocksResponse response = client.saveStocks(request);

        assertEquals(response.getSaveStocksCount(), 1,
                "Вернулось другое количество созданных стоков");
    }

    @Story("Стоки")
    @CaseId(52)
    @Test(  description = "Добавление стоков к созданному новому продукту без поля \"sku\"",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: stock number=0 has empty sku")
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

        client.saveStocks(request);
    }

    @Story("Цены")
    @CaseId(53)
    @Test(  description = "Добавление стоимости продукту",
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

        SavePricesResponse response = client.savePrices(request);

        assertEquals(response.getSavePricesCount(), 1,
                "Вернулось другое количество созданных цен");
    }

    @Story("Цены")
    @CaseId(54)
    @Test(  description = "Добавление стоимости продукту без поля \"sku\"",
            groups = {"grpc-product-hub"},
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: price number=0 has empty sku")
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

        client.savePrices(request);
    }

    @Story("Товарные предложения")
    @CaseId(55)
    @Test(  description = "Добавление предложений к продукту",
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

        SaveOffersResponse response = client.saveOffers(request);

        assertEquals(response.getSaveOffersCount(), 1,
                "Вернулось другое количество созданных предложений");
    }

    @Story("Товарные предложения")
    @CaseId(56)
    @Test(  description = "Добавление предложений к продукту без поля \"retailer_id\"",
            groups = {"grpc-product-hub"},
            dependsOnMethods = "saveProducts",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: offer number=0 has empty retailer_id")
    public void saveOffersWithoutRetailerId() {
        SaveOffersRequest request = SaveOffersRequest.newBuilder()
                .addOffers(Offer.newBuilder()
                        .setSku(productSku)
                        .setRetailerSku("266353")
                        .setPricer(Pricer.PER_ITEM)
                        .setStatus(Status.ENABLE)
                        .build())
                .build();

        client.saveOffers(request);
    }

    @Story("Продукты")
    @CaseId(149)
    @Test(  description = "Получение продукта по SKU",
            groups = {"grpc-product-hub"},
            dependsOnMethods = "saveProducts")
    public void getProductsBySKU() {
        var frontChannel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_PRODUCT_HUB_FRONT);
        var frontClient = ProductHubFrontDataGrpc.newBlockingStub(frontChannel);

        var request = ProductHubFrontDataOuterClass
                .GetProductsBySKURequest
                .newBuilder()
                .addSku(productSku)
                .build();

        var response = frontClient.getProductsBySKU(request);

        frontChannel.shutdown();
    }
}
