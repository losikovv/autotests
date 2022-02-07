package ru.instamart.test.reforged.stf.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.TestVariables;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Адрес доставки")
public final class UserShippingAddressTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private final String defaultAddress = Addresses.Moscow.defaultAddress();
    private final String testAddress = Addresses.Moscow.testAddress();
    private final String outOfZoneAddress = Addresses.Moscow.outOfZoneAddress();

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
        shop().interactHeader().interactAddress().fillAddress(defaultAddress);
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().checkIsSetAddressEqualToInput(defaultAddress, shop().interactHeader().getShippingAddressFromHeader());
    }

    //Какие то непонятки с адресами
    @CaseId(35)
    @Story("Сохранение и изменение адреса доставки")
    @Test(enabled = false, description = "Тест изменения адреса на предыдущий из списка адресной модалки", groups = "regression")
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

    @CaseIDs({@CaseId(2568), @CaseId(2570)})
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
        shop().interactHeader().checkIsSetAddressEqualToInput(defaultAddress,
                shop().interactHeader().getShippingAddressFromHeader());

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
        shop().interactAddress().fillAddress(defaultAddress);
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().clickOnSave();
        shop().interactHeader().checkLoginIsVisible();
        shop().checkPageContains("metro?sid=1");

        shop().goToPage();

        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(testAddress);
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().clickOnSave();
        shop().interactHeader().checkLoginIsVisible();
        shop().checkPageContains("metro?sid=12");
    }

    @CaseIDs({@CaseId(2576), @CaseId(2574)})
    @Story("Сохранение и изменение адреса доставки")
    @Test(description = "Сохранение адреса за пользователем при оформлении заказа", groups = "regression")
    public void saveAddressForNextOrder() {
        final var addressData = TestVariables.testAddressData();
        final var userData = UserManager.getQaUser();
        this.helper.makeAndCancelOrder(userData, EnvironmentProperties.DEFAULT_SID, 2);
        this.helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

        shop().goToPage();
        shop().addCookie(CookieFactory.COOKIE_ALERT);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();

        checkout().setDeliveryOptions().fillApartment(addressData.getApartment());
        checkout().setDeliveryOptions().fillFloor(addressData.getFloor());
        checkout().setDeliveryOptions().fillEntrance(addressData.getEntrance());
        checkout().setDeliveryOptions().fillDoorPhone(addressData.getDomofon());
        checkout().setDeliveryOptions().fillComments(addressData.getComments());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardToCourier();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        helper.dropAndFillCartWithoutSetAddress(userData, EnvironmentProperties.DEFAULT_SID);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();

        checkout().setDeliveryOptions().checkApartmentValue(checkout().setDeliveryOptions().getApartmentValue(), addressData.getApartment());
        checkout().setDeliveryOptions().checkFloorValue(checkout().setDeliveryOptions().getFloorValue(), addressData.getFloor());
        checkout().setDeliveryOptions().checkEntranceValue(checkout().setDeliveryOptions().getEntranceValue(), addressData.getEntrance());
        checkout().setDeliveryOptions().checkDoorPhoneValue(checkout().setDeliveryOptions().getDoorPhoneValue(), addressData.getDomofon());
        checkout().setDeliveryOptions().checkCommentsValue(checkout().setDeliveryOptions().getCommentsValue(), addressData.getComments());
        checkout().assertAll();
    }

    @CaseId(2575)
    @Story("Сохранение и изменение адреса доставки")
    @Test(description = "Сохранение нескольких адресов за пользователем при оформлении заказа", groups = "regression")
    public void testSuccessFewAddressesOnCheckout() {
        final var userData = UserManager.getQaUser();
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

        checkout().setSlot().setLastActiveSlot();

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

    @CaseId(2577)
    @Story("Сохранение и изменение адреса доставки")
    @Test(description = "Редактирование параметров сохраненного адреса", groups = "regression")
    public void editSavedParamsAddressOnCheckoutPage() {
        final var addressData = TestVariables.testAddressData();
        final var addressChangeData = TestVariables.testChangeAddressData();
        final var userData = UserManager.getQaUser();
        this.helper.makeAndCancelOrder(userData, EnvironmentProperties.DEFAULT_SID, 2);
        this.helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

        shop().goToPage();
        shop().addCookie(CookieFactory.COOKIE_ALERT);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().checkModalIsVisible();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();

        checkout().setDeliveryOptions().fillApartment(addressData.getApartment());
        checkout().setDeliveryOptions().fillFloor(addressData.getFloor());
        checkout().setDeliveryOptions().fillEntrance(addressData.getEntrance());
        checkout().setDeliveryOptions().fillDoorPhone(addressData.getDomofon());
        checkout().setDeliveryOptions().fillComments(addressData.getComments());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardToCourier();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        this.helper.dropAndFillCartWithoutSetAddress(userData, EnvironmentProperties.DEFAULT_SID);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();

        checkout().setDeliveryOptions().fillApartment(addressChangeData.getApartment());
        checkout().setDeliveryOptions().fillFloor(addressChangeData.getFloor());
        checkout().setDeliveryOptions().fillEntrance(addressChangeData.getEntrance());
        checkout().setDeliveryOptions().fillDoorPhone(addressChangeData.getDomofon());
        checkout().setDeliveryOptions().fillComments(addressChangeData.getComments());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardToCourier();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        this.helper.dropAndFillCartWithoutSetAddress(userData, EnvironmentProperties.DEFAULT_SID);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();

        checkout().setDeliveryOptions().checkApartmentValue(checkout().setDeliveryOptions().getApartmentValue(), addressChangeData.getApartment());
        checkout().setDeliveryOptions().checkFloorValue(checkout().setDeliveryOptions().getFloorValue(), addressChangeData.getFloor());
        checkout().setDeliveryOptions().checkEntranceValue(checkout().setDeliveryOptions().getEntranceValue(), addressChangeData.getEntrance());
        checkout().setDeliveryOptions().checkDoorPhoneValue(checkout().setDeliveryOptions().getDoorPhoneValue(), addressChangeData.getDomofon());
        checkout().setDeliveryOptions().checkCommentsValue(checkout().setDeliveryOptions().getCommentsValue(), addressChangeData.getComments());
        checkout().assertAll();
    }

    @CaseId(1568)
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

    @CaseId(2569)
    @Test(description = "Ввод адреса доступен в каталоге магазина (новый пользователь)", groups = "regression")
    public void successSelectAddressAfterRegistration() {
        final var userData = UserManager.getQaUser();

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

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

    @CaseId(2571)
    @Test(description = "Адрес и магазин сохраняется при авторизации пользователя с ранее выбранным другим адресом и магазином", groups = "regression")
    public void saveAddressAfterAuth() {
        final var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Ekaterinburg.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().checkYmapsReady();
        shop().interactHeader().interactAddress().fillAddress(defaultAddress);
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().interactAddress().checkAddressModalIsNotVisible();

        //TODO: Костыль из-за бейсика
        shop().goToPage(ShopUrl.METRO);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().checkIsSetAddressEqualToInput(
                defaultAddress,
                shop().interactHeader().getShippingAddressFromHeader()
        );
    }

    @CaseId(2572)
    @Test(description = "Выбранный адрес не попадает в зону доставки ритейлера", groups = "regression")
    public void selectedAddressIsOutOfDeliveryRange() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactHeader().interactAddress().checkYmapsReady();
        shop().interactHeader().interactAddress().fillAddress("Мытищи, Лётная улица, 17");
        shop().interactHeader().interactAddress().selectFirstAddress();
        shop().interactHeader().interactAddress().clickOnSave();
        shop().interactHeader().interactAddress().checkAddressModalIsNotVisible();

        shop().goToPage(ShopUrl.VKUSVILL);
        shop().plusFirstItemToCart();
        shop().interactStoreModal().checkStoreModalIsOpen();
    }
}