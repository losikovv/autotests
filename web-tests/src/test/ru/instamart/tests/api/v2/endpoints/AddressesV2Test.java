package ru.instamart.tests.api.v2.endpoints;

import instamart.api.SessionFactory;
import instamart.api.common.RestBase;
import instamart.api.enums.SessionType;
import instamart.api.requests.v2.AddressesRequest;
import instamart.api.responses.v2.AddressesResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;
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
    @Test(groups = {"api-instamart-regress"})
    @Story("Без обязательных полей")
    public void testWithOutRequiredFields() {
        final AddressesRequest.Addresses addresses = AddressesRequest.Addresses.AddressesBuilder
                .anAddresses()
                .withFirstName("Имя сестра")
                .build();
        final Response response = AddressesRequest.POST(addresses);
        checkStatusCode200(response);
        final AddressesResponse addressesResponse = response.as(AddressesResponse.class);
        assertEquals(addresses.getFirstName(), addressesResponse.getAddress().getFirstName(), "Названия полей не совпадают");
    }

    @CaseId(206)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Только с обязательными полями")
    public void testWithRequiredFields() {
        final AddressesRequest.Addresses addresses = AddressesRequest.Addresses.AddressesBuilder
                .anAddresses()
                .withCity("Москоу")
                .withStreet("Фридриха Энгельса")
                .withBuilding("56")
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
    @Test(groups = {"api-instamart-regress"})
    @Story("Невалидные или пустые поля")
    public void testWithInvalidValueInFields() {
        final AddressesRequest.Addresses addresses = AddressesRequest.Addresses.AddressesBuilder
                .anAddresses()
                .withFirstName("<script>alert()</script>")
                .withLastName("<script>alert()</script>")
                .withBlock("<script>alert()</script>")
                .withEntrance("<script>alert()</script>")
                .withFloor("<script>alert()</script>")
                .withApartment("<script>alert()</script>")
                .withComments("<script>alert()</script>")
                .withDoorPhone("<script>alert()</script>")
                .build();
        final Response response = AddressesRequest.POST(addresses);
        checkStatusCode200(response);
    }
}
