package ru.instamart.autotests.application;

import org.openqa.selenium.By;
import ru.instamart.autotests.models.CreditCardData;
import ru.instamart.autotests.models.ElementData;
import ru.instamart.autotests.models.JuridicalData;

public class Elements {

    /** Страница 500 ошибки */
    public interface Page500 {

        static ElementData placeholder() {
            return new ElementData("We're sorry, but something went wrong.", By.xpath("/html/body/div/h1"));
        }
    }

    /** Страница 404 ошибки */
    public interface Page404 {

        static ElementData title() {
            return new ElementData("Страница не найдена", By.xpath("/html/body/div[1]/div/div/div/div[1]/div/div[1]"));
        }

        static ElementData catWisdomButton() {
            return new ElementData("ПОЗНАТЬ КОТОМУДРОСТЬ", By.className("btn--small"));
        }

        static ElementData quote() {
            return new ElementData(By.cssSelector(".error-page-slider > div:nth-child(1) >" +
                    " div:nth-child(1) > div:nth-child(1) > div:nth-child(13) > div:nth-child(1)"));
        }

        static ElementData quote(int quoteId) {
            return new ElementData(By.xpath("/html/body/div[1]/div/div/div/div[2]/div[2]/div/div[1]/div/div/div/div/div["+quoteId+"]/div/blockquote"));
        }

        static ElementData homePageButton() {
            return new ElementData("на главную", By.className(".error-page__link"));
        }

        static ElementData learnPricesButton() {
            return new ElementData("Познать цены *", By.xpath("/html/body/div[1]/div/div/div/div[2]" +
                    "/div[2]/div/div[3]/a"));
        }

        static ElementData showMoreCatWisdom() {
            return new ElementData("ещё котомудрость", By.cssSelector("li.error-page__links-nav__item:nth-child(2" +
                    ") > a:nth-child(1)"));
        }
    }

    /** Лендинг */
    public interface Landing {

        static ElementData header() {
            return new ElementData(By.className("top-line"));
        }

        static ElementData addressField() {
            return new ElementData(By.id("header_ship_address"));
        }

        static ElementData addressSuggest() {
            return new ElementData(By.id("downshift-0-item-0"));
        }

        static ElementData selectStoreButton() {
            return new ElementData("Выбрать магазин", By.cssSelector(".ship-address-selector-form__action"));
        }

        static ElementData loginButton() {
            return new ElementData(By.linkText("Вход"));
        }
    }

    /** Сайт */
    public interface Site {

        static ElementData drawersBackdrop() {
            return new ElementData(By.xpath("//div[@class='drawer__backdrop opened']"),
                    "Тень шторок");
        }

        static ElementData fade() {
            return new ElementData(By.xpath("//div[@class='frame fade-enter-done']"),
                    "Тень поп-апов");
        }

        /** Шапка сайта */
        interface Header {

            static ElementData shipAddressButton() {
                return new ElementData(By.className("ship-address-selector__edit-btn"));
            }

            static ElementData currentShipAddress() {
                return new ElementData(By.className("ship-address-selector__full-address"));
            }

            static ElementData loginButton() {
                return new ElementData(By.className("header-login-btn"));
            }

            static ElementData profileButton() {
                return new ElementData("Профиль", By.className("header-menu-toggle__btn"));
            }

            static ElementData changeStoreButton() {
                return new ElementData(By.className("search-container-selector__control"));
            }

            static ElementData deliveryButton() {
                return new ElementData(null,
                        By.cssSelector("button.navbar-button:nth-child(3) > div:nth-child(1)"));
            }

            static ElementData partnersButton() {
                return new ElementData(null,
                        By.cssSelector("button.navbar-button:nth-child(5) > div:nth-child(1)"));
            }

            static ElementData favoritesButton() {
                return new ElementData(By.cssSelector(".favorites-link"));
            }

            /** Поиск товаров */
            interface Search {

                static ElementData searchField() {
                    return new ElementData(By.cssSelector(".header-search--active > input:nth-child(1)"));
                }

                static ElementData searchButton() {
                    return new ElementData(By.cssSelector(".header-search--active > button:nth-child(2)"));
                }

                static ElementData categorySuggest() {
                    return new ElementData(By.className("header-search-list-category"));
                }

                static ElementData productSuggest() {
                    return new ElementData(By.className("header-search-list-product"));
                }
            }
        }

        /** Модалка "Доставка" */
        interface DeliveryModal {

            static ElementData popup() {
                return new ElementData(By.cssSelector(".modal-lg > div:nth-child(1)"));
            }

            static ElementData title() {
                return new ElementData("Доступные интервалы доставки",
                        By.cssSelector(".delivery__intervals > h3:nth-child(1)"));
            }

            static ElementData closeButton() {
                return new ElementData(By.cssSelector(".close"));
            }
        }

        /** Модалка "Партнеры" */
        interface PartnersModal {

            static ElementData popup() {
                return new ElementData(By.cssSelector(".modal-lg > div:nth-child(1)"));
            }

            static ElementData title() {
                return new ElementData("Наши партнеры", By.cssSelector(".modal-title"));
            }

            static ElementData closeButton() {
                return new ElementData(By.cssSelector(".close"));
            }
        }

        /** Модалка "Оплата" */
        interface PaymentModal {

            static ElementData popup() {
                return new ElementData(By.cssSelector(".modal-lg > div:nth-child(1)"));
            }

            static ElementData title() {
                return new ElementData("Какие существуют способы оплаты?", By.cssSelector(".modal-title"));
            }

            static ElementData closeButton() {
                return new ElementData(By.cssSelector(".close"));
            }
        }

        /** Всплывающее меню "Профиль" */
        interface AccountMenu {

            static ElementData popup() {
                return new ElementData(By.className("account-menu"));
            }

            static ElementData header() {
                return new ElementData(By.className("account-menu__header"));
            }

            static ElementData profileButton() {
                return new ElementData("Профиль", By.linkText("Профиль"));
            }

            static ElementData ordersButton() {
                return new ElementData("Заказы", By.linkText("Заказы"));
            }

