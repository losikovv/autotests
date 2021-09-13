package ru.instamart.ui.checkpoint.shoppingcart;

import io.qameta.allure.Step;
import ru.instamart.ui.checkpoint.Checkpoint;
import ru.instamart.ui.module.Base;
import ru.instamart.ui.Elements;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.kraken.util.StringUtil.failMessage;
import static ru.instamart.ui.module.Base.kraken;

public class ShoppingCartCheckpoints implements Checkpoint {

    @Step("Проверяем, что корзина не пуста после действия: {0}")
    public void checkIsCartEmpty(String action,String errorMessage){
        Base.catchAndCloseAd(Elements.Modals.AuthModal.promoModalButton(),2);
        log.debug("> проверяем, что корзина не пуста после действия: {}", action);
        assertFalse(
                kraken.detect().isCartEmpty(),
                "\n"+errorMessage);
        log.debug("✓ Успешно");
    }

    @Step("Проверяем, что корзина очистилась после действия: {0}")
    public void checkIsCartNotEmpty(String action){
        log.debug("> проверяем, что корзина очистилась после действия: {}", action);
        assertTrue(kraken.detect().isCartEmpty(),
                failMessage("Не сбросилась корзина после: " + action));
        log.debug("✓ Успешно");
    }
}
