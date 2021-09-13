package ru.instamart.ui.checkpoint.admin;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.ui.Elements;
import ru.instamart.ui.checkpoint.Checkpoint;

import static ru.instamart.kraken.util.StringUtil.failMessage;
import static ru.instamart.ui.module.Base.kraken;

public class AdminPageCheckpoints implements Checkpoint {

    @Step("Проверяем авторизацию в Админке")
    public void checkIsAdminPageOpen() {
        log.debug("> проверяем, что юзер успешно авторизован в Админке");
        Assert.assertTrue(
                kraken.detect().isInAdmin(),
                "Нет доступна админка пользователю c админ. правми");
        log.debug("✓ Успешно");
    }

    @Step("Проверяем что юзер не авторизован в Админке")
    public void checkIsNotAdminPageOpen() {
        log.debug("> проверяем, что юзер не авторизован в Админке");
        Assert.assertFalse(
                kraken.detect().isInAdmin(),
                "Есть доступ в админку у пользователя без админ. прав");
        log.debug("✓ Успешно");
    }

    @Step("Проверяем, что пользователь не авторизован в Админке")
    public void checkIsUserNotAutorisedAdminPage(String logMessage) {
        log.debug("> проверяем, что юзер не авторизован в Админке");
        Assert.assertFalse(
                kraken.detect().isUserAuthorised(), failMessage(logMessage));
        log.debug("✓ Успешно");
    }

    @Step("Проверяем текст сообщения об ошибке для поля username при неудачной авторизации")
    public void checkErrorMessageUserNameAdminPage(String message, String logMessage) {
        log.debug("> проверяем, что текст сообщения об ошибке отображается и он соответсвует ожидаемому результату: " + message);
        krakenAssert.assertTrue(
                kraken.detect().isElementPresent(Elements.Administration.LoginPage.emailFieldErrorText(message)),
                failMessage(logMessage)
        );
        assertAll();
        log.debug("✓ Успешно");
    }

    @Step("Проверяем текст сообщения об ошибке для поля password при неудачной авторизации")
    public void checkPasswordFieldErrorTextAdminPage(String message, String logMessage) {
        log.debug("> проверяем, что текст сообщения об ошибкедля поля " +
                "password отображается и он соответсвует ожидаемому результату: " + message);
        krakenAssert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Administration.LoginPage.passwordFieldErrorText(message)),
                failMessage(logMessage)
        );
        assertAll();
        log.debug("✓ Успешно");
    }
}
