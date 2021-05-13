package ru.instamart.tests.api.v2.e2e;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.helper.RegistrationHelper;
import ru.instamart.api.model.v2.StoreV2;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.core.dataprovider.RestDataProvider;
import ru.instamart.ui.common.pagesdata.UserData;
import ru.instamart.ui.helper.WaitingHelper;

@Epic("ApiV2")
@Feature("E2E тесты")
public final class RetailerE2ETests extends RestBase {

    private static final Logger log = LoggerFactory.getLogger(RetailerE2ETests.class);

    @BeforeClass(description = "Проверка самих провайдеров")
    public void selfTest() {
        RestDataProvider.getAvailableRetailers();
    }

    @Story("Тесты оформления заказа у каждого ретейлера")
    @CaseId(104)
    @Test(  dataProvider = "storeOfEachRetailer",
            dataProviderClass = RestDataProvider.class,
            description = "Тест заказов у каждого ретейлера",
            groups = {"api-retailer"})
    public void orderByRetailer(final StoreV2 store) {
        apiV2.skipTestIfOnlyPickupIsAvailable(store);

        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);

        log.info("Оформляем заказ в {}", store.getName());
        apiV2.order(userData, store.getId());
        log.info("Отменяем заказ в {}", store.getName());
        apiV2.cancelCurrentOrder();
        WaitingHelper.simply(30);
    }
}
