package ru.instamart.test.content.catalog;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import lombok.extern.slf4j.Slf4j;
import navigation.Navigation;
import navigation.NavigationServiceGrpc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcContentHosts;

import static org.testng.Assert.assertTrue;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("Catalog Microservice")
@Feature("Navigation")
@Slf4j
public class NavigationTest extends GrpcBase {
    private NavigationServiceGrpc.NavigationServiceBlockingStub client;
    private Navigation.MenuCategory category;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_CATALOG_NAVIGATION);
        client = NavigationServiceGrpc.newBlockingStub(channel);
    }

    @TmsLink("150")
    @Test(groups = {"grpc-product-hub"},
            description = "Проверка построения дерева продуктов")
    public void getMenuTree() {
        var request = Navigation.GetMenuTreeRequest
                .newBuilder()
                .setStoreId("1")
                .setTenantId("sbermarket")
                .setTreeDepth(5)
                .build();

        var response = client.getMenuTree(request);

        assertTrue(response.getCategoriesCount() > 0, "Вернулся пустой массив категорий");

        category = response.getCategories(0);
    }


    @TmsLink("151")
    @Test(groups = {"grpc-product-hub"},
            description = "Проверка построения дерева продуктов без поля \"store_id\"",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: required field \"store_id\" is not specified")
    public void getMenuTreeWithout() {
        var request = Navigation.GetMenuTreeRequest
                .newBuilder()
                .setTenantId("sbermarket")
                .setTreeDepth(5)
                .build();
        client.getMenuTree(request);
    }

    @TmsLink("152")
    @Test(groups = {"grpc-product-hub"},
            description = "Получение хлебных крошек по ID категории",
            dependsOnMethods = "getMenuTree")
    public void getBreadcrumbsCategoryID() {
        var request = Navigation.GetBreadcrumbsByCategoryIDRequest.newBuilder()
                .setCategoryId(category.getId())
                .build();
        var response = client.getBreadcrumbsByCategoryID(request);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getBreadcrumbs(0).getId(), category.getId(), "Категория отличается");
        softAssert.assertEquals(response.getBreadcrumbs(0).getName(), category.getName(), "Наименование категории не совпадает");
        softAssert.assertAll();
    }

    @TmsLink("153")
    @Test(groups = {"grpc-product-hub"},
            description = "Получение хлебных крошек без указания ID категории ",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: required field \"category_id\" is not specified")
    public void getBreadcrumbsWithoutCategoryID() {
        var request = Navigation.GetBreadcrumbsByCategoryIDRequest.newBuilder()
                .setCategoryId("")
                .build();
        client.getBreadcrumbsByCategoryID(request);
    }


    @TmsLink("154")
    @Test(groups = {"grpc-product-hub"},
            description = "Проверка построения дерева категорий",
            dependsOnMethods = "getMenuTree")
    public void getCategoryTree() {
        var request = Navigation.GetCategoryTreesRequest.newBuilder()
                .setCategoryId(category.getId())
                .build();
        var response = client.getCategoryTrees(request);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getCategories(0).getId(), category.getId(), "Категория отличается");
        softAssert.assertEquals(response.getCategories(0).getName(), category.getName(), "Наименование категории не совпадает");
        softAssert.assertTrue(response.getCategories(0).getChildrenList().size() > 0, "Children null");
        softAssert.assertAll();
    }

    @TmsLink("155")
    @Test(groups = {"grpc-product-hub"},
            description = "Проверка построения дерева категорий с пустым значением у поля \"category_id\"")
    public void emptyCategoryID() {
        var request = Navigation.GetCategoryTreesRequest.newBuilder()
                .setCategoryId("")
                .build();
        var response = client.getCategoryTrees(request);
        assertTrue(response.getCategoriesCount() > 0, "Вернулся пустой массив категорий");
    }

    @TmsLink("224")
    @Test(groups = {"grpc-product-hub"},
            description = "Проверка построения дерева продуктов без поля \"tenant_id\"",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: required field \"tenant_id\" is not specified")
    public void withoutTenantID() {
        var request = Navigation.GetMenuTreeRequest.newBuilder()
                .setStoreId("1")
                .setTreeDepth(5)
                .build();
        client.getMenuTree(request);
    }

    @TmsLink("225")
    @Test(groups = {"grpc-product-hub"},
            description = "Проверка построения дерева продуктов с пустым значением в поле \"store_id\"",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: required field \"store_id\" is not specified")
    public void emptyStoreID() {
        var request = Navigation.GetMenuTreeRequest.newBuilder()
                .setStoreId("")
                .setTenantId("sbermarket")
                .setTreeDepth(5)
                .build();
        client.getMenuTree(request);
    }

    @TmsLink("226")
    @Test(groups = {"grpc-product-hub"},
            description = "Проверка построения дерева продуктов с пустым значением в поле \"tenant_id\"",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: required field \"tenant_id\" is not specified")
    public void emptyTenantID() {
        var request = Navigation.GetMenuTreeRequest.newBuilder()
                .setStoreId("1")
                .setTenantId("")
                .setTreeDepth(5)
                .build();
        client.getMenuTree(request);
    }

    @TmsLink("227")
    @Test(groups = {"grpc-product-hub"}, enabled = false, //ждет обновления от Дмитрия Дьячкова после изменения логики
            description = "Проверка построения дерева продуктов без поля \"tree_depth\"")
    public void withoutTreeDepth() {
        var request = Navigation.GetMenuTreeRequest.newBuilder()
                .setStoreId("1")
                .setTenantId("sbermarket")
                .build();
        var response = client.getMenuTree(request);
        assertTrue(response.getCategoriesCount() > 0, "Вернулся пустой массив категорий");
    }
}
