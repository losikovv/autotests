package ru.instamart.reforged.stf.block.header;

import io.qameta.allure.Step;
import org.testng.asserts.SoftAssert;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertEquals;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface HeaderCheck extends Check, HeaderElement {

    @Step("Проверяем что есть header")
    default void checkHeaderVisible() {
        waitAction().shouldBeVisible(header);
    }

    @Step("Проверяем наличие кнопки выбора адреса")
    default void checkSelectAddressButtonVisible() {
        waitAction().shouldBeVisible(selectAddress);
    }

    @Step("Проверяем наличие времени работы службы поддержки")
    default void checkHotlineWorkHoursVisible() {
        waitAction().shouldBeVisible(hotlineWorkHoursText);
    }

    @Step("Проверяем наличие ссылки 'Для бизнеса'")
    default void checkForB2bVisible() {
        waitAction().shouldBeVisible(forB2B);
    }

    @Step("Проверяем наличие ссылки 'Для производителей'")
    default void checkForBrandsVisible() {
        waitAction().shouldBeVisible(forBrands);
    }

    @Step("Проверяем наличие ссылки 'Как мы работаем'")
    default void checkHowWeWorkVisible() {
        waitAction().shouldBeVisible(howWeWork);
    }

    @Step("Проверяем наличие ссылки 'Контакты'")
    default void checkContactsVisible() {
        waitAction().shouldBeVisible(contacts);
    }

    @Step("Проверяем наличие ссылки 'Помощь'")
    default void checkHelpVisible() {
        waitAction().shouldBeVisible(help);
    }

    @Step("Проверяем наличие ссылки 'Доставка и оплата'")
    default void checkDeliveryAndPaymentVisible() {
        waitAction().shouldBeVisible(deliveryAndPayment);
    }

    @Step("Проверяем наличие ссылки 'Меню категорий'")
    default void checkCategoryMenuVisible() {
        waitAction().shouldBeVisible(categoryMenu);
    }

    @Step("Проверяем наличие ссылки 'Выбор магазина'")
    default void checkStoreSelectorVisible() {
        waitAction().shouldBeVisible(storeSelector);
    }

    @Step("Проверяем наличие поиска")
    default void checkSearchInputVisible() {
        waitAction().shouldBeVisible(searchInput);
    }

    @Step("Проверяем наличие кнопки поиска")
    default void checkSearchButtonVisible() {
        waitAction().shouldBeVisible(searchButton);
    }

    @Step("Проверяем наличие кнопки 'Корзина'")
    default void checkCartVisible() {
        waitAction().shouldBeVisible(cart);
    }

    @Step("Проверяем наличие кнопки 'Избранное' для неавторизованных")
    default void checkFavoritesNoAuthVisible() {
        waitAction().shouldBeVisible(favoriteWithOutAuth);
    }

    @Step("Проверяем, что кнопка профиля не видна")
    default void checkProfileButtonNotVisible() {
        waitAction().shouldNotBeVisible(profile);
    }

    @Step("Проверяем, что кнопка профиля видна")
    default void checkProfileButtonVisible() {
        waitAction().shouldBeVisible(profile);
    }

    @Step("Проверяем, что не выбран адрес доставки")
    default void checkIsShippingAddressNotSet() {
        assertEquals(firstSelectAddress.getText(), "Выберите адрес доставки");
    }

    @Step("Проверяем, что выбран адрес доставки")
    default void checkIsShippingAddressSet() {
        waitAction().shouldNotBeVisible(currentShipAddress);
    }

    @Step("Проверяем, что утановленный адрес: \"{0}\" \n совпадает с адресом, отображаемом на странице: \"{1}\"")
    default void checkIsSetAddressEqualsToInput(String defaultAddress, String currentAddress) {

        final SoftAssert softAssert = new SoftAssert();
        final String[] defaultAddressList = defaultAddress.split(", ");
        log.info("> проверяем, что установленный адрес: '{}' совпадает с адресом на странице: '{}'",
                defaultAddress,
                currentAddress);
        boolean checkState = false;
        for (final String check : defaultAddressList) {
            if (currentAddress.contains(check)) {
                checkState = true;
            } else {
                log.info("> в введенном адресе отсутсвует: {}", check);
                checkState = false;
            }
            softAssert.assertTrue(
                    checkState,
                    "\n> В адресе отображаемом на странице отсутсвует элемент: "
                            + "\n> отображаемый адрес: " + currentAddress
                            + "\n> Ожидаемый элемент: " + check
            );
        }
        softAssert.assertAll();
        log.info("✓ Успешно");
    }

    default void checkLoginIsVisible() {
        waitAction().shouldBeVisible(login);
    }
}
