package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.reforged.core.Kraken;
import io.qameta.allure.TmsLink;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkDeliveryIntervalsNonEquals;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.getNearestInterval;
import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.Group.STARTING_X;
import static ru.instamart.reforged.core.config.UiProperties.*;
import static ru.instamart.reforged.stf.page.StfRouter.home;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Главная страница")
public final class HomePageTests {

    private final ApiHelper apiHelper = new ApiHelper();

    @TmsLink("3358")
    @Test(description = "Отображение слотов доставки магазина для адреса в пересечении зон доставки", groups = {STARTING_X, REGRESSION_STF})
    public void crossZonesStoreSingleZoneAddress() {
        final String zone1DeliveryInterval = apiHelper.getNextDeliveryInfo(DEFAULT_METRO_CROSSZONES_SID, RestAddresses.Moscow.metroCrossZone1Address());
        final String zone2DeliveryInterval = apiHelper.getNextDeliveryInfo(DEFAULT_METRO_CROSSZONES_SID, RestAddresses.Moscow.metroCrossZone2Address());
        checkDeliveryIntervalsNonEquals(zone1DeliveryInterval, zone2DeliveryInterval);

        home().goToPage();
        home().checkLoginButtonIsVisible();

        home().fillAddressInLanding(Addresses.Moscow.crossZone1MetroAddress());
        home().selectFirstAddressInFounded();
        home().checkDeliveryStoresContainerVisible();

        home().checkNextDeliveryInCardEquals(home().getNextDeliveryBySid(DEFAULT_METRO_CROSSZONES_SID), zone1DeliveryInterval);

        home().clickOnStoreWithSid(DEFAULT_METRO_CROSSZONES_SID);

        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().interactHeader().checkNextDeliveryEquals(zone1DeliveryInterval);

        Kraken.back();

        home().interactMultisearchHeader().clickChangeAddress();
        home().interactAddressModal().checkYmapsReady();
        home().interactAddressModal().clearInput();
        home().interactAddressModal().fillAddress(Addresses.Moscow.crossZone2MetroAddress());
        home().interactAddressModal().selectFirstAddress();
        home().interactAddressModal().clickFindStores();
        home().interactAddressModal().checkAddressModalIsNotVisible();

        home().checkDeliveryStoresContainerVisible();
        home().checkNextDeliveryInCardEquals(home().getNextDeliveryBySid(DEFAULT_METRO_CROSSZONES_SID), zone2DeliveryInterval);

        home().clickOnStoreWithSid(DEFAULT_METRO_CROSSZONES_SID);

        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().interactHeader().checkNextDeliveryEquals(zone2DeliveryInterval);
    }

    @TmsLink("3359")
    @Test(description = "Отображение слотов доставки магазина для адреса вне пересечения зон доставки", groups = {STARTING_X, REGRESSION_STF})
    public void crossZonesStoreDoubleZoneAddress() {
        final String zone1DeliveryInterval = apiHelper.getNextDeliveryInfo(DEFAULT_METRO_CROSSZONES_SID, RestAddresses.Moscow.metroCrossZone1Address());
        final String zone2DeliveryInterval = apiHelper.getNextDeliveryInfo(DEFAULT_METRO_CROSSZONES_SID, RestAddresses.Moscow.metroCrossZone2Address());
        checkDeliveryIntervalsNonEquals(zone1DeliveryInterval, zone2DeliveryInterval);
        final String nearestDeliveryInterval = getNearestInterval(zone1DeliveryInterval, zone2DeliveryInterval);

        home().goToPage();
        home().checkLoginButtonIsVisible();
        home().fillAddressInLanding(Addresses.Moscow.crossZonesMetroAddress());
        home().selectFirstAddressInFounded();
        home().checkDeliveryStoresContainerVisible();

        home().checkNextDeliveryInCardEquals(home().getNextDeliveryBySid(DEFAULT_METRO_CROSSZONES_SID), nearestDeliveryInterval);

        home().clickOnStoreWithSid(DEFAULT_METRO_CROSSZONES_SID);

        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().interactHeader().checkNextDeliveryEquals(nearestDeliveryInterval);
    }

    @TmsLink("3360")
    @Test(description = "Отображение страницы для авторизованного пользователя", groups = {STARTING_X, REGRESSION_STF})
    public void homePageForAuthorizedUser() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().checkLoginButtonIsVisible();

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactAuthModal().checkModalIsNotVisible();

