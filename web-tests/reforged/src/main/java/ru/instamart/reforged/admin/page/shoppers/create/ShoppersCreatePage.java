package ru.instamart.reforged.admin.page.shoppers.create;

import io.qameta.allure.Step;
import ru.instamart.kraken.data.enums.ShoppersRole;
import ru.instamart.reforged.admin.AdminPage;

import java.util.Set;

public final class ShoppersCreatePage implements AdminPage, ShoppersCreateCheck {

    @Step("Заполнить поле 'Имя и фамилия' данными {0}")
    public void setName(final String name) {
        nameInput.fill(name);
    }

    @Step("Заполнить поле 'Телефон' данными {0}")
    public void setPhone(final String phone) {
        phoneInput.fill(phone);
    }

    @Step("Заполнить поле 'Логин' данными {0}")
    public void setLogin(final String login) {
        loginInput.fill(login);
    }

    @Step("Заполнить поле 'Текущий магазин' данными {0}")
    public void setCurrentShop(final String currentShop) {
        currentShopSelector.fillAndSelect(currentShop);
    }

    @Step("Заполнить поле 'Роли сотрудника' данными {0}")
    public void setShoppersRole(final String shoppersRole) {
        shoppersRoleSelector.select(shoppersRole);
    }

    @Step("Заполнить поле 'Роли сотрудника' данными {0}")
    public void setShoppersRoles(final Set<ShoppersRole> roles) {
        shoppersRoleSelector.select(roles.stream().map(ShoppersRole::getData).toArray(String[]::new));
    }

    @Step("Заполнить поле 'Пароль' данными {0}")
    public void setPassword(final String password) {
        passwordInput.fill(password);
    }

    @Step("Заполнить поле 'ИНН' данными {0}")
    public void setInn(final String inn) {
        innInput.fill(inn);
    }

    @Step("Нажать на кнопку 'Сохранить'")
    public void save() {
        saveButton.click();
    }

    @Override
    public String pageUrl() {
        return "shoppers/create";
    }
}
