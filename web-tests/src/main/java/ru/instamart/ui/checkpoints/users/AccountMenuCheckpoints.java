package ru.instamart.ui.checkpoints.users;

import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static ru.instamart.ui.modules.Base.kraken;

public class AccountMenuCheckpoints extends BaseUICheckpoints {

    private static final Logger log = LoggerFactory.getLogger(AccountMenuCheckpoints.class);

    SoftAssert softAssert = new SoftAssert();

    @Step("Проверяем, что всплывающее меню профиля юзера открылось")
    public void checkIsAccountMenuOpen(){
        log.info("> проверяем, что всплывающее меню профиля юзера открылось");
        softAssert.assertTrue(
                kraken.detect().isAccountMenuOpen(),
                failMessage("Не открывается всплывающее меню профиля Sbermarket"));
        softAssert.assertAll();
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что всплывающее меню профиля юзера закрылось")
    public void checkIsAccountMenuClosed(){
        log.info("> проверяем, что всплывающее меню профиля юзера закрылось");
        softAssert.assertFalse(
                kraken.detect().isAccountMenuOpen(),
                failMessage("Не закрывается всплывающее меню профиля Sbermarket"));
        softAssert.assertAll();
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что открылась модалка с зонами доставки из меню профиля")
    public void checkIsDeliveryMenuOpen(){
        log.info("> проверяем, что открылась модалка с зонами доставки из меню профиля");
        Assert.assertTrue(
                kraken.detect().isDeliveryModalOpen(),
                failMessage("Не открывается модалка 'Доставка' из всплывающего меню 'Профиль'"));
        log.info("✓ Успешно");
    }
}
