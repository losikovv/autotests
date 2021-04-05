package ru.instamart.tests.api.v2.endpoints;

import instamart.api.SessionFactory;
import instamart.api.checkpoints.InstamartApiCheckpoints;
import instamart.api.common.RestBase;
import instamart.api.enums.SessionType;
import instamart.api.requests.v2.AddressesRequest;
import instamart.api.responses.v2.AddressesResponse;
import instamart.core.listeners.ExecutionListenerImpl;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;
import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode404;
import static org.testng.Assert.assertEquals;

@Epic("ApiV2")
@Feature("Добавление нового адреса")
public final class AddressesV2Test extends RestBase {

    @BeforeClass(alwaysRun = true)
    @Story("Создание сессии")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.APIV2);
    }

    @CaseId(205)
    @Story("Создание нового адреса доставки")
    @Test(groups = {"api-instamart-regress"}, description = "Без обязательных полей")
    public void testWithOutRequiredFields() {
        final AddressesRequest.Addresses addresses = AddressesRequest.Addresses.builder()
                .firstName("Имя сестра")
                .build();
        final Response response = AddressesRequest.POST(addresses);
        checkStatusCode200(response);
        final AddressesResponse addressesResponse = response.as(AddressesResponse.class);
        assertEquals(addresses.getFirstName(), addressesResponse.getAddress().getFirstName(), "Названия полей не совпадают");
    }

    @CaseId(206)
    @Story("Создание нового адреса доставки")
    @Test(groups = {"api-instamart-smoke"}, description = "Только с обязательными полями")
    public void testWithRequiredFields() {
        final AddressesRequest.Addresses addresses = AddressesRequest.Addresses.builder()
                .city("Москоу")
                .street("Фридриха Энгельса")
                .building("56")
                .build();
        final Response response = AddressesRequest.POST(addresses);
        checkStatusCode200(response);
        final AddressesResponse addressesResponse = response.as(AddressesResponse.class);
        assertEquals(addresses.getCity(), addressesResponse.getAddress().getCity(), "Названия полей не совпадают");
        assertEquals(addresses.getStreet(), addressesResponse.getAddress().getStreet(), "Названия полей не совпадают");
        assertEquals(addresses.getBuilding(), addressesResponse.getAddress().getBuilding(), "Названия полей не совпадают");
    }

    //TODO: Валидацию не завезли, отписал апсекам на посмотреть
    // Завели баг, после исправления нужно будет переделать тест
    @Issue("SBUG-35")
    @Story("Создание нового адреса доставки")
    @Test(groups = {"api-instamart-regress"}, description = "Невалидные или пустые поля")
    public void testWithInvalidValueInFields() {
        final AddressesRequest.Addresses addresses = AddressesRequest.Addresses.builder()
                .firstName("<script>alert()</script>")
                .lastName("<script>alert()</script>")
                .block("<script>alert()</script>")
                .entrance("<script>alert()</script>")
                .floor("<script>alert()</script>")
                .apartment("<script>alert()</script>")
                .comments("<script>alert()</script>")
                .doorPhone("<script>alert()</script>")
                .build();
        final Response response = AddressesRequest.POST(addresses);
        checkStatusCode200(response);
    }

    @CaseId(231)
    @Story("Удалить адрес доставки")
    @Test(groups = {"api-instamart-smoke"}, description = "Существующий id")
    public void testDeleteWithValidId() {
        final AddressesRequest.Addresses addresses = AddressesRequest.Addresses.builder()
                .firstName("Имя сестра")
                .build();
        Response response = AddressesRequest.POST(addresses);
        checkStatusCode200(response);
        final AddressesResponse addressesResponse = response.as(AddressesResponse.class);
        response = AddressesRequest.DELETE(addressesResponse.getAddress().getId());
        checkStatusCode200(response);
    }

    @CaseId(232)
    @Story("Удалить адрес доставки")
    @Test(groups = {"api-instamart-regress"}, description = "Несуществующий id")
    public void testDeleteWithInvalidId() {
        final Response response = AddressesRequest.DELETE(666666);
        checkStatusCode404(response);
    }
}
