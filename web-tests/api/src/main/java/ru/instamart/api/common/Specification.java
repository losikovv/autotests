package ru.instamart.api.common;

import com.github.viclovsky.swagger.coverage.SwaggerCoverageV3RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.specification.ProxySpecification.host;
import static java.util.Objects.nonNull;
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
    //Внешние сервисы
    @Getter private RequestSpecification webhookSteRequestSpec;


    public void initSpec() {
        final String apiV1FullUrl = EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH;
        final String adminFullUrl = EnvironmentProperties.Env.FULL_ADMIN_URL_WITH_BASIC_AUTH_OLD;
        final String apiV2FullUrl = EnvironmentProperties.Env.FULL_SITE_URL;
        final String apiV3FullUrl = EnvironmentProperties.Env.FULL_SITE_URL;
        final String prodFullUrl =  EnvironmentProperties.Env.PROD_FULL_SITE_URL;

        final String prodAdminUrl =  EnvironmentProperties.Env.ADMIN_FULL_URL;
        final String shopperFullBaseUrl = EnvironmentProperties.Env.FULL_SHOPPER_GW_URL;
        final String shopperFullAdminUrl = EnvironmentProperties.Env.FULL_SHOPPER_URL;
        final String shopperStage = (EnvironmentProperties.STAGE).isBlank() ? EnvironmentProperties.K8S_NAME_SHP_SPACE :
                (EnvironmentProperties.K8S_NAME_SHP_SPACE).replace("kraken", EnvironmentProperties.STAGE);
        config = config().encoderConfig(encoderConfig().defaultContentCharset("UTF-8"));
        defaultParser = Parser.JSON;

        final boolean sslValidation = Boolean.parseBoolean(System.getProperty("ssl", "true"));
        if (!sslValidation) {
            log.debug("Enable SSL ignore");
            useRelaxedHTTPSValidation();
        }

        final String proxyIp = System.getProperty("proxy_ip");
        final int proxyPort = Integer.parseInt(System.getProperty("proxy_port", "443"));
        final int proxyTimeout = Integer.parseInt(System.getProperty("proxy_timeout", "5000"));

        if (nonNull(proxyIp) && addressReachable(proxyIp, proxyPort, proxyTimeout)) {
            log.debug("Setup proxy with url {}:{}", proxyIp, proxyPort);
            proxy = host(proxyIp).withPort(proxyPort);
        }

        responseSpecDefault = new ResponseSpecBuilder()
                //.expectResponseTime(lessThan(30000L))
                .expectStatusCode(not(401))
                .expectStatusCode(not(429))
                .expectStatusCode(not(500))
                .expectStatusCode(not(502))
                .expectStatusCode(not(503))
                //.expectContentType(ContentType.JSON)
                .build();

        responseSpecification = responseSpecDefault;

        responseSpecDataProvider = new ResponseSpecBuilder()
                //.expectResponseTime(lessThan(30000L))
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
                .setConfig(RestAssuredConfig.newConfig().redirect(RedirectConfig.redirectConfig().followRedirects(true)))
                .addFilter(new AllureRestAssuredCustom())
                .addFilter(new CounterFilter())
                .build();

        apiV2RequestSpec = new RequestSpecBuilder()
                .setBaseUri(apiV2FullUrl)
                .setBasePath("api/")
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssuredCustom())
                .addFilter(new SwaggerCoverageV3RestAssured())
                .addFilter(new CounterFilter())
                .build();

        prodRequestSpec = new RequestSpecBuilder()
                .setBaseUri(prodFullUrl)
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
                .addHeader(
                        "Client-Ver",
                        "99.9.9")
                .addHeader("x-testing-otp","true")
                .addHeader("x-testing-nosms","true")
                .addHeader("x-testing-nolimiter","true")
                .addHeader("sbm-forward-feature-version-shp", shopperStage)
                .addFilter(new AllureRestAssuredCustom())
                .addFilter(new CounterFilter())
                .build();

        shopperAdminRequestSpec = new RequestSpecBuilder()
                .setBaseUri(shopperFullAdminUrl)
                .setBasePath("")
                .setAccept(ContentType.JSON)
                .addHeader(
                        "Client-Ver",
                        "99.9.9")
                .addFilter(new AllureRestAssuredCustom())
                .addFilter(new CounterFilter())
                .build();

        locatorRequestSpec = new RequestSpecBuilder()
                .setBaseUri(shopperFullBaseUrl)
                .setBasePath("locator/v1/")
                .addHeader(
                        "Client-Ver",
                        "99.9.9")
                .addHeader("x-testing-otp","true")
                .addHeader("x-testing-nosms","true")
                .addHeader("x-testing-nolimiter","true")
                .addHeader("sbm-forward-feature-version-shp", shopperStage)
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
                .setBaseUri("https://paas-content-operations-eta.k-stage.sbmt.io")
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

    private static boolean addressReachable(final String address, final int port, final int timeout) {
        try (final Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(address, port), timeout);
            return true;
        } catch (IOException exception) {
            log.error("Broken proxy ! Skip proxy setup and continue without proxy");
            return false;
        }
    }
}
