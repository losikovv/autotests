package ru.instamart.tests.user;

import instamart.core.helpers.ConsoleOutputCapturerHelper;
import instamart.core.settings.Config;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.checkpoints.users.ShippingAddressCheckpoints;
import instamart.ui.checkpoints.users.UsersAuthorizationCheckpoints;
import instamart.ui.common.lib.Addresses;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import io.qameta.allure.Allure;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.TestBase;

public class UserShippingAddressTests extends TestBase {

    ConsoleOutputCapturerHelper capture = new ConsoleOutputCapturerHelper();
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    UsersAuthorizationCheckpoints authChecks = new UsersAuthorizationCheckpoints();
    ShippingAddressCheckpoints shippingChecks = new ShippingAddressCheckpoints();
    String env = System.getProperty("env", Config.CoreSettings.defaultEnvironment);

    @BeforeMethod(alwaysRun = true,
            description ="Проверяем залогинен ли пользователь, если да то завершаем сессию")
    public void quickLogout() {
        capture.start();
        User.Logout.quickly();
    }
    @AfterMethod(alwaysRun = true,
            description ="Добавление сообщений из консоли в лог теста")
    public void afterTest(){
        String value = capture.stop();
        Allure.addAttachment("Системный лог теста",value);
    }

    @Test(
            description = "Тест на то что по дефолту на витрине ритейлера не выбран адрес",
            groups = {
                    "metro-acceptance", "metro-regression","testing",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 301
    )
    public void noShippingAddressByDefault() {
        SoftAssert softAssert = new SoftAssert();
        User.Logout.quickly();
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        shippingChecks.checkIsShippingAddressNotSet("Выбираем дефолтный адрес доставки");
        baseChecks.checkIsElementPresent(Elements.Header.shipAddressPlaceholder(),
                "Не отображается плейсхолдер при невыбранном адресе доставки");
    }

    @Test(
            description = "Тест дефолтного списка магазинов при отсутствии адреса доставки",
            priority = 302,
            groups = {
                    "sbermarket-acceptance","sbermarket-regression","testing"
            }
    )
    public void successOperateDefaultShoplist() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        Shop.StoresDrawer.open();
        shippingChecks.checkIsStoresDrawerOpen("Не открывается дефолтный список магазинов");
        shippingChecks.checkIsStoresDrawerNotEmpty("Дефолтный список магазинов пуст");
        Shop.StoresDrawer.close();
        shippingChecks.checkIsStoresDrawerNotOpen("Не закрывается дефолтный список магазинов");
    }

    //TODO successOperateShoplist()

