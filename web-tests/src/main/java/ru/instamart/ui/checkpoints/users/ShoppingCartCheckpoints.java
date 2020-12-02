package instamart.ui.checkpoints.users;

import instamart.ui.checkpoints.BaseUICheckpoints;
import io.qameta.allure.Step;
import org.testng.asserts.SoftAssert;

import static instamart.core.helpers.HelperBase.verboseMessage;
import static instamart.ui.modules.Base.kraken;

public class ShoppingCartCheckpoints extends BaseUICheckpoints {
    SoftAssert softAssert = new SoftAssert();

    @Step("Проверяем, что корзина не пуста после действия: {0}")
    public void checkIsCartEmpty(String action,String errorMessage){
        verboseMessage("> проверяем, что корзина не пуста после действия: "+action+"\n");
        softAssert.assertFalse(
                kraken.detect().isCartEmpty(),
                "\n"+errorMessage);
        softAssert.assertAll();
        verboseMessage("✓ Успешно");
    }

    @Step("Проверяем, что корзина очистилась после действия: {0}")
    public void checkIsCartNotEmpty(String action){
        verboseMessage("> проверяем, что корзина очистилась после действия: "+action+"\n");
        softAssert.assertTrue(kraken.detect().isCartEmpty(),
                failMessage("Не сбросилась корзина после: "+action));
        softAssert.assertAll();
        verboseMessage("✓ Успешно");
    }
}
