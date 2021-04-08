package ru.instamart.tests.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.requests.v2.DepartmentsV2Request;
import ru.instamart.api.responses.v2.DepartmentV2Response;
import ru.instamart.api.responses.v2.DepartmentsV2Response;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Выдача для главного экрана")
public final class DepartmentsV2Test extends RestBase {

    @CaseId(261)
    @Story("Данные для главной страницы")
    @Test(groups = {"api-instamart-regress"}, description = "С параметром offers_limit")
    public void testDepartmentsWithOffersLimit() {
        final Response response = DepartmentsV2Request.GET(1, 25);
        checkStatusCode200(response);
        final DepartmentsV2Response departmentsResponse = response.as(DepartmentsV2Response.class);
        assertNotEquals(departmentsResponse.getDepartments().size(), 0, "Товары не импортированы");
    }

    @CaseId(255)
    @Story("Данные для главной страницы")
    @Test(groups = {"api-instamart-smoke"}, description = "Существующий sid")
    public void testDepartmentsWithValidSid() {
        final Response response = DepartmentsV2Request.GET(1, 25);
        checkStatusCode200(response);
        final DepartmentsV2Response departmentsResponse = response.as(DepartmentsV2Response.class);
        assertNotEquals(departmentsResponse.getDepartments().size(), 0, "Товары не импортированы");
    }

    @CaseId(256)
    @Story("Данные для главной страницы")
    @Test(groups = {"api-instamart-regress"}, description = "Не существующий sid")
    public void testDepartmentsWithInvalidSid() {
        final Response response = DepartmentsV2Request.GET(99999999, 25);
        checkStatusCode404(response);
    }

    @CaseId(257)
    @Story("Выдача для страницы каталога")
    @Test(groups = {"api-instamart-smoke"}, description = "Существующий sid категории в магазине")
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

    @CaseId(258)
    @Story("Выдача для страницы каталога")
    @Test(groups = {"api-instamart-regress"}, description = "Не существующий sid категории в магазине")
    public void testDepartmentWithInvalidSId() {
        final Response response = DepartmentsV2Request.Id.GET(16116, 999999);
        checkStatusCode404(response);
    }

    @CaseId(260)
    @Story("Выдача для страницы каталога")
    @Test(groups = {"api-instamart-regress"}, description = "Не существующий id категории в магазине")
    public void testDepartmentWithInvalidId() {
        final Response response = DepartmentsV2Request.Id.GET(999999, 1);
        checkStatusCode404(response);
    }
}
