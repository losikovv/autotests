package ru.instamart.tests.administration;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import instamart.ui.objectsmap.Elements;
import instamart.ui.modules.Administration;
import ru.instamart.tests.TestBase;

import static instamart.core.settings.Config.TestsConfiguration.AdministrationTests.enablePagesSectionTests;

public class AdministrationPagesSectionTests extends TestBase {

    // TODO актуализировать тесты в классе - ATST-233

    @BeforeMethod(alwaysRun = true)
    public void reachAdministration() {
        kraken.reach().admin();
    }

    @Test(  enabled = enablePagesSectionTests,
            description = "Проверка вкладки статических страниц",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 10801
    )
    public void ValidatePagesRootPage() {
        // TODO: вынести тестовую страницу в глобальные константы - ATST-233
        String page = "О компании";
        kraken.get().adminPage("pages");

        assertPresence(Elements.Administration.PagesSection.table());
        assertPresence(Elements.Administration.PagesSection.newPageButton());
        assertPresence(Elements.Administration.PagesSection.editPageButton(page));
        assertPresence(Elements.Administration.PagesSection.deletePageButton(page));
    }

    @Test(  enabled = enablePagesSectionTests,
            description = "Проверка страницы создания статической страницы",
            groups = {},
            priority = 10802
    )
    public void ValidatePagesCreationPage() {
        String testPageName = "О компании";
        Administration.Pages.validateStaticPage(testPageName);
    }

    @Test(  enabled = enablePagesSectionTests,
            description = "Тест создания и удаления статической страницы",
            groups = {},
            priority = 10803
    )
    public void CreateDeletePage() {
        String pageName = "AAA";
        String pageURL = "testAAA";
        String desc = "я маленькая тестовая страничка, которую должен видеть только селен";

        kraken.get().adminPage("pages");

        Administration.Pages.create(pageName, pageURL, desc);
            assertPresence(Elements.Administration.PagesSection.tableEntry(pageName));

        Administration.Pages.validateStaticPage(pageName, pageURL);
        kraken.detect().isElementPresent(Elements.StaticPages.pageTitle());

        Administration.Pages.editStaticPage(pageName);

        Administration.Pages.delete(pageName);
            // TODO проверить отсутствие страницы в списке в админке - ATST-233
            // TODO добавить проверку что тестовая страничка - 404 на сайте - ATST-233
    }
}