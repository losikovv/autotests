package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.v1.FiltersFilesModeV1;
import ru.instamart.api.enums.v1.ImportStatusV1;
import ru.instamart.api.model.v1.ImportsFileV1;
import ru.instamart.api.model.v1.ProductsImagesArchiveV1;
import ru.instamart.api.request.admin.StoresAdminRequest;
import ru.instamart.api.request.v1.ImportsV1Request;
import ru.instamart.api.response.v1.imports.*;
import ru.instamart.jdbc.dao.stf.*;
import ru.instamart.jdbc.entity.stf.*;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.util.ThreadUtil;
import io.qameta.allure.TmsLinks;
import io.qameta.allure.TmsLink;

import java.util.List;
import java.util.Objects;

import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.request.admin.StoresAdminRequest.getStoreSelgrosMiklouhoMaclay;
import static ru.instamart.kraken.util.FileUtil.changeXlsFileSheetName;

@Epic("ApiV1")
@Feature("Импорты")
public class ImportsV1Tests extends RestBase {

    private Long offerId;
    private Integer storeId;
    private byte[] fileBytes;
    private String importKey;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.authApi();
    }

    @TmsLink("1903")
    @Story("Фильтры")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение информации о файлах импорта фильтров")
    public void getFilterFiles() {
        final Response response = ImportsV1Request.FilterFiles.GET(10000);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, FiltersFilesV1Response.class);

        List<ImportsFileV1> files = response.as(FiltersFilesV1Response.class).getFiltersFiles();
        int filesFromDbCount = ImportFilesDao.INSTANCE.getCount("FiltersFile");
        compareTwoObjects(files.size(), filesFromDbCount);
    }

    @TmsLink("1903")
    @Story("Фильтры")
    @Test(groups = {API_INSTAMART_PROD, "api-v1"},
            description = "Получение информации о файлах импорта фильтров")
    public void getFilterFilesPROD() {
        final Response response = ImportsV1Request.FilterFiles.GET(100);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, FiltersFilesV1Response.class);
    }

    @TmsLink("1904")
    @Story("Цены")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение информации о файлах импорта цен")
    public void getPricesFiles() {
        final Response response = ImportsV1Request.PricesFiles.GET(10000);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PricesFilesV1Response.class);

        List<ImportsFileV1> files = response.as(PricesFilesV1Response.class).getPricesFiles();
        int filesFromDbCount = ImportFilesDao.INSTANCE.getCount(":PricesFile");
        compareTwoObjects(files.size(), filesFromDbCount);
    }

    @TmsLink("1904")
    @Story("Цены")
    @Test(groups = {API_INSTAMART_PROD, "api-v1"},
            description = "Получение информации о файлах импорта цен")
    public void getPricesFilesPROD() {
        final Response response = ImportsV1Request.PricesFiles.GET(100);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PricesFilesV1Response.class);
    }

    @TmsLink("1905")
    @Story("Изображения продуктов")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение информации о файлах импорта изображений продуктов")
    public void getProductsImagesArchives() {
        final Response response = ImportsV1Request.ProductsImagesArchives.GET(10000);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsImagesArchivesV1Response.class);

        List<ProductsImagesArchiveV1> files = response.as(ProductsImagesArchivesV1Response.class).getProductsImagesArchives();
        int filesFromDbCount = ImportFilesDao.INSTANCE.getCount("ProductsImages");
        compareTwoObjects(files.size(), filesFromDbCount);
    }

    @TmsLink("1905")
    @Story("Изображения продуктов")
    @Test(groups = {API_INSTAMART_PROD, "api-v1"},
            description = "Получение информации о файлах импорта изображений продуктов")
    public void getProductsImagesArchivesPROD() {
        final Response response = ImportsV1Request.ProductsImagesArchives.GET(100);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsImagesArchivesV1Response.class);
    }

    @TmsLink("1906")
    @Story("Штрихкоды")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение информации о файлах импорта штрихкодов")
    public void getEansFiles() {
        final Response response = ImportsV1Request.EansFiles.GET(10000);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, EansFilesV1Response.class);

        List<ImportsFileV1> files = response.as(EansFilesV1Response.class).getEansFiles();
        int filesFromDbCount = ImportFilesDao.INSTANCE.getCount("EansFile");
        compareTwoObjects(files.size(), filesFromDbCount);
    }

    @TmsLink("1906")
    @Story("Штрихкоды")
    @Test(groups = {API_INSTAMART_PROD, "api-v1"},
            description = "Получение информации о файлах импорта штрихкодов")
    public void getEansFilesPROD() {
        final Response response = ImportsV1Request.EansFiles.GET(100);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, EansFilesV1Response.class);
    }

    @TmsLink("1907")
    @Story("Офферы")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение информации о файлах импорта офферов")
    public void getOffersFiles() {
        final Response response = ImportsV1Request.OffersFiles.GET(10000);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OffersFilesV1Response.class);

        List<ImportsFileV1> files = response.as(OffersFilesV1Response.class).getOffersFiles();
        int filesFromDbCount = ImportFilesDao.INSTANCE.getCount("OffersFile");
        compareTwoObjects(files.size(), filesFromDbCount);
    }

    @TmsLink("1907")
    @Story("Офферы")
    @Test(groups = {API_INSTAMART_PROD, "api-v1"},
            description = "Получение информации о файлах импорта офферов")
    public void getOffersFilesPROD() {
        final Response response = ImportsV1Request.OffersFiles.GET(100);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OffersFilesV1Response.class);
    }

    @TmsLink("1908")
    @Story("Продукты")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение информации о файлах импорта продуктов")
    public void getProductsFiles() {
        final Response response = ImportsV1Request.ProductsFiles.GET(10000);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsFilesV1Response.class);

        List<ImportsFileV1> files = response.as(ProductsFilesV1Response.class).getProductsFiles();
        int filesFromDbCount = ImportFilesDao.INSTANCE.getCount("ProductsFile");
        compareTwoObjects(files.size(), filesFromDbCount);
    }

    @TmsLink("1908")
    @Story("Продукты")
    @Test(groups = {API_INSTAMART_PROD, "api-v1"},
            description = "Получение информации о файлах импорта продуктов")
    public void getProductsFilesPROD() {
        final Response response = ImportsV1Request.ProductsFiles.GET(100);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsFilesV1Response.class);
    }

    @TmsLink("1909")
    @Story("Стоки")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение информации о файлах импорта стоков")
    public void getStocksFiles() {
        final Response response = ImportsV1Request.OffersStocksFiles.GET(10000);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, StocksFilesV1Response.class);

        List<ImportsFileV1> files = response.as(StocksFilesV1Response.class).getOffersStocksFiles();
        int filesFromDbCount = ImportFilesDao.INSTANCE.getCount("StocksFile");
        compareTwoObjects(files.size(), filesFromDbCount);
    }

    @TmsLink("1909")
    @Story("Стоки")
    @Test(groups = {API_INSTAMART_PROD, "api-v1"},
            description = "Получение информации о файлах импорта стоков")
    public void getStocksFilesPROD() {
        final Response response = ImportsV1Request.OffersStocksFiles.GET(100);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, StocksFilesV1Response.class);
    }

    @TmsLink("1910")
    @Story("Мастер каталог")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение информации о файлах импорта мастер каталога")
    public void getMasterCategoriesFiles() {
        final Response response = ImportsV1Request.MasterCategoriesFiles.GET(10000);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MasterCategoriesFilesV1Response.class);

        List<ImportsFileV1> files = response.as(MasterCategoriesFilesV1Response.class).getMasterCategoriesFiles();
        int filesFromDbCount = ImportFilesDao.INSTANCE.getCount("MasterCategoriesFile");
        compareTwoObjects(files.size(), filesFromDbCount);
    }

    @TmsLink("1910")
    @Story("Мастер каталог")
    @Test(groups = {API_INSTAMART_PROD, "api-v1"},
            description = "Получение информации о файлах импорта мастер каталога")
    public void getMasterCategoriesFilesPROD() {
        final Response response = ImportsV1Request.MasterCategoriesFiles.GET(100);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MasterCategoriesFilesV1Response.class);
    }

    @TmsLink("1911")
    @Story("Мастер каталог")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение информации о файлах импорта аттрибутов мастер каталога")
    public void getMasterCategoryAttributesFiles() {
        final Response response = ImportsV1Request.MasterCategoryAttributesFiles.GET(10000);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MasterCategoryAttributesFilesV1Response.class);

        List<ImportsFileV1> files = response.as(MasterCategoryAttributesFilesV1Response.class).getMasterCategoryAttributesFiles();
        int filesFromDbCount = ImportFilesDao.INSTANCE.getCount("MasterCategoryAttributesFile");
        compareTwoObjects(files.size(), filesFromDbCount);
    }

    @TmsLink("1911")
    @Story("Мастер каталог")
    @Test(groups = {API_INSTAMART_PROD, "api-v1"},
            description = "Получение информации о файлах импорта аттрибутов мастер каталога")
    public void getMasterCategoryAttributesFilesPROD() {
        final Response response = ImportsV1Request.MasterCategoryAttributesFiles.GET(100);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MasterCategoryAttributesFilesV1Response.class);
    }

    @TmsLink("1912")
    @Story("Бренды")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение информации о файлах импорта брендов")
    public void getBrandsFiles() {
        final Response response = ImportsV1Request.BrandFiles.GET(10000);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, BrandsFilesV1Response.class);

        List<ImportsFileV1> files = response.as(BrandsFilesV1Response.class).getBrandsFiles();
        int filesFromDbCount = ImportFilesDao.INSTANCE.getCount("BrandsFile");
        compareTwoObjects(files.size(), filesFromDbCount);
    }

    @TmsLink("1912")
    @Story("Бренды")
    @Test(groups = {API_INSTAMART_PROD, "api-v1"},
            description = "Получение информации о файлах импорта брендов")
    public void getBrandsFilesPROD() {
        final Response response = ImportsV1Request.BrandFiles.GET(100);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, BrandsFilesV1Response.class);
    }

    @TmsLink("1913")
    @Story("Категории")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение информации о файлах импорта категорий")
    public void getTaxonsFiles() {
        final Response response = ImportsV1Request.TaxonsFiles.GET(10000);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, TaxonsFilesV1Response.class);

        List<ImportsFileV1> files = response.as(TaxonsFilesV1Response.class).getTaxonsFiles();
        int filesFromDbCount = ImportFilesDao.INSTANCE.getCount("TaxonsFile");
        compareTwoObjects(files.size(), filesFromDbCount);
    }

    @TmsLink("1913")
    @Story("Категории")
    @Test(groups = {API_INSTAMART_PROD, "api-v1"},
            description = "Получение информации о файлах импорта категорий")
    public void getTaxonsFilesPROD() {
        final Response response = ImportsV1Request.TaxonsFiles.GET(100);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, TaxonsFilesV1Response.class);
    }

    @TmsLink("1914")
    @Story("Иконки категорий")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение информации о файлах импорта иконок категорий")
    public void getTaxonsImagesFiles() {
        final Response response = ImportsV1Request.TaxonsImagesFiles.GET(10000);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, TaxonsImagesFilesV1Response.class);

        List<ImportsFileV1> files = response.as(TaxonsImagesFilesV1Response.class).getTaxonsImagesFiles();
        int filesFromDbCount = ImportFilesDao.INSTANCE.getCount("TaxonsImagesFile");
        compareTwoObjects(files.size(), filesFromDbCount);
    }

    @TmsLink("1914")
    @Story("Иконки категорий")
    @Test(groups = {API_INSTAMART_PROD, "api-v1"},
            description = "Получение информации о файлах импорта иконок категорий")
    public void getTaxonsImagesFilesPROD() {
        final Response response = ImportsV1Request.TaxonsImagesFiles.GET(100);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, TaxonsImagesFilesV1Response.class);
    }

    @TmsLink("1915")
    @Story("Мета страниц")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение информации о файлах импорта мета страницы")
    public void getPageMetasFiles() {
        final Response response = ImportsV1Request.PageMetasFiles.GET(10000);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PageMetasFilesV1Response.class);

        List<ImportsFileV1> files = response.as(PageMetasFilesV1Response.class).getPageMetasFiles();
        int filesFromDbCount = ImportFilesDao.INSTANCE.getCount("PageMetasFile");
        compareTwoObjects(files.size(), filesFromDbCount);
    }

    @TmsLink("1915")
    @Story("Мета страниц")
    @Test(groups = {API_INSTAMART_PROD, "api-v1"},
            description = "Получение информации о файлах импорта мета страницы")
    public void getPageMetasFilesPROD() {
        final Response response = ImportsV1Request.PageMetasFiles.GET(100);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PageMetasFilesV1Response.class);
    }

    @TmsLink("1928")
    @Story("Продукты")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Импорт нового продукта")
    public void importProducts() {
        final Response response = ImportsV1Request.ProductsFiles.POST("src/test/resources/data/products.xlsx");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsFileV1Response.class);

        int count = 0;
        String status = null;
        while (count < 26) {
            final Response responseWithList = ImportsV1Request.ProductsFiles.GET(1);
            checkStatusCode200(responseWithList);
            status = responseWithList.as(ProductsFilesV1Response.class).getProductsFiles().get(0).getStatus();
            if (status.equals(ImportStatusV1.DONE.getValue()))
                break;
            ThreadUtil.simplyAwait(2);
            count++;
        }

        compareTwoObjects(status, ImportStatusV1.DONE.getValue());
        SpreeProductsEntity spreeProductsEntity = SpreeProductsDao.INSTANCE.getProductBySku("37327000000");
        checkFieldIsNotEmpty(spreeProductsEntity, "продукт в БД");
        SpreeProductsDao.INSTANCE.delete(spreeProductsEntity.getId());
    }

    @TmsLink("1929")
    @Story("Офферы")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Импорт нового оффера")
    public void importOffers() {
        admin.auth();
        StoresAdminRequest.Stores stores = getStoreSelgrosMiklouhoMaclay();

        admin.createStore(stores);
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(stores.getStore().getLocation().getLat(), stores.getStore().getLocation().getLon());
        storeId = storeFromDb.getId();
        StoresTenantsDao.INSTANCE.addStoreTenant(storeId, "sbermarket");
        importKey = SpreeRetailersDao.INSTANCE.findById(storeFromDb.getRetailerId()).get().getKey() + "-" + stores.getStore().getConfig().getImportKeyPostfix();

        fileBytes = changeXlsFileSheetName("src/test/resources/data/offers.xlsx", importKey, 0);

        final Response response = ImportsV1Request.OffersFiles.POST(fileBytes);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OffersFileV1Response.class);

        int count = 0;
        String status = null;
        while (count < 20) {
            status = admin.getOfferFiles(1).get(0).getStatus();
            if (status.equals(ImportStatusV1.DONE.getValue()))
                break;
            ThreadUtil.simplyAwait(1);
            count++;
        }
        compareTwoObjects(status, ImportStatusV1.DONE.getValue());
        OffersEntity offerFromDb = OffersDao.INSTANCE.getOfferByStoreId(Math.toIntExact(storeId));
        offerId = offerFromDb.getId();
        checkFieldIsNotEmpty(offerFromDb, "оффер в БД");
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(offerFromDb.getName(), "КАРТОФЕЛЬ (СЕТКА) 25КГ", softAssert);
        compareTwoObjects(offerFromDb.getPublished(), 1, softAssert);
        softAssert.assertAll();
    }

    @TmsLink("1930")
    @Story("Офферы")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Импорт стоков оффера",
            dependsOnMethods = "importOffers")
    public void importOffersStocks() {
        OffersDao.INSTANCE.updateOfferStock(offerId, 1);
        final Response response = ImportsV1Request.OffersStocksFiles.POST(fileBytes);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OffersStocksFileV1Response.class);

        int count = 0;
        String status = null;
        while (count < 20) {
            final Response responseWithList = ImportsV1Request.OffersStocksFiles.GET(1);
            checkStatusCode200(responseWithList);
            status = responseWithList.as(StocksFilesV1Response.class).getOffersStocksFiles().get(0).getStatus();
            if (status.equals(ImportStatusV1.DONE.getValue()))
                break;
            ThreadUtil.simplyAwait(1);
            count++;
        }
        compareTwoObjects(status, ImportStatusV1.DONE.getValue());
        OffersEntity offerFromDb = OffersDao.INSTANCE.getOfferByStoreId(Math.toIntExact(storeId));
        checkFieldIsNotEmpty(offerFromDb, "оффер в БД");
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(offerFromDb.getName(), "КАРТОФЕЛЬ (СЕТКА) 25КГ", softAssert);
        compareTwoObjects(offerFromDb.getPublished(), 1, softAssert);
        compareTwoObjects(offerFromDb.getStock(), 999, softAssert);
        softAssert.assertAll();
    }

    @TmsLinks(value = {@TmsLink("1931"), @TmsLink("1932"), @TmsLink("1933")})
    @Story("Фильтры")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Импорт фильтров",
            dataProvider = "filtersImportModes",
            dataProviderClass = RestDataProvider.class)
    public void importFilters(FiltersFilesModeV1 mode) {
        final Response response = ImportsV1Request.FilterFiles.POST("src/test/resources/data/filters_import.xlsx", mode.getValue());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, FiltersFileV1Response.class);

        int count = 0;
        String status = null;
        while (count < 20) {
            final Response responseWithList = ImportsV1Request.FilterFiles.GET(1);
            checkStatusCode200(responseWithList);
            status = responseWithList.as(FiltersFilesV1Response.class).getFiltersFiles().get(0).getStatus();
            if (status.equals(ImportStatusV1.DONE.getValue()))
                break;
            ThreadUtil.simplyAwait(1);
            count++;
        }
        compareTwoObjects(status, ImportStatusV1.DONE.getValue());
        SpreeProductFiltersEntity productsFilterFromDb = SpreeProductFiltersDao.INSTANCE.getFilterByInstamartId(10100112L);
        checkFieldIsNotEmpty(productsFilterFromDb, "Фильтр в БД");
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(productsFilterFromDb.getName(), "Готовый завтрак", softAssert);
        compareTwoObjects(productsFilterFromDb.getKeywords(), "готовый завтрак", softAssert);
        compareTwoObjects(productsFilterFromDb.getPosition(), 2, softAssert);
        softAssert.assertAll();
        SpreeProductFiltersDao.INSTANCE.delete(productsFilterFromDb.getId());
    }

    @TmsLink("1949")
    @Story("Цены")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Импорт цен",
            dependsOnMethods = "importOffers")
    public void importPrices() {
        final Response response = ImportsV1Request.PricesFiles.POST(String.format("product_price__%s.xlsx", importKey), "src/test/resources/data/product_price___5-10.xlsx");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PricesFileV1Response.class);

        int count = 0;
        String status = null;
        while (count < 20) {
            final Response responseWithList = ImportsV1Request.PricesFiles.GET(1);
            checkStatusCode200(responseWithList);
            status = responseWithList.as(PricesFilesV1Response.class).getPricesFiles().get(0).getStatus();
            if (status.equals(ImportStatusV1.DONE.getValue()))
                break;
            ThreadUtil.simplyAwait(1);
            count++;
        }
        compareTwoObjects(status, ImportStatusV1.DONE.getValue());
        PricesEntity priceFromDb = PricesDao.INSTANCE.getPriceByOfferId(offerId);
        checkFieldIsNotEmpty(priceFromDb, "цена в БД");
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(priceFromDb.getStoreId(), storeId, softAssert);
        compareTwoObjects(priceFromDb.getCostPrice(), 1840.57, softAssert);
        compareTwoObjects(priceFromDb.getRetailerPrice(), 2045.0, softAssert);
        compareTwoObjects(priceFromDb.getOfferRetailerPrice(), 2045.0, softAssert);
        softAssert.assertAll();
    }

    @TmsLink("1950")
    @Story("Штрихкоды")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Импорт штрихкодов",
            dependsOnMethods = "importOffers")
    public void importEans() {
        final Response response = ImportsV1Request.EansFiles.POST("src/test/resources/data/export_ean.xml");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, EansFileV1Response.class);

        int count = 0;
        String status = null;
        while (count < 20) {
            final Response responseWithList = ImportsV1Request.EansFiles.GET(1);
            checkStatusCode200(responseWithList);
            status = responseWithList.as(EansFilesV1Response.class).getEansFiles().get(0).getStatus();
            if (status.equals(ImportStatusV1.DONE.getValue()))
                break;
            ThreadUtil.simplyAwait(1);
            count++;
        }
        compareTwoObjects(status, ImportStatusV1.DONE.getValue());
        EansEntity eanFromDb = EansDao.INSTANCE.getEanByRetailerSku("6654414444");
        checkFieldIsNotEmpty(eanFromDb, "штрихкод в БД");
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(eanFromDb.getRetailerId(), 32L, softAssert);
        compareTwoObjects(eanFromDb.getValue(), "2999820000022", softAssert);
        softAssert.assertAll();
        EansDao.INSTANCE.delete(eanFromDb.getId());
    }

    @TmsLink("1951")
    @Story("Бренды")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Импорт брендов")
    public void importBrands() {
        final Response response = ImportsV1Request.BrandFiles.POST("src/test/resources/data/brands_import.xlsx");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, BrandsFileV1Response.class);

        int count = 0;
        String status = null;
        while (count < 20) {
            final Response responseWithList = ImportsV1Request.BrandFiles.GET(1);
            checkStatusCode200(responseWithList);
            status = responseWithList.as(BrandsFilesV1Response.class).getBrandsFiles().get(0).getStatus();
            if (status.equals(ImportStatusV1.DONE.getValue()))
                break;
            ThreadUtil.simplyAwait(1);
            count++;
        }
        compareTwoObjects(status, ImportStatusV1.DONE.getValue());
        SpreeBrandsEntity brandFromDb = SpreeBrandsDao.INSTANCE.getBrandByName("Автотест");
        checkFieldIsNotEmpty(brandFromDb, "бренд в БД");
        compareTwoObjects(brandFromDb.getKeywords(), "Автотест");
        SpreeBrandsDao.INSTANCE.delete(brandFromDb.getId());
    }

    @TmsLink("1952")
    @Story("Изображения продуктов")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Импорт изображений продуктов")
    public void importProductsImages() {
        final Response response = ImportsV1Request.ProductsImagesArchives.POST("src/test/resources/data/product_image.zip");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsImagesArchiveV1Response.class);

        int count = 0;
        String status = null;
        while (count < 20) {
            final Response responseWithList = ImportsV1Request.ProductsImagesArchives.GET(1);
            checkStatusCode200(responseWithList);
            status = responseWithList.as(ProductsImagesArchivesV1Response.class).getProductsImagesArchives().get(0).getStatus();
            if (status.equals(ImportStatusV1.ARCHIVE_PROCESSED.getValue()))
                break;
            ThreadUtil.simplyAwait(2);
            count++;
        }
        compareTwoObjects(status, ImportStatusV1.ARCHIVE_PROCESSED.getValue());
        compareTwoObjects(ImagesDraftsDao.INSTANCE.getCount("13626"), 2);
    }

    @TmsLink("1953")
    @Story("Иконки категорий")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Импорт иконок категорий")
    public void importTaxonsImages() {
        final Response response = ImportsV1Request.TaxonsImagesFiles.POST("src/test/resources/data/taxon_icons.zip");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, TaxonsImagesFileV1Response.class);

        int count = 0;
        String status = null;
        while (count < 20) {
            final Response responseWithList = ImportsV1Request.TaxonsImagesFiles.GET(1);
            checkStatusCode200(responseWithList);
            status = responseWithList.as(TaxonsImagesFilesV1Response.class).getTaxonsImagesFiles().get(0).getStatus();
            if (status.equals(ImportStatusV1.DONE.getValue()))
                break;
            ThreadUtil.simplyAwait(2);
            count++;
        }
        compareTwoObjects(status, ImportStatusV1.DONE.getValue());
        SpreeTaxonsEntity taxonFromDb = SpreeTaxonsDao.INSTANCE.getTaxonByInstamartId(66080700);
        checkFieldIsNotEmpty(taxonFromDb, "категория в БД");
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(taxonFromDb.getIconFileName(), "66080700.jpg", softAssert);
        compareTwoObjects(taxonFromDb.getIconContentType(), "image/jpeg", softAssert);
        checkFieldIsNotEmpty(taxonFromDb.getIconFileSize(), "размер иконки");
        softAssert.assertAll();
        SpreeTaxonsDao.INSTANCE.updateTaxonIcon(null, null, null, 66080700);
    }

    @TmsLink("1954")
    @Story("Категории")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Импорт категорий с неверным файлом")
    public void importTaxons() {
        final Response response = ImportsV1Request.TaxonsFiles.POST("src/test/resources/data/brands_import.xlsx");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, TaxonsFileV1Response.class);

        int count = 0;
        String status = null;
        while (count < 20) {
            final Response responseWithList = ImportsV1Request.TaxonsFiles.GET(1);
            checkStatusCode200(responseWithList);
            status = responseWithList.as(TaxonsFilesV1Response.class).getTaxonsFiles().get(0).getStatus();
            if (status.equals(ImportStatusV1.FAILED.getValue()))
                break;
            ThreadUtil.simplyAwait(1);
            count++;
        }
        compareTwoObjects(status, ImportStatusV1.FAILED.getValue());
    }

    @TmsLink("1955")
    @Story("Мастер каталог")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Импорт мастер каталога с неверным файлом")
    public void importMasterCategories() {
        final Response response = ImportsV1Request.MasterCategoriesFiles.POST("src/test/resources/data/brands_import.xlsx");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MasterCategoriesFileV1Response.class);

        int count = 0;
        String status = null;
        while (count < 20) {
            final Response responseWithList = ImportsV1Request.MasterCategoriesFiles.GET(1);
            checkStatusCode200(responseWithList);
            status = responseWithList.as(MasterCategoriesFilesV1Response.class).getMasterCategoriesFiles().get(0).getStatus();
            if (status.equals(ImportStatusV1.FAILED.getValue()))
                break;
            ThreadUtil.simplyAwait(1);
            count++;
        }
        compareTwoObjects(status, ImportStatusV1.FAILED.getValue());
    }

    @TmsLink("1956")
    @Story("Мастер каталог")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Импорт аттрибутов мастер каталога с неверным файлом")
    public void importMasterCategoryAttributes() {
        final Response response = ImportsV1Request.MasterCategoryAttributesFiles.POST("src/test/resources/data/brands_import.xlsx");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MasterCategoryAttributesFileV1Response.class);

        int count = 0;
        String status = null;
        while (count < 20) {
            final Response responseWithList = ImportsV1Request.MasterCategoryAttributesFiles.GET(1);
            checkStatusCode200(responseWithList);
            status = responseWithList.as(MasterCategoryAttributesFilesV1Response.class).getMasterCategoryAttributesFiles().get(0).getStatus();
            if (status.equals(ImportStatusV1.FAILED.getValue()))
                break;
            ThreadUtil.simplyAwait(1);
            count++;
        }
        compareTwoObjects(status, ImportStatusV1.FAILED.getValue());
    }


    @AfterClass(alwaysRun = true)
    public void clearData() {
        if(!EnvironmentProperties.Env.isProduction()) {
            if (Objects.nonNull(offerId)) {
                OffersDao.INSTANCE.delete(offerId);
                PricesDao.INSTANCE.deletePriceByOfferId(offerId);
            }
            if (Objects.nonNull(storeId)) {
                StoresDao.INSTANCE.delete(storeId);
                StoreConfigsDao.INSTANCE.deleteByStoreId(storeId);
                StoresTenantsDao.INSTANCE.deleteStoreTenantByStoreId(storeId);
                PaymentMethodStoresDao.INSTANCE.deletePaymentMethodByStoreId(storeId);
            }
            ImagesDraftsDao.INSTANCE.deleteImagesByName("13626");
        }
    }
}
