package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.stf.page.StfRouter.home;

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
}
