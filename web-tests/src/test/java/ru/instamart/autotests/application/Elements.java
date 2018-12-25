package ru.instamart.autotests.application;

import org.openqa.selenium.By;

public class Elements {
    static String text;
    static By locator;

    Elements(String elementText, By elementLocator) {
        text = elementText;
        locator = elementLocator;
    }

    public static String text() {
        return text;
    }

    public static By locator() {
        return locator;
    } // todo возвращать локатор в зависимости от окружения


    /**
     * Страница 500 ошибки
     */
    public interface Page500 {
        static Elements placeholder() {
            text = "We're sorry, but something went wrong.";
            locator = By.xpath("/html/body/div/h1");
            return new Elements(text, locator);
        }
    }


    /**
     * Страница 404 ошибки
     */
    public interface Page404 {
        static Elements title() {
            text = "Страница не найдена";
            locator = By.xpath("/html/body/div[1]/div/div/div/div[1]/div/div[1]");
            return new Elements(text, locator);
        }
    }


    /**
     * Сайт
     */
    public interface Site {

        /**
         * Шапка сайта
         */
        interface Header {

            static Elements shipAddressButton() {
                return new Elements(null, By.className("ship-address-selector__edit-btn"));
            }

            static Elements currentShipAddress() {
                locator = By.className("ship-address-selector__full-address");
                return new Elements(null, locator);
            }

            static Elements loginButton() {
                locator = By.xpath("//*[@id='wrap']/div[1]/div/div/header/nav/div[3]/button");
                return new Elements(null, locator);
            }

            static Elements profileButton() {
                text = "Профиль";
                locator = By.xpath("//*[@id='wrap']/div[1]/div/div/header/nav/div[3]/div/div[1]/div[1]");
                return new Elements(text, locator);
            }

            static Elements changeStoreButton() {
                locator = By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[3]/div/div[1]");
                return new Elements(null, locator);
            }

            static Elements deliveryButton() {
                return new Elements(null,
                        By.cssSelector("button.navbar-button:nth-child(3) > div:nth-child(1)"));
            }

            static Elements partnersButton() {
                return new Elements(null,
                        By.cssSelector("button.navbar-button:nth-child(5) > div:nth-child(1)"));
            }


            /**
             * Поиск
             */
            interface Search {

                static Elements searchField() {
                    return new Elements(null, By.cssSelector(".header-search--active > input:nth-child(1)"));
                }

                static Elements searchButton() {
                    return new Elements(null, By.cssSelector(".header-search--active > button:nth-child(2)"));
                }

                static Elements categorySuggest() {
                    return new Elements(null, By.className("header-search-list-category"));
                }

                static Elements productSuggest() {
                    return new Elements(null, By.className("header-search-list-product"));
                }
            }
        }


        /**
         * Модалка "Доставка"
         */
        interface DeliveryModal {

            static Elements popup() {
                return new Elements(null,
                        By.cssSelector(".modal-lg > div:nth-child(1)"));
            }

            static Elements title() {
                return new Elements("Доступные интервалы",
                        By.cssSelector(".delivery__intervals > h3:nth-child(1)"));
            }

            static Elements closeButton() {
                return new Elements(null, By.cssSelector(".close"));
            }
        }


        /**
         * Модалка "Партнеры"
         */
        interface PartnersModal {

            static Elements popup() {
                return new Elements(null,
                        By.cssSelector(".modal-lg > div:nth-child(1)"));
            }

            static Elements title() {
                return new Elements("Наши партнеры",
                        By.cssSelector(".modal-title"));
            }

            static Elements closeButton() {
                return new Elements(null,
                        By.cssSelector(".close"));
            }
        }

        /**
         * Модалка "Оплата"
         */
        interface PaymentModal {

            static Elements popup() {
                return new Elements(null,
                        By.cssSelector(".modal-lg > div:nth-child(1)"));
            }

            static Elements title() {
                return new Elements("Какие существуют способы оплаты?",
                        By.cssSelector(".modal-title"));
            }

            static Elements closeButton() {
                return new Elements(null,
                        By.cssSelector(".close"));
            }
        }


        /**
         * Всплывающее меню "Профиль"
         */
        interface AccountMenu {

