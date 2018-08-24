package ru.instamart.autotests.configuration;

import org.openqa.selenium.By;



// Web-элементы сайта



public class Elements {
    static String text;
    static By locator;

    Elements(String elementText, By elementLocator) {
        text = elementText;
        locator = elementLocator;
    }

    public static String getText() {
        return text;
    }

    public static By getLocator() {
        return locator;
    }


    /** Страница 500 ошибки */

    public interface Page500 {

        static Elements placeholder() {
            text = "We're sorry, but something went wrong.";
            locator = By.xpath("/html/body/div/h1");
            return new Elements(text, locator);
        }

    }


    /** Страница 404 ошибки */

    public interface Page404 {

        static Elements title() {
            text = "Страница не найдена";
            locator = By.xpath("/html/body/div[3]/div/div/div/div[1]/div/div[1]");
            return new Elements(text, locator);
        }

    }


    /** Сайт */

    public interface Site {


        /** Шапка сайта */

        interface Header {


            static Elements setShipAddressButton() {
                text = "Ввести адрес";
                locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/div/div/div/div/div/button");
                return new Elements(text, locator);
            }

            static Elements changeShipAddressButton() {
                text = "Изменить";
                locator = By.className("ship-address-selector__edit-btn");
                return new Elements(text, locator);
            }

            static Elements currentShipAddress() {
                locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/div/div/div/div/div/span");
                return new Elements(null, locator);
            }

            static Elements loginButton() {
                locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/div/header/nav/div[3]");
                return new Elements(null, locator);
            }

            static Elements profileButton() {
                text = "Профиль";
                locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[1]/div[1]");
                return new Elements(text, locator);
            }

            static Elements openCartButton() {
                locator = By.className("open-new-cart");
                return new Elements(null, locator);
            }

            static Elements changeStoreButton() {
                locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/div[1]/div[3]/div/div[1]");
                return new Elements(null, locator);
            }

            static Elements searchField() {
                locator = By.className("header-search__inp");
                return new Elements(null, locator);
            }

            static Elements searchButton() {
                locator = By.className("header-search__btn");
                return new Elements(null, locator);
            }
        }


        /** Всплывающее меню "Профиль" */

        interface AccountMenu {

            static Elements popup() {
                locator = By.className("account-menu");
                return new Elements(null, locator);
            }

            static Elements header() {
                locator = By.className("account-menu__header");
                return new Elements(null, locator);
            }

            static Elements profileButton() {
                locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[3]/a");
                return new Elements(null, locator);
            }

            static Elements ordersButton() {
                locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[4]/a");
                return new Elements(null, locator);
            }

            static Elements termsButton() {
                locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[5]/a");
                return new Elements(null, locator);
            }

            static Elements blogButton() {
                locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[6]/a");
                return new Elements(null, locator);
            }

            static Elements logoutButton() {
                locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[8]/a");
                return new Elements(null, locator);
            }

            static Elements deliveryLink() {
                locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[10]/button[1]");
                return new Elements(null, locator);
            }

            static Elements paymentLink() {
                locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[10]/button[2]");
                return new Elements(null, locator);
            }

            static Elements faqLink() {
                locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[10]/button[3]");
                return new Elements(null, locator);
            }

            static Elements contactsLink() {
                locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[10]/button[4]");
                return new Elements(null, locator);
            }

        }


        /** Модалка авторизации / регистрации */

        interface AuthModal {


            static Elements popup() {
                locator = By.className("auth-modal");
                return new Elements(null, locator);
            }

            static Elements submitButton() {
                locator = By.className("auth-modal__button");
                return new Elements(null, locator);
            }

        }


        /** Адресные модалки Феникса */

        interface AddressModal {


            static Elements header() {
                locator = By.className("address-modal__header");
                return new Elements(null, locator);
            }

            static Elements closeButton() {
                locator = By.className("modal-container__close");
                return new Elements(null, locator);
            }

            static Elements addressField() {
                locator = By.id("ship_address");
                return new Elements(null, locator);
            }

            static Elements addressSuggest() {
                locator = By.className("modal-address-autocomplete__dropdown-item");
                return new Elements(null, locator);
            }

            static Elements saveButton() {
                text = "Сохранить";
                locator = By.xpath("//*[@id='react-modal']/div/div/div/span/div[1]/div/div/div/div/div/div[2]/form/button");
                return new Elements(text, locator);
            }

