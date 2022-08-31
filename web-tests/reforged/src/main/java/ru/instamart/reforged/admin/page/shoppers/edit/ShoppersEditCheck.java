package ru.instamart.reforged.admin.page.shoppers.edit;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Check;

public interface ShoppersEditCheck extends Check, ShoppersEditElement {

    @Step("Проверить, что данные в таблице таблицы '{0}' соответствуют ожидаемым '{1}'")
    default void checkInformationFor(final String tableName, final String data) {
        krakenAssert.assertTrue(information.is().containText(data, tableName), "Данные в таблице '" + tableName + "' отличаются");
    }

    @Step("Проверить, что поле 'Имя и фамилия' содержит текст '{0}'")
    default void checkName(final String text) {
        krakenAssert.assertTrue(nameInput.is().containTextFromAttribute("value", text), "Данные в поле 'Имя и фамилия' отличаются");
    }

    @Step("Проверить, что поле 'Логин' содержит текст '{0}'")
    default void checkLogin(final String text) {
        krakenAssert.assertTrue(loginInput.is().containTextFromAttribute("value", text), "Данные в поле 'Логин' отличаются");
    }

    @Step("Проверить, что поле 'Телефон' содержит текст '{0}'")
    default void checkPhone(final String text) {
        krakenAssert.assertTrue(phoneInput.is().containTextFromAttribute("value", text), "Данные в поле 'Телефон' отличаются");
    }

    @Step("Проверить, что поле 'Текущий магазин' содержит текст '{0}'")
    default void checkCurrentShop(final String text) {
        krakenAssert.assertTrue(currentShop.is().containText(text), "Магазин отличается");
    }

    @Step("Проверить, что поле 'Роли сотрудника' содержит текст '{0}'")
    default void checkRoles(final String roles) {
        var r = rolesCollection.getTextFromAllElements();
        krakenAssert.assertTrue(StringUtil.arrayToString(r).contains(roles), "Роли отличаются");
    }

    @Step("Проверить, что поле 'ИНН' содержит текст '{0}'")
    default void checkInn(final String text) {
        krakenAssert.assertTrue(innInput.is().containTextFromAttribute("value", text), "ИНН отличается");
    }

    @Step("Картинка при пустом транспорте")
    default void checkEmptyCar() {
        emptyCar.should().visible();
    }

    @Step("Кнопка 'Добавить оборудование' отображается")
    default void checkAddEquipmentButtonVisible() {
        addEquipmentButton.should().visible();
    }

    @Step("Кнопка 'Добавить униформу' отображается")
    default void checkAddUniformButtonVisible() {
        addUniformButton.should().visible();
    }

    @Step("Кнопка 'Сохранить' неактивна")
    default void checkButtonSaveInactive() {
        saveButton.should().notClickable();
    }
}
