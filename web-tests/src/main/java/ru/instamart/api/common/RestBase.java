package instamart.api.common;

import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import instamart.core.common.AppManager;
import instamart.ui.common.pagesdata.UserData;

import java.util.UUID;

public abstract class RestBase {
    protected static final AppManager kraken = new AppManager();
    protected Response response;

    @BeforeSuite(groups = {"rest","rest-zones","rest-smoke","rest-v2-smoke"})
    public void start() throws Exception {
        kraken.riseRest();
    }

    @BeforeClass(alwaysRun = true,
                 groups = {"rest","rest-zones","rest-smoke","rest-v2-smoke"})
    public void logout() {
        kraken.rest().logout();
    }

    @AfterMethod(description = "Отмена активных заказов после каждого теста",
                 alwaysRun = true)
    public void cancelActiveOrders() {
        if (Requests.token.get() != null) {
            System.out.println("Отменяем активные заказы");
            kraken.rest().cancelActiveOrders();
        }
    }

    public String email() {
        return UUID.randomUUID() + "@example.com";
    }

    public UserData user() {
        return new UserData(
                email(),
                "instamart",
                "Василий Автотестов");
    }
}