            static ElementData termsButton() {
                return new ElementData("Условия пользования", By.linkText("Условия пользования"));
            }

            static ElementData blogButton() {
                return new ElementData("Блог", By.linkText("Блог"));
            }

            static ElementData logoutButton() {
                return new ElementData("Выйти", By.linkText("Выйти"));
            }

            static ElementData deliveryLink() {
                return new ElementData(By.cssSelector("a.account-menu__footer-link:nth-child(1)"));
            }

            static ElementData paymentLink() {
                return new ElementData(By.cssSelector("a.account-menu__footer-link:nth-child(2)"));
            }

            static ElementData faqLink() {
                return new ElementData(By.cssSelector("a.account-menu__footer-link:nth-child(3)"));
            }

            static ElementData contactsLink() {
                return new ElementData(By.cssSelector("a.account-menu__footer-link:nth-child(4)"));
            }
        }

        /** Шторка каталога категорий */
        interface CatalogDrawer {

            static ElementData drawer() {
                return new ElementData(By.xpath("//div[@class='category-menu']"));
            }

            static ElementData openCatalogButton() {
                return new ElementData(By.xpath("//button[contains(@class,'navbar-button--catalog')]"));
            }

            static ElementData closeCatalogButton() {
                return new ElementData(By.xpath("//button[@class='category-menu__close']"));
            }

            static ElementData department(int position) {
                return new ElementData(null, By.xpath("//ul[3]/li[" + (position + 1) + "]/a[1]/div[1]/div[2]"));
            }

            static ElementData taxon(int categoryNumber, int taxonNumber) { return new ElementData(
                    By.xpath("//li[" + (categoryNumber + 1) + "]/div[1]/ul[1]/li[" + (taxonNumber + 1) + "]/a[1]/div[1]/div[2]"));
            }
        }


        /**
         * Страницы категорий/таксонов
         */
        interface CategoryPage {

            static ElementData title() {
                return new ElementData(By.className("taxons-nav__title"));
            }

            static ElementData filters() {
                return new ElementData(By.className("filters_container"));
            }

        }


        /** Модалка авторизации-регистрации */
        interface AuthModal {

            static ElementData popup() {
                return new ElementData(By.className("auth-modal"));
            }

            static ElementData closeButton() {
                return new ElementData(By.xpath("//button[@class='modal-wrapper__close']"));
            }

            static ElementData authorisationTab() {
                return new ElementData(By.xpath("//button[contains(text(),'Вход')]"));
            }

            static ElementData registrationTab() {
                return new ElementData(By.xpath("//button[contains(text(),'Регистрация')]"));
            }

            static ElementData nameField() {
                return new ElementData(By.name("fullname"));
            }

            static ElementData emailField() {
                return new ElementData(By.name("email"));
            }

            static ElementData passwordField() {
                return new ElementData(By.name("password"));
            }

            static ElementData passwordConfirmationField() {
                return new ElementData(By.name("passwordConfirmation"));
            }

            //устарело static ElementData agreementCheckbox() { return new ElementData(By.className("checkbox__check")); }

            static ElementData agreementRulesLink() {
                return new ElementData(By.className("auth-modal__agreement"));
            }

            static ElementData forgotPasswordButton() {
                return new ElementData("Забыли пароль?", By.className("auth-modal__forgotten"));
            }

            static ElementData submitButton() {
                return new ElementData(By.className("auth-modal__button"));
            }

            static ElementData successRecoveryText() {
                return new ElementData("На указанный вами E-mail высланы инструкции по восстановлению пароля.",
                        By.cssSelector(".auth-modal__recovery-text > span:nth-child(1)"));
            }

            static ElementData errorMessage(String errorText) {
                return new ElementData(null,
                        (By.xpath("//*[contains(@class, 'auth-input__error') and contains(text(),'" + errorText + "')]")));
            }

            static ElementData vkontakte() {
                return new ElementData(null,
                        By.xpath("//div[@class='auth-modal__social-icon auth-modal__social-icon--vkontakte']"));
            }

            static ElementData facebook() {
                return new ElementData(null,
                        By.xpath("//div[@class='auth-modal__social-icon auth-modal__social-icon--facebook']"));
            }
        }

        /** Адресные модалки Феникса */
        interface AddressModal {

            static ElementData header() {
                return new ElementData(By.className("address-modal__header"));
            }

            static ElementData closeButton() {
                return new ElementData(By.className("address-modal__header"));
            }

            static ElementData addressField() {
                return new ElementData(By.id("ship_address"));
            }

            static ElementData addressSuggest() {
                return new ElementData(By.className("modal-address-autocomplete__dropdown-item"));
            }

            static ElementData saveButton() {
                return new ElementData("Сохранить", By.className("address-modal__button"));
            }

            static ElementData recentAddressesList() {
                return new ElementData(By.className("address-modal__addresses"));
            }

            static ElementData recentAddress() {
                return new ElementData(By.xpath("//*[@id='react-modal']/div/div/div/div/div/div[1]/div/div/div/div/div/div[2]/div/div[2]"));
            }

            static ElementData authButton() {
                return new ElementData(By.className("address-modal__to-login-link"));
            }

            static ElementData popup() {
                return new ElementData(By.className("address-modal"));
            }

            static ElementData titleSet() {
                return new ElementData ("Введите адрес доставки", By.className("address-modal__header"));
            }

            static ElementData titleChange() {
                return new ElementData ("Редактирование адреса", By.className("address-modal__header"));
            }

            static ElementData titleOutOfZone() {
                return new ElementData ("Адрес не в зоне доставки", By.className("address-modal__header"));
            }

            static ElementData pickNewAddressButton() {
                return new ElementData("Выбрать другой адрес", By.className("address-modal__button--reselect"));
            }
        }

        /** Феникс-модалка выбора магазина при отсутствии текущего ритейлера по выбранному адресу */
        interface StoresModal {

            static ElementData popup() {
                return new ElementData("Выберите магазин", By.className("stores-modal"));
            }

