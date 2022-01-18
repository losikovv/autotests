package ru.instamart.reforged.admin.block.side_menu;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.DropDown;
import ru.instamart.reforged.core.component.Link;

public interface SideMenuElement {

    DropDown storesDropdown = new DropDown(By.xpath("//span[@data-qa='nav_menu_retailers']"), "empty");
    Link retailers = new Link(By.xpath("//a[@data-qa='nav_menu_retailers_submenu_retailers']"), "empty");
    Link regions = new Link(By.xpath("//a[@data-qa='nav_menu_retailers_submenu_operational_zones']"), "empty");
    Link shipmentArea = new Link(By.xpath("//a[@data-qa='nav_menu_retailers_submenu_delivery_areas']"), "empty");

    DropDown ordersDropdown = new DropDown(By.xpath("//span[@data-qa='nav_menu_shipments']"), "empty");
    Link multipleOrder = new Link(By.xpath("//a[@data-qa='nav_menu_shipments_submenu_shipments']"), "empty");
    Link logisticControl = new Link(By.xpath("//a[@data-qa='nav_menu_shipments_submenu_routes']"), "empty");
    Link rootsParameters = new Link(By.xpath("//a[@data-qa='nav_menu_shipments_submenu_scheduling_modes']"), "empty");

    DropDown contentDropdown = new DropDown(By.xpath("//span[@data-qa='nav_menu_content']"), "empty");
    Link products = new Link(By.xpath("//a[@data-qa='nav_menu_content_submenu_products']"), "empty");
    Link goodsOptions = new Link(By.xpath("//a[@data-qa='nav_menu_content_submenu_option_types']"), "empty");
    Link properties = new Link(By.xpath("//a[@data-qa='nav_menu_content_submenu_properties']"), "empty");
    Link brands = new Link(By.xpath("//a[@data-qa='nav_menu_content_submenu_brands']"), "empty");
    Link manufacturers = new Link(By.xpath("//a[@data-qa='nav_menu_content_submenu_manufacturers']"), "empty");
    Link manufacturingCountries = new Link(By.xpath("//a[@data-qa='nav_menu_content_submenu_manufacturing_countries']"), "empty");
    Link categories = new Link(By.xpath("//a[@data-qa='nav_menu_content_submenu_taxonomies']"), "empty");
    Link contentImport = new Link(By.xpath("//a[@data-qa='nav_menu_content_submenu_imports']"), "empty");

    Link settings = new Link(By.xpath("//a[@data-qa='nav_menu_settings']"), "empty");

    DropDown marketingDropdown = new DropDown(By.xpath("//span[@data-qa='nav_menu_marketing']"), "empty");
    Link promoCards = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_promo_cards']"), "empty");
    Link promoActions = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_promotions']"), "empty");
    Link welcomeBanners = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_welcome_banners']"), "empty");
    Link ads = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_ad_banners']"), "empty");
    Link carts = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_source_channel_buckets']"), "empty");
    Link bonusCards = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_loyalty_programs']"), "empty");
    Link retailerPrograms = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_retailer_loyalty_programs']"), "empty");
    Link referralProgram = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_referral_program_config']"), "empty");
    Link newCities = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_city_landings']"), "empty");
    Link inAppBanners = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_promotion_cards']"), "empty");
    Link bonusCount = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_bonus_account']"), "empty");
    Link redirects = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_redirects']"), "empty");
    Link sampling = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_samples']"), "empty");
    Link marketingCategories = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_marketing_categories']"), "empty");

    DropDown staffDropdown = new DropDown(By.xpath("//span[@data-qa='nav_menu_staff']"), "empty");
    Link shoppers = new Link(By.xpath("//a[@data-qa='nav_menu_staff_submenu_shoppers']"), "empty");
    Link shifts = new Link(By.xpath("//a[@data-qa='nav_menu_staff_submenu_shifts']"), "empty");
    Link tariffs = new Link(By.xpath("//a[@data-qa='nav_menu_staff_submenu_tariffs']"), "empty");
    Link partnersImport = new Link(By.xpath("//a[@data-qa='nav_menu_staff_submenu_import_partners']"), "empty");

    Link users = new Link(By.xpath("//a[@data-qa='nav_menu_users']"), "empty");

    Link pages = new Link(By.xpath("//a[@data-qa='nav_menu_pages']"), "empty");

    Link companies = new Link(By.xpath("//a[@data-qa='nav_menu_companies']"), "empty");
}
