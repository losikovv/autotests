package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.api.request.admin.StoresAdminRequest;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Работа с ритейлерами")
public final class AdministrationRetailerActivateTests {

    private final ApiHelper apiHelper = new ApiHelper();
    private final String retailerName = Generate.literalString(6) + "_retailer";
    private final String cityNameFirst = Generate.literalString(6) + "_city";
    private StoresAdminRequest.Store firstStore = new StoresAdminRequest.Store();

    @BeforeMethod(alwaysRun = true)
    public void prepareData() {
        apiHelper.createRetailerInAdmin(retailerName);

        apiHelper.setupCity(cityNameFirst);
        firstStore = apiHelper.createStoreInAdmin(retailerName, cityNameFirst);

        apiHelper.setupStoreForActivation(firstStore);
    }

    @AfterMethod(alwaysRun = true)
    public void clearData() {
        apiHelper.deleteRetailerWithStores(retailerName);

        apiHelper.deleteCityInAdmin(cityNameFirst);
        apiHelper.deleteOperationalZonesInAdmin(cityNameFirst);
        apiHelper.deleteOperationalZonesInShopper(cityNameFirst);
    }

    @CaseId(213)
    @Story("Страница ретейлеров")
    @Test(description = "Ретейлер без действующих магазинов недоступен", groups = {"acceptance", "regression"})
    public void retailerInaccessibilityWithoutActiveStores() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnRetailer(retailerName);

        retailer().checkAddNewStoreButtonVisible();
        retailer().clickOnFirstStore();

        retailer().checkAllStoresDisable();

        retailers().goToPage();
        retailers().checkRetailerInactive(retailerName);
    }

    @CaseId(214)
    @Story("Страница ретейлеров")
    @Test(description = "Ритейлер доступен, если у него есть 1 или более действующих магазинов", groups = {"acceptance", "regression"})
    public void retailerAccessibilityWithActiveStore() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().clickOnPlusForRetailer(retailerName);
        retailers().clickOnPlusForFirstCity();

        retailers().clickOnActivateStoreViaAddress(cityNameFirst + ", Mira, 211 ");

        retailers().interactActivateStoreModal().checkActivateStoreModalVisible();
        retailers().interactActivateStoreModal().clickOnOkButton();
        retailers().interactActivateStoreModal().checkActivateStoreModalNotVisible();

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnRetailer(retailerName);

        retailer().checkAddNewStoreButtonVisible();
        retailer().clickOnFirstStore();

        retailer().checkSomeStoreEnable();

        retailers().goToPage();
        retailers().checkRetailerActive(retailerName);
    }

    @CaseIDs(value = {@CaseId(541), @CaseId(542)})
    @Story("Страница ретейлеров")
    @Test(description = "Активация, деактивация магазина", groups = {"acceptance", "regression"})
    public void shopActivateAndDeactivate() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().clickOnPlusForRetailer(retailerName);
        retailers().clickOnPlusForFirstCity();

        retailers().clickOnActivateStoreViaAddress(cityNameFirst + ", Mira, 211 ");

        retailers().interactActivateStoreModal().checkActivateStoreModalVisible();
        retailers().interactActivateStoreModal().clickOnOkButton();
        retailers().interactActivateStoreModal().checkActivateStoreModalNotVisible();

        retailers().checkStoreActiveViaAddress(cityNameFirst + ", Mira, 211 ");

        retailers().clickOnDeactivateStoreViaAddress(cityNameFirst + ", Mira, 211 ");

        retailers().checkDeactivateStorePopupVisible();
        retailers().clickOkOnDeactivateStorePopup();
        retailers().checkDeactivateStorePopupNotVisible();

        retailers().checkStoreInactiveViaAddress(cityNameFirst + ", Mira, 211 ");
    }
}
