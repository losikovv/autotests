package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.jdbc.dao.stf.SpreeProductsDao;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.annotation.CookieProvider;
import ru.instamart.reforged.core.enums.ShopUrl;

import java.util.Arrays;
import java.util.List;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_PREREPLACEMENT_SID;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Предзамены")
public final class OrdersPrereplacementsTests {
    //Пользователь должен быть добавлен в А/Б-тест "0a404a10-84f1-4176-89ad-d3fdd9970975" группа "pre_replacements_low"

    private static final ThreadLocal<UserData> userData = new ThreadLocal<>();
    private final ApiHelper apiHelper = new ApiHelper();
    private final String product1WithReplacementLink = "tonik-green-mama-uspokaivayuschiy-s-morskimi-vodoroslyami-dlya-kozhi-sklonnoy-k-poyavleniyu-neestetichnoy-krasnoty";
    private final String product2WithReplacementLink = "krem-green-mama-dlya-ustavshih-tyazhelyh-nog-osvezhayuschiy-kashtan-i-propolis";
    private final String productWithOnlyOneReplacementLink = "melok-got2b-strand-up-dlya-volos-okrashivayuschiy-goluboy-denim-3-5-g";
    private final String productWithoutPrereplacementLink = "maslo-de-cecco-extra-virgin-olivkovoe";

    private Long product1WithReplacementId, product2WithReplacementId, productWithOnlyOneReplacementId;

    @BeforeClass(alwaysRun = true)
    private void getOfferIdsForCurrentStage() {
        product1WithReplacementId = SpreeProductsDao.INSTANCE.getOfferIdByPermalink(product1WithReplacementLink, DEFAULT_PREREPLACEMENT_SID);
        product2WithReplacementId = SpreeProductsDao.INSTANCE.getOfferIdByPermalink(product2WithReplacementLink, DEFAULT_PREREPLACEMENT_SID);
        productWithOnlyOneReplacementId = SpreeProductsDao.INSTANCE.getOfferIdByPermalink(productWithOnlyOneReplacementLink, DEFAULT_PREREPLACEMENT_SID);
    }

