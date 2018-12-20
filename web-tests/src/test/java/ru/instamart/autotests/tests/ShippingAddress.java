package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.testdata.Generate;


// Тесты адреса доставки по Фениксу


public class ShippingAddress extends TestBase{


    @Test(
            description = "Тест на то что по дефолту на витрине ритейлера не выбран адрес",
            groups = {"acceptance","regression"},
            priority = 200
    )
    public void emptyShippingAddressByDefault() throws Exception {
        kraken.get().page("metro");

        Assert.assertFalse(kraken.detect().isShippingAddressSet(),
                "По умолчанию установлен адрес доставки, хотя адрес не должен быть выбран\n");
    }


    @Test(
            description = "Тест дефолтного списка магазинов при отсутствии адреса доставки",
            groups = {"acceptance","regression"},
            priority = 201
    )
    public void checkDefaultShoplist() throws Exception {
        kraken.get().page("metro");
        kraken.shopping().openStoreSelector();

        Assert.assertFalse(kraken.detect().isStoreSelectorOpen(),
                "Не открывается дефолтный список магазинов\n");

        Assert.assertFalse(kraken.detect().isStoreSelectorEmpty(),
                "Дефолтный список магазинов пуст\n");
    }


    @Test(
            description = "Тест отмены ввода адреса доставки на витрине ритейлера",
            groups = {"regression"},
            priority = 202
    )
    public void cancelSetShippingAddressOnRetailerPage() throws Exception {
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
            priority = 203
    )
    public void noAvailableShopsOutOfDeliveryZone() throws Exception {
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.outOfZoneAddress());
        kraken.perform().refresh();
        kraken.perform().switchToActiveElement();
        kraken.shopping().openStoreSelector();

        Assert.assertTrue(kraken.detect().isStoreSelectorOpen(), // TODO тест падает из-за бага в детекторе
                "Не открывается список магазинов вне зоны доставки\n");

        Assert.assertTrue(kraken.detect().isStoreSelectorEmpty(),
                "Не пуст список магазинов с адресом вне зоны доставки\n");
    }


    @Test(
            description = "Тест ввода адреса доставки на витрине ритейлера",
            groups = {"acceptance","regression"},
            priority = 204
    )
    public void setShippingAddressOnRetailerPage() throws Exception {
        kraken.get().page("metro");
        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());

        Assert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Адрес доставки не установлен\n");

        Assert.assertEquals(kraken.grab().currentShipAddress(), Addresses.Moscow.defaultAddress(),
                "Установлен некорректный адрес доставки\n");
    }


    @Test(
            description = "Тест отмены изменения адреса доставки",
            groups = {"regression"},
            priority = 205
    )
    public void cancelChangeShippingAddress() throws Exception {
        kraken.get().page("metro");
        if(!kraken.detect().isShippingAddressSet()) {
            kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        }

        kraken.shipAddress().openAddressModal();
        kraken.shipAddress().fill(Addresses.Moscow.testAddress());
        kraken.shipAddress().closeAddressModal();

        Assert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Адрес доставки сброшен после отмены ввода\n");

        Assert.assertNotEquals(kraken.grab().currentShipAddress(), Addresses.Moscow.testAddress(),
                "Адрес доставки изменён после отмены ввода\n");
    }


    @Test(
            description = "Тест изменения адреса доставки",
            groups = {"acceptance","regression"},
            priority = 206
    )
    public void changeShippingAddress() throws Exception {
        kraken.get().page("metro");
        if(!kraken.detect().isShippingAddressSet()) {
            kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        }

        kraken.shipAddress().change(Addresses.Moscow.testAddress());

        Assert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Адрес доставки сброшен после введения изменений\n");

        Assert.assertEquals(kraken.grab().currentShipAddress(), Addresses.Moscow.testAddress(),
                "Адрес доставки не изменён после введения измений\n");
    }


    @Test(
            description = "Тест изменения адреса на предыдущий из списка адресной модалки",
            groups = {"regression"},
            priority = 207
    )
    public void changeShippingAddressToRecent() throws Exception {
        kraken.perform().quickLogout();
        kraken.perform().registration(Generate.testUserData());
        kraken.shipAddress().set(Addresses.Moscow.testAddress());
        kraken.perform().order();
        kraken.perform().cancelLastOrder();
        kraken.get().baseUrl();
        kraken.shipAddress().change(Addresses.Moscow.defaultAddress());

        kraken.shipAddress().openAddressModal();
        kraken.shipAddress().choseRecent();
        kraken.shipAddress().submit();

        Assert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Адрес доставки сброшен после выбора предыдущего");

        Assert.assertNotEquals(kraken.grab().currentShipAddress(), Addresses.Moscow.testAddress(),
                "Адрес доставки изменен после выбора предыдущего");
    }


    @Test(
            description = "Тест на ввод адреса в модалке после добавления товара из карточки",
            groups = {"regression"},
            priority = 208
    )
    public void setShippingAddressAfterAddOnProductCard() throws Exception {
        kraken.perform().quickLogout();
        kraken.get().page("metro");
        kraken.shopping().openFirstItemCard();
        kraken.shopping().hitPlusButton();

        Assert.assertTrue(kraken.detect().isAddressModalOpen(),
        "Не открывается адресная модалка после добавления товара");

        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        kraken.shopping().closeItemCard();

        Assert.assertTrue(kraken.detect().isItemCardOpen(),
                "Не перезагрузился контент в соответствии с указанным адресом");

        kraken.shopping().closeItemCard();

        Assert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Адрес доставки не был введен");
    }

    @Test(
            description = "Тест на ввод адреса в модалке после добавления товара из каталога",
            groups = {"regression"},
            priority = 209
    )
    public void setShippingAddressAfterAddOnProductList() throws Exception {
        kraken.get().page("metro");
        kraken.shopping().plusSnippetButton();

        Assert.assertTrue(kraken.detect().isAddressModalOpen(),
                "Не открыта адресная модалка после добавления товара");

        kraken.shipAddress().set(Addresses.Moscow.defaultAddress());

        Assert.assertTrue(kraken.detect().isShippingAddressSet(),
                "Адрес доставки не был введен");
    }
}
