package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;


// Тесты адреса доставки по Фениксу


public class ShippingAddress extends TestBase{


    @Test(
            description = "Тест на то что по дефолту на витрине ритейлера не выбран адрес",
            groups = {"acceptance","regression"},
            priority = 301
    )
    public void noShippingAddressByDefault() {
        kraken.perform().quickLogout();
        kraken.get().page("metro");

        Assert.assertFalse(kraken.detect().isShippingAddressSet(),
                "По умолчанию установлен адрес доставки, хотя адрес не должен быть выбран\n");
    }


    @Test(
            description = "Тест дефолтного списка магазинов при отсутствии адреса доставки",
            groups = {"acceptance","regression"},
            priority = 302
    )
    public void successOperateDefaultShoplist() {
        kraken.get().page("metro");
        kraken.shopping().openStoreSelector();

        Assert.assertTrue(kraken.detect().isStoreSelectorOpen(),
                "Не открывается дефолтный список магазинов\n");

        Assert.assertFalse(kraken.detect().isStoreSelectorEmpty(),
                "Дефолтный список магазинов пуст\n");

        kraken.shopping().closeStoreSelector();

        Assert.assertFalse(kraken.detect().isStoreSelectorOpen(),
                "Не закрывается дефолтный список магазинов\n");


    }


    @Test(
            description = "Тест отмены ввода адреса доставки на витрине ритейлера",
            groups = {"regression"},
            priority = 303
    )
    public void noSetShippingAddressOnCancel() {
        kraken.get().page("metro");
        kraken.shipAddress().openAddressModal();
        kraken.shipAddress().fill(Addresses.Moscow.defaultAddress());
        kraken.shipAddress().closeAddressModal();

        Assert.assertFalse(kraken.detect().isShippingAddressSet(),
                "Адрес доставки установлен после отмены ввода\n");
    }


