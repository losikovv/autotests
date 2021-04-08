package ru.instamart.ui.checkpoints.users;

import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import ru.instamart.ui.modules.Base;
import ru.instamart.ui.objectsmap.Elements;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;

import static ru.instamart.ui.modules.Base.kraken;

public class ShoppingCartCheckpoints extends BaseUICheckpoints {

    private static final Logger log = LoggerFactory.getLogger(ShoppingCartCheckpoints.class);
    private final SoftAssert softAssert = new SoftAssert();

    @Step("Проверяем, что корзина не пуста после действия: {0}")
    public void checkIsCartEmpty(String action,String errorMessage){
        Base.catchAndCloseAd(Elements.Modals.AuthModal.promoModalButton(),2);
        log.info("> проверяем, что корзина не пуста после действия: {}", action);
        softAssert.assertFalse(
                kraken.detect().isCartEmpty(),
                "\n"+errorMessage);
        softAssert.assertAll();
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что корзина очистилась после действия: {0}")
    public void checkIsCartNotEmpty(String action){
        log.info("> проверяем, что корзина очистилась после действия: {}", action);
        softAssert.assertTrue(kraken.detect().isCartEmpty(),
                failMessage("Не сбросилась корзина после: " + action));
        softAssert.assertAll();
        log.info("✓ Успешно");
    }
}
