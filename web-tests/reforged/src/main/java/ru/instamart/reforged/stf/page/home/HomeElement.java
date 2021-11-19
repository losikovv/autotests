package ru.instamart.reforged.stf.page.home;

import org.openqa.selenium.By;

import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;
import ru.instamart.reforged.stf.block.footer.Footer;
import ru.instamart.reforged.stf.block.helpdesk.HelpDesk;
import ru.instamart.reforged.stf.drawer.cookie.CookieDrawer;
import ru.instamart.reforged.stf.frame.address.Address;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;

public interface HomeElement {

    AuthModal authModal = new AuthModal();
    Address addressModal = new Address();
    HelpDesk helpDesk = new HelpDesk();
    Footer footer = new Footer();
    CookieDrawer cookieAlert = new CookieDrawer();

    Button loginButton = new Button(By.xpath("//div/button[contains(text(), 'Войти')]"));
    Button forYourself = new Button(By.xpath("//button[@aria-controls='b2c-tab']"));
    Button forBusiness = new Button(By.xpath("//button[@aria-controls='b2b-tab']"));
    Button setAddress = new Button(By.xpath("//button[contains(@class, 'description')]"));
    Button setAddressNew = new Button(By.xpath("//div[contains(@class, 'Address')]/button"), "кнопка 'Выбрать адрес'");
    Button showAllRetailers = new Button(By.xpath("//button[contains(text(), 'Показать всех')]"));
    Button showAllCities = new Button(By.xpath("//button[@data-qa='home_landing_show_button'][contains(text(), 'Показать')]"));
    Button hideCities = new Button(By.xpath("//button[@data-qa='home_landing_show_button'][contains(text(), 'Скрыть')]"));
    Link googlePlay = new Link(By.xpath("//a[@data-qa='home_landing_google_play_app_container']"), "Кнопка скачивания приложения сбермаркет в google play");
    Link appStore = new Link(By.xpath("//a[@data-qa='home_landing_app_store_app_container']"), "Кнопка скачивания приложения сбермаркет в app store");
    Link appGallery = new Link(By.xpath("//a[@data-qa='home_landing_huawei_store_app_container']"), "Кнопка скачивания приложения сбермаркет в app gallery");

    Element headerContainer = new Element(By.xpath("//header"), "шапка лендинга Сбермаркета");
    Element headerLogo = new Element(By.xpath("//i[contains(@class,'home-logo')]"), "логотип Сбермаркета в шапке лендинга");
    Element mainBlockContainer = new Element(By.xpath("//div[@data-qa='home_landing']"), "главный блок лендинга Сбермаркета");
    Element mainBlockIllustration = new Element(By.xpath("//div[@data-qa='home_landing']//div[contains(@class,'mainImg')]"), "главная иллюстрация лендинга Сбермаркета");
    Element mainBlockAddressButton = new Element(By.xpath("//div[@data-qa='home_landing']//span[contains(text(),'Указать адрес доставки')]"), "кнопка с указанием адреса доставки на лендинге");
    Element mainBlockText = new Element(By.xpath("//div[@data-qa='home_landing_description']"), "текст лендинга Сбермаркета");
    Element storesList = new Element(By.xpath("//div[@data-qa='home_landing_stores']//h2[contains(text(),'Наши партнёры')]"), "блок со списком магазинов на лендинге Сбермаркета");
    Element storesButton = new Element(By.xpath("//div[@data-qa='home_landing_stores']//div[contains(@class, 'store')]/button"), "кнопка первого магазина на лендинге Сбермаркета");
    Element storesButtonAuchan = new Element(By.xpath("//div[text()='Ашан']/ancestor::button"), "кнопка выбора магазина Ашан");
    Element storesButtonMetro = new Element(By.xpath("//div[text()='METRO']/ancestor::button"), "кнопка выбора магазина Метро");
    Element storesButtonLenta = new Element(By.xpath("//div[text()='Лента']/ancestor::button"), "кнопка выбора магазина Лента");
    Element advantagesBlockContainer = new Element(By.xpath("//div[@data-qa='home_landing_advantages']"), "блок преимуществ на лендинге Сбермаркета");
    Element deliveryAdv = new Element(By.xpath("//div[@data-qa='home_landing_advantage_delivery']"), "преимущества быстрой доставки");
    Element heavyToDoorAdv = new Element(By.xpath("//div[@data-qa='home_landing_advantage_door']"), "преимущества доставки тяжелых товаров до двери");
    Element goodQualityAdv = new Element(By.xpath("//div[@data-qa='home_landing_advantage_best']"), "преимущества доставки товаров высокого качества");
    Element saleAdv = new Element(By.xpath("//div[@data-qa='home_landing_advantage_sale']"), "преимущества получения скидок от партнеров на большое количество товаров");
    Element zonesBlockContainer = new Element(By.xpath("//div[@data-qa='home_landing_cities']"), "блок зон доставки на лендинге Сбермаркета");
    Button zonesBlockShowAllButton = new Button(By.xpath("//button[@data-qa='home_landing_show_button' and text()='Показать все']"), "кнопка показать все города, где работает сбермаркет");
    Element orderBlockContainer = new Element(By.xpath("//div[@data-qa='home_landing_steps']"), "блок механики заказа на лендинге Сбермаркета");
    Element orderBlockStepFirst = new Element(By.xpath("//div[@data-qa='home_landing_step_first']"), "первый шаг заказа на лендинге Сбермаркета");
    Element orderBlockStepSecond = new Element(By.xpath("//div[@data-qa='home_landing_step_second']"), "второй шага заказа на лендинге Сбермаркета");
    Element orderBlockStepThird = new Element(By.xpath("//div[@data-qa='home_landing_step_third']"), "третий шаг заказа на лендинге Сбермаркета");
    Element appsBlockContainer = new Element(By.xpath("//div[@data-qa='home_landing_app_stores']"), "блок моб. приложений на лендинге Сбермаркета");
    Element footerContainer = new Element(By.xpath("//footer"), "подвал лендинга Сбермаркета");

    Link storeCard = new Link(By.xpath("//a[@data-qa='store-card']"), "карточка магазина на главной");

}

