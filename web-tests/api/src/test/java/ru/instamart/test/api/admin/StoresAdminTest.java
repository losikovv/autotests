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
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.request.admin.StoresAdminRequest;
import ru.instamart.jdbc.dao.stf.PaymentMethodStoresDao;
import ru.instamart.jdbc.dao.stf.StoreConfigsDao;
import ru.instamart.jdbc.dao.stf.StoresDao;
import ru.instamart.jdbc.entity.stf.StoreConfigsEntity;
import ru.instamart.jdbc.entity.stf.StoresEntity;
import ru.instamart.kraken.data_provider.JsonDataProvider;
import ru.instamart.kraken.data_provider.JsonProvider;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Objects;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStoreInDb;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode302;
import static ru.instamart.api.request.admin.StoresAdminRequest.getStoreSelgrosMiklouhoMaclay;

@Epic("Admin")
@Feature("Магазины")
public class StoresAdminTest extends RestBase {

    private Integer id;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.auth();
    }

    @CaseId(1189)
    @Story("Магазины ритейлеров")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание нового магазина")
    public void createStore() {
        StoresAdminRequest.Store store = getStoreSelgrosMiklouhoMaclay();
        admin.createStore(store);
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(store.getLat(), store.getLon());
        checkFieldIsNotEmpty(storeFromDb, "магазин в БД");
        id = storeFromDb.getId();
        StoreConfigsEntity storeConfigs = StoreConfigsDao.INSTANCE.getConfigsByStoreId(id);
        checkStoreInDb(store, storeFromDb, storeConfigs);
    }

    @CaseId(1190)
    @Story("Магазины ритейлеров")
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование нового магазина",
            dependsOnMethods = "createStore")
    public void editStore() {
        StoresAdminRequest.Store store = getStoreSelgrosMiklouhoMaclay();
        final Response response = StoresAdminRequest.PATCH(store, id);
        checkStatusCode302(response);
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(store.getLat(), store.getLon());
        checkFieldIsNotEmpty(storeFromDb, "магазин в БД");
        StoreConfigsEntity storeConfigs = StoreConfigsDao.INSTANCE.getConfigsByStoreId(id);
        checkStoreInDb(store, storeFromDb, storeConfigs);
    }

    @CaseIDs(value = {@CaseId(1210), @CaseId(1211), @CaseId(1212), @CaseId(1213), @CaseId(1214), @CaseId(1215), @CaseId(1216)})
    @Story("Магазины ритейлеров")
    @JsonDataProvider(path = "data/json_admin/admin_negative_stores_data.json", type = RestDataProvider.StoresAdminTestDataRoot.class)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание нового магазина с отсутствующим обязательным параметром",
            dataProvider = "jsonWithoutParallel",
            dataProviderClass = JsonProvider.class)
    public void createStoreWithoutRequiredParams(RestDataProvider.StoresAdminTestData testData) {
        Allure.step(testData.getDescription());
        final Response response = StoresAdminRequest.POST(testData.getStore());
        checkStatusCode(response, testData.getStatusCode(), ContentType.HTML);
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(testData.getStore().getLat(), testData.getStore().getLon());
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
