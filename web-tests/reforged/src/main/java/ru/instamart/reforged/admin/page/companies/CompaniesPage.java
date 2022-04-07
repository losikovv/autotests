package ru.instamart.reforged.admin.page.companies;

import io.qameta.allure.Step;
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

    @Step("Перейти в редактирование найденной компании")
    public void goToFirstCompanyEdit() {
        companies.clickCompanyName(0);
    }

    @Override
    public String pageUrl() {
        return "companies";
    }
}