            static Elements popup() {
                return new Elements(null, By.className("account-menu"));
            }

            static Elements header() {
                return new Elements(null, By.className("account-menu__header"));
            }

            static Elements profileButton() {
                return new Elements("Профиль", By.linkText("Профиль"));
            }

            static Elements ordersButton() {
                return new Elements("Заказы", By.linkText("Заказы"));
            }

            static Elements termsButton() {
                return new Elements("Условия пользования", By.linkText("Условия пользования"));
            }

            static Elements blogButton() {
                return new Elements("Блог", By.linkText("Блог"));
            }

            static Elements logoutButton() {
                return new Elements("Выйти", By.linkText("Выйти"));
            }

            static Elements deliveryLink() {
                return new Elements(null, By.cssSelector("a.account-menu__footer-link:nth-child(1)"));
            }

            static Elements paymentLink() {
                return new Elements(null, By.cssSelector("a.account-menu__footer-link:nth-child(2)"));
            }

            static Elements faqLink() {
                return new Elements(null, By.cssSelector("a.account-menu__footer-link:nth-child(3)"));
            }

            static Elements contactsLink() {
                return new Elements(null, By.cssSelector("a.account-menu__footer-link:nth-child(4)"));
            }
        }


        /**
         * Шторка каталога
         */
        interface CatalogDrawer {

            static Elements drawer() {
                return new Elements(null, By.cssSelector(".drawer__left"));
            }

            static Elements openCatalogButton() {
                return new Elements(null, By.className("navbar-button--catalog"));
            }

            static Elements closeCatalogButton() {
                return new Elements(null, By.className("header-navbar-taxons__close"));
            }
        }


        /**
         * Модалка авторизации / регистрации
         */
        interface AuthModal {

            static Elements popup() {
                return new Elements(null, By.className("auth-modal"));
            }

            static Elements closeButton() {
                return new Elements(null, By.className("modal-wrapper__close"));
            }

            static Elements authorisationTab() {
                return new Elements(null, By.cssSelector("button.auth-modal__tab:nth-child(1)"));
            }

            static Elements registrationTab() {
                return new Elements(null, By.cssSelector("button.auth-modal__tab:nth-child(2)"));
            }

            static Elements nameField() {
                return new Elements(null, By.name("fullname"));
            }

            static Elements emailField() {
                return new Elements(null, By.name("email"));
            }

            static Elements passwordField() {
                return new Elements(null, By.name("password"));
            }

            static Elements passwordConfirmationField() {
                return new Elements(null, By.name("passwordConfirmation"));
            }

            static Elements forgotPasswordButton() {
                return new Elements("Забыли пароль?", By.className("auth-modal__forgotten"));
            }

            static Elements submitButton() {
                return new Elements(null, By.className("auth-modal__button"));
            }

            static Elements successRecoveryText() {
                return new Elements("На указанный вами E-mail высланы инструкции по восстановлению пароля.",
                        By.cssSelector(".auth-modal__recovery-text > span:nth-child(1)"));
            }

            // TODO Добавить/переделать локаторы под авторизацию

            static Elements nameErrorMessage() {
                return new Elements(null,
                        (By.xpath("//*[@id='auth']/div/div/div[1]/form/div/div[1]/div[2]")));
            }

            static Elements emailErrorMessage() {
                return new Elements(null,
                        (By.xpath("//*[@id='auth']/div/div/div[1]/form/div/div[2]/div[2]")));
            }

            static Elements passwordErrorMessage() {
                return new Elements(null,
                        (By.xpath("//*[@id='auth']/div/div/div[1]/form/div/div[3]/div[2]")));
            }

            static Elements passwordConfirmationErrorMessage() {
                return new Elements(null,
                        (By.xpath("//*[@id='auth']/div/div/div[1]/form/div/div[4]/div[2]")));
            }
        }

        /**
         * Адресные модалки Феникса
         */
        interface AddressModal {

            static Elements header() {
                locator = By.className("address-modal__header");
                return new Elements(null, locator);
            }

            static Elements closeButton() {
                locator = By.className("modal-wrapper__close");
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
                return new Elements("Сохранить", By.className("address-modal__button"));
            }

