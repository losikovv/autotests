package ru.instamart.reforged.admin.page.user_companies;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public final class UserCompaniesPage implements AdminPage, UserCompaniesCheck {

    @Step("Нажимаем кнопку '+ Новая компания'")
    public void clickAddNewCompany() {
        addNewCompany.click();
    }

    @Step("Нажимаем кнопку 'Назад'")
    public void clickGoBack() {
        goBack.click();
    }

    @Step("Нажимаем кнопку 'Редактировать' в '{lineNumber}'-й строке")
    public void clickEdit(final int lineNumber) {
        table.clickEdit(lineNumber - 1);
    }

    public void goToPage(final int userId) {
        goToPage("users/" + userId + "/company_documents");
    }

    @Override
    public void goToPage() {
        throw new RuntimeException("Для перехода на страницу 'Реквизиты компаний' пользователя необходимо использовать метод с параметрами");
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
