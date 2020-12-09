package instamart.api.common;

import instamart.ui.common.pagesdata.EnvironmentData;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.not;

public enum Specification {

    INSTANCE;

    private RequestSpecification customerRequestSpec;
    private RequestSpecification shopperRequestSpec;

    public void initSpec() {
        String customerFullBaseUrl = EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth();
        String shopperFullBaseUrl = EnvironmentData.INSTANCE.getShopperUrl();
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

    public RequestSpecification getCustomerRequestSpec() {
        return customerRequestSpec;
    }

    public RequestSpecification getShopperRequestSpec() {
        return shopperRequestSpec;
    }
}
