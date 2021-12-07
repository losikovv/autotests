package ru.instamart.test.content.catalog;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import shelf.ShelfOuterClass;
import shelf.ShelfServiceGrpc;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("Catalog Microservice")
@Feature("Shelf")
@Slf4j
public class ShelfTest extends GrpcBase {
    private ShelfServiceGrpc.ShelfServiceBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_CATALOG_SHELF);
        client = ShelfServiceGrpc.newBlockingStub(channel);
    }

    @Story("Проверка построения полок")
    @CaseId(182)
    @Test(description = "Проверка построения полок по категории продукта",
            groups = "grpc-product-hub",
            enabled = false) //todo написать предусловие чтобы находить всегда существующий setOriginalCategoryId
    public void checkingConstructionShelvesByProductCategory() {
        var request = ShelfOuterClass.GetShelfByOriginalCategoryIDRequest
                .newBuilder()
                .setOriginalCategoryId("122")
                .setProductsLimit(1)
                .setStoreId("1")
                .setTenantId("sbermarket")
                .build();

        var response = client.getShelfByOriginalCategoryID(request);

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(response.getCategoriesList().size() > 0, "Категории товаров не вернулись");
        softAssert.assertEquals(response.getShelves(0).getCategoryId(), "123", "Категория товара не совпадает");
        softAssert.assertEquals(response.getShelves(0).getCategoryPermalink(), "miaso-ptitsa-new/miaso", "category_permalink не совпадает");
        softAssert.assertTrue(response.getShelves(0).getProductCount() > 0, "product_count is null");
        softAssert.assertTrue(response.getShelves(0).getProductsList().size() > 0, "products вернулся пустым");
        softAssert.assertAll();
    }

    @Story("Проверка построения полок")
    @CaseId(257)
    @Test(description = "Проверка построения полок по категории продукта с пустым значением поля \"category_id\"",
            groups = "grpc-product-hub")
    public void emptyCategoryID() {
        var request = ShelfOuterClass.GetShelfByOriginalCategoryIDRequest.newBuilder()
                .setOriginalCategoryId("")
                .setProductsLimit(1)
                .setStoreId("1")
                .setTenantId("sbermarket")
                .build();

        var response = client.getShelfByOriginalCategoryID(request);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(response.getCategoriesList().size() > 0, "Категории товаров не вернулись");
        softAssert.assertTrue(response.getShelves(0).getProductCount() > 0, "product_count is null");
        softAssert.assertTrue(response.getShelves(0).getProductsList().size() > 0, "products вернулся пустым");
        softAssert.assertAll();
    }


    @Story("Проверка построения полок")
    @CaseId(258)
    @Test(description = "Проверка построения полок по категории продукта с пустым полем \"store_id\"",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid request: rpc error: code = InvalidArgument desc = required field \"store_id\" is not specified")
    public void emptyStoreID() {
        var request = ShelfOuterClass.GetShelfByOriginalCategoryIDRequest.newBuilder()
                .setOriginalCategoryId("122")
                .setProductsLimit(1)
                .setStoreId("")
                .setTenantId("sbermarket")
                .build();

        client.getShelfByOriginalCategoryID(request);
    }

    @Story("Проверка построения полок")
    @CaseId(259)
    @Test(description = "Проверка построения полок по категории продукта без передачи поля \"store_id\"",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid request: rpc error: code = InvalidArgument desc = required field \"store_id\" is not specified")
    public void withoutStoreID() {
        var request = ShelfOuterClass.GetShelfByOriginalCategoryIDRequest.newBuilder()
                .setOriginalCategoryId("122")
                .setProductsLimit(1)
                .setTenantId("sbermarket")
                .build();

        client.getShelfByOriginalCategoryID(request);
    }

    @Story("Проверка построения полок")
    @CaseId(260)
    @Test(description = "Проверка построения полок по категории продукта с пустым полем \"tenant_id\"",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid request: rpc error: code = InvalidArgument desc = required field \"tenant_id\" is not specified")
    public void emptyTenantID() {
        var request = ShelfOuterClass.GetShelfByOriginalCategoryIDRequest.newBuilder()
                .setOriginalCategoryId("122")
                .setProductsLimit(1)
                .setStoreId("1")
                .setTenantId("")
                .build();

        client.getShelfByOriginalCategoryID(request);
    }


    @Story("Проверка построения полок")
    @CaseId(261)
    @Test(description = "Проверка построения полок по категории продукта без поля \"tenant_id\"",
            groups = "grpc-product-hub",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid request: rpc error: code = InvalidArgument desc = required field \"tenant_id\" is not specified")
    public void withoutTenantID() {
        var request = ShelfOuterClass.GetShelfByOriginalCategoryIDRequest.newBuilder()
                .setOriginalCategoryId("122")
                .setProductsLimit(1)
                .setStoreId("1")
                .setTenantId("")
                .build();

        client.getShelfByOriginalCategoryID(request);
    }
}
