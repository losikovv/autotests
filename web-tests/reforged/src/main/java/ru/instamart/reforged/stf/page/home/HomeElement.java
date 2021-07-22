package ru.instamart.reforged.stf.page.home;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Link;
import ru.instamart.reforged.stf.frame.Address;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;

public interface HomeElement {

    AuthModal authModal = new AuthModal();
    Address addressModal = new Address();

    Button loginButton = new Button(By.xpath("//button[contains(@class, 'home_header')]"));
    Button forYourself = new Button(By.xpath("//button[@aria-controls='b2c-tab']"));
    Button forBusiness = new Button(By.xpath("//button[@aria-controls='b2b-tab']"));
    Button setAddress = new Button(By.xpath("//button[contains(@class, 'description')]"));
    Button showAllRetailers = new Button(By.xpath("//button[contains(text(), 'Показать всех')]"));
    Button showAllCities = new Button(By.xpath("//button[@data-qa='home_landing_show_button'][contains(text(), 'Показать')]"));
    Button hideCities = new Button(By.xpath("//button[@data-qa='home_landing_show_button'][contains(text(), 'Скрыть')]"));
    Link googlePlay = new Link(By.xpath("//a[@data-qa='home_landing_google_play_app_container']"));
    Link appStore = new Link(By.xpath("//a[@data-qa='home_landing_app_store_app_container']"));
    Link appGallery = new Link(By.xpath("//a[@data-qa='home_landing_huawei_store_app_container']"));
}
