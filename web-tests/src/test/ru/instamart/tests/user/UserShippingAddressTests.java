package ru.instamart.tests.user;

import instamart.core.settings.Config;
import instamart.ui.common.lib.Addresses;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.TestBase;

public class UserShippingAddressTests extends TestBase {

    @Test(
            description = "Тест на то что по дефолту на витрине ритейлера не выбран адрес",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 301
    )
    public void noShippingAddressByDefault() {
        SoftAssert softAssert = new SoftAssert();
        User.Logout.quickly();
        kraken.get().page(Config.CoreSettings.defaultRetailer);

        softAssert.assertFalse(
                kraken.detect().isShippingAddressSet(),
                    "\n> По дефолту выбран адрес доставки\n");

        softAssert.assertTrue(
                kraken.detect().isElementPresent(Elements.Header.shipAddressPlaceholder()),
                    "\n> Не отображается плейсхолдер при невыбранном адресе доставки\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест дефолтного списка магазинов при отсутствии адреса доставки",
            priority = 302,
            groups = {
                    "sbermarket-acceptance","sbermarket-regression"
            }
    )
    public void successOperateDefaultShoplist() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        Shop.StoreSelector.open();

        Assert.assertTrue(
                kraken.detect().isStoreSelectorOpen(),
                    "\n> Не открывается дефолтный список магазинов\n");

        Assert.assertFalse(
                kraken.detect().isStoreSelectorEmpty(),
                    "\n> Дефолтный список магазинов пуст\n");

        Shop.StoreSelector.close();

        Assert.assertFalse(
                kraken.detect().isStoreSelectorOpen(),
                    "\n> Не закрывается дефолтный список магазинов\n");
    }

    //TODO successOperateShoplist()

    @Test(
            description = "Тест отмены ввода адреса доставки на витрине ритейлера",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 303
    )
    public void noShippingAddressSetOnClose() {
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        Shop.ShippingAddressModal.open();
        Shop.ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        Shop.ShippingAddressModal.close();

        Assert.assertFalse(
                kraken.detect().isShippingAddressSet(),
                    "Адрес доставки установлен после отмены ввода закрытием модалки\n");
    }

    @Test(
            description = "Тест на отсутствие доступных магазинов по адресу вне зоны доставки",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 304
    )
    public void noAvailableShopsOutOfDeliveryZone() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        User.ShippingAddress.set(Addresses.Moscow.outOfZoneAddress());

        Shop.StoreSelector.open();

        softAssert.assertTrue(
                kraken.detect().isStoreSelectorOpen(),
                    "\n> Не открывается список магазинов вне зоны доставки\n");

        softAssert.assertTrue(
                kraken.detect().isStoreSelectorEmpty(),
                    "\n> Не пуст список магазинов с адресом вне зоны доставки\n");

        Shop.StoreSelector.close();