            static Elements recentAddressesList() {
                locator = By.className("address-modal__addresses");
                return new Elements(null, locator);
            }

            static Elements recentAddress() {
                locator = By.className("address-modal__address");
                return new Elements(null, locator);
            }

        }


        /** Селектор магазинов */

        interface ShopSelector {

            static Elements closeButton() {
                locator = By.className("store-selector__close");
                return new Elements(null, locator);
            }

            static Elements storeButton() {
                locator = By.className("store-card");
                return new Elements(null, locator);
            }

            public static Elements noStoresPlaceholder() {
                text = "Нет доступных магазинов по выбранному адресу";
                locator = By.className("store-selector__empty");
                return new Elements(null, locator);
            }

        }


        /** Каталог товаров */

        interface Catalog {


            static Elements emptySearchPlaceholder() {
                locator = By.className("search__noresults");
                return new Elements(null, locator);
            }

            static Elements product() {
                locator = By.className("product");
                return new Elements(null, locator);
            }

            static Elements firstItem() {
                locator = By.xpath("//*[@id='home']/div[2]/ul/li[1]/ul/li[1]/a");
                return new Elements(null, locator);
            }

        }


        /** Карточка товара */

        interface ItemCard {


            static Elements popup() {
                locator = By.className("product-popup");
                return new Elements(null, locator);
            }

            static Elements closeButton() {
                locator = By.className("close");
                return new Elements(null, locator);
            }

            static Elements plusButton() {
                locator = By.xpath("//*[@id='react-modal']/div/div/div/span/div[1]/div/div/div/div/span/div/div[2]/div[2]/div[2]/div/div[3]/button[2]");
                return new Elements(null, locator);
            }

            static Elements minusButton() {
                locator = By.xpath("//*[@id='react-modal']/div/div/div/span/div[1]/div/div/div/div/span/div/div[2]/div[2]/div[2]/div/div[3]/button[1]");
                return new Elements(null, locator);
            }

        }


        /** Корзина */

        interface Cart {

            static Elements drawer() {
                locator = By.className("new-cart");
                return new Elements(null, locator);
            }

            static Elements closeButton() {
                locator = By.className("btn-close-cart");
                return new Elements(null, locator);
            }

            static Elements placeholder() {
                locator = By.className("new-cart-empty");
                return new Elements(null, locator);
            }

            static Elements checkoutButton() {
                locator = By.className("cart-checkout-link");
                return new Elements(null, locator);
            }

        }


        /** Подвал сайта */

        interface Footer {

            static Elements aboutCompanyButton() {
                text = "О компании";
                locator = By.xpath("//*[@id='new-home-footer']/div/div[1]/div[1]/ul/li[1]/a");
                return new Elements(text, locator);
            }

            static Elements contactsButton() {
                text = "Контакты";
                locator = By.xpath("//*[@id='new-home-footer']/div/div[1]/div[1]/ul/li[2]/a");
                return new Elements(text, locator);
            }

            static Elements deliveryButton() {
                text = "Доставка";
                locator = By.xpath("//*[@id='new-home-footer']/div/div[1]/div[1]/ul/li[3]/button");
                return new Elements(text, locator);
            }

            static Elements paymentButton() {
                text = "Оплата";
                locator = By.xpath("//*[@id='new-home-footer']/div/div[1]/div[1]/ul/li[4]/button");
                return new Elements(text, locator);
            }

            static Elements partnersButton() {
                text = "Партнеры";
                locator = By.xpath("//*[@id='new-home-footer']/div/div[1]/div[1]/ul/li[5]/button");
                return new Elements(text, locator);
            }

            static Elements faqButton() {
                text = "FAQ";
                locator = By.xpath("//*[@id='new-home-footer']/div/div[1]/div[1]/ul/li[6]/a");
                return new Elements(text, locator);
            }

            static Elements feedbackFormButton() {
                text = "Форма обратной связи";
                locator = By.xpath("//*[@id='new-home-footer']/div/div[1]/div[2]/ul/li[3]/a");
                return new Elements(text, locator);
            }

            static Elements returnPolicyButton() {
                text = "Политика возврата";
                locator = By.xpath("//*[@id='new-home-footer']/div/div[2]/div[2]/a[1]");
                return new Elements(text, locator);
            }

