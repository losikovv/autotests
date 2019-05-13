package ru.instamart.autotests.application;

public class Pages {
    private static String pageTitle;
    private static String pagePath;

    // todo возвращать не path, а url c подставленной переменной окружения baseURL + path
    Pages(String title, String path) {
        pageTitle = title;
        pagePath = path;
    }

    public static Pages page404() { return new Pages(null, "nowhere"); }
    public static Pages page500() { return new Pages(null, "stores/21/shipping_methods"); }

    public interface Site {

        static Pages checkout() {
            return new Pages(
                    "Доставка продуктов на дом из интернет-магазина. Купить продукты на дом в Москве — Instamart",
                    "checkout/edit");
        }

        interface Retailers {
            static Pages metro() { return new Pages(null, "metro"); }
            static Pages vkusvill() { return new Pages(null, "vkusvill"); }
            static Pages lenta() { return new Pages(null, "lenta"); }
            static Pages karusel() { return new Pages(null, "karusel"); }
            static Pages auchan() { return new Pages(null, "auchan"); }
            static Pages selgros() { return new Pages(null, "selgros"); }
            static Pages flora() { return new Pages(null, "flora"); }
            static Pages foodcity() { return new Pages(null, "foodcity"); }
            static Pages magnit() { return new Pages(null, "magnit"); }
            static Pages testretailer() { return new Pages(null, "testretailer"); }
        }

        interface Catalog {

            static Pages seo() {
                return new Pages(
                        "Гречневая крупа купить в Москве. Скидки до 50% – Инстамарт",
                        "categories/bakalieia/krupy/griechnievaia");
            }

        }

        interface Landings {

            static Pages instamart() {
                return new Pages(
                        "Доставка продуктов на дом в Москве от интернет-магазина Instamart ",
                        "");
            }

            static Pages mnogoru() {
                return new Pages("Много.ру",
                        "mnogoru");
            }

            static Pages aeroflot() {
                return new Pages("Аэрофлот Бонус",
                        "aeroflot_09-02-31-12");
            }

            static Pages sovest() {
                return new Pages("Совесть",
                        "sovest");
            }

            static Pages halva() {
                return new Pages("Халва",
                        "halva");
            }

            static Pages feedback() {
                return new Pages("Оцените нас!",
                        "landings/feedback");
            }

            static Pages kazan() {
                return new Pages("г. Казань - Instamart",
                        "cities/kazan");
            }

            static Pages mobile() {
                return new Pages("METRO - доставка продуктов на дом",
                        "landings/m-general-white");
            }

            // TODO Дописать лендинг вкусвилл

            // TODO Дописать лендинг метро
        }

        interface Static {

            static Pages about() {
                return new Pages("Информация о компании Instamart",
                        "about");
            }

            static Pages delivery() {
                return new Pages("Доставка",
                        "delivery");
            }

            static Pages rules() {
                return new Pages("Правила работы",
                        "rules");
            }

            static Pages payment() {
                return new Pages("Оплата",
                        "payment");
            }

            static Pages returnPolicy() {
                return new Pages("Политика возврата - Instamart",
                        "return");
            }

            static Pages faq() {
                return new Pages("FAQ - Instamart",
                        "faq");
            }

            static Pages terms() {
                return new Pages("Публичная офферта",
                        "terms");
            }

            static Pages contacts() {
                return new Pages("Контакты - Instamart",
                        "contacts");
            }
        }

        interface Profile {

            static Pages edit() {
                return new Pages("Информация об аккаунте",
                        "user/edit");
            }

            static Pages favorites() {
                return new Pages("Любимые товары",
                        "user/favorites");
            }

            static Pages orders() {
                return new Pages("Заказы",
                        "user/orders");
            }

            static Pages addresses() {
                return new Pages("Адреса",
                        "user/addresses");
            }

            static Pages orderDetails(String orderNumber) {
                return new Pages(null,
                        "user/orders/" + orderNumber);
            }

        }

    }

    public interface Admin {

        static Pages shipments() {
            return new Pages("Spree Администрирование: Отправки",
                    "admin/shipments");
        }

        static Pages retailers() {
            return new Pages("Spree Администрирование: Retailers",
                    "admin/retailers");
        }

        static Pages products() {
            return new Pages("Spree Администрирование: Товары",
                    "admin/products");
        }

        static Pages imports() {
            return new Pages("Spree Администрирование: Imports",
                    "admin/imports");
        }

        static Pages settings() {
            return new Pages("Spree Администрирование: Общие настройки",
                    "admin/general_settings/edit");
        }

        static Pages marketing() {
            return new Pages("Spree Администрирование: Promo Cards",
                    "admin/promo_cards");
        }

        static Pages staff() {
            return new Pages("Spree Администрирование: Shoppers",
                    "admin/shoppers");
        }

        static Pages users() {
            return new Pages("Spree Администрирование: Пользователи",
                    "admin/users");
        }

        static Pages pages() {
            return new Pages("Spree Администрирование: Страницы",
                    "admin/pages");
        }

        public interface Order {

            static Pages details(String number) {
                return new Pages("Spree Администрирование: Заказы",
                        "orders/" + number + "/edit");
            }

            static Pages payments(String number) {
                return new Pages("Spree Администрирование: Платежи",
                        "orders/" + number + "/payments");
            }

            static Pages requisites(String number) {
                return new Pages("Spree Администрирование: Реквизиты клиента",
                        "orders/" + number + "/customer");
            }

        }

    }

    public static String getPageTitle() {
        return pageTitle;
    }

    public static String getPagePath() {
        return pagePath;
    }

    // TODO public static String getPageUrl() { return fullBaseUrl + pagePath; }

}
