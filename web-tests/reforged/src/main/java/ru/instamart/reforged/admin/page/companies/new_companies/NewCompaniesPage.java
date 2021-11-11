package ru.instamart.reforged.admin.page.companies.new_companies;

import io.qameta.allure.Step;
import ru.instamart.kraken.data.Company;
import ru.instamart.reforged.admin.AdminPage;

public final class NewCompaniesPage implements AdminPage, NewCompaniesCheck {

    @Step("Заполнить форму данными {0}")
    public void fillCompany(final Company company) {
        fillInn(company.getInn());
        fillCompanyName(company.getCompanyName());
        fillOwnerEmail(company.getOwnerEmail());
    }

    @Step("Заполнить поле ИНН={0}")
    public void fillInn(final String data) {
        inn.fill(data);
    }

    @Step("Заполнить поле имя компании={0}")
    public void fillCompanyName(final String data) {
        companyName.fill(data);
    }

    @Step("Заполнить поле почта владелька компании={0}")
    public void fillOwnerEmail(final String data) {
        ownerEmail.fill(data);
    }

    @Step("Подтвердить создание компании")
    public void clickToSubmit() {
        submit.click();
    }

    @Override
    public String pageUrl() {
        return "companies/new";
    }
}
