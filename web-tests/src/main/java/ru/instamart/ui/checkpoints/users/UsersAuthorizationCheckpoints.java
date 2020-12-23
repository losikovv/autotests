package instamart.ui.checkpoints.users;

import instamart.ui.checkpoints.BaseUICheckpoints;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static instamart.ui.modules.Base.kraken;

public class UsersAuthorizationCheckpoints extends BaseUICheckpoints {

    private static final Logger log = LoggerFactory.getLogger(UsersAuthorizationCheckpoints.class);

    private final SoftAssert softAssert = new SoftAssert();

    /**Проверяем, что пользователь не авторизован на сайте*/
    @Step("Проверяем, что пользователь не авторизован на сайте")
    public void checkIsUserNotAuthorized(String message){
        log.info("> проверяем, что пользователь не авторизован на сайте");
        kraken.get().baseUrl();
        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                message+"\n");
    }

    /**Проверяем, что пользователь авторизован на сайте*/
    @Step("Проверяем, что пользователь авторизован на сайте")
    public void checkIsUserAuthorized(String message){
        log.info("> проверяем, что пользователь авторизован на сайте");
        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                message+"\n");
    }

    /**Проверяем, что при авторизации из корзины происходит редирект в чекаут*/
    @Step("Проверяем, что при авторизации из корзины происходит редирект в чекаут")
    public void checkAutoCheckoutRedirect(String message){
        log.info("> проверяем, что при авторизации из корзины происходит редирект в чекаут");
        softAssert.assertTrue(
                kraken.detect().isOnCheckout(),
                "\n"+message);
        softAssert.assertAll();
    }
}