            static ElementData firstStoreAvailable() {
                return new ElementData("Выбор магазина", By.className("store-card"));
            }

            static ElementData pickNewAddressButton() {
                return new ElementData("Ввести другой адрес", By.className("stores-modal__to-login-link"));
            }
        }

        /** Селектор магазинов */
        interface StoreSelector {

            static ElementData drawer() {
                return new ElementData(By.className("store-selector"));
            }

            static ElementData closeButton() {
                return new ElementData(By.className("store-selector__close"));
            }

            static ElementData placeholder() {
                return new ElementData("Нет доступных магазинов по выбранному адресу", By.className("store-selector__empty"));
            }
        }

        /** Каталог товаров */
        interface Catalog {

            static ElementData emptySearchPlaceholder() {
                return new ElementData(By.cssSelector(".resource-not-found__message-block"));
            }

            static ElementData product() {
                return new ElementData(By.xpath("//*[@class='product' or @class='favorite-product' or @class='category-product']"));
            }

            static ElementData plusButton() {
                return new ElementData(By.className("fa-plus"));
            }

            static ElementData addToFavoritesButton() {
                return new ElementData(By.className("favorite-button-default"));
            }

            static ElementData minusButton() { return new ElementData(By.className("fa-minus"));
            }

        }

        /** SEO-каталог */
        interface SeoCatalog {

            static ElementData product() {
                return new ElementData(By.className("category-product"));
            }
        }

        /** Модалка обновления цен */
        interface RefreshPricesModal { //TODO добавить проверку в hitPlus в карточке товара

            static ElementData popup() {
                return new ElementData(By.className("prices-modal"));
            }

            static ElementData refreshPricesButton() {
                return new ElementData(By.className("prices-modal__btn"));
            }
        }

        /** Карточка товара */
        interface ItemCard {

            static ElementData popup() {
                return new ElementData(By.className("product-popup"));
            }

            static ElementData image() {
                return new ElementData(By.className("product-popup__img"));
            }

            static ElementData price() {
                return new ElementData(By.cssSelector(".product-popup__price > div:nth-child(1) > span:nth-child(1)"));
            }

            static ElementData salePrice() {
                return new ElementData(By.cssSelector(".product-popup__sale-price > div:nth-child(1) > span:nth-child(1)"));
            }

            static ElementData saleBadge() {
                return new ElementData(By.className("sale-badge"));
            }

            static ElementData closeButton() {
                return new ElementData(By.className("close"));
            }

            static ElementData plusButton() {
                return new ElementData(By.className("popup-cart-actions__btn--right"));
            }

            static ElementData minusButton() {
                return new ElementData(By.className("popup-cart-actions__btn--left"));
            }

            static ElementData cartCounter() {
                return new ElementData(By.className("//*[contains(@class,'product-popup')]//*[contains(@class,'product__cart-counter')]"));
            }

            static ElementData notInStock() {
                return new ElementData(By.className("product-popup__not-in-stock"));
            }

            static ElementData addToFavoritesButton() {
                return new ElementData(By.className("favorite-button"));
            }

            static ElementData deleteFromFavoritesButton() { //TODO переделать локатор
                return new ElementData(By.xpath("/html[1]/body[1]" +
                    "/div[3]/div[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]" +
                    "/div[2]/div[2]/div[2]/div[1]/div[3]/button[1]")); }
        }

        /** Корзина */
        interface Cart {

            static ElementData drawer() {
                return new ElementData(By.className("new-cart"));
            }

            static ElementData closeButton() {
                return new ElementData(By.className("btn-close-cart"));
            }

            static ElementData placeholder() {
                return new ElementData(By.className("new-cart-empty"));
            }

            static ElementData checkoutButton() {
                return new ElementData(By.className("cart-checkout-link"));
            }

            static ElementData total() {
                return new ElementData(By.cssSelector(".cart-checkout-link__well > div:nth-child(1)"));
            }

            static ElementData openCartButton() {
                return new ElementData(By.className("open-new-cart"));
            }

            static ElementData item() {
                return new ElementData(By.className("cart-line-item"));
            }

            static ElementData removeItemButton() {
                return new ElementData(By.className("cart-line-item__remove"));
            }

            static ElementData upArrowButton() {
                return new ElementData(By.className("fa-caret-up"));
            }

            static ElementData downArrowButton() {
                return new ElementData(By.className("fa-caret-down"));
            }

            static ElementData alertText() {
                return new ElementData(By.className("cart-retailer__alert"));
            }
        }

        /** Любимые товары */
        interface Favorites {

            static ElementData container() {
                return new ElementData(By.className("user-wrapper__container"));
            }

            static ElementData product() {
                return new ElementData(By.className("favorite-product"));
            }

            static ElementData placeholder() {
                return new ElementData(By.className("empty-favorites"));
            }

            static ElementData allItemsFilterButton() {
                return new ElementData("Все товары", By.linkText("Все товары"));
            }

            static ElementData inStockFilterButton() {
                return new ElementData("В наличии", By.linkText("В наличии"));
            }

            static ElementData outOfStockFilterButton() {
                return new ElementData("Нет в наличии", By.linkText("Нет в наличии"));
            }

            static ElementData activeFilter() {
                return new ElementData(By.xpath("//a[@class='favorite-list-filter__link favorite-list-filter__link--active']"));
            }

            static ElementData showMoreButton() {
                return new ElementData("Показать еще", By.xpath("//*[@id='wrap']/div/div/div/" + "div/div[2]/div/div[2]/div[2]/div/button"));
            }

            static ElementData secondPageProduct() {
                return new ElementData(By.xpath("//*[@id='wrap']/div/div/div/div/div[2]" + "/div/div[2]/div[2]/a[31]"));
            }

            static ElementData deleteFromFavoritesButton() {
                return new ElementData(By.className("favorite-button--active"));
            }
        }

        /** Футер */
        static ElementData footer() {
            return new ElementData(By.className("footer"));
        }

        interface Footer {

