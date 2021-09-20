package ru.instamart.test.api.v2.deprecated;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.AddressesV2Request;
import ru.instamart.api.request.v2.AddressesV2Request.Addresses;
import ru.instamart.api.response.v2.AddressV2Response;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.*;

@Epic("ApiV2")
@Feature("Добавление нового адреса")
@Deprecated
public final class AddressesV2Test extends RestBase {

    private int addressesId;

    @BeforeClass(alwaysRun = true)
    @Story("Создание сессии")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
    }

    @Deprecated
    @Story("Создание нового адреса доставки")
    @Test(groups = {}, description = "Без обязательных полей")
    public void testWithOutRequiredFields() {
        final Addresses addresses = Addresses.builder()
                .firstName("Имя сестра")
                .build();
        final Response response = AddressesV2Request.POST(addresses);
        checkStatusCode200(response);
        final AddressV2Response addressesResponse = response.as(AddressV2Response.class);
        assertEquals(addresses.getFirstName(), addressesResponse.getAddress().getFirstName(), "Названия полей не совпадают");
        addressesId = addressesResponse.getAddress().getId();
        System.out.println(addressesId);
    }

    @Deprecated
    @Story("Создание нового адреса доставки")
    @Test(groups = {}, description = "Только с обязательными полями")
    public void testWithRequiredFields() {
        final Addresses addresses = Addresses.builder()
                .city("Москоу")
                .street("Фридриха Энгельса")
                .building("56")
                .build();
        final Response response = AddressesV2Request.POST(addresses);
        checkStatusCode200(response);
        final AddressV2Response addressesResponse = response.as(AddressV2Response.class);
        assertEquals(addresses.getCity(), addressesResponse.getAddress().getCity(), "Названия полей не совпадают");
        assertEquals(addresses.getStreet(), addressesResponse.getAddress().getStreet(), "Названия полей не совпадают");
        assertEquals(addresses.getBuilding(), addressesResponse.getAddress().getBuilding(), "Названия полей не совпадают");
    }

    @Deprecated
    @Issue("SBUG-35")
    @Story("Создание нового адреса доставки")
    @Test(  groups = {},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "getAddresses",
            description = "Невалидные или пустые поля"
    )
    public void testWithInvalidValueInFields(final Addresses addresses) {
        final Response response = AddressesV2Request.POST(addresses);
        checkStatusCode422(response);
    }

    @Deprecated
    @Story("Удалить адрес доставки")
    @Test(groups = {}, description = "Существующий id")
    public void testDeleteWithValidId() {
        final Addresses addresses = Addresses.builder()
                .firstName("Имя сестра")
                .build();
        Response response = AddressesV2Request.POST(addresses);
        checkStatusCode200(response);
        final AddressV2Response addressesResponse = response.as(AddressV2Response.class);
        response = AddressesV2Request.DELETE(addressesResponse.getAddress().getId());
        checkStatusCode200(response);
    }

    @Deprecated
    @Story("Удалить адрес доставки")
    @Test(groups = {}, description = "Несуществующий id")
    public void testDeleteWithInvalidId() {
        final Response response = AddressesV2Request.DELETE(0);
        checkStatusCode404(response);
    }

    //TODO: Валидацию не завезли, отписал апсекам, завели баг
    //217-230
    @Deprecated
    @Issue("SBUG-35")
    @Story("Редактирование адреса доставки")
    @Test(  groups = {},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "getAddresses",
            dependsOnMethods = "testWithOutRequiredFields",
            description = "Невалидное или пустое значение полей"
    )
    public void testEditWithInvalidData(final Addresses addresses) {
        final Response response = AddressesV2Request.PUT(addressesId, addresses);
        checkStatusCode422(response);
    }
}
