package ru.instamart.reforged.admin.page.settings.all_cities.city_add;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;


public class CityAddPage implements AdminPage, CityAddCheck {

    @Override
    public String pageUrl() {
        return "cities/new";
    }

    @Step("Ввести имя города")
    public void inputCityName(String cityName) {
        cityNameInput.fill(cityName);
    }

    @Step("Нажать создать новый город")
    public void createNewCityButton() {
        createButton.click();
    }
}
