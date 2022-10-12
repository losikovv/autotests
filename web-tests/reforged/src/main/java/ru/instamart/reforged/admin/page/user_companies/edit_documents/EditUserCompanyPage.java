package ru.instamart.reforged.admin.page.user_companies.edit_documents;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public final class EditUserCompanyPage implements AdminPage, EditUserCompanyCheck {

    @Step("Нажимаем кнопку 'Назад к списку компаний'")
    public void clickBackToCompaniesList() {
        backToCompaniesList.click();
    }

    @Step("Очищаем поле ввода 'ИНН'")
    public void clearInn() {
        inn.clearByKeysCombination();
    }

    @Step("Вводим в поле 'ИНН': '{text}'")
    public void fillInn(final String text) {
        inn.fill(text);
    }

    @Step("Очищаем поле ввода 'Юридическое лицо покупателя'")
    public void clearJuridicalName() {
        juridicalName.clearByKeysCombination();
    }

    @Step("Вводим в поле 'Юридическое лицо покупателя': '{text}'")
    public void fillJuridicalName(final String text) {
        juridicalName.fill(text);
    }

    @Step("Вводим в поле 'Юридический адрес покупателя': '{text}'")
    public void fillJuridicalAddress(final String text) {
        juridicalAddress.fill(text);
    }

    @Step("Вводим в поле 'КПП покупателя': '{text}'")
    public void fillKpp(final String text) {
        kpp.fill(text);
    }

    @Step("Вводим в поле 'Юридическое лицо грузополучателя': '{text}'")
    public void fillConsigneeJuridicalName(final String text) {
        consigneeName.fill(text);
    }

    @Step("Вводим в поле 'Юридический адрес грузополучателя': '{text}'")
    public void fillConsigneeJuridicalAddress(final String text) {
        consigneeAddress.fill(text);
    }

    @Step("Вводим в поле 'КПП грузополучателя': '{text}'")
    public void fillConsigneeKpp(final String text) {
        consigneeKpp.fill(text);
    }

    @Step("Вводим в поле 'БИК': '{text}'")
    public void fillBik(final String text) {
        bik.fill(text);
    }

    @Step("Вводим в поле 'Корреспонденсткий счёт': '{text}'")
    public void fillCorrespondentAccount(final String text) {
        correspondentAccount.fill(text);
    }

    @Step("Вводим в поле 'Расчётный счёт': '{text}'")
    public void fillOperationalAccount(final String text) {
        operationalAccount.fill(text);
    }

    @Step("Вводим в поле 'Банк': '{text}'")
    public void fillBank(final String text) {
        bank.fill(text);
    }

    @Step("Нажимаем кнопку 'Изменить'")
    public void clickSubmit() {
        submit.click();
    }

    @Step("Нажимаем кнопку 'Отменить'")
    public void clickCancel() {
        cancel.click();
    }

    public void goToPage(final int userId, final int companyId) {
        goToPage("users/" + userId + "/company_documents/" + companyId + "/edit");
    }

    @Override
    public void goToPage() {
        throw new RuntimeException("Для перехода на страницу редактирования реквизитов компании необходимо использовать метод с параметрами");
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
