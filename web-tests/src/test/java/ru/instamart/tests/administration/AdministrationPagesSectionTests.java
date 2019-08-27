package ru.instamart.tests.administration;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.application.lib.Elements;
import ru.instamart.application.platform.modules.Administration;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.Config.TestsConfiguration.AdministrationTests.enablePagesSectionTests;

public class AdministrationPagesSectionTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void reachAdministration() {
        kraken.reach().admin();
    }

    @Test(  enabled = enablePagesSectionTests,
            description = "Проверка вкладки статических страниц",
            groups = {"acceptance","regression"},
            priority = 10801
    )
    public void ValidatePagesRootPage() {
        // TODO: вынести тестовую страницу в глобальные константы
        String page = "О компании";
        kraken.get().adminPage("pages");

        assertElementPresence(Elements.Administration.PagesSection.table());
        assertElementPresence(Elements.Administration.PagesSection.newPageButton());
        assertElementPresence(Elements.Administration.PagesSection.editPageButton(page));
        assertElementPresence(Elements.Administration.PagesSection.deletePageButton(page));
    }

    @Test(  enabled = enablePagesSectionTests,
            description = "Проверка страницы создания статической страницы",
            groups = {"acceptance","regression"},
            priority = 10802
    )
    public void ValidatePagesCreationPage() {
        String testPageName = "О компании";
        Administration.Pages.validateStaticPage(testPageName);
    }

    @Test(  enabled = enablePagesSectionTests,
            description = "Тест создания и удаления статической страницы",
            groups = {"acceptance","regression"},
            priority = 10803
    )
    public void CreateDeletePage() {
        String pageName = "AAA";
        String pageURL = "testAAA";
        String desc = "я маленькая тестовая страничка, которую должен видеть только селен";

        kraken.get().adminPage("pages");

        Administration.Pages.create(pageName, pageURL, desc);
            assertElementPresence(Elements.Administration.PagesSection.tableEntry(pageName));

        Administration.Pages.validateStaticPage(pageName, pageURL);
        kraken.detect().isElementPresent(Elements.StaticPages.pageTitle());

        Administration.Pages.editStaticPage(pageName);

        Administration.Pages.delete(pageName);
            // TODO проверить отсутствие страницы в списке в админке
            // TODO добавить проверку что тестовая страничка - 404 на сайте
    }
}