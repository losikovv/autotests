package instamart.ui.checkpoints.users;

import instamart.ui.checkpoints.BaseUICheckpoints;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static instamart.core.helpers.HelperBase.verboseMessage;
import static instamart.ui.modules.Base.kraken;

public class FavoriteItemsCheckpoints extends BaseUICheckpoints {
    SoftAssert softAssert = new SoftAssert();

    @Step("Проверяем переход в категорию любимых товаров")
    public void checkIsFavoriteOpen(){
        verboseMessage("> проверяем, что категория любимых товаров открыта");
        Assert.assertTrue(
                kraken.detect().isInFavorites(),
                "Не работает переход в любимые товары по кнопке в шапке\n");
        verboseMessage("✓ Успешно");
    }
}
