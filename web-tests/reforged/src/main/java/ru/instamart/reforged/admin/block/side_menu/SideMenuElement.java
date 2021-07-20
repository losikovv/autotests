package ru.instamart.reforged.admin.block.side_menu;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.DropDown;
import ru.instamart.reforged.core.component.Link;

public interface SideMenuElement {
    DropDown storesDropdown = new DropDown(By.xpath("//div[@role='button']//*[text()='Магазины']"));
    Link regions = new Link(By.xpath("//a[contains(@href, '/admin/operational_zones')]"));
    Link retailers = new Link(By.xpath("//a[contains(@href, '/admin/retailers')]"));
    Link shipmentArea = new Link(By.xpath("//a[contains(@href, '/admin/delivery_areas')]"));

    DropDown ordersDropdown = new DropDown(By.xpath("//div[@role='button']//*[text()='Заказы']"));
    Link logisticControl = new Link(By.xpath("//a[contains(@href, '/admin/routes')]"));
    Link rootsParameters = new Link(By.xpath("//a[contains(@href, '/admin/scheduling_modes')]"));
    Link multipleOrder = new Link(By.xpath("//a[contains(@href, '/admin/shipments')]"));

    DropDown contentDropdown = new DropDown(By.xpath("//div[@role='button']//*[text()='Контент']"));
    Link products = new Link(By.xpath("//a[contains(@href, '/admin/products')]"));
    Link goodsOptions = new Link(By.xpath("//a[contains(@href, '/admin/option_types')]"));
    Link properties = new Link(By.xpath("//a[contains(@href, '/admin/properties')]"));
    Link brands = new Link(By.xpath("//a[contains(@href, '/admin/brands')]"));
    Link manufacturers = new Link(By.xpath("//a[contains(@href, '/admin/manufacturers')]"));
    Link manufacturingCountries = new Link(By.xpath("//a[contains(@href, '/manufacturing_countries')]"));
    Link categories = new Link(By.xpath("//a[contains(@href, '/admin/taxonomies')]"));
    Link contentImport = new Link(By.xpath("//a[contains(@href, '/admin/imports')]"));

    Link settings = new Link(By.xpath("//a[contains(@href, '/admin/general_settings/edit')]"));

    DropDown marketingDropdown = new DropDown(By.xpath("//div[@role='button']//*[text()='Маркетинг']"));
    Link promoCards = new Link(By.xpath("//a[contains(@href, '/admin/promo_cards')]"));
    Link promoActions = new Link(By.xpath("//a[contains(@href, '/admin/promotions')]"));
    Link welcomeBanners = new Link(By.xpath("//a[contains(@href, '/admin/welcome_banners')]"));
    Link ads = new Link(By.xpath("//a[contains(@href, '/admin/ad_banners')]"));
    Link carts = new Link(By.xpath("//a[contains(@href, '/admin/source_channel_buckets')]"));
    Link bonusCards = new Link(By.xpath("//a[contains(@href, '/admin/loyalty_programs')]"));
    Link retailerPrograms = new Link(By.xpath("//a[contains(@href, '/admin/retailer_loyalty_programs')]"));
    Link referralProgram = new Link(By.xpath("//a[contains(@href, '/admin/referral_program_config/edit')]"));
    Link newCities = new Link(By.xpath("//a[contains(@href, '/admin/city_landings')]"));
    Link inAppBanners = new Link(By.xpath("//a[contains(@href, '/admin/promotion_cards')]"));
    Link bonusCount = new Link(By.xpath("//a[contains(@href, '/admin/bonus_account')]"));
    Link redirects = new Link(By.xpath("//a[contains(@href, '/admin/redirects')]"));
    Link sampling = new Link(By.xpath("//a[contains(@href, '/admin/samples')]"));
    Link marketingCategories = new Link(By.xpath("//a[contains(@href, '/admin/marketing_categories')]"));

    DropDown staffDropdown = new DropDown(By.xpath("//div[@role='button']//*[text()='Персонал']"));
    Link shifts = new Link(By.xpath("//a[contains(@href, '/admin/shifts')]"));
    Link tariffs = new Link(By.xpath("//a[contains(@href, '/admin/tariffs')]"));
    Link collectors = new Link(By.xpath("//a[contains(@href, '/admin/shoppers')]"));
    Link partnersImport = new Link(By.xpath("//a[contains(@href, '/admin/import_partners')]"));

    Link users = new Link(By.xpath("//a[contains(@href, '/admin/users')]"));

    Link pages = new Link(By.xpath("//a[contains(@href, '/admin/pages')]"));

    Link companies = new Link(By.xpath("//a[contains(@href, '/admin/companies')]"));


}
