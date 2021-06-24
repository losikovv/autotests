package ru.instamart.reforged.admin.page.pages;

import lombok.SneakyThrows;
import org.openqa.selenium.By;
import ru.instamart.kraken.testdata.pagesdata.StaticPageData;
import ru.instamart.reforged.admin.page.AdminPage;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.Input;

public final class New implements AdminPage {

    private final Input title = new Input(By.id("page_title"));
    private final Input slug = new Input(By.id("page_slug"));
    private final Input text = new Input(By.xpath("//html/body"));


    @SneakyThrows
    public void create(final StaticPageData data) {
        title.fill(data.getPageName());
        slug.fill(data.getText());
        Kraken.switchFrame(0);
        text.fill(data.getDescription());
        Thread.sleep(3000);
    }

    @Override
    public String pageUrl() {
        return "pages/new";
    }
}
