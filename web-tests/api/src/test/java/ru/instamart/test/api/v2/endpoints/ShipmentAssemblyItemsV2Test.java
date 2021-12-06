package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseIDs;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.ProductPriceTypeV2;
import ru.instamart.api.enums.v2.StateV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AssemblyItemV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v2.ShipmentV2;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.api.response.v2.ShipmentAssemblyItemsV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkAssemblyItem;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;
import static ru.instamart.api.helper.K8sHelper.*;

@Epic("ApiV2")
@Feature("Заказы (shipments)")
public class ShipmentAssemblyItemsV2Test extends RestBase {

    private ShipmentV2 shipment;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        UserData userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        apiV2.fillCart(userData, EnvironmentProperties.DEFAULT_SID);
        OrderV2 order = apiV2.getOpenOrder();
        shipment = order.getShipments().get(0);
    }


    @CaseId(529)
    @Story("Детали по сборке подзаказа")
    @Test(groups = {"api-instamart-smoke"},
            description = "Детали по существующему подзаказу до сборки")
    public void getAssemblyItemsOfExistingShipment() {
        final Response response = ShipmentsV2Request.AssemblyItems.GET(shipment.getNumber());
        checkStatusCode200(response);
        AssemblyItemV2 assemblyItem = response.as(ShipmentAssemblyItemsV2Response.class).getAssemblyItems().get(0);
        checkAssemblyItem(shipment, assemblyItem, StateV2.PENDING);
    }

    @CaseId(536)
    @Story("Детали по сборке подзаказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Детали по сборке несуществующего подзаказа")
    public void getAssemblyItemsOfNonExistingShipment() {
        final Response response = ShipmentsV2Request.AssemblyItems.GET("failedShipmentNumber");
        checkStatusCode404(response);
        checkError(response, "Доставка не существует");
    }

    @CaseId(822)
    @Story("Детали по сборке подзаказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Детали по сборке cобранного подзаказа",
            dependsOnMethods = "getAssemblyItemsOfExistingShipment")
    public void getAssemblyItemsOfShipmentAfterAssembling() {
        changeToAssembled(shipment.getNumber(), "0");
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        final Response response = ShipmentsV2Request.AssemblyItems.GET(shipment.getNumber());
        checkStatusCode200(response);
        AssemblyItemV2 assemblyItem = response.as(ShipmentAssemblyItemsV2Response.class).getAssemblyItems().get(0);
        checkAssemblyItem(shipment, assemblyItem, StateV2.ASSEMBLED);
    }

    @CaseId(531)
    @Story("Детали по сборке подзаказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Детали по сборке отмененного подзаказа",
            dependsOnMethods = {"getAssemblyItemsOfExistingShipment", "getAssemblyItemsOfShipmentAfterAssembling"})
    public void getCancelledAssemblyItemsOfShipment() {
        changeItemToCancel(shipment.getNumber(), "0");

        final Response responseWithAssemblyItems = ShipmentsV2Request.AssemblyItems.GET(shipment.getNumber());
        checkStatusCode200(responseWithAssemblyItems);
        AssemblyItemV2 assemblyItem = responseWithAssemblyItems.as(ShipmentAssemblyItemsV2Response.class).getAssemblyItems().get(0);
        checkAssemblyItem(shipment, assemblyItem, StateV2.CANCELED);
    }

    @CaseIDs(value = {@CaseId(534), @CaseId(535)})
    @Story("Детали по сборке подзаказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Детали по сборке существующего подзаказа c выбранным типом цены",
            dataProvider = "priceTypes",
            dataProviderClass = RestDataProvider.class)
    public void getAssemblyItemsWithPriceType(ProductPriceTypeV2 priceType) {
        UserData userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        apiV2.fillCart(userData, EnvironmentProperties.DEFAULT_SID, priceType);
        ShipmentV2 shipment = apiV2.getOpenOrder().getShipments().get(0);
        final Response response = ShipmentsV2Request.AssemblyItems.GET(shipment.getNumber());
        checkStatusCode200(response);
        AssemblyItemV2 assemblyItem = response.as(ShipmentAssemblyItemsV2Response.class).getAssemblyItems().get(0);
        checkAssemblyItem(shipment, assemblyItem, StateV2.PENDING);
    }
}
