package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.models.EnvironmentData;

public class AddressHelper extends HelperBase {

    AddressHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    /**
     * Установить адрес доставки
     */
    public void set(String address) {
        message("Устанавливаем адрес доставки >>> " + address + "\n");
        if (kraken.grab().currentURL().equals(fullBaseUrl)) {
            kraken.perform().fillField(Elements.Landing.addressField(), address);
            kraken.await().implicitly(1); // Ожидание загрузки адресных саджестов
            kraken.perform().click(Elements.Landing.addressSuggest());
            kraken.perform().click(Elements.Landing.selectStoreButton());
            kraken.await().implicitly(1); // Ожидание загрузки витрины магазина
            kraken.await().fluently(
                    ExpectedConditions.invisibilityOfElementLocated(
                            Elements.Modals.fade().getLocator()), "\nНе пропадает адресная модалка\n");
        } else {
            openAddressModal();
            fill(address);
            submit();
        }
    }

    /**
     * Изменить адрес доставки
     */
    public void change(String address) {
        message("Изменяем адрес доставки >>> " + address + "\n");
        openAddressModal();
        clearAddressField();
        fill(address);
        submit();
    }

    /**
     * Свапнуть тестовый и дефолтные адреса
     */
    public void swap() {
        if (kraken.grab().currentShipAddress().equals(Addresses.Moscow.defaultAddress())) {
            change(Addresses.Moscow.testAddress());
        } else {
            change(Addresses.Moscow.testAddress());
            change(Addresses.Moscow.defaultAddress());
        }
    }

    /**
     * Выбрать первый адресный саджест
     */
    private void selectAddressSuggest() {
        // TODO переделать на fluent-ожидание подсказок
        if (kraken.detect().isShippingAddressSuggestsPresent()) {
            kraken.perform().click(Elements.Modals.AddressModal.addressSuggest());
            kraken.await().fluently(ExpectedConditions.elementToBeClickable(Elements.Modals.AddressModal.saveButton().getLocator()),
                    "Неактивна кнопка сохранения адреса");
        } else {
            throw new AssertionError("Нет адресных подсказок, невозможно выбрать адрес");
        }
    }

    /**
     * Открыть модалку ввода адреса
     */
    public void openAddressModal() {
        if (kraken.detect().isAddressModalOpen()) {
            debugMessage("Пропускаем открытие модалки адреса, она уже открыта");
        } else {
            kraken.perform().click(Elements.Header.shipAddressButton());
            kraken.await().simply(1); // Ожидание анимации открытия адресной модалки
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(
                            Elements.Modals.AddressModal.popup().getLocator()),
                                "Не открылась модалка ввода адреса доставки\n");
        }
    }

    /**
     * Очистить поле в адресной модалке
     */
    public void clearAddressField() {
        kraken.perform().fillField(Elements.Modals.AddressModal.addressField(), "");
    }

    /**
     * Ввести адрес в адресной модалке
     */
    public void fill(String address) {
        kraken.perform().fillField(Elements.Modals.AddressModal.addressField(), address);
        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(
                        Elements.Modals.AddressModal.addressSuggest().getLocator()),
                            "Не подтянулись адресные подсказки\n"
        );
        selectAddressSuggest();
    }

    /**
     * Применить введенный адрес в адресной модалке
     */
    public void submit() throws AssertionError {
        kraken.perform().click(Elements.Modals.AddressModal.saveButton());
        if (kraken.detect().isAddressOutOfZone()) {
            verboseMessage("Выбранный адрес вне зоны доставки");
            kraken.perform().refresh();
        } else {
            kraken.await().fluently(
                    ExpectedConditions.invisibilityOfElementLocated(
                            Elements.Modals.AddressModal.popup().getLocator()),
                    "Не применяется адрес доставки\n");
        }
    }

    /**
     * Выбрать первый в списке предыдущий адрес в адресной модалке
     */
    public void choseRecent() {
        kraken.perform().click(Elements.Modals.AddressModal.recentAddress());
        kraken.await().implicitly(1); // Ожидание применения предыдущего адреса
    }

    /**
     * Закрыть модалку адреса
     */
    public void closeAddressModal() {
        if (kraken.detect().isAddressModalOpen()) {
            kraken.perform().click(Elements.Modals.AddressModal.closeButton());
            kraken.await().implicitly(1); // Ожидание анимации закрытия адресной модалки
        } else {
            message("Пропускаем закрытие модалки адреса, она уже закрыта");
        }
    }
}