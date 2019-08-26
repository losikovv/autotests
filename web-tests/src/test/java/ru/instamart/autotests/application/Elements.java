package ru.instamart.autotests.application;

import org.openqa.selenium.By;
import ru.instamart.autotests.models.CheckoutStepData;
import ru.instamart.autotests.models.CreditCardData;
import ru.instamart.autotests.models.ElementData;
import ru.instamart.autotests.models.JuridicalData;

import static ru.instamart.autotests.application.CheckoutSteps.*;
import static ru.instamart.autotests.application.Config.TestVariables.CompanyParams.companyHotlineWorkhours;
import static ru.instamart.autotests.application.Config.TestVariables.CompanyParams.companyHotlineWorkhoursShort;
import static ru.instamart.autotests.application.Config.TestVariables.CompanyParams.companyHotlinePhoneLink;
import static ru.instamart.autotests.application.Config.TestVariables.CompanyParams.companyHotlinePhoneNumber;

public class Elements {

    public static ElementData spinner() {
        return new ElementData(
                By.xpath("//div[@class='spinner-container']"),
                    "спиннер загрузки");
    }

    /** Страница 500 ошибки */
    public interface Page500 {

        static ElementData placeholder() {
            return new ElementData(By.xpath("//*[contains(text(),'sorry, but something went wrong')]"),
                    "тайтл ошибки на странице 500");
        }
    }

    /** Страница 502 ошибки CloudFlare */
    public interface Page502 {

