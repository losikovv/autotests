package ru.instamart.test.reforged.stf.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Адрес доставки")
public final class UserShippingAddressTests extends BaseTest {

    private final String defaultAddress = Addresses.Moscow.defaultAddress();
    private final String testAddress = Addresses.Moscow.testAddress();
    private final String outOfZoneAddress = Addresses.Moscow.outOfZoneAddress();
    private final ApiHelper helper = new ApiHelper();

    @CaseId(1558)
    @Story("Дефолтные настройки адреса доставки")
    @Test(description = "Тест на то, что по дефолту на витрине ритейлера не выбран адрес", groups = {"acceptance", "regression", "MRAutoCheck"})
    public void noShippingAddressByDefault() {
        shop().goToPage();
        shop().interactHeader().checkIsShippingAddressNotSet();
    }

    @CaseId(1559)
    @Story("Дефолтные настройки адреса доставки")
    @Test(description = "Тест дефолтного списка магазинов, при отсутствии адреса доставки", groups = "regression")
    public void successOperateDefaultShopList() {
        shop().goToPage();
        shop().interactHeader().clickToStoreSelector();
        shop().interactHeader().interactStoreSelector().checkStoreSelectorFrameIsOpen();
        shop().interactHeader().interactStoreSelector().checkStoreSelectorDrawerIsNotEmpty();
        shop().interactHeader().interactStoreSelector().close();
        shop().interactHeader().interactStoreSelector().checkStoreSelectorFrameIsNotOpen();
    }

