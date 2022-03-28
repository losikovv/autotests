package ru.instamart.reforged.admin.page.settings.all_cities;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.admin.block.add_city.AddCity;
import ru.instamart.reforged.admin.block.edit_city.EditCity;

public class AllCitiesPage implements AdminPage, AllCitiesCheck {

    public AddCity interactAddCityModal() {
        return interactAddCityModal;
    }

    public EditCity interactEditCityModal() {
        return interactEditCityModal;
    }

    @Step("Удалить тестовый город: {0}")
    public void deleteTestCity(final String cityName) {
        removeCity.click(cityName);
    }

    @Step("Нажать на 'Добавить город'")
    public void clickOnAddCityButton() {
        addCityButton.click();
    }

    @Step("Нажать на 'Настройка' у города {city}")
    public void clickOnEditCityButton(final String city) {
        editCityButton.click(city);
    }

    @Override
    public String pageUrl() {
        return "cities";
    }
}