            static ElementData aboutCompanyButton() {
                return new ElementData("О компании", By.xpath("//*[@id='new-home-footer']/div/div[1]/div[1]/ul/li[1]/a"));
            }

            static ElementData contactsButton() {
                return new ElementData("Контакты", By.xpath("//*[@id='new-home-footer']/div/div[1]/div[1]/ul/li[2]/a"));
            }

            static ElementData deliveryButton() {
                return new ElementData("Доставка", By.xpath("//*[@id='new-home-footer']/div/div[1]/div[1]/ul/li[3]/button"));
            }

            static ElementData paymentButton() {
                return new ElementData("Оплата", By.xpath("//*[@id='new-home-footer']/div/div[1]/div[1]/ul/li[4]/button"));
            }

            static ElementData partnersButton() {
                return new ElementData("Партнеры", By.xpath("//*[@id='new-home-footer']/div/div[1]/div[1]/ul/li[5]/button"));
            }

            static ElementData faqButton() {
                return new ElementData("FAQ", By.xpath("//*[@id='new-home-footer']/div/div[1]/div[1]/ul/li[6]/a"));
            }

            static ElementData feedbackFormButton() {
                return new ElementData("Форма обратной связи", By.xpath("//*[@id='new-home-footer']/div/div[1]/div[2]/ul/li[3]/a"));
            }

            static ElementData returnPolicyButton() {
                return new ElementData("Политика возврата", By.xpath("//*[@id='new-home-footer']/div/div[2]/div[2]/a[1]"));
            }

            static ElementData publicOfferButton() {
                return new ElementData("Публичная оферта", By.xpath("//*[@id='new-home-footer']/div/div[2]/div[2]/a[2]"));
            }
        }

        /** Чекаут */
        interface Checkout {

            static ElementData header() {
                return new ElementData(By.className("chekout-header"), "шапка чекаута");
            }

            static ElementData nextButton(int step) {
                return new ElementData("Продолжить", By.xpath("(//button[@type='button'])[" + step + "]"));
            }

            static ElementData changeStepButton(int step) {
                return new ElementData("Изменить", By.cssSelector("div.checkout-panel:nth-child(" + step + ") > div:nth-child(1) > div:nth-child(3) > a:nth-child(1)"));
            }

            // Костыльный элемент для 5 шага
            static ElementData changeStep5Button() { //TODO переделать локаторы так, чтобы избавиться от отдельной кнопки для 5 шага
                return new ElementData("Изменить",
                        By.xpath("/html/body/div[3]/div/form/div/div/div[1]/div[5]/div[2]/div/div/div/div[1]/div[3]"));
            }

            static ElementData sendOrderButton() {
                return new ElementData(By.className("checkout-finalize__button"));
            }

            static ElementData replacementPolicy(int option) {
                return new ElementData(By.cssSelector("div.replacement-policy:nth-child(" + option + ")"));
            }

            static ElementData paymentTypeSelector(int option) {
                return new ElementData(By.cssSelector("div.payment-method:nth-child(" + option + ") > div:nth-child(1)"));
            }

            static ElementData addPromocodeButton() {
                return new ElementData("Применить промокод", By.linkText("Применить промокод"));
            }

            static ElementData appliedPromocodeAttribute() {
                return new ElementData("Промокод:", By.className("promo-codes__label"));
            }

            static ElementData clearPromocodeButton() {
                return new ElementData("Удалить", By.linkText("Удалить"));
            }

            /** Итерфейс выбора слота доставки */
            static ElementData deliveryWindowSelector() {
                return new ElementData(By.className("windows-selector-item"));
            }

            /** Селектор дней доставки */
            static ElementData deliveryDaySelector(int day) {
                return new ElementData(By.cssSelector("div.panel-tab:nth-child(" + day + ")"));
            }

            /** Интервал слота доставки */
            static ElementData slotTime(int day, int position) {
                return new ElementData( By.xpath("//*[@id='deliveryDay-" + (day-1) + "']/div[" + position + "]/span"));
            }

            /** Стоимость слота доставки */
            static ElementData slotPrice(int day, int position) {
                return new ElementData(By.xpath("//*[@id='deliveryDay-" + (day-1) + "']/div[" + position + "]/div/div/div/div"));
            }

            /** Кнопка выбора слота доставки */
            static ElementData chooseSlotButton(int day, int position) {
                return new ElementData(By.xpath("//*[@id='deliveryDay-" + (day-1) + "']/div[" + position + "]/div/span"));
            }

            /** Стоимость доставки заказа */
            static ElementData deliveryPrice() {
                return new ElementData(By.cssSelector("div.checkout-summary__subtotal:nth-child(4) > span:nth-child(2) > div:nth-child(1)"));
            }

            /** Элементы номера телефона */
            static ElementData phoneIcon() {
                return new ElementData(By.className("fa-mobile"));
            }

            static ElementData phoneNumberField() {
                return new ElementData(By.id("phone-input"));
            }

            static ElementData editPhoneButton() {
                return new ElementData(By.xpath("//div[2]/div[1]/div/div/a"));
            }

            static ElementData deletePhoneButton() {
                return new ElementData(By.xpath("//div[2]/div/div[1]/button"));
            }

            static ElementData phoneNumber() {
                return new ElementData(By.className("checkout-selector-item__text"));
            }

            /** Элементы оплаты банковской картой */
            static ElementData addPaymentCardButton() {
                return new ElementData(By.xpath("//div[contains(text(),'+ Добавить новую карту')]"));
            }

            static ElementData changePaymentCardButton() {
                return new ElementData(By.xpath("//div[2]/div[1]/div[1]/div/div/a"));
            }

            static ElementData paymentCardsList() {
                return new ElementData(By.xpath("//div[4]/div[2]/div/div/div[2]/div[2]/div[1]/div/div/div/div/span[2]/span"));
            }

            static ElementData paymentCardTitle(int number) {
                return new ElementData(By.xpath("//div[4]/div[2]/div/div/div[2]/div[2]/div[1]/div[" + number + "]/div/div/div/span[2]/span"));
            }

