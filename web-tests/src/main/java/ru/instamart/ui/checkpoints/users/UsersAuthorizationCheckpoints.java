package instamart.ui.checkpoints.users;

import instamart.ui.checkpoints.BaseUICheckpoints;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static instamart.core.helpers.HelperBase.verboseMessage;
import static instamart.ui.modules.Base.kraken;

public class UsersAuthorizationCheckpoints extends BaseUICheckpoints {
    SoftAssert softAssert = new SoftAssert();

    /**Проверяем, что пользователь не авторизован на сайте*/
    @Step("Проверяем, что пользователь не авторизован на сайте")
    public void checkIsUserNotAuthorized(String message){
        verboseMessage("Проверяем, что пользователь не авторизован на сайте");
        kraken.get().baseUrl();
        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                message+"\n");
    }

    /**Проверяем, что пользователь авторизован на сайте*/
    @Step("Проверяем, что пользователь авторизован на сайте")
    public void checkIsUserAuthorized(String message){
        verboseMessage("Проверяем, что пользователь авторизован на сайте");
        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                message+"\n");
    }

    /**Проверяем, что при авторизации из корзины происходит редирект в чекаут*/
    @Step("Проверяем, что при авторизации из корзины происходит редирект в чекаут")
    public void checkAutoCheckoutRedirect(String message){
        verboseMessage("Проверяем, что при авторизации из корзины происходит редирект в чекаут\n");
        softAssert.assertTrue(
                kraken.detect().isOnCheckout(),
                "\n"+message);
        softAssertAll();
    }

}
