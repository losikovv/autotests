package ru.instamart.reforged.admin.page.settings.all_cities;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.core.Kraken;

public class AllCitiesPage implements AdminPage, AllCitiesCheck {

    @Step("Удалить тестовый город")
    public void deleteTestCity(String cityName) {
        Kraken.getWebDriver().findElement(By.xpath("//td[text() = '" + cityName + "']/parent::tr/descendant::a[@data-action='remove']")).click();
    }

    @Override
    public String pageUrl() {
        return "cities";
    }
}