        home().interactMultisearchHeader().checkUserActionsButtonVisible();
        home().interactMultisearchHeader().checkIsSetAddressEqualToInput(home().getEnteredAddress(), Addresses.Moscow.defaultAddressRest());
    }

    @TmsLink("3361")
    @Test(description = "Определение города по IP если в городе работает СберМаркет", groups = {STARTING_X, REGRESSION_STF})
    public void detectCityByIPAndChangeCityFromOnboarding() {
        home().goToPage();
        home().setLocation("Moscow");
        home().checkBannerTitleText(home().getAddressBlockText(), "Доставка из любимых магазинов в Москве");

        String expectedDeliveryBlockTitle = String.format("Нашли %d магазинов в", home().getRetailersCountInBlock());
        home().checkDeliveryBlockTitle(home().getRetailersBlockTitle(), expectedDeliveryBlockTitle);

        //Кнопка 'Показать все' города отображается только если городов > 18, на стейджах её нет, так как городов в списке, как правило, меньше
        //home().clickShowAllCities();
        home().selectCity("Санкт-Петербург");
        expectedDeliveryBlockTitle = String.format("Нашли %d магазин в", home().getRetailersCountInBlock());
        home().checkDeliveryBlockTitle(home().getRetailersBlockTitle(), expectedDeliveryBlockTitle);
    }

    @TmsLink("3362")
    @Test(description = "Отображение магазинов после ввода адреса доставки", groups = {STARTING_X, REGRESSION_STF})
    public void showShopsAfterFillAddress() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().checkLoginButtonIsVisible();

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactAuthModal().checkModalIsNotVisible();

        final String expectedDeliveryBlockTitle = String.format("К вам привозят из %d магазинов", home().getStoresCountInBlock());
        //home().checkDeliveryBlockTitle(home().getStoresBlockTitle(), expectedDeliveryBlockTitle);
    }

    @TmsLink("3363")
    @Test(description = "Отображение магазинов после ввода адреса вне города доставки", groups = {STARTING_X, REGRESSION_STF})
    public void outOfDeliveryLocation() {
        home().goToPage();
        home().checkLoginButtonIsVisible();
        home().setLocation("Moscow");

        home().fillAddressInLanding(Addresses.Krasnodar.outOfDeliveryArea());
        home().selectFirstAddressInFounded();

        home().checkDeliveryStoresNotVisible();
        home().checkDeliveryBlockTitle(home().getRetailersBlockTitle(), String.format("Нашли %d магазинов в", home().getRetailersCountInBlock()));

        home().interactMultisearchHeader().clickChangeAddress();
        home().interactAddressModal().checkIsAddressOutOfZone();
    }

    @Skip //Непонятен порядок определения геолокации + что должно определяться в контейнерах
    @TmsLink("3364")
    @Test(description = "Определение города по IP и координатам", groups = {STARTING_X, REGRESSION_STF})
    public void retailerNotInCity() {
        home().goToPage();
        home().checkLoginButtonIsVisible();
        home().setLocation("Nizhniy-novgorod");

        home().checkAddressBlockTextEquals(home().getAddressBlockText(), "Доставка из любимых магазинов в Нижнем Новгороде");
        //TODO Не соответствует кейсу: ритейлер, у которого в настройках не указан текущий город, не отображается.
        //Соответствует текущей реализации: ритейлер определяется по настройкам в админке, не учитывает геолокацию.

        home().fillAddressInLanding(Addresses.NizhnyNovgorod.defaultAddress());
        home().selectFirstAddressInFounded();

        home().checkAddressBlockTextEquals(home().getAddressBlockText(), "Доставка из любимых магазинов в Нижнем Новгороде");
        home().checkStoreCardDisplayed(DEFAULT_AUCHAN_SID);
    }

    @TmsLink("3365")
    @Test(description = "Переход по ссылке с кодом лейбла при незаполненном адресе", groups = {STARTING_X, REGRESSION_STF})
    public void openGroupsAddressNotSet() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().checkLoginButtonIsVisible();
        home().setLocation("Moscow");

        home().openGroupPage("express");
        home().checkAlertDisplayed();
        home().checkAlertTextEquals(home().getAlertText(), "Чтобы посмотреть подходящие магазины, укажите адрес");

        home().checkDeliveryRetailersContainerVisible();
        home().checkRetailersCardCountEquals(home().getRetailersCountInBlock(), 6);

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactMultisearchHeader().checkUserActionsButtonVisible();

        home().checkStoresCardCountEquals(home().getStoresCountInBlock(), 3);
        home().checkStoreCardDisplayed(DEFAULT_METRO_MOSCOW_SID);
    }

    @TmsLink("3366")
    @Test(description = "Переход по ссылке с кодом лейбла при заполненном адресе", groups = {STARTING_X, REGRESSION_STF})
    public void openGroupsAddressSet() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().checkLoginButtonIsVisible();

        home().fillAddressInLanding(Addresses.Moscow.defaultAddress());
        home().selectFirstAddressInFounded();

        home().checkDeliveryStoresContainerVisible();
        home().checkStoresCardCountEquals(home().getStoresCountInBlock(), 3);

        home().openGroupPage("express");

        home().checkDeliveryStoresContainerVisible();
        home().checkStoresCardCountEquals(home().getStoresCountInBlock(), 1);
        home().checkStoreCardDisplayed(DEFAULT_METRO_MOSCOW_SID);

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().checkLogoutButtonDisplayed();

        home().checkDeliveryStoresContainerVisible();
        home().checkStoresCardCountEquals(home().getStoresCountInBlock(), 1);
        home().checkStoreCardDisplayed(DEFAULT_METRO_MOSCOW_SID);
    }

    @TmsLink("3204")
    @Test(description = "Переход по ссылке с кодом лейбла при заполненном адресе и отсутствующих магазинах", groups = {STARTING_X, REGRESSION_STF})
    public void openGroupsAddressSetNoStoresByGroupLabel() {
        home().goToPage();
        home().clickToSetAddress();
        home().interactAddressModal().checkYmapsReady();
        home().interactAddressModal().fillAddress(Addresses.Moscow.defaultAddress());
        home().interactAddressModal().selectFirstAddress();
        home().interactAddressModal().clickFindStores();
        home().interactAddressModal().checkAddressModalIsNotVisible();

        home().openGroupPage("alcohol");
        home().checkAlertDisplayed();
        home().checkAlertTextEquals(home().getAlertText(), "Кажется, из некоторых магазинов пока не доставляем. Но у нас есть другие — с похожими товарами");

        home().checkDeliveryStoresContainerVisible();
        home().checkStoreCardDisplayed(DEFAULT_AUCHAN_SID);
    }
}
