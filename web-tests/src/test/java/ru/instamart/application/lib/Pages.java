package ru.instamart.application.lib;

import ru.instamart.application.models.PageData;

public class Pages {

    public static PageData page404() {
        return new PageData("nowhere");
    }

    public static PageData page500() {
        return new PageData("stores/21/shipping_methods");
    }

    public static PageData checkout() { return new PageData("checkout/edit"); }

    public static PageData seo_catalog() { return new PageData("categories/bakalieia/krupy/griechnievaia"); }

    public interface Retailers {

        static PageData metro() {
            return new PageData( "metro");
        }

        static PageData vkusvill() {
            return new PageData( "vkusvill");
        }

        static PageData azbuka() {
            return new PageData( "azbukavkusa");
        }

        static PageData lenta() {
            return new PageData("lenta");
        }

        static PageData karusel() {
            return new PageData( "karusel");
        }

        static PageData auchan() {
            return new PageData( "auchan");
        }

        static PageData selgros() {
            return new PageData( "selgros");
        }

        static PageData flora() {
            return new PageData("flora");
        }

        static PageData foodcity() {
            return new PageData( "foodcity");
        }

        static PageData magnit() {
            return new PageData( "magnit");
        }

        static PageData testretailer() {
            return new PageData( "testretailer");
        }
    }

    public interface Landings {

        static PageData mnogoru() { return new PageData("mnogoru"); }

        static PageData aeroflot() { return new PageData("aeroflot_09-02-31-12"); }

        static PageData sovest() { return new PageData("sovest"); }

        static PageData halva() { return new PageData("halva"); }

        static PageData feedback() { return new PageData("landings/feedback"); }

        static PageData mobile() { return new PageData("landings/m-general-white"); }
    }

    public interface ServicePages {

        static PageData giftCertificates() { return new PageData("gift_certificates/new"); }

        static PageData massHiring() { return new PageData("landings/job"); }
    }

    public interface UserProfile {

        static PageData edit() { return new PageData("user/edit"); }

        static PageData favorites() { return new PageData("user/favorites"); }

        static PageData shipments() { return new PageData("user/shipments"); }

        static PageData orderDetails(String orderNumber) { return new PageData("user/orders/" + orderNumber); }
    }

    public interface Sbermarket {

        static PageData about() { return new PageData("about"); }

        static PageData delivery() { return new PageData("delivery-sbermarket"); }

        static PageData rules() { return new PageData("rules-sbermarket"); }

        static PageData payment() { return new PageData("payment"); }

        static PageData returnPolicy() { return new PageData("return"); }

        static PageData faq() { return new PageData("faq-sbermarket"); }

        static PageData terms() { return new PageData("terms-sbermarket"); }

        static PageData contacts() { return new PageData("contacts"); }
    }

    public interface Metro {

        static PageData about() { return new PageData("about-metro"); }

        static PageData delivery() { return new PageData("delivery-metro"); }

        static PageData rules() { return new PageData("rules-metro"); }

        static PageData payment() { return new PageData("payment-metro"); }

        static PageData returnPolicy() { return new PageData("return"); }

        static PageData faq() { return new PageData("faq-metro"); }

        static PageData terms() { return new PageData("terms-metro"); }

        static PageData contacts() { return new PageData("contacts"); }
    }

    public interface Lenta {

        static PageData about() { return new PageData("about-lenta"); }

        static PageData delivery() { return new PageData("delivery-lenta"); }

        static PageData rules() { return new PageData("rules-lenta"); }

        static PageData payment() { return new PageData("payment-lenta"); }

        static PageData returnPolicy() { return new PageData("return"); }

        static PageData faq() { return new PageData("faq-lenta"); }

        static PageData terms() { return new PageData("terms-lenta"); }

        static PageData contacts() { return new PageData("contacts"); }
    }

    public interface Admin {

        static PageData login() {
            return new PageData("admin/login",
                    "страница авторизации админки");
        }

        static PageData shipments() {
            return new PageData(
                    "admin/shipments");
        }

        static PageData retailers() {
            return new PageData(
                    "admin/retailers");
        }

        static PageData products() {
            return new PageData(
                    "admin/products");
        }

        static PageData imports() {
            return new PageData(
                    "admin/imports");
        }

        static PageData settings() {
            return new PageData(
                    "admin/general_settings/edit");
        }

        static PageData marketing() {
            return new PageData(
                    "admin/promo_cards");
        }

        static PageData staff() {
            return new PageData(
                    "admin/shoppers");
        }

        static PageData users() {
            return new PageData(
                    "admin/users");
        }

        static PageData pages() {
            return new PageData(
                    "admin/pages");
        }

        static PageData stores(int sid) {
            return new PageData(
                    "admin/stores/" + sid);
        }

        static PageData oktell() {
            return new PageData(
                    "widgets/oktell_order_view");
        }

        public interface Order {

            static PageData details(String number) {
                return new PageData(
                        "orders/" + number + "/edit");
            }

            static PageData payments(String number) {
                return new PageData(
                        "orders/" + number + "/payments");
            }

            static PageData requisites(String number) {
                return new PageData("orders/" + number + "/customer");
            }
        }
    }
}
