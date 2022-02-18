package ru.instamart.reforged.stf.block.header;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
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

    @Step("Проверяем наличие контейнера поиска")
    default void checkSearchContainerVisible() {
        waitAction().shouldBeVisible(searchContainer);
    }

    @Step("Проверяем, что нотификация после добавления товара в корзину скрыта")
    default void checkCartNotificationIsNotVisible() {
        waitAction().shouldNotBeVisible(cartNotification);
    }

    @Step("Проверяем, что нотификация после добавления товара в корзину показана")
    default void checkCartNotificationIsVisible() {
        waitAction().shouldBeVisible(cartNotification);
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

    @Step("Проверяем, что алерт ошибки минимальной суммы заказа в корзине отображается")
    default void checkMinAmountAlertVisible() {
        waitAction().shouldBeVisible(minAmountAlert);
    }

    @Step("Проверяем, что алерт авторизации или регистрации отображается")
    default void checkAuthOrRegAlertVisible() {
        waitAction().shouldBeVisible(authOrRegAlert);
    }

    @Step("Проверяем, что не выбран адрес доставки")
    default void checkIsShippingAddressNotSet() {
        assertEquals(firstSelectAddress.getText(), "Выберите адрес доставки");
    }

    @Step("Проверяем, что саджестор поиска отображается")
    default void checkSuggesterVisible() {
        waitAction().shouldBeVisible(searchSuggester);
    }

    @Step("Проверяем, что категорийные подсказки при поиске алко имеют картинки 18+")
    default void checkAlcoStubInCategories() {
        krakenAssert.assertTrue(taxonCategoriesCollection.getElements().size() ==
                taxonCategoriesCollectionImagesAlco.getElements().size(), "Не все категорийные подсказки имеют картинки-заглушки 18+");
    }

    @Step("Проверяем, что товарные подсказки при поиске алко имеют картинки 18+")
    default void checkAlcoStubInSuggest() {
        krakenAssert.assertTrue(suggesterFirstTabItems.getElements().size() ==
                searchSuggestsCollectionImagesAlco.getElements().size(), "Не все товарные подсказки имеют картинки-заглушки 18+");
    }

    @Step("Проверяем, товарные подсказки в поиске отображаются")
    default void checkSearchSuggestsVisible() {
        waitAction().shouldBeVisible(suggesterFirstTabItems);
    }

    @Step("Проверяем что текст в кнопке поиска в саджесторе изменился при переключении категории")
    default void checkTextSearchButton(final String textWithAllCategory, final String textWithSmthCategory) {
        assertNotEquals(textWithAllCategory, textWithSmthCategory);
    }

    @Step("Проверяем, что категория 'все сразу' отображается в саджесторе")
    default void checkCategoryAllVisible() {
        waitAction().shouldBeVisible(suggesterTabHeaders);
    }

    @Step("Проверяем, что категория 'все сразу' не отображается в саджесторе")
    default void checkCategoryAllInvisible() {
        waitAction().shouldNotBeVisible(suggesterTabHeaders);
    }

    @Step("Проверяем, что инпут поиска пустой")
    default void checkSearchBarEmpty() {
        assertEquals(searchInput.getValue(), "");
    }

    @Step("Проверяем, что выбран адрес доставки")
    default void checkIsShippingAddressSet() {
        waitAction().shouldBeVisible(enteredAddress);
    }

    @Step("Проверяем, что установленный адрес: '{0}' \n совпадает с адресом, отображаемом на странице: '{1}'")
    default void checkIsSetAddressEqualToInput(final String defaultAddress, final String currentAddress) {
        final var defaultAddressList = defaultAddress.split(", ");
        log.debug("> проверяем, что установленный адрес: '{}' совпадает с адресом на странице: '{}'",
                defaultAddress,
                currentAddress);
        for (final var check : defaultAddressList) {
            krakenAssert.assertTrue(
                    currentAddress.contains(check),
                    "\n> В адресе отображаемом на странице отсутствует элемент: "
                            + "\n> отображаемый адрес: " + currentAddress
                            + "\n> Ожидаемый элемент: " + check
            );
        }
        assertAll();
    }

    @Step("Проверяем, что установленный адрес:\"{0}\" не изменился")
    default void checkIsSetAddressNotEqualToInput(String defaultAddress, String currentAddress) {
        final String[] defaultAddressList = defaultAddress.split(", ");
        log.debug("> проверяем, что адрес доставки не изменился: {}", defaultAddress);
        String checkState;
        for (final String check : defaultAddressList) {
            if (currentAddress.contains(check)) checkState = "contains";
            else {
                log.debug("> в введенном адресе отсутсвует: {}", check);
                checkState = "doesn't";
            }
            krakenAssert.assertNotEquals(
                    checkState, "contains",
                    "\n> Адрес доставки изменен после выбора предыдущего: "
                            + "\n> отображаемый адрес: " + currentAddress
                            + "\n> Ожидаемый элемент: " + check
            );
        }
        log.debug("✓ Успешно");
    }

    default void checkLoginIsVisible() {
        waitAction().shouldBeVisible(login);
    }

    @Step("Проверяем, что отображается введенный адрес")
    default void checkEnteredAddressIsVisible() {
        waitAction().shouldBeVisible(enteredAddress);
    }

    @Step("Проверяем, что не отображается введенный адрес")
    default void checkEnteredAddressNotVisible() {
        waitAction().shouldNotBeVisible(enteredAddress);
    }
}
