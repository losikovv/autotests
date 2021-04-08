package ru.instamart.tests.api.v2.endpoints;

import ru.instamart.api.SessionFactory;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.requests.v2.AddressesRequest;
import ru.instamart.api.requests.v2.AddressesRequest.Addresses;
import ru.instamart.api.responses.v2.AddressesResponse;
import ru.instamart.core.listeners.ExecutionListenerImpl;
import ru.instamart.core.testdata.dataprovider.RestDataProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.*;
import static org.testng.Assert.assertEquals;

@Epic("ApiV2")
@Feature("Добавление нового адреса")
public final class AddressesV2Test extends RestBase {

    private int addressesId;

    @BeforeClass(alwaysRun = true)
    @Story("Создание сессии")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.APIV2);
    }

    @CaseId(205)
    @Story("Создание нового адреса доставки")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Без обязательных полей")
    public void testWithOutRequiredFields() {
        final Addresses addresses = Addresses.builder()
                .firstName("Имя сестра")
                .build();
        final Response response = AddressesRequest.POST(addresses);
        checkStatusCode200(response);
        final AddressesResponse addressesResponse = response.as(AddressesResponse.class);
        assertEquals(addresses.getFirstName(), addressesResponse.getAddress().getFirstName(), "Названия полей не совпадают");
        addressesId = addressesResponse.getAddress().getId();
        System.out.println(addressesId);
    }

    @CaseId(206)
    @Story("Создание нового адреса доставки")
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Только с обязательными полями")
    public void testWithRequiredFields() {
        final Addresses addresses = Addresses.builder()
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

    //TODO: Валидацию не завезли, отписал апсекам, завели баг
    @Issue("SBUG-35")
    @Story("Создание нового адреса доставки")
    @Test(groups = {"api-instamart-regress"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "getAddresses",
            description = "Невалидные или пустые поля",
            enabled = false
    )
    public void testWithInvalidValueInFields(final Addresses addresses) {
        final Response response = AddressesRequest.POST(addresses);
        checkStatusCode422(response);
    }

    @CaseId(231)
    @Story("Удалить адрес доставки")
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Существующий id")
    public void testDeleteWithValidId() {
        final Addresses addresses = Addresses.builder()
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
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Несуществующий id")
    public void testDeleteWithInvalidId() {
        final Response response = AddressesRequest.DELETE(0);
        checkStatusCode404(response);
    }

    //TODO: Валидацию не завезли, отписал апсекам, завели баг
    //217-230
    @CaseId(217)
    @Issue("SBUG-35")
    @Story("Редактирование адреса доставки")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "getAddresses",
            dependsOnMethods = "testWithOutRequiredFields",
            description = "Невалидное или пустое значение полей",
            enabled = false
    )
    public void testEditWithInvalidData(final Addresses addresses) {
        final Response response = AddressesRequest.PUT(addressesId, addresses);
        checkStatusCode422(response);
    }
}
