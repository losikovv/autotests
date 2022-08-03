package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v1.AdminShippingMethodV1;
import ru.instamart.api.model.v1.PricerV1;
import ru.instamart.api.model.v1.ShippingMethodKindV1;
import ru.instamart.api.model.v1.ShippingMethodV1;
import ru.instamart.api.request.v1.ShippingMethodKindsV1Request;
import ru.instamart.api.request.v1.ShippingMethodsV1Request;
import ru.instamart.api.response.v1.*;
import ru.instamart.jdbc.dao.stf.ShippingMethodKindsDao;
import ru.instamart.jdbc.dao.stf.ShippingMethodsCleanDao;
import ru.instamart.jdbc.dao.stf.SpreeShippingMethodsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Collections;
import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Способы доставки")
public class ShippingMethodsV1Tests extends RestBase {

    private ShippingMethodKindV1 shippingMethodKind;
    private ShippingMethodsV1Request.ShippingMethod shippingMethod;
    private int shippingMethodId;
    private int marketingPricerId;
    private int nominalCostPricerId;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authApi();
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2137)
    @Test(description = "Получение списка способов доставки",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getShippingMethodKinds() {
        final Response response = ShippingMethodKindsV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingMethodKindsV1Response.class);
        if (!EnvironmentProperties.Env.isProduction()) {
            int countFromDb = ShippingMethodKindsDao.INSTANCE.getCount();
            compareTwoObjects(response.as(ShippingMethodKindsV1Response.class).getShippingMethodKinds().size(), countFromDb);
        }
    }

    @CaseId(2137)
    @Test(description = "Получение списка способов доставки",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getAdminShippingMethodKinds() {
        final Response response = ShippingMethodKindsV1Request.Admin.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingMethodKindsV1Response.class);
        if (!EnvironmentProperties.Env.isProduction()) {
            int countFromDb = ShippingMethodKindsDao.INSTANCE.getCount();
            compareTwoObjects(response.as(ShippingMethodKindsV1Response.class).getShippingMethodKinds().size(), countFromDb);
        }
    }

    @CaseId(2776)
    @Test(description = "Получение списка способов доставки",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getShippingMethods() {
        final Response response = ShippingMethodsV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingMethodsV1Response.class);
        if (!EnvironmentProperties.Env.isProduction()) {
            List<ShippingMethodV1> shippingMethods = response.as(ShippingMethodsV1Response.class).getShippingMethods();
            compareTwoObjects(shippingMethods.size(), SpreeShippingMethodsDao.INSTANCE.getCount());
        }
    }

    @CaseId(2777)
    @Test(description = "Создание способа доставки",
            groups = {"api-instamart-regress"})
    public void createShippingMethod() {
        shippingMethodKind = apiV1.getShippingMethodKinds().get(0);
        shippingMethod = ShippingMethodsV1Request.ShippingMethod.builder()
                .name("Autotest-" + Generate.literalString(4))
                .shippingCategoryIds(Collections.singletonList(apiV1.getShippingCategories().get(0).getId()))
                .shippingMethodKindId(shippingMethodKind.getId())
                .build();
        final Response response = ShippingMethodsV1Request.POST(shippingMethod);
        checkStatusCode(response, 201);
        checkResponseJsonSchema(response, AdminShippingMethodV1Response.class);
        AdminShippingMethodV1 shippingMethodFromResponse = response.as(AdminShippingMethodV1Response.class).getShippingMethod();
        shippingMethodId = shippingMethodFromResponse.getId();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(shippingMethodFromResponse.getName(), shippingMethod.getName(), softAssert);
        compareTwoObjects(shippingMethodFromResponse.getShippingMethodKindId(), shippingMethodKind.getId(), softAssert);
        compareTwoObjects(shippingMethodFromResponse.getKind(), shippingMethodKind.getSlug(), softAssert);
        softAssert.assertAll();
    }

    @CaseId(2778)
    @Test(description = "Получение способа доставки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "createShippingMethod")
    public void getShippingMethod() {
        final Response response = ShippingMethodsV1Request.GET(shippingMethodId);
        checkStatusCode(response, 200);
        checkResponseJsonSchema(response, ShippingMethodV1Response.class);
        ShippingMethodV1 shippingMethodFromResponse = response.as(ShippingMethodV1Response.class).getShippingMethod();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(shippingMethodFromResponse.getName(), shippingMethod.getName(), softAssert);
        compareTwoObjects(shippingMethodFromResponse.getKind(), shippingMethodKind.getSlug(), softAssert);
        softAssert.assertAll();
    }

    @CaseId(2779)
    @Test(description = "Получение списка правил",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getRuleTypes() {
        final Response response = ShippingMethodsV1Request.Rules.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RuleTypesV1Response.class);
    }

    @CaseId(2780)
    @Test(description = "Получение списка калькуляторов цен",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getCalculatorTypes() {
        final Response response = ShippingMethodsV1Request.Calculators.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CalculatorTypesV1Response.class);
    }

    @CaseId(2781)
    @Test(description = "Создание маркетингового правила",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "createShippingMethod")
    public void createMarketingRule() {
        final Response response = ShippingMethodsV1Request.MarketingPricers.POST(shippingMethodId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PricerV1Response.class);
        marketingPricerId = response.as(PricerV1Response.class).getPricer().getId();
    }

    @CaseId(2782)
    @Test(description = "Получение маркетингового правила",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "createMarketingRule")
    public void getMarketingRule() {
        final Response response = ShippingMethodsV1Request.MarketingPricers.GET(shippingMethodId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PricersV1Response.class);
        List<PricerV1> pricers = response.as(PricersV1Response.class).getPricers();
        checkFieldIsNotEmpty(pricers, "маркетинговые правила доставки");
        compareTwoObjects(pricers.get(0).getId(), marketingPricerId);
    }

    @CaseId(2783)
    @Test(description = "Создание правила номинальной стоимости доставки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "createShippingMethod")
    public void createNominalCostRule() {
        final Response response = ShippingMethodsV1Request.NominalPricers.POST(shippingMethodId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PricerV1Response.class);
        nominalCostPricerId = response.as(PricerV1Response.class).getPricer().getId();
    }

    @CaseId(2784)
    @Test(description = "Получение правил номинальной стоимости доставки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "createNominalCostRule")
    public void getNominalCostRule() {
        final Response response = ShippingMethodsV1Request.NominalPricers.GET(shippingMethodId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PricersV1Response.class);
        List<PricerV1> pricers = response.as(PricersV1Response.class).getPricers();
        checkFieldIsNotEmpty(pricers, "правила номинальной стоимости доставки");
        compareTwoObjects(pricers.get(0).getId(), nominalCostPricerId);
    }

    @AfterClass(alwaysRun = true)
    public void clearData() {
        if(!EnvironmentProperties.Env.isProduction() && shippingMethodId != 0) {
            ShippingMethodsCleanDao.INSTANCE.deleteShippineMethods(shippingMethodId);
        }
    }
}
