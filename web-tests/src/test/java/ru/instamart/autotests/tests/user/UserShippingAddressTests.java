package ru.instamart.autotests.tests.user;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.libs.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.appmanager.platform.Shop;
import ru.instamart.autotests.appmanager.platform.User;
import ru.instamart.autotests.tests.TestBase;

public class UserShippingAddressTests extends TestBase {

    @Test(
            description = "Тест на то что по дефолту на витрине ритейлера не выбран адрес",
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 301
    )
    public void noShippingAddressByDefault() {
        SoftAssert softAssert = new SoftAssert();
        User.Do.quickLogout();
        kraken.get().page("metro");

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
            groups = {
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 302
    )
    public void successOperateDefaultShoplist() {
        kraken.get().page("metro");
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
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 303
    )
    public void noShippingAddressSetOnClose() {
        kraken.get().page("metro");
        Shop.ShippingAddress.openAddressModal();
        Shop.ShippingAddress.fill(Addresses.Moscow.defaultAddress());
        Shop.ShippingAddress.closeAddressModal();

        Assert.assertFalse(
                kraken.detect().isShippingAddressSet(),
                    "Адрес доставки установлен после отмены ввода закрытием модалки\n");
    }

    @Test(
            description = "Тест на отсутствие доступных магазинов по адресу вне зоны доставки",
            groups = {
                    "regression",
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 304
    )
    public void noAvailableShopsOutOfDeliveryZone() {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");
        Shop.ShippingAddress.set(Addresses.Moscow.outOfZoneAddress());

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
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 305
    )
    public void successSetShippingAddressOnRetailerPage() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page("metro");
        Shop.ShippingAddress.set(Addresses.Moscow.defaultAddress());

        softAssert.assertTrue(
                kraken.detect().isShippingAddressSet(),
                    "\n> Адрес доставки не установлен");

        softAssert.assertEquals(
                kraken.grab().currentShipAddress(), Addresses.Moscow.defaultAddress(),
                    "\n> Установленный адрес доставки не совпадает с введенным");

        softAssert.assertAll();
    }

    @Test(
            description = "Тест отмены изменения адреса доставки",
            groups = {
                    "regression",
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 306
    )
    public void noChangeShippingAddressOnCancel() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page("metro");
        if(!kraken.detect().isShippingAddressSet()) {
            Shop.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        }

        Shop.ShippingAddress.openAddressModal();
        Shop.ShippingAddress.fill(Addresses.Moscow.testAddress());
        Shop.ShippingAddress.closeAddressModal();

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
                    "acceptance", "regression",
                    "metro-acceptance", "metro-regression",
                    "sbermarket-acceptance","sbermarket-regression"
            },
            priority = 307
    )
    public void successChangeShippingAddress() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page("metro");
        if(!kraken.detect().isShippingAddressSet()) {
            Shop.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        }

        Shop.ShippingAddress.change(Addresses.Moscow.testAddress());

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
                    "regression",
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 308
    )
    public void successChangeShippingAddressToRecent() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        User.Do.quickLogout();
        User.Do.registration();
        Shop.ShippingAddress.set(Addresses.Moscow.testAddress());
        kraken.perform().order();
        kraken.perform().cancelLastOrder();
        kraken.get().baseUrl();
        Shop.ShippingAddress.change(Addresses.Moscow.defaultAddress());

        Shop.ShippingAddress.openAddressModal();
        Shop.ShippingAddress.choseRecent();
        Shop.ShippingAddress.submit();

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
                    "regression",
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 309
    )
    public void successSetShippingAddressAfterAddingProductFromItemCard() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        User.Do.quickLogout();
        kraken.get().page("metro");
        Shop.Catalog.Item.addToCart();
        Shop.ItemCard.addToCart();

        softAssert.assertTrue(kraken.detect().isAddressModalOpen(),
        "Не открывается адресная модалка после добавления товара");

        Shop.ShippingAddress.set(Addresses.Moscow.defaultAddress()); // TODO обработать кейс когда после ввода адреса товар недоступен
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
                    "regression",
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 310
    )
    public void successSetShippingAddressAfterAddingProductFromCatalog() {
        SoftAssert softAssert = new SoftAssert();

        User.Do.quickLogout();
        kraken.get().page("metro");
        Shop.Catalog.Item.addToCart();

        softAssert.assertTrue(
                kraken.detect().isAddressModalOpen(),
                    "\n> Не открывается адресная модалка после добавления товара");

        Shop.ShippingAddress.set(Addresses.Moscow.defaultAddress());

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
                    "regression",
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 311
    )
    public void successSelectNewStoreAfterShipAddressChange() {
        SoftAssert softAssert = new SoftAssert();

        User.Do.quickLogout();
        kraken.get().page("vkusvill");
        Shop.ShippingAddress.change(Addresses.Kazan.defaultAddress());

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
                    "regression",
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 312
    )
    public void successSetNewAddressAfterOutOfRetailerZoneAddressChange() {
        SoftAssert softAssert = new SoftAssert();

        User.Do.quickLogout();
        kraken.get().page("lenta");
        Shop.ShippingAddress.change(Addresses.Kazan.defaultAddress());

        softAssert.assertTrue(
                kraken.detect().isChangeStoreModalOpen(),
                    "\n> Не открывается модалка с магазинами доступными по новому адресу");

        kraken.perform().click(Elements.Modals.StoresModal.pickNewAddressButton());
        Shop.ShippingAddress.set(Addresses.Moscow.testAddress());
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
                    "regression",
                    "metro-regression",
                    "sbermarket-regression"
            },
            priority = 313
    )
    public void successSetNewAddressAfterOutOfZoneAddressChange() {
        SoftAssert softAssert = new SoftAssert();

        User.Do.quickLogout();
        kraken.get().page("metro");
        Shop.ShippingAddress.change(Addresses.Moscow.outOfZoneAddress());

        softAssert.assertTrue(
                kraken.detect().isAddressOutOfZone(),
                    "\n> Не открывается модалка Адрес вне зоны доставки");

        kraken.perform().click(Elements.Modals.AddressModal.pickNewAddressButton());
        Shop.ShippingAddress.set(Addresses.Moscow.testAddress());
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToCart();

        softAssert.assertFalse(
                kraken.detect().isChangeStoreModalOpen(),
                    "\n> В модалке не выбирается новый адрес");

        softAssert.assertAll();
    }
}
