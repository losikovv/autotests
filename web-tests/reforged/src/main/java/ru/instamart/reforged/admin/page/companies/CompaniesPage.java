package ru.instamart.reforged.admin.page.companies;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import ru.instamart.reforged.admin.AdminPage;

public final class CompaniesPage implements AdminPage, CompaniesCheck {

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

    @Step("Перейти в редактирование компании '{0}'")
    public void goToCompanyEdit(final String companyName) {
        companyTable.clickOnColumnElement(companyName);
    }

    @Step("Изменить имя компании на '{0}'")
    public void changeCompanyName(final String companyName) {
        buttonEditCompanyName.click();
        editCompanyName.fillField(companyName);
        editCompanyName.getActions().sendKeys(Keys.ENTER);
    }

    @Override
    public String pageUrl() {
        return "companies";
    }
}
