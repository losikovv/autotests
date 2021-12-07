package ru.instamart.reforged.stf.page.user.companies;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.frame.checkout.subsections.create_company.AddCompany;
import ru.instamart.reforged.stf.page.StfPage;

import static ru.instamart.reforged.stf.page.user.companies.UserCompaniesElement.*;

public final class UserCompaniesPage implements StfPage {

    @Step("Кликнуть Добавить компанию")
    public void clickAddCompany() {
        buttonAddCompany.click();
    }

    public AddCompany interactAddCompanyModal() {
        return addCompanyModal;
    }


    @Override
    public String pageUrl() {
        return "/user/companies";
    }
}
