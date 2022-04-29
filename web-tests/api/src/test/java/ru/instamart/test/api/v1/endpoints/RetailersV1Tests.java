package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.v1.RetailerSortTypeV1;
import ru.instamart.api.enums.v1.RulesAttributesTypeV1;
import ru.instamart.api.model.v1.EtaV1;
import ru.instamart.api.model.v1.ShippingPolicyV1;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.api.request.v1.ProxyV1Request;
import ru.instamart.api.request.v1.RetailerPositionV1Request;
import ru.instamart.api.request.v1.RetailersV1Request;
import ru.instamart.api.request.v1.ShippingPoliciesV1Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.v1.*;
import ru.instamart.api.response.v2.RetailerV2Response;
import ru.instamart.api.response.v2.RetailersV2Response;
import ru.instamart.jdbc.dao.stf.OperationalZonesDao;
import ru.instamart.jdbc.dao.stf.ShippingPoliciesDao;
import ru.instamart.jdbc.dao.stf.ShippingPolicyRulesDao;
import ru.instamart.jdbc.dao.stf.SpreeRetailersDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data_provider.JsonDataProvider;
import ru.instamart.kraken.data_provider.JsonProvider;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.compareShippingPolicies;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.api.request.v1.ShippingPoliciesV1Request.getShippingPolicies;

@Epic("ApiV1")
@Feature("Admin Web")
public class RetailersV1Tests extends RestBase {