    @TmsLink("3267")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Выбор предзамены - один товар", groups = {REGRESSION_STF})
    public void selectPrereplacementFromAlertPopup() {
        userData.set(UserManager.getQaUser());
        apiHelper.setAddress(userData.get(), RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(userData.get(), DEFAULT_PREREPLACEMENT_SID, List.of(product1WithReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().checkPrereplacementPopupDisplayed();
        shop().interactHeader().clickPrereplacementPopup();

        shop().interactHeader().interactPrereplacementModal().checkPrereplacementModalVisible();
        final String itemForReplaceName = shop().interactHeader().interactPrereplacementModal().getItemForReplaceName(1);

        shop().interactHeader().interactPrereplacementModal().selectFirstItemForReplace();
        shop().interactHeader().interactPrereplacementModal().clickConfirm();

        shop().interactHeader().interactPrereplacementModal().checkPrererlacementModalNotVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().getFirstItem().checkPrereplacementBlockDisplayed();
        shop().interactCart().getFirstItem().checkReplacementItemNameEquals(itemForReplaceName);
    }

    @TmsLink("3268")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Выбор предзамены из корзины", groups = {REGRESSION_STF})
    public void selectPrereplacementFromCart() {
        userData.set(UserManager.getQaUser());
        apiHelper.setAddress(userData.get(), RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(userData.get(), DEFAULT_PREREPLACEMENT_SID, List.of(product1WithReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().checkReplaceButtonDisplayed();
        shop().interactCart().getFirstItem().clickSelectReplacement();

        shop().interactHeader().interactPrereplacementModal().checkPrereplacementModalVisible();
        final String itemForReplaceName = shop().interactHeader().interactPrereplacementModal().getItemForReplaceName(1);

        shop().interactHeader().interactPrereplacementModal().selectFirstItemForReplace();
        shop().interactHeader().interactPrereplacementModal().clickConfirm();

        shop().interactHeader().interactPrereplacementModal().checkPrererlacementModalNotVisible();
        shop().interactCart().getFirstItem().checkPrereplacementBlockDisplayed();

        shop().interactCart().getFirstItem().checkReplacementItemNameEquals(itemForReplaceName);

        shop().interactCart().getFirstItem().clickEditReplacement();
        shop().interactHeader().interactPrereplacementModal().checkPrereplacementModalVisible();

        shop().interactHeader().interactPrereplacementModal().clickAnyWillSuit();
        shop().interactHeader().interactPrereplacementModal().clickConfirm();

        shop().interactHeader().interactPrereplacementModal().checkPrererlacementModalNotVisible();
        shop().interactCart().getFirstItem().checkPrereplacementBlockDisplayed();
        shop().interactCart().getFirstItem().checkPrereplacementAnySuiteDisplayed();
    }

    @TmsLink("3269")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Изменение выбранных предзамен", groups = {REGRESSION_STF})
    public void editPrereplacement() {
        userData.set(UserManager.getQaUser());
        apiHelper.setAddress(userData.get(), RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(userData.get(), DEFAULT_PREREPLACEMENT_SID, List.of(product1WithReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().checkReplaceButtonDisplayed();
        shop().interactCart().getFirstItem().clickSelectReplacement();

        shop().interactHeader().interactPrereplacementModal().checkPrereplacementModalVisible();
        String itemForReplaceName = shop().interactHeader().interactPrereplacementModal().getItemForReplaceName(1);

        shop().interactHeader().interactPrereplacementModal().selectFirstItemForReplace();
        shop().interactHeader().interactPrereplacementModal().clickConfirm();

        shop().interactHeader().interactPrereplacementModal().checkPrererlacementModalNotVisible();
        shop().interactCart().getFirstItem().checkPrereplacementBlockDisplayed();

        shop().interactCart().getFirstItem().checkReplacementItemNameEquals(itemForReplaceName);

        shop().interactCart().getFirstItem().clickEditReplacement();
        shop().interactHeader().interactPrereplacementModal().checkPrereplacementModalVisible();

        itemForReplaceName = shop().interactHeader().interactPrereplacementModal().getItemForReplaceName(2);
        shop().interactHeader().interactPrereplacementModal().selectSecondItemForReplace();
        shop().interactHeader().interactPrereplacementModal().clickConfirm();

        shop().interactHeader().interactPrereplacementModal().checkPrererlacementModalNotVisible();
        shop().interactCart().getFirstItem().checkReplacementItemNameEquals(itemForReplaceName);
    }

    @TmsLink("3270")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Удаление выбранных предзамен", groups = {REGRESSION_STF})
    public void removePrereplacement() {
        userData.set(UserManager.getQaUser());
        apiHelper.setAddress(userData.get(), RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(userData.get(), DEFAULT_PREREPLACEMENT_SID, List.of(product1WithReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().checkReplaceButtonDisplayed();
        shop().interactCart().getFirstItem().clickSelectReplacement();

        shop().interactHeader().interactPrereplacementModal().checkPrereplacementModalVisible();

        shop().interactHeader().interactPrereplacementModal().selectFirstItemForReplace();
        shop().interactHeader().interactPrereplacementModal().clickConfirm();

        shop().interactHeader().interactPrereplacementModal().checkPrererlacementModalNotVisible();
        shop().interactCart().getFirstItem().checkPrereplacementBlockDisplayed();

        shop().interactCart().getFirstItem().clickRemoveReplacement();
        shop().interactCart().getFirstItem().checkPrereplacementBlockNotDisplayed();
        shop().interactCart().getFirstItem().checkReplaceButtonDisplayed();
    }

    @TmsLink("3271")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "При открытии модалки из попапа отображается весь список товаров с рекомендованными предзаменами", groups = {REGRESSION_STF})
    public void checkOnlyProductsWithRelacementViewInModal() {
        userData.set(UserManager.getQaUser());
        apiHelper.setAddress(userData.get(), RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(userData.get(), DEFAULT_PREREPLACEMENT_SID, Arrays.asList(product1WithReplacementId, product2WithReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        shop().openProductCardByLink(ShopUrl.LENTA, productWithoutPrereplacementLink);
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().clickOnClose();
        shop().interactProductCard().checkProductCardIsNotVisible();

        shop().interactHeader().checkPrereplacementPopupDisplayed();
        shop().interactHeader().clickPrereplacementPopup();
        shop().interactHeader().interactPrereplacementModal().checkPrereplacementModalVisible();

        shop().interactHeader().interactPrereplacementModal().checkItemsToReplaceCountEquals(
                shop().interactHeader().interactPrereplacementModal().getItemsToReplaceCount(),
                2);
    }

    @TmsLink("3273")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Попап при добавлении товара с предзаменами в корзину", groups = {REGRESSION_STF})
    public void alertDisplayedWhenAddingProduct() {
        userData.set(UserManager.getQaUser());
        apiHelper.setAddress(userData.get(), RestAddresses.Moscow.prereplacementAddress());

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        shop().openProductCardByLink(ShopUrl.LENTA, product1WithReplacementLink);
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().clickOnClose();
        shop().interactProductCard().checkProductCardIsNotVisible();

        shop().interactHeader().checkPrereplacementPopupDisplayed();
    }

    @TmsLink("3274")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Повторное отображение попапа при добавлении товара в корзину", groups = {REGRESSION_STF})
    public void alertDisplayedWhenAddingAnotherProduct() {
        userData.set(UserManager.getQaUser());
        apiHelper.setAddress(userData.get(), RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(userData.get(), DEFAULT_PREREPLACEMENT_SID, List.of(product1WithReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().checkPrereplacementPopupDisplayed();
        shop().interactHeader().closePrereplacementPopup();
        shop().interactHeader().checkPrereplacementPopupNotDisplayed();

        shop().openProductCardByLink(ShopUrl.LENTA, productWithoutPrereplacementLink);
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().clickOnClose();
        shop().interactProductCard().checkProductCardIsNotVisible();
        shop().interactHeader().checkPrereplacementPopupNotDisplayed();

        shop().openProductCardByLink(ShopUrl.LENTA, product2WithReplacementLink);
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().clickOnClose();
        shop().interactProductCard().checkProductCardIsNotVisible();
        shop().interactHeader().checkPrereplacementPopupDisplayed();
    }

    @TmsLink("3275")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Выбор предзамены - любой товар", groups = {REGRESSION_STF})
    public void selectAnyWillPrereplacementFromAlertPopup() {
        userData.set(UserManager.getQaUser());
        apiHelper.setAddress(userData.get(), RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(userData.get(), DEFAULT_PREREPLACEMENT_SID, List.of(product1WithReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().checkPrereplacementPopupDisplayed();
        shop().interactHeader().clickPrereplacementPopup();

        shop().interactHeader().interactPrereplacementModal().checkPrereplacementModalVisible();

        shop().interactHeader().interactPrereplacementModal().clickAnyWillSuit();
        shop().interactHeader().interactPrereplacementModal().clickConfirm();

        shop().interactHeader().interactPrereplacementModal().checkPrererlacementModalNotVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().checkCartOpen();
        shop().interactCart().getFirstItem().checkPrereplacementBlockDisplayed();
        shop().interactCart().getFirstItem().checkPrereplacementAnySuiteDisplayed();
    }

    @TmsLink("3276")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Отображение товаров с доступными предзаменами в корзине", groups = {REGRESSION_STF})
    public void cartPrereplacementCheck() {
        userData.set(UserManager.getQaUser());
        apiHelper.setAddress(userData.get(), RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(userData.get(), DEFAULT_PREREPLACEMENT_SID, List.of(product1WithReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().checkReplaceButtonDisplayed();
    }

    @TmsLink("3277")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Отображение товара с единственной предзаменой в модалке", groups = {REGRESSION_STF})
    public void onlyOneReplacementCheck() {
        userData.set(UserManager.getQaUser());
        apiHelper.setAddress(userData.get(), RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(userData.get(), DEFAULT_PREREPLACEMENT_SID, List.of(productWithOnlyOneReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().checkPrereplacementPopupDisplayed();
        shop().interactHeader().clickPrereplacementPopup();

        shop().interactHeader().interactPrereplacementModal().checkPrereplacementModalVisible();
        shop().interactHeader().interactPrereplacementModal().checkItemsForReplaceCountEquals(
                shop().interactHeader().interactPrereplacementModal().getItemsForReplaceCount(),
                1);
        shop().interactHeader().interactPrereplacementModal().checkAnyWillSuitButtonNotDisplayed();
    }

    @TmsLink("3278")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Отображение товара с несколькими предзаменами в модалке", groups = {REGRESSION_STF})
    public void severalReplacementCheck() {
        userData.set(UserManager.getQaUser());
        apiHelper.setAddress(userData.get(), RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(userData.get(), DEFAULT_PREREPLACEMENT_SID, List.of(product1WithReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData.get());
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().checkPrereplacementPopupDisplayed();
        shop().interactHeader().clickPrereplacementPopup();

        shop().interactHeader().interactPrereplacementModal().checkPrereplacementModalVisible();
        shop().interactHeader().interactPrereplacementModal().checkItemsForReplaceCountEquals(
                shop().interactHeader().interactPrereplacementModal().getItemsForReplaceCount(),
                2);
        shop().interactHeader().interactPrereplacementModal().checkAnyWillSuitButtonDisplayed();
    }
}
