package ru.instamart.reforged.business.page.user.companies;

import io.qameta.allure.Step;
import ru.instamart.reforged.business.frame.AddCompanyModal;
import ru.instamart.reforged.business.page.BusinessPage;

public final class UserCompaniesPage implements BusinessPage, UserCompaniesCheck {

    public AddCompanyModal interactAddCompany() {
        return addCompanyModal;
    }

    @Step("Нажимаем кнопку 'Добавить компанию'")
    public void clickAddCompany() {
        addCompany.click();
    }

    @Step("Нажимаем на название первой компании в списке")
    public void clickOnFirstCompanyName() {
        companiesList.clickOnFirst();
    }

    @Override
    public String pageUrl() {
        return "/user/companies";
    }
}
