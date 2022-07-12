package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.jdbc.dao.stf.SpreeProductsDao;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.CookieProvider;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Arrays;
import java.util.List;

import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_PREREPLACEMENT_SID;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Предзамены")
public class OrdersPrereplacementsTests {
    //Пользователь должен быть добавлен в А/Б-тест "0a404a10-84f1-4176-89ad-d3fdd9970975" группа "pre_replacements_low"

    private final ApiHelper apiHelper = new ApiHelper();
    private final String product1WithReplacementLink = "tonik-green-mama-uspokaivayuschiy-s-morskimi-vodoroslyami-dlya-kozhi-sklonnoy-k-poyavleniyu-neestetichnoy-krasnoty";
    private final String product2WithReplacementLink = "krem-green-mama-dlya-ustavshih-tyazhelyh-nog-osvezhayuschiy-kashtan-i-propolis";
    private final String productWithOnlyOneReplacementLink = "melok-got2b-strand-up-dlya-volos-okrashivayuschiy-goluboy-denim-3-5-g";
    private final String productWithoutPrereplacementLink = "maslo-de-cecco-extra-virgin-olivkovoe";

    private UserData user;
    private Long product1WithReplacementId, product2WithReplacementId, productWithOnlyOneReplacementId;

    @BeforeClass(alwaysRun = true)
    private void getOfferIdsForCurrentStage() {
        product1WithReplacementId = SpreeProductsDao.INSTANCE.getOfferIdByPermalink(product1WithReplacementLink, DEFAULT_PREREPLACEMENT_SID);
        product2WithReplacementId = SpreeProductsDao.INSTANCE.getOfferIdByPermalink(product2WithReplacementLink, DEFAULT_PREREPLACEMENT_SID);
        productWithOnlyOneReplacementId = SpreeProductsDao.INSTANCE.getOfferIdByPermalink(productWithOnlyOneReplacementLink, DEFAULT_PREREPLACEMENT_SID);
    }

    @CaseId(3267)
    @CookieProvider(cookies = "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE")
    @Test(description = "Выбор предзамены - один товар", groups = {"regression"})
    public void selectPrereplacementFromAlertPopup() {
        user = UserManager.getQaUser();
        apiHelper.setAddress(user, RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(user, DEFAULT_PREREPLACEMENT_SID, List.of(product1WithReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(user);
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

    @CaseId(3268)
    @CookieProvider(cookies = "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE")
    @Test(description = "Выбор предзамены из корзины", groups = {"regression"})
    public void selectPrereplacementFromCart() {
        user = UserManager.getQaUser();
        apiHelper.setAddress(user, RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(user, DEFAULT_PREREPLACEMENT_SID, List.of(product1WithReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(user);
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

    @CaseId(3269)
    @CookieProvider(cookies = "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE")
    @Test(description = "Изменение выбранных предзамен", groups = {"regression"})
    public void editPrereplacement() {
        user = UserManager.getQaUser();
        apiHelper.setAddress(user, RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(user, DEFAULT_PREREPLACEMENT_SID, List.of(product1WithReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(user);
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

    @CaseId(3270)
    @CookieProvider(cookies = "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE")
    @Test(description = "Удаление выбранных предзамен", groups = {"regression"})
    public void removePrereplacement() {
        user = UserManager.getQaUser();
        apiHelper.setAddress(user, RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(user, DEFAULT_PREREPLACEMENT_SID, List.of(product1WithReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(user);
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

    @CaseId(3271)
    @CookieProvider(cookies = "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE")
    @Test(description = "При открытии модалки из попапа отображается весь список товаров с рекомендованными предзаменами", groups = {"regression"})
    public void checkOnlyProductsWithRelacementViewInModal() {
        user = UserManager.getQaUser();
        apiHelper.setAddress(user, RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(user, DEFAULT_PREREPLACEMENT_SID, Arrays.asList(product1WithReplacementId, product2WithReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(user);
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

    @CaseId(3273)
    @CookieProvider(cookies = "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE")
    @Test(description = "Попап при добавлении товара с предзаменами в корзину", groups = {"regression"})
    public void alertDisplayedWhenAddingProduct() {
        user = UserManager.getQaUser();
        apiHelper.setAddress(user, RestAddresses.Moscow.prereplacementAddress());

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(user);
        shop().interactHeader().checkProfileButtonVisible();

        shop().openProductCardByLink(ShopUrl.LENTA, product1WithReplacementLink);
        shop().interactProductCard().clickOnBuy();
        shop().interactProductCard().clickOnClose();
        shop().interactProductCard().checkProductCardIsNotVisible();

        shop().interactHeader().checkPrereplacementPopupDisplayed();
    }

    @CaseId(3274)
    @CookieProvider(cookies = "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE")
    @Test(description = "Повторное отображение попапа при добавлении товара в корзину", groups = {"regression"})
    public void alertDisplayedWhenAddingAnotherProduct() {
        user = UserManager.getQaUser();
        apiHelper.setAddress(user, RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(user, DEFAULT_PREREPLACEMENT_SID, List.of(product1WithReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(user);
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

    @CaseId(3275)
    @CookieProvider(cookies = "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE")
    @Test(description = "Выбор предзамены - любой товар", groups = {"regression"})
    public void selectAnyWillPrereplacementFromAlertPopup() {
        user = UserManager.getQaUser();
        apiHelper.setAddress(user, RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(user, DEFAULT_PREREPLACEMENT_SID, List.of(product1WithReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(user);
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

    @CaseId(3276)
    @CookieProvider(cookies = "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE")
    @Test(description = "Отображение товаров с доступными предзаменами в корзине", groups = {"regression"})
    public void cartPrereplacementCheck() {
        user = UserManager.getQaUser();
        apiHelper.setAddress(user, RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(user, DEFAULT_PREREPLACEMENT_SID, List.of(product1WithReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(user);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToCart();
        shop().interactCart().getFirstItem().checkReplaceButtonDisplayed();
    }

    @CaseId(3277)
    @CookieProvider(cookies = "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE")
    @Test(description = "Отображение товара с единственной предзаменой в модалке", groups = {"regression"})
    public void onlyOneReplacementCheck() {
        user = UserManager.getQaUser();
        apiHelper.setAddress(user, RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(user, DEFAULT_PREREPLACEMENT_SID, List.of(productWithOnlyOneReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(user);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().checkPrereplacementPopupDisplayed();
        shop().interactHeader().clickPrereplacementPopup();

        shop().interactHeader().interactPrereplacementModal().checkPrereplacementModalVisible();
        shop().interactHeader().interactPrereplacementModal().checkItemsForReplaceCountEquals(
                shop().interactHeader().interactPrereplacementModal().getItemsForReplaceCount(),
                1);
        shop().interactHeader().interactPrereplacementModal().checkAnyWillSuitButtonNotDisplayed();
    }

    @CaseId(3278)
    @CookieProvider(cookies = "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE")
    @Test(description = "Отображение товара с несколькими предзаменами в модалке", groups = {"regression"})
    public void severalReplacementCheck() {
        user = UserManager.getQaUser();
        apiHelper.setAddress(user, RestAddresses.Moscow.prereplacementAddress());
        apiHelper.addItemsToCart(user, DEFAULT_PREREPLACEMENT_SID, List.of(product1WithReplacementId));

        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(user);
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
