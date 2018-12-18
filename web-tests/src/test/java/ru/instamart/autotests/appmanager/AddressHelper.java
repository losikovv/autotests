package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;

public class AddressHelper extends HelperBase {

    private ApplicationManager kraken;

    AddressHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    /**
     * Установить адрес доставки
     */
    public void set(String address) {
        printMessage("Устанавливаем адрес доставки >>> " + address + "\n");
        if (kraken.grab().currentURL().equals(fullBaseUrl)) {
            kraken.perform().fillField(Elements.Site.Landing.addressField(), address);
            kraken.perform().waitingFor(1); // Ожидание загрузки адресных саджестов
            kraken.perform().click(Elements.Site.Landing.addressSuggest());
            kraken.perform().click(Elements.Site.Landing.selectStoreButton());
            kraken.perform().waitingFor(2); // Ожидание загрузки витрины магазина
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
        printMessage("Изменяем адрес доставки >>> " + address + "\n");
        openAddressModal();
        clearAddressField();
        fill(address);
        submit();
    }

    /**
     * Выбрать первый адресный саджест
     */
    private void selectAddressSuggest() {
        if (kraken.detect().isAnyAddressSuggestsAvailable()) {
            kraken.perform().click(Elements.Site.AddressModal.addressSuggest());
            kraken.perform().waitingFor(1); // Ожидание раздизабливания кнопки сохранения адреса
        } else {
            printMessage("Нет адресных подсказок");
        }
    }

    /**
     * Открыть модалку адреса
     */
    public void openAddressModal() {
        if (kraken.detect().isAddressModalOpen()) {
            printMessage("Пропускаем открытие модалки адреса, она уже открыта");
        } else {
            kraken.perform().click(Elements.Site.Header.shipAddressButton());
            kraken.perform().waitingFor(1); // Ожидание анимации открытия адресной модалки
        }
    }

    /**
     * Очисить поле в адресной модалке
     */
    public void clearAddressField() {
        kraken.perform().fillField(Elements.Site.AddressModal.addressField(), "");
    }

    /**
     * Ввести адрес в адресной модалке
     */
    public void fill(String address) {
        kraken.perform().fillField(Elements.Site.AddressModal.addressField(), address);
        selectAddressSuggest();
    }

    /**
     * Нажать Сохранить в адресной модалке
     */
    public void submit() {
        kraken.perform().click(Elements.Site.AddressModal.saveButton());
        kraken.perform().waitingFor(2); // Ожидание применения адреса доставки
        if(kraken.detect().isAddressModalOpen()){
            printMessage("⚠ Проблемы с производительностью: слишком медленно применяется адрес доставки");
            kraken.perform().waitingFor(2); // Дополнительное ожидание применения адреса доставки, для стабильности
        }
    }

    /**
     * Выбрать первый в списке предыдущий адрес в адресной модалке
     */
    public void choseRecent() {
        kraken.perform().click(Elements.Site.AddressModal.recentAddress());
    }

    /**
     * Закрыть модалку адреса
     */
    public void closeAddressModal() {
        if (kraken.detect().isAddressModalOpen()) {
            kraken.perform().click(Elements.Site.AddressModal.closeButton());
            kraken.perform().waitingFor(1); // Ожидание анимации закрытия адресной модалки
        } else {
            printMessage("Пропускаем закрытие модалки адреса, она уже закрыта");
        }
    }
}