package ru.instamart.tests.ui.administration;

import org.testng.annotations.BeforeClass;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.core.testdata.ui.StaticPages;
import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import ru.instamart.ui.common.pagesdata.StaticPageData;
import ru.instamart.ui.modules.Administration;
import ru.instamart.ui.modules.User;
import ru.instamart.ui.objectsmap.Elements;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

@Epic("Админка STF")
@Feature("Работа со статическими страницами")
public class AdministrationPagesSectionTests extends TestBase {
    
    private final BaseUICheckpoints baseChecks = new BaseUICheckpoints();

    @BeforeClass(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.reach().admin();
        User.Auth.withEmail(UserManager.getDefaultAdmin());
        kraken.get().adminPage("pages");
    }

    @CaseId(507)
    @Story("Тест на проверку элементов на вкладке статических страниц")
    @Test(  description = "Тест на проверку элементов на вкладке статических страниц",
            groups = {"admin-ui-smoke"}
    )
    public void validatePagesRootPage() {
        String page = "О компании";
        kraken.get().adminPage("pages");
        baseChecks.checkIsElementPresent(Elements.Administration.StaticPagesSection.table());
        baseChecks.checkIsElementPresent(Elements.Administration.StaticPagesSection.tableEntry(page));
        baseChecks.checkIsElementPresent(Elements.Administration.StaticPagesSection.newPageButton());
        baseChecks.checkIsElementPresent(Elements.Administration.StaticPagesSection.editPageButton(page));
        baseChecks.checkIsElementPresent(Elements.Administration.StaticPagesSection.deletePageButton(page));
    }

    @CaseId(13)
    @Story("Тест создания и удаления статической страницы")
    @Test(  description = "Тест создания и удаления статической страницы",
            groups = {"admin-ui-smoke"}
    )
    public void createDeletePage() {
        final StaticPageData staticPage = StaticPages.newStaticPage();
        Administration.Pages.checkAndDeleteIfExists(staticPage.getPageName());
        Administration.Pages.create(staticPage);
        baseChecks.checkIsElementPresent(Elements.Administration.StaticPagesSection.tableEntry(staticPage.getPageName()));
        Administration.Pages.validateStaticPage(staticPage.getPageName(), staticPage.getPageURL());
        Administration.Pages.delete(staticPage.getPageName());
        baseChecks.checkIsElementNotPresent(Elements.Administration.StaticPagesSection.editPageButton(staticPage.getPageName()));
        Administration.Pages.validateStaticNotExistsPage(staticPage.getPageName(), staticPage.getPageURL());
    }

    @CaseId(14)
    @Story("Тест редактирования статической страницы")
    @Test(  description = "Тест редактирования статической страницы",
            groups = {"admin-ui-smoke"}
    )
    public void createAndEditStaticPage(){
        final StaticPageData staticPage = StaticPages.newStaticPage();
        final StaticPageData staticPageEdited = StaticPages.editedStaticPage();
        Administration.Pages.checkAndDeleteIfExists(staticPage.getPageName());
        Administration.Pages.checkAndDeleteIfExists(staticPageEdited.getPageName());
        Administration.Pages.create(staticPage);
        Administration.Pages.editStaticPage(staticPage,staticPageEdited);
        baseChecks.checkIsElementPresent(Elements.Administration.StaticPagesSection.tableEntry(staticPageEdited.getPageName()));
        Administration.Pages.validateStaticPage(staticPageEdited.getPageName(), staticPageEdited.getPageURL());
        Administration.Pages.delete(staticPageEdited.getPageName());
    }
}