            static ElementData paymentCardTitle(CreditCardData creditCardData) {
                String number = creditCardData.getCardNumber();
                return new ElementData(By.xpath("//span[contains(text(),'•••• " + number.substring(number.length()-4) + "')]"));
            }

            /** Модалка карты оплаты */
            interface PaymentCardModal {

                static ElementData cardNumber() {
                    return new ElementData(By.xpath("//form/div/div[1]/span"));
                }

                static ElementData cardNumberField() {
                    return new ElementData(By.xpath("//input[@data-cp='cardNumber']"));
                }

                static ElementData monthField() {
                    return new ElementData(By.xpath("//input[@data-cp='expDateMonth']"));
                }

                static ElementData yearField() {
                    return new ElementData(By.xpath("//input[@data-cp='expDateYear']"));
                }

                static ElementData cvvField() {
                    return new ElementData(By.xpath("//input[@data-cp='cvv']"));
                }

                static ElementData nameField() {
                    return new ElementData(By.xpath("//input[@data-cp='name']"));
                }

                static ElementData deleteButton() {
                    return new ElementData(By.xpath("//button[contains(text(),'Удалить')]"));
                }

                static ElementData confirmButton() {
                    return new ElementData(By.xpath("//button[contains(text(),'Сохранить')]"));
                }
            }

            /** Страница подтверждения платежа по карте */
            interface Cloudpayments {

                static ElementData answerField() {
                    return new ElementData(By.id("password"));
                }

                static ElementData confirmButton() {
                    return new ElementData(By.xpath("//button[contains(text(),'Подтвердить')]"));
                }
            }

            /** Элементы оплаты банковским переводом */
            static ElementData addJuridicalButton() {
                return new ElementData(By.xpath("//div[contains(text(),'+ Добавить реквизиты')]"));
            }

            static ElementData changeJuridicalButton() {
                return new ElementData(By.xpath("//div[2]/div[1]/div[1]/div/div/a"));
            }

            static ElementData juridicalTitle(int number) {
                return new ElementData(By.xpath("//div[" + number + "]/div/div/div/span[2]/span"));
            }

            static ElementData juridicalTitle(JuridicalData juridicalData) {
                return new ElementData(By.xpath(
                        "//span[contains(text(),'" + juridicalData.getJuridicalName() + ", ИНН: " + juridicalData.getInn() + "')]"));
            }

            /** Модалка юр.лица */
            interface JuridicalModal {

                static ElementData nameField() {
                    return new ElementData(By.name("name"));
                }

                static ElementData addressField() {
                    return new ElementData(By.name("address"));
                }

                static ElementData innField() {
                    return new ElementData(By.name("inn"));
                }

                static ElementData kppField() {
                    return new ElementData(By.name("kpp"));
                }

                static ElementData operatingAccountField() {
                    return new ElementData(By.name("operatingAccount"));
                }

                static ElementData bikField() {
                    return new ElementData(By.name("bik"));
                }

                static ElementData bankField() {
                    return new ElementData(By.name("bank"));
                }

                static ElementData correspondentAccountField() {
                    return new ElementData(By.name("correspondentAccount"));
                }

                static ElementData deleteButton() {
                    return new ElementData(By.xpath("//button[contains(text(),'Удалить')]"));
                }

                static ElementData confirmButton() {
                    return new ElementData(By.xpath("//button[contains(text(),'Сохранить')]"));
                }
            }

            /** Модалка промокода */
            interface PromocodeModal {

                // TODO static ElementData popup()

                static ElementData title() {
                    return new ElementData("Введите промокод", By.className("modal-form__title"));
                }

                static ElementData field() {
                    return new ElementData(By.id("couponCode"));
                }

                static ElementData applyButton() {
                    return new ElementData("Добавить код",By.xpath("//*[@id='react-modal']/div/div[2]/div/div/div[2]/form/div[2]/div/div[2]/button"));
                }

                static ElementData cancelButton() {
                    return new ElementData("Отменить", By.xpath("//*[@id='react-modal']/div/div[2]/div/div/div[2]/form/div[2]/div/div[1]/button"));
                }

                static ElementData closeButton() {
                    return new ElementData(By.className("rc-modal__close"));
                }
            }

            /** Программы лояльности */
            static ElementData loyaltyPrograms() {
                return new ElementData(By.xpath("/html/body/div[3]/div/form/div/aside/div/div[3]"));
            }
        }

        /** Профиль пользователя */
        interface UserProfile {

            static ElementData menu() {
                return new ElementData(null,
                        By.className("user-menu"));
            }

            /** Страница аккаунта */
            interface AccountPage {

                static ElementData email() {
                    return new ElementData(By.xpath("//*[@id='wrap']/div/div/div/div/div[1]/div/div[2]/div/div/div[2]/div[2]/div[2]"));
                }

                static ElementData changePasswordButton() {
                    return new ElementData(By.xpath("//*[@id='wrap']/div/div/div/div/div[2]/div/div[2]/div/div/div[2]/div[1]/div[3]/button"));
                }

                static ElementData changeEmailButton() {
                    return new ElementData(By.xpath("//*[@id='wrap']/div/div/div/div/div[2]/div/div[2]/div/div/div[2]/div[2]/div[3]/button"));
                }

                static ElementData changeNameButton() {
                    return new ElementData( By.xpath("//*[@id='wrap']/div/div/div/div/div[2]/div/div[2]/div/div/div[2]/div[3]/div[3]/button"));
                }
            }

            /** Страница истории заказов */
            interface OrdersPage {

                static ElementData placeholder() {
                    return new ElementData("У вас нет завершенных заказов", By.className("no-content__text"));
                }

                static ElementData lastOrderActionButton() {
                    return new ElementData(By.xpath("//*[@id='wrap']/div/div/div/div/div[1]/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/button"));
                }

                static ElementData lastOrderActionButton(int position) {
                    return new ElementData(By.xpath("//*[@id='wrap']/div/div/div/div/div[1]/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/button[" + position + "]"));
                }

