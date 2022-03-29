package ru.instamart.reforged.admin;

import ru.instamart.reforged.admin.page.companies.CompaniesPage;
import ru.instamart.reforged.admin.page.companies.new_companies.NewCompaniesPage;
import ru.instamart.reforged.admin.page.main.MainPage;
import ru.instamart.reforged.admin.page.retailers.RetailersPage;
import ru.instamart.reforged.admin.page.retailers.add_new_retailer.RetailerAddPage;
import ru.instamart.reforged.admin.page.retailers.add_new_shop.ShopAddPage;
import ru.instamart.reforged.admin.page.retailers.regions.RegionsPage;
import ru.instamart.reforged.admin.page.retailers.retailer_page.RetailerPage;
import ru.instamart.reforged.admin.page.retailers.retailer_page.store_page.StorePage;
import ru.instamart.reforged.admin.page.retailers.retailer_page.store_page.store_zone_edit.StoreZone;
import ru.instamart.reforged.admin.page.settings.shipping_method.ShippingMethodPage;
import ru.instamart.reforged.admin.page.settings.all_cities.AllCitiesPage;
import ru.instamart.reforged.admin.page.settings.company_settings.CompanySettingsPage;
import ru.instamart.reforged.admin.page.settings.general_settings.GeneralSettingsPage;
import ru.instamart.reforged.admin.page.settings.payments_settings.PaymentsSettingsPage;
import ru.instamart.reforged.admin.page.settings.shipping_method.new_method.ShippingNewMethodPage;
import ru.instamart.reforged.admin.page.settings.sms_settings.SmsSettingsPage;
import ru.instamart.reforged.admin.page.shipment.ShipmentsPage;
import ru.instamart.reforged.admin.page.login.LoginPage;
import ru.instamart.reforged.admin.page.pages.new_page.NewPage;
import ru.instamart.reforged.admin.page.pages.Pages;
import ru.instamart.reforged.admin.page.sections.*;
import ru.instamart.reforged.admin.page.users.UsersPage;
import ru.instamart.reforged.admin.page.usersEdit.UsersEditPage;
import ru.instamart.reforged.core.page.Router;

public final class AdminRout extends Router {

    public static LoginPage login() {
        return (LoginPage) getPage(LoginPage.class);
    }

    public static Pages pages() {
        return (Pages) getPage(Pages.class);
    }

    public static NewPage newPages() {
        return (NewPage) getPage(NewPage.class);
    }

    public static Imports imports() {
        return (Imports) getPage(Imports.class);
    }

    public static Marketing marketing() {
        return (Marketing) getPage(Marketing.class);
    }

    public static Products products() {
        return (Products) getPage(Products.class);
    }

    public static RetailersPage retailers() {
        return (RetailersPage) getPage(RetailersPage.class);
    }

    public static RetailerAddPage retailerAdd() {
        return (RetailerAddPage) getPage(RetailerAddPage.class);
    }

    public static RetailerPage retailer() {
        return (RetailerPage) getPage(RetailerPage.class);
    }

    public static StorePage store() {
        return (StorePage) getPage(StorePage.class);
    }

    public static StoreZone zonePage() {
        return (StoreZone) getPage(StoreZone.class);
    }

    public static RegionsPage regions() {
        return (RegionsPage) getPage(RegionsPage.class);
    }

    public static AllCitiesPage allCities() {
        return (AllCitiesPage) getPage(AllCitiesPage.class);
    }

    public static ShopAddPage shopAdd() {
        return (ShopAddPage) getPage(ShopAddPage.class);
    }

    public static GeneralSettingsPage settings() {
        return (GeneralSettingsPage) getPage(GeneralSettingsPage.class);
    }

    public static PaymentsSettingsPage payments() {
        return (PaymentsSettingsPage) getPage(PaymentsSettingsPage.class);
    }

    public static Staff staff() {
        return (Staff) getPage(Staff.class);
    }

    public static ShipmentsPage shipments() {
        return (ShipmentsPage) getPage(ShipmentsPage.class);
    }

    public static UsersPage users() {
        return (UsersPage) getPage(UsersPage.class);
    }

    public static MainPage main() {
        return (MainPage) getPage(MainPage.class);
    }

    public static Oktell oktell() {
        return (Oktell) getPage(Oktell.class);
    }

    public static UsersEditPage usersEdit() {
        return (UsersEditPage) getPage(UsersEditPage.class);
    }

    public static CompaniesPage companies() {
        return (CompaniesPage) getPage(CompaniesPage.class);
    }

    public static NewCompaniesPage addCompanies() {
        return (NewCompaniesPage) getPage(NewCompaniesPage.class);
    }

    public static ShippingMethodPage shippingMethod() {
        return (ShippingMethodPage) getPage(ShippingMethodPage.class);
    }

    public static ShippingNewMethodPage shippingNewMethod() {
        return (ShippingNewMethodPage) getPage(ShippingNewMethodPage.class);
    }

    public static CompanySettingsPage companySettings() {
        return (CompanySettingsPage) getPage(CompanySettingsPage.class);
    }

    public static SmsSettingsPage smsSettings() {
        return (SmsSettingsPage) getPage(SmsSettingsPage.class);
    }

    private AdminRout() {
    }
}
