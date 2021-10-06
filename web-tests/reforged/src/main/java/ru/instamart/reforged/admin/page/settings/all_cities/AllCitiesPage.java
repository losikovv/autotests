package ru.instamart.reforged.admin.page.settings.all_cities;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.core.Kraken;

public class AllCitiesPage implements AdminPage, AllCitiesCheck {

    @Step("Удалить тестовый город: {0}")
    public void deleteTestCity(final String cityName) {
        removeCity.click(cityName);
    }

    @Override
    public String pageUrl() {
        return "cities";
    }
}
