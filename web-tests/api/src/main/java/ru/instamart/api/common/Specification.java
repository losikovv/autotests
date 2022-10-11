package ru.instamart.api.common;

import com.github.viclovsky.swagger.coverage.SwaggerCoverageV3RestAssured;
import io.restassured.authentication.NoAuthScheme;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;

import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.not;

@Slf4j
public enum Specification {

    INSTANCE;

    private static ResponseSpecification responseSpecDefault;
    private static ResponseSpecification responseSpecDataProvider;
    @Getter private RequestSpecification apiV3RequestSpec;
    @Getter private RequestSpecification apiV2RequestSpec;
    @Getter private RequestSpecification apiV1RequestSpec;
    @Getter private RequestSpecification apiAdminRequestSpec;
    @Getter private RequestSpecification shadowcatRequestSpec;
    @Getter private RequestSpecification shopperRequestSpec;
    @Getter private RequestSpecification surgeRequestSpec;
    @Getter private RequestSpecification etaRequestSpec;
    @Getter private RequestSpecification risExporterRequestSpec;
    @Getter private RequestSpecification prodRequestSpec;
    @Getter private RequestSpecification prodAdminRequestSpec;
    @Getter private RequestSpecification shopperAdminRequestSpec;
    @Getter private RequestSpecification locatorRequestSpec;
    @Getter private RequestSpecification authorizationServiceRequestSpec;
    @Getter private RequestSpecification keycloakRequestSpec;
    @Getter private RequestSpecification apiDCRequestSpec;
    //Внешние сервисы
    @Getter private RequestSpecification webhookSteRequestSpec;


    public void initSpec() {
        final var basicAuthScheme = new PreemptiveBasicAuthScheme();
        basicAuthScheme.setUserName(CoreProperties.BASIC_AUTH_USERNAME);
        basicAuthScheme.setPassword(CoreProperties.BASIC_AUTH_PASSWORD);

        final String apiV1FullUrl = hackForProd();
        final String adminFullUrl = EnvironmentProperties.Env.ADMIN_FULL_URL;
        final String apiV2FullUrl = EnvironmentProperties.Env.FULL_SITE_URL;
        final String apiV3FullUrl = hackForProd();
        final String prodFullUrl = EnvironmentProperties.Env.FULL_SITE_URL;

        final String prodAdminUrl = EnvironmentProperties.Env.ADMIN_FULL_URL;
        final String shopperFullBaseUrl = EnvironmentProperties.SHOPPER_GW_URL;
        final String shopperFullAdminUrl = EnvironmentProperties.Env.FULL_SHOPPER_URL;
        final String shopperStage = (EnvironmentProperties.STAGE).isBlank() ? "kraken" : EnvironmentProperties.STAGE;
        final String bffForward = (System.getProperty("bff_forward")) == null ? "m" : System.getProperty("bff_forward");
        final String etaStage = "https://" + EnvironmentProperties.Env.ETA_NAMESPACE + ".gw-stage.sbmt.io";

        config = config().encoderConfig(encoderConfig().defaultContentCharset("UTF-8"));
        defaultParser = Parser.JSON;

        responseSpecDefault = new ResponseSpecBuilder()
                .expectStatusCode(not(401))
                .expectStatusCode(not(429))
                .expectStatusCode(not(500))
                .expectStatusCode(not(502))
                .expectStatusCode(not(503))
                .build();

        responseSpecification = responseSpecDefault;

        responseSpecDataProvider = new ResponseSpecBuilder()
                .expectStatusCode(not(401))
                .expectStatusCode(not(429))
                .expectStatusCode(not(500))
                .expectStatusCode(not(502))
                .expectStatusCode(not(503))
                .build();

        apiV1RequestSpec = new RequestSpecBuilder()
                .setBaseUri(apiV1FullUrl)
                .setBasePath("api/")
                .setAccept(ContentType.JSON)
                .setAuth(EnvironmentProperties.Env.isProduction() ? new NoAuthScheme() : basicAuthScheme)
                .addFilter(new AllureRestAssuredCustom())
                .addFilter(new SwaggerCoverageV3RestAssured())
                .addFilter(new CounterFilter())
                .build();

        prodAdminRequestSpec = new RequestSpecBuilder()
                .setBaseUri(prodAdminUrl)
                .setBasePath("api/")
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssuredCustom())
                .addFilter(new CounterFilter())
                .build();

        apiAdminRequestSpec = new RequestSpecBuilder()
                .setBaseUri(adminFullUrl)
                .setBasePath("admin/")
                .setAuth(EnvironmentProperties.Env.isProduction() ? new NoAuthScheme() : basicAuthScheme)
                .setConfig(RestAssuredConfig.newConfig().redirect(RedirectConfig.redirectConfig().followRedirects(true)))
                .addFilter(new AllureRestAssuredCustom())
                .addFilter(new CounterFilter())
                .build();

