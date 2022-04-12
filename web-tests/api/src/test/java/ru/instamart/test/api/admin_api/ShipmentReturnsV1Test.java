package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.v1.ShipmentReturnReasonV1;
import ru.instamart.api.model.v1.ItemReturnV1;
import ru.instamart.api.request.v1.admin.ShipmentReturnsAdminV1Request;
import ru.instamart.api.response.v1.admin.ShipmentReturnV1Response;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.K8sHelper.deleteLastShipmentReturn;

@Epic("ApiV1")
@Feature("История возвратов ДС по заказу")
public class ShipmentReturnsV1Test extends RestBase {

    private String shipmentReturnUUID;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authApiWithAdminNewRoles();
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2312)
    @Test(groups = {"api-instamart-regress"},
            description = "Отображение истории возвратов на стр. возвратов (sberpayments)")
    public void getShipmentReturns() {
        Response response = ShipmentReturnsAdminV1Request.GET("8fbb749f-5353-45a5-90e2-3823fc05df60");//TODO: расхардкодить, добавить проверку схемы.

        checkStatusCode200(response);
    }

    @Issue("B2C-6444")
    @Skip(onServer = Server.STAGING)
    @CaseId(2313)
    @Test(groups = {"api-instamart-regress"}, enabled = false,
            description = "Детализация истории возвратов на стр. возвратов (sberpayments)")
    public void getShipmentReturn() {
        Response response = ShipmentReturnsAdminV1Request.GET("8fbb749f-5353-45a5-90e2-3823fc05df60", "d1ebed51-892e-403a-bf37-62884fce588d");//TODO: расхардкодить, добавить проверку схемы.

        checkStatusCode200(response);
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2311)
    @Test(groups = {"api-instamart-regress"}, enabled = false, //TODO: нужно расхардкодить
            description = "Полный возврат средств за заказ включая доставку и сборку на стр. возвратов (sberpayments)")
    public void postShipmentReturns() {
        var body = ShipmentReturnsAdminV1Request.ShipmentReturnRequest
                .builder()
                .shipmentReturn(ShipmentReturnsAdminV1Request.ShipmentReturn
                        .builder()
                        .comment("Комментарий о клиенте")
                        .reason(ShipmentReturnReasonV1.FAULTY_PRODUCT_QUALITY.getId())
                        .kind("full")
                        .email("man@best.ru")
                        .amount(2839.9)
                        .throughEmployee(false)
                        .itemReturn(ShipmentReturnsAdminV1Request.ItemReturn
                                .builder()
                                .lineItemUuid("3c48b157-5383-427c-9e7f-bce5b55ba9c2")
                                .position(1)
                                .quantity(2)
                                .percent(100)
                                .amount(2398.0)
                                .kind("product")
                                .build())
                        .itemReturn(ShipmentReturnsAdminV1Request.ItemReturn
                                .builder()
                                .position(2)
                                .lineItemUuid("66cb9b90-e976-4355-a8ea-7b702c11bfcd")
                                .quantity(1)
                                .percent(100)
                                .amount(74.9)
                                .kind("product")
                                .build())
                        .itemReturn(ShipmentReturnsAdminV1Request.ItemReturn
                                .builder()
                                .position(3)
                                .lineItemUuid("c38d009b-dda8-4e88-814a-12fce227a2df")
                                .quantity(1)
                                .percent(100)
                                .amount(117.0)
                                .kind("product")
                                .build())
                        .itemReturn(ShipmentReturnsAdminV1Request.ItemReturn
                                .builder()
                                .position(4)
                                .lineItemUuid("null")
                                .quantity(1)
                                .percent(100)
                                .amount(250.0)
                                .kind("delivery")
                                .build())
                        .build())
                .build();
        Response response = ShipmentReturnsAdminV1Request.POST("8fbb749f-5353-45a5-90e2-3823fc05df60", body);//TODO: расхардкодить, добавить проверку схемы.

        checkStatusCode200(response);
        checkResponseJsonSchema(response,ShipmentReturnV1Response.class);
        List<ItemReturnV1> itemReturns = response.as(ShipmentReturnV1Response.class).getShipmentReturn().getItemReturns();
        final SoftAssert sa = new SoftAssert();
        compareTwoObjects(itemReturns.get(0).getLineItemUuid(), body.getShipmentReturn().getItemReturns().get(0).getLineItemUuid(), sa);
        compareTwoObjects(itemReturns.get(1).getLineItemUuid(), body.getShipmentReturn().getItemReturns().get(1).getLineItemUuid(), sa);
        sa.assertAll();
        shipmentReturnUUID = response.as(ShipmentReturnV1Response.class).getShipmentReturn().getUuid();
    }

    @AfterClass(alwaysRun = true)
    public void postconditions() {
        if (shipmentReturnUUID != null) deleteLastShipmentReturn();
    }
}
