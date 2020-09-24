package instamart.ui.checkpoints.users;

import instamart.ui.checkpoints.BaseUICheckpoints;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static instamart.core.helpers.HelperBase.verboseMessage;
import static instamart.ui.modules.Base.kraken;

public class UsersAuthorizationCheckpoints extends BaseUICheckpoints {
    SoftAssert softAssert = new SoftAssert();

    /**Проверяем, что пользователь не авторизован на сайте*/
    public void checkIsUserNotAuthorized(String message){
        kraken.get().baseUrl();
        Assert.assertFalse(
                kraken.detect().isUserAuthorised(),
                message+"\n");
    }

    /**Проверяем, что пользователь авторизован на сайте*/
    public void checkIsUserAuthorized(String message){
        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                message+"\n");
    }

    public void checkAutoCheckoutRedirect(String message){
        verboseMessage("Проверяем, что при авторизации из корзины происходит редирект в чекаут\n");
        softAssert.assertTrue(
                kraken.detect().isOnCheckout(),
                "\n"+message);
        softAssertAll();
    }

}
