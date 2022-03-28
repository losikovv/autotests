package ru.instamart.reforged.admin.block.add_city;

import io.qameta.allure.Step;

public class AddCity implements AddCityCheck{

    @Step("Ввести имя города: {0}")
    public void inputCityName(String cityName) {
        cityNameInput.fill(cityName);
    }

    @Step("Нажать создать новый город")
    public void createNewCityButton() {
        createButton.click();
    }
}
