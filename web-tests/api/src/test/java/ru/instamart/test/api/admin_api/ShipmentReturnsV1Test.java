package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.v1.ShipmentReturnReasonV1;
import ru.instamart.api.model.v1.ShipmentReturnV1;
import ru.instamart.api.model.v2.LineItemV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v1.admin.ShipmentReturnsAdminV1Request;
import ru.instamart.api.response.v1.admin.ShipmentReturnV1Response;
import ru.instamart.api.response.v1.admin.ShipmentReturnsV1Response;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.util.TimeUtil;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkShipmentReturn;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.kraken.data.PaymentCards.testCardNo3dsWithSpasibo;

@Epic("ApiV1")
@Feature("История возвратов ДС по заказу")
public class ShipmentReturnsV1Test extends RestBase {

    private String shipmentReturnUUID;
    private String shipmentUUID;
    private OrderV2 order;
    private LineItemV2 lineItem;
    private ShipmentReturnsAdminV1Request.ShipmentReturnRequest body;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        UserData user = UserManager.getQaUser();
        apiV2.authByQA(user);
        apiV2.dropAndFillCart(user, EnvironmentProperties.DEFAULT_SID);
        apiV2.bindCardToUser(user, apiV2.getCurrentOrderNumber(), testCardNo3dsWithSpasibo());
        order = apiV2.setDefaultAttributesAndCompleteOrder("4468");
        SpreeOrdersDao.INSTANCE.updateShipmentStateToShip(order.getNumber(), TimeUtil.getDbDate());
        admin.authApiWithAdminNewRoles();
        shipmentUUID = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber()).getUuid();
        lineItem = order.getShipments().get(0).getLineItems().get(0);
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2311)
    @Test(groups = {"api-instamart-regress"},
            description = "Полный возврат средств за заказ включая доставку и сборку на стр. возвратов (sberpayments)")
    public void postShipmentReturns() {
        body = ShipmentReturnsAdminV1Request.ShipmentReturnRequest
                .builder()
                .shipmentReturn(ShipmentReturnsAdminV1Request.ShipmentReturn
                        .builder()
                        .comment("Комментарий о клиенте")
                        .reason(ShipmentReturnReasonV1.FAULTY_PRODUCT_QUALITY.getId())
                        .kind("full")
                        .email("man@best.ru")
                        .amount(order.getTotal())
                        .throughEmployee(false)
                        .itemReturn(ShipmentReturnsAdminV1Request.ItemReturn
                                .builder()
                                .lineItemUuid(lineItem.getUuid())
                                .position(1)
                                .quantity(lineItem.getQuantity())
                                .percent(100)
                                .amount(lineItem.getTotal())
                                .kind("product")
                                .build())
                        .build())
                .build();
        Response response = ShipmentReturnsAdminV1Request.POST(shipmentUUID, body);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShipmentReturnV1Response.class);
        ShipmentReturnV1 shipmentReturn = response.as(ShipmentReturnV1Response.class).getShipmentReturn();
        shipmentReturnUUID = shipmentReturn.getUuid();
        checkShipmentReturn(body, shipmentReturn);
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2312)
    @Test(groups = {"api-instamart-regress"},
            description = "Отображение истории возвратов на стр. возвратов (sberpayments)",
            dependsOnMethods = "postShipmentReturns")
    public void getShipmentReturns() {
        Response response = ShipmentReturnsAdminV1Request.GET(shipmentUUID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShipmentReturnsV1Response.class);
        ShipmentReturnV1 shipmentReturn = response.as(ShipmentReturnsV1Response.class).getShipmentReturns().get(0);
        compareTwoObjects(shipmentReturn.getUuid(), shipmentReturnUUID);
    }

    @Issue("B2C-6444")
    @Skip(onServer = Server.STAGING)
    @CaseId(2313)
    @Test(groups = {"api-instamart-regress"},
            description = "Детализация истории возвратов на стр. возвратов (sberpayments)",
            dependsOnMethods = "postShipmentReturns")
    public void getShipmentReturn() {
        Response response = ShipmentReturnsAdminV1Request.GET(shipmentUUID, shipmentReturnUUID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShipmentReturnV1Response.class);
        ShipmentReturnV1 shipmentReturn = response.as(ShipmentReturnV1Response.class).getShipmentReturn();
        checkShipmentReturn(body, shipmentReturn);
    }
}
