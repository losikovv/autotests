package ru.instamart.autotests.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.appmanager.AdministrationHelper;

import static ru.instamart.autotests.application.Config.testAdministration;

public class AdministrationPages extends TestBase{

    @BeforeMethod(alwaysRun = true)
    public void reachAdministration() {
        kraken.reach().admin();
    }

    @Test(  enabled = testAdministration,
            description = "Проверка вкладки статических страниц",
            groups = {"acceptance","regression"},
            priority = 2801
    )
    public void ValidatePagesRootPage() {
        // TODO: вынести тестовую страницу в глобальные константы
        String testPageName = "О компании";
        AdministrationHelper.Pages.validatePagesPage(testPageName);
    }

    @Test(  enabled = testAdministration,
            description = "Проверка страницы создания статической страницы",
            groups = {"acceptance","regression"},
            priority = 2802
    )
    public void ValidatePagesCreationPage() {
        String testPageName = "О компании";
        AdministrationHelper.Pages.validateStaticPage(testPageName);
    }

    @Test(  enabled = testAdministration,
            description = "Тест создания и удаления статической страницы",
            groups = {"acceptance","regression"},
            priority = 2803
    )
    public void CreateDeletePage() {
        String pageName = "AAA";
        String pageURL = "testAAA";
        String desc = "я маленькая тестовая страничка, которую должен видеть только селен";

        kraken.get().adminPage("pages");
        AdministrationHelper.Pages.createStaticPage(pageName, pageURL, desc);
        AdministrationHelper.Pages.validateStaticPage(pageName, pageURL);
        AdministrationHelper.Pages.editStaticPage(pageName);
        AdministrationHelper.Pages.deleteStaticPage(pageName);
            // TODO проверить отсутствие страницы в списке в админке
            // TODO добавить проверку что тестовая страничка - 404 на сайте
    }
}