package ru.instamart.test.reforged.admin.retailer;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.reforged.Group.REGRESSION_ADMIN;
import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Редактирование ритейлеров")
public final class AdministrationRetailerEditTests {

    private final ApiHelper apiHelper = new ApiHelper();
    private final String retailerName = Generate.literalString(6) + "_retailer";

    @BeforeClass(alwaysRun = true)
    public void prepareData() {
        apiHelper.createRetailerInAdmin(retailerName);
    }

    @AfterClass(alwaysRun = true)
    public void clearData() {
        apiHelper.deleteRetailerByNameInAdmin(retailerName);
    }

    @TmsLink("1417")
    @Story("Страница ритейлеров")
    @Test(description = "Кнопка 'Настройки' раскрывает сайдбар с настройками ритейлера", groups = REGRESSION_ADMIN)
    public void openRetailerSettingsSidebar() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnRetailer(retailerName);

        retailer().waitPageLoad();
        retailer().checkLogoNotAnimated();
        retailer().clickOnSettings();
        retailer().interactiveSettings().checkSidebarVisible();
        retailer().interactiveSettings().checkDrawerTitle("Редактирование настроек ритейлера");
        retailer().interactiveSettings().checkRetailerNameInputValue(retailerName);
    }

    @TmsLink("1418")
    @Story("Страница ритейлеров")
    @Test(description = "Кнопка 'Внешний вид' раскрывает сайдбар с редактированием внешнего вида ритейлера", groups = REGRESSION_ADMIN)
    public void openRetailerAppearanceSidebar() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        retailers().goToPage();
        retailers().checkAddNewRetailerButtonVisible();
        retailers().clickOnRetailer(retailerName);

        retailer().waitPageLoad();
        retailer().checkLogoNotAnimated();
        retailer().clickOnAppearance();
        retailer().interactiveAppearance().checkSidebarVisible();
        retailer().interactiveAppearance().checkDrawerTitle("Редактирование внешнего вида ритейлера");
        retailer().interactiveAppearance().checkUploadLogoButtonVisible();
    }

    @TmsLink("1429")
    @Story("Страница ритейлеров")
    @Test(description = "Кнопка 'Ранжировать список' раскрывает сайдбар с ранжированием списка ритейлеров", groups = REGRESSION_ADMIN)
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
}
