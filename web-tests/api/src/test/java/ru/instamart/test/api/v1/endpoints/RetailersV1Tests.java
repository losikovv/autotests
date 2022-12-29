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
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import io.qameta.allure.TmsLinks;
import io.qameta.allure.TmsLink;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
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
    @TmsLink("122")
    @Test(description = "Контрактный тест списка ритейлеров для shopper-бэкенда",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"})
    public void getRetailers() {
        final Response response = RetailersV1Request.GET(new RetailersV1Request.RetailerParams());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        retailers = response.as(RetailersV2Response.class).getRetailers();
        if (!EnvironmentProperties.Env.isProduction()) {
            compareTwoObjects(retailers.size(), SpreeRetailersDao.INSTANCE.getCount());
        }
    }

    @Story("Ритейлеры")
    @TmsLink("123")
    @Test(description = "Контрактный тест ритейлера для shopper-бэкенда",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "retailersSpree-parallel")
    public void getRetailer(RetailerV2 retailer) {
        final Response response = RetailersV1Request.GET(retailer.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailerV2Response.class);
    }

    @Story("Ритейлеры")
    @TmsLink("124")
    @Test(description = "Контрактный тест списка штрихкодов у ритейлера для shopper-бэкенда",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "retailersSpree-parallel")
    public void getRetailerEans(RetailerV2 retailer) {
        final Response response = RetailersV1Request.Eans.GET(retailer.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, EansV1Response.class);
    }

    @Story("Ритейлеры")
    @TmsLink("1273")
    @Test(description = "Получение списка ритейлеров по региону",
            groups = {"api-instamart-smoke", "api-v1"})
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
    @TmsLinks(value = {@TmsLink("1277"), @TmsLink("1284"), @TmsLink("1288"), @TmsLink("1296")})
    @Test(description = "Получение списка ритейлеров с несуществующими параметрами",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"},
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
    @TmsLinks(value = {@TmsLink("1278"), @TmsLink("1279"), @TmsLink("1280"), @TmsLink("1281"), @TmsLink("1282"), @TmsLink("1283")})
    @Test(description = "Получение списка ритейлеров по имени",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"},
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
    @TmsLink("1285")
    @Test(description = "Получение списка ритейлеров по имени (пробел)",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
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
    @TmsLink("1286")
    @Test(description = "Получение ритейлера по id",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
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
    @TmsLink("1287")
    @Test(description = "Получение ритейлеров по нескольким id",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
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

    @Skip(onServer = Server.STAGING)
    @Story("Ритейлеры")
    @TmsLink("1289")
    @Test(description = "Получение доступных ритейлеров",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"})
    public void getActiveRetailers() {
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .active(true)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        if (!EnvironmentProperties.Env.isProduction()) {
            List<RetailerV2> retailersFromResponse = response.as(RetailersV2Response.class).getRetailers();
            int retailersFromDb = SpreeRetailersDao.INSTANCE.getCountByAccessibility();
            compareTwoObjects(retailersFromResponse.size(), retailersFromDb);
        }
    }

    @Skip(onServer = Server.STAGING)
    @Story("Ритейлеры")
    @TmsLink("1290")
    @Test(description = "Получение недоступных ритейлеров",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"})
    public void getInactiveRetailers() {
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .active(false)
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        if (!EnvironmentProperties.Env.isProduction()) {
            List<RetailerV2> retailersFromResponse = response.as(RetailersV2Response.class).getRetailers();
            int retailersFromDb = SpreeRetailersDao.INSTANCE.getCount() - SpreeRetailersDao.INSTANCE.getCountByAccessibility();
            compareTwoObjects(retailersFromResponse.size(), retailersFromDb);
        }
    }

    @Story("Ритейлеры")
    @TmsLink("1291")
    @Test(description = "Получение ритейлеров, отсортированных по имени в возрастающем порядке",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"},
            dependsOnMethods = "getRetailers")
    public void getRetailersSortedByNameAsc() {
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .sorts(RetailerSortTypeV1.NAME_ASC.getValue())
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        List<RetailerV2> retailersFromResponse = response.as(RetailersV2Response.class).getRetailers().stream().filter(r -> !r.getName().contains("_")).filter(r -> !r.getName().contains("ё")).collect(Collectors.toList());
        List<RetailerV2> sortedRetailers = retailers.stream().filter(r -> !r.getName().contains("_")).filter(r -> !r.getName().contains("ё"))
                .sorted(Comparator.comparing(RetailerV2::getName, String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toList());
        for(int i = 0; i < sortedRetailers.size(); i++) {
          Assert.assertEquals(retailersFromResponse.get(i), sortedRetailers.get(i), "Ретейлеры не совпадают");
        }
        compareTwoObjects(retailersFromResponse, sortedRetailers);
    }

    @Story("Ритейлеры")
    @TmsLink("1292")
    @Test(description = "Получение ритейлеров, отсортированных по имени в убывающем порядке",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"},
            dependsOnMethods = "getRetailers")
    public void getRetailersSortedByNameDesc() {
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .sorts(RetailerSortTypeV1.NAME_DESC.getValue())
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        List<RetailerV2> retailersFromResponse = response.as(RetailersV2Response.class).getRetailers().stream().filter(r -> !r.getName().contains("_")).filter(r -> !r.getName().contains("ё")).collect(Collectors.toList());;
        List<RetailerV2> sortedRetailers = retailers.stream().filter(r -> !r.getName().contains("_")).filter(r -> !r.getName().contains("ё"))
                .sorted(Comparator.comparing(RetailerV2::getName,String.CASE_INSENSITIVE_ORDER).reversed()).collect(Collectors.toList());
        for(int i = 0; i < sortedRetailers.size(); i++) {
            Assert.assertEquals(retailersFromResponse.get(i), sortedRetailers.get(i), "Ретейлеры не совпадают");
        }
        compareTwoObjects(retailersFromResponse, sortedRetailers);
    }

    @Story("Ритейлеры")
    @TmsLink("1293")
    @Test(description = "Получение ритейлеров, отсортированных по дате создания в возрастающем порядке",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"},
            dependsOnMethods = "getRetailers")
    public void getRetailersSortedByCreatedAtAsc() {
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .sorts(RetailerSortTypeV1.CREATED_AT_ASC.getValue())
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        List<RetailerV2> retailersFromResponse = response.as(RetailersV2Response.class).getRetailers().stream().filter(r -> Objects.nonNull(r.getCreatedAt())).collect(Collectors.toList());
        List<RetailerV2> sortedRetailers = retailers.stream().filter(r -> Objects.nonNull(r.getCreatedAt())).sorted(Comparator.comparing(RetailerV2::getCreatedAt)).collect(Collectors.toList());
        compareTwoObjects(retailersFromResponse, sortedRetailers);
    }

    @Story("Ритейлеры")
    @TmsLink("1294")
    @Test(description = "Получение ритейлеров, отсортированных по дате создания в убывающем порядке",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"},
            dependsOnMethods = "getRetailers")
    public void getRetailersSortedByCreatedAtDesc() {
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .sorts(RetailerSortTypeV1.CREATED_AT_DESC.getValue())
                .build());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        List<RetailerV2> retailersFromResponse = response.as(RetailersV2Response.class).getRetailers().stream().filter(r -> Objects.nonNull(r.getCreatedAt())).collect(Collectors.toList());;
        List<RetailerV2> sortedRetailers = retailers.stream().filter(r -> Objects.nonNull(r.getCreatedAt())).sorted(Comparator.comparing(RetailerV2::getCreatedAt).reversed()).collect(Collectors.toList());
        compareTwoObjects(retailersFromResponse, sortedRetailers);
    }

    @Story("Ритейлеры")
    @TmsLink("1295")
    @Test(description = "Получение ритейлеров с пагинацией",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"})
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
    @TmsLink("1297")
    @Test(description = "Получение ритейлеров с несколькими параметрами сортировки и фильтрации",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"})
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
    @TmsLink("1306")
    @Test(description = "Изменение позиций ритейлеров",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
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
    @TmsLink("1307")
    @Test(description = "Изменение позиций ритейлеров с несуществующими id ритейеров",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void changeRetailerPositionsForNonExistingRetailer() {
        final Response response = RetailerPositionV1Request.PUT(0, 111111);
        checkStatusCode422(response);
        ErrorResponse error = response.as(ErrorResponse.class);
        compareTwoObjects("Invalid retailer ID's [0, 111111]", error.getErrors().getBase());
    }

    @Skip(onServer = Server.STAGING) //todo починить 405 "Создание ритейлеров в данном разделе отключено. Используйте раздел Онбординг."
    @Story("Ритейлеры")
    @TmsLink("1310")
    @Test(description = "Создание ритейлера",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
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

    @Skip(onServer = Server.STAGING) //todo починить 405 "Создание ритейлеров в данном разделе отключено. Используйте раздел Онбординг."
    @Story("Ритейлеры")
    @JsonDataProvider(path = "data/json_v1/api_v1_negative_retailer_data.json", type = RestDataProvider.RetailerV1TestDataRoot.class)
    @TmsLinks(value = {@TmsLink("1311"), @TmsLink("1312"), @TmsLink("1313"), @TmsLink("1314"), @TmsLink("1315"), @TmsLink("1316"),
            @TmsLink("1317"), @TmsLink("1318"), @TmsLink("1319"), @TmsLink("1320"), @TmsLink("1321"), @TmsLink("1322"), @TmsLink("1323")})
    @Test(description = "Создание ритейлера с невалидными параметрами",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
            dataProvider = "jsonWithoutParallel",
            dataProviderClass = JsonProvider.class)
    public void createRetailerWithInvalidParams(RestDataProvider.RetailerV1TestData testData) {
        final Response response = RetailersV1Request.POST(testData.getRetailer());
        checkStatusCode422(response);
        Assert.assertTrue(response.asString().contains(testData.getErrorMessage()), "Текст ошибки неверный");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Ритейлеры")
    @TmsLink("1325")
    @Test(description = "Редактирование ритейлера",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
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

    @Skip(onServer = Server.STAGING)
    @Story("Ритейлеры")
    @JsonDataProvider(path = "data/json_v1/api_v1_negative_edit_retailer_data.json", type = RestDataProvider.RetailerV1TestDataRoot.class)
    @TmsLinks(value = {@TmsLink("1326"), @TmsLink("1327")})
    @Test(description = "Редактирование ритейлера с невалидными параметрами",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
            dataProvider = "jsonWithoutParallel",
            dataProviderClass = JsonProvider.class,
            dependsOnMethods = "createRetailer")
    public void editRetailerWithInvalidParams(RestDataProvider.RetailerV1TestData testData) {
        final Response response = RetailersV1Request.PUT(testData.getRetailer(), retailerFromResponse.getSlug());
        checkStatusCode422(response);
        Assert.assertTrue(response.asString().contains(testData.getErrorMessage()), "Текст ошибки неверный");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Ритейлеры - ETA")
    @TmsLink("1330")
    @Test(description = "Редактирование ETA для ритейлера",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
            dependsOnMethods = "createRetailer")
    public void editEta() {
        final Response response = ProxyV1Request.PUT(retailerFromResponse.getId());
        checkStatusCode200(response);
    }

    @Skip(onServer = Server.STAGING)
    @Story("Ритейлеры - ETA")
    @TmsLink("1331")
    @Test(description = "Получение ETA для ритейлера",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
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

    @Skip(onServer = Server.STAGING)
    @Story("Ритейлеры - Слоты доставки")
    @TmsLink("1347")
    @Test(description = "Создание правил доступности слотов доставки",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
            dependsOnMethods = "createRetailer")
    public void createShippingPolicies() {
        ShippingPoliciesV1Request.ShippingPolicies shippingPolicies = getShippingPolicies(retailerFromResponse.getId());
        final Response response = ShippingPoliciesV1Request.POST(shippingPolicies);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingPolicyV1Response.class);
        shippingPolicy = response.as(ShippingPolicyV1Response.class).getShippingPolicy();
        compareShippingPolicies(shippingPolicies, shippingPolicy);
    }

    @Skip(onServer = Server.STAGING)
    @Story("Ритейлеры - Слоты доставки")
    @TmsLink("1348")
    @Test(description = "Получение правила доступности слотов доставки",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
            dependsOnMethods = "createShippingPolicies")
    public void getShippingPolicy() {
        final Response response = ShippingPoliciesV1Request.GET(shippingPolicy.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingPolicyV1Response.class);
        ShippingPolicyV1 shippingPolicyFromResponse = response.as(ShippingPolicyV1Response.class).getShippingPolicy();
        compareTwoObjects(shippingPolicyFromResponse, shippingPolicy);
    }

    @Skip(onServer = Server.STAGING)
    @Story("Ритейлеры - Слоты доставки")
    @TmsLink("1349")
    @Test(description = "Получение правил доступности слотов доставки",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
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
    @TmsLink("1350")
    @Test(description = "Получение несуществующего правила доступности слотов доставки",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"})
    public void getNonExistingShippingPolicy() {
        final Response response = ShippingPoliciesV1Request.GET(0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Ритейлеры - Слоты доставки")
    @TmsLink("1351")
    @Test(description = "Получение правил доступности слотов доставки для несуществующего ритейлера",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
            dependsOnMethods = "createShippingPolicies")
    public void getAllShippingPoliciesForNonExistingRetailer() {
        final Response response = RetailersV1Request.ShippingPolicies.GET("fgdhgshsfghsgh");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Ритейлеры - Слоты доставки")
    @TmsLinks(value = {@TmsLink("2345"), @TmsLink("2349"), @TmsLink("2350"), @TmsLink("2351"), @TmsLink("2352")})
    @Test(description = "Создание правила доступности слотов доставки",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
            dataProvider = "shippingPolicyRulesData",
            dataProviderClass = RestDataProvider.class,
            dependsOnMethods = {"createShippingPolicies", "getShippingPolicy", "getAllShippingPolicies"})
    public void createShippingPolicyRule(ShippingPoliciesV1Request.ShippingPolicyRules shippingPolicyRules) {
        final Response response = ShippingPoliciesV1Request.ShippingPoliciesRules.POST(shippingPolicy.getId(), shippingPolicyRules);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingPolicyV1Response.class);
    }

    @Skip(onServer = Server.STAGING)
    @Story("Ритейлеры - Слоты доставки")
    @TmsLink("2356")
    @Test(description = "Создание правила доступности слотов доставки для несуществующего правила",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
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

    @Skip(onServer = Server.STAGING)
    @Story("Ритейлеры - Слоты доставки")
    @TmsLink("1352")
    @Test(description = "Удаление правила доступности слотов доставки",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
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

    @Skip(onServer = Server.STAGING)
    @Story("Ритейлеры - Слоты доставки")
    @TmsLink("1353")
    @Test(description = "Удаление несуществующего правила доступности слотов доставки",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
            dependsOnMethods = {"createShippingPolicyRule", "createShippingPolicyRuleForNonExistentPolicy"})
    public void deleteNonExistingShippingPolicyRule() {
        final Response response = ShippingPoliciesV1Request.ShippingPoliciesRules.DELETE(shippingPolicy.getId(), 0L);
        checkStatusCode404(response);
    }

    @Skip(onServer = Server.STAGING)
    @Story("Ритейлеры - Слоты доставки")
    @TmsLink("1354")
    @Test(description = "Редактирование правил доступности слотов доставки",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
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
    @TmsLink("1355")
    @Test(description = "Редактирование несуществующих правил доступности слотов доставки",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void editNonExistingShippingPolicies() {
        ShippingPoliciesV1Request.ShippingPolicies shippingPolicies = getShippingPolicies(0);
        final Response response = ShippingPoliciesV1Request.PUT(shippingPolicies, 0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Skip(onServer = Server.STAGING)
    @Story("Ритейлеры - Слоты доставки")
    @TmsLink("1356")
    @Test(description = "Удаление правил доступности слотов доставки",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
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
    @TmsLink("1357")
    @Test(description = "Удаление правил доступности несуществующих слотов доставки",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void deleteNonExistingShippingPolicies() {
        final Response response = ShippingPoliciesV1Request.DELETE(0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Story("Ритейлеры - Соглашения")
    @TmsLink("2339")
    @Test(description = "Получение списка соглашений ритейлеров",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"})
    public void getRetailerAgreements() {
        final Response response = RetailersV1Request.RetailerAgreements.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailerAgreementsV1Response.class);
    }

    @Story("Ритейлеры - Соглашения")
    @TmsLink("2340")
    @Test(description = "Получение списка типов контрактов ритейлеров",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"})
    public void getRetailerContractTypes() {
        final Response response = RetailersV1Request.RetailerContractTypes.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ContractTypesV1Response.class);
    }


    @AfterClass(alwaysRun = true)
    public void clearData() {
        if(!EnvironmentProperties.Env.isProduction() && Objects.nonNull(retailerFromResponse)) {
            SpreeRetailersDao.INSTANCE.delete(Long.valueOf(retailerFromResponse.getId()));
        }
    }
}
