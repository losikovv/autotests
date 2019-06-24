package ru.instamart.autotests.application;

import org.openqa.selenium.By;
import ru.instamart.autotests.models.CreditCardData;
import ru.instamart.autotests.models.ElementData;
import ru.instamart.autotests.models.JuridicalData;

import static ru.instamart.autotests.application.Config.*;

public class Elements {

    /** Страница 500 ошибки */
    public interface Page500 {

        static ElementData placeholder() {
            return new ElementData(By.xpath("//h1[text()='We're sorry, but something went wrong.']"),
                    "текст ошибки 500");
        }
    }

    /** Страница 502 ошибки CloudFlare */
    public interface Page502 {

        static ElementData title() {
            return new ElementData(By.xpath("//span[@class='cf-error-code' and text()='502']"),
                    "текст ошибки 502");
        }
    }

    /** Страница 404 ошибки */
    public interface Page404 {

        static ElementData title() {
            return new ElementData(By.xpath("//div[text()='Страница не найдена']"),
                    "текст ошибки на странице 404");
        }

        static ElementData catWisdomButton() {
            return new ElementData(By.xpath("//a[text()='ПОЗНАТЬ КОТОМУДРОСТЬ']"),
                    "кнопка \"Познать котомудрость\" на странице 404");
        }

        //todo сделать нормальный локатор
        static ElementData quote() {
            return new ElementData(By.cssSelector(".error-page-slider > div:nth-child(1) >" +
                    " div:nth-child(1) > div:nth-child(1) > div:nth-child(13) > div:nth-child(1)"));
        }

        //todo сделать нормальный локатор
        static ElementData quote(int quoteId) {
            return new ElementData(By.xpath("/html/body/div[1]/div/div/div/div[2]/div[2]/div/div[1]/div/div/div/div/div["+quoteId+"]/div/blockquote"));
        }

        static ElementData toHomeButton() {
            return new ElementData(By.xpath("//a[@class='error-page__link' and text()='на главную']"),
                    "кнопка \"На главную\" на странице 404");
        }

        static ElementData learnPricesButton() {
            return new ElementData(By.xpath("//a[text()='Познать цены *']"),
                    "кнопка \"Познать цены\" на странице 404");
        }

        static ElementData showMoreWisdomButton() {
            return new ElementData(By.xpath("//a[@class='error-page__link' and text()='ещё котомудрость']"),
                    "кнопка \"Еще котомудрость\" на странице 404");
        }
    }

    /** Лендинг */
    public interface Landing {

        static ElementData logo() {
            return new ElementData(By.xpath("//header//a[@class='logo']"),
                    "логотип в шапке лендинга");
        }

        static ElementData loginButton() {
            return new ElementData(By.xpath("//header//a[text()='Вход']"),
                    "кнопка \"Вход\" в шапке лендинга");
        }

        static ElementData title() {
            return new ElementData(
                    By.xpath("//h1[@class='window__title' and contains(text(),'Доставка продуктов на дом и в офис')]"),
                    "главный тайтл лендинга");
        }

        static ElementData addressField() {
            return new ElementData(By.xpath("//input[@id='header_ship_address']"),
                    "поле ввода адреса доставки на лендинге");
        }

        static ElementData addressSuggest() {
            return new ElementData(By.id("downshift-0-item-0"));
        }

        static ElementData selectStoreButton() {
            return new ElementData(By.xpath("//button[text()='Выбрать магазин']"),
                    "кнопка \"Выбрать магазин\" на лендинге");
        }

        static ElementData goToCatalogButton() {
            return new ElementData(By.xpath("//a[text()='Перейти в каталог']"),
                    "кнопка \"Перейти в каталог\" на лендинге");
        }

        static ElementData phoneField() {
            return new ElementData(By.xpath("//input[@class='mobile-promo__input']"),
                    "поле ввода телефона на лендинге");
        }

        static ElementData getDownloadLinkButton() {
            return new ElementData(By.xpath("//button[text()='Получить ссылку на скачивание']"),
                    "кнопка \"Получить ссылку на скачивание\" на лендинге");
        }

