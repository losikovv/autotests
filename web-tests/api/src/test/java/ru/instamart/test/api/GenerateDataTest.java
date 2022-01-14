package ru.instamart.test.api;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.helper.InstamartApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;

@Slf4j
public class GenerateDataTest {
    InstamartApiHelper apiV2 = new InstamartApiHelper();

    @Test(groups = "generate-data")
    public void generateOrdersWithSingleItem() {
        SessionFactory.makeSession(SessionType.API_V2);
        for (int i = 0; i < 10; i++) {
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
        for (int i = 0; i < 20; i++) {
            log.info("Создание заказа для шоппера с несколькими товарами");
            apiV2.order(
                    SessionFactory.getSession(SessionType.API_V2).getUserData(),
                    EnvironmentProperties.DEFAULT_SID,
                    5,
                    "SHP-TEST-MULTI");
        }
    }
}
