package ru.instamart.reforged.admin.block.side_menu;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.DropDown;
import ru.instamart.reforged.core.component.Link;

public interface SideMenuElement {

    DropDown storesDropdown = new DropDown(By.xpath("//span[@data-qa='nav_menu_retailers']"));
    Link retailers = new Link(By.xpath("//a[@data-qa='nav_menu_retailers_submenu_retailers']"));
    Link regions = new Link(By.xpath("//a[@data-qa='nav_menu_retailers_submenu_operational_zones']"));
    Link shipmentArea = new Link(By.xpath("//a[@data-qa='nav_menu_retailers_submenu_delivery_areas']"));

    DropDown ordersDropdown = new DropDown(By.xpath("//span[@data-qa='nav_menu_shipments']"));
    Link multipleOrder = new Link(By.xpath("//a[@data-qa='nav_menu_shipments_submenu_shipments']"));
    Link logisticControl = new Link(By.xpath("//a[@data-qa='nav_menu_shipments_submenu_routes']"));
    Link rootsParameters = new Link(By.xpath("//a[@data-qa='nav_menu_shipments_submenu_scheduling_modes']"));

    DropDown contentDropdown = new DropDown(By.xpath("//span[@data-qa='nav_menu_content']"));
    Link products = new Link(By.xpath("//a[@data-qa='nav_menu_content_submenu_products']"));
    Link goodsOptions = new Link(By.xpath("//a[@data-qa='nav_menu_content_submenu_option_types']"));
    Link properties = new Link(By.xpath("//a[@data-qa='nav_menu_content_submenu_properties']"));
    Link brands = new Link(By.xpath("//a[@data-qa='nav_menu_content_submenu_brands']"));
    Link manufacturers = new Link(By.xpath("//a[@data-qa='nav_menu_content_submenu_manufacturers']"));
    Link manufacturingCountries = new Link(By.xpath("//a[@data-qa='nav_menu_content_submenu_manufacturing_countries']"));
    Link categories = new Link(By.xpath("//a[@data-qa='nav_menu_content_submenu_taxonomies']"));
    Link contentImport = new Link(By.xpath("//a[@data-qa='nav_menu_content_submenu_imports']"));

    Link settings = new Link(By.xpath("//a[@data-qa='nav_menu_settings']"));

    DropDown marketingDropdown = new DropDown(By.xpath("//span[@data-qa='nav_menu_marketing']"));
    Link promoCards = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_promo_cards']"));
    Link promoActions = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_promotions']"));
    Link welcomeBanners = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_welcome_banners']"));
    Link ads = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_ad_banners']"));
    Link carts = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_source_channel_buckets']"));
    Link bonusCards = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_loyalty_programs']"));
    Link retailerPrograms = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_retailer_loyalty_programs']"));
    Link referralProgram = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_referral_program_config']"));
    Link newCities = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_city_landings']"));
    Link inAppBanners = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_promotion_cards']"));
    Link bonusCount = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_bonus_account']"));
    Link redirects = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_redirects']"));
    Link sampling = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_samples']"));
    Link marketingCategories = new Link(By.xpath("//a[@data-qa='nav_menu_marketing_submenu_marketing_categories']"));

    DropDown staffDropdown = new DropDown(By.xpath("//span[@data-qa='nav_menu_staff']"));
    Link shoppers = new Link(By.xpath("//a[@data-qa='nav_menu_staff_submenu_shoppers']"));
    Link shifts = new Link(By.xpath("//a[@data-qa='nav_menu_staff_submenu_shifts']"));
    Link tariffs = new Link(By.xpath("//a[@data-qa='nav_menu_staff_submenu_tariffs']"));
    Link partnersImport = new Link(By.xpath("//a[@data-qa='nav_menu_staff_submenu_import_partners']"));

    Link users = new Link(By.xpath("//a[@data-qa='nav_menu_users']"));

    Link pages = new Link(By.xpath("//a[@data-qa='nav_menu_pages']"));

    Link companies = new Link(By.xpath("//a[@data-qa='nav_menu_companies']"));
}
