package ru.instamart.reforged.admin.block;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.DropDown;
import ru.instamart.reforged.core.component.Link;

@Getter
@Accessors(fluent = true)
public final class SideMenu {
    private final DropDown storesDropdown = new DropDown(By.xpath("//div[@role='button']//*[text()='Магазины']"));
    private final Link regions = new Link(By.xpath("//a[contains(@href, '/admin/operational_zones')]"));
    private final Link retailers = new Link(By.xpath("//a[contains(@href, '/admin/retailers')]"));
    private final Link shipmentArea = new Link(By.xpath("//a[contains(@href, '/admin/delivery_areas')]"));

    private final DropDown ordersDropdown = new DropDown(By.xpath("//div[@role='button']//*[text()='Заказы']"));
    private final Link logisticControl = new Link(By.xpath("//a[contains(@href, '/admin/routes')]"));
    private final Link rootsParameters = new Link(By.xpath("//a[contains(@href, '/admin/scheduling_modes')]"));
    private final Link multipleOrder = new Link(By.xpath("//a[contains(@href, '/admin/shipments')]"));

    private final DropDown contentDropdown = new DropDown(By.xpath("//div[@role='button']//*[text()='Контент']"));
    private final Link products = new Link(By.xpath("//a[contains(@href, '/admin/products')]"));
    private final Link goodsOptions = new Link(By.xpath("//a[contains(@href, '/admin/option_types')]"));
    private final Link properties = new Link(By.xpath("//a[contains(@href, '/admin/properties')]"));
    private final Link brands = new Link(By.xpath("//a[contains(@href, '/admin/brands')]"));
    private final Link manufacturers = new Link(By.xpath("//a[contains(@href, '/admin/manufacturers')]"));
    private final Link manufacturingCountries = new Link(By.xpath("//a[contains(@href, '/manufacturing_countries')]"));
    private final Link categories = new Link(By.xpath("//a[contains(@href, '/admin/taxonomies')]"));
    private final Link contentImport = new Link(By.xpath("//a[contains(@href, '/admin/imports')]"));

    private final Link settings = new Link(By.xpath("//a[contains(@href, '/admin/general_settings/edit')]"));

    private final DropDown marketingDropdown = new DropDown(By.xpath("//div[@role='button']//*[text()='Маркетинг']"));
    private final Link promoCards = new Link(By.xpath("//a[contains(@href, '/admin/promo_cards')]"));
    private final Link promoActions = new Link(By.xpath("//a[contains(@href, '/admin/promotions')]"));
    private final Link welcomeBanners = new Link(By.xpath("//a[contains(@href, '/admin/welcome_banners')]"));
    private final Link ads = new Link(By.xpath("//a[contains(@href, '/admin/ad_banners')]"));
    private final Link carts = new Link(By.xpath("//a[contains(@href, '/admin/source_channel_buckets')]"));
    private final Link bonusCards = new Link(By.xpath("//a[contains(@href, '/admin/loyalty_programs')]"));
    private final Link retailerPrograms = new Link(By.xpath("//a[contains(@href, '/admin/retailer_loyalty_programs')]"));
    private final Link referralProgram = new Link(By.xpath("//a[contains(@href, '/admin/referral_program_config/edit')]"));
    private final Link newCities = new Link(By.xpath("//a[contains(@href, '/admin/city_landings')]"));
    private final Link inAppBanners = new Link(By.xpath("//a[contains(@href, '/admin/promotion_cards')]"));
    private final Link bonusCount = new Link(By.xpath("//a[contains(@href, '/admin/bonus_account')]"));
    private final Link redirects = new Link(By.xpath("//a[contains(@href, '/admin/redirects')]"));
    private final Link sampling = new Link(By.xpath("//a[contains(@href, '/admin/samples')]"));
    private final Link marketingCategories = new Link(By.xpath("//a[contains(@href, '/admin/marketing_categories')]"));

    private final DropDown staffDropdown = new DropDown(By.xpath("//div[@role='button']//*[text()='Персонал']"));
    private final Link shifts = new Link(By.xpath("//a[contains(@href, '/admin/shifts')]"));
    private final Link tariffs = new Link(By.xpath("//a[contains(@href, '/admin/tariffs')]"));
    private final Link collectors = new Link(By.xpath("//a[contains(@href, '/admin/shoppers')]"));
    private final Link partnersImport = new Link(By.xpath("//a[contains(@href, '/admin/import_partners')]"));

    private final Link users = new Link(By.xpath("//a[contains(@href, '/admin/users')]"));

    private final Link pages = new Link(By.xpath("//a[contains(@href, '/admin/pages')]"));

    private final Link companies = new Link(By.xpath("//a[contains(@href, '/admin/companies')]"));


}
