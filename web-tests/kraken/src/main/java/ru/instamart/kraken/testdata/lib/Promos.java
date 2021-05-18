package ru.instamart.kraken.testdata.lib;

import ru.instamart.kraken.testdata.pagesdata.PromoData;
import ru.instamart.kraken.testdata.pagesdata.RegionData;
import ru.instamart.kraken.testdata.UserData;

public class Promos {

    public static PromoData freeOrderDelivery() {
        return new PromoData(
                "freeOrderDelivery",
                "autotest-free_order_delivery",
                "Бесплатная доставка заказа"
        );
    }

    public static PromoData freeShipmentDelivery() {
        return new PromoData(
                "freeShipmentDelivery",
                "autotest-free_shipment_delivery",
                "Бесплатная доставка подзаказа"
        );
    }

    public static PromoData percentDiscount() {
        return new PromoData(
                "percentDiscount",
                "autotest-10_percent_discount",
                "Скидка 10%"
        );
    }

    public static PromoData percentDiscountWithLimit() {
        return new PromoData(
                "percentDiscountWithLimit",
                "autotest-50_percent_discount_with_limit_1000",
                "Скидка 50% с лимитом 1000р"
        );
    }

    // TODO завести - ATST-229
    public static PromoData fixedDiscount() {
        return new PromoData(
                "fixedDiscount",
                "autotest-fixed_discount_99",
                "Скидка 99р"
        );
    }

    // TODO завести - ATST-229
    public static PromoData fixedDiscountOnFirstOrder() {
        return new PromoData(
                "fixedDiscountOnFirstOrder",
                "autotest-fixed_discount_100_on_first_order",
                "Скидка 100р на первый заказ"
        );
    }

    // TODO завести - ATST-229
    public static PromoData fixedDiscountOnOrderAboveSum() {
        return new PromoData(
                "fixedDiscountOnOrderAboveSum",
                "autotest-fixed_discount_100_on_order_above_2500",
                "Скидка 100р на заказ от 2500р"
        );
    }

    // TODO завести - ATST-229
    public static PromoData fixedDiscountOnOrderUnderSum() {
        return new PromoData(
                "fixedDiscountOnOrderUnderSum",
                "autotest-fixed_discount_100_on_order_under_5000",
                "Скидка 100р на заказ до 5000р"
        );
    }

    // TODO завести - ATST-229
    public static PromoData fixedDiscountForNewUser() {
        return new PromoData(
                "fixedDiscountForNewUser",
                "autotest-fixed_discount_100_for_new_user",
                "Скидка 100р на заказ для нового пользователя"
        );
    }

    // TODO завести - ATST-229
    public static PromoData fixedDiscountForUser(UserData user) {
        switch(user.getLogin()) {
            case "autotestuser@instamart.ru" :
                return new PromoData(
                        "fixedDiscountForAutotestUser",
                        "autotest-fixed_discount_100_for_autotest_user",
                        "Скидка 100р на заказы автотест юзера"
                );
            default:
                return new PromoData(
                        "",
                        "",
                        ""
                );
        }
    }

    // TODO завести - ATST-229
    public static PromoData fixedDiscountForB2bUser(UserData user) {
        switch(user.getLogin()) {
            case "autotestuser@instamart.ru" :
                return new PromoData(
                        "fixedDiscountForB2BUser",
                        "autotest-fixed_discount_100_for_b2b_user",
                        "Скидка 100р на заказы B2B юзера"
                );
            default:
                return new PromoData(
                        "",
                        "",
                        ""
                );
        }
    }

    // TODO - ATST-229
    public static PromoData referralPromo(UserData user) {
        // код разный на окружениях, попытаться сделать одинаковый для autotest юзера, руками в базе стейджа
        switch(user.getLogin()) {
            case "autotestuser@instamart.ru" :
                return new PromoData(
                        "fixedDiscountForB2BUser",
                        "todo", //todo
                        "Скидка 100р на заказы B2B юзера"
                );
            default:
                return new PromoData(
                        "",
                        "",
                        ""
                );
        }
    }

    // TODO public static PromoData fixedDiscountForItem  - ATST-229

    // TODO - ATST-229
    public static PromoData fixedDiscountForRetailer(String retailer) {
        switch(retailer) {
            case "metro" :
                return new PromoData(
                        "fixedDiscountForRetailerMetro",
                        "autotest-fixed_discount_100_for_retailer_metro",
                        "Скидка 100р на заказы в Метро"
                );
            case "auchan" :
                return new PromoData(
                        "fixedDiscountForRetailerAuchan",
                        "autotest-fixed_discount_100_for_retailer_auchan",
                        "Скидка 100р на заказы в Ашан"
                );
            default:
                return new PromoData(
                        "",
                        "",
                        ""
                );
        }
    }

    // TODO - ATST-229
    public static PromoData fixedDiscountForRegion(RegionData region) {
        switch(region.getAlias()) {
            case "msk" :
                return new PromoData(
                        "fixedDiscountForMoscow",
                        "autotest-fixed_discount_100_for_moscow",
                        "Скидка 100р на заказы из Москвы"
                );
            case "spb" :
                return new PromoData(
                        "fixedDiscountForSaintPetersburg",
                        "autotest-fixed_discount_100_for_saint_petersburg",
                        "Скидка 100р на заказы из Питера"
                );
            case "kzn" :
                return new PromoData(
                        "fixedDiscountForKazan",
                        "autotest-fixed_discount_100_for_kazan",
                        "Скидка 100р на заказы из Казани"
                );
            default:
                return new PromoData(
                        "",
                        "",
                        ""
                );
        }
    }

    // TODO завести - ATST-229
    public static PromoData fixedDiscountForTenant(String tenant) {
        switch(tenant) {
            case "instamart":
                return new PromoData(
                        "fixedDiscountForTenantInstamart",
                        "autotest-fixed_discount_100_for_tenant_instamart",
                        "Скидка 100р для заказов на сайте Instamart"
                );
            case "metro" :
                return new PromoData(
                        "fixedDiscountForTenantMetro",
                        "autotest-fixed_discount_100_for_tenant_metro",
                        "Скидка 100р для заказов на сайте Metro WL"
                );
            default:
                return new PromoData(
                        "",
                        "",
                        ""
                );
        }
    }

    // TODO - ATST-229
    public static PromoData oldPromo() {
        return null;
    }

    // TODO - ATST-229
    public static PromoData futurePromo() {
        return null;
    }

    // TODO public static PromoData fixedDiscountForCertainOrder - ATST-229

    // TODO public static PromoData fixedDiscountForSerialOrder - ATST-229
}