    @Test(
            description = "Тест отмены ввода адреса доставки на витрине ритейлера",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression","testing"
            },
            priority = 303
    )
    public void noShippingAddressSetOnClose() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        Shop.ShippingAddressModal.open();
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),false);
        Shop.ShippingAddressModal.close();
        shippingChecks.checkIsShippingAddressNotSet("Отмены ввода закрытием модалки");
    }

    @Test(
            description = "Тест на отсутствие доступных магазинов по адресу вне зоны доставки",
            groups = {
                    "metro-regression",
                    "sbermarket-regression","testing"
            },
            priority = 304
    )
    public void noAvailableShopsOutOfDeliveryZone() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        User.ShippingAddress.set(Addresses.Moscow.outOfZoneAddressMoscow(),true);
        shippingChecks.checkIsAddressOutOfZone(
                "Не открывается модалка Адрес вне зоны доставки");
        //kraken.perform().click(Elements.Modals.AddressModal.pickNewAddressButton());
        Shop.StoresDrawer.open();
        shippingChecks.checkIsStoresDrawerOpen("Не открывается список магазинов вне зоны доставки");
        shippingChecks.checkIsStoresDrawerEmpty("Не пуст список магазинов с адресом вне зоны доставки");
        Shop.StoresDrawer.close();
        shippingChecks.checkIsStoresDrawerNotOpen("Не закрывается пустой список магазинов");
    }

    @Test(
            description = "Тест ввода адреса доставки на витрине ритейлера",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression","testing"
            },
            priority = 305
    )
    public void successSetShippingAddressOnRetailerPage() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
        shippingChecks.checkIsShippingAddressSet("Адрес доставки не установлен");
        shippingChecks.checkIsSetAddresEqualsToInput(Addresses.Moscow.defaultAddress(),
                kraken.grab().currentShipAddress());
    }

    @Test(
            description = "Тест отмены изменения адреса доставки",
            groups = {
                    "metro-regression",
                    "sbermarket-regression","testing"
            },
            priority = 306
    )
    public void noChangeShippingAddressOnCancel() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        if(!kraken.detect().isShippingAddressSet()) {
            User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
        }
        Shop.ShippingAddressModal.open();
        User.ShippingAddress.set(Addresses.Moscow.testAddress(),false);
        Shop.ShippingAddressModal.close();
        shippingChecks.checkIsShippingAddressSet("Адрес доставки сброшен после отмены ввода");
        baseChecks.checkIsStringValuesNotEquals(kraken.grab().currentShipAddress(),Addresses.Moscow.testAddress(),
                "адрес доставки не изменился после отмены ввода",
                "Адрес доставки изменён после отмены ввода");
    }

    @Test(
            description = "Тест изменения адреса доставки",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression","testing"
            },
            priority = 307
    )
    public void successChangeShippingAddress() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        if(!kraken.detect().isShippingAddressSet()) {
            User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
        }
        User.ShippingAddress.set(Addresses.Moscow.testAddress(),true);
        shippingChecks.checkIsShippingAddressSet("Адрес доставки сбрасывается при попытке изменения");
        shippingChecks.checkIsSetAddresEqualsToInput(Addresses.Moscow.testAddress(),kraken.grab().currentShipAddress());
    }

    @Test(
            description = "Тест изменения адреса на предыдущий из списка адресной модалки",
            groups = {
                    "metro-regression",
                    "sbermarket-regression","testing"
            },
            priority = 308
    )
    public void successChangeShippingAddressToRecent() {
        if(env.contains("production")){skipTest();}
        //ToDo логика чекаута сильно изменилась, нужна дополнительная отладка на стейдже
        User.Logout.quickly();
        User.Do.registration();
        User.ShippingAddress.set(Addresses.Moscow.testAddress(),true);
        kraken.perform().order();
        kraken.perform().cancelOrder();
        kraken.get().baseUrl();
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);

        Shop.ShippingAddressModal.open();
        Shop.ShippingAddressModal.chooseRecentAddress();
        Shop.ShippingAddressModal.submit();

        shippingChecks.checkIsShippingAddressSet("Адрес доставки сброшен после выбора предыдущего");
        shippingChecks.checkIsSetAddressDoesntEqualToInput(kraken.grab().currentShipAddress(),
                Addresses.Moscow.testAddress());
    }

    @Test(
            description = "Тест на ввод адреса в модалке после добавления товара из каталога",
            groups = {
                    "metro-regression",
                    "sbermarket-regression","testing"
            },
            priority = 310
    )
    public void successSetShippingAddressAfterAddingProductFromCatalog() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        Shop.Catalog.Item.addToCart();
        shippingChecks.checkIsAddressModalOpen("Не открывается адресная модалка после добавления товара");
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
        baseChecks.checkIsElementPresent(Elements.Catalog.Product.snippet(),
                "Не перезагрузился контент в соответствии с указанным адресом");
        shippingChecks.checkIsShippingAddressSet("Адрес доставки не был введен");
    }

    @Test(
            description = "Тест на успешный выбор нового магазина в модалке феникса после изменения адреса доставки",
            groups = {
                    "metro-regression",
                    "sbermarket-regression","testing"
            },
            priority = 311
    )
    public void successSelectNewStoreAfterShipAddressChange() {
        kraken.get().page("vkusvill");
        User.ShippingAddress.set(Addresses.Kazan.defaultAddress(),true);
        shippingChecks.checkisChangeStoreModalOpen(
                "Не открывается модалка с магазинами доступными по новому адресу");
        Shop.StoresModal.selectFirstStore();
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToCart();
        shippingChecks.checkIsChangeStoreModalNotOpen(
                "В модалке выбирается магазин по новому адресу, но не должен");
    }


    @Test(
            description = "Тест на успешный выбор нового адреса в модалке феникса после ввода адреса," +
                    " по которому нет доставки текущего ритейлера",
            groups = {
                    "metro-regression",
                    "sbermarket-regression","testing"
            },
            priority = 312
    )
    public void successSetNewAddressAfterOutOfRetailerZoneAddressChange() {
        kraken.get().page("lenta");
        User.ShippingAddress.set(Addresses.Kazan.defaultAddress(),true);
        shippingChecks.checkisChangeStoreModalOpen(
                "Не открывается модалка с магазинами доступными по новому адресу");
        kraken.perform().click(Elements.Modals.StoresModal.pickNewAddressButton());
        User.ShippingAddress.set(Addresses.Moscow.testAddress(),true);
        Shop.StoresModal.selectFirstStore();
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToCart();
        shippingChecks.checkIsChangeStoreModalNotOpen("В модалке не выбирается новый адрес");
    }
    
    @Test(
            description = "Тест на успешный выбор нового адреса в модалке феникса, после ввода адреса вне зоны доставки",
            groups = {
                    "metro-regression",
                    "sbermarket-regression","testing"
            },
            priority = 313
    )
    public void successSetNewAddressAfterOutOfZoneAddressChange() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        User.ShippingAddress.set(Addresses.Moscow.outOfZoneAddress(),true);
        shippingChecks.checkIsAddressOutOfZone(
                "Не открывается модалка Адрес вне зоны доставки");
        kraken.perform().click(Elements.Modals.AddressModal.pickNewAddressButton());
        User.ShippingAddress.set(Addresses.Moscow.testAddress(),true);
        Shop.StoresModal.selectFirstStore();
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToCart();
        shippingChecks.checkIsChangeStoreModalNotOpen("В модалке не выбирается новый адрес");
    }
}
