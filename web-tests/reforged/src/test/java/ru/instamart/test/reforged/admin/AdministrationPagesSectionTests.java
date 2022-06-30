package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.jdbc.dao.stf.SpreePagesDao;
import ru.instamart.kraken.data.StaticPageData;
import ru.instamart.kraken.data.StaticPages;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.stf.page.StfRouter;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("Админка STF")
@Feature("Работа со статическими страницами")
public final class AdministrationPagesSectionTests {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(507)
    @Story("Тест на проверку элементов на вкладке статических страниц")
    @Test(description = "Тест на проверку элементов на вкладке статических страниц", groups = {"regression", "smoke"})
    public void validatePagesRootPage() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        pages().goToPage();
        pages().checkTable();
        pages().checkTableEntry();
    }

    @CaseId(13)
    @Story("Тест создания и удаления статической страницы")
    @Test(description = "Тест создания и удаления статической страницы", groups = "regression")
    public void createDeletePage() {
        final StaticPageData staticPage = StaticPages.newStaticPage();

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        pages().goToPage();
        pages().clickToNewPage();
        newPages().fillPageData(staticPage);
        newPages().submit();

        home().goToPage(staticPage.getPageURL());
        home().checkPageIsAvailable();

        pages().goToPage();
        pages().waitPageLoad();
        pages().removeEntry(staticPage.getPageName());
        pages().checkDeletePopupVisible();
        pages().clickConfirmDeletePage();

        pages().checkDeleteAlertVisible();
    }

    @CaseId(14)
    @Story("Тест редактирования статической страницы")
    @Test(description = "Тест редактирования статической страницы", groups = "regression")
    public void createAndEditStaticPage() {
        final StaticPageData staticPage = StaticPages.newStaticPage();
        final StaticPageData staticPageEdited = StaticPages.editedStaticPage();

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        helper.createStaticPageInAdmin(staticPage);

        pages().goToPage();
        pages().waitPageLoad();
        pages().checkTable();
        pages().editEntry(staticPage.getPageName());

        newPages().waitPageLoad();
        newPages().fillPageData(staticPageEdited);
        newPages().submit();

        home().goToPage(staticPageEdited.getPageURL());
        home().checkPageIsAvailable();
    }

    @AfterClass(alwaysRun = true)
    public void clearData() {
        SpreePagesDao.INSTANCE.deletePageBySlug("Auto_");
    }
}
