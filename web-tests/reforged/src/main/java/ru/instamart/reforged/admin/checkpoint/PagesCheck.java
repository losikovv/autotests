package ru.instamart.reforged.admin.checkpoint;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.component.Element;

import static ru.instamart.reforged.core.action.WaitAction.shouldBeVisible;

public interface PagesCheck extends Check {

    Element table = new Element(By.xpath("//table"));
    Element tableEntry = new Element(By.id("page_2"));
    Element newPageButton = new Element(By.xpath("//a[@class='button icon-plus']"));
    Element editPageButton = new Element(By.xpath("//tr[@id='page_2']/td/a[@data-action='edit']"));
    Element deletePageButton = new Element(By.xpath("//tr[@id='page_2']/td/a[@data-action='remove']"));

    @Step("Проверяем присутствие элемента Таблица на странице")
    default void checkTable() {
        shouldBeVisible(table).isDisplayed();
    }

    @Step("Проверяем присутствие элемента Внутри Таблицы на странице")
    default void checkTableEntry() {
        shouldBeVisible(tableEntry).isDisplayed();
    }


    @Step("Проверяем присутствие элемента Следующая страница на странице")
    default void checkTableNextPage() {
        shouldBeVisible(newPageButton).isDisplayed();
    }

    @Step("Проверяем присутствие элемента Редактирование на странице")
    default void checkTableEditAction() {
        shouldBeVisible(editPageButton).isDisplayed();
    }

    @Step("Проверяем присутствие элемента Удаление на странице")
    default void checkTableDeleteAction() {
        shouldBeVisible(deletePageButton).isDisplayed();
    }
}
