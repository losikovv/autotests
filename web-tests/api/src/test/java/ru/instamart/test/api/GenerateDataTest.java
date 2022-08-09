package ru.instamart.test.api;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.api.helper.ApiV2Helper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.PaymentCards;
import ru.instamart.kraken.data.user.UserManager;

@Slf4j
public class GenerateDataTest {
    private final ApiV2Helper apiV2 = new ApiV2Helper();
    private final ApiHelper helper = new ApiHelper();
    private final int numberOfTestRuns = 20;
    private final int shipmentSingleForUICount = 10;

    @Test(groups = "generate-data")
    public void generateOrdersWithSingleItem() {
        SessionFactory.makeSession(SessionType.API_V2);
        for (int i = 0; i < numberOfTestRuns; i++) {
            log.info("Создание заказа для шоппера с 1 товаром");
            apiV2.order(
                    SessionFactory.getSession(SessionType.API_V2).getUserData(),
                    EnvironmentProperties.DEFAULT_SID,
                    1,
                    "SHP-TEST-SINGLE");
        }
    }

    @Test(groups = "generate-data")
    public void generateOrdersWithMultipleItems() {
        SessionFactory.makeSession(SessionType.API_V2);
        for (int i = 0; i < (numberOfTestRuns * 2); i++) {
            log.info("Создание заказа для шоппера с несколькими товарами");
            apiV2.order(
                    SessionFactory.getSession(SessionType.API_V2).getUserData(),
                    EnvironmentProperties.DEFAULT_SID,
                    5,
                    "SHP-TEST-MULTI");
        }
    }

    @Test(groups = "generate-data-ui")
    public void generateUIOrdersWithSingleItem() {
        SessionFactory.makeSession(SessionType.API_V2);
        for (int i = 0; i < shipmentSingleForUICount; i++) {
            log.info("Создание заказа для тестов UI с 1 товаром");
            apiV2.order(
                    SessionFactory.getSession(SessionType.API_V2).getUserData(),
                    EnvironmentProperties.DEFAULT_SID,
                    1,
                    "UI-TEST-SINGLE");
        }
    }

    @Test(groups = "generate-data-ui")
    public void generateUIOrdersWithSingleItemForSberPay() {
        var userData = UserManager.getQaUser();
        helper.bindCardToUser(userData, EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID,
                PaymentCards.testBusinessCard());
        for (int i = 0; i < 1; i++) {
            log.info("Создание заказа для тестов UI с 1 товаром");
            helper.makeOrderWithComment(userData,
                    EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID,
                    "ORDER-FOR-SBERPAY");
        }
    }
}
