package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Редактирование ритейлеров")
public final class AdministrationRetailerEditTests {

    private final ApiHelper apiHelper = new ApiHelper();
    private String retailerName;

    @BeforeClass(alwaysRun = true)
    public void prepareData() {
        retailerName = Generate.literalString(6) + "_retailer";
        apiHelper.createRetailerInAdmin(retailerName);
    }

    @CaseId(1417)
    @Story("Страница ритейлеров")
    @Test(description = "Кнопка 'Настройки' раскрывает сайдбар с настройками ритейлера", groups = {"regression"})
    public void openRetailerSettingsSidebar() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnRetailer(retailerName);

        retailer().checkAddNewStoreButtonVisible();
        retailer().clickOnSettings();
        retailer().interactiveSettings().checkSidebarVisible();
        retailer().interactiveSettings().checkDrawerTitle("Редактирование настроек ритейлера");
        retailer().interactiveSettings().checkRetailerNameInputValue(retailerName);
    }

    @CaseId(1418)
    @Story("Страница ритейлеров")
    @Test(description = "Кнопка 'Внешний вид' раскрывает сайдбар с редактированием внешнего вида ритейлера", groups = {"regression"})
    public void openRetailerAppearanceSidebar() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnRetailer(retailerName);

        retailer().checkAddNewStoreButtonVisible();
        retailer().clickOnAppearance();
        retailer().interactiveAppearance().checkSidebarVisible();
        retailer().interactiveAppearance().checkDrawerTitle("Редактирование внешнего вида ритейлера");
        retailer().interactiveAppearance().checkUploadLogoButtonVisible();
    }

    @CaseId(1429)
    @Story("Страница ритейлеров")
    @Test(description = "Кнопка 'Ранжировать список' раскрывает сайдбар с ранжированием списка ритейлеров", groups = {"regression"})
    public void openRankListSidebar() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();

        retailers().clickRankList();

        retailers().interactiveRankList().checkSidebarVisible();
        retailers().interactiveRankList().checkDrawerTitle("Ранжировать список ритейлеров");
        retailers().interactiveRankList().checkRetailersListVisible();
    }

    @AfterClass(alwaysRun = true)
    public void clearData() {
        apiHelper.deleteRetailerByNameInAdmin(retailerName);
    }
}
