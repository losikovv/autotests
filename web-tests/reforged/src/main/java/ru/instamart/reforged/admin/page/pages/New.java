package ru.instamart.reforged.admin.page.pages;

import io.qameta.allure.Step;
import io.qameta.allure.Story;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import ru.instamart.kraken.testdata.pagesdata.StaticPageData;
import ru.instamart.reforged.admin.page.AdminPage;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Checkbox;
import ru.instamart.reforged.core.component.Input;

public final class New implements AdminPage {

    private final Input title = new Input(By.id("page_title"), false);
    private final Input slug = new Input(By.id("page_slug"));
    private final Input body = new Input(By.xpath("//html/body"));
    private final Input metaTitle = new Input(By.id("page_meta_title"));
    private final Input metaKeywords = new Input(By.id("page_meta_keywords"));
    private final Input metaDescription = new Input(By.id("page_meta_description"));
    private final Input pageLayout = new Input(By.id("page_layout"));
    private final Input foreignLink = new Input(By.id("page_foreign_link"));
    private final Input pagePosition = new Input(By.id("page_position"));

    private final Checkbox sidebar = new Checkbox(By.id("page_show_in_sidebar"));
    private final Checkbox header = new Checkbox(By.id("page_show_in_header"));
    private final Checkbox footer = new Checkbox(By.id("page_show_in_footer"));
    private final Checkbox pageVisible = new Checkbox(By.id("page_visible"));
    private final Checkbox renderLayoutAsPartial = new Checkbox(By.id("page_render_layout_as_partial"));

    private final Button submit = new Button(By.xpath("//button[@type='submit']"));

    @Step("Заполнить страницу данными {0}")
    public void fillPageData(final StaticPageData data) {
        fillTitle(data.getPageName());
        fillSlug(data.getPageURL());
        fillBody(data.getDescription());
        fillMetaTitle(data.getText());
        fillMetaKeywords(data.getText());
        fillMetaDescription(data.getText());
        fillPageLayout(data.getText());
        fillForeignLink(data.getText());
        fillPagePosition(data.getPosition());
    }

    @Step("Заполнить поле title={0}")
    public void fillTitle(final String text) {
        title.fill(text);
    }

    @Step("Заполнить поле slug={0}")
    public void fillSlug(final String text) {
        slug.fill(text);
    }

    @Step("Заполнить поле body={0}")
    public void fillBody(final String text) {
        Kraken.switchFrame(0);
        body.fill(text);
        Kraken.switchToParentFrame();
    }

    @Step("Заполнить поле metaTitle={0}")
    public void fillMetaTitle(final String text) {
        metaTitle.fill(text);
    }

    @Step("Заполнить поле metaKeywords={0}")
    public void fillMetaKeywords(final String text) {
        metaKeywords.fill(text);
    }

    @Step("Заполнить поле metaDescription={0}")
    public void fillMetaDescription(final String text) {
        metaDescription.fill(text);
    }

    @Step("Заполнить поле pageLayout={0}")
    public void fillPageLayout(final String text) {
        pageLayout.fill(text);
    }

    @Step("Заполнить поле foreignLink={0}")
    public void fillForeignLink(final String text) {
        foreignLink.fill(text);
    }

    @Step("Заполнить поле pagePosition={0}")
    public void fillPagePosition(final String text) {
        pagePosition.fill(text);
    }

    @Step("Сохранить изменения")
    public void submit() {
        submit.click();
    }

    @Override
    public String pageUrl() {
        return "pages/new";
    }
}
