package instamart.api.common;

import instamart.api.v2.ApiV2Requests;
import instamart.core.common.AppManager;
import instamart.ui.common.pagesdata.UserData;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import instamart.core.common.AppManager;
import instamart.ui.common.pagesdata.UserData;

import java.util.UUID;

import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.not;

public class RestBase {
    protected static final AppManager kraken = new AppManager();
    protected Response response;
    public static RequestSpecification customerRequestSpec;
    public static RequestSpecification shopperRequestSpec;

    @BeforeSuite(groups = {"rest","rest-zones","rest-smoke","rest-v2-smoke"})
    public void start() throws Exception {
        kraken.riseRest();
        initSpec();
    }

    private void initSpec() {
        String customerFullBaseUrl = AppManager.environment.getBasicUrlWithHttpAuth();
        String shopperFullBaseUrl = AppManager.environment.getShopperUrl();
        port = 443;
        config = config().encoderConfig(encoderConfig().defaultContentCharset("UTF-8"));
        defaultParser = Parser.JSON;

        responseSpecification = new ResponseSpecBuilder()
                //.expectResponseTime(lessThan(30000L))
                .expectStatusCode(not(401))
                .expectStatusCode(not(429))
                .expectStatusCode(not(500))
                .build();

        customerRequestSpec = new RequestSpecBuilder()
                .setBaseUri(customerFullBaseUrl.substring(0, customerFullBaseUrl.length() - 1))
                .setBasePath("api/")
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .addFilter(new ErrorLoggingFilter())
                .build();

        shopperRequestSpec = new RequestSpecBuilder()
                .setBaseUri(shopperFullBaseUrl.substring(0, shopperFullBaseUrl.length() - 1))
                .setBasePath("")
                .addHeader(
                        "Client-Ver",
                        "99.9.9")
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .addFilter(new ErrorLoggingFilter())
                .build();
    }

    @BeforeClass(alwaysRun = true,
                 groups = {"rest","rest-zones","rest-smoke","rest-v2-smoke"})
    public void logout() {
        kraken.apiV2().logout();
    }

    @AfterMethod(description = "Отмена активных заказов после каждого теста",
                 alwaysRun = true)
    public void cancelActiveOrders() {
        if (ApiV2Requests.authorized() &&
                AppManager.environment.getServer().equalsIgnoreCase("production")) {
            System.out.println("Отменяем активные заказы");
            kraken.apiV2().cancelActiveOrders();
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