    @Test(
            description = "Тест на отсутствие доступных магазинов по адресу вне зоны доставки",
            groups = {"regression"},
            priority = 304
    )
    public void noAvailableShopsOutOfDeliveryZone() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.outOfZoneAddress());
        kraken.perform().refresh();
        kraken.perform().switchToActiveElement();
        kraken.shopping().openStoreSelector();

        softAssert.assertTrue(kraken.detect().isStoreSelectorOpen(), // TODO тест падает из-за бага в детекторе
                "Не открывается список магазинов вне зоны доставки\n");

        softAssert.assertTrue(kraken.detect().isStoreSelectorEmpty(),
                "Не пуст список магазинов с адресом вне зоны доставки\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Тест ввода адреса доставки на витрине ритейлера",
            groups = {"acceptance","regression"},
            priority = 305
    )
    public void successSetShippingAddressOnRetailerPage() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());

        softAssert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Адрес доставки не установлен\n");

        softAssert.assertEquals(kraken.grab().currentShipAddress(), Addresses.Moscow.defaultAddress(),
                "Установлен некорректный адрес доставки\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Тест отмены изменения адреса доставки",
            groups = {"regression"},
            priority = 306
    )
    public void noChangeShippingAddressOnCancel() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page("metro");
        if(!kraken.detect().isShippingAddressSet()) {
            kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        }

        kraken.shipAddress().openAddressModal();
        kraken.shipAddress().fill(Addresses.Moscow.testAddress());
        kraken.shipAddress().closeAddressModal();

        softAssert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Адрес доставки сброшен после отмены ввода\n");

        softAssert.assertNotEquals(kraken.grab().currentShipAddress(), Addresses.Moscow.testAddress(),
                "Адрес доставки изменён после отмены ввода\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Тест изменения адреса доставки",
            groups = {"acceptance","regression"},
            priority = 307
    )
    public void successChangeShippingAddress() {
        SoftAssert softAssert = new SoftAssert();

        kraken.get().page("metro");
        if(!kraken.detect().isShippingAddressSet()) {
            kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        }

        kraken.shipAddress().change(Addresses.Moscow.testAddress());

        softAssert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Адрес доставки сброшен после введения изменений\n");

        softAssert.assertEquals(kraken.grab().currentShipAddress(), Addresses.Moscow.testAddress(),
                "Адрес доставки не изменён после введения измений\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Тест изменения адреса на предыдущий из списка адресной модалки",
            groups = {"regression"},
            priority = 308
    )
    public void successChangeShippingAddressToRecent() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().quickLogout();
        kraken.perform().registration();
        kraken.shipAddress().set(Addresses.Moscow.testAddress());
        kraken.perform().order();
        kraken.perform().cancelLastOrder();
        kraken.get().baseUrl();
        kraken.shipAddress().change(Addresses.Moscow.defaultAddress());

        kraken.shipAddress().openAddressModal();
        kraken.shipAddress().choseRecent();
        kraken.shipAddress().submit();

        softAssert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Адрес доставки сброшен после выбора предыдущего");

        softAssert.assertNotEquals(kraken.grab().currentShipAddress(), Addresses.Moscow.testAddress(),
                "Адрес доставки изменен после выбора предыдущего");

        softAssert.assertAll();
    }


    @Test(
            description = "Тест на ввод адреса в модалке после добавления товара из карточки",
            groups = {"regression"},
            priority = 309
    )
    public void successSetShippingAddressAfterAddingProductFromItemCard() {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().quickLogout();
        kraken.get().page("metro");
        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitPlusButton();

        softAssert.assertTrue(kraken.detect().isAddressModalOpen(),
        "Не открывается адресная модалка после добавления товара");

        kraken.shipAddress().set(Addresses.Moscow.defaultAddress()); // TODO обработать кейс когда после ввода адреса товар недоступен
        kraken.shopping().closeItemCard();

        softAssert.assertTrue(kraken.detect().isElementPresent(Elements.Site.Catalog.product()),
                "Не перезагрузился контент в соответствии с указанным адресом");

        softAssert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Адрес доставки не был введен");

        softAssert.assertAll();
    }


    @Test(
            description = "Тест на ввод адреса в модалке после добавления товара из каталога",
            groups = {"regression"},
            priority = 310
    )
    public void successSetShippingAddressAfterAddingProductFromCatalog() {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().quickLogout();
        kraken.get().page("metro");
        kraken.shopping().hitFirstItemPlusButton();

        softAssert.assertTrue(kraken.detect().isAddressModalOpen(),
                "Не открывается адресная модалка после добавления товара");

        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());

        softAssert.assertTrue(kraken.detect().isElementPresent(Elements.Site.Catalog.product()),
                "Не перезагрузился контент в соответствии с указанным адресом");

        softAssert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Адрес доставки не был введен");

        softAssert.assertAll();
    }


    @Test(
            description = "Тест на успешный выбор нового магазина в модалке феникса после изменения адреса доставки",
            groups = {"regression"},
            priority = 311
    )
    public void successSelectNewStoreAfterShipAddressChange() {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().quickLogout();
        kraken.get().page("vkusvill");
        kraken.shipAddress().change(Addresses.Kazan.defaultAddress());

        softAssert.assertTrue(kraken.detect().isChangeStoreModalOpen(),
                "Не открывается модалка с магазинами доступными по новому адресу");

        kraken.perform().click(Elements.Site.StoresModal.firstStoreAvailable());
        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitPlusButton();

        softAssert.assertFalse(kraken.detect().isChangeStoreModalOpen(),
                "В модалке не выбирается магазин по новому адресу");

        softAssert.assertAll();
    }


    @Test(
            description = "Тест на успешный выбор нового адреса в модалке феникса после ввода адреса," +
                    " по которому нет доставки текущего ритейлера",
            groups = {"regression"},
            priority = 312
    )
    public void successSetNewAddressAfterOutOfRetailerZoneAddressChange() {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().quickLogout();
        kraken.get().page("lenta");
        kraken.shipAddress().change(Addresses.Kazan.defaultAddress());

        softAssert.assertTrue(kraken.detect().isChangeStoreModalOpen(),
                "Не открывается модалка с магазинами доступными по новому адресу");

        kraken.perform().click(Elements.Site.StoresModal.pickNewAddressButton());
        kraken.shipAddress().set(Addresses.Moscow.testAddress());
        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitPlusButton();

        softAssert.assertFalse(kraken.detect().isChangeStoreModalOpen(),
                "В модалке не выбирается новый адрес");

        softAssert.assertAll();
    }

    
    @Test(
            description = "Тест на успешный выбор нового адреса в модалке феникса после ввода адреса," +
                    " по которому совсем нет доставки",
            groups = {"regression"},
            priority = 313
    )
    public void successSetNewAddressAfterOutOfZoneAddressChange() {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().quickLogout();
        kraken.get().page("metro");
        kraken.shipAddress().change(Addresses.Moscow.outOfZoneAddress());

        softAssert.assertTrue(kraken.detect().isAddressOutOfZone(),
                "Не открывается модалка Адрес вне зоны доставки");

        kraken.perform().click(Elements.Site.AddressModal.pickNewAddressButton());
        kraken.shipAddress().set(Addresses.Moscow.testAddress());
        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitPlusButton();

        softAssert.assertFalse(kraken.detect().isChangeStoreModalOpen(),
                "В модалке не выбирается новый адрес");

        softAssert.assertAll();
    }

}