    @CaseId(31)
    @Story("Сохранение и изменение адреса доставки")
    @Test(description = "Тест отмены ввода адреса доставки на витрине ритейлера", groups = {"acceptance", "regression"})
    public void noShippingAddressSetOnClose() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().checkYmapsReady();
        shop().interactHeader().interactAddress().fillAddress(defaultAddress);
        shop().interactHeader().interactAddress().close();
        shop().interactHeader().interactStoreSelector().checkStoreSelectorFrameIsNotOpen();
        shop().interactHeader().checkIsShippingAddressNotSet();
    }

    @CaseId(2567)
    @Story("Зона доставки")
    @Test(description = "Тест на отсутствие доступных магазинов по адресу вне зоны доставки", groups = "regression")
    public void noAvailableShopsOutOfDeliveryZone() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().checkYmapsReady();
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
    @Test(description = "Тест ввода адреса доставки на витрине ритейлера", groups = {"acceptance", "regression"})
    public void successSetShippingAddressOnRetailerPage() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().checkYmapsReady();
        shop().interactHeader().interactAddress().fillAddress(defaultAddress);
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().checkIsSetAddressEqualToInput(
                defaultAddress,
                shop().interactHeader().getShippingAddressFromHeader()
        );
    }

    @CaseId(33)
    @Story("Сохранение и изменение адреса доставки")
    @Test(description = "Тест отмены изменения адреса доставки", groups = "regression")
    public void noChangeShippingAddressOnCancel() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().checkYmapsReady();
        shop().interactHeader().interactAddress().fillAddress(defaultAddress);
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().checkIsShippingAddressSet();
        shop().goToPage(); //обновляется страница, чтобы получить элемент selectAddress
        shop().interactHeader().clickToSelectAddress();
        shop().interactHeader().interactAddress().checkYmapsReady();
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
    @Test(description = "Тест изменения адреса доставки", groups = {"acceptance", "regression"})
    public void successChangeShippingAddress() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().checkYmapsReady();
        shop().interactHeader().interactAddress().fillAddress(defaultAddress);
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().checkIsShippingAddressSet();
        shop().goToPage(); //обновляется страница, чтобы получить элемент selectAddress
        shop().interactHeader().clickToSelectAddress();
        shop().interactHeader().interactAddress().checkYmapsReady();
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
    @Test(description = "Тест на успешный выбор нового адреса в модалке феникса, после ввода адреса," +
                    " по которому нет доставки текущего ритейлера", groups = "regression")
    public void successSetNewAddressAfterOutOfRetailerZoneAddressChange() {
        shop().goToPage(ShopUrl.LENTA);
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().checkYmapsReady();
        shop().interactHeader().interactAddress().fillAddress(Addresses.Kazan.defaultAddress());
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().interactStoreSelector().clickToChangeAddress();
        shop().interactHeader().interactAddress().checkYmapsReady();
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
    @Test(description = "Тест на успешный выбор нового адреса в модалке феникса, после ввода адреса вне зоны доставки", groups = "regression")
    public void successSetNewAddressAfterOutOfZoneAddressChange() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().checkYmapsReady();
        shop().interactHeader().interactAddress().fillAddress(outOfZoneAddress);
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().interactAddress().clickToChangeAddress();
        shop().interactHeader().interactAddress().checkYmapsReady();
        shop().interactHeader().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().checkIsSetAddressEqualToInput(
                Addresses.Moscow.defaultAddress(),
                shop().interactHeader().getShippingAddressFromHeader()
        );
    }

    //Какие то непонятки с адресами
    @Skip
    @CaseId(35)
    @Story("Сохранение и изменение адреса доставки")
    @Test(description = "Тест изменения адреса на предыдущий из списка адресной модалки", groups = "regression")
    public void successChangeShippingAddressToRecent() {
        final var user = UserManager.getQaUser();
        this.helper.makeAndCancelOrder(user, EnvironmentProperties.DEFAULT_SID, 1);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(user);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToSelectAddress();
        shop().interactHeader().interactAddress().checkYmapsReady();
        final var firstPrevAdr = shop().interactHeader().interactAddress().getFirstPrevAddress();
        shop().interactHeader().interactAddress().clickOnFirstPrevAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().checkIsSetAddressEqualToInput(
                shop().interactHeader().getShippingAddressFromHeader(),
                firstPrevAdr);
    }

    @CaseId(1567)
    @Story("Сохранение и изменение адреса доставки")
    @Test(description = "Тест на ввод адреса в модалке, после добавления товара из каталога", groups = "regression")
    public void successSetShippingAddressAfterAddingProductFromCatalog() {
        shop().goToPage();
        shop().plusFirstItemToCart();
        shop().interactHeader().interactAddress().checkYmapsReady();
        shop().interactHeader().interactAddress().fillAddress(defaultAddress);
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().checkIsShippingAddressSet();
        shop().checkSnippet();
    }

    @CaseId(2570)
    @Story("Сохранение и изменение адреса доставки")
    @Test(description = "Адрес сохраняется при регистрации нового пользователя", groups = "regression")
    public void testSuccessSaveAddressAfterRegistration() {
        home().goToPage();
        home().clickToSetAddress();
        home().interactAddressModal().checkYmapsReadyTmp();
        home().interactAddressModal().fillAddressTmp(defaultAddress);
        home().interactAddressModal().selectFirstAddressTmp();
        home().interactAddressModal().clickFindStores();
        home().clickToFirstRetailer();
        shop().interactHeader().checkEnteredAddressIsVisible();

        //TODO: Костыль из-за бейсика
        shop().goToPage(ShopUrl.AUCHAN);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().authViaPhone(UserManager.getQaUser());
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().interactHeader().checkIsSetAddressEqualToInput(
                defaultAddress,
                shop().interactHeader().getShippingAddressFromHeader()
        );
    }

    @CaseId(2573)
    @Story("Сохранение и изменение адреса доставки")
    @Test(description = "Автовыбор магазина того же ретейлера после изменения адреса доставки", groups = "regression")
    public void testAutoSelectAddressAfterChangeShipmentAddress() {
        shop().goToPage();

        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().clickOnSave();
        shop().interactHeader().checkLoginIsVisible();
        shop().checkPageContains("metro?sid=1");

        shop().goToPage();

        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.testAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().clickOnSave();
        shop().interactHeader().checkLoginIsVisible();
        shop().checkPageContains("metro?sid=12");
    }

    @CaseId(2575)
    @Story("Сохранение и изменение адреса доставки")
    @Test(description = "Сохранение нескольких адресов за пользователем при оформлении заказа", groups = "regression")
    public void testSuccessFewAddressesOnCheckout() {
        var userData = UserManager.getQaUser();
        this.helper.makeAndCancelOrder(userData, EnvironmentProperties.DEFAULT_SID, 2);
        this.helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);
        this.helper.setAddress(userData, RestAddresses.Chelyabinsk.defaultAddress());

        shop().goToPage();
        shop().addCookie(CookieFactory.COOKIE_ALERT);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();

        checkout().setDeliveryOptions().fillApartment("30");
        checkout().setDeliveryOptions().fillFloor("10");
        checkout().setDeliveryOptions().fillEntrance("3");
        checkout().setDeliveryOptions().fillDoorPhone("30");
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setFirstActiveSlot();

        checkout().setPayment().clickToByCardToCourier();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().checkCountOfStoredAddresses(2);
        shop().interactAddress().checkStoredAddresses(
                shop().interactAddress().getStoredAddress(0),
                RestAddresses.Chelyabinsk.defaultAddress().fullAddress().add("кв. 30").toString()
        );
    }

    @Test(description = "Тест на успешный выбор нового магазина в модалке феникса, после изменения адреса доставки", groups = "regression")
    public void successSelectNewStoreAfterShipAddressChange() {
        shop().goToPage(ShopUrl.VKUSVILL);
        shop().interactHeader().clickToSelectAddress();
        shop().interactHeader().interactAddress().checkYmapsReady();
        shop().interactHeader().interactAddress().fillAddress(Addresses.Kazan.defaultAddress());
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().interactStoreSelector().clickToStoreCard();
        shop().plusFirstItemToCart();
        shop().interactHeader().interactStoreSelector().checkStoreSelectorFrameIsNotOpen();
    }
}