                static ElementData lastOrderDetailsButton() {
                    return new ElementData(By.xpath("//*[@id='wrap']/div[1]/div/div/div/div[1]/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/a"));
                }
            }

            /** Страница деталей заказа */
            interface OrderDetailsPage {

                static ElementData activeOrderAttribute() {
                    return new ElementData(By.className("user-order"),
                            "признак активного заказа");
                }

                static ElementData canceledOrderAttribute() {
                    return new ElementData(By.className("user-order-shipment-header__container--canceled"),
                            "признак отмененного заказа");
                }

                static ElementData documentation(int position) {
                    return new ElementData(By.xpath("//*[@id='wrap']/div/div/div/div/div[2]/div/div/div[2]/div/div[2]/div[2]/div/div/div[3]/a[" + position + "]"),
                            "документы к заказу");
                }

                static ElementData deliveryPrice() {
                    return new ElementData(By.xpath("//*[@id='wrap']/div/div/div/div/div[2]/div/div/div[3]/div/div/div/div[1]/div[2]/div"),
                            "стоимость доставки");
                }

                static ElementData shipmentNumber() {
                    return new ElementData(By.xpath("//div[2]/div/div[1]/div/div/div/div[1]/strong[1]"),
                            "номер доставки");
                }

                static ElementData shipmentPayment() {
                    return new ElementData(By.cssSelector("div.user-order-shipment-summary__item:nth-child(2)"),
                            "способ оплаты");
                }
            }
        }

        /** Чат Jivosite */
        interface Jivosite {

            static ElementData widget() {
                return new ElementData(By.id("jvlabelWrap"));
            }

            static ElementData openButton() {
                return new ElementData(By.xpath(
                        "//*[@id=\"jvlabelWrap\"]/jdiv/jdiv[1]"));
            }

            static ElementData closeButton() {
                return new ElementData(By.xpath(
                        "//*[@id=\"jivo_close_button\"]/jdiv"));
            }

            static ElementData chatArea() {
                return  new ElementData(By.className("container_CV"));
            }

            static ElementData messageField() {
                return  new ElementData(By.xpath(
                        "//*[@id=\"jcont_content_wrapper\"]/jdiv[2]/jdiv/jdiv[4]/jdiv/jdiv/jdiv[1]/textarea"));
            }

            static ElementData sendMessageButton() {
                return  new ElementData(By.xpath(
                        "//*[@id=\"jcont_content_wrapper\"]/jdiv[2]/jdiv/jdiv[4]/jdiv/jdiv/jdiv[2]/jdiv"));
            }
            static ElementData sentMessage() {
                return new ElementData(By.xpath(
                        "//*[@id=\"scrollbar-container\"]/jdiv[1]/jdiv/jdiv[5]/jdiv[1]/jdiv/jdiv/jdiv"));
            }
        }
    }

    /** Админка */
    public interface Admin {

        static ElementData container() {
            return new ElementData(By.className("admin"));
        }

        /** Шапка админки */
        interface Header {

            static ElementData logoutButton() {
                return new ElementData(By.xpath("//*[@id='login-nav']/li[3]/a"));
            }

            static ElementData profileButton() {
                return new ElementData("Учетная запись", By.xpath("//*[@id='login-nav']/li[2]/a"));
            }

            static ElementData backToListButton() {
                return new ElementData("Назад к списку", By.xpath("//*[@id='login-nav']/li[4]/a"));
            }

            /** Меню админки */
            interface Menu {

                static ElementData ordersButton() {
                    return new ElementData(By.xpath("//*[contains(@href,'/admin/shipments')]//*[contains(text(),'Заказы')]"));
                }

                static ElementData storesButton() {
                    return new ElementData("Магазины", By.xpath("//*[@id='admin-menu']/div/div/ul/li[2]/a/span"));
                }

                static ElementData productsButton() {
                    return new ElementData("Продукты", By.xpath("//*[@id='admin-menu']/div/div/ul/li[3]/a/span"));
                }

                static ElementData importButton() {
                    return new ElementData("Импорт", By.xpath("//*[@id='admin-menu']/div/div/ul/li[4]/a/span"));
                }

                static ElementData reportsButton() {
                    return new ElementData("Отчеты", By.xpath("//*[@id='admin-menu']/div/div/ul/li[5]/a/span"));
                }

                static ElementData settingsButton() {
                    return new ElementData("Настройки", By.xpath("//*[@id='admin-menu']/div/div/ul/li[6]/a/span"));
                }

                static ElementData marketingButton() {
                    return new ElementData("Маркетинг", By.xpath("//*[@id='admin-menu']/div/div/ul/li[7]/a/span"));
                }

                static ElementData usersButton() {
                    return new ElementData("Пользователи", By.xpath("//*[@id='admin-menu']/div/div/ul/li[8]/a/span"));
                }

                static ElementData pagesButton() {
                    return new ElementData("Страницы", By.xpath("//*[@id='admin-menu']/div/div/ul/li[9]/a/span"));
                }
            }

            /** Подменю "Заказы" */
            interface SubmenuOrders {

                static ElementData multiOrderButton() {
                    return new ElementData("Мульти заказ", By.xpath("//*[@id='sub_nav']/li[1]/a"));

                }

                static ElementData exportButton() {
                    return new ElementData("Export", By.xpath("//*[@id='sub_nav']/li[2]/a"));
                }

                static ElementData veerouteButton() {
                    return new ElementData("Veeroute", By.xpath("//*[@id='sub_nav']/li[3]/a"));
                }
            }

            /** Подменю "Магазины" */
            interface SubmenuStores {

                static ElementData retailersButton() {
                    return new ElementData("Ритейлеры", By.xpath("//*[@id='sub_nav']/li[1]/a"));
                }

                static ElementData zonesButton() {
                    return new ElementData("Зоны", By.xpath("//*[@id='sub_nav']/li[2]/a"));
                }
            }

            /** Подменю "Продукты" */
            interface SubmenuProducts {

                static ElementData subProductsButton() {
                    return new ElementData("Продукты", By.xpath("//*[@id='sub_nav']/li[1]/a"));
                }

