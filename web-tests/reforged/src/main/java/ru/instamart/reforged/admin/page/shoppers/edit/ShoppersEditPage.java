package ru.instamart.reforged.admin.page.shoppers.edit;

import io.qameta.allure.Step;
import ru.instamart.kraken.data.enums.ShoppersRole;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.admin.block.flash_alert.FlashAlert;
import ru.instamart.reforged.admin.page.shoppers.edit.car_type_modal.CarTypeModal;
import ru.instamart.reforged.admin.popover.Popover;

import java.util.Set;

public final class ShoppersEditPage implements AdminPage, ShoppersEditCheck {

    public FlashAlert interactAlert() {
        return alert;
    }

    public CarTypeModal interactCarTypeModal() {
        return carTypeModal;
    }

    public Popover interactPopover() {
        return popover;
    }

    @Step("Заполнить поле 'Имя и фамилия' данными {0}")
    public void setName(final String name) {
        nameInput.fillField(name);
    }

    @Step("Заполнить поле 'Телефон' данными {0}")
    public void setPhone(final String phone) {
        phoneInput.fillField(phone);
    }

    @Step("Заполнить поле 'Логин' данными {0}")
    public void setLogin(final String login) {
        loginInput.fillField(login);
    }

    @Step("Нажать на кнопку 'Сохранить'")
    public void save() {
        saveButton.click();
    }

    @Step("Заполнить поле 'Текущий магазин' данными {0}")
    public void setCurrentShop(final String currentShop) {
        currentShopSelector.fillAndSelect(currentShop);
    }

    @Step("Очистить поле 'Текущий магазин'")
    public void clearCurrentShop() {
        currentShopSelector.removeAll();
    }

    @Step("Заполнить поле 'Роли сотрудника' данными {0}")
    public void setShoppersRole(final String shoppersRole) {
        shoppersRoleSelector.select(shoppersRole);
    }

    @Step("Очистить поле 'Роли сотрудника'")
    public void clearShoppersRole() {
        shoppersRoleSelector.removeAll();
    }

    @Step("Заполнить поле 'Роли сотрудника' данными {0}")
    public void setShoppersRoles(final Set<ShoppersRole> roles) {
        shoppersRoleSelector.select(roles.stream().map(ShoppersRole::getData).toArray(String[]::new));
    }

    @Step("Нажать на кнопку 'Добавить транспортное средство'")
    public void clickToAddVehicle() {
        addVehicleButton.click();
    }

    @Step("Нажать на первое Сердечко добавления в избранное")
    public void clickOnFirstUnselectedHeart() {
        unselectedHearts.clickOnFirst();
    }

    @Step("Нажать на кнопку удалить у первого ТС")
    public void clickToDeleteOnFirstVehicles() {
        deleteButton.clickOnFirst();
    }

    @Step("Указать 'Модель' нового транспортного средства '{0}'")
    public void fillNewModel(final String model) {
        newVehiclesModel.fill(model);
    }

    @Step("Указать 'Номер' нового транспортного средства '{0}'")
    public void fillNewNumber(final String number) {
        newVehiclesNumber.fill(number);
    }

    @Step("Указать 'Грузоподъемность' нового транспортного средства '{0}'")
    public void fillNewVolume(final String volume) {
        newVehiclesVolume.fill(volume);
    }

    @Step("Указать 'Модель' редактируемого транспортного средства '{0}'")
    public void fillEditableModel(final String model) {
        editableVehiclesModel.fill(model);
    }

    @Step("Указать 'Номер' редактируемого транспортного средства '{0}'")
    public void fillEditableNumber(final String number) {
        editableVehiclesNumber.fill(number);
    }

    @Step("Указать 'Грузоподъемность' редактируемого транспортного средства '{0}'")
    public void fillEditableVolume(final String volume) {
        editableVehiclesVolume.fill(volume);
    }

    @Step("Перейти на таб '{0}'")
    public void clickToTab(final String tabName) {
        tab.click(tabName);
    }

    @Override
    public String pageUrl() {
        return "shoppers";
    }

    public void goToPage(final int id) {
        goToPage(pageUrl() + "/" + id + "/edit");
    }
}
