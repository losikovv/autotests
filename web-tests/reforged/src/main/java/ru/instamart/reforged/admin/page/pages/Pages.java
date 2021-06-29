package ru.instamart.reforged.admin.page.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.admin.checkpoint.PagesCheck;
import ru.instamart.reforged.admin.page.AdminPage;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;
import ru.instamart.reforged.core.component.Table;

public final class Pages implements AdminPage, PagesCheck {

    private final Link newPageButton = new Link(By.xpath("//a[@class='button icon-plus']"));
    private final Table table = new Table();

    @Step("Перейти к созданию новой страницы")
    public void clickToNewPage() {
        newPageButton.click();
    }

    @Step("Перейти к редактированию страницы {0}")
    public void editEntry(final String name) {
        table.getLine(name).findElement(By.xpath(".//ancestor::td[@class='actions']/a[@data-action='edit']")).click();
    }

    @Step("Удалить запись {0} из таблицы")
    public void deleteEntry(final String name) {
        table.getLine(name).findElement(By.xpath(".//ancestor::td[@class='actions']/a[@data-action='remove']")).click();
        confirmBrowserAlert();
    }

    @Override
    public String pageUrl() {
        return "pages";
    }
}
