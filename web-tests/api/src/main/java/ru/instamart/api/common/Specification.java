package ru.instamart.api.common;

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
    @Getter private RequestSpecification shopperRequestSpec;
    @Getter private RequestSpecification surgeRequestSpec;
    @Getter private RequestSpecification risExporterRequestSpec;
    @Getter private RequestSpecification prodRequestSpec;

    public void initSpec() {
        final String apiV1FullUrl = EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH;
        final String adminFullUrl = EnvironmentProperties.Env.FULL_ADMIN_URL_WITH_BASIC_AUTH;
        final String apiV2FullUrl = EnvironmentProperties.Env.FULL_SITE_URL;
        final String apiV3FullUrl = EnvironmentProperties.Env.FULL_SITE_URL;
        final String prodFullUrl =  EnvironmentProperties.Env.PROD_FULL_SITE_URL;
        final String shopperFullBaseUrl = EnvironmentProperties.Env.FULL_SHOPPER_URL;
        port = 443;
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
                .build();

        apiAdminRequestSpec = new RequestSpecBuilder()
                .setBaseUri(adminFullUrl)
                .setConfig(RestAssuredConfig.newConfig().redirect(RedirectConfig.redirectConfig().followRedirects(true)))
                .addFilter(new AllureRestAssuredCustom())
                .build();

        apiV2RequestSpec = new RequestSpecBuilder()
                .setBaseUri(apiV2FullUrl)
                .setBasePath("api/")
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssuredCustom())
                .build();

        apiV3RequestSpec = new RequestSpecBuilder()
                //todo перевести на спецификацию все тесты по Apiv3
                .setBaseUri(apiV3FullUrl)
                .setBasePath("api/")
                .setAccept(ContentType.JSON)
                .addHeader("Api-Version", "3.0")
                .addFilter(new AllureRestAssuredCustom())
                .build();

        shopperRequestSpec = new RequestSpecBuilder()
                .setBaseUri(shopperFullBaseUrl.substring(0, shopperFullBaseUrl.length() - 1))
                .setBasePath("")
                .setAccept(ContentType.JSON)
                .addHeader(
                        "Client-Ver",
                        "99.9.9")
                .addFilter(new AllureRestAssuredCustom())
                .build();

        surgeRequestSpec = new RequestSpecBuilder()
                .setBaseUri("https://paas-content-operations-surge.k-stage.sbermarket.tech")
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

        prodRequestSpec = new RequestSpecBuilder()
                .setBaseUri(prodFullUrl)
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
