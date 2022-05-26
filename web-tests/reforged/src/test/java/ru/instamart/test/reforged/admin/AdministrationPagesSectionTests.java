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
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;

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

        pages().openAdminPageWithoutSpa(pages().pageUrl());
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

        pages().openAdminPageWithoutSpa(pages().pageUrl());
        pages().clickToNewPage();
        newPages().fillPageData(staticPage);
        newPages().submit();

        pages().openAdminPageWithoutSpa(pages().pageUrl());
        pages().checkTable();
        final var id = pages().getPageId(staticPage);

        pages().openSitePage(staticPage.getPageURL());
        pages().checkPageIsAvailable();

        pages().openAdminPageWithoutSpa(pages().pageUrl());
        pages().deleteEntry(id);
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

        pages().openAdminPageWithoutSpa(pages().pageUrl());
        pages().checkTable();
        pages().editEntry(staticPage.getPageName());
        newPages().fillPageData(staticPageEdited);
        newPages().submit();
        pages().waitPageLoad();
        pages().openSitePage(staticPageEdited.getPageURL());
        pages().waitPageLoad();
        pages().checkPageIsAvailable();
    }

    @AfterClass(alwaysRun = true)
    public void clearData() {
        SpreePagesDao.INSTANCE.deletePageBySlug("Auto_");
    }
}