            static Elements recentAddressesList() {
                locator = By.className("address-modal__addresses");
                return new Elements(null, locator);
            }

            static Elements recentAddress() {
                locator = By.xpath("//*[@id='react-modal']/div/div/div/div/div/div[1]/div/div/div/div/div/div[2]/div/div[2]");
                return new Elements(null, locator);
            }

            static Elements authButton() {
                locator = By.xpath("//*[@id='react-modal']/div/div/div/div/div/div[1]/div/div/div/div/div/div[2]/div/span");
                return new Elements(null, locator);
            }

            static Elements popup() {
                return new Elements(null,
                        By.className("address-modal"));
            }

            static Elements titleSet() {
                return new Elements ("Введите адрес доставки",
                        By.className("address-modal__header"));
            }

            static Elements titleChange() {
                return new Elements ("Редактирование адреса",
                        By.className("address-modal__header"));
            }

            static Elements titleOutOfZone() {
                return new Elements ("Адрес не в зоне доставки",
                        By.className("address-modal__header"));
            }

            static Elements setNewAddress() {
                return new Elements("Выбрать другой адрес",
                        By.className("address-modal__button--reselect"));
            }
        }


        /**
         * Феникс-модалка выбора магазина при отсутствии текущего ритейлера по выбранному адресу
         */
        interface StoresModal {

            static Elements popup() {
                return new Elements("Выберите магазин",
                        By.className("stores-modal"));

            }
            static Elements firstStoreAvailable() {
                return new Elements("Выбор магазина",
                        By.className("store-card"));
            }

            static Elements setNewAddress() {
                return new Elements("Ввести другой адрес",
                        By.className("stores-modal__to-login-link"));
            }
        }


        /**
         * Селектор магазинов
         */
        interface StoreSelector {

            static Elements drawer() {
                return new Elements(null,
                        By.className("store-selector"));
            }

            static Elements closeButton() {
                return new Elements(null,
                        By.className("store-selector__close"));
            }

            static Elements placeholder() {
                return new Elements("Нет доступных магазинов по выбранному адресу",
                        By.className("store-selector__empty"));
            }
        }


        /**
         * Каталог товаров
         */
        interface Catalog {

            static Elements emptySearchPlaceholder() {
                return new Elements(null, By.cssSelector(".resource-not-found__message-block"));
            }

            static Elements product() {
                return new Elements(null, By.className("product"));
            }

            static Elements firstItem() {
                return new Elements(null, By.className("product__img")
                );
            }

            static Elements firstItemPlusButton() {
                return new Elements(null, By.className("cart-actions__btn"));
            }

        }


        /**
         * Модалка "Обновить цены"
         */
        interface RefreshPricesModal { //TODO добавить проверку в hitPlus в карточке товара

            static Elements popup() {
                return new Elements(null, By.className("prices-modal"));
            }

            static Elements refreshPricesButton() {
                return new Elements(null, By.className("prices-modal__btn"));
            }
        }


        /**
         * Карточка товара
         */
        interface ItemCard {

            static Elements popup() {
                return new Elements(null, By.className("product-popup"));
            }

            static Elements price() {
                return new Elements(null,
                        By.cssSelector(".product-popup__price > div:nth-child(1) > span:nth-child(1)"));
            }

            static Elements salePrice() {
                return new Elements(null,
                        By.cssSelector(".product-popup__sale-price > div:nth-child(1) > span:nth-child(1)"));
            }

            static Elements saleBadge() {
                return new Elements(null,
                        By.className("sale-badge"));
            }

            static Elements closeButton() {
                return new Elements(null, By.className("close"));
            }

            static Elements plusButton() {
                return new Elements(null, By.className("popup-cart-actions__btn--right"));
            }

            static Elements minusButton() {
                return new Elements(null, By.className("popup-cart-actions__btn--left"));
            }
        }


        /**
         * Корзина
         */
        interface Cart {

            static Elements drawer() {
                return new Elements(null, By.className("new-cart"));
            }

            static Elements closeButton() {
                return new Elements(null, By.className("btn-close-cart"));
            }

            static Elements placeholder() {
                return new Elements(null, By.className("new-cart-empty"));
            }

            static Elements checkoutButton() {
                return new Elements(null, By.className("cart-checkout-link"));
            }