            static Elements publicOfferButton() {
                text = "Публичная оферта";
                locator = By.xpath("//*[@id='new-home-footer']/div/div[2]/div[2]/a[2]");
                return new Elements(text, locator);
            }

        }
    }


    /** Админка */

    public interface Admin {


        /** Шапка админки */

        interface Header {

            static Elements logoutButton() {
                locator = By.xpath("//*[@id='login-nav']/li[3]/a");
                return new Elements(null, locator);
            }

            static Elements profileButton() {
                text = "Учетная запись";
                locator = By.xpath("//*[@id='login-nav']/li[2]/a");
                return new Elements(text, locator);
            }

            static Elements backToListButton() {
                text = "Назад к списку";
                locator = By.xpath("//*[@id='login-nav']/li[4]/a");
                return new Elements(text, locator);

            }


            /** Меню админки */

            interface Menu {

                static Elements ordersButton() {
                    text = "Заказы";
                    locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[1]/a/span");
                    return new Elements(text, locator);
                }

                static Elements storeButton() {
                    text = "Магазины";
                    locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[2]/a/span");
                    return new Elements(text, locator);
                }

                static Elements productsButton() {
                    text = "Продукты";
                    locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[3]/a/span");
                    return new Elements(text, locator);
                }

                static Elements importButton() {
                    text = "Импорт";
                    locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[4]/a/span");
                    return new Elements(text, locator);
                }

                static Elements reportsButton() {
                    text = "Отчеты";
                    locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[5]/a/span");
                    return new Elements(text, locator);
                }

                static Elements settingsButton() {
                    text = "Настройки";
                    locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[6]/a/span");
                    return new Elements(text, locator);
                }

                static Elements marketingButton() {
                    text = "Маркетинг";
                    locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[7]/a/span");
                    return new Elements(text, locator);
                }

                static Elements usersButton() {
                    text = "Пользователи";
                    locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[8]/a/span");
                    return new Elements(text, locator);
                }

                static Elements pagesButton() {
                    text = "Страницы";
                    locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[9]/a/span");
                    return new Elements(text, locator);
                }

            }


            /** Подменю "Заказы" */

            interface SubmenuOrders {

                static Elements multiOrderButton() {
                    text = "Мульти заказ";
                    locator = By.xpath("//*[@id='sub_nav']/li[1]/a");
                    return new Elements(text, locator);

                }

                static Elements exportButton() {
                    text = "Export";
                    locator = By.xpath("//*[@id='sub_nav']/li[2]/a");
                    return new Elements(text, locator);
                }

                static Elements veerouteButton() {
                    text = "Veeroute";
                    locator = By.xpath("//*[@id='sub_nav']/li[3]/a");
                    return new Elements(text, locator);
                }

            }


            /** Подменю "Магазины" */

            interface SubmenuStores {


                static Elements retailersButton() {
                    text = "Ритейлеры";
                    locator = By.xpath("//*[@id='sub_nav']/li[1]/a");
                    return new Elements(text, locator);
                }

                static Elements zonesButton() {
                    text = "Зоны";
                    locator = By.xpath("//*[@id='sub_nav']/li[2]/a");
                    return new Elements(text, locator);
                }
            }


            /** Подменю "Продукты" */

            interface SubmenuProducts {


                static Elements subProductsButton() {
                    text = "Продукты";
                    locator = By.xpath("//*[@id='sub_nav']/li[1]/a");
                    return new Elements(text, locator);
                }

                static Elements productsStatsButton() {
                    text = "Статистика";
                    locator = By.xpath("//*[@id='sub_nav']/li[2]/a");
                    return new Elements(text, locator);
                }

                static Elements optionTypesButton() {
                    text = "Option Types";
                    locator = By.xpath("//*[@id='sub_nav']/li[3]/a");
                    return new Elements(text, locator);
                }

                static Elements propertiesButton() {
                    text = "Properties";
                    locator = By.xpath("//*[@id='sub_nav']/li[4]/a");
                    return new Elements(text, locator);
                }

                static Elements prototypesButton() {
                    text = "Prototypes";
                    locator = By.xpath("//*[@id='sub_nav']/li[5]/a");
                    return new Elements(text, locator);
                }

                static Elements brandsButtton() {
                    text = "Бренды";
                    locator = By.xpath("//*[@id='sub_nav']/li[6]/a");
                    return new Elements(text, locator);
                }

