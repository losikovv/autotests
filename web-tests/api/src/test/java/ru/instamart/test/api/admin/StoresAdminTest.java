package ru.instamart.test.api.admin;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.admin.StoresAdminRequest;
import ru.instamart.jdbc.dao.stf.PaymentMethodStoresDao;
import ru.instamart.jdbc.dao.stf.StoreConfigsDao;
import ru.instamart.jdbc.dao.stf.StoresDao;
import ru.instamart.jdbc.entity.stf.StoreConfigsEntity;
import ru.instamart.jdbc.entity.stf.StoresEntity;
import ru.instamart.kraken.data_provider.JsonDataProvider;
import ru.instamart.kraken.data_provider.JsonProvider;
import ru.instamart.kraken.listener.Skip;
import io.qameta.allure.TmsLinks;
import io.qameta.allure.TmsLink;

import java.util.Objects;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStoreInDb;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.api.request.admin.StoresAdminRequest.getStoreSelgrosMiklouhoMaclay;

@Epic("Admin")
@Feature("Магазины")
public class StoresAdminTest extends RestBase {

    private Integer id;
    private String uuid;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.authApi();
    }

    @BeforeMethod(alwaysRun = true, description = "Повторная авторизация если токен протух или invalid")
    public void auth() {
        if (Objects.equals(SessionFactory.getSession(SessionType.ADMIN).getToken(), "invalid")) {
            admin.authApi();
        }
    }

    @TmsLink("1189")
    @Story("Магазины ритейлеров")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Создание нового магазина")
    public void createStore() {
        StoresAdminRequest.Stores store = getStoreSelgrosMiklouhoMaclay();
        admin.createStore(store);
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(store.getStore().getLocation().getLat(), store.getStore().getLocation().getLon());
        checkFieldIsNotEmpty(storeFromDb, "магазин в БД");
        id = storeFromDb.getId();
        uuid = storeFromDb.getUuid();
        StoreConfigsEntity storeConfigs = StoreConfigsDao.INSTANCE.getConfigsByStoreId(id);
        checkStoreInDb(store, storeFromDb, storeConfigs);
    }

    @TmsLink("1190")
    @Story("Магазины ритейлеров")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Редактирование нового магазина",
            dependsOnMethods = "createStore")
    public void editStore() {
        StoresAdminRequest.Stores stores = getStoreSelgrosMiklouhoMaclay();
        final Response response = StoresAdminRequest.PATCH(stores, uuid);
        checkStatusCode302(response);
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(stores.getStore().getLocation().getLat(), stores.getStore().getLocation().getLon());
        checkFieldIsNotEmpty(storeFromDb, "магазин в БД");
        StoreConfigsEntity storeConfigs = StoreConfigsDao.INSTANCE.getConfigsByStoreId(id);
        checkStoreInDb(stores, storeFromDb, storeConfigs);
    }

    @Skip
    @TmsLinks(value = {@TmsLink("1210"), @TmsLink("1211"), @TmsLink("1212"), @TmsLink("1213"), @TmsLink("1214"), @TmsLink("1215"), @TmsLink("1216")})
    @Story("Магазины ритейлеров")
    @JsonDataProvider(path = "data/json_admin/admin_negative_stores_data.json", type = RestDataProvider.StoresAdminTestDataRoot.class)
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Создание нового магазина с отсутствующим обязательным параметром",
            dataProvider = "jsonWithoutParallel",
            dataProviderClass = JsonProvider.class)
    public void createStoreWithoutRequiredParams(RestDataProvider.StoresAdminTestData testData) {
        Allure.step(testData.getDescription());
        final Response response = StoresAdminRequest.POST(testData.getStore());
        checkStatusCode(response, testData.getStatusCode(), ContentType.HTML);
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(testData.getStore().getStore().getLocation().getLat(), testData.getStore().getStore().getLocation().getLon());
        Allure.step("Asserts",()->{
            Assert.assertNull(storeFromDb, "Проверка создания магазина в БД вернула не null");
        });
    }

    @AfterClass(alwaysRun = true)
    public void deleteStore() {
        if(Objects.nonNull(id)) {
            StoresDao.INSTANCE.delete(id);
            StoreConfigsDao.INSTANCE.deleteByStoreId(id);
            PaymentMethodStoresDao.INSTANCE.deletePaymentMethodByStoreId(id);
        }
    }
}
