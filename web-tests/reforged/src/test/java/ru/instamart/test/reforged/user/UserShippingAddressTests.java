package ru.instamart.test.reforged.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.lib.Addresses;
import ru.instamart.reforged.stf.page.shop.ShopPage;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.core.service.KrakenDriver.refresh;
import static ru.instamart.reforged.stf.page.StfRouter.home;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Адрес доставки")
public class UserShippingAddressTests extends BaseTest {

    String defaultAddress = Addresses.Moscow.defaultAddress();
    String testAddress = Addresses.Moscow.testAddress();
    String outOfZoneAddress = Addresses.Moscow.outOfZoneAddress();
    private final ApiHelper helper = new ApiHelper();

    @CaseId(1558)
    @Story("Дефолтные настройки адреса доставки")
    @Test(
            description = "Тест на то, что по дефолту на витрине ритейлера не выбран адрес",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-Ui-smoke", "MRAutoCheck", "ui-smoke-production"
            }
    )
    public void noShippingAddressByDefault() {
        home().openSitePage(Config.DEFAULT_RETAILER);
        shop().interactHeader().checkIsShippingAddressNotSet();
    }

    @CaseId(1559)
    @Story("Дефолтные настройки адреса доставки")
    @Test(
            description = "Тест дефолтного списка магазинов, при отсутствии адреса доставки",
            groups = {"sbermarket-Ui-smoke", "ui-smoke-production"}
    )
    public void successOperateDefaultShopList() {
        home().openSitePage(Config.DEFAULT_RETAILER);

        shop().interactHeader().clickToStoreSelector();
        shop().interactHeader().interactStoreSelector().checkStoreSelectorFrameIsOpen();
        shop().interactHeader().interactStoreSelector().checkStoreSelectorDrawerIsNotEmpty();
        shop().interactHeader().interactStoreSelector().close();
        shop().interactHeader().interactStoreSelector().checkStoreSelectorFrameIsNotOpen();
    }

    @CaseId(31)
    @Story("Сохранение и изменение адреса доставки")
    @Test(
            description = "Тест отмены ввода адреса доставки на витрине ритейлера",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-Ui-smoke", "ui-smoke-production"
            }
    )
    public void noShippingAddressSetOnClose() {
        home().openSitePage(Config.DEFAULT_RETAILER);

        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().fillAddress(defaultAddress);
        shop().interactHeader().interactAddress().close();
        shop().interactHeader().interactStoreSelector().checkStoreSelectorFrameIsNotOpen();
        shop().interactHeader().checkIsShippingAddressNotSet();
    }

    @CaseId(1562)
    @Story("Зона доставки")
    @Test(
            description = "Тест на отсутствие доступных магазинов по адресу вне зоны доставки",
            groups = {
                    "metro-regression", "sbermarket-Ui-smoke", "ui-smoke-production"
            }
    )
    public void noAvailableShopsOutOfDeliveryZone() {
        home().openSitePage(Config.DEFAULT_RETAILER);

        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().fillAddress(outOfZoneAddress);
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().interactAddress().checkIsAddressOutOfZone();
        shop().interactHeader().interactAddress().close();
        shop().interactHeader().clickToStoreSelector();
        shop().interactHeader().interactStoreSelector().checkStoreSelectorDrawerIsEmpty();
        shop().interactHeader().interactStoreSelector().close();
        shop().interactHeader().interactStoreSelector().checkStoreSelectorFrameIsNotOpen();
    }

    @CaseId(32)
    @Story("Сохранение и изменение адреса доставки")
    @Test(
            description = "Тест ввода адреса доставки на витрине ритейлера",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-Ui-smoke", "ui-smoke-production"
            }
    )
    public void successSetShippingAddressOnRetailerPage() {
        home().openSitePage(Config.DEFAULT_RETAILER);

        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().fillAddress(defaultAddress);
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().checkIsSetAddressEqualToInput(
                defaultAddress,
                shop().interactHeader().getShippingAddressFromHeader());
    }

    @CaseId(33)
    @Story("Сохранение и изменение адреса доставки")
    @Test(
            description = "Тест отмены изменения адреса доставки",
            groups = {
                    "metro-regression",
                    "sbermarket-Ui-smoke", "ui-smoke-production"
            }
    )
    public void noChangeShippingAddressOnCancel() {
        home().openSitePage(Config.DEFAULT_RETAILER);

        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().fillAddress(defaultAddress);
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().checkIsShippingAddressSet();
        refresh(); //обновляется страница, чтобы получить элемент selectAddress
        shop().interactHeader().clickToSelectAddress();
        shop().interactHeader().interactAddress().fillAddress(testAddress);
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().close();
        shop().interactHeader().checkIsSetAddressEqualToInput(
                defaultAddress,
                shop().interactHeader().getShippingAddressFromHeader()
        );
    }

    @CaseId(34)
    @Story("Сохранение и изменение адреса доставки")
    @Test(
            description = "Тест изменения адреса доставки",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-Ui-smoke", "ui-smoke-production"
            }
    )
    public void successChangeShippingAddress() {
        home().openSitePage(Config.DEFAULT_RETAILER);
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().fillAddress(defaultAddress);
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().checkIsShippingAddressSet();
        refresh(); //обновляется страница, чтобы получить элемент selectAddress
        shop().interactHeader().clickToSelectAddress();
        shop().interactHeader().interactAddress().fillAddress(testAddress);
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().checkIsSetAddressEqualToInput(
                testAddress,
                shop().interactHeader().getShippingAddressFromHeader()
        );
    }

    @CaseId(1569)
    @Story("Зона доставки")
    @Test(
            description = "Тест на успешный выбор нового адреса в модалке феникса, после ввода адреса," +
                    " по которому нет доставки текущего ритейлера",
            groups = {
                    "metro-regression", "sbermarket-Ui-smoke", "ui-smoke-production"
            }
    )
    public void successSetNewAddressAfterOutOfRetailerZoneAddressChange() {
        shop().goToPage(ShopPage.ShopUrl.LENTA);
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().fillAddress(Addresses.Kazan.defaultAddress());
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().interactStoreSelector().clickToChangeAddress();
        shop().interactHeader().interactAddress().fillAddress(testAddress);
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().checkIsSetAddressEqualToInput(
                testAddress,
                shop().interactHeader().getShippingAddressFromHeader()
        );
    }

    @CaseId(1570)
    @Story("Зона доставки")
    @Test(
            description = "Тест на успешный выбор нового адреса в модалке феникса, после ввода адреса вне зоны доставки",
            groups = {
                    "metro-regression", "sbermarket-Ui-smoke", "ui-smoke-production"
            }
    )
    public void successSetNewAddressAfterOutOfZoneAddressChange() {
        home().openSitePage(Config.DEFAULT_RETAILER);
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().fillAddress(outOfZoneAddress);
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().interactAddress().clickToChangeAddress();
        shop().interactHeader().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().checkIsSetAddressEqualToInput(
                Addresses.Moscow.defaultAddress(),
                shop().interactHeader().getShippingAddressFromHeader()
        );
    }

    @Skip
    @CaseId(35)
    @Story("Сохранение и изменение адреса доставки")
    @Test(
            description = "Тест изменения адреса на предыдущий из списка адресной модалки",
            groups = {
                    "metro-regression",
                    "sbermarket-Ui-smoke"
            }
    )
    public void successChangeShippingAddressToRecent() {
        UserData user = UserManager.addressUser();
        String firstPrevAdr;
        helper.makeAndCancelOrder(user, 1, 1);

        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(user);
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillDefaultSMSWithSleep();
        shop().interactHeader().clickToSelectAddress();
        shop().interactHeader().interactAddress().fillAddress(defaultAddress);
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        refresh();
        shop().interactHeader().clickToSelectAddress();
        firstPrevAdr = shop().interactHeader().interactAddress().getFirstPrevAddress();
        shop().interactHeader().interactAddress().clickOnFirstPrevAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().checkIsSetAddressEqualToInput(
                shop().interactHeader().getShippingAddressFromHeader(),
                firstPrevAdr);
    }

    @Skip
    @CaseId(1567)
    @Story("Сохранение и изменение адреса доставки")
    @Test(
            description = "Тест на ввод адреса в модалке, после добавления товара из каталога",
            groups = {
                    "metro-regression",
                    "sbermarket-Ui-smoke", "ui-smoke-production"
            }
    )
    public void successSetShippingAddressAfterAddingProductFromCatalog() {
        home().openSitePage(Config.DEFAULT_RETAILER);
        shop().plusFirstItemToCartOld();
        shop().interactHeader().interactAddress().fillAddress(defaultAddress);
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().checkIsShippingAddressSet();
        shop().checkOldSnippet();
    }

    @Skip
    @Test(
            description = "Тест на успешный выбор нового магазина в модалке феникса, после изменения адреса доставки",
            groups = {
                    "metro-regression",
                    "sbermarket-Ui-smoke", "ui-smoke-production"
            }
    )
    public void successSelectNewStoreAfterShipAddressChange() {
        shop().goToPage(ShopPage.ShopUrl.VKUSVILL);
        shop().interactHeader().clickToSelectAddress();
        shop().interactHeader().interactAddress().fillAddress(Addresses.Kazan.defaultAddress());
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().interactStoreSelector().clickToStoreCard();
        shop().plusFirstItemToCartOld();
        shop().interactHeader().interactStoreSelector().checkStoreSelectorFrameIsNotOpen();
    }
}