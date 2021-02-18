package instamart.ui.objectsmap;

import instamart.ui.common.lib.CheckoutSteps;
import instamart.ui.common.pagesdata.JuridicalData;
import instamart.ui.common.pagesdata.PaymentCardData;
import org.openqa.selenium.By;

import instamart.ui.common.pagesdata.ElementData;
import instamart.ui.common.pagesdata.CheckoutStepData;

import static instamart.core.testdata.TestVariables.CompanyParams.*;

public class Elements {

    public static ElementData spinner() {
        return new ElementData(
                By.xpath("//div[@class='spinner-container']"),
                    "спиннер загрузки");
    }

    public static ElementData none() {
        return new ElementData(
                By.xpath("//*[@id='nowhere']"),"none");
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

        static ElementData quote() {
            return new ElementData(By.xpath("//blockquote"),
                        "цитата котомудрости на странице 404");
        }

        static ElementData quote(int quoteId) {
            return new ElementData(By.xpath("//div[@data-id='"+quoteId+"']//blockquote"),
                    "цитата котомудрости на странице 404 под номером " + quoteId);
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
    public interface Landings {

        interface SbermarketLanding {

            interface Header {

                static ElementData container() {
                    return new ElementData(By.xpath("//header"),
                            "шапка лендинга Сбермаркета");
                }

                static ElementData logo() {
                    return new ElementData(By.xpath("//header//*[contains(@class,'home-logo')]"),
                            "логотип Сбермаркета в шапке лендинга");
                }

                static ElementData loginButton() {
                    return new ElementData(By.xpath("//header//button[text()='Войти']"),
                            "кнопка входа в шапке лендинга Сбермаркета");
                }
            }

            interface MainBlock{

                static ElementData container() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing']"),
                            "главный блок лендинга Сбермаркета");
                }

                static ElementData illustration() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing']//div[contains(@class,'mainImg')]"),
                            "главная иллюстрация лендинга Сбермаркета");
                }

                static ElementData addressButton() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing']//*[contains(text(),'Указать адрес доставки')]"),
                            "кнопка с указанием адреса доставки на лендинге");
                }

                static ElementData text() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing_description']"),
                            "текст лендинга Сбермаркета");
                }

                interface Stores {

                    static ElementData list() {
                        return new ElementData(By.xpath("//*[@data-qa='home_landing_stores']//*[contains(text(),'Наши партнёры')]"),
                                "блок со списком магазинов на лендинге Сбермаркета");
                    }

                    static ElementData button(int position) {
                        return new ElementData(By.xpath("//div[contains(@class,'stores__store')]["+position+"]//button[contains(@class,'stores__storeCard')]"),
                                "кнопка " + position + " магазина на лендинге Сбермаркета");
                    }

                    static ElementData buttonAuchan() {
                        return new ElementData(By.xpath("//*[@data-qa='home_landing_store_image_72']"),
                                "кнопка выбора магазина Ашан");
                    }

                    static ElementData buttonMetro() {
                        return new ElementData(By.xpath("//*[@data-qa='home_landing_store_image_1']"),
                                "кнопка выбора магазина Метро");
                    }

                    static ElementData homeLanding(){
                        return new ElementData(By.xpath("//div[contains(@class,'home_landing')]"),
                                "главный лендинг сбермаркета");
                    }
                }
            }

            interface AdvantagesBlock {

                static ElementData container() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing_advantages']"),
                            "блок преимуществ на лендинге Сбермаркета");
                }

                static ElementData deliveryAdv() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing_advantage_delivery']"),
                            "преимущества быстрой доставки");
                }

                static ElementData heavyToDoorAdv() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing_advantage_door']"),
                            "преимущества доставки тяжелых товаров до двери");
                }

                static ElementData goodQualityAdv() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing_advantage_best']"),
                            "преимущества доставки товаров высокого качества");
                }

                static ElementData saleAdv() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing_advantage_sale']"),
                            "преимущества получения скидок от партнеров на большое количество товаров");
                }
            }

            interface ZonesBlock {

                static ElementData container() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing_cities']"),
                            "блок зон доставки на лендинге Сбермаркета");
                }

                static ElementData showAllButton() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing_show_button' and text()='Показать все']"),
                            "кнопка показать все города, где работает сбермаркет");
                }
            }

            interface OrderBlock {

                static ElementData container() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing_steps']" +
                            "//*[text()='СберМаркет сходит в магазин вместо вас']"),
                            "блок механики заказа на лендинге Сбермаркета");
                }

                static ElementData stepFirst() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing_step_first']" +
                            "//*[text()='выберите удобное время доставки заказа']"),
                            "первый шаг заказа на лендинге Сбермаркета");
                }

                static ElementData stepSecond() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing_step_second']" +
                            "//*[contains(text(),'Специалист по закупкам соберет')]"),
                            "второй шага заказа на лендинге Сбермаркета");
                }

                static ElementData stepThird() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing_step_third']" +
                            "//*[contains(text(),'Курьер привезет продукты')]"),
                            "третий шаг заказа на лендинге Сбермаркета");
                }
            }

            interface AppsBlock {

                static ElementData container() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing_app_stores']"),
                            "блок моб. приложений на лендинге Сбермаркета");
                }

                static ElementData appStoreButton() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing_google_play_app_container']"),
                            "кнопка перехода в AppStore на лендинге Сбермаркета");
                }

                static ElementData googlePlayButton() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing_app_store_app_container']"),
                            "кнопка перехода в Google Play на лендинге Сбермаркета");
                }
                static ElementData huaweiStoreButton() {
                    return new ElementData(By.xpath("//*[@data-qa='home_landing_huawei_store_app_container']"),
                            "кнопка перехода в Huawei Store на лендинге Сбермаркета");
                }
            }
        }

        interface ReferralLanding {
            // TODO
        }

        interface MassHiringLanding {
            // TODO
        }

        interface driversHiringLanding {

            interface DriverJobForm{

                static ElementData submitButton() {
                    return new ElementData(
                            By.xpath("//button[@name='btnSubmit']"),
                            "кнопка отправки заявки на лендинге найма водителей Сбермаркета");
                }

                static ElementData nameField() {
                    return new ElementData(
                            By.xpath("//input[@name='full_name']"),
                            "поле для ввода имени и фамилии");
                }

                static ElementData citySelector() {
                    return new ElementData(
                            By.xpath("//span[text()='Владивосток']"),
                            "селектор с городами");
                }

                static ElementData countrySelector() {
                    return new ElementData(
                            By.xpath("//span[text()='РФ']"),
                            "селектор с доступными гражданствами");
                }

                static ElementData phoneField() {
                    return new ElementData(
                            By.xpath("//input[@name='phone_number']"),
                            "поле для ввода номера телефона водителя");
                }
            }

            static ElementData workConditions() {
                return new ElementData(
                        By.xpath("//div[@class='driverJob__middleBlock']"),
                        "блок с условиями работы");
            }

            static ElementData howToJoin() {
                return new ElementData(
                        By.xpath("//h2[contains(text(),'команде просто')]"),
                        "блок с описанием процесса трудоустройства");
            }

            static ElementData whatToDo() {
                return new ElementData(
                        By.xpath("//div[text()='Что нужно делать?']"),
                        "блок с кратким описанием обязанностей");
            }

        }

        interface sberAppPromoLanding {

            static ElementData submitButton() {
                return new ElementData(
                        By.xpath("//button[@class='submit' and text()='Отправить SMS клиенту']"),
                            "кнопка отправки смс на промо-лендинге приложения Сбермаркета");
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
                        By.xpath("//header//*[text()='Выберите адрес доставки']"),
                            "плейсхолдер пустого адреса доставки в шапке сайта");
            }

            static ElementData shipAddressButton() {
                return new ElementData(
                        By.xpath("//header//*[@data-qa='ship-address-selector']//*[@data-qa='select-button']"),
                            "кнопка выбора/изменения адреса доставки в шапке сайта");
            }

            static ElementData currentShipAddress() {
                return new ElementData(
                        By.xpath("//header//*[@data-qa='current-ship-address']"),
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
                        By.xpath("//header//a[contains(@class,'logo')]"),
                            "логотип компании в шапке сайта");
            }

            static ElementData infoButton(String name) {
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

            static ElementData forBusinessButton() {
                return infoButton("\uD83D\uDC69\u200D\uD83D\uDCBB Для бизнеса");
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
                        By.xpath("//header//*[@data-qa='login-button']"),
                        "кнопка входа в шапке сайта");
            }

            static ElementData favoritesButton() {
                return new ElementData(
                        By.xpath("//header//*[@data-qa='favorites-link']"),
                            "кнопка избранного в шапке сайта");
            }

            static ElementData profileButton() {
                return new ElementData(
                        By.xpath("//*[@data-qa='profile-button']"),
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
                    return new ElementData(By.xpath("//header//*[@data-qa='search']"),
                            "поисковая строка в шапке сайта");
                }

                static ElementData inputField() {
                    return new ElementData(By.xpath("//header//*[@data-qa='search']//input"),
                            "поле ввода поискового запроса в шапке сайта");
                }

                static ElementData sendButton() {
                    return new ElementData(By.xpath("//header//*[@data-qa='search']//button"),
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
            return new ElementData(By.id("new-home-footer"),
                    "блок футера");
        }

        static ElementData info() {
            return new ElementData(By.xpath("//div[@class='footer-info']"),
                    "сео-инфоблок над футером");
        }

        static ElementData instamartLogo() {
            return new ElementData(By.xpath("//footer//div[@class='footer__logo']"),
                    "логотип 'Сбермаркет' в футере");
        }

        static ElementData infoLink(String name) {
            return new ElementData(By.xpath("//footer//*[@class='footer__link' and text()='" + name + "']"),
                    "ссылка '" + name + "' в футере");
        }

        static ElementData sbermarketTitle() {
            return new ElementData(By.xpath("//footer//div[@class='footer__title' and text()='СберМаркет']"),
                    "подзаголовок 'Сбермаркет' в футере");
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
                    "подзаголовок 'Помощь покупателю' в футере");
        }

        static ElementData deliveryButton() {
            return infoLink("Доставка и оплата");
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
            return new ElementData(By.xpath("//footer//div[@class='footer__networks']//a[@href='" + companyFacebookLink + "']"),
                    "кнопка Facebook в футере");
        }

        static ElementData vkontakteButton() {
            return new ElementData(By.xpath("//footer//div[@class='footer__networks']//a[@href='" + companyVkontakteLink + "']"),
                    "кнопка Вконтакте в футере");
        }

        static ElementData instagramButton() {
            return new ElementData(By.xpath("//footer//div[@class='footer__networks']//a[@href='" + companyInstagramLink + "']"),
                    "кнопка Instagram в футере");
        }

        static ElementData huaweiButton() {
            return new ElementData(By.xpath("//*[@data-qa='home_landing_huawei_store_footer']"),
                    "кнопка huaweiStore в футере");
        }

        static ElementData appstoreButton() {
            return new ElementData(By.xpath("//*[@data-qa='home_landing_app_store_footer']"),
                    "кнопка Appstore в футере");
        }

        static ElementData googlePlayButton() {
            return new ElementData(By.xpath("//*[@data-qa='home_landing_google_play_footer']"),
                    "кнопка GooglePlay в футере");
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
            return new ElementData(By.xpath("//footer//a[contains(@class,'footer__link') and text()='Официальное уведомление']"),
                    "ссылка на Официальное уведомление в футере");
        }
    }

    /** Модалки */
    public interface Modals {

        static ElementData fade() {
            return new ElementData(By.xpath("//div[@class='frame fade-enter-done']"),
                    "тень модалок");
        }

        /** Модалка авторизации-регистрации */
        interface AuthModal {

            //todo очень плохо, что сейчас приходится в xpath менять имя рута много раз
            // нужно вынести все руты в один элемент и наследоваться от него
            // создам задачку на data-qa атрибут это сильно повысит стабильность автотестов на модалке например его нет
        static ElementData popup() {
            return new ElementData(By.xpath("//*[contains(@class,'auth')]"),
                    "поп-ап модалки авторизации");
        }
        static ElementData promoModalButton(){
            return new ElementData(By.xpath("//*[contains(@class,'free_delivery_promo__info')]//button"),
                    "поп-ап с рекламой мешающий авторизации/регистрации пользователя");
        }

        static ElementData expressDelivery(){
            return new ElementData(By.xpath("//div[contains(@class,'wrapper')]//button[contains(@aria-label,'Закрыть')]"),
                    "поп-ап с рекламой экспресс доставки");
        }

        static ElementData popupMail() {
            return new ElementData(By.xpath("//*[contains(@class,'auth')]"),
                    "поп-ап модалки авторизации");
        }
       /* static ElementData popupMail() {
            return new ElementData(By.xpath("//*[@class='wrapper_2y0mH']"),
                    "поп-ап модалки авторизации");
        }
        */

        static ElementData phoneNumber() {
            return new ElementData(By.xpath("//div[contains(@class,'FormGroup__root')]"),
                    "поле для ввода номера телефона");
        }

        static ElementData smsCode() {
            return new ElementData(By.xpath("//input[@data-qa='sms_confirm_input']"),
                    "поле для ввода кода из смс");
        }

        // Старое значение модалки auth-modal пусть пока тут поживет, проверим как часто это значение меняется
        static ElementData closeButton(){
            return new ElementData(By.xpath("//*[contains(@class,'wrapper')]//button[@class='modal-wrapper__close']"),
                    "крестик закрытия модалки авторизации");
        }

        static ElementData authorisationTab() {
            return new ElementData(By.xpath("//*[contains(@class,'wrapper')]//button[text()='Вход']"),
                    "кнопка переключения на вкладку авторизации в модалке авторизации");
        }

        static ElementData registrationTab() {
            return new ElementData(By.xpath("//*[@class='auth-modal__form']//button[text()='Регистрация']"),
                    "кнопка переключения на вкладку регистрации в модалке авторизации");
        }

        static ElementData nameField() {
            return new ElementData(By.xpath("//*[contains(@class,'wrapper')]//input[@name='fullname']"),
                    "поле 'Имя и фамилия' в модалке авторизации");
        }

        static ElementData emailField() {
            return new ElementData(By.xpath("//*[contains(@class,'wrapper')]//input[@name='email']"),
                    "поле 'Электронная почта' в модалке авторизации");
        }

        static ElementData passwordField() {
            return new ElementData(By.xpath("//*[contains(@class,'wrapper')]//input[@name='password']"),
                    "поле 'Пароль' в модалке авторизации");
        }

        static ElementData passwordConfirmationField() {
            return new ElementData(By.xpath("//*[contains(@class,'wrapper')]//input[@name='passwordConfirmation']"),
                    "поле 'Подтверждение пароля' в модалке авторизации");
        }


        static ElementData errorMessage(String text) {
            return new ElementData(By.xpath("//*[contains(@class, 'auth-input__error') and contains(text(),'" + text + "')]"),
                    "текст пользовательской ошибки в полях модалки авторизации");
        }

        static ElementData errorPhoneMessage(String text) {
            return new ElementData(By.xpath("//div[@data-qa='tel_login_form_error' and text()='"+text+"']"),
                    "текст пользовательской ошибки в полях модалки авторизации");
        }

        static ElementData personalDataWarningText() {
            return new ElementData(By.xpath("//*[@class='auth-modal__form']//div[@class='privacy-terms']//text()']"),
                    "текст предупреждения о согласии с условиями на обработку персональных данных в модалке авторизации");
        }

        static ElementData personalDataAgreementLink() {
            return new ElementData(By.xpath("//*[@class='auth-modal__form']//*[@class='privacy-terms__link']"),
                    "ссылка на условия обработки персональных данных в модалке авторизации");
        }

        // todo rememberMeCheckbox
        static ElementData agreementCheckbox() { return new ElementData(
                By.xpath("//div[contains(@class,'block_checkbox') and text()='Согласен']"),
                "чекбокс согласия на получение почтовой рассылки в модалке авторизации"); }

        static ElementData checkBoxes(){
            return new ElementData(By.xpath("//span[contains(@class,' checkbox_')]"),
                    "объект возвращает 2 чекбокса 'Хочу заказать для бизнеса' и 'Согласен на рекламную рассылку'");
        }

        static ElementData forgotPasswordButton() {
            return new ElementData(By.xpath("//*[@class='auth-modal__form']//*[text()='Забыли пароль?']"),
                    "кнопка Забыли пароль в модалке авторизации");
        }

        static ElementData submitButton() {
            return new ElementData(By.xpath("//button[@data-qa='login_form_submit_button']"),
                    "кнопка отправки в модалке авторизации");
        }
        static ElementData submitButtonRegistration() {
            return new ElementData(By.xpath("//button[@data-qa='registration_form_submit_button']"),
                        "кнопка отправки в модалке авторизации");
        }
        //ToDo здесь нужно добавить дата атрибут с именем, так как кнопки друг от друга ни как не отличаются
        static ElementData vkontakteButton() {
            return new ElementData(
                    By.xpath("//button[@data-qa='vkontakte']"),
                        "кнопка авторизации через Vkontakte");
        }

        static ElementData socialButtonsSectionParent(){
            return new ElementData(By.xpath("//div[contains(@class,'omni_auth_buttons')]"),
                    "Парент объект для кнопок авторизации через социальные сети");
        }

        static ElementData facebookButton() {
            return new ElementData(
                    By.xpath("//button[@data-qa='facebook']"),
                        "кнопка авторизации через Facebook");
        }

        static ElementData mailruButton() {
            return new ElementData(
                    By.xpath("//button[@data-qa='mail_ru']"),
                        "кнопка авторизации через Mail.ru");
        }

        static ElementData sberButton() {
            return new ElementData(
                    By.xpath("//button[@data-qa='sber_id']"),
                        "кнопка авторизации через Sber ID");
        }

        static ElementData continueButton(){
            return new ElementData(By.xpath("//button[@data-qa='tel_login_form_button']"),
                    "кнопка Продолжить на модалке авторизации");
        }
    }

    /** Модалка авторизации-регистрации */
    interface PasswordRecoveryModal {

        static ElementData closeButton() {
            return new ElementData(
                    By.xpath("//button[@class='modal-wrapper__close']"),
                        "крестик закрытия модалки восстановления пароля");
        }

        static ElementData backButton() {
            return new ElementData(
                    By.xpath("//div[@class='auth-modal__back']"),
                        "кнопка 'Вернуться назад' в модалке восстановления пароля");
        }

        static ElementData emailField() {
            return new ElementData(
                    By.xpath("//input[@name='email']"),
                        "поле 'Пароль' в модалке восстановления пароля");
        }

        static ElementData passwordField() {
            return new ElementData(
                    By.xpath("//input[@name='password']"),
                        "поле 'Пароль' в модалке восстановления пароля");
        }

        static ElementData passwordConfirmationField() {
            return new ElementData(
                    By.xpath("//input[@name='passwordConfirmation']"),
                        "поле 'Подтверждение пароля' в модалке восстановления пароля");
        }

        static ElementData submitRequestButton() {
            return new ElementData(
                    By.xpath("//button[@type='submit' and text()='Восстановить']"),
                        "кнопка отправки запроса на восстановление пароля в модалке восстановления пароля");
        }

        static ElementData submitRecoveryButton() {
            return new ElementData(
                    By.xpath("//button[@type='submit' and text()='Сохранить и войти']"),
                        "кнопка восстановления пароля в модалке восстановления пароля");
        }

        static ElementData successRecoveryRequestText() {
            return new ElementData(
                    By.xpath("//*[@class='auth-modal']//*[@class='auth-modal__recovery-text']//span[text()='На указанный вами E-mail высланы инструкции по восстановлению пароля.']"),
                        "текст успешного запроса в модалке восстановления пароля");
        }
    }

    /** Адресные модалки Феникса */
    interface AddressModal {

        static ElementData popup() {
            return new ElementData(By.xpath("//*[@data-qa='address-edit']"),
                    "поп-ап адресной модалки");
        }

        static ElementData closeButton() {
            return new ElementData(By.xpath("//button[@data-qa='address-modal-close']"),
                    "крестик закрытия модалки выбора адреса");
        }

        static ElementData deliveryButton() {
            return new ElementData(By.xpath("//button[@data-qa='address-edit-selector-delivery']"),
                    "кнопка 'Доставка' в адресной модалке");
        }

        static ElementData pickupButton() {
            return new ElementData(By.xpath("//button[@data-qa='address-edit-selector-pickup']"),
                    "кнопка 'Самовывоз' в адресной модалке");
        }

        static ElementData addressField() {
            return new ElementData(By.xpath("//input[@data-qa='address-modal-input']"),
                    "поле ввода адреса в модалке выбора адреса");
        }

        static ElementData adressImageOnMap(){
            return new ElementData(By.xpath("//*[contains(@class,'notice')]"),
                    "иголка адреса на карте");
        }
        static ElementData modalMapWithText(){
            return new ElementData(By.xpath("//*[contains(@class,'map')]//*[contains(@class,'float-button-icon')]"),
                    "модалка с картой и текстом ошибки");
        }

        static ElementData errorOnTheMap(){
            return new ElementData(By.xpath("//*[contains(@class,'map')]//*[contains(@class,'map')]"),
                    "ошибка неточного адреса");
        }

        static ElementData addressSuggest() {
            return new ElementData(
                    /*By.xpath("//*[@data-qa='address-autocomplete-dropdown']//*[@id='downshift-0-item-0']"),
                    "адресная подсказка в модалке выбора адреса");
                     */
                    By.xpath("//*[contains(@class,'search_select')]//*[text()]"),
                    "адресная подсказка в модалке выбора адреса");
        }

        static ElementData submitButton() {
            //return new ElementData(By.xpath("//button[@data-qa='address-modal-submit']"));
            return new ElementData(
                    By.xpath("//*[contains(@class,'address-modal')]//*[contains(text(),'Сохранить')]"),
                    "кнопка сохранить адрес в модалке");
        }

        static ElementData findShopButton() {
            return new ElementData(
                    By.xpath("//*[@data-qa='address-modal-submit']"),
                    "кнопка найти магазины в модалке");
        }

        static ElementData recentAddressesList() {
            return new ElementData(By.className("address-modal__addresses"),
                    "список предыдущих адресов в модалке выбора адреса");
        }

        static ElementData recentAddress() {
            return new ElementData(By.xpath("//*[@id='react-modal']/div/div/div/div/div/div[1]/div/div/div/div/div/div[2]/div/div[2]"),
                    "предыдущий адрес в модалке выбора адреса");
        }

        static ElementData authButton() {
            return new ElementData(
                    By.xpath("//*[@data-qa='address-modal-login' and text()='Войти']"),
                            "кнопка перехода в авторизацию в модалке выбора адреса");
        }

        static ElementData deliverySelector() {
            return new ElementData(
                    By.xpath("//button[@data-qa='address-edit-selector-delivery' and text()='Доставка']"),
                    "кнопка переключения на доставку в модалке выбора адреса");
        }

        static ElementData pickupSelector() {
            return new ElementData(
                    By.xpath("//button[@data-qa='address-edit-selector-pickup' and text()='Самовывоз']"),
                    "кнопка переключения на самовывоз в модалке выбора адреса");
        }

        static ElementData title() {
            return new ElementData (
                    By.xpath("//*[@class='address-modal__header' and text()='Выберите способ получения']"),
                    "заголовок модалки выбора адреса");
        }

        static ElementData titleOutOfZone() {
            return new ElementData (By.xpath("//div[text()='Адрес не в зоне доставки']"),
                    "");
        }

        static ElementData pickNewAddressButton() {
            return new ElementData(By.className("address-modal__button--reselect"),
                    "кнопка выбрать другой адрес");
        }
    }

        /** Феникс-модалка выбора магазина при отсутствии текущего ритейлера по выбранному адресу */
        interface StoresModal {

        static ElementData popup() {
            return new ElementData(By.className("stores-modal"), "модалка выбора магазина");
        }

        static ElementData firstStoreAvailable() {
            return new ElementData(By.xpath("//*[contains(@class,'stores-modal__')]//a[@data-qa='store-card']"),
                    "первый магазин в модалке выбора магазина");
        }

        static ElementData pickNewAddressButton() {
            return new ElementData(By.className("stores-modal__to-login-link"),
                    "кнопка изменения адреса в модалке выбора магазина");
        }
    }

        /** Модалка обновления цен */
        interface PricesModal {

        static ElementData popup() {
            return new ElementData(
                    By.xpath("//div[contains(@class,'prices-modal')]"),
                        "поп-ап модалки обновления цен"
            );
        }

        static ElementData refreshPricesButton() {
            return new ElementData(By.className("//div[contains(@class,'prices-modal')]//a[@class='prices-modal__btn']"),
                    "кнопка 'Обновить цены' в модалке цен"
            );
        }
    }

        /** Модалка "Доставка" */
        interface DeliveryModal {

        static ElementData popup() {
            return new ElementData(By.xpath("//*[@class='modal-content']"),
                    "попап модалки Доставка");
        }

        static ElementData title() {
            return new ElementData( By.xpath("//*[@class='modal-title']"),
                    "заголовок модалки Доставка");
        }

        static ElementData closeButton() {
            return new ElementData(By.xpath("//*[@class='modal-content']//button[@class='close']"),
                    "кнопка закрытия модалки Доставка");
        }
    }

        /** Модалка "Оплата" */
        interface PaymentModal {

            static ElementData popup() {
                return new ElementData(By.cssSelector(".modal-lg > div:nth-child(1)"),
                        "попап модалки Оплата");
            }

            static ElementData title() {
                return new ElementData(By.cssSelector(".modal-title"),
                        "заголовок модалки Оплата");
            }

            static ElementData closeButton() {
                return new ElementData(By.cssSelector(".close"),
                        "кнопка закрытия модалки Оплата");
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

        static ElementData errorMessageStore() {
            return new ElementData(
                    By.xpath("//*[text()='Нет доступных магазинов по выбранному адресу']"),
                    "сообщение об отсутсвии магазинов по выбранному адресу доставки");
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
        static ElementData categoryFirstLevel(String name) {
            return new ElementData(By.linkText(name),
                    "категория \"" + name + "\"");
        }
    }

    /** Всплывающее меню профиля пользователя */
    public interface AccountMenu {

        static ElementData popup() {
            return new ElementData(
                    By.xpath("//header//*[@data-qa='account-menu']"),
                        "всплывающее меню профиля");
        }

        static ElementData header() {

            return new ElementData(
                    By.xpath("//header//*[@data-qa='account-menu']//*[@data-qa='account-menu-username']"),
                        "заголовок с именем юзера во всплывающем меню профиля");
        }

        static ElementData profileButton() {
            return new ElementData(By.xpath("//header//*[@data-qa='account-menu']//*[@data-qa='account-menu-profile']"),
                    "кнопка 'Профиль' во всплывающем меню профиля");
        }

        static ElementData ordersHistoryButton() {
            return new ElementData(By.xpath("//header//*[@data-qa='account-menu']//*[@data-qa='account-menu-orders']"),
                    "кнопка 'История заказов' во всплывающем меню профиля");
        }

        static ElementData termsButton() {
            return new ElementData(By.xpath("//header//*[@data-qa='account-menu']//*[@data-qa='account-menu-terms']"),
                    "кнопка 'Условия пользования' во всплывающем меню профиля");
        }

        static ElementData logoutButton() {
            return new ElementData(By.xpath("//header//*[@data-qa='account-menu']//*[@data-qa='account-menu-logout']"),
                    "кнопка 'Выйти' во всплывающем меню профиля");
        }

        static ElementData deliveryButton() {
            return new ElementData(By.xpath("//header//*[@data-qa='account-menu']//*[@data-qa='account-menu-delivery']"),
                    "кнопка 'Доставка' во всплывающем меню профиля");
        }

        static ElementData paymentButton() {
            return new ElementData(By.xpath("//header//*[@data-qa='account-menu']//*[@data-qa='account-menu-payment']"),
                    "кнопка 'Оплата' во всплывающем меню профиля");
        }

        static ElementData faqButton() {
            return new ElementData(By.xpath("//header//*[@data-qa='account-menu']//*[@data-qa='account-menu-faq']"),
                    "кнопка 'FAQ' во всплывающем меню профиля");
        }

        static ElementData contactsButton() {
            return new ElementData(By.xpath("//header//*[@data-qa='account-menu']//*[@data-qa='account-menu-contacts']"),
                    "кнопка 'Контакты' во всплывающем меню профиля");
        }

        static ElementData deliveryModalButtonClose() {
            return new ElementData(By.xpath("//div[@class='modal-content']//button[@class='close']"),
                    "кнопка 'Закрыть' во всплывающем окне с зонами доставки");
        }
    }

    /** Профиль пользователя */
    public interface UserProfile {

        static ElementData menu() {
            return new ElementData(
                    By.className("user-menu"),
                        "боковое меню в разделе профиля");
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

            static ElementData allOrdersFilterButton() {
                return new ElementData(By.xpath("//button[@data-qa='user-shipment-list-selector-all']"),
                        "кнопка фильтра всех заказов в истории заказов");
            }

            static ElementData activeOrdersFilterButton() {
                return new ElementData(By.xpath("//button[@data-qa='user-shipment-list-selector-active']"),
                        "кнопка фильтра активных заказов в истории заказов");
            }

            static ElementData completeOrdersFilterButton() {
                return new ElementData(By.xpath("//button[@data-qa='user-shipment-list-selector-inactive']"),
                        "кнопка фильтра завершенных заказов в истории заказов");
            }

            static ElementData allOrdersPlaceholder() {
                return new ElementData(By.xpath("//h3[text()='У вас нет завершенных заказов']"),
                        "плейсхолдер пустой истории заказов");
            }

            static ElementData activeOrdersPlaceholder() {
                return new ElementData(By.xpath("//h3[text()='У вас нет активных заказов']"),
                        "плейсхолдер пустой истории активных заказов");
            }

            static ElementData completeOrdersPlaceholder() {
                return new ElementData(By.xpath("//h3[text()='У вас нет завершенных заказов']"),
                        "плейсхолдер пустой истории завершенных заказов");
            }

            static ElementData goShoppingButton() {
                return new ElementData(By.xpath("//*[@data-qa='user-shipment-list-buy-more']"),
                        "кнопка 'Перейти к покупкам' на плейсхолдере пустой истории заказов");
            }

            interface order {

                static ElementData snippet() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-list-shipment']"),
                            "сниппет верхнего заказа на странице истории заказов");
                }

                static ElementData snippet(int position) {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-list-shipment']["+position+"]"),
                            "сниппет " + position + " заказа на странице истории заказов");
                }

                static ElementData positionsCount() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-list-shipment-count']"),
                            "количество позиций в сниппете заказа на странице истории заказов");
                }

                static ElementData weight() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-list-shipment-weight']"),
                            "вес в сниппете заказа на странице истории заказов");
                }

                static ElementData price() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-list-shipment-price']"),
                            "стоимость в сниппете заказа на странице истории заказов");
                }

                static ElementData retailerLogo() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-list-shipment-logo']"),
                            "лого ритейлера в сниппете заказа на странице истории заказов");
                }

                static ElementData shipmentDate() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-list-shipment-date']"),
                            "дата доставки в сниппете заказа на странице истории заказов");
                }

                static ElementData shipmentInterval() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-list-shipment-interval']"),
                            "интервал доставки в сниппете заказа на странице истории заказов");
                }

                static ElementData shipmentOldSlot() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-list-shipment-previous-date']"),
                            "устаревший слот доставки в сниппете заказа на странице истории заказов");
                }

                static ElementData shipmentNumber() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-list-shipment-number']"),
                            "номер доставки в сниппете заказа на странице истории заказов");
                }

                static ElementData shipmentStatus() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-list-shipment-state']"),
                            "статус доставки в сниппете заказа на странице истории заказов");
                }

                static ElementData shipmentItems() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-list-shipment-products']"),
                            "превью товаров в сниппете заказа на странице истории заказов");
                }

                static ElementData repeatButton() {
                    return new ElementData(By.xpath("//button[@data-qa='user-shipment-list-shipment-repeat']"),
                            "кнопка повтора верхнего заказа на странице истории заказов");
                }

            }
        }

        /** Страница деталей заказа */
        interface OrderDetailsPage {

            interface OrderStatus {

                static ElementData placed() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-status' and @id='shipment-ready']"),
                            "статус заказа 'Ожидает сборки' на странице деталей заказа");
                }

                static ElementData collecting() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-status' and @id='shipment-collecting']"),
                            "статус заказа 'Собирается' на странице деталей заказа");
                }

                static ElementData shipping() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-status' and @id='shipment-shipping']"),
                            "статус заказа 'В пути' на странице деталей заказа");
                }

                static ElementData canceled() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-status' and @id='shipment-canceled']"),
                            "статус заказа 'Отменен' на странице деталей заказа");
                }

                static ElementData shipped() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-status' and @id='shipment-shipped']"),
                            "статус заказа 'Доставлен' на странице деталей заказа");
                }

                static ElementData carryover() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-status' and @id='shipment-carryover']"),
                            "статус заказа 'Перенесен' на странице деталей заказа");
                }
            }

            interface OrderAssembly {

                static ElementData allItemsButton() {
                    return new ElementData(By.xpath("//button[@data-qa='user-shipment-assembly-items-all']"),
                            "кнопка 'Все' в блоке сборки на странице деталей заказа");
                }

                static ElementData collectedItemsButton() {
                    return new ElementData(By.xpath("//button[@data-qa='user-shipment-assembly-items-collected']"),
                            "кнопка 'Собрано' в блоке сборки на странице деталей заказа");
                }

                static ElementData replacedItemsButton() {
                    return new ElementData(By.xpath("//button[@data-qa='user-shipment-assembly-items-replaced']"),
                            "кнопка 'Замены' в блоке сборки на странице деталей заказа");
                }

                static ElementData canceledItemsButton() {
                    return new ElementData(By.xpath("//button[@data-qa='user-shipment-assembly-items-canceled']"),
                            "кнопка 'Отмены' в блоке сборки на странице деталей заказа");
                }
            }

            interface OrderSummary {

                static ElementData container() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-summary']"),
                            "плашка деталей на странице деталей заказа");
                }

                static ElementData shipmentInterval() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-interval']"),
                            "интервал доставки на странице деталей заказа");
                }

                static ElementData shipmentNumber() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-number']"),
                            "номер доставки на странице деталей заказа");
                }

                static ElementData trigger() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-summary-trigger']"),
                            "триггер разворота доп. деталей на странице деталей заказа");
                }

                static ElementData shipmentAddress() {
                    return new ElementData(By.xpath("//*[@qakey='user-shipment-address']"),
                            "адрес доставки на странице деталей заказа");
                }

                static ElementData shipmentPhone() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-phone']"),
                            "телефон получателя на странице деталей заказа");
                }

                static ElementData shipmentEmail() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-email']"),
                            "email получателя на странице деталей заказа");
                }

                static ElementData shipmentReplacementPolicy() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-replacement-policy']"),
                            "способ замен на странице деталей заказа");
                }

                static ElementData paymentMethod() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-payment-method']"),
                            "способ оплаты на странице деталей заказа");
                }

                static ElementData shipmentCost() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-products-cost']"),
                            "стоимость доставки на странице деталей заказа");
                }

                static ElementData shipmentItemsCount() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-products-count']"),
                            "количество позиций доставки на странице деталей заказа");
                }

                static ElementData shipmentItemsWeight() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-products-weight']"),
                            "вес доставки на странице деталей заказа");
                }

                static ElementData repeatOrderButton() {
                    return new ElementData(By.xpath("//button[@data-qa='user-shipment-repeat']"),
                            "кнопка повтора заказа на странице деталей заказа");
                }

                static ElementData cancelOrderButton() {
                    return new ElementData(By.xpath("//button[@data-qa='user-shipment-cancel']"),
                            "кнопка отмены заказа на странице деталей заказа");
                }
            }

            interface OrderDocuments {

                static ElementData container() {
                    return new ElementData(By.xpath("todo"),
                            "плашка документов на странице деталей заказа");
                }
            }

            interface CancelOrderModal {

                static ElementData popup() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-modal-cancel']"),
                            "поп-ап модалки отмены заказа");
                }

                static ElementData yesButton() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-modal-cancel']//button[@data-qa='user-shipment-modal-cancel-btn-cancel']"),
                            "кнопка 'Да, отменить' в модалке отмены заказа");
                }

                static ElementData noButton() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-modal-cancel']//button[@data-qa='user-shipment-modal-cancel-btn-dismiss']"),
                            "кнопка 'Не отменять' в модалке отмены заказа");
                }

                static ElementData closeButton() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-modal-cancel']//button[@data-qa='user-shipment-modal-cancel-close']"),
                            "крестик закрытия модалки отмены заказа");
                }
            }

            interface RepeatOrderModal {

                static ElementData popup() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-modal-repeat']"),
                            "поп-ап модалки повтора заказа");
                }

                static ElementData yesButton() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-modal-repeat']//button[@data-qa='user-shipment-modal-repeat-btn-repeat']"),
                            "кнопка 'Добавить товары' в модалке повтора заказа");
                }

                static ElementData noButton() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-modal-repeat']//button[@data-qa='user-shipment-modal-repeat-btn-dismiss']"),
                            "кнопка 'Не добавлять' в модалке повтора заказа");
                }

                static ElementData closeButton() {
                    return new ElementData(By.xpath("//*[@data-qa='user-shipment-modal-repeat']//button[@data-qa='user-shipment-modal-repeat-close']"),
                            "крестик закрытия модалки повтора заказа");
                }
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
            return new ElementData(By.cssSelector(".resource-not-found__message-block"),
                    "плейсхолдер пустой поисковой выдачи");
        }

        interface Product {
            static ElementData snippet() {
                return new ElementData(By.xpath("//*[@class='product']//*[contains(@class,'product__img')]//img[contains(@alt,text())]"),
                        "сниппет продукта");
            }

            static ElementData snippetFavoriteButton() {
                return new ElementData(By.xpath("//button[contains(@class,'bt--buy')]//*[contains(@class,'cart-actions__plus')]"),
                        "кнопка добавить любимый товар");
            }

            static ElementData snippetFavorite() {
                return new ElementData(By.xpath("//*[contains(@class,'favorites__head')]//*[contains(@class,'favorites__image')]//img[contains(@alt,text())]"),
                        "сниппет любимого продукта");
            }

            static ElementData firstProductOnSnippet(){
                return new ElementData(By.xpath(""),
                        "");
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
            return new ElementData(By.className("category-product"),
                    "товар сео-каталога");
        }
    }

    /** Страницы категорий/таксонов */
    public interface CategoryPage {

        static ElementData title() {
            return new ElementData(By.className("taxons-nav__title"),
                    "заголовок страницы категории");
        }

        static ElementData filters() {
            return new ElementData(By.className("filters_container"),
                    "фильтры на странице категории");
        }

    }

    /** Карточка товара */
    public interface ItemCard {

            static ElementData popup() {
                return new ElementData(By.xpath("//*[contains(@class,'product_cards')]"),
                        "попап с карточкой товара");
                //return new ElementData(By.className("product-popup"));
            }

            static ElementData name() {
                return new ElementData(By.xpath("//h1[contains(@class,'popup')]"),
                        "имя товара из карточки");
            }

            static ElementData image() {
                return new ElementData(By.xpath("//*[contains(@class,'preview_cell')]"),"");
                //return new ElementData(By.className("product-popup__img"));
            }

            static ElementData price() {
                return new ElementData(By.xpath("//div[contains(@class,'popup')]//div[contains(@class,' components')]//span"),
                        "цена товара из карточки");
                //return new ElementData(By.xpath("//div[@class='product-popup__price']//span"));
            }

            static ElementData prices() {
                return new ElementData(By.xpath("//div[contains(@class,'popup')]//div[contains(@class,' components')]//span"),
                    "цена товара из карточки");
                //return new ElementData(By.xpath("//div[@class='product-popup__price']//span"));
            }

            static ElementData quantity() {
                return new ElementData(By.xpath("//div[@class='product-popup']//div[contains(@class,'product__cart-counter')]"),
                "каунтер количества добавленного в корзину товара в карточке товара");
            }

            static ElementData quantityByText() {
                return new ElementData(By.xpath("//div[@itemprop='offers']//div[contains(@class,'_description')]"),
                    "каунтер количества доступного товара в карточке товара");
            }
            static ElementData quantityByCount(int count) {
                return new ElementData(By.xpath("//div[contains(@class,'popup_cart_actions__quantity') and contains(text(),'"+count+"')]"),
                        "каунтер количества добавленного товара");
            }

            static ElementData offersElement(){
                return new ElementData(By.xpath("//*[contains(@itemprop,'offers')]"),
                        "секция с ценой, скидкой и кнопками на карточке товара");
            }

            static ElementData buttonBuy(){
                return new ElementData(By.xpath("//button[@type='button' and text()='Купить']"),
                    "кнопка купить для на карточке товара");
            }

            static ElementData cartNew(){
                return new ElementData(By.xpath("//*[contains(@class,'product__cart-counter--with-icon')]"),
                "иконка корзины с добавленным элементом");
            }

            static ElementData saleBadge() {
                return new ElementData(By.className("sale-badge"),
                        "шильдик Скидка в карточке товара");
            }

            static ElementData closeButton() {
                return new ElementData(By.xpath("//*[contains(@class,'wrapper_')]//*[contains(@aria-label,'Закрыть')]"),
                        "кнопка закрыть модалку карточки продукта");
            }

            static ElementData plusButton() {
                return new ElementData(By.xpath("//button[@type='button' and text()='+']"),
                        "кнопка + в карточке товара");
            }

            static ElementData buyButton() {
                return new ElementData(By.xpath("//button[contains(text(),'Купить')]"),
                    "кнопка купить в карточке товара");
            }

            static ElementData minusButton() {
                return new ElementData(By.className("popup-cart-actions__btn--left"),
                        "кнопка минус в карточке товара");
            }

            static ElementData notInStock() {
                return new ElementData(By.className("product-popup__not-in-stock"),
                        "пометка Нет в наличии в карточке товара");
            }

            static ElementData addToFavoritesButton() {
                return new ElementData(By.xpath("//div[@itemprop='offers']//*[@role='button' and @class='favorite-button favorite-button-large']"),
                        "кнопка добавления товара в избранное в карточке товара");
            }

            static ElementData deleteFromFavoritesButton() {
                return new ElementData(By.xpath("//div[@itemprop='offers']//*[@role='button' and @class='favorite-button favorite-button-large favorite-button--active']"),
                        "кнопка удаления товара из избранного в карточке товара");
            }
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
                        By.xpath("//*[@data-qa='cart']//*[@data-qa='cart_close-button']"),
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
                return new ElementData(By.xpath("//*[@data-qa='cart']//*[@data-qa='cart_checkout_button']"),
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
            return new ElementData(By.xpath("//header[contains(@class,'checkout-header')]"),
                    "шапка чекаута");
        }

        interface Step {

            static ElementData panel(CheckoutStepData step) {
                return new ElementData(By.xpath("//div[@class='panel-header__text' and text()='" + step.getTitle() + "']//ancestor::div[@class='panel-header']//parent::div"),
                        "открытая панель шага \"" + step.getName() + "\" в чекауте");
            }

            static ElementData icon(CheckoutStepData step) {
                return new ElementData(By.xpath("//div[@class='panel-header__text' and text()='" + step.getTitle() + "']//preceding::*[contains(@class,'indicator')]"),
                        "иконка шага \"" + step.getName() + "\" в чекауте");
            }

            static ElementData title(CheckoutStepData step) {
                return new ElementData(By.xpath("//div[@class='panel-header__text' and text()='" + step.getTitle() + "']"),
                        "тайтл шага \"" + step.getName() + "\" в чекауте");
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

            CheckoutStepData addressStep = CheckoutSteps.addressStep();

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

            instamart.ui.common.pagesdata.CheckoutStepData contactsStep = CheckoutSteps.contactsStep();

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

            CheckoutStepData replacementsStep = CheckoutSteps.replacementsStep();

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

            CheckoutStepData paymentStep = CheckoutSteps.paymentStep();

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

            CheckoutStepData deliveryStep = CheckoutSteps.deliveryStep();

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
                return new ElementData(By.cssSelector("div.checkout-panel:nth-child(" + step + ") > div:nth-child(1) > div:nth-child(3) > a:nth-child(1)"),
                        "кнопка изменения шага чекаута");
            }

            // Костыльный элемент для 5 шага
            static ElementData changeStep5Button() { //TODO переделать локаторы так, чтобы избавиться от отдельной кнопки для 5 шага
                return new ElementData(By.xpath("/html/body/div[3]/div/form/div/div/div[1]/div[5]/div[2]/div/div/div/div[1]/div[3]"),
                        "кнопка изменения 5 шага чекаута");
            }

            static ElementData replacementPolicy(int option) {
                return new ElementData(By.cssSelector("div.replacement-policy:nth-child(" + option + ")"),
                        "политика замен");
            }

            static ElementData paymentTypeSelector(String option) {
                return new ElementData(
                        By.xpath("//*[@class='payment-method__name' and text()='" + option + "']//parent::*[contains(@class,'payment-method')]"),
                            "кнопка выбора способа оплаты " + option);
            }

            static ElementData activePaymentTypeSelector(String option) {
                return new ElementData(
                     By.xpath("//*[@class='payment-method__name' and text()='" + option + "']//parent::*[contains(@class,'payment-method--active')]"),
                        "активная кнопка способа оплаты " + option);
            }

            static ElementData deliveryDaySelector(int day) {
                return new ElementData(By.cssSelector("div.panel-tab:nth-child(" + day + ")"),
                        "селектор дня доставки");
            }

            static ElementData deliveryWindowsPlaceholder() {
                return new ElementData(By.xpath("//div[text()='Интервалы доставки недоступны']"),
                        "заглушка слотов доставки");
            }

            static ElementData slotTime(int day, int position) {
                return new ElementData( By.xpath("//*[@id='deliveryDay-" + (day-1) + "']/div[" + position + "]/div/span"),
                        "время слота доставки");
            }

            static ElementData slotPrice(int day, int position) {
                return new ElementData(By.xpath("//*[@id='deliveryDay-" + (day-1) + "']/div[" + position + "]/div/div/div/div"),
                        "цена слота доставки");
            }

            static ElementData chooseSlotButton() {
                return new ElementData(By.xpath("//*[@id='deliveryDay-6']//span[text()='Выбрать']"),
                        "кнопка выбора слота доставки");
            }

            static ElementData chooseSlotButton(int day, int position) {
                return new ElementData(By.xpath("//*[@id='deliveryDay-" + (day-1) + "']/div[" + position + "]/div/span"),
                        "кнопка выбора слота доставки");
            }

            static ElementData deliveryPrice() {
                return new ElementData(By.cssSelector("div.checkout-summary__subtotal:nth-child(4) > span:nth-child(2) > div:nth-child(1)"),
                        "стоимость доставки заказа");
            }

            /** Элементы номера телефона */
            static ElementData phoneNumberField() {
                return new ElementData(By.id("phone-input"),
                        "поле ввода номера телефона");
            }

            static ElementData editPhoneButton() {
                return new ElementData(By.xpath("//div[2]/div[1]/div/div/a"),
                        "кнопка редактирования номера телефона");
            }

            static ElementData deletePhoneButton() {
                return new ElementData(By.xpath("//div[2]/div/div[1]/button"),
                        "кнопка удаления номера телефона");
            }

            static ElementData phoneNumber() {
                return new ElementData(By.className("checkout-selector-item__text"),
                        "номер телефона");
            }

            /** Элементы оплаты банковской картой */
            static ElementData addPaymentCardButton() {
                return new ElementData(By.xpath("//div[contains(text(),'+ Добавить новую карту')]"),
                        "кнопка добавления карты оплатч");
            }

            static ElementData changePaymentCardButton() {
                return new ElementData(By.xpath("//div[2]/div[1]/div[1]/div/div/a"),
                        "кнопка изменения карты оплаты");
            }

            static ElementData paymentCardsList() {
                return new ElementData(By.xpath("//div[4]/div[2]/div/div/div[2]/div[2]/div[1]/div/div/div/div/span[2]/span"),
                        "список карт оплаты");
            }

            static ElementData paymentCardTitle(int number) {
                return new ElementData(By.xpath("//div[4]/div[2]/div/div/div[2]/div[2]/div[1]/div[" + number + "]/div/div/div/span[2]/span"),
                        "название карты оплаты");
            }

            static ElementData paymentCardTitle(PaymentCardData creditCardData) {
                String number = creditCardData.getCardNumber();
                return new ElementData(By.xpath("//span[contains(text(),'•••• " + number.substring(number.length()-4) + "')]"),
                        "название карты оплаты");
            }

            /** Модалка карты оплаты */
            interface PaymentCardModal {

                static ElementData cardNumber() {
                    return new ElementData(By.xpath("//form/div/div[1]/span"),
                            "номер карты в модалке ввода карты оплаты");
                }

                static ElementData cardNumberField() {
                    return new ElementData(By.xpath("//input[@data-cp='cardNumber']"),
                            "поле номера карты в модалке добавления карты оплаты");
                }

                static ElementData monthField() {
                    return new ElementData(By.xpath("//input[@data-cp='expDateMonth']"),
                            "поле месяца в модалке добавления карты оплаты");
                }

                static ElementData yearField() {
                    return new ElementData(By.xpath("//input[@data-cp='expDateYear']"),
                            "поле года в модалке добавления карты оплаты");
                }

                static ElementData cvvField() {
                    return new ElementData(By.xpath("//input[@data-cp='cvv']"),
                            "поле cvv в модалке добавления карты оплаты");
                }

                static ElementData nameField() {
                    return new ElementData(By.xpath("//input[@data-cp='name']"),
                            "поле имени в модалке добавления карты оплаты");
                }

                static ElementData deleteButton() {
                    return new ElementData(By.xpath("//button[contains(text(),'Удалить')]"),
                            "кнопка удаления карты в модалке добавления карты оплаты");
                }

                static ElementData confirmButton() {
                    return new ElementData(By.xpath("//button[contains(text(),'Сохранить')]"),
                            "кнопка сохранения в модалке добавления карты оплаты");
                }
            }

            /** Страница подтверждения платежа по карте */
            interface Cloudpayments {

                static ElementData answerField() {
                    return new ElementData(By.id("password"),
                            "поле ввода капчи клаудпейментс");
                }

                static ElementData confirmButton() {
                    return new ElementData(By.xpath("//button[contains(text(),'Подтвердить')]"),
                            "кнопка отправки капчи клаудпейментс");
                }
            }

            /** Элементы оплаты банковским переводом */
            static ElementData addJuridicalButton() {
                return new ElementData(By.xpath("//div[contains(text(),'+ Добавить реквизиты')]"),
                        "кнопка добавления юрлица");
            }

            static ElementData changeJuridicalButton() {
                return new ElementData(By.xpath("//div[2]/div[1]/div[1]/div/div/a"),
                        "кнопка изменения юрлица");
            }

            static ElementData juridicalTitle(int number) {
                return new ElementData(By.xpath("//div[" + number + "]/div/div/div/span[2]/span"),
                        "название юрлица");
            }

            static ElementData juridicalTitle(JuridicalData juridicalData) {
                return new ElementData(By.xpath(
                        "//span[contains(text(),'" + juridicalData.getJuridicalName() + ", ИНН: " + juridicalData.getInn() + "')]"),
                        "название юрлица");
            }

            /** Модалка юр.лица */
            interface JuridicalModal {

                static ElementData innField() {
                    return new ElementData(By.xpath("//input[@name='requisites.inn']"),
                            "поле ввода ИНН в модалке юр.лица в чекауте");
                }

                static ElementData nameField() {
                    return new ElementData(By.xpath("//input[@name='requisites.name']"),
                            "поле ввода названия юр.лица в модалке юр.лица в чекауте");
                }

                static ElementData addressField() {
                    return new ElementData(By.xpath("//input[@name='requisites.address']"),
                            "поле ввода юр.адреса в модалке юр.лица в чекауте");
                }

                static ElementData kppField() {
                    return new ElementData(By.xpath("//input[@name='requisites.kpp']"),
                            "поле ввода КПП в модалке юр.лица в чекауте");
                }

                static ElementData operatingAccountField() {
                    return new ElementData(By.xpath("//input[@name='requisites.operatingAccount']"),
                            "поле ввода рассчетного счета в модалке юр.лица в чекауте");
                }

                static ElementData bikField() {
                    return new ElementData(By.xpath("//input[@name='requisites.bik']"),
                            "поле ввода БИК в модалке юр.лица в чекауте");
                }

                static ElementData bankField() {
                    return new ElementData(By.xpath("//input[@name='requisites.bank']"),
                            "поле ввода названия банка в модалке юр.лица в чекауте");
                }

                static ElementData correspondentAccountField() {
                    return new ElementData(By.xpath("//input[@name='requisites.correspondentAccount']"),
                            "поле ввода корреспондентского счета в модалке юр.лица в чекауте");
                }

                static ElementData deleteButton() {
                    return new ElementData(By.xpath("//button[contains(text(),'Удалить')]"),
                            "кнопка 'Удалить' в модалке юр.лица в чекауте");
                }

                static ElementData cancelButton() {
                    return new ElementData(By.xpath("//button[contains(text(),'Отменить')]"),
                            "кнопка 'Отменить' в модалке юр.лица в чекауте");
                }

                static ElementData confirmButton() {
                    return new ElementData(By.xpath("//button[contains(text(),'Сохранить')]"),
                            "кнопка 'Сохранить' в модалке юр.лица в чекауте");
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

            interface RetailerCard {

                static ElementData input() {
                    return new ElementData(
                            By.xpath("//div[@class='retailer-card__label']"),
                            "поле ввода карты ритейлера в чекауте");
                }

                interface Modal {

                    static ElementData popup() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__container']"),
                                "модалка карты ритейлера в чекауте");
                    }

                    static ElementData closeButton() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__container']//button[@class='rc-modal__close']"),
                                "крестик закрытия модалки карты ритейлера в чекауте");
                    }

                    static ElementData title() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__container']//div[@class='modal-form__title']"),
                                "заголовок модалки карты ритейлера в чекауте");
                    }

                    static ElementData inputField() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//input[@placeholder='Номер карты']"),
                                "поле ввода номера карты в модалке карты ритейлера в чекауте");
                    }

                    static ElementData errorText() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//div[@class='checkout-input-error']"),
                                "текст ошибки в модалке карты ритейлера в чекауте");
                    }

                    static ElementData deleteButton() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//button[text()='Удалить']"),
                                "кнопка \"Удалить\" в модалке карты ритейлера в чекауте"
                        );
                    }

                    static ElementData cancelButton() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//button[text()='Отменить']"),
                                "кнопка \"Отменить\" в модалке карты ритейлера в чекауте"
                        );
                    }

                    static ElementData saveButton() {
                        return new ElementData(
                                By.xpath("//div[@class='rc-modal__body']//button[text()='Сохранить']"),
                                "кнопка \"Сохранить\" в модалке карты ритейлера в чекауте");
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
                            By.xpath("//aside//*[text()='Итого']//following-sibling::span//div//text()"),
                                "сумма \"Итого\" в боковой колонке чекаута");
                }

                static ElementData sendOrderButton() {
                    return new ElementData(
                            By.xpath("//aside//*[text()='Оформить заказ']//parent::button"),
                                "кнопка \"Оформить заказ\" в боковой колонке чекаута");
                }
            }
        }

    /** Чат Jivosite */
    public interface Jivosite {

            static ElementData widget() {
                return new ElementData(
                        By.xpath("//jdiv[@id='jvlabelWrap']"),
                            "виджет Jivosite");
            }

            static ElementData openButton() {
                return new ElementData(
                        By.xpath("//jdiv[@id='jvlabelWrap']//jdiv[@class='hoverl_6R']"),
                            "панель виджета Jivosite");
            }

            static ElementData closeButton() {
                return new ElementData(
                        By.xpath("//jdiv[@id='jivo_close_button']"),
                            "кнопка закрытия виджета Jivosite");
            }

            static ElementData chatArea() {
                return new ElementData(
                        By.className("container_CV"),
                            "чат виджета Jivosite");
            }

            static ElementData messageField() {
                return new ElementData(
                        By.xpath("//textarea[@class='inputField_2G']"),
                            "поле сообщения виджета Jivosite");
            }

            static ElementData sendMessageButton() {
                return new ElementData(
                        By.xpath("//jdiv[@class='sendButton_1o']"),
                            "кнопка отправки ссобщения в виджете Jivosite");
            }

            static ElementData sentMessage(String text) {
                return new ElementData(
                        By.xpath("//*[@id='scrollbar-container']//*[text()='" + text + "']"),
                            "");
            }
        }

    /** Блоки RetailRocket */
    public interface RetailRocket {

        static ElementData widget(String widgetId) {
            return new ElementData(By.xpath("//*[contains(@class,'" + widgetId + "')]"),
                    "виджет RetailRocket");
        }

        static ElementData title(String widgetId) {
            return new ElementData(By.xpath("//*[contains(@class,'" + widgetId + "')]//*[contains(@class,'widgettitle')]"),
                    "тайтл виджета RetailRocket");
        }

        static ElementData item(String widgetId) {
            return new ElementData(By.xpath("//*[contains(@class,'" + widgetId + "')]//*[contains(@class,'swiper-slide')]"),
                    "товары виджета RetailRocket");
        }

        static ElementData addButton(String widgetId) {
            return new ElementData(By.xpath("//*[contains(@class,'" + widgetId + "')]//*[contains(@class,'rr-product-cart-btn-add')]"),
                    "кнопка добавления товара из виджета RetailRocket");
        }
    }

    /** Социальные сети */
    public interface Social {

        /** Vkontakte auth form */
        interface Vkontakte {

            static ElementData loginField() {
                return new ElementData(By.xpath("//input[@name='email']"),
                        "поле ввода логина формы авторизации Vkontakte");
            }

            static ElementData passwordField() {
                return new ElementData(By.xpath("//input[@name='pass']"),
                        "поле ввода пароля формы авторизации Vkontakte");
            }

            static ElementData submitButton() {
                return new ElementData(By.xpath("//button[@type='submit']"),
                        "кнопка отправки формы авторизации Vkontakte");
            }
        }

        /** Facebook auth form */
        interface Facebook {

            static ElementData loginField() {
                return new ElementData(By.xpath("//input[@id='email']"),
                        "поле ввода логина формы авторизации Facebook");
            }

            static ElementData passwordField() {
                return new ElementData(By.xpath("//input[@id='pass']"),
                        "поле ввода пароля формы авторизации Facebook");
            }

            static ElementData submitButton() {
                return new ElementData(By.xpath("//input[@type='submit']"),
                        "кнопка отправки формы авторизации Facebook");
            }
        }

        /** Mail.ru auth form */
        interface MailRu {

            static ElementData loginField() {
                return new ElementData(By.xpath("//input[@name='username']"),
                        "поле ввода логина формы авторизации Mail.ru");
            }

            static ElementData passwordField() {
                return new ElementData(By.xpath("//*[@class='login-row password']//input"),
                        "поле ввода пароля формы авторизации Mail.ru");
            }
            static ElementData passwordFieldUp(){
                return new ElementData(By.xpath("//*[@class='login-row password']"),
                        "поле ввода пароля формы авторизации Mail.ru");
            }


            static ElementData submitButton() {
                return new ElementData(By.xpath("//button[@data-test-id='submit-button']"),
                        "кнопка отправки формы авторизации Mail.ru");
            }

            static ElementData nextButton(){
                return new ElementData(By.xpath("//button[@data-test-id='next-button']"),
                        "кнопка для перехода на ввод пароля");
            }

            static ElementData loginButton(String login) {
                return new ElementData(By.xpath("//*[@data-name='login' and @data-value='" + login + "@mail.ru']"),
                        "кнопка выбора аккаунта в форме авторизации Mail.ru");
            }
        }

        /** Sber ID auth form */
        interface Sber {

            static ElementData loginButton() {
                return new ElementData(By.xpath("//div[@data-type='login']"),
                        "кнопка Логин в форме авторизации Sber ID");
            }

            static ElementData loginField() {
                return new ElementData(By.xpath("//input[@name='login']"),
                        "поле ввода логина формы авторизации Sber ID");
            }

            static ElementData passwordField() {
                return new ElementData(By.xpath("//input[@name='password']"),
                        "поле ввода пароля формы авторизации Sber ID");
            }

            static ElementData sberButtonsSection(){
                return new ElementData(By.tagName("nav"),
                        "парент объект кнопок авторизации через сбер ID");
            }

            static ElementData submitButton() {
                return new ElementData(By.xpath("//button[@type='submit']"),
                        "кнопка отправки формы авторизации Sber ID");
            }
        }

        interface Gmail {

            interface AuthForm {

                static ElementData loginField() {
                    return new ElementData(By.xpath("//input[@id='identifierId']"),
                            "поле ввода логина формы авторизации Gmail");
                }

                static ElementData loginNextButton() {
                    return new ElementData(By.xpath("//*[@role='button' and @id='identifierNext']"),
                            "кнопка 'Далее' в форме ввода логина Gmail");
                }

                static ElementData passwordField() {
                    return new ElementData(By.xpath("//input[@name='password']"),
                            "поле ввода пароля формы авторизации Gmail");
                }

                static ElementData passwordNextButton() {
                    return new ElementData(By.xpath("//*[@role='button' and @id='passwordNext']"),
                            "кнопка 'Далее' в форме ввода пароля Gmail");
                }
            }

            interface MailInterface {

            }
        }
    }

    /** Админка */
    public interface Administration {

        interface LoginPage {

            static ElementData title() {
                return new ElementData(By.xpath("//body[@class='admin']//h1[text()='Вход']"),
                        "заголовок 'Вход' на странице авторизации админки");
            }

            static ElementData emailField() {
                return new ElementData(By.xpath("//body[@class='admin']//input[@name='email']"),
                        "поле ввода email на странице авторизации админки");
            }

            static ElementData emailFieldErrorText(String text) {
                return new ElementData(By.xpath("//div[@role='alert' and text()='"+text+"']"),
                        "текст ошибки поля ввода email на странице авторизации админки");
            }

            static ElementData passwordField() {
                return new ElementData(By.xpath("//body[@class='admin']//input[@name='password']"),
                        "поле ввода пароля на странице авторизации админки");
            }

            static ElementData passwordFieldErrorText(String text) {
                return new ElementData(By.xpath("//div[@role='alert' and text()='" + text + "']"),
                        "текст ошибки поля ввода password на странице авторизации админки");
            }

            static ElementData submitButton() {
                return new ElementData(By.xpath("//body[@class='admin']//span[text()='Войти']//ancestor::button[@type='submit']"),
                        "кнопка 'Войти' на странице авторизации админки");
            }
        }

        static ElementData insideContainer() {
            return new ElementData(By.xpath("//body[@class='admin']//*[@id='admin-menu']"),
                    "контейнер внутренней части админки");
        }

        static ElementData menuButton(String name) {
            return new ElementData(By.xpath("//nav[@id='admin-menu']//span[text()='" + name + "']"),
                    "кнопка '" + name + "' в навигационном меню админки");
        }

        static ElementData submenuButton(String name) {
            return new ElementData(By.xpath("//nav[@class='admin-sub-menu']//*[text()='" + name + "']"),
                    "кнопка '" + name + "' в навигационном субменю админки");
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

            interface OrdersSearchPage {

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

                    static ElementData applyFilterButton() {
                        return new ElementData(By.xpath("//button[@class='icon-search button']"),
                                "кнопка \"Применить фильтр\"");
                    }

                    static ElementData clearFilterButton() {
                        return new ElementData(By.xpath("//a[@class='button icon-remove']"),
                                "кнопка \"Сбросить фильтр\"");
                    }
                }

                /** Фильтры заказов */
                interface OrdersTable {

                    static ElementData container() {
                        return new ElementData(
                                By.xpath("//table[@id='listing_orders']"),
                                "таблица с заказами на странице поиска заказов");
                    }

                    static ElementData placeholder() {
                        return new ElementData(
                                By.xpath("//div[@class='no-objects-found']//span[text()='No Orders Found']"),
                                "плейсхолдер пустого результата поиска на странице поиска заказов");
                    }

                    static ElementData column(String name) {
                        return new ElementData(
                                By.xpath("//table[@id='listing_orders']//th[text()='" + name + "']"),
                                "заголовок \"" + name + "\" в таблице с заказами на странице поиска заказов");
                    }

                    interface orderRow {

                        static ElementData container() {
                            return new ElementData(By.xpath("//*[@id='listing_orders']/tbody/tr/td[14]/a"),
                                    "строка заказа в таблице заказов в админке");
                        }

                        static ElementData orderNumber() {
                            return new ElementData(By.xpath("//*[@id='listing_orders']/tbody/tr/td[1]/span[2]/a"),
                                    "номер заказа в таблице заказов в админке");
                        }

                        static ElementData shipmentNumber() {
                            return new ElementData(By.xpath("//*[@id='listing_orders']/tbody/tr/td[1]/span[1]/a"),
                                    "номер шипмента в таблице заказов в админке");
                        }
                    }
                }
            }

            // TODO Update locators
            interface OrderDetailsPage {

                /** Страница деталей заказа в админке */
                interface Details {

                    static ElementData resumeOrderButton() {
                        return new ElementData(
                                By.xpath("//button[@class='icon-resume button']"),
                                "кнопка возобновления заказа на странице деталей заказа в админке");
                    }

                    static ElementData cancelOrderButton() {
                        return new ElementData(By.xpath("//a[@class='button icon-cancel']"),
                                "кнопка отмены заказа на странице деталей заказа в админке");
                    }

                    static ElementData confirmOrderCancellationButton() {
                        return new ElementData(By.xpath("//*[@id='new_cancellation']/fieldset/div[3]/button"),
                                "кнопка подтверждения отмены заказа");
                    }

                    static ElementData canceledOrderAttribute() {
                        return new ElementData(By.xpath("//b[contains(text(),'ЗАКАЗ ОТМЕНЕН')]"),
                                "признак отмененного заказа в админке");
                    }

                    static ElementData loyaltyProgram() {
                        return new ElementData(By.xpath("//*[@id='content']/div/table/tbody/tr[5]"),
                                "программа лояльности заказа");
                    }

                    static ElementData cancellationReasonType(int reasonTypePosition) {
                        return new ElementData(By.id("cancellation_reason_id_" + reasonTypePosition),
                                "причина отмены заказа");
                    }

                    static ElementData cancellationReasonField() {
                        return new ElementData(
                                By.id("cancellation_reason_details"),
                                "поле ввода причины отмены заказа");
                    }

                }

                /** Страница деталей оплаты заказа в админке */
                interface Payments {

                    static ElementData paymentType() {
                        return new ElementData(By.xpath("//div[@id='wrapper']//td[5]/a"),
                                "тип оплаты зпказа в админке");
                    }
                }

                /** Страница реквизитов заказа в админке */
                interface Requisites {

                    static ElementData phoneField() {
                        return new ElementData(By.id("order_ship_address_attributes_phone"),
                                "телефон получателя заказа");
                    }

                    static ElementData innField() {
                        return new ElementData(By.id("order_company_document_attributes_inn"),
                                "ИНН получателя заказа");
                    }
                }
            }
        }

        /** Раздел Users в админке */
        interface UsersSection {

            static ElementData emailField() {
                return new ElementData(
                        By.xpath("//input[@id='search_email']"),
                            "поле ввода email на странице списка пользователей");
            }

            static ElementData searchButton() {
                return new ElementData(
                        By.xpath("//button[text()='Поиск']"),
                            "кнопка 'Поиск' на странице списка пользователей");
            }

            static ElementData userlistFirstRow() {
                return new ElementData(By.xpath("//*[@id='content']/div/table/tbody/tr"),
                        "первая строка таблицы пользоватетелй");
            }

            static ElementData userEmail() {
                return new ElementData(By.xpath("//*[@id='content']//div[@class='user_email']//a"),
                        "имейл юзера в таблице пользователей в админке");
            }

            static ElementData editUserButton() {
                return new ElementData(By.xpath(
                        "//a[@data-action='edit']"),
                            "кнопка редактирования пользователя на странице списка пользователей");
            }

            static ElementData deleteUserButton() {
                return new ElementData(
                        By.xpath("//a[@data-action='remove']"),
                            "кнопка удаления пользователя на странице списка пользователей");
            }

            //static ElementData firstUserB2BLabel() {
            //    return new ElementData("B2B", By.className("b2b_client"));
            //}

            //static ElementData b2bCheckbox() {
            //    return new ElementData(By.id("q_b2b_true"));
            //}

            //static ElementData tenantCheckbox() {
            //    return new ElementData(By.id("q_tenant_eq"));
            //}

            // TODO Update locators
            /** Страница пользователя в админке */
            interface UserPage {

                static ElementData returnBackButton() {
                    return new ElementData(By.xpath("//a[text()='Вернуться к списку пользователей']"),
                            "кнопка 'Вернуться к списку пользователей' на странице редактирования пользователя");
                }

                static ElementData emailField() {
                    return new ElementData(By.xpath("//input[@id='user_email']"),
                            "поле 'Электронная почта' на странице редактирования пользователя");
                }

                static ElementData passwordField() {
                    return new ElementData(By.xpath("//input[@id='user_password']"),
                            "поле 'Пароль' на странице редактирования пользователя");
                }

                static ElementData passwordConfirmationField() {
                    return new ElementData(By.xpath("//input[@id='uuser_password_confirmation']"),
                            "поле 'Подтверждение пароля' на странице редактирования пользователя");
                }

                static ElementData b2bCheckbox() {
                    return new ElementData(By.id("user_b2b"),
                            "чекбокс признака b2b-клиента на странице редактирования пользователя");
                }

                static ElementData adminRoleCheckbox() {
                    return new ElementData(By.xpath(
                            "/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div[1]/fieldset[1]/div[2]/form[1]/div[2]" +
                                    "/div[2]/div[1]/ul[1]/li[1]/input[1]"),"чекбокс админ-прав");
                }

                static ElementData commentField() {
                    return new ElementData(By.xpath("//textarea[@id='user_customer_comment']"),
                            "поле 'Примечание' на странице редактирования пользователя");
                }

                static ElementData saveButton() {
                    return new ElementData(By.xpath("//button[text()='Изменить']"),
                            "кнопка 'Изменить' на странице редактирования пользователя");
                }

                static ElementData cancelButton() {
                    return new ElementData(By.xpath("//a[text()='Отменить']"),
                            "кнопка 'Отменить' на странице редактирования пользователя");
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
    /** Письмо с подтверждением */
    public interface EmailConfirmation{

        static ElementData lastEmail() {
            return new ElementData(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Instamart'])[1]/following::span[1]"),
                    "последнее полученное письмо от СберМаркета");
        }

        static ElementData linkText() {
            return new ElementData(By.linkText("- Показать цитируемый текст -"),
                    "цитируемый текст");
        }

        static ElementData passwordRecovery() {
            return new ElementData(By.xpath("//a[contains(text(),'Продолжить')]"),
                    "сброс пароля");
        }
    }
}
