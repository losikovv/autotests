package ru.instamart.test.reforged.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.testdata.lib.Addresses;
import ru.instamart.reforged.stf.page.shop.ShopPage;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Адрес доставки")
public class UserShippingAddressTests extends BaseTest {

    @CaseId(1558)
    @Story("Дефолтные настройки адреса доставки")
    @Test(
            description = "Тест на то что по дефолту на витрине ритейлера не выбран адрес",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-Ui-smoke", "MRAutoCheck", "ui-smoke-production"
            }
    )
    public void noShippingAddressByDefault() {

        shop().goToPage(ShopPage.ShopUrl.METRO);
        shop().interactHeader().checkIsShippingAddressNotSet();
    }

    @CaseId(1559)
    @Story("Дефолтные настройки адреса доставки")
    @Test(
            description = "Тест дефолтного списка магазинов при отсутствии адреса доставки",
            groups = {"sbermarket-Ui-smoke", "ui-smoke-production"}
    )
    public void successOperateDefaultShopList() {

        shop().goToPage(ShopPage.ShopUrl.METRO);
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

        shop().goToPage(ShopPage.ShopUrl.METRO);
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().setAddress(Addresses.Moscow.defaultAddress());
        shop().interactHeader().interactAddress().close();
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

        shop().goToPage(ShopPage.ShopUrl.METRO);
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().setAddress(Addresses.Moscow.outOfZoneAddress());
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

        String defaultAddress = Addresses.Moscow.defaultAddress();
        String currentAddress;

        shop().goToPage(ShopPage.ShopUrl.METRO);
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().setAddress(defaultAddress);
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        currentAddress = shop().interactHeader().getShippingAddressFromHeader();
        shop().interactHeader().checkIsSetAddressEqualsToInput(defaultAddress, currentAddress);
    }
}