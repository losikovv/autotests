package ru.instamart.autotests.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.appmanager.AdministrationHelper;

import static ru.instamart.autotests.application.Config.enableAdministrationTests;

public class AdministrationPages extends TestBase{

    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel() {
        kraken.reach().admin();
    }

    @Test(  enabled = enableAdministrationTests,
            description = "Проверка вкладки статических страниц",
            groups = {"ohmy"},
            priority = 6666
    )
    public void ValidatePagesRootPage() throws Exception {
        // TODO: вынести тестовую страницу в глобальные константы
        String testPageName = "О компании";
        AdministrationHelper.Pages.validatePagesPage(testPageName);
    }

    @Test(  enabled = enableAdministrationTests,
            description = "Проверка страницы создания статической страницы",
            groups = {"ohmy"},
            priority = 6667
    )
    public void ValidatePagesCreationPage() throws Exception {
        String testPageName = "О компании";
        AdministrationHelper.Pages.validateStaticPage(testPageName);
    }

    @Test(  enabled = enableAdministrationTests,
            description = "Тест создания и удаления статической страницы",
            groups = {"ohmy"/*, "acceptance","regression"*/},
            priority = 6668
    )
    public void CreateDeletePage() throws Exception {
        String pageName = "AAA";
        String pageURL = "testAAA";
        String desc = "я маленькая тестовая страничка, которую должен видеть только селен";

        kraken.get().adminPage("pages");
        AdministrationHelper.Pages.createStaticPage(pageName, pageURL, desc);
        AdministrationHelper.Pages.validateStaticPage(pageName, pageURL);


        AdministrationHelper.Pages.editStaticPage(pageName);
        AdministrationHelper.Pages.deleteStaticPage(pageName);

    }
}