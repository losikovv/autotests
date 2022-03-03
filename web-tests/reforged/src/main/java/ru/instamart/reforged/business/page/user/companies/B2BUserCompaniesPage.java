package ru.instamart.reforged.business.page.user.companies;

import io.qameta.allure.Step;
import ru.instamart.reforged.business.frame.B2BAddCompanyModal;
import ru.instamart.reforged.business.page.BusinessPage;

public final class B2BUserCompaniesPage implements BusinessPage, B2BUserCompaniesCheck {

    public B2BAddCompanyModal interactAddCompany() {
        return addCompanyModal;
    }

    @Step("Нажимаем кнопку 'Добавить компанию'")
    public void clickAddCompany() {
        addCompany.click();
    }

    @Step("Нажимаем кнопку 'Личный профиль'")
    public void clickProfile() {
        profileEdit.click();
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
