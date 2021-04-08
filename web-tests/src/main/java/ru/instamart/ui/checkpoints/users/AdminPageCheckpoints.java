package ru.instamart.ui.checkpoints.users;

import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import ru.instamart.ui.objectsmap.Elements;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static ru.instamart.ui.modules.Base.kraken;

public class AdminPageCheckpoints extends BaseUICheckpoints {
    private static final Logger log = LoggerFactory.getLogger(AccountMenuCheckpoints.class);

    SoftAssert softAssert = new SoftAssert();

    @Step("Проверяем авторизацию в Админке")
    public void checkIsAdminPageOpen(){
        log.info("> проверяем, что юзер успешно авторизован в Админке");
        Assert.assertTrue(
                kraken.detect().isInAdmin(),
                "Нет доступна админка пользователю c админ. правми");
        log.info("✓ Успешно");
    }

    @Step("Проверяем что юзер не авторизован в Админке")
    public void checkIsNotAdminPageOpen(){
        log.info("> проверяем, что юзер не авторизован в Админке");
        Assert.assertFalse(
                kraken.detect().isInAdmin(),
                "Есть доступ в админку у пользователя без админ. прав");
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что пользователь не авторизован в Админке")
    public void checkIsUserNotAutorisedAdminPage(String logMessage){
        log.info("> проверяем, что юзер не авторизован в Админке");
        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),failMessage(logMessage));
        log.info("✓ Успешно");
    }

    @Step("Проверяем текст сообщения об ошибке для поля username при неудачной авторизации")
    public void checkErrorMessageUserNameAdminPage(String message,String logMessage){
        log.info("> проверяем, что текст сообщения об ошибке отображается и он соответсвует ожидаемому результату: "+message);
        softAssert.assertTrue(
                kraken.detect().isElementPresent(Elements.Administration.LoginPage.emailFieldErrorText(message)),
                failMessage(logMessage)
        );
        softAssert.assertAll();
        log.info("✓ Успешно");
    }

    @Step("Проверяем текст сообщения об ошибке для поля password при неудачной авторизации")
    public void checkPasswordFieldErrorTextAdminPage(String message,String logMessage){
        log.info("> проверяем, что текст сообщения об ошибкедля поля " +
                "password отображается и он соответсвует ожидаемому результату: "+message);
        softAssert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Administration.LoginPage.passwordFieldErrorText(message)),
                failMessage(logMessage)
        );
        softAssert.assertAll();
        log.info("✓ Успешно");
    }
}
