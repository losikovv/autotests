package ru.instamart.reforged.admin.page.pages;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.checkpoint.PagesCheck;
import ru.instamart.reforged.admin.page.AdminPage;
import ru.instamart.reforged.core.component.Table;

public final class Pages implements AdminPage, PagesCheck {

    private final Table table = new Table();

    public void deleteEntry(final String name) {
        table.getLine(name).findElement(By.xpath(".//ancestor::td[@class='actions']/a[@data-action='remove']")).click();
        confirmBrowserAlert();
    }

    @Override
    public String pageUrl() {
        return "pages";
    }
}
