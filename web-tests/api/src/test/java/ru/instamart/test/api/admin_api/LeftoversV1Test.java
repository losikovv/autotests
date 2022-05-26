package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v1.admin.LeftoversAdminV1Request;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.TimeUtil;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.kraken.data.PaymentCards.testCardNo3dsWithSpasibo;

@Epic("ApiV1")
@Feature("Остатки")
public class LeftoversV1Test extends RestBase {

    private String shipmentUUID;
    private OrderV2 order;

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
    }

    @CaseId(2310)
    @Test(groups = {"api-instamart-regress"}, enabled = false, // TODO: Сейчас 500-ая
            //undefined method `shipments' for nil:NilClass , уточнить у Манаса, что еще требуется
            description = "Отображение таблицы товаров на стр. возвратов (sberpayments)")
    public void getLeftovers() {
        Response response = LeftoversAdminV1Request.GET(shipmentUUID);
        checkStatusCode200(response);
    }
}
