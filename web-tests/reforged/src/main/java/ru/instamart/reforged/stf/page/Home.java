package ru.instamart.reforged.stf.page;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.action.JsAction;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Link;
import ru.instamart.reforged.core.page.Window;
import ru.instamart.reforged.stf.checkpoint.HomeCheck;
import ru.instamart.reforged.stf.frame.Address;
import ru.instamart.reforged.stf.frame.auth.AuthModal;

@Slf4j
public final class Home implements StfPage, Window, HomeCheck {

    private final Button loginButton = new Button(By.xpath("//button[contains(@class, 'home_header')]"));
    private final Button forYourself = new Button(By.xpath("//button[@aria-controls='b2c-tab']"));
    private final Button forBusiness = new Button(By.xpath("//button[@aria-controls='b2b-tab']"));
    private final Button setAddress = new Button(By.xpath("//button[contains(@class, 'description')]"));
    private final Button showAllRetailers = new Button(By.xpath("//button[contains(text(), 'Показать всех')]"));
    private final Button showAllCities = new Button(By.xpath("//button[@data-qa='home_landing_show_button'][contains(text(), 'Показать')]"));
    private final Button hideCities = new Button(By.xpath("//button[@data-qa='home_landing_show_button'][contains(text(), 'Скрыть')]"));
    private final Link googlePlay = new Link(By.xpath("//a[@data-qa='home_landing_google_play_app_container']"));
    private final Link appStore = new Link(By.xpath("//a[@data-qa='home_landing_app_store_app_container']"));
    private final Link appGallery = new Link(By.xpath("//a[@data-qa='home_landing_huawei_store_app_container']"));

    private final AuthModal authModal = new AuthModal();
    private final Address addressModal = new Address();

    @Override
    public String pageUrl() {
        return "";
    }

    @Step("Открыть модалку авторизации")
    public void openLoginModal() {
        log.info("Открываем модальное окно авторизации");
        loginButton.click();
    }

    @Step("Нажать Для себя")
    public void clickForYourself() {
        forYourself.click();
    }

    @Step("Нажать Для бизнеса")
    public void clickForBusiness() {
        forBusiness.click();
    }

    @Step("Открыть модалку ввода адреса доставки")
    public void clickToSetAddress() {
        setAddress.click();
        JsAction.ymapReady();
    }

    @Step("Нажать Показать всех ритейлеров")
    public void clickToShowAllRetailers() {
        showAllRetailers.click();
    }

    @Step("Нажать Показать все города")
    public void clickToShowAllCities() {
        showAllCities.click();
    }


    @Step("Нажать Скрыть все города")
    public void clickToHideCities() {
        hideCities.click();
    }

    @Step("Перейти в GooglePlay")
    public void clickToGooglePlay() {
        googlePlay.click();
    }

    @Step("Перейти в AppStore")
    public void clickToAppStore() {
        appStore.click();
    }

    @Step("Перейти в AppGallery")
    public void clickToAppGallery() {
        appGallery.click();
    }

    public AuthModal interactAuthModal() {
        return authModal;
    }

    public Address interactAddressModal() {
        return addressModal;
    }
}
