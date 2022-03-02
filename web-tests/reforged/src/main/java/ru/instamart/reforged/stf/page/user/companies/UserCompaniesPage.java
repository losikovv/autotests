package ru.instamart.reforged.stf.page.user.companies;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.frame.AddCompanyModal;
import ru.instamart.reforged.stf.page.StfPage;

public final class UserCompaniesPage implements StfPage, UserCompaniesCheck {

    public AddCompanyModal interactAddCompany() {
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
