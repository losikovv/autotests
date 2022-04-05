package ru.instamart.test.api.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.admin.PagesAdminRequest;
import ru.instamart.jdbc.dao.stf.SpreePagesDao;
import ru.instamart.jdbc.entity.stf.SpreePagesEntity;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkPageInDb;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("Admin")
@Feature("Страницы")
public class PagesAdminTest extends RestBase {

    private Long id;
    private String slug;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.auth();
    }

    @CaseId(1144)
    @Story("Статические страницы")
    @Test(groups = {"api-instamart-regress"}, description = "Получение всех страниц")
    public void getAllPage() {
        final Response response = PagesAdminRequest.GET();
        checkStatusCode(response, 200, ContentType.HTML);
    }

    @CaseIDs(value = {@CaseId(1135), @CaseId(1140)})
    @Story("Статические страницы")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание новой статической страницы с обязательными параметрами",
            dataProvider = "pageData",
            dataProviderClass = RestDataProvider.class)
    public void createPage(PagesAdminRequest.Page page, int visible, int position) {
        slug = page.getSlug();
        admin.createStaticPage(page);
        SpreePagesEntity pageFromDb = SpreePagesDao.INSTANCE.getPageBySlug(slug);
        checkPageInDb(page, pageFromDb, visible, position);
        id = pageFromDb.getId();
    }

    @CaseId(1141)
    @Story("Статические страницы")
    @Test(groups = {"api-instamart-regress"}, description = "Создание новой статической страницы без обязательных параметров")
    public void createPageWithoutRequiresParams() {
        final Response response = PagesAdminRequest.POST(new PagesAdminRequest.Page());
        checkStatusCode400(response);
    }

    @CaseIDs(value = {@CaseId(1136), @CaseId(1142)})
    @Story("Статические страницы")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование статической страницы с обязательными параметрами",
            dependsOnMethods = "createPage",
            dataProvider = "updatedPageData",
            dataProviderClass = RestDataProvider.class)
    public void editPage(PagesAdminRequest.Page page, int visible, int position) {
        SessionFactory.createSessionToken(SessionType.ADMIN, UserManager.getDefaultAdminAllRoles());
        String slug = page.getSlug();
        final Response response = PagesAdminRequest.PATCH(page, id);
        checkStatusCode302(response);
        SpreePagesEntity pageFromDb = SpreePagesDao.INSTANCE.getPageBySlug(slug);
        checkPageInDb(page, pageFromDb, visible, position);
    }

    @CaseId(1143)
    @Story("Статические страницы")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование статической страницы без обязательных параметров",
            dependsOnMethods = "createPage")
    public void editPageWithoutRequiredParams() {
        final Response response = PagesAdminRequest.PATCH(new PagesAdminRequest.Page(), id);
        checkStatusCode400(response);
    }

    @CaseId(1137)
    @Story("Статические страницы")
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление страницы",
            dependsOnMethods = {"editPage", "editPageWithoutRequiredParams"})
    public void deletePage() {
        admin.deleteStaticPage(id);
        SpreePagesEntity pageFromDb = SpreePagesDao.INSTANCE.getPageBySlug(slug);
        Assert.assertNull(pageFromDb, "Страница не удалилась");
    }

    @AfterClass
    public void clearData() {
        SpreePagesDao.INSTANCE.deletePageBySlug("Autotest-");
    }
}