                static ElementData productsStatsButton() {
                    return new ElementData("Статистика", By.xpath("//*[@id='sub_nav']/li[2]/a"));
                }

                static ElementData optionTypesButton() {
                    return new ElementData("Option Types", By.xpath("//*[@id='sub_nav']/li[3]/a"));
                }

                static ElementData propertiesButton() {
                    return new ElementData("Properties", By.xpath("//*[@id='sub_nav']/li[4]/a"));
                }

                static ElementData prototypesButton() {
                    return new ElementData("Prototypes", By.xpath("//*[@id='sub_nav']/li[5]/a"));
                }

                static ElementData brandsButtton() {
                    return new ElementData("Бренды", By.xpath("//*[@id='sub_nav']/li[6]/a"));
                }

                static ElementData producersButton() {
                    return new ElementData("Производители", By.xpath("//*[@id='sub_nav']/li[7]/a"));
                }

                static ElementData producersCountriesButton() {
                    return new ElementData("Страны производства", By.xpath("//*[@id='sub_nav']/li[8]/a"));
                }
            }

            /** Подменю "Импорт" */
            interface SubmenuImport {

                static ElementData queueOfTasksButton() {
                    return new ElementData("Очередь задач", By.xpath("//*[@id='sub_nav']/li[1]/a"));
                }

                static ElementData importStatsButton() {
                    return new ElementData("Статистика", By.xpath("//*[@id='sub_nav']/li[2]/a"));
                }

                static ElementData archiveButton() {
                    return new ElementData("Архив", By.xpath("//*[@id='sub_nav']/li[3]/a"));
                }

                static ElementData blackListButton() {
                    return new ElementData("Черный список Gm", By.xpath("//*[@id='sub_nav']/li[4]/a"));
                }

                static ElementData categoryButton() {
                    return new ElementData("Категории", By.xpath("//*[@id='sub_nav']/li[5]/a"));
                }

                static ElementData filtersButton() {
                    return new ElementData("Фильтры", By.xpath("//*[@id='sub_nav']/li[6]/a"));
                }

                static ElementData importProductsButton() {
                    return new ElementData("Продукты", By.xpath("//*[@id='sub_nav']/li[7]/a"));
                }

                static ElementData priceButton() {
                    return new ElementData("Цены", By.xpath("//*[@id='sub_nav']/li[8]/a"));
                }

                static ElementData imagesButton() {
                    return new ElementData("Изображения", By.xpath("//*[@id='sub_nav']/li[9]/a"));
                }
            }

            /** Подменю "Маркетинг" */
            interface SubmenuMarketing {

                static ElementData promoCardsButton() {
                    return new ElementData("Промо карточки", By.xpath("//*[@id='sub_nav']/li[1]/a/span"));
                }

                static ElementData promoActionButton() {
                    return new ElementData("Промо акции", By.xpath("//*[@id='sub_nav']/li[2]/a/span"));
                }

                static ElementData welcomeBannersButton() {
                    return new ElementData("Welcome баннеры", By.xpath("//*[@id='sub_nav']/li[3]/a/span"));
                }

                static ElementData advertisementButton() {
                    return new ElementData("Реклама", By.xpath("//*[@id='sub_nav']/li[4]/a/span"));
                }

                static ElementData yandexMarketButton() {
                    return new ElementData("Яндекс.Маркет", By.xpath("//*[@id='sub_nav']/li[5]/a/span"));
                }

                static ElementData cartsButton() {
                    return new ElementData("Корзины", By.xpath("//*[@id='sub_nav']/li[6]/a/span"));
                }

                static ElementData bonusCardsButton() {
                    return new ElementData("Бонусные карты", By.xpath("//*[@id='sub_nav']/li[7]/a/span"));
                }


                static ElementData retailersProgramsButton() {
                    return new ElementData("Программы ритейлеров", By.xpath("//*[@id='sub_nav']/li[8]/a/span"));
                }

                static ElementData newCitiesButton() {
                    return new ElementData("Новые Города", By.xpath("//*[@id='sub_nav']/li[9]/a/span"));
                }
            }
        }

        /** Раздел Shipments в админке */
        interface Shipments {

            static ElementData emptyListPlaceholder() {
                return new ElementData(By.className("no-objects-found"));
            }

            static ElementData firstOrderInTable() {
                return new ElementData(By.xpath("//*[@id='listing_orders']/tbody/tr/td[14]/a"));
            }

            static ElementData firstShipmentNumberInTable() {
                return new ElementData(By.xpath("//*[@id='listing_orders']/tbody/tr/td[1]/span[1]/a"));
            }

            static ElementData firstOrderNumberInTable() {
                return new ElementData(By.xpath("//*[@id='listing_orders']/tbody/tr/td[1]/span[2]/a"));
            }

            static ElementData searchNumberField() {
                return new ElementData(By.id("search_number"));
            }

            static ElementData searchButton() {
                return new ElementData(By.xpath("/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div[1]/fieldset[1]/div[1]/form[1]" +
                                "/div[7]/div[1]/button[1]"));
            }

            static ElementData b2bCheckbox() {
                return new ElementData(By.id("search_only_b2b"));
            }

            interface Order {

                /** Страница деталей заказа в админке */
                interface Details {

                    static ElementData resumeOrderButton() {
                        return new ElementData(By.className("icon-resume"));
                    }

                    static ElementData cancelOrderButton() {
                        return new ElementData(By.className("icon-cancel"));
                    }

                    static ElementData confirmOrderCancellationButton() {
                        return new ElementData(By.xpath("//*[@id='new_cancellation']/fieldset/div[3]/button"));
                    }

                    static ElementData canceledOrderAttribute() {
                        return new ElementData("ЗАКАЗ ОТМЕНЕН", By.xpath("//b[contains(text(),'ЗАКАЗ ОТМЕНЕН')]"));
                    }

                    static ElementData replacementPolicy() {
                        return new ElementData(By.xpath("//*[@id='order_tab_summary']/dl/dl[1]/div[2]"));
                    }
                }

