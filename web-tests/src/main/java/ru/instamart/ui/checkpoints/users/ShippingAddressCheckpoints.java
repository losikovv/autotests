package instamart.ui.checkpoints.users;

import instamart.ui.checkpoints.BaseUICheckpoints;
import io.qameta.allure.Step;
import org.testng.asserts.SoftAssert;

import static instamart.ui.modules.Base.kraken;
import static instamart.ui.modules.Base.verboseMessage;

public class ShippingAddressCheckpoints extends BaseUICheckpoints {
    SoftAssert softAssert = new SoftAssert();

    @Step("Проверяем, что адресс доставки сбросился после действия: {}")
    public void checkIsShippingAddressNotSet(String action){
        verboseMessage("> проверяем, что адресс доставки сбросился после: "+ action);
        softAssert.assertFalse(
                kraken.detect().isShippingAddressSet(),
                failMessage("Не сбросился адрес доставки после: "+ action));
        softAssertAll();
    }

}
