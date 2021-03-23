package ru.instamart.tests.api.v2.endpoints;

import instamart.api.common.RestBase;
import instamart.api.requests.v2.DepartmentsRequest;
import instamart.api.responses.v2.DepartmentResponse;
import instamart.api.responses.v2.DepartmentsResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;
import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode404;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

@Epic("ApiV2")
@Feature("Выдача для главного экрана")
public final class DepartmentsV2Test extends RestBase {

    @CaseId(261)
    @Test(groups = {"api-instamart-regress"})
    @Story("С параметром offers_limit")
    public void testDepartmentsWithOffersLimit() {
        final Response response = DepartmentsRequest.GET(1, 25);
        checkStatusCode200(response);
        final DepartmentsResponse departmentsResponse = response.as(DepartmentsResponse.class);
        assertNotEquals(departmentsResponse.getDepartments().size(), 0, "Товары не импортированы");
    }

    @CaseId(255)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Существующий sid")
    public void testDepartmentsWithValidSid() {
        final Response response = DepartmentsRequest.GET(1, 25);
        checkStatusCode200(response);
        final DepartmentsResponse departmentsResponse = response.as(DepartmentsResponse.class);
        assertNotEquals(departmentsResponse.getDepartments().size(), 0, "Товары не импортированы");
    }

    @CaseId(256)
    @Test(groups = {"api-instamart-regress"})
    @Story("Не существующий sid")
    public void testDepartmentsWithInvalidSid() {
        final Response response = DepartmentsRequest.GET(99999999, 25);
        checkStatusCode404(response);
    }

    @CaseId(257)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Существующий sid категории в магазине")
    public void testDepartmentWithValidSId() {
        final Response response = DepartmentsRequest.Id.GET(16116, 1);
        checkStatusCode200(response);
        final DepartmentResponse departmentsResponse = response.as(DepartmentResponse.class);
        assertNotNull(departmentsResponse.getDepartment().getAisles(), "Товары не импортированы");
        departmentsResponse.getDepartment()
                .getAisles()
                .forEach(aisles -> aisles
                        .getProducts()
                        .forEach(product -> assertNotEquals(product.getId(), 16116L, "Получили неправильную категорию")));
    }

    @CaseId(258)
    @Test(groups = {"api-instamart-regress"})
    @Story("Не существующий sid категории в магазине")
    public void testDepartmentWithInvalidSId() {
        final Response response = DepartmentsRequest.Id.GET(16116, 999999);
        checkStatusCode404(response);
    }

    @CaseId(260)
    @Test(groups = {"api-instamart-regress"})
    @Story("Не существующий id категории в магазине")
    public void testDepartmentWithInvalidId() {
        final Response response = DepartmentsRequest.Id.GET(999999, 1);
        checkStatusCode404(response);
    }
}
