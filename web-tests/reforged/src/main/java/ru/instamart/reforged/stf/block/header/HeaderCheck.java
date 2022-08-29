package ru.instamart.reforged.stf.block.header;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface HeaderCheck extends Check, HeaderElement {

    @Step("Проверяем что есть header")
    default void checkHeaderVisible() {
        krakenAssert.assertTrue(header.is().displayed(), "header не отображается");
    }

    @Step("Проверяем наличие кнопки выбора адреса")
    default void checkSelectAddressButtonVisible() {
        krakenAssert.assertTrue(selectAddress.is().displayed(), "кнопка выбора адреса не отображается");
    }

    @Step("Проверяем наличие времени работы службы поддержки")
    default void checkHotlineWorkHoursVisible() {
        krakenAssert.assertTrue(hotlineWorkHoursText.is().displayed(), "время работы службы поддержки не отображается");
    }

    @Step("Проверяем наличие телефона службы поддержки")
    default void checkHotlinePhoneNumberVisible() {
        krakenAssert.assertTrue(hotlinePhoneNumber.is().displayed(), "телефон службы поддержки не отображается");
    }

    @Step("Проверяем наличие ссылки 'Для бизнеса'")
    default void checkForB2bVisible() {
        krakenAssert.assertTrue(forB2B.is().displayed(), "ссылка 'Для бизнеса' не отображается");
    }

    @Step("Проверяем наличие ссылки 'Для производителей'")
    default void checkForBrandsVisible() {
        krakenAssert.assertTrue(forBrands.is().displayed(), "ссылка 'Для производителей' не отображается");
    }

    @Step("Проверяем наличие ссылки 'Как мы работаем'")
    default void checkHowWeWorkVisible() {
        krakenAssert.assertTrue(howWeWork.is().displayed(), "ссылка 'Как мы работаем' не отображается");
    }

    @Step("Проверяем наличие ссылки 'Контакты'")
    default void checkContactsVisible() {
        krakenAssert.assertTrue(contacts.is().displayed(), "ссылка 'Контакты' не отображается");
    }

    @Step("Проверяем наличие ссылки 'Помощь'")
    default void checkHelpVisible() {
        krakenAssert.assertTrue(help.is().displayed(), "ссылка 'Помощь' не отображается");
    }

    @Step("Проверяем наличие ссылки 'Доставка и оплата'")
    default void checkDeliveryAndPaymentVisible() {
        krakenAssert.assertTrue(deliveryAndPayment.is().displayed(), "ссылка 'Доставка и оплата' не отображается");
    }

    @Step("Проверяем наличие ссылки 'Меню категорий'")
    default void checkCategoryMenuVisible() {
        krakenAssert.assertTrue(categoryMenu.is().displayed(), "ссылка 'Меню категорий' не отображается");
    }

    @Step("Проверяем наличие ссылки 'Выбор магазина'")
    default void checkStoreSelectorVisible() {
        krakenAssert.assertTrue(storeSelector.is().displayed(), "ссылка 'Выбор магазина' не отображается");
    }

    @Step("Проверяем наличие поиска")
    default void checkSearchInputVisible() {
        krakenAssert.assertTrue(searchInput.is().displayed(), "поиск не отображается");
    }

    @Step("Проверяем наличие кнопки поиска")
    default void checkSearchButtonVisible() {
        krakenAssert.assertTrue(searchButton.is().displayed(), "кнопка поиска не отображается");
    }

    @Step("Проверяем наличие контейнера поиска")
    default void checkSearchContainerVisible() {
        krakenAssert.assertTrue(searchContainer.is().displayed(), "контейнер поиска не отображается");
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
        krakenAssert.assertTrue(cart.is().displayed(), "кнопка 'Корзина' не отображается");
    }

    @Step("Проверяем наличие кнопки 'Избранное' для неавторизованных")
    default void checkFavoritesNoAuthVisible() {
        krakenAssert.assertTrue(favoriteWithOutAuth.is().displayed(), "кнопка 'App Store' не отображается");
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

    @Step("Проверяем что текст в кнопке поиска в саджесторе '{0}' изменился при переключении категории на текст '{1}'")
    default void checkTextSearchButton(final String textWithAllCategory, final String textWithSmthCategory) {
        assertNotEquals(textWithAllCategory, textWithSmthCategory, "Текст кнопки поиска не изменился");
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
        assertEquals(searchInput.getValue(), "", "Инпут поиска не пустой");
    }

    @Step("Проверяем, что выбран адрес доставки")
    default void checkIsShippingAddressSet() {
        waitAction().shouldBeVisible(enteredAddress);
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

    @Step("Проверяем, что отображается кнопка логина")
    default void checkLoginIsVisibleSoft() {
        krakenAssert.assertTrue(login.is().displayed(), "кнопка логина не отображается");
    }

    @Step("Проверяем, что отображается кнопка логина")
    default void checkLoginIsVisible() {
        login.shouldBe().visible();
    }

    @Step("Проверяем, что отображается введенный адрес")
    default void checkEnteredAddressIsVisible() {
        waitAction().shouldBeVisible(enteredAddress);
    }

    @Step("Проверяем, что отображается ближайший интервал доставки: '{expectedNextDelivery}'")
    default void checkNextDeliveryEquals(final String expectedNextDelivery) {
        Assert.assertEquals(nextDelivery.getText(), expectedNextDelivery, String.format("Указанный интервал доставки отличается от ожидаемого: '%s'", expectedNextDelivery));
    }

    @Step("Проверяем, что не отображается введенный адрес")
    default void checkEnteredAddressNotVisible() {
        waitAction().shouldNotBeVisible(enteredAddress);
    }

    @Step("Проверяем, что отображается сообщение об ошибке")
    default void checkErrorAlertDisplayed() {
        waitAction().shouldBeVisible(alert);
    }

    @Step("Проверяем, что сообщение об ошибке закрылось")
    default void checkErrorAlertIsNotDisplayed() {
        waitAction().isElementsShouldNotBeExist(alerts);
    }

    @Step("Проверяем, что появилось всплывающее сообщение о возможности предзамены")
    default void checkPrereplacementPopupDisplayed() {
        waitAction().shouldBeVisible(popupAlert);
    }

    @Step("Проверяем, что не отображается всплывающее сообщение о возможности предзамены")
    default void checkPrereplacementPopupNotDisplayed() {
        waitAction().shouldNotBeVisible(popupAlert);
    }
}
