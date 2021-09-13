package ru.instamart.ui.checkpoint.users;

import io.qameta.allure.Step;
import ru.instamart.ui.checkpoint.Checkpoint;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.kraken.util.StringUtil.failMessage;
import static ru.instamart.ui.module.Base.kraken;

public class AccountMenuCheckpoints implements Checkpoint {

    @Step("Проверяем, что всплывающее меню профиля юзера открылось")
    public void checkIsAccountMenuOpen(){
        log.debug("> проверяем, что всплывающее меню профиля юзера открылось");
        assertTrue(
                kraken.detect().isAccountMenuOpen(),
                failMessage("Не открывается всплывающее меню профиля Sbermarket"));
        log.debug("✓ Успешно");
    }

    @Step("Проверяем, что всплывающее меню профиля юзера закрылось")
    public void checkIsAccountMenuClosed(){
        log.debug("> проверяем, что всплывающее меню профиля юзера закрылось");
        assertFalse(
                kraken.detect().isAccountMenuOpen(),
                failMessage("Не закрывается всплывающее меню профиля Sbermarket"));
        log.debug("✓ Успешно");
    }

    @Step("Проверяем, что открылась модалка с зонами доставки из меню профиля")
    public void checkIsDeliveryMenuOpen(){
        log.debug("> проверяем, что открылась модалка с зонами доставки из меню профиля");
        assertTrue(
                kraken.detect().isDeliveryModalOpen(),
                failMessage("Не открывается модалка 'Доставка' из всплывающего меню 'Профиль'"));
        log.debug("✓ Успешно");
    }
}
