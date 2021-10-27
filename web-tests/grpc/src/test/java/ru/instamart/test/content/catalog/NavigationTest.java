package ru.instamart.test.content.catalog;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import lombok.extern.slf4j.Slf4j;
import navigation.Navigation;
import navigation.NavigationServiceGrpc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcHosts;

import static org.testng.Assert.assertTrue;

@Epic("Catalog Microservice")
@Feature("Navigation")
@Slf4j
public class NavigationTest extends GrpcBase {
    private NavigationServiceGrpc.NavigationServiceBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcHosts.PAAS_CONTENT_PRODUCT_NAVIGATION);
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
        allure.showRequest(request);

        var response = client.getMenuTree(request);
        allure.showResponse(response);

        assertTrue(response.getCategoriesCount() > 0, "Вернулся пустой массив категорий");

        var categories = response.getCategoriesList();
        assertTrue(grpc.findCategoryById(categories, "900000"), "Не вернулась 900000 категория");
    }
}
