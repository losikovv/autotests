package ru.instamart.reforged.business.page;

import ru.instamart.reforged.business.page.checkout.B2BCheckoutPage;
import ru.instamart.reforged.business.page.home.B2BHomePage;
import ru.instamart.reforged.business.page.shop.B2BShopPage;
import ru.instamart.reforged.business.page.user.companies.B2BUserCompaniesPage;
import ru.instamart.reforged.business.page.user.companies.companyInfo.B2BCompanyInfoPage;
import ru.instamart.reforged.business.page.user.profile.B2BUserProfilePage;
import ru.instamart.reforged.core.page.Router;
import ru.instamart.reforged.stf.page.shop.ShopPage;
import ru.instamart.reforged.stf.page.user.companies.UserCompaniesPage;
import ru.instamart.reforged.stf.page.user.profile.UserProfilePage;

public class BusinessRouter extends Router {

    public static B2BHomePage business() {
        return (B2BHomePage) getPage(B2BHomePage.class);
    }

    public static B2BCheckoutPage checkout() {
        return (B2BCheckoutPage) getPage(B2BCheckoutPage.class);
    }

    public static B2BShopPage shop() {
        return (B2BShopPage) getPage(B2BShopPage.class);
    }

    public static B2BUserCompaniesPage companies() {
        return (B2BUserCompaniesPage) getPage(B2BUserCompaniesPage.class);
    }

    public static B2BCompanyInfoPage companyInfoPage() {
        return (B2BCompanyInfoPage) getPage(B2BCompanyInfoPage.class);
    }

    public static B2BUserProfilePage userEdit() {
        return (B2BUserProfilePage) getPage(B2BUserProfilePage.class);
    }

    public static ShopPage b2cShop() {
        return (ShopPage) getPage(ShopPage.class);
    }

    public static UserCompaniesPage b2cCompanies() {
        return (UserCompaniesPage) getPage(UserCompaniesPage.class);
    }

    public static UserProfilePage b2cUserEdit() {
        return (UserProfilePage) getPage(UserProfilePage.class);
    }

    private BusinessRouter() {
    }
}