        static ElementData successDownloadLinkPlaceholder() {
            return new ElementData(By.xpath("//div[@class='mobile-promo__success' and text()='Вам отправлено СМС со ссылкой на наше приложение']"),
                    "текст успешного запроса ссылки на скачку прилоджения на лендинге");
        }
    }

    /** Шапка сайта */
    public interface Header {

            static ElementData container() {
                return new ElementData(
                        By.xpath("//header"),
                            "контейнер шапки сайта");
            }

            static ElementData shipAddressPlaceholder() {
                return new ElementData(
                        By.xpath("//header//div[text()='Укажите ваш адрес для отображения доступных магазинов']"),
                            "плейсхолдер пустого адреса доставки в шапке сайта");
            }

            static ElementData shipAddressButton() {
                return new ElementData(
                        By.xpath("//header//button[@class='ship-address-selector__edit-btn']"),
                            "кнопка выбора/изменения адреса доставки в шапке сайта");
            }

            static ElementData currentShipAddress() {
                return new ElementData(
                        By.xpath("//header//span[@class='ship-address-selector__full-address']"),
                            "текущий адрес доставки в шапке сайта");
            }

            static ElementData hotlinePhoneNumber() {
                return new ElementData(
                        By.xpath("//header//a[@href='" + companyHotlinePhoneLink + "']//span[text()='" + companyHotlinePhoneNumber + "']"),
                            "ссылка-телефон горячей линии в шапке сайта");
            }

            static ElementData hotlineWorkhoursText() {
                return new ElementData(
                        By.xpath("//header//span[text()='" + companyHotlineWorkhoursShort + "']"),
                            "часы работы горячей линии в шапке сайта");
            }

            static ElementData logo() {
                return new ElementData(
                        By.xpath("//header//div[@class='header-logo']"),
                            "логотип компании в шапке сайта");
            }

            static ElementData aboutCompanyButton() {
                return new ElementData(
                        By.xpath("//header//a[@class='navbar-button']//span[text()='О компании']"),
                            "кнопка \"О компании\" в шапке сайта");
            }

            static ElementData contactsButton() {
                return new ElementData(
                        By.xpath("//header//a[@class='navbar-button']//span[text()='Контакты']"),
                            "кнопка \"Контакты\" в шапке сайта");
            }

            static ElementData helpButton() {
                return new ElementData(
                        By.xpath("//header//a[@class='navbar-button']//span[text()='Помощь']"),
                            "кнопка \"Помощь\" в шапке сайта");
            }

            static ElementData deliveryButton() {
                return new ElementData(
                        By.xpath("//header//a[@class='navbar-button']//span[text()='Доставка и оплата']"),
                            "кнопка \"Доставка и оплата\" в шапке сайта");
            }
            static ElementData corporativeCustomersButton() {
                return new ElementData(
                        By.xpath("//header//a[@class='navbar-button']//span[text()='Корпоративным клиентам']"),
                            "кнопка \"Корпоративным клиентам\" в шапке сайта");
            }

            static ElementData mnogoruButton() {
                return new ElementData(
                        By.xpath("//header//a[@class='navbar-button']//div[@class='navbar-button__mnogoru']"),
                        "кнопка \"МногоРу\" в шапке сайта");
            }

            static ElementData catalogButton() {
                switch (Config.environment.getTenant()) {
                    case "metro" :
                        return new ElementData(By.xpath("//header//button[@class='header__button-catalog']"),
                                "кнопка открытия каталога категорий в шапке сайта Метро");
                    default:
                        return new ElementData(By.xpath("//header//button[contains(@class,'navbar-button--catalog')]"),
                                "кнопка открытия каталога категорий в шапке сайта");
                }
            }

            static ElementData storeButton() {
                return new ElementData(
                        By.xpath("//header//div[@class='search-container-selector']"),
                            "кнопка выбора магазина в шапке сайта");
            }

            static ElementData loginButton() {
                return new ElementData(
                        By.xpath("//header//a[contains(@class,'header-icon--login')]"),
                        "кнопка \"Войти\" в шапке сайта");
            }

            static ElementData favoritesButton() {
                return new ElementData(
                        By.xpath("//header//a[@class='header-icon' and @href='/user/favorites']"),
                            "кнопка избранного в шапке сайта");
            }