                static Elements producersButton() {
                    text = "Производители";
                    locator = By.xpath("//*[@id='sub_nav']/li[7]/a");
                    return new Elements(text, locator);
                }

                static Elements producersCountriesButton() {
                    text = "Страны производства";
                    locator = By.xpath("//*[@id='sub_nav']/li[8]/a");
                    return new Elements(text, locator);
                }

            }


            /** Подменю "Импорт" */

            interface SubmenuImport {

                static Elements queueOfTasksButton() {
                    text = "Очередь задач";
                    locator = By.xpath("//*[@id='sub_nav']/li[1]/a");
                    return new Elements(text, locator);
                }

                static Elements importStatsButton() {
                    text = "Статистика";
                    locator = By.xpath("//*[@id='sub_nav']/li[2]/a");
                    return new Elements(text, locator);
                }

                static Elements archiveButton() {
                    text = "Архив";
                    locator = By.xpath("//*[@id='sub_nav']/li[3]/a");
                    return new Elements(text, locator);
                }

                static Elements blackListButton() {
                    text = "Черный список Gm";
                    locator = By.xpath("//*[@id='sub_nav']/li[4]/a");
                    return new Elements(text, locator);
                }

                static Elements categoryButton() {
                    text = "Категории";
                    locator = By.xpath("//*[@id='sub_nav']/li[5]/a");
                    return new Elements(text, locator);
                }

                static Elements filtersButton() {
                    text = "Фильтры";
                    locator = By.xpath("//*[@id='sub_nav']/li[6]/a");
                    return new Elements(text, locator);
                }

                static Elements importProductsButton() {
                    text = "Продукты";
                    locator = By.xpath("//*[@id='sub_nav']/li[7]/a");
                    return new Elements(text, locator);
                }

                static Elements priceButton() {
                    text = "Цены";
                    locator = By.xpath("//*[@id='sub_nav']/li[8]/a");
                    return new Elements(text, locator);
                }

            }


            /** Подменю "Маркетинг" */

            interface SubmenuMarketing {

                static Elements promoCardsButton() {
                    text = "Промо карточки";
                    locator = By.xpath("//*[@id='sub_nav']/li[1]/a/span");
                    return new Elements(text, locator);
                }

                static Elements promoActionButton() {
                    text = "Промо акции";
                    locator = By.xpath("//*[@id='sub_nav']/li[2]/a/span");
                    return new Elements(text, locator);
                }

                static Elements welcomeBannersButton() {
                    text = "Welcome баннеры";
                    locator = By.xpath("//*[@id='sub_nav']/li[3]/a/span");
                    return new Elements(text, locator);
                }

                static Elements advertisementButton() {
                    text = "Реклама";
                    locator = By.xpath("//*[@id='sub_nav']/li[4]/a/span");
                    return new Elements(text, locator);
                }

                static Elements yandexMarketButton() {
                    text = "Яндекс.Маркет";
                    locator = By.xpath("//*[@id='sub_nav']/li[5]/a/span");
                    return new Elements(text, locator);
                }

                static Elements cartsButton() {
                    text = "Корзины";
                    locator = By.xpath("//*[@id='sub_nav']/li[6]/a/span");
                    return new Elements(text, locator);
                }

                static Elements bonusCardsButton() {
                    text = "Бонусные карты";
                    locator = By.xpath("//*[@id='sub_nav']/li[7]/a/span");
                    return new Elements(text, locator);
                }


                static Elements retailersProgramsButton() {
                    text = "Программы ритейлеров";
                    locator = By.xpath("//*[@id='sub_nav']/li[8]/a/span");
                    return new Elements(text, locator);
                }

                static Elements newCitiesButton() {
                    text = "Новые Города";
                    locator = By.xpath("//*[@id='sub_nav']/li[9]/a/span");
                    return new Elements(text, locator);
                }

            }

        }


        /** Страница деталей заказа в админке */

        interface OrderPage {

            static Elements resumeOrderButton() {
                locator = By.xpath("//*[@id='content-header']/div/div/div/div[2]/ul/li[1]/form/button");
                return new Elements(null, locator);
            }

            static Elements canceledOrderAttribute() {
                text = "ЗАКАЗ ОТМЕНЕН";
                locator = By.xpath("//*[@id='content']/div/table/tbody/tr[3]/td/b");
                return new Elements(null, locator);
            }

        }

    }

}
