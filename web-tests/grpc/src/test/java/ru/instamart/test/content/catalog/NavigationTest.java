package ru.instamart.test.content.catalog;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
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

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_CATALOG_NAVIGATION);
        client = NavigationServiceGrpc.newBlockingStub(channel);
    }

    @CaseId(150)
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

        var categories = response.getCategoriesList();
    }


    @CaseId(151)
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

    @CaseId(152)
    @Test(groups = {"grpc-product-hub"},
            description = "Получение хлебных крошек по ID категории")
    public void getBreadcrumbsCategoryID() {
        var request = Navigation.GetBreadcrumbsByCategoryIDRequest.newBuilder()
                .setCategoryId("131")
                .build();
        var response = client.getBreadcrumbsByCategoryID(request);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getBreadcrumbs(0).getId(), "131", "Категория отличается");
        softAssert.assertEquals(response.getBreadcrumbs(0).getName(), "Вода, соки, напитки", "Наименование категории не совпадает");
        softAssert.assertEquals(response.getBreadcrumbs(0).getPermalink(), "voda-soki-napitki-new", "Permalink не совпадает");
        softAssert.assertAll();
    }

    @CaseId(153)
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


    @CaseId(154)
    @Test(groups = {"grpc-product-hub"},
            description = "Проверка построения дерева категорий")
    public void getCategoryTree() {
        var request = Navigation.GetCategoryTreesRequest.newBuilder()
                .setCategoryId("131")
                .build();
        var response = client.getCategoryTrees(request);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getCategories(0).getId(), "131", "Категория отличается");
        softAssert.assertEquals(response.getCategories(0).getName(), "Вода, соки, напитки", "Наименование категории не совпадает");
        softAssert.assertEquals(response.getCategories(0).getStatus(), Navigation.Status.ENABLE, "status не совпадает");
        softAssert.assertTrue(response.getCategories(0).getChildrenList().size() > 0, "Children null");
        softAssert.assertAll();
    }

    @CaseId(155)
    @Test(groups = {"grpc-product-hub"},
            description = "Проверка построения дерева категорий с пустым значением у поля \"category_id\"")
    public void emptyCategoryID() {
        var request = Navigation.GetCategoryTreesRequest.newBuilder()
                .setCategoryId("")
                .build();
        var response = client.getCategoryTrees(request);
        assertTrue(response.getCategoriesCount() > 0, "Вернулся пустой массив категорий");
    }

    @CaseId(224)
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

    @CaseId(225)
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

    @CaseId(226)
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

    @CaseId(227)
    @Test(groups = {"grpc-product-hub"},
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