            static Elements total() {
                return new Elements(null, By.cssSelector(".cart-checkout-link__well > div:nth-child(1)"));
            }

            static Elements openCartButton() {
                return new Elements(null,
                        By.xpath("//*[@id='wrap']/div[1]/div/div/header/nav/div[4]/div/div"));
            }
        }


        /**
         * Подвал сайта
         */

        static Elements footer() {
            locator = By.className("footer");
            return new Elements(text, locator);
        }

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


        /**
         * Лендинг
         */
        // TODO вытащить наверх из Site

        interface Landing {

            static Elements header() {
                return new Elements(null, By.className("top-line"));
            }

            static Elements addressField() {
                return new Elements(null, By.id("header_ship_address"));
            }

            static Elements addressSuggest() {
                return new Elements(null, By.id("downshift-0-item-0"));
            }

            static Elements selectStoreButton() {
                return new Elements("Выбрать магазин",
                        By.cssSelector(".ship-address-selector-form__action"));
            }

            static Elements catalogButton() {
                return new Elements("Перейти в каталог", By.cssSelector("a.btn"));
            }

            static Elements headButton() {
                text = "Правила работы";
                locator = By.xpath("/html/body/a[1]/div");
                return new Elements(text, locator);
            }

            static Elements rulesButton() {
                text = "Как мы работаем";
                locator = By.xpath("/html/body/div[4]/header/div[1]/ul/li[1]/a");
                return new Elements(text, locator);
            }

            static Elements helpButton() {
                text = "Помощь";
                locator = By.xpath("/html/body/div[4]/header/div[1]/ul/li[2]/a");
                return new Elements(text, locator);
            }

            static Elements callNumberButton() {
                text = "Набор номера 1";
                locator = By.xpath("/html/body/div[4]/header/div[2]/ul/li[2]/a/span[1]");
                return new Elements(text, locator);
            }

            static Elements callSupportButton() {
                text = "Набор номера 2";
                locator = By.xpath("/html/body/div[4]/header/div[2]/ul/li[2]/a/span[2]");
                return new Elements(text, locator);
            }

            static Elements loginButton() {
                return new Elements(null, By.linkText("Вход"));
            }

            static Elements pricesWithoutChargesButton() {
                text = "Цены без наценок";
                locator = By.xpath("/html/body/div[5]/div[1]/div[3]/div/div[3]/a[1]/span");
                return new Elements(text, locator);
            }

            static Elements freshFoodButton() {
                text = "Свежие продукты";
                locator = By.xpath("/html/body/div[5]/div[1]/div[3]/div/div[3]/a[2]/span");
                return new Elements(text, locator);
            }

            static Elements fastDeliveryButton() {
                text = "Быстрая доставка";
                locator = By.xpath("/html/body/div[5]/div[1]/div[3]/div/div[3]/a[3]/span");
                return new Elements(text, locator);
            }

            static Elements closePopupRulesButton() {
                text = "Закрытие попапа с правилами";
                locator = By.xpath("/html/body/div[4]/div/div/button/i");
                return new Elements(text, locator);
            }

            static Elements changeSlidesPopupButton() {
                text = "Переключение слайдов попапа";
                locator = By.xpath("/html/body/div[4]/div/div/div/div[3]/div[2]/span");
                return new Elements(text, locator);
            }

            static Elements mnogoruButton() {
                text = "Переход на много.ру";
                locator = By.xpath("/html/body/div[4]/header/div[2]/ul/li[1]/a");
                return new Elements(text, locator);
            }

            static Elements swipeCategoriesButton() {
                text = "Свайп подкатегорий";
                locator = By.xpath("//*[@id='new-home-top-categories']/div/div[2]/div[2]/div[2]/i");
                return new Elements(text, locator);
            }

            static Elements linkDownloadButton() {
                text = "Ссылка на скачивание";
                locator = By.xpath("/html/body/div[5]/div[7]/div[2]/div/div[3]/form/button");
                return new Elements(text, locator);
            }
        }


        /**
         * Чекаут
         */
        interface Checkout {

            static Elements header() {
                return new Elements(null, By.className("chekout-header"));
            }

