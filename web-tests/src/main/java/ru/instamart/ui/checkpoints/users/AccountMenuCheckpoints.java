package instamart.ui.checkpoints.users;

import instamart.ui.checkpoints.BaseUICheckpoints;
import io.qameta.allure.Step;
import org.testng.asserts.SoftAssert;

import static instamart.core.helpers.HelperBase.verboseMessage;
import static instamart.ui.modules.Base.kraken;

public class AccountMenuCheckpoints extends BaseUICheckpoints {
    SoftAssert softAssert = new SoftAssert();

    @Step("Проверяем, что всплывающее меню профиля юзера открылось")
    public void checkIsAccountMenuOpen(){
        verboseMessage("> проверяем, что всплывающее меню профиля юзера открылось");
        softAssert.assertTrue(
                kraken.detect().isAccountMenuOpen(),
                failMessage("Не открывается всплывающее меню профиля Sbermarket"));
        softAssert.assertAll();
        verboseMessage("✓ Успешно");
    }

    @Step("Проверяем, что всплывающее меню профиля юзера закрылось")
    public void checkIsAccountMenuClosed(){
        verboseMessage("> проверяем, что всплывающее меню профиля юзера закрылось");
        softAssert.assertFalse(
                kraken.detect().isAccountMenuOpen(),
                failMessage("Не закрывается всплывающее меню профиля Sbermarket"));
        softAssert.assertAll();
        verboseMessage("✓ Успешно");
    }
}