        static ElementData title() {
            return new ElementData(By.xpath("//span[@class='cf-error-code' and text()='502']"),
                    "текст ошибки на странице 502");
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
                    "кнопка 'Познать котомудрость' на странице 404");
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
                    "кнопка 'На главную' на странице 404");
        }

        static ElementData learnPricesButton() {
            return new ElementData(By.xpath("//a[text()='Познать цены *']"),
                    "кнопка 'Познать цены' на странице 404");
        }

        static ElementData showMoreWisdomButton() {
            return new ElementData(By.xpath("//a[@class='error-page__link' and text()='ещё котомудрость']"),
                    "кнопка 'Еще котомудрость' на странице 404");
        }
    }

    /** Лендинг */
    public interface Landing {

        interface MainBlock {

            static ElementData logo() {
                return new ElementData(By.xpath("//header//a[@class='logo']"),
                        "логотип в шапке лендинга");
            }

            static ElementData title() {
                return new ElementData(By.xpath("//h1[@class='window__title' and contains(text(),'Доставка продуктов')]"),
                    "главный тайтл лендинга");
            }

            static ElementData howWeWorkLink() {
                return new ElementData(By.xpath("//header//a[contains(text(),'Как мы работаем?')]"),
                        "ссылка 'Как мы работаем?' в шапке лендинга");
            }


            static ElementData helpLink() {
                return new ElementData(By.xpath("//header//a[contains(text(),'Помощь')]"),
                        "ссылка 'Помощь' в шапке лендинга");
            }

            static ElementData mnogoruLogo() {
                return new ElementData(By.xpath("//header//a[@href='/mnogoru']//img"),
                        "логотип Много.ру в шапке лендинга");
            }

            static ElementData hotlineLink() {
                return new ElementData(By.xpath("//header//a[@href='tel:+78002221100']//span[text()='+7 800 222-11-00']"),
                        "ссылка на телефон горячей линии в шапке лендинга");
            }

            static ElementData loginButton() {
                return new ElementData(By.xpath("//header//a[text()='Вход']"),
                        "кнопка 'Вход' в шапке лендинга");
            }

            static ElementData mainTitle() {
                return new ElementData(
                        By.xpath("//h1[@class='window__title' and contains(text(),'Доставка')]"),
                        "главный тайтл в главном блоке лендинга");
            }

            static ElementData goToCatalogButton() {
                return new ElementData(By.xpath("//a[text()='Перейти в каталог']"),
                        "кнопка 'Перейти в каталог' в главном блоке лендинга");
            }

            static ElementData advantages() {
                return new ElementData(By.xpath("//div[@class='advantages']"),
                        "список преимуществ в главном блоке лендинга");
            }
        }

        interface PricesPromoBlock {

            static ElementData panel() {
                return new ElementData(By.xpath("//div[@class='promo block' and @id='new-home-promo']"),
                        "промоблок цен как в магазине на лендинге");
            }

            static ElementData goToCatalogButton() {
                return new ElementData(By.xpath("//div[@class='promo block' and @id='new-home-promo']//a[text()='Перейти в каталог']"),
                        "кнопка 'Перейти в каталог' в промоблоке цен как в магазине на лендинге");
            }
        }

        interface UserReviewsPromoBlock {

            static ElementData panel() {
                return new ElementData(By.xpath("//div[@id='new-home-reviews']"),
                        "промоблок пользовательских отзывов на лендинге");
            }
        }

        interface MobileAppPromoBlock {

            static ElementData panel() {
                return new ElementData(By.xpath("//div[@class='promo mobile-promo block']"),
                        "промоблок мобильного приложения на лендинге");
            }

            static ElementData phoneField() {
                return new ElementData(By.xpath("//input[@class='mobile-promo__input']"),
                        "поле ввода телефона в промоблоке мобильного приложения на лендинге");
            }

            static ElementData sendLinkButton() {
                return new ElementData(By.xpath("//button[text()='Получить ссылку на скачивание']"),
                        "кнопка 'Получить ссылку на скачивание' в промоблоке мобильного приложения на лендинге");
            }

            static ElementData successPlaceholder() {
                return new ElementData(By.xpath("//div[@class='mobile-promo__success' and text()='Вам отправлено СМС со ссылкой на наше приложение']"),
                        "текст успешного запроса ссылки в промоблоке мобильного приложения на лендинге");
            }
        }

        interface SeoBlock {

            static ElementData panel() {
                return new ElementData(By.xpath("//div[@class='promo mobile-promo block']"),
                        "SEO-блок на лендинге");
            }
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
                        By.xpath("//header//*[@data-qa='ship-address-selector']//*[@data-qa='select-button']"),
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

            private static ElementData infoButton(String name) {
                return new ElementData(
                       By.xpath("//header//*[contains(text(),'" + name + "')]//ancestor::*[@data-qa='header-navbar-button']"),
                           "инфокнопка '" + name + "' в шапке сайта");
            }


            static ElementData howWeWorkInfoButton() {
                return infoButton("Как мы работаем");
            }

            static ElementData contactsInfoButton() {
                return infoButton("Контакты");
            }

            static ElementData helpInfoButton() {
                return infoButton("Помощь");
            }

            static ElementData deliveryInfoButton() {
                return infoButton("Доставка и оплата");
            }

            static ElementData corporativeCustomersInfoButton() {
                return infoButton("Корпоративным клиентам");
            }

            static ElementData mnogoruButton() {
                return new ElementData(
                        By.xpath("//header//a[@class='navbar-button']//div[@class='navbar-button__mnogoru']"),
                            "кнопка \"МногоРу\" в шапке сайта");
            }

            static ElementData catalogButton() {
                return new ElementData(By.xpath("//header//*[@data-qa='catalog-button']"),
                            "кнопка открытия каталога категорий в шапке сайта");
            }

            static ElementData storeButton() {
                return new ElementData(
                        By.xpath("//header//*[@data-qa='open-store-selector-button']"),
                            "кнопка выбора магазина в шапке сайта");
            }

            static ElementData loginButton() {
                return new ElementData(
                        By.xpath("//header//a[contains(@class,'header-icon--login')]"),
                            "кнопка 'Войти' в шапке сайта");
            }

            static ElementData favoritesButton() {
                return new ElementData(
                        By.xpath("//header//*[@data-qa='favorites-link']"),
                            "кнопка избранного в шапке сайта");
            }

            static ElementData profileButton() {
                return new ElementData(
                        By.xpath("//header//*[@data-qa='menu-toggle']//*[@data-qa='toggle-button']"),
                            "кнопка профиля в шапке сайта");
            }

            static ElementData cartButton() {
                return new ElementData(
                        By.xpath("//header//*[@data-qa='open-cart-button']"),
                            "кнопка открытия корзины в шапке сайта");
            }

            static ElementData cartCounter() {
              return new ElementData(
                       By.xpath("//header//div[@class='header__cart']//div[@class='open-new-cart__counter']"),
                            "наклейка счетчика товаров на кнопке корзины в шапке сайта");
            }

            /** Поиск в шапке */
            interface Search {

                static ElementData container() {
                    return new ElementData(By.xpath("//header//*[@data-qa='search-container']"),
                            "поисковая строка в шапке сайта");
                }

                static ElementData inputField() {
                    return new ElementData(By.xpath("//header//*[@data-qa='search-container']//input[@type='search']"),
                            "поле ввода поискового запроса в шапке сайта");
                }

                static ElementData sendButton() {
                    return new ElementData(By.xpath("//header//*[@data-qa='search-container']//button[@type='submit']"),
                            "кнопка отправки поискового запроса в шапке сайта");
                }

                static ElementData categorySuggest() {
                    return new ElementData(By.className("header-search-list-category"),
                            "категорийная поисковая подсказка");
                }

                static ElementData productSuggest() {
                    return new ElementData(By.className("header-search-list-product"),
                            "товарная поисковая подсказка");
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

        static ElementData infoLink(String name) {
            return new ElementData(By.xpath("//footer//*[@class='footer__link' and text()='" + name + "']"),
                    "ссылка \"" + name + "\" в футере");
        }

        static ElementData instamartTitle() {
            return new ElementData(By.xpath("//footer//div[@class='footer__title' and text()='Instamart']"),
                    "подзаголовок \"Инстамарт\" в футере");
        }

        static ElementData aboutCompanyLink() {
            return infoLink("О компании");
        }

        static ElementData vacanciesLink() {
            return infoLink("Вакансии");
        }

        static ElementData partnersButton() {
            return infoLink("Партнеры");
        }

        static ElementData contactsLink() {
            return infoLink("Контакты");
        }

        static ElementData customerHelpTitle() {
            return new ElementData(By.xpath("//footer//div[@class='footer__title' and contains(text(),'Помощь')]"),
                    "подзаголовок \"Помощь покупателю\" в футере");
        }

        static ElementData deliveryButton() {
            return infoLink("Доставка");
        }

        static ElementData paymentButton() {
            return infoLink("Оплата");
        }

        static ElementData faqButton() {
            return infoLink("FAQ");
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
                    By.xpath("//*[@data-qa='store-selector']"),
                        "шторка выбора магазинов");
        }

        static ElementData closeButton() {
            return new ElementData(
                    By.xpath("//*[@data-qa='store-selector']//*[@data-qa='close-button']"),
                        "кнопка закрытия шторки выбора магазинов");
        }

        static ElementData placeholder() {
            return new ElementData(
                    By.xpath("//*[@data-qa='store-selector']//div[@class='store-selector__empty' and text()='Нет доступных магазинов по выбранному адресу']"),
                        "плейсхолдер пустой шторки выбора магазинов");
        }

        static ElementData storeCard() {
            return storeCard(1);
        }

        static ElementData storeCard(int position) {
            return new ElementData(
                    By.xpath("//*[@data-qa='store-card'][" + position + "]"),
                        position + "карточка магазина в шторке выбора магазинов");
        }
    }

    /** Шторка каталога категорий */
    public interface CatalogDrawer {

        static ElementData drawer() {
            return new ElementData(By.xpath("//*[@data-qa='category-menu']"),
                    "шторка каталога категорий");
        }

        static ElementData closeButton() {
            return new ElementData(By.xpath("//*[@data-qa='category-menu']//*[@data-qa='close-button']"),
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
                    By.xpath("//header//*[@data-qa='account-menu']"), "всплывающее меню профиля");
        }

        static ElementData header() {

            return new ElementData(
                    By.xpath("//header//*[@data-qa='account-menu']//div[@class='account-menu__header']"),
                    "заголовок с именем юзера во всплывающем меню профиля");
        }

        static ElementData profileButton() {
            return new ElementData(By.xpath("//header//*[@data-qa='account-menu']//a[@href='/user/edit']"),
                    "кнопка \"Профиль\" во всплывающем меню профиля");
        }

        static ElementData ordersHistoryButton() {
            return new ElementData(By.xpath("//header//*[@data-qa='account-menu']//a[@href='/user/orders']"),
                    "кнопка \"История заказов\" во всплывающем меню профиля");
        }

        static ElementData termsButton() {
            return new ElementData(By.xpath("//header//*[@data-qa='account-menu']//a[@href='/terms']"),
                    "кнопка \"Условия пользования\" во всплывающем меню профиля");
        }

        static ElementData logoutButton() {
            return new ElementData(By.xpath("//header//*[@data-qa='account-menu']//a[@href='/logout']"),
                    "кнопка \"Выйти\" во всплывающем меню профиля");
        }

        static ElementData deliveryButton() {
            return new ElementData(By.xpath("//header//*[@data-qa='account-menu']//button[text()='Доставка']"),
                    "кнопка \"Доставка\" во всплывающем меню профиля");
        }

        static ElementData paymentButton() {
            return new ElementData(By.xpath("//header//*[@data-qa='account-menu']//button[text()='Оплата']"),
                    "кнопка \"Оплата\" во всплывающем меню профиля");
        }

        static ElementData faqButton() {
            return new ElementData(By.xpath("//header//*[@data-qa='account-menu']//a[@href='/faq']"),
                    "кнопка \"FAQ\" во всплывающем меню профиля");
        }

        static ElementData contactsButton() {
            return new ElementData(By.xpath("//header//*[@data-qa='account-menu']//a[@href='/contacts']"),
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
        interface OrdersHistoryPage {

            static ElementData placeholder() {
                return new ElementData(By.xpath("//div[@class='no-content__text' and text()='У вас нет завершенных заказов']"),
                        "плейсхолдер пустой истории заказов");
            }

            interface order {

                static ElementData snippet() {
                    return new ElementData(By.xpath("//div[@class='user-orders__item user-block']"),
                            "сниппет верхнего заказа на странице истории заказов");
                }

                static ElementData cancelButton() {
                    return new ElementData(By.xpath("//div[@class='user-orders__item user-block']//button[text()='Отменить']"),
                            "кнопка отмены верхнего заказа на странице истории заказов");
                }

                static ElementData repeatButton() {
                    return new ElementData(By.xpath("//div[@class='user-orders__item user-block']//button[text()='Повторить']"),
                            "кнопка повтора верхнего заказа на странице истории заказов");
                }

                static ElementData detailsButton() {
                    return new ElementData(By.xpath("//div[@class='user-orders__item user-block']//a[text()='Детали заказа']"),
                            "кнопка деталей верхнего заказа на странице истории заказов");
                }
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
                return new ElementData(
                        By.xpath("//*[@data-qa='cart']"),
                            "шторка корзины");
            }

            static ElementData closeButton() {
                return new ElementData(
                        By.xpath("//*[@data-qa='cart']//*[@data-qa='close-button']"),
                            "кнопка закрытия корзины");
            }

            static ElementData placeholder() {
                return new ElementData(
                        By.xpath("//*[@data-qa='cart']//div[@class='new-cart-empty__text' and text()='Ваша корзина пуста']"),
                            "плейсхолдер пустой корзины");
            }

            static ElementData alertText() {
                return new ElementData(
                        By.xpath("//*[@data-qa='cart']//div[@class='cart-retailer__alert']"),
                            "алерт с суммой минимального заказа в корзине");
            }

            interface item {

                static ElementData snippet() {
                    return new ElementData(
                            By.xpath("//*[@data-qa='cart']//*[@data-qa='line-item']//div[@class='cart-line-item']"),
                                "ячейка верхнего товара в корзине");
                }

                static ElementData openButton() {
                    return new ElementData(
                            By.xpath("//*[@data-qa='cart']//*[@data-qa='line-item']//*[@data-qa='open-button']"),
                                "кнопка открытия верхнего товара в корзине");
                }

                static ElementData increaseButton() {
                    return new ElementData(
                            By.xpath("//*[@data-qa='cart']//*[@data-qa='line-item']//*[@data-qa='increase-button']"),
                                "кнопка увеличения количества верхнего товара в корзине");
                }

                static ElementData counter() {
                    return new ElementData(
                            By.xpath("//*[@data-qa='cart']//*[@data-qa='line-item']//*[@data-qa='line-item-counter']//span[@class='line-item-counter__text']"),
                                "количество верхнего товара в корзине");
                }

                static ElementData decreaseButton() {
                    return new ElementData(
                            By.xpath("//*[@data-qa='cart']//*[@data-qa='line-item']//*[@data-qa='decrease-button']"),
                                "кнопка уменьшения количества верхнего товара в корзине");
                }

                static ElementData removeButton() {
                    return new ElementData(
                            By.xpath("//*[@data-qa='cart']//*[@data-qa='line-item']//*[@data-qa='remove-button']"),
                                "кнопка удаления верхнего товара в корзине");
                }
            }

            static ElementData checkoutButton() {
                return new ElementData(By.xpath("//*[@data-qa='cart']//*[@data-qa='checkout-button']"),
                            "кнопка 'Сделать заказ' в корзине");
            }

            static ElementData total() {
                return new ElementData(
                        By.xpath("//*[@data-qa='cart']//*[@data-qa='checkout-button']//div[@class='cart-checkout-link__well']/child::div"),
                            "сумма заказа в корзине");
            }
        }

    // TODO Update and rearrange locators
    /** Чекаут */
    public interface Checkout {

        static ElementData header() {
            return new ElementData(By.className("chekout-header"),
                    "шапка чекаута");
        }

        interface Step {

            static ElementData panel(CheckoutStepData step) {
                return new ElementData(By.xpath("//div[@class='panel-header__text' and text()='" + step.getTitle() + "']//ancestor::div[@class='panel-header']//parent::div"),
                        "открытая панель шага \"" + step.getName() + "\" в чекауте");
            }

            static ElementData icon(CheckoutStepData step) {
                return new ElementData(By.xpath("//div[@class='panel-header__text' and text()='" + step.getTitle() + "']//preceding::*[@class='panel-header__ico']"),
                        "иконка шага \"" + step.getName() + "\" в чекауте");
            }

            static ElementData title(CheckoutStepData step) {
                return new ElementData(By.xpath("//div[@class='panel-header__text' and text()='" + step.getTitle() + "']"),
                        "иконка шага \"" + step.getName() + "\" в чекауте");
            }

            static ElementData nextButton(CheckoutStepData step) {
                return new ElementData(By.xpath("//div[@class='panel-header__text' and text()='" + step.getTitle() + "']//ancestor::div[@class='panel-header']//parent::div//button[text()='Продолжить']"),
                        "кнопка \"Продолжить\" шага \"" + step.getName() + "\" в чекауте");
            }

            static ElementData saveButton(CheckoutStepData step) {
                return new ElementData(By.xpath("//div[@class='panel-header__text' and text()='" + step.getTitle() + "']//ancestor::div[@class='panel-header']//parent::div//button[text()='Сохранить']"),
                        "кнопка \"Сохранить\" шага \"" + step.getName() + "\" в чекауте");
            }
        }

        interface MinimizedStep {

            static ElementData panel(CheckoutStepData step) {
                return new ElementData(By.xpath("//div[@class='panel-header__text' and text()='" + step.getName() + "']//ancestor::div[@class='checkout-panel']"),
                        "панель свернутого шага " + step.getName() + "\" в чекауте");
            }

            static ElementData icon(CheckoutStepData step) {
                return new ElementData(By.xpath("//div[@class='panel-header__text' and text()='" + step.getName() + "']//ancestor::div[@class='checkout-panel']//*[@class='panel-header__ico']"),
                        "иконка свернутого шага " + step.getName() + "\" в чекауте");
            }

            static ElementData name(CheckoutStepData step) {
                return new ElementData(By.xpath("//div[@class='panel-header__text' and text()='" + step.getName() + "']"),
                        "название свернутого шага " + step.getName() + "\" в чекауте");
            }

            static ElementData details(CheckoutStepData step) {
                return new ElementData(By.xpath("//div[@class='panel-header__text' and text()='" + step.getName() + "']//ancestor::div[@class='checkout-panel']//div[@class='panel-header__details']"),
                        "детали свернутого шага " + step.getName() + "\" в чекауте");
            }

            // todo поправить локатор - чтобы цеплялся только к указанному шагу
            static ElementData changeButton(CheckoutStepData step) {
                return new ElementData(By.xpath("//div[@class='panel-header__text' and text()='" + step.getName() + "']//ancestor::div[@class='checkout-panel']//*[contains(text(),'Изменить')]"),
                    "кнопка \"Изменить\" в свернутом шаге " + step.getName() + "\" в чекауте");
            }
        }

        interface ActiveStep {

            static ElementData panel() {
                return new ElementData(By.xpath("//div[contains(@class,'checkout-panel--active')]"),
                        "панель активного шага в чекауте");
            }

            static ElementData icon() {
                return new ElementData(By.xpath("//div[contains(@class,'checkout-panel--active')]//*[@class='panel-header__ico']"),
                        "иконка активного шага в чекауте");
            }

            static ElementData title() {
                return new ElementData(By.xpath("//div[contains(@class,'checkout-panel--active')]//div[@class='panel-header__text']"),
                        "название активного шага в чекауте");
            }
        }

        interface AddressStep {

            CheckoutStepData addressStep = addressStep();

            static ElementData panel() {
                return Checkout.Step.panel(addressStep);
            }

            static ElementData minimizedPanel() {
                return Checkout.MinimizedStep.panel(addressStep);
            }

            static ElementData icon() {
                return Checkout.Step.icon(addressStep);
            }

            static ElementData title() {
                return Checkout.Step.title(addressStep);
            }

            static ElementData deliveryAddress() {
                return new ElementData(By.xpath("//div[@class='selected-address__address']"),
                        "выбранный адрес доставки в шаге \"" + addressStep.getName() + "\" в чекауте");
            }

            static ElementData homeRadioButton() {
                return new ElementData(By.xpath("//input[@value='home']"),
                        "радиокнопка \"Квартира\" в шаге \"" + addressStep.getName() + "\" в чекауте");
            }

            static ElementData officeRadioButton() {
                return new ElementData(By.xpath("//input[@value='office']"),
                        "радиокнопка \"Офис\" в шаге \"" + addressStep.getName() + "\" в чекауте");
            }

            static ElementData apartmentInputField() {
                return new ElementData(By.xpath("//input[@name='apartment']"),
                        "поле \"Номер квартиры / офис\" в шаге \"" + addressStep.getName() + "\" в чекауте");
            }

            static ElementData floorInputField() {
                return new ElementData(By.xpath("//input[@name='floor']"),
                        "поле \"Этаж\" в шаге \"" + addressStep.getName() + "\" в чекауте");
            }

            static ElementData elevatorCheckbox() {
                return new ElementData(By.xpath("//span[text()='Есть лифт']//preceding::*[@class='checkbox__check']"),
                        "чекбокс \"Есть лифт\" в шаге \"" + addressStep.getName() + "\" в чекауте");
            }

            static ElementData entranceInputField() {
                return new ElementData(By.xpath("//input[@name='entrance']"),
                        "поле \"Подъезд\" в шаге \"" + addressStep.getName() + "\" в чекауте");
            }

            static ElementData domofonInputField() {
                return new ElementData(By.xpath("//input[@name='doorPhone']"),
                        "поле \"Код домофона\" в шаге \"" + addressStep.getName() + "\" в чекауте");
            }

            static ElementData commentariesInputField() {
                return new ElementData(By.xpath("//textarea[@name='order[special_instructions]']"),
                        "поле \"Комментарии по доставке\" в шаге \"" + addressStep.getName() + "\" в чекауте");
            }

            static ElementData nextButton() {
                return Checkout.Step.nextButton(addressStep);
            }

            static ElementData saveButton() {
                return Checkout.Step.saveButton(addressStep);
            }

            static ElementData changeButton() {
                return Checkout.MinimizedStep.changeButton(addressStep);
            }
        }

        interface ContactsStep {

            CheckoutStepData contactsStep = contactsStep();

            static ElementData panel() {
                return Checkout.Step.panel(contactsStep);
            }

            static ElementData minimizedPanel() {
                return Checkout.MinimizedStep.panel(contactsStep);
            }

            static ElementData icon() {
                return Checkout.Step.icon(contactsStep);
            }

            static ElementData title() {
                return Checkout.Step.title(contactsStep);
            }

            static ElementData firstNameInputField() {
                return new ElementData(By.xpath("//input[@name='user.firstName']"),
                        "поле \"Имя\" в шаге \"" + contactsStep.getName() + "\" в чекауте");
            }

            static ElementData firstNameInputFieldErrorText() {
                return new ElementData(By.xpath("//input[@name='user.firstName']//following-sibling::div[@class='checkout-input-error']//text()"),
                        "текст ошибки поля \"Имя\" в шаге \"" + contactsStep.getName() + "\" в чекауте");
            }

            static ElementData lastNameInputField() {
                return new ElementData(By.xpath("//input[@name='user.lastName']"),
                        "поле \"Фамилия\" в шаге \"" + contactsStep.getName() + "\" в чекауте");
            }

            static ElementData lastNameInputFieldErrorText() {
                return new ElementData(By.xpath("//input[@name='user.lastName']//following-sibling::div[@class='checkout-input-error']//text()"),
                        "текст ошибки поля \"Фамилия\" в шаге \"" + contactsStep.getName() + "\" в чекауте");
            }

            static ElementData emailInputField() {
                return new ElementData(By.xpath("//input[@name='user.email']"),
                        "поле \"Email\" в шаге \"" + contactsStep.getName() + "\" в чекауте");
            }

            static ElementData emailInputFieldErrorText() {
                return new ElementData(By.xpath("//input[@name='user.email']//following-sibling::div[@class='checkout-input-error']//text()"),
                        "текст ошибки поля \"Фамилия\" в шаге \"" + contactsStep.getName() + "\" в чекауте");
            }

            static ElementData phonesTitle() {
                return new ElementData(By.xpath("//div[text()='Телефон для sms оповещений']"),
                        "подзаголовок \"Телефон для sms оповещений\" в шаге \"" + contactsStep.getName() + "\" в чекауте");
            }

            //todo phoneInputFieldErrorText()

            static ElementData phoneInputField() {
                return new ElementData(By.xpath("//input[@name='phone.phoneNumber']"),
                        "поле \"Телефон\" в шаге \"" + contactsStep.getName() + "\" в чекауте");
            }

            interface Phones {

                interface TopEntry {

                    static ElementData panel() {
                        return new ElementData(By.xpath("//div[text()='Телефон для sms оповещений']//following-sibling::div[@class='phone-selector']//div[contains(@class,'checkout-btn')]"),
                                "панель верхнего добавленного телефона в шаге \"" + contactsStep.getName() + "\" в чекауте");
                    }

                    static ElementData icon() {
                        return new ElementData(By.xpath("//div[text()='Телефон для sms оповещений']//following-sibling::div[@class='phone-selector']//div[contains(@class,'checkout-btn')]//*[@class='checkout-selector-item__ico']"),
                                "иконка верхнего добавленного телефона в шаге \"" + contactsStep.getName() + "\" в чекауте");
                    }

                    static ElementData number() {
                        return new ElementData(By.xpath("//div[text()='Телефон для sms оповещений']//following-sibling::div[@class='phone-selector']//div[contains(@class,'checkout-btn')]//*[@class='checkout-selector-item__content']"),
                                "номер верхнего добавленного телефона в шаге \"" + contactsStep.getName() + "\" в чекауте");
                    }

                    static ElementData changeButton() {
                        return new ElementData(By.xpath("//div[text()='Телефон для sms оповещений']//following-sibling::div[@class='phone-selector']//div[contains(@class,'checkout-btn')]//*[@class='checkout-link' and text()='Изменить']"),
                                "кнопка \"Изменить\" верхнего добавленного телефона в шаге \"" + contactsStep.getName() + "\" в чекауте");
                    }
                }

                interface ActiveEntry {

                    static ElementData panel() {
                        return new ElementData(By.xpath("//div[text()='Телефон для sms оповещений']//following-sibling::div[@class='phone-selector']//div[contains(@class,'checkout-btn--primary')]"),
                                "панель активного добавленного телефона в шаге \"" + contactsStep.getName() + "\" в чекауте");
                    }

                    static ElementData icon() {
                        return new ElementData(By.xpath("//div[text()='Телефон для sms оповещений']//following-sibling::div[@class='phone-selector']//div[contains(@class,'checkout-btn--primary')]//*[@class='checkout-selector-item__ico']"),
                                "иконка активного добавленного телефона в шаге \"" + contactsStep.getName() + "\" в чекауте");
                    }

                    static ElementData number() {
                        return new ElementData(By.xpath("//div[text()='Телефон для sms оповещений']//following-sibling::div[@class='phone-selector']//div[contains(@class,'checkout-btn--primary')]//*[@class='checkout-selector-item__content']"),
                                "номер активного добавленного телефона в шаге \"" + contactsStep.getName() + "\" в чекауте");
                    }

                    static ElementData changeButton() {
                        return new ElementData(By.xpath("//div[text()='Телефон для sms оповещений']//following-sibling::div[@class='phone-selector']//div[contains(@class,'checkout-btn--primary')]//*[@class='checkout-link' and text()='Изменить']"),
                                "кнопка \"Изменить\" активного добавленного телефона в шаге \"" + contactsStep.getName() + "\" в чекауте");
                    }
                }

                interface NotActiveEntry {

                    static ElementData panel() {
                        return new ElementData(By.xpath("//div[text()='Телефон для sms оповещений']//following-sibling::div[@class='phone-selector']//div[contains(@class,'checkout-btn--secondary')]"),
                                "панель неактивного добавленного телефона в шаге \"" + contactsStep.getName() + "\" в чекауте");
                    }

                    static ElementData icon() {
                        return new ElementData(By.xpath("//div[text()='Телефон для sms оповещений']//following-sibling::div[@class='phone-selector']//div[contains(@class,'checkout-btn--secondary')]//*[@class='checkout-selector-item__ico']"),
                                "иконка неактивного добавленного телефона в шаге \"" + contactsStep.getName() + "\" в чекауте");
                    }

                    static ElementData number() {
                        return new ElementData(By.xpath("//div[text()='Телефон для sms оповещений']//following-sibling::div[@class='phone-selector']//div[contains(@class,'checkout-btn--secondary')]//*[@class='checkout-selector-item__content']"),
                                "номер неактивного добавленного телефона в шаге \"" + contactsStep.getName() + "\" в чекауте");
                    }

                    static ElementData changeButton() {
                        return new ElementData(By.xpath("//div[text()='Телефон для sms оповещений']//following-sibling::div[@class='phone-selector']//div[contains(@class,'checkout-btn--secondary')]//*[@class='checkout-link' and text()='Изменить']"),
                                "кнопка \"Изменить\" неактивного добавленного телефона в шаге \"" + contactsStep.getName() + "\" в чекауте");
                    }
                }

                static ElementData addNewPhoneButton() {
                    return new ElementData(By.xpath("//div[contains(@class,'heckout-btn') and text()='+ Добавить новый телефон']"),
                            "кнопка \"Добавить новый телефон\" в шаге \"" + contactsStep.getName() + "\" в чекауте");
                }

                interface Modal {

                    static ElementData popup() {
                        return new ElementData(
                                By.xpath("//div[@class='modal-form__title' and contains(text(),'телефон')]//ancestor::div[@class='rc-modal__container']"),
                                "модалка телефона в чекауте");
                    }

                    static ElementData closeButton() {
                        return new ElementData(
                                By.xpath("//button[@class='rc-modal__close']"),
                                "крестик закрытия модалки телефона в чекауте");
                    }

                    static ElementData title() {
                        return new ElementData(
                                By.xpath("//div[@class='modal-form__title'"),
                                "заголовок модалки телефона в чекауте");
                    }

                    static ElementData inputField() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//input[@id='phone-input']"),
                                "поле ввода номера телефона в модалке телефона в чекауте");
                    }

                    static ElementData errorText() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//div[@class='checkout-input-error']"),
                                "текст ошибки в модалке телефона в чекауте");
                    }

                    static ElementData deleteButton() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//button[text()='Удалить']"),
                                "кнопка удаления номера телефона в модалке телефона в чекауте"
                        );
                    }

                    static ElementData cancelButton() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//button[text()='Отменить']"),
                                "кнопка отмены ввода телефона в модалке телефона в чекауте"
                        );
                    }

                    static ElementData submitButton() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//button[text()='Сохранить']"),
                                "кнопка сохранения в модалке телефона в чекауте");
                    }

                }
            }

            //todo agreement

            static ElementData sendEmailsCheckbox() {
                return new ElementData(By.xpath("//span[text()='Отправлять информацию о заказе на email']//preceding::input[@name='order[send_emails]']//following-sibling::label//*[@class='checkbox__check']"),
                        "чекбокс \"Отправлять информацию о заказе на email\" в шаге \"" + contactsStep.getName() + "\" в чекауте");
            }

            static ElementData nextButton() {
                return Checkout.Step.nextButton(contactsStep);
            }

            static ElementData saveButton() {
                return Checkout.Step.saveButton(contactsStep);
            }

            static ElementData changeButton() {
                return Checkout.MinimizedStep.changeButton(contactsStep);
            }
        }

        interface ReplacementsStep {

            CheckoutStepData replacementsStep = replacementsStep();

            static ElementData panel() {
                return Checkout.Step.panel(replacementsStep);
            }

            static ElementData minimizedPanel() {
                return Checkout.MinimizedStep.panel(replacementsStep);
            }

            static ElementData icon() {
                return Checkout.Step.icon(replacementsStep);
            }

            static ElementData title() {
                return Checkout.Step.title(replacementsStep);
            }

            static ElementData nextButton() {
                return Checkout.Step.nextButton(replacementsStep);
            }

            static ElementData saveButton() {
                return Checkout.Step.saveButton(replacementsStep);
            }

            static ElementData changeButton() {
                return Checkout.MinimizedStep.changeButton(replacementsStep);
            }
        }

        interface PaymentStep {

            CheckoutStepData paymentStep = paymentStep();

            static ElementData panel() {
                return Checkout.Step.panel(paymentStep);
            }

            static ElementData minimizedPanel() {
                return Checkout.MinimizedStep.panel(paymentStep);
            }

            static ElementData icon() {
                return Checkout.Step.icon(paymentStep);
            }

            static ElementData title() {
                return Checkout.Step.title(paymentStep);
            }

            static ElementData nextButton() {
                return Checkout.Step.nextButton(paymentStep);
            }

            static ElementData saveButton() {
                return Checkout.Step.saveButton(paymentStep);
            }

            static ElementData changeButton() {
                return Checkout.MinimizedStep.changeButton(paymentStep);
            }
        }

        interface DeliveryStep {

            CheckoutStepData deliveryStep = deliveryStep();

            static ElementData panel() {
                return Checkout.Step.panel(deliveryStep);
            }

            static ElementData minimizedPanel() {
                return Checkout.MinimizedStep.panel(deliveryStep);
            }

            static ElementData icon() {
                return Checkout.Step.icon(deliveryStep);
            }

            static ElementData title() {
                return Checkout.Step.title(deliveryStep);
            }

            static ElementData nextButton() {
                return Checkout.Step.nextButton(deliveryStep);
            }

            static ElementData changeButton() {
                return new ElementData(By.xpath("//div[@class='panel-header__text' and text()='" + deliveryStep.getTitle() + "']//ancestor::div[@class='checkout-panel checkout-panel--active']//*[contains(text(),'Изменить')]"),
                        "кнопка \"Изменить\" заполненного шага \"" + deliveryStep.getName() + "\" в чекауте");
                }
        }

        static ElementData sendOrderButton() {
            return new ElementData(By.xpath("//button[text()='Оформить заказ']"),
                    "кнопка \"Оформить заказ\" в нижнем блоке чекаута");
        }






            static ElementData changeStepButton(int step) {
                return new ElementData("Изменить", By.cssSelector("div.checkout-panel:nth-child(" + step + ") > div:nth-child(1) > div:nth-child(3) > a:nth-child(1)"));
            }

            // Костыльный элемент для 5 шага
            static ElementData changeStep5Button() { //TODO переделать локаторы так, чтобы избавиться от отдельной кнопки для 5 шага
                return new ElementData("Изменить",
                        By.xpath("/html/body/div[3]/div/form/div/div/div[1]/div[5]/div[2]/div/div/div/div[1]/div[3]"));
            }

            static ElementData replacementPolicy(int option) {
                return new ElementData(By.cssSelector("div.replacement-policy:nth-child(" + option + ")"));
            }

            static ElementData paymentTypeSelector(int option) {
                return new ElementData(By.cssSelector("div.payment-method:nth-child(" + option + ") > div:nth-child(1)"));
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

            interface Promocode {

                static ElementData addButton() {
                    return new ElementData(
                            By.xpath("//aside//a[text()='Применить промокод']"),
                                "кнопка \"Применить промокод\" в чекауте");
                }

                static ElementData deleteButton() {
                    return new ElementData(
                            By.xpath("//aside//div[@class='promo-codes']//a[text()='Удалить']"),
                                "кнопка удаления промокода в чекауте");
                }

                interface Modal {

                    static ElementData popup() {
                        return new ElementData(
                                By.xpath("//div[@class='modal-form__title' and text()='Введите промокод']//ancestor::div[@class='rc-modal__container']"),
                                    "модалка ввода промокода в чекауте");
                    }

                    static ElementData closeButton() {
                        return new ElementData(
                                By.xpath("//button[@class='rc-modal__close']"),
                                    "крестик закрытия модалки ввода промокода в чекауте");
                    }

                    static ElementData title() {
                        return new ElementData(
                                By.xpath("//div[@class='modal-form__title' and text()='Введите промокод']"),
                                    "заголовок модалки ввода промокода в чекауте");
                    }

                    static ElementData inputField() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//input[@id='couponCode']"),
                                    "поле ввода промокода");
                    }

                    static ElementData errorText() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//div[@class='checkout-input-error']"),
                                    "текст ошибки применения промокода");
                    }

                    static ElementData cancelButton() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//button[text()='Отменить']"),
                                    "кнопка отмены ввода промокода"
                        );
                    }

                    static ElementData submitButton() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//button[text()='Добавить код']"),
                                    "кнопка \"Добавить код\"");
                    }
                }
            }

            interface Bonuses {

                static ElementData list() {
                    return new ElementData(
                            By.xpath("//aside//div[@class='loyalty-programs']"),
                                "блок бонусных программ в чекауте");
                }

                interface Program {

                    static ElementData snippet(String name) {
                        return new ElementData(
                                By.xpath("//aside//div[contains(@class,'loyalty-program')]//div[@class='loyalty-program__name' and text()='" + name + "']"),
                                "бонусная программа " + name + " в чекауте");
                    }

                    static ElementData activeSnippet(String name) {
                        return new ElementData(
                                By.xpath("//aside//div[contains(@class,'loyalty-program--active')]//div[@class='loyalty-program__name' and text()='" + name + "']"),
                                    "активная бонусная программа " + name + " в чекауте");
                    }

                    static ElementData addButton(String name) {
                        return new ElementData(
                                By.xpath("//aside//div[contains(@class,'loyalty-program')]//div[@class='loyalty-program__name' and text()='" + name + "']//following-sibling::div[text()='Добавить карту']"),
                                    "кнопка добавления бонусной программы " + name + " в чекауте");
                    }

                    static ElementData editButton(String name) {
                        return new ElementData(
                                By.xpath("//aside//div[contains(@class,'loyalty-program')]//div[@class='loyalty-program__name' and text()='" + name + "']//ancestor::div[contains(@class,'loyalty-program')]//div[@class='loyalty-program__edit']"),
                                    "кнопка редактирования бонусной программы " + name + " в чекауте");
                    }

                    static ElementData logo(String name) {
                        return new ElementData(
                                By.xpath("//aside//div[contains(@class,'loyalty-program')]//div[@class='loyalty-program__name' and text()='" + name + "']//ancestor::div[contains(@class,'loyalty-program')]//div[@class='loyalty-program__logo']"),
                                "кнопка редактирования бонусной программы " + name + " в чекауте");
                    }
                }

                interface Modal {

                    static ElementData popup() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__container']"),
                                    "модалка бонусной программы в чекауте");
                    }

                    static ElementData closeButton() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__container']//button[@class='rc-modal__close']"),
                                    "крестик закрытия модалки бонусной программы в чекауте");
                    }

                    static ElementData title() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__container']//div[@class='modal-form__title']"),
                                    "заголовок модалки бонусной программы в чекауте");
                    }

                    static ElementData inputField() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//input[@placeholder='Номер карты']"),
                                "поле ввода номера карты в модалке бонусной программы в чекауте");
                    }

                    static ElementData errorText() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//div[@class='checkout-input-error']"),
                                    "текст ошибки в модалке бонусной программы в чекауте");
                    }

                    static ElementData deleteButton() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//button[text()='Удалить']"),
                                "кнопка \"Удалить\" в модалке бонусной программы в чекауте"
                        );
                    }

                    static ElementData cancelButton() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//button[text()='Отменить']"),
                                    "кнопка \"Отменить\" в модалке бонусной программы в чекауте"
                        );
                    }

                    static ElementData saveButton() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//button[text()='Сохранить']"),
                                    "кнопка \"Сохранить\" в модалке бонусной программы в чекауте");
                    }
                }
            }

            interface SideBar {

                static ElementData panel() {
                    return new ElementData(
                            By.xpath("//aside[@class='checkout-sidebar']"),
                            "боковая колонка чекаута");
                }

                static ElementData itemsTotal() {
                    return new ElementData(
                            By.xpath("//aside//*[text()='Товары']//ancestor::div[@class='checkout-summary__subtotal']//span//div"),
                                "сумма \"Товары\" в боковой колонке чекаута");
                }

                static ElementData discount() {
                    return new ElementData(
                            By.xpath("//aside//*[text()='Скидки и акции']//ancestor::div[@class='checkout-summary__subtotal']//span//div"),
                                "сумма скидки в боковой колонке чекаута");
                }

                static ElementData deliveryPrice() {
                    return new ElementData(
                            By.xpath("//aside//*[text()='Доставка']//ancestor::div[@class='checkout-summary__subtotal']//span//div"),
                                "стоимость доставки в боковой колонке чекаута");
                }

                static ElementData total() {
                    return new ElementData(
                            By.xpath("//aside//div[@class='checkout-summary__total']//div//div"),
                                "сумма \"Итого\" в боковой колонке чекаута");
                }

                static ElementData sendOrderButton() {
                    return new ElementData(
                            By.xpath("//aside//button[text()='Оформить заказ']"),
                                "кнопка \"Оформить заказ\" в боковой колонке чекаута");
                }
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
    public interface Administration {

        interface LoginPage {

            static ElementData title() {
                return new ElementData(By.xpath("//h1[text()='Вход']"),
                        "заголовок 'Вход' на странице авторизации админки");
            }

            static ElementData emailField() {
                return new ElementData(By.xpath("//input[@name='email']"),
                        "поле ввода email на странице авторизации админки");
            }

            // todo emailFieldErrorText

            static ElementData passwordField() {
                return new ElementData(By.xpath("//input[@name='password']"),
                        "поле ввода пароля на странице авторизации админки");
            }

            // todo passwordFieldErrorText

            static ElementData submitButton() {
                return new ElementData(By.xpath("//span[text()='Войти']//ancestor::button[@type='submit']"),
                        "кнопка 'Войти' на странице авторизации админки");
            }
        }

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

            static ElementData userEmail() {
                return new ElementData(By.xpath("//body[@class='admin']//div[@class='admin-login-navigation-bar__email']"),
                        "email авторизованного пользователя в шапке админки");
            }

            static ElementData logoutButton() {
                return new ElementData(By.xpath("//body[@class='admin']//a[@class='admin-login-navigation-bar__logout']"),
                        "кнопка 'Выйти' в шапке админки");
            }
        }

        /** Раздел Shipments в админке */
        interface ShipmentsSection {

            static ElementData title() {
                return new ElementData(
                        By.xpath("//h1[@class='page-title ' and contains(text(),'Список заказов')]"),
                            "заголовок на странице поиска заказов");
            }

            /** Фильтры заказов */
            interface Filters {

                static ElementData orderDateFrom() {
                    return new ElementData(
                            By.xpath("//label[text()='Дата заказа']//following-sibling::div//input[@id='search_completed_at_st']"),
                                "фильтр \"Дата заказа - Начало\" на странице поиска заказов");
                }

                static ElementData orderDateTo() {
                    return new ElementData(
                            By.xpath("//label[text()='Дата заказа']//following-sibling::div//input[@id='search_completed_at_end']"),
                                "фильтр \"Дата заказа - Конец\" на странице поиска заказов");
                }

                static ElementData customerName() {
                    return new ElementData(
                            By.xpath("//label[text()='Имя']//following-sibling::input[@id='search_first_name']"),
                                "фильтр \"Имя\" на странице поиска заказов");
                }

                static ElementData customerSurname() {
                    return new ElementData(
                            By.xpath("//label[text()='Фамилия']//following-sibling::input[@id='search_last_name']"),
                                "фильтр \"Фамилия\" на странице поиска заказов");
                }

                static ElementData juridicalNameContains() {
                    return new ElementData(
                            By.xpath("//label[text()='Юридическое лицо содержит']//following-sibling::input[@id='search_order_company_name']"),
                                "фильтр \"Юридическое лицо содержит\" на странице поиска заказов");
                }

                static ElementData shopperLogin() {
                    return new ElementData(
                            By.xpath("//label[text()='Логин сборщика']//following-sibling::input[@id='search_shopper_login']"),
                                "фильтр \"Логин сборщика\" на странице поиска заказов");
                }

                static ElementData driverLogin() {
                    return new ElementData(
                            By.xpath("//label[text()='Логин водителя']//following-sibling::input[@id='search_driver_login']"),
                                "фильтр \"Логин водителя\" на странице поиска заказов");
                }

                static ElementData deliveryTimeFrom() {
                    return new ElementData(
                            By.xpath("//label[text()='Дата и время доставки']//following-sibling::div//input[@id='search_delivery_window_starts_at_st']"),
                                "фильтр \"Дата и время доставки - От\n\" на странице поиска заказов");
                }

                static ElementData deliveryTimeTo() {
                    return new ElementData(
                            By.xpath("//label[text()='Дата и время доставки']//following-sibling::div//input[@id='search_delivery_window_starts_at_end']"),
                                "фильтр \"Дата и время доставки - До\" на странице поиска заказов");
                }

                static ElementData phoneNumberContains() {
                    return new ElementData(
                            By.xpath("//label[text()='Телефон содержит']//following-sibling::input[@id='search_order_phone']"),
                                "фильтр \"Телефон содержит\" на странице поиска заказов");
                }

                static ElementData email() {
                    return new ElementData(
                            By.xpath("//label[text()='Электронная почта']//following-sibling::input[@id='search_email']"),
                                "фильтр \"Электронная почта\" на странице поиска заказов");
                }

                static ElementData innNumber() {
                    return new ElementData(
                            By.xpath("//label[text()='ИНН']//following-sibling::input[@id='search_inn']"),
                                "фильтр \"ИНН\" на странице поиска заказов");
                }

                static ElementData invoiceNumber() {
                    return new ElementData(
                            By.xpath("//label[text()='Номер накладной']//following-sibling::input[@id='search_invoice_number']"),
                                "фильтр \"Номер накладной\" на странице поиска заказов");
                }

                static ElementData orderNumber() {
                    return new ElementData(
                            By.xpath("//label[text()='Заказ']//following-sibling::input[@id='search_number']"),
                                "фильтр \"Заказ\" на странице поиска заказов");
                }

                static ElementData orderStatus() {
                    return new ElementData(
                            By.xpath("//label[text()='Статус']//following-sibling::div[@id='s2id_search_state']"),
                                "фильтр \"Статус\" на странице поиска заказов");
                }

                static ElementData retailer() {
                    return new ElementData(
                            By.xpath("//label[text()='Ритейлер']//following-sibling::div[@id='s2id_search_retailer_id']"),
                                "фильтр \"Ритейлер\" на странице поиска заказов");
                }

                static ElementData store() {
                    return new ElementData(
                            By.xpath("//label[text()='Магазин сборки']//following-sibling::div[@id='s2id_search_store_id']"),
                                "фильтр \"Магазин сборки\" на странице поиска заказов");
                }

                static ElementData paymentMethod() {
                    return new ElementData(
                            By.xpath("//label[text()='Способ оплаты']//following-sibling::div[@id='s2id_search_payment_method_id']"),
                                "фильтр \"Способ оплаты\" на странице поиска заказов");
                }

                static ElementData paymentStatus() {
                    return new ElementData(
                            By.xpath("//label[text()='Состояние оплаты']//following-sibling::div[@id='s2id_search_payment_state']"),
                                "фильтр \"Состояние оплаты\" на странице поиска заказов");
                }

                static ElementData promocode() {
                    return new ElementData(
                            By.xpath("//label[text()='Промокод']//following-sibling::input[@id='search_coupon_code']"),
                                "фильтр \"Промокод\" на странице поиска заказов");
                }

                static ElementData itemsFrom() {
                    return new ElementData(
                            By.xpath("//label[text()='Число позиций']//following-sibling::div//input[@id='search_item_count_st']"),
                                "фильтр \"Число позиций - От\" на странице поиска заказов");
                }

                static ElementData itemsTo() {
                    return new ElementData(
                            By.xpath("//label[text()='Число позиций']//following-sibling::div//input[@id='search_item_count_end']"),
                                "фильтр \"Число позиций - До\" на странице поиска заказов");
                }

                static ElementData weightFrom() {
                    return new ElementData(
                            By.xpath("//label[text()='Вес, кг.']//following-sibling::div//input[@id='search_total_weight_st']"),
                                "фильтр \"Вес, кг. - От\" на странице поиска заказов");
                }

                static ElementData weightTo() {
                    return new ElementData(
                            By.xpath("//label[text()='Вес, кг.']//following-sibling::div//input[@id='search_total_weight_end']"),
                                "фильтр \"Вес, кг. - До\" на странице поиска заказов");
                }
            }

            /** Чекбоксы фильтации заказов */
            interface Checkboxes {

                static ElementData completedOnly() {
                    return new ElementData(By.xpath("//input[@id='search_only_completed']"),
                            "чекбокс \"Показывать только завершенные заказы\" на странице поиска заказов");
                }

                static ElementData b2bOnly() {
                    return new ElementData(By.xpath("//input[@id='search_only_b2b']"),
                            "чекбокс \"Только B2B клиенты\" на странице поиска заказов");
                }

                static ElementData metroOnly() {
                    return new ElementData(By.xpath("//input[@id='search_tenant']"),
                            "чекбокс \"Только заказы с сайта метро\" на странице поиска заказов");
                }

                static ElementData deliveryChangedOnly() {
                    return new ElementData(By.xpath("//input[@id='search_delivery_window_changed']"),
                            "чекбокс \"Только с переносом времени доставки\" на странице поиска заказов");
                }
            }

            static ElementData applyFilterButton() {
                return new ElementData(By.xpath("//button[@class='icon-search button']"),
                        "кнопка \"Применить фильтр\"");
            }

            static ElementData clearFilterButton() {
                return new ElementData(By.xpath("//a[@class='button icon-remove']"),
                        "кнопка \"Сбросить фильтр\"");
            }

            static ElementData placeholder() {
                return new ElementData(
                        By.xpath("//div[@class='no-objects-found']//span[text()='No Orders Found']"),
                            "плейсхолдер пустого результата поиска на странице поиска заказов");
            }

            static ElementData table() {
                return new ElementData(
                        By.xpath("//table[@id='listing_orders']"),
                        "таблица с заказами на странице поиска заказов");
            }

            static ElementData tableTitle(String name) {
                return new ElementData(
                        By.xpath("//table[@id='listing_orders']//th[text()='" + name + "']"),
                        "заголовок \"" + name + "\" в таблице с заказами на странице поиска заказов");
            }

            interface shipmentRow {
                // TODO
            }

            // TODO remove
            static ElementData firstOrderInTable() {
                return new ElementData(By.xpath("//*[@id='listing_orders']/tbody/tr/td[14]/a"));
            }

            // TODO remove
            static ElementData firstShipmentNumberInTable() {
                return new ElementData(By.xpath("//*[@id='listing_orders']/tbody/tr/td[1]/span[1]/a"));
            }

            // TODO remove
            static ElementData firstOrderNumberInTable() {
                return new ElementData(By.xpath("//*[@id='listing_orders']/tbody/tr/td[1]/span[2]/a"));
            }

            // TODO Update locators
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

                    static ElementData cancellationReasonField() {
                        return new ElementData(
                                By.id("cancellation_reason_details"),
                                "поле ввода причины отмены заказа");
                    }

                }

                // TODO Update locators
                /** Страница деталей оплаты заказа в админке */
                interface Payments {

                    static ElementData paymentType() {
                        return new ElementData(By.xpath("//div[@id='wrapper']//td[5]/a"));
                    }
                }

                // TODO Update locators
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

        // TODO Update locators
        /** Раздел Users в админке */
        interface UsersSection {

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

            // TODO Update locators
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

        /** Раздел статических страниц в админке **/
        interface PagesSection {

            static ElementData newPageButton() {
                return new ElementData(
                        By.xpath("//a[@class='button icon-plus']"), "кнопка \"Новая страница\"");
            }

            static ElementData table() {
                return new ElementData(By.xpath("//table"), "таблица со списком страниц");
            }

            static ElementData tableEntry(String name) {
                return new ElementData(
                        By.xpath("//table//tbody//td[contains(text(),'" + name + "')]"),
                        "запись в таблице \" + name + \"");
            }

            static ElementData editPageButton(String name) {
                return new ElementData(
                        By.xpath("//table//tbody//td[contains(text(),'" + name + "')]//following-sibling::td[@class='actions']//a[contains(@class,'icon-edit')]"),
                        "кнопка редактирования записи \" + name + \" в таблице");
            }

            static ElementData deletePageButton(String name) {
                return new ElementData(
                        By.xpath("//table//tbody//td[contains(text(),'" + name + "')]//following-sibling::td[@class='actions']//a[contains(@class,'icon-trash')]"),
                        "кнопка удаления записи \" + name + \" в таблице");
            }

            interface PageEditPage {

                static ElementData pageNameField() {
                    return new ElementData(
                            By.xpath("//*[@id='page_title']"), "поле названия страницы");
                }

                static ElementData pageURLField() {
                    return new ElementData(
                            By.xpath("//*[@id='page_slug']"), "поле ссылки на страницу");
                }

                static ElementData pageDescriptionSourceButton() {
                    return new ElementData(
                            By.xpath("//a[@id='cke_13']"), "переключение на plain text в html-поле описания");
                }

                static ElementData pageDescriptionField() {
                    return new ElementData(
                            By.xpath("//*[@id='cke_1_contents']/textarea"), "поле описания");
                }

                static ElementData savePageButton() {
                    return new ElementData(
                            By.xpath("//*[@class='icon-refresh button']"), "кнопка \"изменить\"");
                }

            }
        }
    }

    /** Статические страницы **/
    public interface StaticPages{

        static ElementData pageTitle() {
            return new ElementData(By.xpath("//*[@class='inner-page__title']/h1"),
                    "заголовок статической страницы");
        }
    }

    public interface PasswordRecovery {

        interface RequestModal {

        }

        interface RecoveryModal {

            static ElementData passwordField() {
                return new ElementData(
                        By.name("password"),
                            "поле 'Пароль' в модалке восстановления пароля");
            }

            static ElementData passwordConfirmationField() {
                return new ElementData(
                        By.name("passwordConfirmation"),
                            "поле 'Подтверждение пароля' в модалке восстановления пароля");
            }

            static ElementData submitButton() {
                return new ElementData(
                        By.className("auth-modal__button"),
                            "кнопка отправки формы в модалке восстановления пароля");
            }
        }
    }

    public interface GMail {

        interface AuthPage{

            static ElementData idField() {
                return new ElementData(
                        By.name("identifier"),
                        "поле 'id' на странице авторизации Gmail");
            }

            static ElementData idNextButton() {
                return new ElementData(
                        By.name("identifier"),
                        "кнопка 'Далее' на странице ввода id авторизации Gmail");
            }

            static ElementData passwordField() {
                return new ElementData(
                        By.name("password"),
                        "поле 'Пароль' на странице авторизации Gmail");
            }

            static ElementData passwordNextButton() {
                return new ElementData(
                        By.id("passwordNext"),
                        "кнопка 'Далее' на странице ввода пароля авторизации Gmail");
            }
        }
    }
}
