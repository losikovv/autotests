package ru.instamart.reforged.stf.page.home;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.*;
import ru.instamart.reforged.stf.block.footer.Footer;
import ru.instamart.reforged.stf.drawer.cookie.CookieDrawer;
import ru.instamart.reforged.stf.frame.address.Address;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.stf.frame.delivery_zones.DeliveryZones;

public interface HomeElement {

    AuthModal authModal = new AuthModal();
    Address addressModal = new Address();
    Footer footer = new Footer();
    CookieDrawer cookieAlert = new CookieDrawer();
    DeliveryZones deliveryZones = new DeliveryZones();

    Element headerContainer = new Element(By.xpath("//header[@data-qa='b2c_home_landing_header_block']"), "шапка лендинга Сбермаркета");
    Element headerLogo = new Element(By.xpath("//div[@data-qa='b2c_home_landing_header_block_logo']"), "логотип Сбермаркета в шапке лендинга");
    Button loginButton = new Button(By.xpath("//button[@data-qa='b2c_home_landing_header_block_user_panel_login_button']"), "кнопка 'Войти'");
    Element alert = new Element(By.xpath("//div[contains(@class,'alert')]"), "Вспрывающее сообщение в правом верхнем углу)");

    Element headerAuthBlockContainer = new Element(By.xpath("//button[@data-qa='b2c_home_landing_header_block_user_panel']"), "блок для авторизованного пользователя");
    Element headerAuthIcon = new Element(By.xpath("//button[@data-qa='b2c_home_landing_header_block_user_panel_account_button']"), "иконка для авторизованного пользователя");
    Element headerAuthCredential = new Element(By.xpath("//span[@data-qa='b2c_home_landing_header_block_user_panel_user_name']"), "фио для авторизованного пользователя");
    Element headerAuthLogoutButton = new Element(By.xpath("//button[@data-qa='b2c_home_landing_header_block_user_panel_logout_button']"), "кнопка деавторизации для авторизованного пользователя");

    Element addressBlockContainer = new Element(By.xpath("//div[@data-qa='b2c_home_landing_address_block']"), "блок адреса доставки лендинга Сбермаркета");
    Element addressBlockText = new Element(By.xpath("//div[@data-qa='b2c_home_landing_address_block']/div/p"), "текст баннера Сбермаркета");
    Input addressBlockAddressInput = new Input(By.xpath("//div[@data-qa='b2c_home_landing_address_block']//input"), "инпут с указанием адреса доставки на лендинге");
    Input clearAddressInput = new Input(By.xpath("//div[@data-qa='b2c_home_landing_address_block']//input[@value='']"), "Очищенное поле ввода");
    Element addressCleanButton = new Element(By.xpath("//div[@data-qa='b2c_home_landing_address_block']//input/following-sibling::div/i"), "Кнопка очистви введенного адреса в баннере");
    DropDown dropDownAddresses = new DropDown(By.xpath("//div[@data-qa='b2c_home_landing_address_block']//input/following::div[2]/div/div/div"), "Выпадающий список найденных адресов");
    Button addressBlockAddressButton = new Button(By.xpath("//button[@data-qa='b2c_home_landing_address_block_map_modal_button_desktop']"), "кнопка с указанием адреса доставки на лендинге");
    Element outOfDeliveryAreaAlert = new Element(By.xpath("//div[@data-qa='b2c_home_landing_address_block']//button[contains(.,'самовывоз')]/../.."), "Уведомдение об адресе вне зоны доставки");

    //До ввода адреса отображается список ритейлеров в городе на основе геолокации или выбранного в лендинге города
    Element deliveryRetailersBlockContainer = new Element(By.xpath("//div[@data-qa='b2c_home_landing_delivery_retailers_block']"), "блок доставки лендинга Сбермаркета");
    Element deliveryRetailersBlockTitle = new Element(By.xpath("//h2[@data-qa='b2c_home_landing_delivery_retailers_block_title']"), "блок доставки заголовок лендинга Сбермаркета");
    Element deliveryRetailersBlockSubTitle = new Element(By.xpath("//h4[@data-qa='b2c_home_landing_delivery_retailers_block_subtitle']"), "блок доставки sub заголовок лендинга Сбермаркета");
    ElementCollection deliveryRetailers = new ElementCollection(By.xpath("//div[contains(@data-qa, 'b2c_home_landing_delivery_retailers_block_retailer_card')]"), "Карточки ритейлеров");

    //После ввода адреса отображается список магазинов, осуществляющих доставку по указанному адресу
    Element deliveryStoresBlockTitle = new Element(By.xpath("//h2[@data-qa='b2c_home_landing_stores_block_title']"), "Заголовок блока магазинов");
    ElementCollection deliveryStores = new ElementCollection(By.xpath("//a[contains(@data-qa, 'b2c_home_landing_stores_block_store_card_')]"), "Магазины в блоке");
    Element storeBySid = new Element(ByKraken.xpathExpression("//a[contains(@data-qa,'b2c_home_landing_stores_block_store_card')][@href='/stores/%s']"), "Карточка магазина по номеру SID");
    Element nextDeliveryBySid = new Element(ByKraken.xpathExpression("(//a[contains(@data-qa,'b2c_home_landing_stores_block_store_card')][@href='/stores/%s']//picture)[2]/following-sibling::div[contains(.,'сегодня')]"), "Ближайшее время доставки для магазина по номеру SID");

    Element advantagesBlockContainer = new Element(By.xpath("//div[@data-qa='b2c_home_landing_advantages_block']"), "блок advantages лендинга Сбермаркета");
    Element infoBlockContainer = new Element(By.xpath("//div[@data-qa='b2c_home_landing_info_block']"), "блок info лендинга Сбермаркета");

    Element citiesBlockContainer = new Element(By.xpath("//div[@data-qa='b2c_home_landing_cities_block']"), "блок cities лендинга Сбермаркета");
    Button showAllCities = new Button(By.xpath("//button[@data-qa='b2c_home_landing_cities_block_show_all_cities_button']"), "Кнопка 'Показать все' блока cities");
    ElementCollection cities = new ElementCollection(By.xpath("//div[@data-qa='b2c_home_landing_cities_block']//a"), "Список городов блока 'Доставляем в...'");

    Element b2bBannerBlockContainer = new Element(By.xpath("//div[@data-qa='b2c_home_landing_b2b_banner']"), "блок b2b banner лендинга Сбермаркета");
    Button b2bBannerBlockButton = new Button(By.xpath("//a[@data-qa='b2c_home_landing_b2b_banner_b2b_button']"), "кнопка b2b banner лендинга Сбермаркета");

    Element stepsBlockContainer = new Element(By.xpath("//div[@data-qa='b2c_home_landing_steps_block']"), "блок steps лендинга Сбермаркета");

    Element appBlockContainer = new Element(By.xpath("//div[@data-qa='b2c_home_landing_app_block']"), "блок app лендинга Сбермаркета");
    Link googlePlay = new Link(By.xpath("//a[@data-qa='home_landing_google_play_app_container']"), "Кнопка скачивания приложения сбермаркет в google play");
    Link appStore = new Link(By.xpath("//a[@data-qa='home_landing_app_store_app_container']"), "Кнопка скачивания приложения сбермаркет в app store");
    Link appGallery = new Link(By.xpath("//a[@data-qa='home_landing_huawei_store_app_container']"), "Кнопка скачивания приложения сбермаркет в app gallery");
}

