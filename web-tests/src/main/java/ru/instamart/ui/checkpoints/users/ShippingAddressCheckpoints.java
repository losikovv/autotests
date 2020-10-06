package instamart.ui.checkpoints.users;

import instamart.ui.checkpoints.BaseUICheckpoints;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static instamart.ui.modules.Base.kraken;
import static instamart.ui.modules.Base.verboseMessage;

public class ShippingAddressCheckpoints extends BaseUICheckpoints {
    SoftAssert softAssert = new SoftAssert();

    @Step("Проверяем, адрес доставки: {0}")
    public void checkIsShippingAddressNotSet(String action){
        verboseMessage("> проверяем, адрес доставки: "+ action);
        softAssert.assertFalse(
                kraken.detect().isShippingAddressSet(),
                failMessage("Адрес доставки не корректен: "+ action));
        softAssert.assertAll();
    }

    @Step("Проверяем, что адрес доставки установлен:")
    public void checkIsShippingAddressSet(String errorMessage){
        verboseMessage("> проверяем, что адрес доставки установлен");
        softAssert.assertTrue(
                kraken.detect().isShippingAddressSet(),
                failMessage(errorMessage));
        softAssert.assertAll();
    }

    @Step("Проверяем, что дефолтный список магазинов открыт")
    public void checkIsStoresDrawerOpen(String errorMessage){
        verboseMessage("> проверяем, что дефолтный список магазинов открыт");
        Assert.assertTrue(
                kraken.detect().isStoresDrawerOpen(),
                "\n"+errorMessage+"\n");
    }

    @Step("Проверяем, что дефолтный список магазинов закрыт")
    public void checkIsStoresDrawerNotOpen(String errorMessage){
        verboseMessage("> проверяем, что дефолтный список магазинов закрыт");
        Assert.assertFalse(
                kraken.detect().isStoresDrawerOpen(),
                "\n"+errorMessage+"\n");
    }

    @Step("Проверяем, что дефолтный список магазинов не пустой")
    public void checkIsStoresDrawerNotEmpty(String errorMessage){
        verboseMessage("> проверяем, что дефолтный список магазинов не пустой");
        Assert.assertFalse(
                kraken.detect().isStoresDrawerEmpty(),
                "\n>"+errorMessage+"\n");
    }

    @Step("Проверяем, что дефолтный список магазинов пуст")
    public void checkIsStoresDrawerEmpty(String errorMessage){
        verboseMessage("> проверяем, что дефолтный список магазинов пуст");
        softAssert.assertTrue(
                kraken.detect().isStoresDrawerEmpty(),
                "\n>"+errorMessage+"\n");
        softAssert.assertAll();
    }

    @Step("Проверяем, что утановленный адрес: \"{1}\" \nсовпадает с дефолтным: \"{0}\"")
    public void checkIsSetAddresEqualsToInput(String defaultAddress, String currentAddress){
        verboseMessage("> проверяем, что дефолтный: "+defaultAddress+"\n адрес совпадает с введенным: "+currentAddress);
        softAssert.assertEquals(
                currentAddress, defaultAddress,
                "\n> Установленный адрес доставки не совпадает с введенным"
                        +"\n> Установлен адрес: " + currentAddress
                        +"\n> Ожидаемый адрес: " + defaultAddress
        );
        softAssert.assertAll();
    }

}