            static ElementData profileButton() {
                return new ElementData(
                        By.xpath("//header//div[@class='header-menu-toggle__btn']//a[@class='header-icon']"),
                            "кнопка профиля в шапке сайта");
            }

            static ElementData cartButton() {
                return new ElementData(
                        By.xpath("//header//div[contains(@class,'open-new-cart') and contains(@class,'header-icon')]"),
                            "кнопка открытия корзины в шапке сайта");
            }

            /** Поиск в шапке */
            interface Search {

                static ElementData inputField() {
                    return new ElementData(By.xpath("//header//input[@type='search']"),
                            "поле ввода поиска в шапке сайта");
                }

                static ElementData sendButton() {
                    return new ElementData(By.xpath("//header//button[@class='header-search__btn']"),
                            "кнопка отправки поиска в шапке сайта");
                }

                static ElementData categorySuggest() {
                    return new ElementData(By.className("header-search-list-category"));
                }

                static ElementData productSuggest() {
                    return new ElementData(By.className("header-search-list-product"));
                }
            }
        }

    /** Футер */
    public interface Footer {

        static ElementData container() {
            return new ElementData(By.xpath("//footer[@id='new-home-footer']"),
                    "блок футера");
        }

        static ElementData info() {
            return new ElementData(By.xpath("//div[@class='footer-info']"),
                    "сео-инфоблок над футером");
        }

        static ElementData instamartLogo() {
            return new ElementData(By.xpath("//footer//div[@class='footer__logo']"),
                    "логотип \"Инстамарт\" в футере");
        }

        static ElementData instamartTitle() {
            return new ElementData(By.xpath("//footer//div[@class='footer__title' and text()='Инстамарт']"),
                    "подзаголовок \"Инстамарт\" в футере");
        }

        static ElementData aboutCompanyLink() {
            return new ElementData(By.xpath("//footer//a[@class='footer__link' and text()='О компании']"),
                    "ссылка \"О компании\" в футере");
        }

        static ElementData vacanciesLink() {
            return new ElementData(By.xpath("//footer//a[@class='footer__link' and text()='Вакансии']"),
                    "ссылка \"Вакансии\" в футере");
        }

        static ElementData partnersButton() {
            return new ElementData(By.xpath("//footer//button[@class='footer__link' and text()='Партнеры']"),
                    "ссылка \"Партнеры\" в футере");
        }

        static ElementData contactsLink() {
            return new ElementData(By.xpath("//footer//a[@class='footer__link' and text()='Контакты']"),
                    "ссылка \"Контакты\" в футере");
        }

        static ElementData customerHelpTitle() {
            return new ElementData(By.xpath("//footer//div[@class='footer__title' and contains(text(),'Помощь')]"),
                    "подзаголовок \"Помощь покупателю\" в футере");
        }

        static ElementData deliveryButton() {
            return new ElementData(By.xpath("//footer//button[@class='footer__link' and text()='Доставка']"),
                    "ссылка \"Доставка\" в футере");
        }

        static ElementData paymentButton() {
            return new ElementData(By.xpath("//footer//button[@class='footer__link' and text()='Оплата']"),
                    "ссылка \"Оплата\" в футере");
        }

        static ElementData faqButton() {
            return new ElementData(By.xpath("//footer//a[@class='footer__link' and text()='FAQ']"),
                    "ссылка \"FAQ\" в футере");
        }

        static ElementData hotlinePhoneNumber() {
            return new ElementData(By.xpath("//footer//a[@href='" + companyHotlinePhoneLink + "' and text()='" + companyHotlinePhoneNumber + "']"),
                    "телефон-ссылка горячей линии в футере");
        }

        static ElementData hotlineWorkhoursText() {
            return new ElementData(By.xpath("//footer//span[text()='" + companyHotlineWorkhours + "']"),
                    "время работы горячей линии в футере");
        }

        static ElementData facebookButton() {
            return new ElementData(By.xpath("//footer//div[@class='footer__networks']//a[@href='https://www.facebook.com/instamart.ru']"),
                    "кнопка \"Facebook\" в футере");
        }

        static ElementData vkontakteButton() {
            return new ElementData(By.xpath("//footer//div[@class='footer__networks']//a[@href='http://vk.com/instamart']"),
                    "кнопка \"Вконтакте\" в футере");
        }

