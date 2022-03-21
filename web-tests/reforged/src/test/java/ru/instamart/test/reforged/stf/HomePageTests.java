package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.api.checkpoint.InstamartApiCheckpoints;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseId;

import java.time.LocalTime;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkDeliveryIntervalsNonEquals;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.getNearestInterval;
import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_METRO_CROSSZONES_SID;
import static ru.instamart.reforged.stf.page.StfRouter.home;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Главная страница")
public final class HomePageTests extends BaseTest {

    private final ApiHelper apiHelper = new ApiHelper();

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
}