                /** Страница деталей оплаты заказа в админке */
                interface Payments {

                    static ElementData paymentType() {
                        return new ElementData(By.xpath("//div[@id='wrapper']//td[5]/a"));
                    }
                }

                /** Страница реквизитов заказа в админке */
                interface Requisites {

                    static ElementData phoneField() {
                        return new ElementData(By.id("order_ship_address_attributes_phone"));
                    }

                    static ElementData innField() {
                        return new ElementData(By.id("order_company_document_attributes_inn"));
                    }
                }
            }
        }

        /** Раздел Users в админке */
        interface Users {

            static ElementData userlistFirstRow() {
                return new ElementData(By.xpath("//*[@id='content']/div/table/tbody/tr"));
            }

            static ElementData firstUserLogin() {
                return new ElementData(By.xpath("//*[@id='content']/div/table/tbody/tr[1]/td[1]/div[1]/a"));
            }

            static ElementData firstUserDeleteButton() {
                return new ElementData(By.xpath("//*[@id='content']/div/table/tbody/tr/td[3]/a[2]"));
            }

            static ElementData firstUserEditButton() {
                return new ElementData(By.xpath(
                        "/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[3]/a[1]"));
            }

            static ElementData firstUserB2BLabel() {
                return new ElementData("B2B", By.className("b2b_client"));
            }

            static ElementData searchField() {
                return new ElementData(By.id("q_email_cont"));
            }

            static ElementData searchButton() {
                return new ElementData(By.xpath("//*[@id=\"spree/user_search\"]/div[5]/button"));
            }

            static ElementData b2bCheckbox() {
                return new ElementData(By.id("q_b2b_true"));
            }

            static ElementData tenantCheckbox() {
                return new ElementData(By.id("q_tenant_eq"));
            }

            /** Страница пользователя в админке */
            interface UserPage {

                static ElementData adminCheckbox() {
                    return new ElementData(By.xpath(
                            "/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div[1]/fieldset[1]/div[2]/form[1]/div[2]" +
                                    "/div[2]/div[1]/ul[1]/li[1]/input[1]"));
                }

                static ElementData saveButton() {
                    return new ElementData(By.xpath(
                            "/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div[1]/fieldset[1]/div[2]/form[1]/div[3]" +
                                    "/div[1]/button[1]"));
                }

                static ElementData passwordField() {
                    return new ElementData(By.id("user_password"));
                }

                static ElementData passwordConfirmationField() {
                    return new ElementData(By.id("user_password_confirmation"));
                }

                static ElementData b2bCheckbox() {
                    return new ElementData(By.id("user_b2b"));
                }
            }
        }
    }

    /** Социальные сети */
    public interface Social {

        /** ВКонтакте */
        interface Vkontakte {

            static ElementData emailField() {
                return new ElementData(By.name("email"));
            }

            static ElementData passwordField() {
                return new ElementData(By.name("pass"));
            }

            static ElementData submitButton() {
                return new ElementData(By.id("install_allow"));
            }

            static ElementData allowAccessButton() {
                return new ElementData(null,
                    By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[1]/button[1]"));
            }

            static ElementData denyAccessButton() {
                return new ElementData(null,
                    By.xpath("//div[@id='app3856350']//div[@class='apps_settings_action_remove']"));
            }

            static ElementData profileButton() {
                return new ElementData(By.xpath("//a[@id='top_profile_link']"));
            }

            static ElementData logoutButton() {
                return new ElementData(By.xpath("//a[@id='top_logout_link']"));
            }

            interface index {

                static ElementData emailField() {
                    return new ElementData(By.id("index_email"));
                }

                static ElementData passwordField() {
                    return new ElementData(By.id("index_pass"));
                }

                static ElementData loginButton() {
                    return new ElementData(By.id("index_login_button"));
                }
            }
        }

        /** Facebook */
        interface Facebook {

            static ElementData emailField() {
                return new ElementData(By.name("email"));
            }

            static ElementData passwordField() {
                return new ElementData(By.name("pass"));
            }

            static ElementData submitButton() {
                return new ElementData(By.id("loginbutton"));
            }

            static ElementData allowAccessButton() {
                return new ElementData(By.name("__CONFIRM__"));
            }

            static ElementData instamartButton() {
                return new ElementData(By.linkText("Instamart"));
            }

            static ElementData denyAccessButton() { //TODO переделать локатор
                return new ElementData(
                    By.xpath("/html[1]/body[1]/div[1]/div[1]/div[4]/div[1]/div[1]/div[1]/div[1]/div[5]/div[1]/div[1]" +
                            "/div[4]/div[3]/a[1]")); }

            static ElementData confirmDenyAccessButton() {
                return new ElementData(By.name("confirmed"));
            }

            static ElementData logoutButton() {
                return new ElementData(By.id("mbasic_logout_button"));
            }

            static ElementData loginButton() {
                return new ElementData(By.name("login"));
            }
        }
    }

    /** Tenant */
    public interface Tenant { // TODO интегрировать с обычным футером, переключать в зависимости от тенанта в environment
        static ElementData tenantFooter() { return new ElementData(By.xpath("//*[@id=\"wrap\"]/div[5]/footer"));}
    }

    /** Блоки RetailRocket */
    public interface RetailRocket { //TODO перенести в Site

        static ElementData widget(String widgetId) {
            return new ElementData(By.xpath("//*[contains(@class,'" + widgetId + "')]"));
        }

        static ElementData title(String widgetId) {
            return new ElementData(By.xpath("//*[contains(@class,'" + widgetId + "')]//*[contains(@class,'widgettitle')]"));
        }

        static ElementData item(String widgetId) {
            return new ElementData(By.xpath("//*[contains(@class,'" + widgetId + "')]//*[contains(@class,'swiper-slide')]"));
        }

        static ElementData addButton(String widgetId) {
            return new ElementData(By.xpath("//*[contains(@class,'" + widgetId + "')]//*[contains(@class,'rr-product-cart-btn-add')]"));
        }
    }
}
