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
        tableComponent.clickToEdit(name);
    }

    @Step("Удалить запись {0} из таблицы")
    public void deleteEntry(final Long id) {
        tableComponent.clickToRemoveById(id);
        confirmBrowserAlert();
    }

    @Step("Вернуть pageId")
    public Long getPageId(StaticPageData data) {
        return tableComponent.getPageId(data.getPageName());
    }

    @Override
    public String pageUrl() {
        return "pages";
    }
}
