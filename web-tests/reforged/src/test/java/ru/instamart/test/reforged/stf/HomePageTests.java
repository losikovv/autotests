package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkDeliveryIntervalsNonEquals;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.getNearestInterval;
import static ru.instamart.kraken.config.EnvironmentProperties.*;
import static ru.instamart.reforged.stf.page.StfRouter.home;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Главная страница")
public final class HomePageTests extends BaseTest {

    private final ApiHelper apiHelper = new ApiHelper();

    @CaseId(3358)
    @Test(description = "Отображение слотов доставки магазина для адреса в пересечении зон доставки", groups = {"regression"})
    public void crossZonesStoreSingleZoneAddress() {
        final String zone1DeliveryInterval = apiHelper.getNextDeliveryInfo(DEFAULT_METRO_CROSSZONES_SID, RestAddresses.Moscow.metroCrossZone1Address());
        final String zone2DeliveryInterval = apiHelper.getNextDeliveryInfo(DEFAULT_METRO_CROSSZONES_SID, RestAddresses.Moscow.metroCrossZone2Address());
        checkDeliveryIntervalsNonEquals(zone1DeliveryInterval, zone2DeliveryInterval);

        home().goToPage(true);
        home().checkLoginButtonIsVisible();

        home().fillAddressInLanding(Addresses.Moscow.crossZone1MetroAddress());
        home().selectFirstAddressInFounded();
        home().checkDeliveryStoresContainerVisible();

        home().checkNextDeliveryInCardEquals(home().getNextDeliveryBySid(DEFAULT_METRO_CROSSZONES_SID), zone1DeliveryInterval);

        home().clickOnStoreWithSid(DEFAULT_METRO_CROSSZONES_SID);

        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().interactHeader().checkNextDeliveryEquals(zone1DeliveryInterval);

        Kraken.back();

        home().checkAddressInputClear();
        home().fillAddressInLanding(Addresses.Moscow.crossZone2MetroAddress());

        home().selectFirstAddressInFounded();
        home().checkDeliveryStoresContainerVisible();
        home().checkNextDeliveryInCardEquals(home().getNextDeliveryBySid(DEFAULT_METRO_CROSSZONES_SID), zone2DeliveryInterval);

        home().clickOnStoreWithSid(DEFAULT_METRO_CROSSZONES_SID);

        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().interactHeader().checkNextDeliveryEquals(zone2DeliveryInterval);
    }

    @CaseId(3359)
    @Test(description = "Отображение слотов доставки магазина для адреса вне пересечения зон доставки", groups = {"regression"})
    public void crossZonesStoreDoubleZoneAddress() {
        final String zone1DeliveryInterval = apiHelper.getNextDeliveryInfo(DEFAULT_METRO_CROSSZONES_SID, RestAddresses.Moscow.metroCrossZone1Address());
        final String zone2DeliveryInterval = apiHelper.getNextDeliveryInfo(DEFAULT_METRO_CROSSZONES_SID, RestAddresses.Moscow.metroCrossZone2Address());
        checkDeliveryIntervalsNonEquals(zone1DeliveryInterval, zone2DeliveryInterval);
        final String nearestDeliveryInterval = getNearestInterval(zone1DeliveryInterval, zone2DeliveryInterval);

        home().goToPage(true);
        home().checkLoginButtonIsVisible();
        home().fillAddressInLanding(Addresses.Moscow.crossZonesMetroAddress());
        home().selectFirstAddressInFounded();
        home().checkDeliveryStoresContainerVisible();

        home().checkNextDeliveryInCardEquals(home().getNextDeliveryBySid(DEFAULT_METRO_CROSSZONES_SID), nearestDeliveryInterval);

        home().clickOnStoreWithSid(DEFAULT_METRO_CROSSZONES_SID);

        shop().interactHeader().checkEnteredAddressIsVisible();
        shop().interactHeader().checkNextDeliveryEquals(nearestDeliveryInterval);
    }

    @CaseId(3360)
    @Test(description = "Отображение страницы для авторизованного пользователя", groups = {"regression"})
    public void homePageForAuthorizedUser() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage(true);
        home().checkLoginButtonIsVisible();

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactAuthModal().checkModalIsNotVisible();

