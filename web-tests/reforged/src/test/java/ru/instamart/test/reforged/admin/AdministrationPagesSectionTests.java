package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.kraken.testdata.StaticPages;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.pagesdata.StaticPageData;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Работа со статическими страницами")
public final class AdministrationPagesSectionTests {

    @BeforeClass(alwaysRun = true,
            description = "Выполняем шаги предусловий для теста")
    public void beforeTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());
    }

    @CaseId(507)
    @Story("Тест на проверку элементов на вкладке статических страниц")
    @Test(description = "Тест на проверку элементов на вкладке статических страниц",
            groups = {"admin-ui-smoke"}
    )
    public void validatePagesRootPage() {
        pages().goToPage();
        pages().checkTable();
        pages().checkTableEntry();
    }

    @CaseId(13)
    @Story("Тест создания и удаления статической страницы")
    @Test(description = "Тест создания и удаления статической страницы",
            groups = {"admin-ui-smoke"}
    )
    public void createDeletePage() {
        final StaticPageData staticPage = StaticPages.newStaticPage();
        pages().goToPage();
        pages().clickToNewPage();
        newPages().fillPageData(staticPage);
        newPages().submit();
        pages().openSitePage(staticPage.getPageURL());
        pages().checkPageIsAvailable();
        pages().goToPage();
        pages().deleteEntry(staticPage.getPageName());
    }

    @CaseId(14)
    @Story("Тест редактирования статической страницы")
    @Test(description = "Тест редактирования статической страницы",
            groups = {"admin-ui-smoke"}
    )
    public void createAndEditStaticPage() {
        final StaticPageData staticPage = StaticPages.newStaticPage();
        final StaticPageData staticPageEdited = StaticPages.editedStaticPage();
        pages().goToPage();
        newPages().goToPage();
        newPages().fillPageData(staticPage);
        newPages().submit();
        pages().editEntry(staticPage.getPageName());
        newPages().fillPageData(staticPageEdited);
        newPages().submit();
        pages().openSitePage(staticPageEdited.getPageURL());
        pages().checkPageIsAvailable();
        pages().goToPage();
        pages().deleteEntry(staticPageEdited.getPageName());
    }
}
