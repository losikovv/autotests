package ru.instamart.reforged.admin.page.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.kraken.data.StaticPageData;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.admin.AdminPage;

public final class Pages implements AdminPage, PagesCheck {

    @Step("Перейти к созданию новой страницы")
    public void clickToNewPage() {
        newPageButton.click();
    }

    @Step("Перейти к редактированию страницы {0}")
    public void editEntry(final String name) {
        table.getLine(name).findElement(By.xpath(".//ancestor::td[@class='actions']/a[@data-action='edit']")).click();
    }

    @Step("Удалить запись {0} из таблицы")
    public void deleteEntry(final Long id) {
        tableEntrySpecificDelete.click(id);
        confirmBrowserAlert();
    }

    @Step("Вернуть pageId")
    public Long returnPageId(StaticPageData data) {
        return StringUtil.stringToLong(table.getLine(data.getPageName()).getAttribute("id"));
    }

    @Override
    public String pageUrl() {
        return "pages";
    }
}
