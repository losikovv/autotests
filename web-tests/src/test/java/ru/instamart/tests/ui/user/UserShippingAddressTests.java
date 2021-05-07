package ru.instamart.tests.ui.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.core.settings.Config;
import ru.instamart.tests.ui.TestBase;
import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import ru.instamart.ui.checkpoints.shipping.ShippingAddressCheckpoints;
import ru.instamart.ui.common.lib.Addresses;
import ru.instamart.ui.modules.Shop;
import ru.instamart.ui.modules.User;
import ru.instamart.ui.modules.shop.Order;
import ru.instamart.ui.modules.shop.ShippingAddressModal;
import ru.instamart.ui.objectsmap.Elements;

@Epic("STF UI")
@Feature("Адрес доставки")
public class UserShippingAddressTests extends TestBase {

    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    ShippingAddressCheckpoints shippingChecks = new ShippingAddressCheckpoints();

    @BeforeMethod(alwaysRun = true,
            description ="Проверяем залогинен ли пользователь, если да то завершаем сессию")
    public void quickLogout() {
        User.Logout.quickly();
    }

    @CaseId(1558)
    @Story("Дефолтные настройки адреса доставки")
    @Test(
            description = "Тест на то что по дефолту на витрине ритейлера не выбран адрес",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-Ui-smoke","MRAutoCheck","ui-smoke-production"
            }
    )
    public void noShippingAddressByDefault() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        shippingChecks.checkIsShippingAddressNotSet("Выбираем дефолтный адрес доставки");
        baseChecks.checkIsElementPresent(Elements.Header.shipAddressPlaceholder());
    }

    @CaseId(1559)
    @Story("Дефолтные настройки адреса доставки")
    @Test(
            description = "Тест дефолтного списка магазинов при отсутствии адреса доставки",
            groups = {"sbermarket-Ui-smoke","ui-smoke-production"}
    )
    public void successOperateDefaultShoplist() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.StoresDrawer.open();
        shippingChecks.checkIsStoresDrawerOpen("Не открывается дефолтный список магазинов");
        shippingChecks.checkIsStoresDrawerNotEmpty("Дефолтный список магазинов пуст");
        Shop.StoresDrawer.close();
        shippingChecks.checkIsStoresDrawerNotOpen("Не закрывается дефолтный список магазинов");
    }

    //TODO successOperateShoplist()

    @CaseId(31)
    @Story("Сохранение и изменение адреса доставки")
    @Test(
            description = "Тест отмены ввода адреса доставки на витрине ритейлера",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-Ui-smoke","ui-smoke-production"
            }
    )
    public void noShippingAddressSetOnClose() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.close();
        shippingChecks.checkIsShippingAddressNotSet("Отмены ввода закрытием модалки");
    }

    @CaseId(1562)
    @Story("Зона доставки")
    @Test(
            description = "Тест на отсутствие доступных магазинов по адресу вне зоны доставки",
            groups = {
                    "metro-regression", "sbermarket-Ui-smoke","ui-smoke-production"
            }
    )
    public void noAvailableShopsOutOfDeliveryZone() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.outOfZoneAddress());
        ShippingAddressModal.selectAddressSuggest();
        ShippingAddressModal.submit();
        shippingChecks.checkIsAddressOutOfZone(
                "Не открывается модалка Адрес вне зоны доставки");
        ShippingAddressModal.close();
        Shop.StoresDrawer.open();
        shippingChecks.checkIsStoresDrawerOpen("Не открывается список магазинов вне зоны доставки");
        shippingChecks.checkIsStoresDrawerEmpty("Не пуст список магазинов с адресом вне зоны доставки");
        Shop.StoresDrawer.close();
        shippingChecks.checkIsStoresDrawerNotOpen("Не закрывается пустой список магазинов");
    }

    @CaseId(32)
    @Story("Сохранение и изменение адреса доставки")
    @Test(
            description = "Тест ввода адреса доставки на витрине ритейлера",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-Ui-smoke","ui-smoke-production"
            }
    )
    public void successSetShippingAddressOnRetailerPage() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.selectAddressSuggest();
        ShippingAddressModal.submit();
        shippingChecks.checkIsShippingAddressSet("Адрес доставки не установлен");
        shippingChecks.checkIsSetAddresEqualsToInput(Addresses.Moscow.defaultAddress(),
                kraken.grab().currentShipAddress());
    }

    @CaseId(33)
    @Story("Сохранение и изменение адреса доставки")
    @Test(
            description = "Тест отмены изменения адреса доставки",
            groups = {
                    "metro-regression",
                    "sbermarket-Ui-smoke","ui-smoke-production"
            }
    )
    public void noChangeShippingAddressOnCancel() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.selectAddressSuggest();
        ShippingAddressModal.submit();
        shippingChecks.checkIsShippingAddressSet("Адрес доставки сброшен после отмены ввода");
        ShippingAddressModal.open();
        ShippingAddressModal.fill("");
        ShippingAddressModal.fill(Addresses.Moscow.testAddress());
        ShippingAddressModal.selectAddressSuggest();
        ShippingAddressModal.close();
        baseChecks.checkIsStringValuesNotEquals(kraken.grab().currentShipAddress(),Addresses.Moscow.testAddress(),
                "адрес доставки не изменился после отмены ввода",
                "Адрес доставки изменён после отмены ввода");
    }

    @CaseId(34)
    @Story("Сохранение и изменение адреса доставки")
    @Test(
            description = "Тест изменения адреса доставки",
            groups = {
                    "metro-acceptance", "metro-regression",
                    "sbermarket-Ui-smoke","ui-smoke-production"
            }
    )
    public void successChangeShippingAddress() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.selectAddressSuggest();
        ShippingAddressModal.submit();
        shippingChecks.checkIsShippingAddressSet("Адрес доставки сбрасывается при попытке изменения");
        ShippingAddressModal.open();
        ShippingAddressModal.fill("");
        ShippingAddressModal.fill(Addresses.Moscow.testAddress());
        ShippingAddressModal.selectAddressSuggest();
        ShippingAddressModal.submit();
        shippingChecks.checkIsSetAddresEqualsToInput(Addresses.Moscow.testAddress(),kraken.grab().currentShipAddress());
    }

    @CaseId(35)
    @Story("Сохранение и изменение адреса доставки")
    @Test(
            description = "Тест изменения адреса на предыдущий из списка адресной модалки",
            groups = {
                    "metro-regression",
                    "sbermarket-Ui-smoke"
            },enabled = false
    )
    public void successChangeShippingAddressToRecent() {
        User.Logout.quickly();
        User.Do.registration();
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.submit();
        Order.order();
        Order.cancelOrder();
        kraken.get().baseUrl();
        ShippingAddressModal.open();
        ShippingAddressModal.chooseRecentAddress();
        ShippingAddressModal.submit();

        shippingChecks.checkIsShippingAddressSet("Адрес доставки сброшен после выбора предыдущего");
        shippingChecks.checkIsSetAddressDoesntEqualToInput(kraken.grab().currentShipAddress(),
                Addresses.Moscow.testAddress());
    }

    @CaseId(1567)
    @Story("Сохранение и изменение адреса доставки")
    @Test(
            description = "Тест на ввод адреса в модалке после добавления товара из каталога",
            groups = {
                    "metro-regression",
                    "sbermarket-Ui-smoke","ui-smoke-production"
            }
    )
    public void successSetShippingAddressAfterAddingProductFromCatalog() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        Shop.Catalog.Item.addToCart();
        shippingChecks.checkIsAddressModalOpen("Не открывается адресная модалка после добавления товара");
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.selectAddressSuggest();
        ShippingAddressModal.submit();
        baseChecks.checkIsElementPresent(Elements.Catalog.Product.snippet());
        shippingChecks.checkIsShippingAddressSet("Адрес доставки не был введен");
    }

    @Test(
            description = "Тест на успешный выбор нового магазина в модалке феникса после изменения адреса доставки",
            groups = {
                    "metro-regression",
                    "sbermarket-Ui-smoke","ui-smoke-production"
            },enabled = false
    )
    public void successSelectNewStoreAfterShipAddressChange() {
        kraken.get().page("vkusvill");
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Kazan.defaultAddress());
        ShippingAddressModal.selectAddressSuggest();
        ShippingAddressModal.submit();
        shippingChecks.checkIsChangeStoreModalOpen(
                "Не открывается модалка с магазинами доступными по новому адресу");
        Shop.StoresModal.selectFirstStore();
        Shop.Catalog.Item.open();
        Shop.ItemCard.addToCart();
        shippingChecks.checkIsChangeStoreModalNotOpen(
                "В модалке выбирается магазин по новому адресу, но не должен");
    }

    @CaseId(1569)
    @Story("Зона доставки")
    @Test(
            description = "Тест на успешный выбор нового адреса в модалке феникса после ввода адреса," +
                    " по которому нет доставки текущего ритейлера",
            groups = {
                    "metro-regression", "sbermarket-Ui-smoke","ui-smoke-production"
            }
    )
    public void successSetNewAddressAfterOutOfRetailerZoneAddressChange() {
        kraken.get().page("lenta");
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Kazan.defaultAddress());
        ShippingAddressModal.selectAddressSuggest();
        ShippingAddressModal.submit();
        shippingChecks.checkIsChangeStoreModalOpen(
                "Не открывается модалка с магазинами доступными по новому адресу");
        ShippingAddressModal.selectNewAdressRetailer();
        ShippingAddressModal.fill("");
        ShippingAddressModal.fill(Addresses.Moscow.testAddress());
        ShippingAddressModal.selectAddressSuggest();
        ShippingAddressModal.submit();
        shippingChecks.checkIsSetAddresEqualsToInput(Addresses.Moscow.testAddress(),
                kraken.grab().currentShipAddress());
    }

    @CaseId(1570)
    @Story("Зона доставки")
    @Test(
            description = "Тест на успешный выбор нового адреса в модалке феникса, после ввода адреса вне зоны доставки",
            groups = {
                    "metro-regression","sbermarket-Ui-smoke","ui-smoke-production"
            }
    )
    public void successSetNewAddressAfterOutOfZoneAddressChange() {
        kraken.get().page(Config.DEFAULT_RETAILER);
        ShippingAddressModal.open();
        ShippingAddressModal.fill(Addresses.Moscow.outOfZoneAddress());
        ShippingAddressModal.selectAddressSuggest();
        ShippingAddressModal.submit();
        shippingChecks.checkIsAddressOutOfZone(
                "Не открывается модалка Адрес вне зоны доставки");
        ShippingAddressModal.selectNewAdress();
        ShippingAddressModal.fill(Addresses.Moscow.defaultAddress());
        ShippingAddressModal.selectAddressSuggest();
        ShippingAddressModal.submit();
        shippingChecks.checkIsSetAddresEqualsToInput(Addresses.Moscow.defaultAddress(),
                kraken.grab().currentShipAddress());
    }
}
