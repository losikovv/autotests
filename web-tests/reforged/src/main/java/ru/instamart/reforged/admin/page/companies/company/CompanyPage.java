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

    @Step("Нажимаем на кнопку 'Удалить представителя'")
    public void clickDeleteEmployee() {
        companyEmployeeDeleteButtons.clickOnFirst();
    }

    @Step("Нажимаем на кнопку 'Ок' в окне подтверждения действия")
    public void clickConfirmStatusModalOk() {
        confirmActionModalOk.click();
    }

    @Step("Нажимаем на кнопку 'Отмена' в окне подтверждения действия")
    public void clickConfirmStatusModalCancel() {
        confirmActionModalCancel.click();
    }

    @Override
    public String pageUrl() {
        return "companies";
    }
}