        Assert.assertFalse(
                kraken.detect().isStoreSelectorOpen(),
                    "Не закрывается пустой список магазинов\n");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест ввода адреса доставки на витрине ритейлера",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 305
    )
    public void successSetShippingAddressOnRetailerPage() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Config.CoreSettings.defaultRetailer);
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());

        softAssert.assertTrue(
                kraken.detect().isShippingAddressSet(),
                    "\n> Адрес доставки не установлен");

        softAssert.assertEquals(
                kraken.grab().currentShipAddress(), Addresses.Moscow.defaultAddress(),
                    "\n> Установленный адрес доставки не совпадает с введенным"
                            +"\n> Установлен адрес: " + kraken.grab().currentShipAddress()
                            +"\n> Ожидаемый адрес: " + Addresses.Moscow.defaultAddress()
        );

        softAssert.assertAll();
    }

    @Test(
            description = "Тест отмены изменения адреса доставки",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 306
    )
    public void noChangeShippingAddressOnCancel() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Config.CoreSettings.defaultRetailer);
        if(!kraken.detect().isShippingAddressSet()) {
            User.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        }

        Shop.ShippingAddressModal.open();
        Shop.ShippingAddressModal.fill(Addresses.Moscow.testAddress());
        Shop.ShippingAddressModal.close();

        softAssert.assertTrue(
                kraken.detect().isShippingAddressSet(),
                    "\n> Адрес доставки сброшен после отмены ввода");

        softAssert.assertNotEquals(
                kraken.grab().currentShipAddress(), Addresses.Moscow.testAddress(),
                    "\n> Адрес доставки изменён после отмены ввода");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест изменения адреса доставки",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 307
    )
    public void successChangeShippingAddress() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page(Config.CoreSettings.defaultRetailer);
        if(!kraken.detect().isShippingAddressSet()) {
            User.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        }

        User.ShippingAddress.set(Addresses.Moscow.testAddress());

        softAssert.assertTrue(
                kraken.detect().isShippingAddressSet(),
                    "\n> Адрес доставки сбрасывается при попытке изменения");

        softAssert.assertEquals(
                kraken.grab().currentShipAddress(), Addresses.Moscow.testAddress(),
                    "\n> Не изменяется адрес доставки");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест изменения адреса на предыдущий из списка адресной модалки",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 308
    )
    public void successChangeShippingAddressToRecent() {
        SoftAssert softAssert = new SoftAssert();

        User.Logout.quickly();
        User.Do.registration();
        User.ShippingAddress.set(Addresses.Moscow.testAddress());
        kraken.perform().order();
        kraken.perform().cancelOrder();
        kraken.get().baseUrl();
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());

        Shop.ShippingAddressModal.open();
        Shop.ShippingAddressModal.chooseRecentAddress();
        Shop.ShippingAddressModal.submit();

        softAssert.assertTrue(
                kraken.detect().isShippingAddressSet(),
                    "\n> Адрес доставки сброшен после выбора предыдущего");

        softAssert.assertNotEquals(
                kraken.grab().currentShipAddress(), Addresses.Moscow.testAddress(),
                    "\n> Адрес доставки изменен после выбора предыдущего");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест на ввод адреса в модалке после добавления товара из карточки",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 309
    )
    public void successSetShippingAddressAfterAddingProductFromItemCard() {
        SoftAssert softAssert = new SoftAssert();

        User.Logout.quickly();
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        Shop.Catalog.Item.addToCart();
        Shop.ItemCard.addToCart();

        softAssert.assertTrue(kraken.detect().isAddressModalOpen(),
        "Не открывается адресная модалка после добавления товара");

        User.ShippingAddress.set(Addresses.Moscow.defaultAddress()); // TODO обработать кейс когда после ввода адреса товар недоступен
        Shop.ItemCard.close();

        softAssert.assertTrue(
                kraken.detect().isElementPresent(Elements.Catalog.Product.snippet()),
                    "\n> Не перезагрузился контент в соответствии с указанным адресом");

        softAssert.assertTrue(
                kraken.detect().isShippingAddressSet(),
                    "\n> Адрес доставки не был введен");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест на ввод адреса в модалке после добавления товара из каталога",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 310
    )
    public void successSetShippingAddressAfterAddingProductFromCatalog() {
        SoftAssert softAssert = new SoftAssert();

        User.Logout.quickly();
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        Shop.Catalog.Item.addToCart();

        softAssert.assertTrue(
                kraken.detect().isAddressModalOpen(),
                    "\n> Не открывается адресная модалка после добавления товара");

        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());

        softAssert.assertTrue(
                kraken.detect().isElementPresent(Elements.Catalog.Product.snippet()),
                    "\n> Не перезагрузился контент в соответствии с указанным адресом");

        softAssert.assertTrue(
                kraken.detect().isShippingAddressSet(),
                    "\n> Адрес доставки не был введен");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест на успешный выбор нового магазина в модалке феникса после изменения адреса доставки",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 311
    )
    public void successSelectNewStoreAfterShipAddressChange() {
        SoftAssert softAssert = new SoftAssert();

        User.Logout.quickly();
        kraken.get().page("vkusvill");
        User.ShippingAddress.set(Addresses.Kazan.defaultAddress());

        softAssert.assertTrue(
                kraken.detect().isChangeStoreModalOpen(),
                    "Не открывается модалка с магазинами доступными по новому адресу");

        kraken.perform().click(Elements.Modals.StoresModal.firstStoreAvailable()); // TODO Сделать метод в Shop
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToCart();

        softAssert.assertFalse(
                kraken.detect().isChangeStoreModalOpen(),
                    "В модалке не выбирается магазин по новому адресу");

        softAssert.assertAll();
    }


    @Test(
            description = "Тест на успешный выбор нового адреса в модалке феникса после ввода адреса," +
                    " по которому нет доставки текущего ритейлера",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 312
    )
    public void successSetNewAddressAfterOutOfRetailerZoneAddressChange() {
        SoftAssert softAssert = new SoftAssert();

        User.Logout.quickly();
        kraken.get().page("lenta");
        User.ShippingAddress.set(Addresses.Kazan.defaultAddress());

        softAssert.assertTrue(
                kraken.detect().isChangeStoreModalOpen(),
                    "\n> Не открывается модалка с магазинами доступными по новому адресу");

        kraken.perform().click(Elements.Modals.StoresModal.pickNewAddressButton());
        User.ShippingAddress.set(Addresses.Moscow.testAddress());
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToCart();

        softAssert.assertFalse(
                kraken.detect().isChangeStoreModalOpen(),
                    "\n> В модалке не выбирается новый адрес");

        softAssert.assertAll();
    }
    
    @Test(
            description = "Тест на успешный выбор нового адреса в модалке феникса, после ввода адреса вне зоны доставки",
            groups = {
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 313
    )
    public void successSetNewAddressAfterOutOfZoneAddressChange() {
        SoftAssert softAssert = new SoftAssert();

        User.Logout.quickly();
        kraken.get().page(Config.CoreSettings.defaultRetailer);
        User.ShippingAddress.set(Addresses.Moscow.outOfZoneAddress());

        softAssert.assertTrue(
                kraken.detect().isAddressOutOfZone(),
                    "\n> Не открывается модалка Адрес вне зоны доставки");

        kraken.perform().click(Elements.Modals.AddressModal.pickNewAddressButton());
        User.ShippingAddress.set(Addresses.Moscow.testAddress());
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToCart();

        softAssert.assertFalse(
                kraken.detect().isChangeStoreModalOpen(),
                    "\n> В модалке не выбирается новый адрес");

        softAssert.assertAll();
    }
}
