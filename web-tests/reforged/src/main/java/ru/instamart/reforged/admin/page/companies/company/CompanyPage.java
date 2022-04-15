package ru.instamart.reforged.admin.page.companies.company;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import ru.instamart.reforged.admin.AdminPage;

public final class CompanyPage implements AdminPage, CompanyCheck {

    @Step("Ввести ИНН={0} в поле поиска")
    public void fillInn(final String inn) {
        search.fillField(inn);
    }

    @Step("Нажать на кнопку поиска")
    public void clickToSearch() {
        buttonSearch.click();
    }

    @Step("Нажать на кнопку 'Добавить компанию'")
    public void clickToAddCompany() {
        addCompany.click();
    }


    @Step("Изменить имя компании на '{0}'")
    public void changeCompanyName(final String companyName) {
        buttonEditCompanyName.click();
        editCompanyName.fillField(companyName);
        editCompanyName.getActions().sendKeys(Keys.ENTER);
    }

    @Step("Нажимаем на кнопку 'Подтвердить статус'")
    public void clickConfirmStatus() {
        confirmStatus.click();
    }

    @Step("Нажимаем на кнопку 'Подтвердить представителя'")
    public void clickConfirmEmployee() {
        companyEmployeeConfirmButtons.clickOnFirst();
    }

    @Step("Вводим email менеджера")
    public void fillManagerEmail(final String email) {
        fillEmailToAddManager.fill(email);
    }

    @Step("Выбираем первого найденного пользователя в выпадающем списке")
    public void selectFirstOfFoundedUsers() {
        foundedUsers.clickOnFirst();
    }

    @Step("Нажимаем на кнопку 'Удалить менеджера'")
    public void clickDeleteManager() {
        companyManagerDeleteButtons.clickOnFirst();
    }

    @Step("Нажимаем на кнопку 'Удалить представителя'")
    public void clickDeleteEmployee() {
        companyEmployeeDeleteButtons.clickOnFirst();
    }

    @Step("Нажимаем на кнопку 'Ок' в окне подтверждения действия")
    public void clickConfirmStatusModalOk() {
        confirmActionModalOk.click();
    }

    @Step("Нажимаем на кнопку 'Да' в окне подтверждения действия")
    public void clickConfirmStatusModalYes() {
        confirmActionModalYes.click();
    }

    @Step("Нажимаем на кнопку 'Отмена' в окне подтверждения действия")
    public void clickConfirmStatusModalCancel() {
        confirmActionModalCancel.click();
    }

    @Step("Нажимаем кнопку 'Добавить договор'")
    public void clickAddContract() {
        addContract.click();
    }

    @Step("Нажимаем кнопку 'Сохранить договор'")
    public void clickSaveContract() {
        saveContract.click();
    }

    @Step("Нажимаем кнопку 'Редактировать договор'")
    public void clickEditContract() {
        editContract.click();
    }

    @Step("Нажимаем кнопку 'Архивировать договор'")
    public void clickArchiveContract() {
        deleteContract.click();
    }

    @Step("Нажимаем кнопку 'Отменить' (добавление/редактирование договора)")
    public void clickAddEditCancel() {
        cancelEdit.click();
    }

    @Step("Вводим номер договора: {contractNumber}")
    public void fillContractNumber(final String contractNumber) {
        contractNumberInput.fillField(contractNumber);
    }

    @Step("Кликает в поле ввода даты договора")
    public void clickDateInput() {
        contractDateInput.click();
    }

    @Step("Вводим дату договора: {contractDate}")
    public void fillContractDate(final String contractDate) {
        contractDateInput.fillField(contractDate);
    }

    @Step("Нажимаем кнопку 'Сегодня' в календаре")
    public void clickToday() {
        calendarToday.hoverAndClick();
    }

    @Step("Нажимаем на кнопку 'Обновить баланс'")
    public void clickRefreshBalance() {
        balanceRefresh.click();
    }

    @Step("Нажимаем на кнопку 'Обновить код безопасности'")
    public void clickRefreshSecurityCode() {
        refreshSecurityCode.click();
    }


    @Override
    public String pageUrl() {
        return "companies";
    }
}
