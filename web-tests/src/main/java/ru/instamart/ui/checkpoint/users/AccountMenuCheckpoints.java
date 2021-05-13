package ru.instamart.ui.checkpoint.users;

import io.qameta.allure.Step;
import ru.instamart.ui.checkpoint.Checkpoint;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.core.util.StringUtil.failMessage;
import static ru.instamart.ui.module.Base.kraken;

public class AccountMenuCheckpoints implements Checkpoint {

    @Step("Проверяем, что всплывающее меню профиля юзера открылось")
    public void checkIsAccountMenuOpen(){
        log.info("> проверяем, что всплывающее меню профиля юзера открылось");
        assertTrue(
                kraken.detect().isAccountMenuOpen(),
                failMessage("Не открывается всплывающее меню профиля Sbermarket"));
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что всплывающее меню профиля юзера закрылось")
    public void checkIsAccountMenuClosed(){
        log.info("> проверяем, что всплывающее меню профиля юзера закрылось");
        assertFalse(
                kraken.detect().isAccountMenuOpen(),
                failMessage("Не закрывается всплывающее меню профиля Sbermarket"));
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что открылась модалка с зонами доставки из меню профиля")
    public void checkIsDeliveryMenuOpen(){
        log.info("> проверяем, что открылась модалка с зонами доставки из меню профиля");
        assertTrue(
                kraken.detect().isDeliveryModalOpen(),
                failMessage("Не открывается модалка 'Доставка' из всплывающего меню 'Профиль'"));
        log.info("✓ Успешно");
    }
}