        static ElementData instagramButton() {
            return new ElementData(By.xpath("//footer//div[@class='footer__networks']//a[@href='https://www.instagram.com/instamart']"),
                    "кнопка \"Instagram\" в футере");
        }

        static ElementData appstoreButton() {
            return new ElementData(By.xpath("//footer//a[@href='https://app.adjust.com/zgmyh1y?campaign=footer'][1]"),
                    "кнопка \"Appstore\" в футере");
        }

        static ElementData googlePlayButton() {
            return new ElementData(By.xpath("//footer//a[@href='https://app.adjust.com/zgmyh1y?campaign=footer'][2]"),
                    "кнопка \"GooglePlay\" в футере");
        }

        static ElementData returnsPolicyLink() {
            return new ElementData(By.xpath("//footer//a[contains(@class,'footer__link') and text()='Политика возврата']"),
                    "ссылка на политику возвратов в футере");
        }

        static ElementData personalDataPolicyLink() {
            return new ElementData(By.xpath("//footer//a[contains(@class,'footer__link') and text()='Обработка персональных данных']"),
                    "ссылка на политику обработки персональных данных в футере");
        }

        static ElementData publicOfferLink() {
            return new ElementData(By.xpath("//footer//a[contains(@class,'footer__link') and text()='Публичная оферта']"),
                    "ссылка на публичную оферту в футере");
        }
    }

    /** Модалки */
    public interface Modals {

        static ElementData fade() {
            return new ElementData(By.xpath("//div[@class='frame fade-enter-done']"),
                    "Тень модалок");
        }

        // TODO update locators
        /** Модалка авторизации-регистрации */
        interface AuthModal {

        static ElementData popup() {
            return new ElementData(By.className("auth-modal"));
        }

        static ElementData closeButton() {
            return new ElementData(By.xpath("//button[@class='modal-wrapper__close']"));
        }

        static ElementData authorisationTab() {
            return new ElementData(By.xpath("//button[contains(text(),'Вход')]"),
                    "кнопка переключения на вкладку авторизации в модалке авторизации");
        }

