package ru.instamart.ui.checkpoint.shipping;

import io.qameta.allure.Step;
import ru.instamart.ui.checkpoint.Checkpoint;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.kraken.util.StringUtil.failMessage;
import static ru.instamart.ui.module.Base.kraken;

public class ShippingAddressCheckpoints implements Checkpoint {

    @Step("Проверяем, адрес доставки: {0}")
    public void checkIsShippingAddressNotSet(String action){
        log.info("> проверяем, адрес доставки: {}", action);
        assertFalse(
                kraken.detect().isShippingAddressSet(),
                failMessage("Адрес доставки не корректен: "+ action));
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что адрес доставки установлен:")
    public void checkIsShippingAddressSet(String errorMessage){
        log.info("> проверяем, что адрес доставки установлен");
        assertTrue(
                kraken.detect().isShippingAddressSet(),
                failMessage(errorMessage));
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что дефолтный список магазинов открыт")
    public void checkIsStoresDrawerOpen(String errorMessage){
        log.info("> проверяем, что дефолтный список магазинов открыт");
        assertTrue(
                kraken.detect().isStoresDrawerOpen(),
                "\n"+errorMessage+"\n");
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что дефолтный список магазинов закрыт")
    public void checkIsStoresDrawerNotOpen(String errorMessage){
        log.info("> проверяем, что дефолтный список магазинов закрыт");
        assertFalse(
                kraken.detect().isStoresDrawerOpen(),
                "\n"+errorMessage+"\n");
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что дефолтный список магазинов не пустой")
    public void checkIsStoresDrawerNotEmpty(String errorMessage){
        log.info("> проверяем, что дефолтный список магазинов не пустой");
        assertFalse(
                kraken.detect().isStoresDrawerEmpty(),
                "\n>"+errorMessage+"\n");
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что дефолтный список магазинов пуст")
    public void checkIsStoresDrawerEmpty(String errorMessage){
        log.info("> проверяем, что дефолтный список магазинов пуст");
        assertTrue(
                kraken.detect().isStoresDrawerEmpty(),
                "\n>"+errorMessage+"\n");
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что утановленный адрес: \"{0}\" \n совпадает с адресом, отображаемом на странице: \"{1}\"")
    public void checkIsSetAddresEqualsToInput(String defaultAddress, String currentAddress){
        final String[] defaultAdressList = defaultAddress.split(", ");
        log.info("> проверяем, что установленный адрес: {} совпадает с адресом на странице: {}",
                defaultAddress,
                currentAddress);
        String checkState;
        for (final String check: defaultAdressList){
            if (currentAddress.contains(check)) checkState = "contains";
            else {
                log.info("> в введенном адресе отсутсвует: {}", check);
                checkState ="doesn't";
            }
            krakenAssert.assertEquals(
                    checkState, "contains",
                    "\n> В адресе отображаемом на странице отсутсвует элемент: "
                            +"\n> отображаемый адрес: " + currentAddress
                            +"\n> Ожидаемый элемент: " + check
            );
        }
        assertAll();
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что утановленный адрес:\"{0}\" не изменился")
    public void checkIsSetAddressDoesntEqualToInput(String defaultAddress, String currentAddress){
        final String[] defaultAdressList = defaultAddress.split(", ");
        log.info("> проверяем, что адрес доставки не изменился: {}", defaultAddress);
        String checkState;
        for(final String check: defaultAdressList){
            if (currentAddress.contains(check)) checkState = "contains";
            else {
                log.info("> в введенном адресе отсутсвует: {}", check);
                checkState ="doesn't";
            }
            krakenAssert.assertNotEquals(
                    checkState, "contains",
                    "\n> Адрес доставки изменен после выбора предыдущего: "
                            +"\n> отображаемый адрес: " + currentAddress
                            +"\n> Ожидаемый элемент: " + check
            );
        }
        assertAll();
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что модальное окно ввода адреса доставки открыто")
    public void checkIsAddressModalOpen(String errorMessage){
        log.info("> проверяем, что модальное окно ввода адреса доставки открыто");
        assertTrue(kraken.detect().isAddressModalOpen(),
                errorMessage);
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что модальное окно выбора альтернативного магазина открыто")
    public void checkIsChangeStoreModalOpen(String errorMessage){
        log.info("> проверяем, что модальное окно выбора альтернативного магазина открыто");
        assertTrue(
                kraken.detect().isChangeStoreModalOpen(),
                errorMessage);
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что модальное окно выбора альтернативного магазина не открылось")
    public void checkIsChangeStoreModalNotOpen(String errorMessage){
        log.info("> проверяем, что модальное окно выбора альтернативного магазина не открылось");
        assertFalse(
                kraken.detect().isChangeStoreModalOpen(),
                errorMessage);
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что модальное окно Адрес вне зоны доставки появилось")
    public void checkIsAddressOutOfZone(String errorMessage){
        log.info("> проверяем, что модальное окно Адрес вне зоны доставки появилось");
        assertTrue(
                kraken.detect().isAddressOutOfZone(),
                errorMessage);
        log.info("✓ Успешно");
    }
}
