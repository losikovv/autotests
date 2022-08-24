package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.ProductPriceTypeV2;
import ru.instamart.api.enums.v2.StateV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AssemblyItemV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v2.ShipmentV2;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.api.response.v2.AssemblyItemsV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkAssemblyItem;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;
import static ru.instamart.api.helper.K8sHelper.changeItemToCancel;
import static ru.instamart.api.helper.K8sHelper.changeToAssembled;

@Epic("ApiV2")
@Feature("Заказы (shipments)")
public class ShipmentAssemblyItemsStatusV2Test extends RestBase {

    private ShipmentV2 shipment;
    private UserData userData;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        apiV2.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID, ProductPriceTypeV2.PER_ITEM);
        OrderV2 order = apiV2.createOrder();
        shipment = order.getShipments().get(0);
    }

    @BeforeMethod(alwaysRun = true)
    public void auth() {
        if(SessionFactory.getSession(SessionType.API_V2).getToken().equals("invalid")){
            SessionFactory.createSessionToken(SessionType.API_V2, userData);
        }
    }

    @CaseId(529)
    @Story("Детали по сборке подзаказа")
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod", "api-v2"},
            description = "Детали по существующему подзаказу до сборки")
    public void getAssemblyItemsOfExistingShipment() {
        final Response response = ShipmentsV2Request.AssemblyItems.GET(shipment.getNumber());
        checkStatusCode200(response);

        List<AssemblyItemV2> items = response.as(AssemblyItemsV2Response.class).getAssemblyItems();
        checkFieldIsNotEmpty(items, "список товаров в заказе");
        checkAssemblyItem(shipment, items.get(0), StateV2.PENDING);
    }

    @CaseId(536)
    @Story("Детали по сборке подзаказа")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Детали по сборке несуществующего подзаказа")
    public void getAssemblyItemsOfNonExistingShipment() {
        final Response response = ShipmentsV2Request.AssemblyItems.GET("failedShipmentNumber");
        checkStatusCode404(response);
        checkError(response, "Доставка не существует");
    }

    @CaseId(822)
    @Story("Детали по сборке подзаказа")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Детали по сборке cобранного подзаказа",
            dependsOnMethods = "getAssemblyItemsOfExistingShipment")
    public void getAssemblyItemsOfShipmentAfterAssembling() {
        changeToAssembled(shipment.getNumber(), "0");
        final Response response = ShipmentsV2Request.AssemblyItems.GET(shipment.getNumber());
        checkStatusCode200(response);
        AssemblyItemV2 assemblyItem = response.as(AssemblyItemsV2Response.class).getAssemblyItems().get(0);
        checkAssemblyItem(shipment, assemblyItem, StateV2.ASSEMBLED);
    }

    @CaseId(531)
    @Story("Детали по сборке подзаказа")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Детали по сборке отмененного подзаказа",
            dependsOnMethods = {"getAssemblyItemsOfExistingShipment", "getAssemblyItemsOfShipmentAfterAssembling"})
    public void getCancelledAssemblyItemsOfShipment() {
        changeItemToCancel(shipment.getNumber(), "0");

        final Response responseWithAssemblyItems = ShipmentsV2Request.AssemblyItems.GET(shipment.getNumber());
        checkStatusCode200(responseWithAssemblyItems);
        AssemblyItemV2 assemblyItem = responseWithAssemblyItems.as(AssemblyItemsV2Response.class).getAssemblyItems().get(0);
        checkAssemblyItem(shipment, assemblyItem, StateV2.CANCELED);
    }

    @Skip(onServer = Server.STAGING) // на stf-0 нет офферов с Pricer::PerPack
    @CaseIDs(value = {@CaseId(534), @CaseId(535)})
    @Story("Детали по сборке подзаказа")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Детали по сборке существующего подзаказа c выбранным типом цены",
            dataProvider = "priceTypes",
            dataProviderClass = RestDataProvider.class,
            dependsOnMethods = "getCancelledAssemblyItemsOfShipment")
    public void getAssemblyItemsWithPriceType(ProductPriceTypeV2 priceType) {
        UserData userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        apiV2.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID, priceType);
        ShipmentV2 shipment = apiV2.createOrder().getShipments().get(0);
        final Response response = ShipmentsV2Request.AssemblyItems.GET(shipment.getNumber());
        checkStatusCode200(response);
        AssemblyItemV2 assemblyItem = response.as(AssemblyItemsV2Response.class).getAssemblyItems().get(0);
        checkAssemblyItem(shipment, assemblyItem, StateV2.PENDING);
    }
}
