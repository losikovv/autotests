package ru.instamart.tests.administration;

import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.modules.Administration;
import instamart.ui.objectsmap.Elements;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

public class AdministrationPagesSectionTests extends TestBase {
    
    private final BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    // TODO актуализировать тесты в классе - ATST-233

    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.reach().admin();
    }

    @Test(  description = "Проверка вкладки статических страниц",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 10801
    )
    public void ValidatePagesRootPage() {
        // TODO: вынести тестовую страницу в глобальные константы - ATST-233
        String page = "О компании";
        kraken.get().adminPage("pages");

        baseChecks.checkIsElementPresent(Elements.Administration.PagesSection.table());
        baseChecks.checkIsElementPresent(Elements.Administration.PagesSection.newPageButton());
        baseChecks.checkIsElementPresent(Elements.Administration.PagesSection.editPageButton(page));
        baseChecks.checkIsElementPresent(Elements.Administration.PagesSection.deletePageButton(page));
    }

    @Test(  description = "Проверка страницы создания статической страницы",
            groups = {},
            priority = 10802
    )
    public void ValidatePagesCreationPage() {
        String testPageName = "О компании";
        Administration.Pages.validateStaticPage(testPageName);
    }

    @Test(  description = "Тест создания и удаления статической страницы",
            groups = {},
            priority = 10803
    )
    public void CreateDeletePage() {
        String pageName = "AAA";
        String pageURL = "testAAA";
        String desc = "я маленькая тестовая страничка, которую должен видеть только селен";

        kraken.get().adminPage("pages");

        Administration.Pages.create(pageName, pageURL, desc);
            baseChecks.checkIsElementPresent(Elements.Administration.PagesSection.tableEntry(pageName));

        Administration.Pages.validateStaticPage(pageName, pageURL);
        kraken.detect().isElementPresent(Elements.StaticPages.pageTitle());

        Administration.Pages.editStaticPage(pageName);

        Administration.Pages.delete(pageName);
            // TODO проверить отсутствие страницы в списке в админке - ATST-233
            // TODO добавить проверку что тестовая страничка - 404 на сайте - ATST-233
    }
}