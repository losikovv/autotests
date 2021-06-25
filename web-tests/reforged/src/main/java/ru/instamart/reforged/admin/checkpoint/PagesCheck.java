package ru.instamart.reforged.admin.checkpoint;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;

import static ru.instamart.reforged.core.action.WaitAction.shouldBeVisible;

public interface PagesCheck extends Check {

    Element table = new Element(By.xpath("//table"));
    Element tableEntry = new Element(By.id("page_2"));

    @Step("Проверяем присутствие элемента Таблица на странице")
    default void checkTable() {
        shouldBeVisible(table).isDisplayed();
    }

    @Step("Проверяем присутствие элемента Внутри Таблицы на странице")
    default void checkTableEntry() {
        shouldBeVisible(tableEntry).isDisplayed();
    }
}