            static Elements nextButton(int step) {
                return new Elements("Продолжить", By.xpath("(//button[@type='button'])[" + step + "]"));
            }

            static Elements sendOrderButton() {
                return new Elements(null, By.className("checkout-finalize__button"));
            }

            static Elements replacementPolicy(int option) {
                return new Elements(null,
                        By.cssSelector("div.replacement-policy:nth-child(" + option + ") > label:nth-child(1) > div:nth-child(2)"));
            }

            static Elements payment(int option) {
                return new Elements(null,
                        By.cssSelector("div.payment-method:nth-child(" + option + ") > div:nth-child(1)"));
            }

            static Elements addPromocodeButton() {
                return new Elements("Добавить промокод",
                        By.linkText("Добавить промокод"));
            }

            static Elements appliedPromocodeAttribute() {
                return new Elements("Промокод:",
                        By.className("promo-codes__label"));
            }

            static Elements clearPromocodeButton() {
                return new Elements("Удалить",
                        By.linkText("Удалить"));
            }

            // TODO изменить локатор, чтобы всегда выбирался первый по порядку доступный слот, даже если первые в списке заняты
            static Elements deliverySlot() {
                return new Elements(null,
                        By.xpath("/html/body/div[3]/div/form/div/div/div[1]/div[5]/div[2]/div/div/div/div[2]/div/div[2]/div[2]/div[1]/div/span"));
            }

            static Elements deliveryPrice() {
                return new Elements(null,
                        By.cssSelector("div.checkout-summary__subtotal:nth-child(4) > span:nth-child(2) > div:nth-child(1)"));
            }

            static Elements phoneIcon() {
                return new Elements(null, By.className("fa-mobile"));
            }

            static Elements phoneNumberField() {
                return new Elements(null, By.id("phone-input"));
            }

            interface PromocodeModal {

                static Elements title() {
                    return new Elements("Введите промокод",
                            By.className("modal-form__title"));
                }

                static Elements field() {
                    return new Elements(null,
                            By.id("couponCode"));
                }

                static Elements applyButton() {
                    return new Elements("Добавить код",
                            By.xpath("//*[@id='react-modal']/div[2]/div/div/div[2]/form/div[2]/div/div[2]/button"));
                }

                static Elements cancelButton() {
                    return new Elements("Отменить",
                            By.xpath("//*[@id='react-modal']/div[2]/div/div/div[2]/form/div[2]/div/div[1]/button"));
                }

                static Elements closeButton() {
                    return new Elements(null,
                            By.className("rc-modal__close"));
                }

            }

        }

        interface AccountPage {

            static Elements email() {
                return new Elements(null,
                        By.xpath("//*[@id='wrap']/div[2]/div/div/div/div[2]/div/div/div[2]/div[2]/div[2]"));
            }

            static Elements changePasswordButton() {
                return new Elements(null,
                        By.xpath("//*[@id='wrap']/div[2]/div/div/div/div[2]/div/div/div[2]/div[1]/div[3]/button"));
            }

            static Elements changeEmailButton() {
                return new Elements(null,
                        By.xpath("//*[@id='wrap']/div[2]/div/div/div/div[2]/div/div/div[2]/div[2]/div[3]/button"));
            }

            static Elements changeNameButton() {
                return new Elements(null,
                        By.xpath("//*[@id='wrap']/div[2]/div/div/div/div[2]/div/div/div[2]/div[3]/div[3]/button"));
            }

        }

        interface OrdersPage {

            static Elements lastOrderActionButton() {
                return new Elements(null,
                        By.xpath("//*[@id='wrap']/div[2]/div/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/button"));
            }

            static Elements lastOrderActionButton(int position) {
                return new Elements(null,
                        By.xpath("//*[@id='wrap']/div[2]/div/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/button[" + position + "]"));
            }

            static Elements lastOrderDetailsButton(int position) {
                return new Elements("Детали заказа",
                        By.xpath("/html/body/div[4]/div[2]/div/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/a"));
            }
        }

        /**
         * Страница деталей заказа
         */
        interface OrderDetailsPage {

            /**
             * Признак активного заказа
             */
            static Elements activeOrderAttribute() {
                return new Elements("Благодарим за использование Instamart!",
                        By.cssSelector(".user-order-shipment-header__thanks"));
            }

