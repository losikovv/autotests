package ru.instamart.reforged.admin.page.settings.store_groups.new_group;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public class EditStoreGroupPage implements AdminPage, EditStoreGroupCheck {

    @Step("Нажимаем 'Назад'")
    public void clickBack() {
        goBack.click();
    }

    @Step("Вводим в поле 'Название': '{text}'")
    public void fillTitle(final String text) {
        title.clearByKeysCombination();
        title.fill(text);
    }

    @Step("Вводим в поле 'Слоган': '{text}'")
    public void fillSubtitle(final String text) {
        subtitle.fill(text);
    }

    @Step("Вводим в поле 'Код': '{text}'")
    public void fillCode(final String text) {
        code.fill(text);
    }

    @Step("Вводим в поле 'Иконка': '{text}'")
    public void fillIcon(final String text) {
        icon.fill(text);
    }

    @Step("Вводим в поле 'Позиция': '{text}'")
    public void fillPosition(final String text) {
        position.fill(text);
    }

    @Step("Вводим в поле 'Уровень': '{text}'")
    public void fillLevel(final String text) {
        level.fill(text);
    }

    @Step("Нажимаем 'Отменить'")
    public void clickCancel() {
        cancel.click();
    }

    @Step("Нажимаем 'Сохранить'")
    public void clickSubmit() {
        submit.click();
    }

    @Override
    public String pageUrl() {
        return "store_labels/new";
    }
}
