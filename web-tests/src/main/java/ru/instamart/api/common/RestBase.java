package instamart.api.common;

import com.google.common.collect.ImmutableMap;
import instamart.api.helpers.ApiV2Helper;
import instamart.api.helpers.ShopperApiHelper;
import instamart.core.common.AppManager;
import instamart.core.helpers.ConsoleOutputCapturerHelper;
import instamart.ui.common.pagesdata.UserData;
import io.qameta.allure.Allure;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.UUID;

import static instamart.core.helpers.AllureHelper.allureEnvironmentWriter;
import static instamart.core.helpers.HelperBase.verboseMessage;
import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.not;

public class RestBase {
    protected static final AppManager kraken = new AppManager();
    protected static final ApiV2Helper apiV2 = new ApiV2Helper();
    protected static final ShopperApiHelper shopper = new ShopperApiHelper();
    protected Response response;
    public static RequestSpecification customerRequestSpec;
    public static RequestSpecification shopperRequestSpec;
    private static ConsoleOutputCapturerHelper capture = new ConsoleOutputCapturerHelper();

    @BeforeSuite(groups = {
            "api-zones",
            "api-v2-smoke",
            "api-shopper-smoke",
            "api-v2-regress",
            "api-shopper-regress",
            "MRAutoCheck"},
                 description = "Инициализация")
    public void start() throws Exception {
        kraken.riseRest();
        initSpec();
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Tenant", AppManager.environment.getTenant())
                        .put("URL", AppManager.environment.getBasicUrl())
                        .put("Administration", AppManager.environment.getAdminUrl())
                        .put("Shopper", AppManager.environment.getShopperUrl())
                        .build(), System.getProperty("user.dir")
                        + "/build/allure-results/");
    }

    public void initSpec() {
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
                 groups = {
                         "api-zones",
                         "api-v2-smoke",
                         "api-shopper-smoke",
                         "api-v2-regress",
                         "api-shopper-regress",
                         "MRAutoCheck"},
                 description = "Логаут")
    public void logout() {
        apiV2.logout();
    }

    @AfterMethod(description = "Отмена активных заказов",
                 groups = {
                    "api-zones",
                    "api-shopper-regress",
                         "MRAutoCheck"},
                 alwaysRun = true)
    public void cancelActiveOrders() {
        if (apiV2.authorized() &&
                AppManager.environment.getServer().equalsIgnoreCase("production")) {
            verboseMessage("Отменяем активные заказы");
            apiV2.cancelActiveOrders();
        }
    }
    @BeforeMethod(alwaysRun = true,description = "Стартуем запись системного лога")
    public void captureStart(){
        capture.start();
    }
    @AfterMethod(alwaysRun = true,description = "Добавляем системный лог к тесту")
    public void captureFinish(){
        String value = capture.stop();
        Allure.addAttachment("Системный лог теста",value);
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
