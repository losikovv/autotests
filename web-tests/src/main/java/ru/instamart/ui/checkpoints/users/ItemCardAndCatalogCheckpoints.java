package instamart.ui.checkpoints.users;

import instamart.ui.checkpoints.BaseUICheckpoints;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static instamart.core.helpers.HelperBase.verboseMessage;
import static instamart.ui.modules.Base.kraken;

public class ItemCardAndCatalogCheckpoints extends BaseUICheckpoints {
    SoftAssert softAssert = new SoftAssert();

    @Step("Проверяем, что карточка товара открыта")
    public void checkIsItemCardOpen(String errorMessage){
        verboseMessage("> проверяем, что карточка товара открылась");
        softAssert.assertTrue(
                kraken.detect().isItemCardOpen(),
                errorMessage);
        softAssert.assertAll();
        verboseMessage("✓ Успешно");
    }

    @Step("Проверяем, что карточка товара закрыта")
    public void checkIsItemCardClosed(String errorMessage){
        verboseMessage("> проверяем, что карточка товара закрылась");
        softAssert.assertFalse(
                kraken.detect().isItemCardOpen(),
                errorMessage);
        softAssert.assertAll();
        verboseMessage("✓ Успешно");
    }

    @Step("Проверяем, что шторка каталога открылась")
    public void checkIsCatalogDrawerOpen(String errorMessage){
        verboseMessage("> проверяем, что шторка каталога открылась");
        Assert.assertTrue(
                kraken.detect().isCatalogDrawerOpen(),
                errorMessage);
        verboseMessage("✓ Успешно");
    }

    @Step("Проверяем, что шторка каталога закрылась")
    public void checkIsCatalogDrawerClosed(String errorMessage){
        verboseMessage("> проверяем, что шторка каталога закрылась");
        Assert.assertFalse(
                kraken.detect().isCatalogDrawerOpen(),
                errorMessage);
        verboseMessage("✓ Успешно");
    }
}
