package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v2.SeparatReviewsV2Request;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;
import static ru.instamart.api.helper.K8sHelper.changeToShip;
import static ru.instamart.kraken.util.TimeUtil.getPastZoneDbDate;

public class ReviewableShipmentNewV2Test extends RestBase {

    private String shipmentNumber;

    @BeforeClass(alwaysRun = true)
    public void before() {
        var sid = EnvironmentProperties.DEFAULT_SID;
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        final UserData userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        final OrderV2 order = apiV2.order(userData, sid);
        shipmentNumber = order.getShipments().get(0).getNumber();
        changeToShip(shipmentNumber);
        String pastZoneDbDate = getPastZoneDbDate(7L);
        Allure.step("Обновление даты доставки в БД", ()->SpreeShipmentsDao.INSTANCE.updateShipmentsByNumber(pastZoneDbDate, shipmentNumber));
    }

    @CaseId(2337)
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Получение информации о заказе | заказ старше 7 дней")
    public void getReviewableShipment() {
        final Response response = SeparatReviewsV2Request.ReviewableShipment.GET();
        checkStatusCode404(response);
        checkError(response, "ActiveRecord::RecordNotFound");
    }
}
