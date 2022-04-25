package ru.instamart.reforged.admin.page.retailers.add_new_retailer;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public class RetailerAddPage implements AdminPage, RetailerAddCheck {

    @Step("Вводим в поле 'Наименование': {text}")
    public void fillName(final String text) {
        nameInput.fill(text);
    }

    @Step("Очищаем поле 'Наименование'")
    public void clearName() {
        nameInput.clearByKeysCombination();
    }

    @Step("Вводим в поле 'Короткое наименование': {text}")
    public void fillShortName(final String text) {
        shortNameInput.fill(text);
    }

    @Step("Вводим в поле 'Короткое описание': {text}")
    public void fillShortDescription(final String text) {
        descriptionInput.fill(text);
    }

    @Step("Вводим в поле 'Короткий URL': {text}")
    public void fillShortURL(final String text) {
        urlInput.fill(text);
    }

    @Step("Очищаем поле 'Короткий URL'")
    public void clearURL() {
        urlInput.clearByKeysCombination();
    }

    @Step("Вводим в поле 'Юридическое имя': {text}")
    public void fillJuridicalName(final String text) {
        juridicalNameInput.fill(text);
    }

    @Step("Вводим в поле 'ИНН': {text}")
    public void fillINN(final String text) {
        innInput.fill(text);
    }

    @Step("Вводим в поле 'Юридический адрес': {text}")
    public void fillJuridicalAddress(final String text) {
        addressInput.fill(text);
    }

    @Step("Вводим в поле 'Телефон для связи': {text}")
    public void fillPhone(final String text) {
        phoneInput.fill(text);
    }

    @Step("Вводим в поле 'Глубина вложения категорий на главной': {text}")
    public void fillCategoriesDepth(final String text) {
        categoriesDepthInput.fill(text);
    }

    @Step("Вводим в поле 'Ключ в файле импорта': {text}")
    public void fillImportKey(final String text) {
        importKeyInput.fill(text);
    }

    @Step("Очищаем поле 'Ключ в файле импорта'")
    public void clearImportKey() {
        importKeyInput.clearByKeysCombination();
    }

    @Step("Загружаем логотип '{filePath}'")
    public void uploadLogo(final String filePath) {
        logoUpload.upload(filePath);
    }

    @Step("Загружаем иконку '{filePath}'")
    public void uploadIcon(final String filePath) {
        iconUploader.upload(filePath);
    }

    @Step("Нажимаем 'Сохранить'")
    public void clickSubmit() {
        submit.hoverAndClick();
    }

    @Step("Нажимаем 'Отменить'")
    public void clickCancel() {
        cancel.hoverAndClick();
    }

    @Override
    public String pageUrl() {
        return "retailers/new";
    }
}