        apiV2RequestSpec = new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .setBaseUri(apiV2FullUrl)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssuredCustom())
                .addFilter(new SwaggerCoverageV3RestAssured())
                .addFilter(new CounterFilter())
                .build();
        //todo убрать после того как на проде можно будет обойтись без "api/v2"
        apiV2RequestSpec.basePath(EnvironmentProperties.STAGE.equals("m") ? "" : "api/v2/");

        if (EnvironmentProperties.STAGE.equals("m")) {
            apiV2RequestSpec.header("sbm-forward-feature-version-paas-content-front-platform-stf-mobile-aggregator", bffForward);
        }

        prodRequestSpec = new RequestSpecBuilder()
                .setBaseUri(prodFullUrl)
                .setBasePath("v2/")
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssuredCustom())
                .addFilter(new CounterFilter())
                .build();

        apiV3RequestSpec = new RequestSpecBuilder()
                .setBaseUri(apiV3FullUrl)
                .setBasePath("api/")
                .setAccept(ContentType.JSON)
                .addHeader("Api-Version", "3.0")
                .addFilter(new AllureRestAssuredCustom())
                .addFilter(new SwaggerCoverageV3RestAssured())
                .addFilter(new CounterFilter())
                .build();

        shopperRequestSpec = new RequestSpecBuilder()
                .setBaseUri(shopperFullBaseUrl)
                .setAccept(ContentType.JSON)
                .addHeader("Client-Ver", "99.9.9")
                .addHeader("x-testing-otp", "true")
                .addHeader("x-testing-nosms", "true")
                .addHeader("x-testing-nolimiter", "true")
                .addHeader("sbm-forward-feature-version-paas-content-shopper", shopperStage)
                .addHeader("sbm-forward-api-gw-traffic", "paas-content-shopper")
                .addFilter(new AllureRestAssuredCustom())
                .addFilter(new CounterFilter())
                .build();

        shopperAdminRequestSpec = new RequestSpecBuilder()
                .setBaseUri(shopperFullAdminUrl)
                .setBasePath("")
                .setAccept(ContentType.JSON)
                .addHeader("Client-Ver", "99.9.9")
                .addFilter(new AllureRestAssuredCustom())
                .addFilter(new CounterFilter())
                .build();

        locatorRequestSpec = new RequestSpecBuilder()
                .setBaseUri(shopperFullBaseUrl)
                .setBasePath("locator/v1/")
                .addHeader("Client-Ver", "99.9.9")
                .addHeader("x-testing-otp", "true")
                .addHeader("x-testing-nosms", "true")
                .addHeader("x-testing-nolimiter", "true")
                .addHeader("sbm-forward-feature-version-paas-content-shopper", shopperStage)
                .addHeader("sbm-forward-api-gw-traffic", "paas-content-shopper")
                .addFilter(new AllureRestAssuredCustom())
                .build();

        surgeRequestSpec = new RequestSpecBuilder()
                .setBaseUri("https://paas-content-operations-surge.k-stage.sbermarket.tech")
                .setBasePath("api/")
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssuredCustom())
                .build();

        etaRequestSpec = new RequestSpecBuilder()
                .setConfig(config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .setBaseUri(etaStage)
                .setBasePath("api/")
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssuredCustom())
                .build();

        risExporterRequestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api-deliveryclub.sbermarket.ru")
                .setBasePath("v1/")
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssuredCustom())
                .build();

        authorizationServiceRequestSpec = new RequestSpecBuilder()
                .setBaseUri("https://paas-content-core-services-authorization.gw-stage.sbmt.io")
                .setBasePath("api/")
                .setAccept(ContentType.ANY)
                .addFilter(new AllureRestAssuredCustom())
                .build();

        keycloakRequestSpec = new RequestSpecBuilder()
                .setBaseUri("https://keycloak-stage.gw-stage.sbmt.io")
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssuredCustom())
                .build();

        apiDCRequestSpec = new RequestSpecBuilder()
                .setBaseUri(apiV2FullUrl)
                .setBasePath("api/")
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssuredCustom())
                .addFilter(new SwaggerCoverageV3RestAssured())
                .addFilter(new CounterFilter())
                .build();

        //Внешние сервисы
        webhookSteRequestSpec = new RequestSpecBuilder()
                .setBaseUri(CoreProperties.DEFAULT_WEBHOOK_SITE_URL)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssuredCustom())
                .build();

        shadowcatRequestSpec = new RequestSpecBuilder()
                .setBaseUri("https://paas-content-demand-shadowcat.gw-stage.sbmt.io")
                .setBasePath("api/v1/admin/")
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssuredCustom())
                .build();
    }

    static public void setResponseSpecDataProvider() {
        responseSpecification = responseSpecDataProvider;
    }

    static public void setResponseSpecDefault() {
        responseSpecification = responseSpecDefault;
    }

    private static String hackForProd() {
        return EnvironmentProperties.Env.isProduction()
                ? EnvironmentProperties.Env.FULL_SITE_URL.replaceAll("api.", "")
                : EnvironmentProperties.Env.FULL_SITE_URL;
    }
}
