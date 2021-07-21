package ru.instamart.reforged.admin.page.pages.new_page;

import io.qameta.allure.Step;
import ru.instamart.kraken.testdata.pagesdata.StaticPageData;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.core.Kraken;

public final class NewPage implements AdminPage, NewPageElement {

    @Step("Заполнить страницу данными {0}")
    public void fillPageData(final StaticPageData data) {
        fillTitle(data.getPageName());
        fillSlug(data.getPageURL());
        fillBody(data.getDescription());
        fillMetaTitle(data.getText());
        fillMetaKeywords(data.getText());
        fillMetaDescription(data.getText());
        fillForeignLink(data.getPageURL());
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
