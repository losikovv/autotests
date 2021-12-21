package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v1.RetailerSortTypesV1;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v1.ProxyV1Request;
import ru.instamart.api.request.v1.RetailerPositionV1Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.model.v1.EtaV1;
import ru.instamart.jdbc.dao.OperationalZonesDao;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.data_provider.JsonDataProvider;
import ru.instamart.kraken.data_provider.JsonProvider;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.api.request.v1.RetailersV1Request;
import ru.instamart.api.response.v1.EansV1Response;
import ru.instamart.api.response.v2.RetailerV2Response;
import ru.instamart.api.response.v2.RetailersV2Response;
import ru.instamart.jdbc.dao.SpreeRetailersDao;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;
import static ru.instamart.api.dataprovider.RestDataProvider.getAvailableRetailersSpree;

@Epic("ApiV1")
@Feature("Admin Web")
public class RetailersV1Tests extends RestBase {

    private List<RetailerV2> retailers;
    private RetailerV2 retailerFromResponse;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.createSessionToken(SessionType.API_V1, UserManager.getDefaultAdminAllRoles());
    }

    @Story("Ритейлеры")
    @CaseId(122)
    @Test(description = "Контрактный тест списка ритейлеров для shopper-бэкенда",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getRetailers() {
        final Response response = RetailersV1Request.GET(new RetailersV1Request.RetailerParams());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
        retailers = response.as(RetailersV2Response.class).getRetailers();
        compareTwoObjects(retailers.size(), SpreeRetailersDao.INSTANCE.getCount());
    }

    @Test(groups = {"api-instamart-regress", "api-instamart-prod"})
    public void selfTestRetailersV1() {
        getAvailableRetailersSpree();
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
            groups = {"api-instamart-regress"})
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
            groups = {"api-instamart-regress"},
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
            groups = {"api-instamart-regress"},
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
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(retailersFromResponse.size(), 2, softAssert);
        compareTwoObjects(retailersFromResponse.get(0).getName(), "Ашан", softAssert);
        compareTwoObjects(retailersFromResponse.get(1).getName(), "METRO", softAssert);
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
                .sorts(RetailerSortTypesV1.NAME_ASC.getValue())
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
                .sorts(RetailerSortTypesV1.NAME_DESC.getValue())
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
                .sorts(RetailerSortTypesV1.CREATED_AT_ASC.getValue())
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
                .sorts(RetailerSortTypesV1.CREATED_AT_DESC.getValue())
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
            groups = {"api-instamart-regress"})
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
            groups = {"api-instamart-regress"})
    public void getRetailersWithAFewParams() {
        final Response response = RetailersV1Request.GET(RetailersV1Request.RetailerParams.builder()
                .page(1)
                .perPage(10)
                .sorts(RetailerSortTypesV1.NAME_DESC.getValue())
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

    @Story("Ритейлеры")
    @CaseId(1330)
    @Test(description = "Редактирование ETA для ритейлера",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "createRetailer")
    public void editEta() {
        final Response response = ProxyV1Request.PUT(retailerFromResponse.getId());
        checkStatusCode200(response);
    }

    @Story("Ритейлеры")
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

    @AfterClass(alwaysRun = true)
    public void clearData() {
        SpreeRetailersDao.INSTANCE.delete(Long.valueOf(retailerFromResponse.getId()));
    }
}
