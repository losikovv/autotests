package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v2.DepartmentsV2Request;
import ru.instamart.api.response.v2.DepartmentV2Response;
import ru.instamart.api.response.v2.DepartmentsV2Response;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Выдача для главного экрана")
@Deprecated
public final class DepartmentsV2Test extends RestBase {

    @Deprecated
    @Story("Данные для главной страницы")
    @Test(groups = {}, description = "С параметром offers_limit")
    public void testDepartmentsWithOffersLimit() {
        final Response response = DepartmentsV2Request.GET(1, 25);
        checkStatusCode200(response);
        final DepartmentsV2Response departmentsResponse = response.as(DepartmentsV2Response.class);
        assertFalse(departmentsResponse.getDepartments().isEmpty(), "Товары не импортированы");
    }

    @Deprecated
    @Story("Данные для главной страницы")
    @Test(groups = {}, description = "Существующий sid")
    public void testDepartmentsWithValidSid() {
        final Response response = DepartmentsV2Request.GET(1, 25);
        checkStatusCode200(response);
        final DepartmentsV2Response departmentsResponse = response.as(DepartmentsV2Response.class);
        assertFalse(departmentsResponse.getDepartments().isEmpty(), "Товары не импортированы");
    }

    @Deprecated
    @Story("Данные для главной страницы")
    @Test(groups = {}, description = "Не существующий sid")
    public void testDepartmentsWithInvalidSid() {
        final Response response = DepartmentsV2Request.GET(99999999, 25);
        checkStatusCode404(response);
    }

    @Deprecated
    @Story("Выдача для страницы каталога")
    @Test(groups = {}, description = "Существующий sid категории в магазине")
    public void testDepartmentWithValidSId() {
        final Response response = DepartmentsV2Request.Id.GET(16116, 1);
        checkStatusCode200(response);
        final DepartmentV2Response departmentsResponse = response.as(DepartmentV2Response.class);
        assertNotNull(departmentsResponse.getDepartment().getAisles(), "Товары не импортированы");
        departmentsResponse.getDepartment()
                .getAisles()
                .forEach(aisles -> aisles
                        .getProducts()
                        .forEach(product -> assertNotEquals(product.getId(), 16116L, "Получили неправильную категорию")));
    }

    @Deprecated
    @Story("Выдача для страницы каталога")
    @Test(groups = {}, description = "Не существующий sid категории в магазине")
    public void testDepartmentWithInvalidSId() {
        final Response response = DepartmentsV2Request.Id.GET(16116, 999999);
        checkStatusCode404(response);
    }

    @Deprecated
    @Story("Выдача для страницы каталога")
    @Test(groups = {}, description = "Не существующий id категории в магазине")
    public void testDepartmentWithInvalidId() {
        final Response response = DepartmentsV2Request.Id.GET(999999, 1);
        checkStatusCode404(response);
    }
}
