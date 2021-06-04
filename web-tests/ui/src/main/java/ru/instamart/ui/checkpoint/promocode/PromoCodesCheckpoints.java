package ru.instamart.ui.checkpoint.promocode;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.ui.checkpoint.Checkpoint;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.kraken.util.StringUtil.failMessage;
import static ru.instamart.ui.module.Base.kraken;

public class PromoCodesCheckpoints implements Checkpoint {

    @Step("Проверяем, что промокод применился")
    public void checkIsPromoCodeApplied(){
        log.info("> проверяем, что промокод применился в чекауте");
        assertTrue(
                kraken.detect().isPromocodeApplied(),
                failMessage("Не применяется промокод в чекауте\n"));
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что промокод удалился")
    public void checkIsPromoCodeNotApplied(){
        log.info("> проверяем, что промокод удалился");
        assertFalse(
                kraken.detect().isPromocodeApplied(),
                failMessage("Промокод присутствует в заказе, но должен быть удален\n"));
        log.info("✓ Успешно");
    }

}
