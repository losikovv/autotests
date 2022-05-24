package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.AdminHelper;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.api.model.v1.RetailerV1;
import ru.instamart.api.request.admin.StoresAdminRequest;
import ru.instamart.jdbc.dao.stf.StoreLabelsDao;
import ru.instamart.jdbc.dao.stf.StoresDao;
import ru.instamart.jdbc.entity.stf.StoresEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.StoreLabelData;
import ru.instamart.kraken.data.StoreLabels;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Settings (Настройки)")
public class AdministrationStoreGroupsTests {

    private final ApiHelper api = new ApiHelper();
    private final AdminHelper adminApi = new AdminHelper();

    private RetailerV1 retailer;
    private String cityName;
    private StoresAdminRequest.Store store;
    private StoresEntity storeDB;
    private StoreLabelData storeLabel;

    @BeforeMethod(alwaysRun = true)
    private void prepareData() {
        retailer = api.createRetailerInAdmin(Generate.literalString(6) + "_retailer");
        cityName = Generate.literalString(6) + "_city";
        api.setupCity(cityName);
        store = api.createStoreInAdmin(retailer.getName(), cityName);
        storeDB = StoresDao.INSTANCE.getStoreByCoordinates(store.getLat(), store.getLon());
        storeLabel = StoreLabels.newStoreLabel();
    }

    @AfterMethod(alwaysRun = true)
    private void clearData() {
        StoreLabelsDao.INSTANCE.deleteStoreLabelLink(
                adminApi.getStoreLabelByName(storeLabel.getTitle()).getId(),
                storeDB.getId());
        api.deleteStoreLabel(storeLabel.getTitle());
        api.deleteCityInAdmin(cityName);
        api.deleteRetailerWithStores(retailer.getName());
    }

    @CaseId(569)
    @Test(description = "Добавление группы", groups = {"regression"})
    public void checkAddNewGroup() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        storeGroups().openAdminPageWithoutSpa(storeGroups().pageUrl());
        storeGroups().checkAddNewGroupButtonVisible();
        storeGroups().clickAddNewGroup();

        editStoreGroup().checkTitleInputVisible();
        editStoreGroup().fillTitle(storeLabel.getTitle());
        editStoreGroup().fillSubtitle(storeLabel.getSubtitle());
        editStoreGroup().fillCode(storeLabel.getCode());
        editStoreGroup().fillIcon(storeLabel.getIcon());
        editStoreGroup().fillPosition(String.valueOf(storeLabel.getPosition()));
        editStoreGroup().fillLevel(String.valueOf(storeLabel.getLevel()));
        editStoreGroup().clickSubmit();

        storeGroups().checkAddNewGroupButtonVisible();
        storeGroups().checkGroupExists(storeLabel.getTitle());
        storeEdit().goToPage(retailer.getName(), storeDB.getUuid());
        storeEdit().checkRegionDropdownVisible();
        storeEdit().checkStoreGroupsContains(storeLabel.getTitle());
    }

    @CaseId(570)
    @Test(description = "Изменение назначенной группы", groups = {"regression"})
    public void checkEditGroup() {
        api.createStoreLabel(storeLabel);
        StoreLabelsDao.INSTANCE.addStoreLabelToStore(adminApi.getStoreLabelByName(storeLabel.getTitle()).getId(), storeDB.getId(), "sbermarket");

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        storeGroups().openAdminPageWithoutSpa(storeGroups().pageUrl());
        storeGroups().checkAddNewGroupButtonVisible();

        storeGroups().editGroup(storeLabel.getTitle());

        storeLabel.setTitle(storeLabel.getTitle() + "_edited");
        editStoreGroup().fillTitle(storeLabel.getTitle());
        editStoreGroup().clickSubmit();

        storeGroups().checkAddNewGroupButtonVisible();
        storeGroups().checkGroupExists(storeLabel.getTitle());

        storeEdit().goToPage(retailer.getName(), storeDB.getUuid());
        storeEdit().checkRegionDropdownVisible();
        storeEdit().checkStoreGroupsContains(storeLabel.getTitle());
    }

    @CaseId(571)
    @Test(description = "Удаление назначенной магазину группы", groups = {"regression"})
    public void checkDeleteGroup() {
        api.createStoreLabel(storeLabel);
        StoreLabelsDao.INSTANCE.addStoreLabelToStore(adminApi.getStoreLabelByName(storeLabel.getTitle()).getId(), storeDB.getId(), "sbermarket");

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        storeGroups().openAdminPageWithoutSpa(storeGroups().pageUrl());
        storeGroups().checkAddNewGroupButtonVisible();

        storeGroups().removeGroup(storeLabel.getTitle());
        storeGroups().checkConfirmActionModalDisplayed();
        storeGroups().clickConfirmStatusModalYes();

        storeGroups().checkNoticePopupDisplayed();
        storeGroups().checkNoticeTextEquals("Группа успешно удалена");
        storeGroups().checkGroupNotExists(storeLabel.getTitle());

        storeEdit().goToPage(retailer.getName(), storeDB.getUuid());
        storeEdit().checkRegionDropdownVisible();
        storeEdit().checkStoreGroupsNotContains(storeLabel.getTitle());
    }

    @CaseId(577)
    @Test(description = "Назначение группы магазину", groups = {"regression"})
    public void checkAddGroupToStore() {
        api.createStoreLabel(storeLabel);

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        storeGroups().openAdminPageWithoutSpa(storeGroups().pageUrl());
        storeGroups().checkAddNewGroupButtonVisible();

        storeEdit().goToPage(ShopUrl.DEFAULT.name(), EnvironmentProperties.DEFAULT_METRO_MOSCOW_UUID);
        storeEdit().checkRegionDropdownVisible();
        storeEdit().checkStoreGroupsContains(storeLabel.getTitle());

        storeEdit().clickOnStoreLabel(storeLabel.getTitle());
        storeEdit().clickSubmit();

        store().checkBackToStoresListButtonVisible();

        storeEdit().goToPage(ShopUrl.DEFAULT.name(), EnvironmentProperties.DEFAULT_METRO_MOSCOW_UUID);
        storeEdit().checkRegionDropdownVisible();
        storeEdit().checkGroupSelected(storeLabel.getTitle());
    }
}