            /**
             * Признак отмененного заказа
             */
            static Elements canceledOrderAttribute() {
                return new Elements(null,
                        By.className("user-order-shipment-header__container--canceled"));
            }

            /**
             * Документы к заказу
             */
            static Elements documentation(int position) {
                return new Elements(null,
                        By.xpath("//*[@id='wrap']/div[2]/div/div/div/div/div[2]/div/div[2]/div[2]/div/div/div[3]/a[" + position + "]"));
            }

            /**
             * Стоимость доставки
             */
            static Elements deliveryPrice() {
                return new Elements(null, By.xpath("/html/body/div[4]/div[2]/div/div/div/div/div[2]" +
                        "/div/div[2]/div[1]/div/div[3]/div[2]/div[2]/div"));
            }

        }

    }


    /**
     * Админка
     */

    public interface Admin {

        static Elements container() {
            locator = By.className("admin");
            return new Elements(null, locator);
        }

        /**
         * Шапка админки
         */
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


            /**
             * Меню админки
             */
            interface Menu {

                static Elements ordersButton() {
                    text = "Заказы";
                    locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[1]/a/span");
                    return new Elements(text, locator);
                }

                static Elements storesButton() {
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


            /**
             * Подменю "Заказы"
             */
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


            /**
             * Подменю "Магазины"
             */
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


            /**
             * Подменю "Продукты"
             */
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


            /**
             * Подменю "Импорт"
             */
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

                static Elements imagesButton() {
                    text = "Изображения";
                    locator = By.xpath("//*[@id='sub_nav']/li[9]/a");
                    return new Elements(text, locator);
                }

            }


            /**
             * Подменю "Маркетинг"
             */
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

        /**
         * Раздел Shipments в админке
         */
        interface Shipments {

            static Elements emptyListPlaceholder() {
                return new Elements(null, By.className("no-objects-found"));
            }

            static Elements firstOrderInTable() {
                return new Elements(null, By.xpath("//*[@id='listing_orders']/tbody/tr/td[14]/a"));
            }

            /**
             * Страница деталей заказа в админке
             */
            interface OrderDetailsPage {

                static Elements resumeOrderButton() {
                    return new Elements(null, By.className("icon-resume"));
                }

                static Elements cancelOrderButton() {
                    return new Elements(null, By.className("icon-cancel"));
                }

                static Elements confirmOrderCancellationButton() {
                    return new Elements(null, By.xpath("//*[@id='new_cancellation']/fieldset/div[3]/button"));
                }

                static Elements canceledOrderAttribute() {
                    return new Elements("ЗАКАЗ ОТМЕНЕН",
                            By.xpath("/html/body/div[1]/div[3]/div/div/div/table/tbody/tr[3]/td/b"));
                }

            }

        }

        /**
         * Раздел Users в админке
         */
        interface Users {

            static Elements userlistFirstRow() {
                return new Elements(null, By.xpath("//*[@id='content']/div/table/tbody/tr"));
            }

            static Elements firstUserLogin() {
                return new Elements(null, By.xpath("//*[@id='content']/div/table/tbody/tr[1]/td[1]/div[1]/a"));
            }

            static Elements firstUserDeleteButton() {
                return new Elements(null, By.xpath("//*[@id='content']/div/table/tbody/tr/td[3]/a[2]"));
            }

            static Elements firstUserEditButton() {
                return new Elements(null, By.xpath(
                        "/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[3]/a[1]"));
            }

            static Elements searchField() {
                return new Elements(null, By.id("q_email_cont"));
            }

            static Elements searchButton() {
                return new Elements(null, By.xpath(
                        "/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/aside[1]/div[1]/form[1]/div[4]/button[1]"));
            }

            interface UserPage {

                static Elements adminCheckbox() {
                    return new Elements(null, By.xpath(
                            "/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div[1]/fieldset[1]/div[2]/form[1]/div[2]/div[2]/div[1]/ul[1]/li[1]/input[1]"));
                }

                static Elements saveButton() {
                    return new Elements(null, By.xpath(
                            "/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div[1]/fieldset[1]/div[2]/form[1]/div[3]/div[1]/button[1]"));
                }

            }
        }

    }

}
