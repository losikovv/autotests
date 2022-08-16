package ru.instamart.kraken.service.qa;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.common.Crypt;

import static io.restassured.RestAssured.given;

@Slf4j
public abstract class QaRequestBase {

    private static final String NAMED_STAGE_TOKEN = "UN6zae5B0xWcmVFm9LZVLgge3Y4JkWr5OAB2juMXDL-AfBBvFTydCxijHIFWc0KfrgeixCpJzhTJiJV-ylGedqjAZR3BCdokbinADShRH7i3UQEEu66XoENH_UYKuJdr";
    private static final String FEATURE_STAGE_TOKEN = "2K_n568xLpSe1Va5MHk5PAo9rbvrOlE227hjkBPjxZXQ-ye1sQ4_CwOdoLq6PmKY6AP0ZF6PSG7fVBdLOY9mn_rA2Iukmkv-paIRDZ7X_k83WwzbDdHbqM1jDa9ELos3";
    private static final String PROD_TOKEN = Crypt.INSTANCE.decrypt("MQVuG/gGvbYCrhfx+aAjYeq0xcP+NrW4IGBvY0FetS9Zz2pX70F4SJjTjqDt9aWGZn3gMjZjAzdkHuMEhVERjcbQCytcqtnGSoR2z7MTZv9SHLmPzfjznOHCohh0iQ2Fj9U48WG9jr+KuNOsPnjR2+fddonGWE9SlTaiBVMH8hnLRrl2H72uaWcitz8yE73A");

    private static final RequestSpecification qaService = new RequestSpecBuilder()
            .setBaseUri(EnvironmentProperties.Env.FULL_SITE_URL)
            .setBasePath("/api/qa")
            .setAccept(ContentType.JSON)
            .addFilter(new AllureRestAssured())
            .build();

    private static final RequestSpecification qaServiceProd = new RequestSpecBuilder()
            .setBaseUri(EnvironmentProperties.Env.PROD_FULL_SITE_URL)
            .setBasePath("/qa")
            .setAccept(ContentType.JSON)
            .addFilter(new AllureRestAssured())
            .build();

    public static RequestSpecification givenWithAuth() {
        return givenWithSpec()
                .header("Authorization", "Token token=" + addToken());
    }

    public static RequestSpecification givenWithSpec() {
        return EnvironmentProperties.Env.isProduction() ? given().spec(qaServiceProd) : given().spec(qaService);
    }

    private static String addToken() {
        final var url = EnvironmentProperties.Env.FULL_SITE_URL;

        if (!url.contains("stf") && !url.contains("sbermarket.ru") && !url.startsWith("m.")|| url.contains("preprod") || url.contains("sbersuperapp")) {
            return FEATURE_STAGE_TOKEN;
        } else if (url.contains("sbermarket.ru")) {
            return PROD_TOKEN;
        } else {
            return NAMED_STAGE_TOKEN;
        }
    }
}
