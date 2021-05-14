package ru.instamart.ui.checkpoints.shoppingcart;

import io.qameta.allure.Step;
import ru.instamart.ui.checkpoints.Checkpoint;
import ru.instamart.ui.modules.Base;
import ru.instamart.ui.objectsmap.Elements;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.core.util.StringUtil.failMessage;
import static ru.instamart.ui.modules.Base.kraken;

public class ShoppingCartCheckpoints implements Checkpoint {

    @Step("Проверяем, что корзина не пуста после действия: {0}")
    public void checkIsCartEmpty(String action,String errorMessage){
        Base.catchAndCloseAd(Elements.Modals.AuthModal.promoModalButton(),2);
        log.info("> проверяем, что корзина не пуста после действия: {}", action);
        assertFalse(
                kraken.detect().isCartEmpty(),
                "\n"+errorMessage);
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что корзина очистилась после действия: {0}")
    public void checkIsCartNotEmpty(String action){
        log.info("> проверяем, что корзина очистилась после действия: {}", action);
        assertTrue(kraken.detect().isCartEmpty(),
                failMessage("Не сбросилась корзина после: " + action));
        log.info("✓ Успешно");
    }
}