        home().checkUserCredentialsDisplayed();
        home().checkLogoutButtonDisplayed();
        home().checkIsSetAddressEqualToInput(Addresses.Moscow.defaultAddressRest(), home().getEnteredAddress());
    }

    @CaseId(3361)
    @Test(description = "Определение города по IP если в городе работает СберМаркет", groups = {"regression"})
    public void detectCityByIPAndChangeCityFromOnboarding() {

        home().goToPage(true);
        home().setLocation("Moscow");
        home().checkBannerTitleText(home().getAddressBlockText(), "Доставка из любимых магазинов в Москве");

        String expectedDeliveryBlockTitle = String.format("Нашли %d магазинов в Москве", home().getRetailersCountInBlock());
        home().checkDeliveryBlockTitle(home().getRetailersBlockTitle(), expectedDeliveryBlockTitle);

        home().clickShowAllCities();
        home().selectCity("Санкт-Петербург");
        expectedDeliveryBlockTitle = String.format("Нашли %d магазин в Санкт-Петербурге", home().getRetailersCountInBlock());
        home().checkDeliveryBlockTitle(home().getRetailersBlockTitle(), expectedDeliveryBlockTitle);
    }

    @CaseId(3362)
    @Test(description = "Отображение магазинов после ввода адреса доставки", groups = {"regression"})
    public void showShopsAfterFillAddress() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage(true);
        home().checkLoginButtonIsVisible();

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactAuthModal().checkModalIsNotVisible();

        final String expectedDeliveryBlockTitle = String.format("К вам привозят из %d магазинов", home().getStoresCountInBlock());
        home().checkDeliveryBlockTitle(home().getStoresBlockTitle(), expectedDeliveryBlockTitle);
    }

    @CaseId(3363)
    @Test(description = "Отображение магазинов после ввода адреса вне города доставки", groups = {"regression"})
    public void outOfDeliveryLocation() {
        home().goToPage(true);
        home().checkLoginButtonIsVisible();
        home().setLocation("Moscow");

        home().fillAddressInLanding(Addresses.Krasnodar.outOfDeliveryArea());
        home().selectFirstAddressInFounded();

        home().checkOutOfDeliveryAreaMessageDisplayed();
        home().checkAddressBlockTextEquals(home().getAddressBlockText(), "Доставка из любимых магазинов");

        home().checkDeliveryBlockTitle(home().getRetailersBlockTitle(), String.format("Нашли %d магазинов в Москве", home().getRetailersCountInBlock()));
    }

    @CaseId(3364)
    @Test(description = "Определение города по IP и координатам", groups = {"regression"})
    public void retailerNotInCity() {
        home().goToPage(true);
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

    @CaseId(3365)
    @Test(description = "Переход по ссылке с кодом лейбла при незаполненном адресе", groups = {"regression"})
    public void openGroupsAddressNotSet() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage(true);
        home().checkLoginButtonIsVisible();
        home().setLocation("Moscow");

        home().openExpressGroupPage();
        home().checkAlertDisplayed();
        home().checkAlertTextEquals(home().getAlertText(), "Чтобы посмотреть подходящие магазины, укажите адрес");

        home().checkDeliveryRetailersContainerVisible();
        home().checkRetailersCardCountEquals(home().getRetailersCountInBlock(), 6);

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().checkLogoutButtonDisplayed();

        home().checkStoresCardCountEquals(home().getStoresCountInBlock(), 1);
        home().checkStoreCardDisplayed(DEFAULT_METRO_MOSCOW_SID);
    }

    @CaseId(3366)
    @Test(description = "Переход по ссылке с кодом лейбла при заполненном адресе", groups = {"regression"})
    public void openGroupsAddressSet() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage(true);
        home().checkLoginButtonIsVisible();

        home().fillAddressInLanding(Addresses.Moscow.defaultAddress());
        home().selectFirstAddressInFounded();

        home().checkDeliveryStoresContainerVisible();
        home().checkStoresCardCountEquals(home().getStoresCountInBlock(), 4);

        home().openExpressGroupPage();

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
}
