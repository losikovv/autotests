package instamart.api.common;

import instamart.ui.common.pagesdata.EnvironmentData;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.specification.ProxySpecification.host;
import static org.hamcrest.Matchers.not;

public enum Specification {

    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(Specification.class);

    private static ResponseSpecification responseSpecDefault;
    private static ResponseSpecification responseSpecDataProvider;
    private RequestSpecification customerRequestSpec;
    private RequestSpecification shopperRequestSpec;

    public void initSpec() {
        final String customerFullBaseUrl = EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth();
        final String shopperFullBaseUrl = EnvironmentData.INSTANCE.getShopperUrl();
        port = 443;
        config = config().encoderConfig(encoderConfig().defaultContentCharset("UTF-8"));
        defaultParser = Parser.JSON;

        final String protocol = System.getProperty("protocol");
        if (protocol != null && !protocol.isEmpty()) {
            logger.info("Enable SSL ignore");
            useRelaxedHTTPSValidation();
        }

        final String proxyIp = System.getProperty("proxy_ip", "127.0.0.1");
        final int proxyPort = Integer.parseInt(System.getProperty("proxy_port", "443"));

        if (!proxyIp.equals("127.0.0.1") && addressReachable(proxyIp, proxyPort, 5000)) {
            logger.info("Setup proxy with url {}:{}", proxyIp, proxyPort);
            proxy = host(proxyIp).withPort(proxyPort);
        }

        responseSpecDefault = new ResponseSpecBuilder()
                //.expectResponseTime(lessThan(30000L))
                .expectStatusCode(not(401))
                .expectStatusCode(not(429))
                .expectStatusCode(not(500))
                .expectStatusCode(not(502))
                .expectStatusCode(not(503))
                .expectContentType(ContentType.JSON)
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

        customerRequestSpec = new RequestSpecBuilder()
                .setBaseUri(customerFullBaseUrl.substring(0, customerFullBaseUrl.length() - 1))
                .setBasePath("api/")
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();

        shopperRequestSpec = new RequestSpecBuilder()
                .setBaseUri(shopperFullBaseUrl.substring(0, shopperFullBaseUrl.length() - 1))
                .setBasePath("")
                .setAccept(ContentType.JSON)
                .addHeader(
                        "Client-Ver",
                        "99.9.9")
                .addFilter(new AllureRestAssured())
                .build();
    }

    static public void setResponseSpecDataProvider() {
        responseSpecification = responseSpecDataProvider;
    }

    static public void setResponseSpecDefault() {
        responseSpecification = responseSpecDefault;
    }

    public RequestSpecification getCustomerRequestSpec() {
        return customerRequestSpec;
    }

    public RequestSpecification getShopperRequestSpec() {
        return shopperRequestSpec;
    }

    private static boolean addressReachable(final String address, final int port, final int timeout) {
        try (final Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(address, port), timeout);
            return true;
        } catch (IOException exception) {
            logger.error("Broken proxy ! Skip proxy setup and continue without proxy");
            return false;
        }
    }
}
