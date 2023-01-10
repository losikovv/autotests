package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.AdminHelper;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.api.model.v1.RetailerV1;
import ru.instamart.jdbc.dao.stf.StoreLabelsDao;
import ru.instamart.jdbc.dao.stf.StoresDao;
import ru.instamart.jdbc.entity.stf.StoresEntity;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.StoreLabelData;
import ru.instamart.kraken.data.StoreLabels;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.annotation.DoNotOpenBrowser;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.enums.ShopUrl;

import static ru.instamart.reforged.Group.REGRESSION_ADMIN;
import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Settings (Настройки)")
public class AdministrationStoreGroupsTests {

    private static final ThreadLocal<RetailerV1> retailer = new ThreadLocal<>();
    private static final ThreadLocal<String> cityName = new ThreadLocal<>();
    private static final ThreadLocal<StoresEntity> storeDB = new ThreadLocal<>();
    private static final ThreadLocal<StoreLabelData> storeLabel = new ThreadLocal<>();

    private final ApiHelper api = new ApiHelper();
    private final AdminHelper adminApi = new AdminHelper();

    @BeforeMethod(alwaysRun = true)
    private void prepareData() {
        retailer.set(api.createRetailerInAdmin(Generate.literalString(6) + "_retailer"));
        cityName.set(Generate.literalString(6) + "_city");
        api.setupCity(cityName.get());
        final var store = api.createStoreInAdmin(retailer.get().getName(), cityName.get());
        storeDB.set(StoresDao.INSTANCE.getStoreByCoordinates(
                store.getStore().getLocation().getLat(), store.getStore().getLocation().getLon()
        ));
        storeLabel.set(StoreLabels.newStoreLabel());
    }

    @AfterMethod(alwaysRun = true)
    @DoNotOpenBrowser
    private void clearData() {
        StoreLabelsDao.INSTANCE.deleteStoreLabelLink(
                adminApi.getStoreLabelByName(storeLabel.get().getTitle()).getId(),
                storeDB.get().getId());
        api.deleteStoreLabel(storeLabel.get().getTitle());
        api.deleteCityInAdmin(cityName.get());
        api.deleteRetailerWithStores(retailer.get().getName());
    }

    @TmsLink("569")
    @Test(description = "Добавление группы", groups = REGRESSION_ADMIN)
    public void checkAddNewGroup() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        storeGroups().goToPageOld();
        storeGroups().checkAddNewGroupButtonVisible();
        storeGroups().clickAddNewGroup();

        editStoreGroup().checkTitleInputVisible();
        editStoreGroup().fillTitle(storeLabel.get().getTitle());
        editStoreGroup().fillSubtitle(storeLabel.get().getSubtitle());
        editStoreGroup().fillCode(storeLabel.get().getCode());
        editStoreGroup().fillIcon(storeLabel.get().getIcon());
        editStoreGroup().fillPosition(String.valueOf(storeLabel.get().getPosition()));
        editStoreGroup().fillLevel(String.valueOf(storeLabel.get().getLevel()));
        editStoreGroup().clickSubmit();

        storeGroups().checkAddNewGroupButtonVisible();
        storeGroups().checkGroupsVisible();
        storeGroups().checkGroupExists(storeLabel.get().getTitle());
        storeEdit().goToPage(retailer.get().getSlug(), storeDB.get().getUuid());
        storeEdit().checkRegionDropdownVisible();
        storeEdit().checkStoreGroupsContains(storeLabel.get().getTitle());
    }

    @TmsLink("570")
    @Test(description = "Изменение назначенной группы", groups = REGRESSION_ADMIN)
    public void checkEditGroup() {
        api.createStoreLabel(storeLabel.get());
        StoreLabelsDao.INSTANCE.addStoreLabelToStore(
                adminApi.getStoreLabelByName(storeLabel.get().getTitle()).getId(),
                storeDB.get().getId(),
                "sbermarket"
        );

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        storeGroups().goToPageOld();
        storeGroups().checkAddNewGroupButtonVisible();

        storeGroups().editGroup(storeLabel.get().getTitle());

        storeLabel.get().setTitle(storeLabel.get().getTitle() + "_edited");
        editStoreGroup().checkTitleInputVisible();
        editStoreGroup().fillTitle(storeLabel.get().getTitle());
        editStoreGroup().clickSubmit();

        storeGroups().checkAddNewGroupButtonVisible();
        storeGroups().checkGroupsVisible();
        storeGroups().checkGroupExists(storeLabel.get().getTitle());

        storeEdit().goToPage(retailer.get().getSlug(), storeDB.get().getUuid());
        storeEdit().checkRegionDropdownVisible();
        storeEdit().checkStoreGroupsContains(storeLabel.get().getTitle());
    }

    @TmsLink("571")
    @Test(description = "Удаление назначенной магазину группы", groups = REGRESSION_ADMIN)
    public void checkDeleteGroup() {
        api.createStoreLabel(storeLabel.get());
        StoreLabelsDao.INSTANCE.addStoreLabelToStore(
                adminApi.getStoreLabelByName(storeLabel.get().getTitle()).getId(),
                storeDB.get().getId(),
                "sbermarket"
        );

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        storeGroups().goToPageOld();
        storeGroups().checkAddNewGroupButtonVisible();

        storeGroups().removeGroup(storeLabel.get().getTitle());
        storeGroups().checkConfirmActionModalDisplayed();
        storeGroups().clickConfirmStatusModalYes();

        storeGroups().checkNoticePopupDisplayed();
        storeGroups().checkNoticeTextEquals("Группа успешно удалена");
        storeGroups().checkGroupNotExists(storeLabel.get().getTitle());

        storeEdit().goToPage(retailer.get().getSlug(), storeDB.get().getUuid());
        storeEdit().checkRegionDropdownVisible();
        storeEdit().checkStoreGroupsNotContains(storeLabel.get().getTitle());
    }

    @TmsLink("577")
    @Test(description = "Назначение группы магазину", groups = REGRESSION_ADMIN)
    public void checkAddGroupToStore() {
        api.createStoreLabel(storeLabel.get());

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        storeGroups().goToPageOld();
        storeGroups().checkAddNewGroupButtonVisible();

        storeEdit().goToPage(ShopUrl.DEFAULT.getUrl(), UiProperties.DEFAULT_METRO_MOSCOW_UUID);
        storeEdit().checkRegionDropdownVisible();
        storeEdit().checkStoreGroupsContains(storeLabel.get().getTitle());

        storeEdit().clickOnStoreLabel(storeLabel.get().getTitle());
        storeEdit().clickSubmit();

        store().checkBackToStoresListButtonVisible();

        storeEdit().goToPage(ShopUrl.DEFAULT.getUrl(), UiProperties.DEFAULT_METRO_MOSCOW_UUID);
        storeEdit().checkRegionDropdownVisible();
        storeEdit().checkGroupSelected(storeLabel.get().getTitle());
    }
}
