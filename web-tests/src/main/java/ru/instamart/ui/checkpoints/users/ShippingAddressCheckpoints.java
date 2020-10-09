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

    @Step("Проверяем, что утановленный адрес: \"{0}\" \n совпадает с адресом, отображаемом на странице: \"{1}\"")
    public void checkIsSetAddresEqualsToInput(String defaultAddress, String currentAddress){
        String []defaultAdressList = defaultAddress.split(", ");
        verboseMessage("> проверяем, что установленный адрес: "+defaultAddress+
                 "\n совпадает с адресом на странице: "+currentAddress);
        String checkState ="";
        for(String check: defaultAdressList){
            if(currentAddress.contains(check)) checkState = "contains";
            else{
                verboseMessage("> в введенном адресе отсутсвует: "+check);
                checkState ="doesn't";
            }
            softAssert.assertEquals(
                    checkState, "contains",
                    "\n> В адресе отображаемом на странице отсутсвует элемент: "
                            +"\n> отображаемый адрес: " + currentAddress
                            +"\n> Ожидаемый элемент: " + check
            );
        }
        softAssert.assertAll();
    }

    @Step("\"Проверяем, что утановленный адрес:\"{0}\" не изменился")
    public void checkIsSetAddressDoesntEqualToInput(String defaultAddress, String currentAddress){
        String []defaultAdressList = defaultAddress.split(", ");
        verboseMessage("> проверяем, что адрес доставки не изменился: "+defaultAddress);
        String checkState ="";
        for(String check: defaultAdressList){
            if(currentAddress.contains(check)) checkState = "contains";
            else{
                verboseMessage("> в введенном адресе отсутсвует: "+check);
                checkState ="doesn't";
            }
            softAssert.assertNotEquals(
                    checkState, "contains",
                    "\n> Адрес доставки изменен после выбора предыдущего: "
                            +"\n> отображаемый адрес: " + currentAddress
                            +"\n> Ожидаемый элемент: " + check
            );
        }
        softAssert.assertAll();
    }

}