        static ElementData registrationTab() {
            return new ElementData(By.xpath("//button[contains(text(),'Регистрация')]"),
                    "кнопка переключения на вкладку регистрации в модалке авторизации");
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

        static ElementData agreementCheckbox() { return new ElementData(By.className("checkbox__check"),
                "чекбокс согласия на получение почтовой рассылки в модалке авторизации"); }

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

        static ElementData errorMessage(String text) {
            return new ElementData(null,
                    (By.xpath("//*[contains(@class, 'auth-input__error') and contains(text(),'" + text + "')]")));
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

        // TODO update locators
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
            return new ElementData (By.xpath("//div[text()='Адрес не в зоне доставки']"));
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

        /** Модалка обновления цен */
        interface PricesModal {

        static ElementData popup() {
            return new ElementData(
                    By.xpath("//div[contains(@class,'prices-modal')]"),
                    "поп-ап модалки обновления цен");
        }

        static ElementData refreshPricesButton() {
            return new ElementData(By.className("//div[contains(@class,'prices-modal')]//a[@class='prices-modal__btn']"),
                    "кнопка обновления в модалке цен"
            );
        }
    }

        /** Модалка "Доставка" */
        interface DeliveryModal {

        static ElementData popup() {
            return new ElementData(By.xpath("//*[@class='modal-content']"));
        }

        static ElementData title() {
            return new ElementData("Доставка", By.xpath("//*[@class='modal-title']"));
        }

        static ElementData closeButton() {
            return new ElementData(By.xpath("//*[@class='modal-content']//button[@class='close']"));
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
    }

    /** Шторка выбора магазинов */
    public interface StoreSelector {

        static ElementData drawer() {
            return new ElementData(
                    By.xpath("//div[@class='drawer']//div[@class='store-selector']"),
                    "шторка выбора магазинов");
        }

        static ElementData closeButton() {
            return new ElementData(
                    By.xpath("//div[@class='drawer']//button[@class='store-selector__close']"),
                    "кнопка закрытия шторки выбора магазинов");
        }

        static ElementData placeholder() {
            return new ElementData(
                    By.xpath("//div[@class='drawer']//div[@class='store-selector__empty' and text()='Нет доступных магазинов по выбранному адресу']"),
                    "плейсхолдер пустой шторки выбора магазинов");
        }
    }

    /** Шторка каталога категорий */
    public interface CatalogDrawer {

        static ElementData drawer() {
            return new ElementData(By.xpath("//div[@class='drawer']//div[@class='category-menu']"),
                    "шторка каталога категорий");
        }

        static ElementData closeButton() {
            return new ElementData(By.xpath("//button[@class='category-menu__close']"),
                    "кнопка закрытия шторки каталога категорий");
        }
        static ElementData category(String name) {
            return new ElementData(By.xpath("//div[@class='category-menu-item__title' and text()='"+name+"']//ancestor::li[@class='category-menu-item']"),
                    "категория \"" + name + "\"");
        }
    }

    /** Всплывающее меню профиля пользователя */
    public interface AccountMenu {

        static ElementData popup() {
            return new ElementData(
                    By.xpath("//div[@class='account-menu']"), "всплывающее меню профиля");
        }

        static ElementData header() {

            return new ElementData(
                    By.xpath("//div[@class='account-menu']//div[@class='account-menu__header']"),
                    "заголовок с именем юзера во всплывающем меню профиля");
        }

        static ElementData profileButton() {
            return new ElementData(By.xpath("//div[@class='account-menu']//a[@href='/user/edit']"),
                    "кнопка \"Профиль\" во всплывающем меню профиля");
        }

        static ElementData ordersHistoryButton() {
            return new ElementData(By.xpath("//div[@class='account-menu']//a[@href='/user/orders']"),
                    "кнопка \"История заказов\" во всплывающем меню профиля");
        }

        static ElementData termsButton() {
            return new ElementData(By.xpath("//div[@class='account-menu']//a[@href='/terms']"),
                    "кнопка \"Условия пользования\" во всплывающем меню профиля");
        }

        static ElementData logoutButton() {
            return new ElementData(By.xpath("//div[@class='account-menu']//a[@href='/logout']"),
                    "кнопка \"Выйти\" во всплывающем меню профиля");
        }

        static ElementData deliveryButton() {
            return new ElementData(By.xpath("//div[@class='account-menu']//button[text()='Доставка']"),
                    "кнопка \"Доставка\" во всплывающем меню профиля");
        }

        static ElementData paymentButton() {
            return new ElementData(By.xpath("//div[@class='account-menu']//button[text()='Оплата']"),
                    "кнопка \"Оплата\" во всплывающем меню профиля");
        }

        static ElementData faqButton() {
            return new ElementData(By.xpath("//div[@class='account-menu']//a[@href='/faq']"),
                    "кнопка \"FAQ\" во всплывающем меню профиля");
        }

        static ElementData contactsButton() {
            return new ElementData(By.xpath("//div[@class='account-menu']//a[@href='/contacts']"),
                    "кнопка \"Контакты\" во всплывающем меню профиля");
        }
    }

    /** Профиль пользователя */
    public interface UserProfile {

        static ElementData menu() {
            return new ElementData(null,
                    By.className("user-menu"));
        }

        /** Страница аккаунта */
        interface AccountPage {

            static ElementData email() {
                return new ElementData(By.xpath("//div[@class='user-profile-field__label' and text()='Email']//following-sibling::div[@class='user-profile-field__value']"),
                        "email юзера на странице информации об аккаунте");
            }

            static ElementData changePasswordButton() {
                return new ElementData(By.xpath("//div[@class='user-profile-field__label' and text()='Пароль']//following-sibling::div[@class='user-profile-field__actions']/button[text()='Изменить']"),
                        "кнопка изменения пароля на странице информации об аккаунте");
            }

            static ElementData changeEmailButton() {
                return new ElementData(By.xpath("//div[@class='user-profile-field__label' and text()='Email']//following-sibling::div[@class='user-profile-field__actions']/button[text()='Изменить']"),
                        "кнопка изменения email на странице информации об аккаунте");
            }

            static ElementData changeNameButton() {
                return new ElementData( By.xpath("//div[@class='user-profile-field__label' and text()='Имя и Фамилия']//following-sibling::div[@class='user-profile-field__actions']/button[text()='Изменить']"),
                        "кнопка изменения имени и фамилии на странице информации об аккаунте");
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

            static ElementData document(String name) {
                return new ElementData(By.xpath("//a[text()='" + name + "']"),
                        "ссылка на \"" + name + "\"");
            }
        }
    }

    /** Любимые товары */
    public interface Favorites {

        static ElementData placeholder() {
            return new ElementData(
                    By.xpath("//h1[text()='Ваш список пуст']"),
                        "плейсхолдер пустого списка любимых товаров");
        }

        static ElementData favsFilterButton(String filterName) {
            return new ElementData(
                    By.xpath("//a[contains(@class,'favorite-list-filter__link') and text()='" + filterName + "']"),
                        "кнопка фильтра \"" + filterName + "\" в любимых товарах");
        }

        static ElementData allItemsFilterButton() {
            return favsFilterButton("Все товары");
        }

        static ElementData inStockFilterButton() {
            return favsFilterButton("В наличии");
        }

        static ElementData outOfStockFilterButton() {
            return favsFilterButton("Нет в наличии");
        }

        static ElementData activeFilter() {
            return new ElementData(
                    By.xpath("//a[contains(@class,'favorite-list-filter__link--active')]"),
                        "активный фильтр любимых товаров");
        }

        static ElementData showMoreButton() {
            return new ElementData(
                    By.xpath("//button[text()='Показать еще']"),
                        "кнопка \"Показать еще\"");
        }

        interface Product {
            static ElementData snippet() {
                return new ElementData(By.xpath("//*[@class='favorite-product']"),
                        "сниппет любимого продукта");
            }

            static ElementData plusButton() {
                return new ElementData(By.xpath("//*[@class='favorite-product']//button[@class='bt bt--buy cart-actions__btn cart-actions__btn--visible']"),
                        "кнопка \"+\" в сниппете любимого продукта");
            }

            static ElementData minusButton() {
                return new ElementData(By.xpath("//*[@class='favorite-product']//button[contains(@class,'cart-actions__btn--left')]"),
                        "кнопка \"-\" в сниппете любимого продукта");
            }

            static ElementData favButton() {
                return new ElementData(By.xpath("//*[@class='favorite-product']//div[contains(@class,'favorite-button')]"),
                        "кнопка избранного в сниппете любимого продукта");
            }
        }
    }

    /** Каталог товаров */
    public interface Catalog {

        static ElementData emptySearchPlaceholder() {
            return new ElementData(By.cssSelector(".resource-not-found__message-block"));
        }

        interface Product {
            static ElementData snippet() {
                return new ElementData(By.xpath("//*[@class='product']"),
                        "сниппет продукта");
            }

            static ElementData plusButton() {
                return new ElementData(By.xpath("//*[@class='product']//div[contains(@class,'add-cart__up')]"),
                        "кнопка \"+\" в сниппете продукта");
            }

            static ElementData minusButton() {
                return new ElementData(By.xpath("//*[@class='product']//div[contains(@class,'add-cart__down')]"),
                        "кнопка \"-\" в сниппете продукта");
            }

            static ElementData favButton() {
                return new ElementData(By.xpath("//*[@class='product']//div[contains(@class,'favorite-button')]"),
                        "кнопка избранного в сниппете продукта");
            }
        }
    }

    /** SEO-каталог */
    public interface SeoCatalog {
        //TODO update locators and logic
        static ElementData product() {
            return new ElementData(By.className("category-product"));
        }
    }

    /** Страницы категорий/таксонов */
    public interface CategoryPage {

        static ElementData title() {
            return new ElementData(By.className("taxons-nav__title"));
        }

        static ElementData filters() {
            return new ElementData(By.className("filters_container"));
        }

    }

    /** Карточка товара */
    public interface ItemCard {

            static ElementData popup() {
                return new ElementData(By.className("product-popup"));
            }

            static ElementData name() {
                return new ElementData(By.xpath("//h1[@class='product-popup__title']"));
            }

            static ElementData image() {
                return new ElementData(By.className("product-popup__img"));
            }

            static ElementData price() {
                return new ElementData(By.xpath("//div[@class='product-popup__price']//span"));
            }

            static ElementData quantity() {
                return new ElementData(By.xpath("//div[@class='product-popup']//div[contains(@class,'product__cart-counter')]"),
                "каунтер количества добавленного в корзину товара в карточке товара");
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
    public interface Cart {

            static ElementData drawer() {
                return new ElementData(By.xpath("//div[@class='drawer']//div[@class='new-cart']"),
                        "шторка корзины");
            }

            static ElementData closeButton() {
                return new ElementData(By.xpath("//div[@class='new-cart']//*[@class='btn-close-cart']"),
                        "кнопка закрытия корзины");
            }

            static ElementData placeholder() {
                return new ElementData(By.xpath("//div[@class='new-cart']//div[@class='new-cart-empty__text' and text()='Ваша корзина пуста']"),
                        "плейсхолдер пустой корзины");
            }

            static ElementData alertText() {
                return new ElementData(By.xpath("//div[@class='new-cart']//div[@class='cart-retailer__alert']"),
                        "алерт с суммой минимального заказа в корзине");
            }

            static ElementData item() {
                return new ElementData(
                        By.xpath("//div[@class='new-cart']//div[@class='cart-line-item']"),
                        "ячейка товара в корзине");
            }

            static ElementData itemUpButton() {
                return new ElementData(
                        By.className("//div[@class='new-cart']//div[@class='cart-line-item']//button[@class='line-item-counter__control'][1]"),
                        "кнопка увеличения количества товара в корзине");
            }

            static ElementData itemQuantity() {
                return new ElementData(
                        By.className("//div[@class='new-cart']//div[@class='cart-line-item']//span[@class='line-item-counter__text']"),
                        "количество товара в корзине");
            }

            static ElementData itemDownButton() {
                return new ElementData(
                        By.className("//div[@class='new-cart']//div[@class='cart-line-item']//button[@class='line-item-counter__control'][2]"),
                        "кнопка уменьшения количества товара в корзине");
            }

            static ElementData itemRemoveButton() {
                return new ElementData(
                        By.xpath("//div[@class='new-cart']//div[@class='cart-line-item']//div[@class='cart-line-item__remove']"),
                        "кнопка удаления товара в корзине");
            }

            static ElementData checkoutButton() {
                return new ElementData(By.xpath("//div[@class='new-cart']//button[@class='cart-checkout-link']"),
                        "кнопка \"Сделать заказ\" в корзине");
            }

            static ElementData total() {
                //return new ElementData(By.cssSelector(".cart-checkout-link__well > div:nth-child(1)"));
                return new ElementData(By.xpath("//div[@class='new-cart']//div[@class='cart-checkout-link__well']/child::div"),
                        "сумма корзины");
            }
        }

    /** Чекаут */
    public interface Checkout {

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

            static ElementData bonusProgramsSelector(int option) {
                return new ElementData(By.xpath("//aside/div/div[3]/div[2]/div[" + option + "]"));
            }

            static ElementData bonusProgramsEditButton(int option) {
                return new ElementData(By.xpath("/html/body/div[3]/div/form/div/aside/div/div[3]/div[2]/div[" + option + "]/div[2]"));
            }

            static ElementData deleteBonusProgramButton() {
                return new ElementData(By.xpath("//*[@id='react-modal']/div/div[2]/div/div/div[2]/div/form/div[2]/div/div[1]/button"));
            }

            static ElementData loyaltyProgramsSelector() {  // Переделать когда появится выбор нескольких программ лояльности
                return new ElementData(By.xpath("/html/body/div[3]/div/form/div/aside/div/div[4]/div[2]/div"));
            }

            static ElementData loyaltyProgramsEditButton() { // Переделать когда появится выбор несколбких программ лояльности
                return new ElementData(By.xpath("/html/body/div[3]/div/form/div/aside/div/div[4]/div[2]/div/div[2]"));
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

            /** Селектор дней доставки */
            static ElementData deliveryDaySelector(int day) {
                return new ElementData(By.cssSelector("div.panel-tab:nth-child(" + day + ")"));
            }

            /** Итерфейс выбора слота доставки */
            static ElementData deliveryWindowSelector() {
                return new ElementData(By.className("windows-selector-item"));
            }

            /** Заглушка при отсутствии доступных слотов доставки в выбранный день */
            static ElementData deliveryWindowsPlaceholder() {
                return new ElementData(By.xpath("//div[text()='Интервалы доставки недоступны']"));
            }

            /** Интервал слота доставки */
            static ElementData slotTime(int day, int position) {
                return new ElementData( By.xpath("//*[@id='deliveryDay-" + (day-1) + "']/div[" + position + "]/span"));
            }

            /** Стоимость слота доставки */
            static ElementData slotPrice(int day, int position) {
                return new ElementData(By.xpath("//*[@id='deliveryDay-" + (day-1) + "']/div[" + position + "]/div/div/div/div"));
            }

            /** Кнопка выбора слота доставки для тестов, первый доступный в крайний день */
            static ElementData chooseSlotButton() {
                return new ElementData(By.xpath("//*[@id='deliveryDay-6']//span[text()='Выбрать']"));
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

    /** Чат Jivosite */
    public interface Jivosite {

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

    /** Блоки RetailRocket */
    public interface RetailRocket {

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

    /** Админка */
    public interface Admin {

        static ElementData container() {
            return new ElementData(By.xpath("//body[@class='admin']"),
                    "контейнер админки");
        }

        static ElementData menuButton(String name) {
            return new ElementData(By.xpath("//nav[@id='admin-menu']//span[text()='" + name + "']"),
                    "кнопка \"" + name + "\" в навигационном меню админки");
        }

        static ElementData submenuButton(String name) {
            return new ElementData(By.xpath("//nav[@class='admin-sub-menu']//*[text()='" + name + "']"),
                    "кнопка \"" + name + "\" в навигационном субменю админки");
        }

        /** Шапка админки */
        interface Header {

            static ElementData logo() {
                return new ElementData(By.xpath("//header//img[@id='logo']"),
                        "логотип в шапке админки");
            }

            static ElementData userAccount() {
                return new ElementData(By.xpath("//header//li[@data-hook='user-logged-in-as']"),
                        "учетка пользователя в шапке админки");
            }

            static ElementData logoutButton() {
                return new ElementData(By.xpath("//header//a[text()='Выйти']"),
                        "кнопка \"Выйти\" в шапке админки");
            }

            static ElementData accountButton() {
                return new ElementData(By.xpath("//header//a[text()='Учетная запись']"),
                        "кнопка \"Учетная запись\"");
            }

            static ElementData backButton() {
                return new ElementData(By.xpath("//header//a[text()='Назад к списку']"),
                        "кнопка \"Назад к списку\"");
            }
        }

        /** Раздел Shipments в админке */
        interface Shipments {

            static ElementData placeholder() {
                return new ElementData(By.className("no-objects-found"));
            }

            static ElementData filter(String name) {
                return new ElementData(
                        By.xpath("//label[text()='Способ оплаты']//following-sibling::div//input"),
                        "фильтр заказов \"" + name + "\"");
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



            static ElementData completedOnlyCheckbox() {
                return new ElementData(By.id("search_only_completed"),
                        "чекбокс \"Показывать только завершенные заказы\"");
            }

            static ElementData b2bOnlyCheckbox() {
                return new ElementData(By.id("search_only_b2b"),
                        "чекбокс \"Только B2B клиенты\"");
            }

            static ElementData metroOnlyCheckbox() {
                return new ElementData(By.id("search_tenant"),
                        "чекбокс \"Только заказы с сайта метро\"");
            }

            static ElementData deliveryChangedOnlyCheckbox() {
                return new ElementData(By.id("search_delivery_window_changed"),
                        "чекбокс \"Только с переносом времени доставки\"");
            }

            static ElementData searchButton() {
                return new ElementData(By.xpath("//button[@class='icon-search button']"),
                        "кнопка \"Применить фильтр\"");
            }

            static ElementData dismissButton() {
                return new ElementData(By.xpath("//a[@class='button icon-remove']"),
                        "кнопка \"Сбросить фильтр\"");
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

                    static ElementData loyaltyProgram() {
                        return new ElementData(By.xpath("//*[@id='content']/div/table/tbody/tr[5]"));
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
}