    private List<RetailerV2> retailers;
    private RetailerV2 retailerFromResponse;
    private ShippingPolicyV1 shippingPolicy;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authApi();
    }

    @Story("Ритейлеры")
    @CaseId(122)
    @Test(description = "Контрактный тест списка ритейлеров для shopper-бэкенда",
            groups = {"api-instamart-regress"})
    public void getRetailers() {
        final Response response = RetailersV1Request.GET(new RetailersV1Request.RetailerParams());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        retailers = response.as(RetailersV2Response.class).getRetailers();
        compareTwoObjects(retailers.size(), SpreeRetailersDao.INSTANCE.getCount());
    }

    @Story("Ритейлеры")
    @CaseId(123)
    @Test(description = "Контрактный тест ритейлера для shopper-бэкенда",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "retailersSpree-parallel")
    public void getRetailer(RetailerV2 retailer) {
        final Response response = RetailersV1Request.GET(retailer.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailerV2Response.class);
    }

    @Story("Ритейлеры")
    @CaseId(124)
    @Test(description = "Контрактный тест списка штрихкодов у ритейлера для shopper-бэкенда",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "retailersSpree-parallel")
    public void getRetailerEans(RetailerV2 retailer) {
        final Response response = RetailersV1Request.Eans.GET(retailer.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, EansV1Response.class);
    }

    @Story("Ритейлеры")
    @CaseId(1273)
    @Test(description = "Получение списка ритейлеров по региону",
            groups = {"api-instamart-smoke"})
    public void getRetailersByOperationalZones() {
        Long operationalZoneId = OperationalZonesDao.INSTANCE.getIdByName("Москва");
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .operationalZoneId(operationalZoneId)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        List<RetailerV2> retailersFromResponse = response.as(RetailersV2Response.class).getRetailers();
        int countFromDb = SpreeRetailersDao.INSTANCE.getCountByOperationalZoneId(operationalZoneId);
        compareTwoObjects(countFromDb, retailersFromResponse.size());
    }

    @Story("Ритейлеры")
    @CaseIDs(value = {@CaseId(1277), @CaseId(1284), @CaseId(1288), @CaseId(1296)})
    @Test(description = "Получение списка ритейлеров с несуществующими параметрами",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProvider = "incorrectRetailerParams",
            dataProviderClass = RestDataProvider.class)
    public void getRetailersByNonExistingOperationalZones(RetailersV1Request.RetailerParams retailerParams) {
        final Response response = RetailersV1Request.GET(retailerParams);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        List<RetailerV2> retailersFromResponse = response.as(RetailersV2Response.class).getRetailers();
        Assert.assertTrue(retailersFromResponse.isEmpty(), "Пришли ретейлеры");
    }

    @Story("Ритейлеры")
    @CaseIDs(value = {@CaseId(1278), @CaseId(1279), @CaseId(1280), @CaseId(1281), @CaseId(1282), @CaseId(1283)})
    @Test(description = "Получение списка ритейлеров по имени",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProvider = "retailerNameData",
            dataProviderClass = RestDataProvider.class)
    public void getRetailersByName(String retailerName) {
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .nameCont(retailerName)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        List<RetailerV2> retailersFromResponse = response.as(RetailersV2Response.class).getRetailers();
        retailersFromResponse.forEach(retailer -> assertTrue(retailer.getName().toLowerCase().contains(retailerName.toLowerCase()), "Пришло неверное имя"));
    }

    @Story("Ритейлеры")
    @CaseId(1285)
    @Test(description = "Получение списка ритейлеров по имени (пробел)",
            groups = {"api-instamart-regress"})
    public void getRetailersBySpaceInName() {
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .nameCont(" ")
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        List<RetailerV2> retailersFromResponse = response.as(RetailersV2Response.class).getRetailers();
        compareTwoObjects(retailersFromResponse.size(), SpreeRetailersDao.INSTANCE.getCount());
    }

    @Story("Ритейлеры")
    @CaseId(1286)
    @Test(description = "Получение ритейлера по id",
            groups = {"api-instamart-regress"})
    public void getRetailersById() {
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .retailerId(SpreeRetailersDao.INSTANCE.getIdBySlug("auchan"))
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        List<RetailerV2> retailersFromResponse = response.as(RetailersV2Response.class).getRetailers();
        compareTwoObjects(retailersFromResponse.size(), 1);
        compareTwoObjects(retailersFromResponse.get(0).getName(), "Ашан");
    }

    @Story("Ритейлеры")
    @CaseId(1287)
    @Test(description = "Получение ритейлеров по нескольким id",
            groups = {"api-instamart-regress"})
    public void getRetailersByIds() {
        final Response response = RetailersV1Request.GET(Stream.of(SpreeRetailersDao.INSTANCE.getIdBySlug("auchan"),
                SpreeRetailersDao.INSTANCE.getIdBySlug("metro")).collect(Collectors.toList()));
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        List<RetailerV2> retailersFromResponse = response.as(RetailersV2Response.class).getRetailers();
        compareTwoObjects(retailersFromResponse.size(), 2);
        List<String> retailersNames = Stream.of(retailersFromResponse.get(0).getName(), retailersFromResponse.get(1).getName()).collect(Collectors.toList());
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(retailersNames.contains("Ашан"), "Не вернулся ретейлер Ашан");
        softAssert.assertTrue(retailersNames.contains("METRO"), "Не вернулся ретейлер METRO");
        softAssert.assertAll();
    }

    @Story("Ритейлеры")
    @CaseId(1289)
    @Test(description = "Получение доступных ритейлеров",
            groups = {"api-instamart-regress"})
    public void getActiveRetailers() {
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .active(true)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        List<RetailerV2> retailersFromResponse = response.as(RetailersV2Response.class).getRetailers();
        int retailersFromDb = SpreeRetailersDao.INSTANCE.getCountByAccessibility();
        compareTwoObjects(retailersFromResponse.size(), retailersFromDb);
    }

    @Story("Ритейлеры")
    @CaseId(1290)
    @Test(description = "Получение недоступных ритейлеров",
            groups = {"api-instamart-regress"})
    public void getInactiveRetailers() {
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .active(false)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        List<RetailerV2> retailersFromResponse = response.as(RetailersV2Response.class).getRetailers();
        int retailersFromDb = SpreeRetailersDao.INSTANCE.getCount() - SpreeRetailersDao.INSTANCE.getCountByAccessibility();
        compareTwoObjects(retailersFromResponse.size(), retailersFromDb);
    }

    @Story("Ритейлеры")
    @CaseId(1291)
    @Test(description = "Получение ритейлеров, отсортированных по имени в возрастающем порядке",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "getRetailers")
    public void getRetailersSortedByNameAsc() {
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .sorts(RetailerSortTypeV1.NAME_ASC.getValue())
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        List<RetailerV2> retailersFromResponse = response.as(RetailersV2Response.class).getRetailers();
        List<RetailerV2> sortedRetailers = retailers.stream().sorted(Comparator.comparing(RetailerV2::getName, String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toList());
        compareTwoObjects(retailersFromResponse, sortedRetailers);
    }

    @Story("Ритейлеры")
    @CaseId(1292)
    @Test(description = "Получение ритейлеров, отсортированных по имени в убывающем порядке",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "getRetailers")
    public void getRetailersSortedByNameDesc() {
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .sorts(RetailerSortTypeV1.NAME_DESC.getValue())
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        List<RetailerV2> retailersFromResponse = response.as(RetailersV2Response.class).getRetailers();
        List<RetailerV2> sortedRetailers = retailers.stream().sorted(Comparator.comparing(RetailerV2::getName, String.CASE_INSENSITIVE_ORDER).reversed()).collect(Collectors.toList());
        compareTwoObjects(retailersFromResponse, sortedRetailers);
    }

    @Story("Ритейлеры")
    @CaseId(1293)
    @Test(description = "Получение ритейлеров, отсортированных по дате создания в возрастающем порядке",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "getRetailers")
    public void getRetailersSortedByCreatedAtAsc() {
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .sorts(RetailerSortTypeV1.CREATED_AT_ASC.getValue())
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        List<RetailerV2> retailersFromResponse = response.as(RetailersV2Response.class).getRetailers();
        List<RetailerV2> sortedRetailers = retailers.stream().sorted(Comparator.comparing(RetailerV2::getCreatedAt)).collect(Collectors.toList());
        compareTwoObjects(retailersFromResponse, sortedRetailers);
    }

    @Story("Ритейлеры")
    @CaseId(1294)
    @Test(description = "Получение ритейлеров, отсортированных по дате создания в убывающем порядке",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "getRetailers")
    public void getRetailersSortedByCreatedAtDesc() {
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .sorts(RetailerSortTypeV1.CREATED_AT_DESC.getValue())
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        List<RetailerV2> retailersFromResponse = response.as(RetailersV2Response.class).getRetailers();
        List<RetailerV2> sortedRetailers = retailers.stream().sorted(Comparator.comparing(RetailerV2::getCreatedAt).reversed()).collect(Collectors.toList());
        compareTwoObjects(retailersFromResponse, sortedRetailers);
    }

    @Story("Ритейлеры")
    @CaseId(1295)
    @Test(description = "Получение ритейлеров с пагинацией",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getRetailersWithPagination() {
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .page(2)
                .perPage(3)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        RetailersV2Response retailersFromResponse = response.as(RetailersV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(retailersFromResponse.getRetailers().size(), 3, softAssert);
        compareTwoObjects(retailersFromResponse.getMeta().getCurrentPage(), 2, softAssert);
        compareTwoObjects(retailersFromResponse.getMeta().getPerPage(), 3, softAssert);
        softAssert.assertAll();
    }

    @Story("Ритейлеры")
    @CaseId(1297)
    @Test(description = "Получение ритейлеров с несколькими параметрами сортировки и фильтрации",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getRetailersWithAFewParams() {
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .page(1)
                .perPage(10)
                .sorts(RetailerSortTypeV1.NAME_DESC.getValue())
                .active(true)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        RetailersV2Response retailersFromResponse = response.as(RetailersV2Response.class);
        List<RetailerV2> retailersList = retailersFromResponse.getRetailers();
        List<RetailerV2> sortedRetailersList = retailersList.stream().sorted(Comparator.comparing(RetailerV2::getName, String.CASE_INSENSITIVE_ORDER).reversed()).collect(Collectors.toList());
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(retailersList, sortedRetailersList);
        compareTwoObjects(retailersFromResponse.getMeta().getCurrentPage(), 1, softAssert);
        compareTwoObjects(retailersFromResponse.getMeta().getPerPage(), 10, softAssert);
        retailersList.forEach(r -> compareTwoObjects(r.getAvailable(), true, softAssert));
        softAssert.assertAll();
    }

    @Story("Ритейлеры")
    @CaseId(1306)
    @Test(description = "Изменение позиций ритейлеров",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "getRetailers")
    public void changeRetailerPositions() {
        final Response response = RetailerPositionV1Request.PUT(retailers.get(1).getId(), retailers.get(0).getId());
        checkStatusCode200(response);

        final Response responseForCheck = RetailersV1Request.GET(new RetailersV1Request.RetailerParams());
        checkStatusCode200(responseForCheck);
        List<RetailerV2> changedRetailers = responseForCheck.as(RetailersV2Response.class).getRetailers();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(changedRetailers.get(0).getId(), retailers.get(1).getId(), softAssert);
        compareTwoObjects(changedRetailers.get(1).getId(), retailers.get(0).getId(), softAssert);
        softAssert.assertAll();
    }

    @Story("Ритейлеры")
    @CaseId(1307)
    @Test(description = "Изменение позиций ритейлеров с несуществующими id ритейеров",
            groups = {"api-instamart-regress"})
    public void changeRetailerPositionsForNonExistingRetailer() {
        final Response response = RetailerPositionV1Request.PUT(0, 111111);
        checkStatusCode422(response);
        ErrorResponse error = response.as(ErrorResponse.class);
        compareTwoObjects("Invalid retailer ID's [0, 111111]", error.getErrors().getBase());
    }

    @Story("Ритейлеры")
    @CaseId(1310)
    @Test(description = "Создание ритейлера",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "getRetailersWithPagination")
    public void createRetailer() {
        RetailersV1Request.Retailer retailer = RetailersV1Request.getRetailer();
        final Response response = RetailersV1Request.POST(retailer);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailerV2Response.class);
        retailerFromResponse = response.as(RetailerV2Response.class).getRetailer();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(retailerFromResponse.getName(), retailer.getName(), softAssert);
        compareTwoObjects(retailerFromResponse.getSlug(), retailer.getSlug(), softAssert);
        compareTwoObjects(retailerFromResponse.getPhone(), "+7" + retailer.getPhone(), softAssert);
        compareTwoObjects(retailerFromResponse.getShortName(), retailer.getShortName(), softAssert);
        compareTwoObjects(retailerFromResponse.getDescription(), retailer.getDescription(), softAssert);
        compareTwoObjects(retailerFromResponse.getAppearance().getBackgroundColor(), retailer.getBackgroundColor(), softAssert);
        compareTwoObjects(retailerFromResponse.getAppearance().getImageColor(), retailer.getImageColor(), softAssert);
        compareTwoObjects(retailerFromResponse.getAppearance().getBlackTheme(), retailer.getBlackTheme(), softAssert);
        softAssert.assertTrue(retailerFromResponse.getAppearance().getLogoImage().contains("logo.png"), "Логотип не загрузился");
        softAssert.assertTrue(retailerFromResponse.getAppearance().getMiniLogoImage().contains("logo.png"), "Мини-логотип не загрузился");
        softAssert.assertAll();
    }

    @Story("Ритейлеры")
    @JsonDataProvider(path = "data/json_v1/api_v1_negative_retailer_data.json", type = RestDataProvider.RetailerV1TestDataRoot.class)
    @CaseIDs(value = {@CaseId(1311), @CaseId(1312), @CaseId(1313), @CaseId(1314), @CaseId(1315), @CaseId(1316),
            @CaseId(1317), @CaseId(1318), @CaseId(1319), @CaseId(1320), @CaseId(1321), @CaseId(1322), @CaseId(1323)})
    @Test(description = "Создание ритейлера с невалидными параметрами",
            groups = {"api-instamart-regress"},
            dataProvider = "jsonWithoutParallel",
            dataProviderClass = JsonProvider.class)
    public void createRetailerWithInvalidParams(RestDataProvider.RetailerV1TestData testData) {
        final Response response = RetailersV1Request.POST(testData.getRetailer());
        checkStatusCode422(response);
        Assert.assertTrue(response.asString().contains(testData.getErrorMessage()), "Текст ошибки неверный");
    }

    @Story("Ритейлеры")
    @CaseId(1325)
    @Test(description = "Редактирование ритейлера",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "createRetailer")
    public void editRetailer() {
        RetailersV1Request.Retailer retailer = RetailersV1Request.Retailer.builder()
                .shortName("autotest-" + Generate.literalString(6))
                .name("Autotest-" + Generate.literalString(6))
                .backgroundColor("#EBC7DF")
                .build();
        final Response response = RetailersV1Request.PUT(retailer, retailerFromResponse.getSlug());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailerV2Response.class);
        RetailerV2 updatedRetailer = response.as(RetailerV2Response.class).getRetailer();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(updatedRetailer.getId(), retailerFromResponse.getId(), softAssert);
        compareTwoObjects(updatedRetailer.getName(), retailer.getName(), softAssert);
        compareTwoObjects(updatedRetailer.getSlug(), retailerFromResponse.getSlug(), softAssert);
        compareTwoObjects(updatedRetailer.getPhone(), retailerFromResponse.getPhone(), softAssert);
        compareTwoObjects(updatedRetailer.getShortName(), retailer.getShortName(), softAssert);
        compareTwoObjects(updatedRetailer.getDescription(), retailerFromResponse.getDescription(), softAssert);
        compareTwoObjects(updatedRetailer.getAppearance().getBackgroundColor(), retailer.getBackgroundColor(), softAssert);
        compareTwoObjects(updatedRetailer.getAppearance().getImageColor(), retailerFromResponse.getAppearance().getImageColor(), softAssert);
        compareTwoObjects(updatedRetailer.getAppearance().getBlackTheme(), retailerFromResponse.getAppearance().getBlackTheme(), softAssert);
        softAssert.assertAll();
    }

    @Story("Ритейлеры")
    @JsonDataProvider(path = "data/json_v1/api_v1_negative_edit_retailer_data.json", type = RestDataProvider.RetailerV1TestDataRoot.class)
    @CaseIDs(value = {@CaseId(1326), @CaseId(1327)})
    @Test(description = "Редактирование ритейлера с невалидными параметрами",
            groups = {"api-instamart-regress"},
            dataProvider = "jsonWithoutParallel",
            dataProviderClass = JsonProvider.class,
            dependsOnMethods = "createRetailer")
    public void editRetailerWithInvalidParams(RestDataProvider.RetailerV1TestData testData) {
        final Response response = RetailersV1Request.PUT(testData.getRetailer(), retailerFromResponse.getSlug());
        checkStatusCode422(response);
        Assert.assertTrue(response.asString().contains(testData.getErrorMessage()), "Текст ошибки неверный");
    }

    @Story("Ритейлеры - ETA")
    @CaseId(1330)
    @Test(description = "Редактирование ETA для ритейлера",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "createRetailer")
    public void editEta() {
        final Response response = ProxyV1Request.PUT(retailerFromResponse.getId());
        checkStatusCode200(response);
    }

    @Story("Ритейлеры - ETA")
    @CaseId(1331)
    @Test(description = "Получение ETA для ритейлера",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "editEta")
    public void getEta() {
        final Response response = ProxyV1Request.GET(retailerFromResponse.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, EtaV1.class);
        EtaV1 eta = response.as(EtaV1.class);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(eta.getCourierSpeed(), 197, softAssert);
        compareTwoObjects(eta.getDeliveryTimeSigma(), 3840, softAssert);
        compareTwoObjects(eta.getWindow(), 1020, softAssert);
        softAssert.assertAll();
    }

    @Story("Ритейлеры - Слоты доставки")
    @CaseId(1347)
    @Test(description = "Создание правил доступности слотов доставки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "createRetailer")
    public void createShippingPolicies() {
        ShippingPoliciesV1Request.ShippingPolicies shippingPolicies = getShippingPolicies(retailerFromResponse.getId());
        final Response response = ShippingPoliciesV1Request.POST(shippingPolicies);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingPolicyV1Response.class);
        shippingPolicy = response.as(ShippingPolicyV1Response.class).getShippingPolicy();
        compareShippingPolicies(shippingPolicies, shippingPolicy);
    }

    @Story("Ритейлеры - Слоты доставки")
    @CaseId(1348)
    @Test(description = "Получение правила доступности слотов доставки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "createShippingPolicies")
    public void getShippingPolicy() {
        final Response response = ShippingPoliciesV1Request.GET(shippingPolicy.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingPolicyV1Response.class);
        ShippingPolicyV1 shippingPolicyFromResponse = response.as(ShippingPolicyV1Response.class).getShippingPolicy();
        compareTwoObjects(shippingPolicyFromResponse, shippingPolicy);
    }

    @Story("Ритейлеры - Слоты доставки")
    @CaseId(1349)
    @Test(description = "Получение правил доступности слотов доставки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "createShippingPolicies")
    public void getAllShippingPolicies() {
        final Response response = RetailersV1Request.ShippingPolicies.GET(retailerFromResponse.getSlug());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingPoliciesV1Response.class);
        List<ShippingPolicyV1> shippingPoliciesFromResponse = response.as(ShippingPoliciesV1Response.class).getShippingPolicies();
        compareTwoObjects(1, shippingPoliciesFromResponse.size());
        compareTwoObjects(shippingPolicy, shippingPoliciesFromResponse.get(0));
    }

    @Story("Ритейлеры - Слоты доставки")
    @CaseId(1350)
    @Test(description = "Получение несуществующего правила доступности слотов доставки",
            groups = {"api-instamart-regress"})
    public void getNonExistingShippingPolicy() {
        final Response response = ShippingPoliciesV1Request.GET(0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Story("Ритейлеры - Слоты доставки")
    @CaseId(1351)
    @Test(description = "Получение правил доступности слотов доставки для несуществующего ритейлера",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "createShippingPolicies")
    public void getAllShippingPoliciesForNonExistingRetailer() {
        final Response response = RetailersV1Request.ShippingPolicies.GET("fgdhgshsfghsgh");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Story("Ритейлеры - Слоты доставки")
    @CaseIDs(value = {@CaseId(2345), @CaseId(2349), @CaseId(2350), @CaseId(2351), @CaseId(2352)})
    @Test(description = "Создание правила доступности слотов доставки",
            groups = {"api-instamart-regress"},
            dataProvider = "shippingPolicyRulesData",
            dataProviderClass = RestDataProvider.class,
            dependsOnMethods = {"createShippingPolicies", "getShippingPolicy", "getAllShippingPolicies"})
    public void createShippingPolicyRule(ShippingPoliciesV1Request.ShippingPolicyRules shippingPolicyRules) {
        final Response response = ShippingPoliciesV1Request.ShippingPoliciesRules.POST(shippingPolicy.getId(), shippingPolicyRules);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingPolicyV1Response.class);
    }

    @Story("Ритейлеры - Слоты доставки")
    @CaseId(2356)
    @Test(description = "Создание правила доступности слотов доставки для несуществующего правила",
            groups = {"api-instamart-regress"},
            dependsOnMethods = {"createShippingPolicies", "getShippingPolicy", "getAllShippingPolicies"})
    public void createShippingPolicyRuleForNonExistentPolicy() {
        ShippingPoliciesV1Request.ShippingPolicyRules shippingPolicyRules = ShippingPoliciesV1Request.ShippingPolicyRules.builder()
                .ruleType(RulesAttributesTypeV1.DELIVERY_WINDOW_NUMBER.getValue())
                .preferences(ShippingPoliciesV1Request.RulesAttribute.builder()
                        .preferredNumber(RandomUtils.nextInt(1, 10))
                        .build())
                .build();
        final Response response = ShippingPoliciesV1Request.ShippingPoliciesRules.POST(0L, shippingPolicyRules);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Story("Ритейлеры - Слоты доставки")
    @CaseId(1352)
    @Test(description = "Удаление правила доступности слотов доставки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = {"createShippingPolicyRule", "createShippingPolicyRuleForNonExistentPolicy"})
    public void deleteShippingPolicyRule() {
        Long shippingPolicyRuleId = shippingPolicy.getRules().get(0).getId();
        final Response response = ShippingPoliciesV1Request.ShippingPoliciesRules.DELETE(shippingPolicy.getId(), shippingPolicyRuleId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingPolicyV1Response.class);
        ShippingPolicyV1 updatedShippingPolicy = response.as(ShippingPolicyV1Response.class).getShippingPolicy();
        compareTwoObjects(updatedShippingPolicy.getRules().size(), 9);
        Assert.assertFalse(ShippingPolicyRulesDao.INSTANCE.findById(shippingPolicyRuleId).isPresent());
    }

    @Story("Ритейлеры - Слоты доставки")
    @CaseId(1353)
    @Test(description = "Удаление несуществующего правила доступности слотов доставки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = {"createShippingPolicyRule", "createShippingPolicyRuleForNonExistentPolicy"})
    public void deleteNonExistingShippingPolicyRule() {
        final Response response = ShippingPoliciesV1Request.ShippingPoliciesRules.DELETE(shippingPolicy.getId(), 0L);
        checkStatusCode404(response);
    }

    @Story("Ритейлеры - Слоты доставки")
    @CaseId(1354)
    @Test(description = "Редактирование правил доступности слотов доставки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = {"createShippingPolicies", "getShippingPolicy", "getAllShippingPolicies", "deleteShippingPolicyRule"})
    public void editShippingPolicies() {
        ShippingPolicyRulesDao.INSTANCE.deleteRulesByShippingPolicyId(shippingPolicy.getId());
        ShippingPoliciesV1Request.ShippingPolicies shippingPolicies = getShippingPolicies(retailerFromResponse.getId());
        final Response response = ShippingPoliciesV1Request.PUT(shippingPolicies, shippingPolicy.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingPolicyV1Response.class);
        ShippingPolicyV1 updatedShippingPolicy = response.as(ShippingPolicyV1Response.class).getShippingPolicy();
        compareShippingPolicies(shippingPolicies, updatedShippingPolicy);
    }

    @Story("Ритейлеры - Слоты доставки")
    @CaseId(1355)
    @Test(description = "Редактирование несуществующих правил доступности слотов доставки",
            groups = {"api-instamart-regress"})
    public void editNonExistingShippingPolicies() {
        ShippingPoliciesV1Request.ShippingPolicies shippingPolicies = getShippingPolicies(0);
        final Response response = ShippingPoliciesV1Request.PUT(shippingPolicies, 0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Story("Ритейлеры - Слоты доставки")
    @CaseId(1356)
    @Test(description = "Удаление правил доступности слотов доставки",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "editShippingPolicies")
    public void deleteShippingPolicies() {
        final Response response = ShippingPoliciesV1Request.DELETE(shippingPolicy.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingPolicyV1Response.class);
        ShippingPolicyV1 deletedShippingPolicy = response.as(ShippingPolicyV1Response.class).getShippingPolicy();
        compareTwoObjects(deletedShippingPolicy.getRules().size(), 0);
        Assert.assertFalse(ShippingPoliciesDao.INSTANCE.findById(shippingPolicy.getId()).isPresent());
    }

    @Story("Ритейлеры - Слоты доставки")
    @CaseId(1357)
    @Test(description = "Удаление правил доступности несуществующих слотов доставки",
            groups = {"api-instamart-regress"})
    public void deleteNonExistingShippingPolicies() {
        final Response response = ShippingPoliciesV1Request.DELETE(0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Story("Ритейлеры - Соглашения")
    @CaseId(2339)
    @Test(description = "Получение списка соглашений ритейлеров",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getRetailerAgreements() {
        final Response response = RetailersV1Request.RetailerAgreements.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailerAgreementsV1Response.class);
    }

    @Story("Ритейлеры - Соглашения")
    @CaseId(2340)
    @Test(description = "Получение списка типов контрактов ритейлеров",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getRetailerContractTypes() {
        final Response response = RetailersV1Request.RetailerContractTypes.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ContractTypesV1Response.class);
    }


    @AfterClass(alwaysRun = true)
    public void clearData() {
        if(!EnvironmentProperties.SERVER.equals("production") && Objects.nonNull(retailerFromResponse)) {
            SpreeRetailersDao.INSTANCE.delete(Long.valueOf(retailerFromResponse.getId()));
        }
    }
}
