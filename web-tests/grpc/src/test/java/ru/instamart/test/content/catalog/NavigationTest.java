package ru.instamart.test.content.catalog;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import lombok.extern.slf4j.Slf4j;
import navigation.Navigation;
import navigation.NavigationServiceGrpc;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.grpc.common.GrpcBase;

@Epic("Catalog Microservice")
@Feature("Navigation")
@Slf4j
public class NavigationTest extends GrpcBase {
    private NavigationServiceGrpc.NavigationServiceBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpcStep.createChannel("paas-content-catalog-navigation.k-stage.sbmt.io", 443);
        client = NavigationServiceGrpc.newBlockingStub(channel);
    }

    @CaseId(1)
    @Test(  groups = {"grpc-product-hub"},
            description = "Проверка построения дерева продуктов")
    public void getMenuTree() {

        var request = Navigation.GetMenuTreeRequest
                .newBuilder()
                .setStoreId("1")
                .setTenantId("sbermarket")
                .setTreeDepth(5)
                .build();

        grpcStep.showRequestInAllure(request);

        var response = client.getMenuTree(request);

        grpcStep.showResponseInAllure(response);

        Assert.assertTrue(response.getCategoriesCount() > 0,
                "Вернулся пустой массив категорий");

        var categories = response.getCategoriesList();

        Assert.assertTrue(grpcStep.findCategoryById(categories, "900000"),
                "Не вернулась 900000 категория");
    }
}
