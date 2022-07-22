package ru.instamart.reforged.admin.page.settings.general_settings;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;

public interface GeneralSettingsElements {
    Element generalSettingsPageHeader =  new Element(By.xpath("//header[@data-qa='content_header']"), "Заголовок страницы общих настроек");

    Element seoSettingsSection  = new Element(By.xpath("//h5[contains(text(), 'SEO')]"),"Лейбл раздела seo");
    Element defaultSeoHeaderLabel = new Element(By.xpath("//label[@for='seo.defaultSeoTitle']"), "Лэйбл Seo-заголовка по умолчанию");
    Input defaultSeoHeader = new Input(By.id("seo.defaultSeoTitle"), "Инпут Seo-заголовка по умолчанию");
    Element defaultMetaDescriptionLabel = new Element(By.xpath("//label[@for='seo.defaultMetaDescription']"), "Лейбл мета-описания по умолчанию");
    Input defaultMetaDescription = new Input(By.id("seo.defaultMetaDescription"), "Инпут мета-описания по умолчанию");

    Element emailSettingsSection  = new Element(By.xpath("//h5[contains(text(), 'Emails')]"),"Лейбл раздела email");
    Element feedbackEmailLabel = new Element(By.xpath("//label[@for='emails.feedbackEmail']"), "Лейбл емейла для обратной связи");
    Input feedbackEmail = new Input(By.id("emails.feedbackEmail"), "Инпут емейла для обратной связи");
    Element pressEmailLabel = new Element(By.xpath("//label[@for='emails.pressEmail']"), "Лейбл емейла пресс-службы");
    Input pressEmail = new Input(By.id("emails.pressEmail"), "Инпут емейла пресс-службы");
    Element contentEmailLabel = new Element(By.xpath("//label[@for='emails.contentEmail']"), "Лейбл емейла отдела контента");
    Input contentEmail = new Input(By.id("emails.contentEmail"), "Инпут емейла отдела контента");
    Element b2bEmailLabel = new Element(By.xpath("//label[@for='emails.b2b_email']"), "Лейбл Б2Б почты");
    Input b2bEmail = new Input(By.id("emails.b2b_email"), "Инпут Б2Б почты");
    Element ordersEmailLabel = new Element(By.xpath("//label[@for='emails.ordersEmail']"), "Лейбл емейла для писем с заказами");
    Input ordersEmail = new Input(By.id("emails.ordersEmail"), "Инпут емейла для писем с заказами");

    Element socialsSection  = new Element(By.xpath("//h5[contains(text(), 'Ссылки на соцсети')]"),"Лейбл раздела ссылок на соцсети");
    Element facebookPageLabel = new Element(By.xpath("//label[@for='socialNetworks.facebookPage']"), "Лейбл страницы в фейсбук");
    Input facebookPage = new Input(By.id("socialNetworks.facebookPage"), "Инпут страницы в фейсбук");
    Element vkontaktePageLabel = new Element(By.xpath("//label[@for='socialNetworks.vkontaktePage']"), "Лейбл страницы во вконтакте");
    Input vkontaktePage = new Input(By.id("socialNetworks.vkontaktePage"), "Инпут страницы во вконтакте");
    Element twitterPageLabel = new Element(By.xpath("//label[@for='socialNetworks.twitterPage']"), "Лейбл страницы в твиттере");
    Input twitterPage = new Input(By.id("socialNetworks.twitterPage"), "Инпут страницы в твиттере");
    Element okPageLabel = new Element(By.xpath("//label[@for='socialNetworks.okPage']"), "Лейбл страницы в одноклассниках");
    Input okPage = new Input(By.id("socialNetworks.okPage"), "Инпут страницы в одноклассниках");
    Element instagramPageLabel = new Element(By.xpath("//label[@for='socialNetworks.instagramPage']"), "Лейбл страницы в инстаграмме");
    Input instagramPage = new Input(By.id("socialNetworks.instagramPage"), "Инпут страницы в инстаграмме");
    Element supportTelegramLabel = new Element(By.xpath("//label[@for='socialNetworks.supportTelegram']"), "Лейбл ссылки саппорта в телеграмме");
    Input supportTelegram = new Input(By.id("socialNetworks.supportTelegram"), "Инпут ссылки саппорта в телеграм");
    Element supportWhatsAppLabel = new Element(By.xpath("//label[@for='socialNetworks.supportWhatsapp']"), "Лейбл ссылки саппорта в ватсапеп");
    Input supportWhatsApp = new Input(By.id("socialNetworks.supportWhatsapp"), "Инпут ссылки саппорта в ватсапп");

    Element aboutServiceMobSection  = new Element(By.xpath("//h5[contains(text(), 'Ссылки \"О сервисе\"')]"),"Лейбл раздела ссылки 'О сервисе'");
    Element linqFaqLabel = new Element(By.xpath("//label[@for='mobileLinks.linkFaq']"), "Лейбл ссылки 'О компании'");
    Input linqFaq = new Input(By.id("mobileLinks.linkFaq"), "Инпут ссылки 'О компании'");
    Element linkRulesLabel = new Element(By.xpath("//label[@for='mobileLinks.linkRules']"), "Лейбл ссылки 'Правила работы'");
    Input linkRules = new Input(By.id("mobileLinks.linkRules"), "Инпут ссылки 'Правила работы'");
    Element linkTermsLabel = new Element(By.xpath("//label[@for='mobileLinks.linkTerms']"), "Лейбл ссылки 'Официальное уведомление'");
    Input linkTerms = new Input(By.id("mobileLinks.linkTerms"), "Инпут ссылки 'Официальное уведомление'");
    Element linkPersonalPolicy = new Element(By.xpath("//label[@for='mobileLinks.linkPersonalDataProcessingPolicy']"), "Лейбл ссылки 'О персональных данных'");
    Input personalPolicy = new Input(By.id("mobileLinks.linkPersonalDataProcessingPolicy"), "Инпут ссылки 'О персональных данных'");
    Element linkSberprimeFaqLabel = new Element(By.xpath("//label[@for='mobileLinks.linkSberprimeFaq']"), "Лейбл ссылки 'Сберпрайм faq'");
    Input linkSberprimeFaq = new Input(By.id("mobileLinks.linkSberprimeFaq"), "Инпут ссылки 'Сберпрайм faq'");

    Element appReviewSection  = new Element(By.xpath("//h5[contains(text(), 'Запрос оценки')]"),"Лейбл раздела 'Запрос оценки'");
    Element appReviewModeLabel = new Element(By.xpath("//label[@for='appReview.appReviewMode']"), "Лейбл режима запроса оценки");
    Input appReviewMode = new Input(By.id("appReview.appReviewMode"), "Инпут режима запроса оценки");
    Element appReviewIntervalLabel = new Element(By.xpath("//label[@for='appReview.appReviewIntervalRequest']"), "Лейбл интервала запроса оценки");
    Input appReviewInterval = new Input(By.id("appReview.appReviewIntervalRequest"), "Инпут интервала запроса оценки");
    Element appReviewIntervalCrashLabel = new Element(By.xpath("//label[@for='appReview.appReviewIntervalCrash']"), "Лейбл интервала запроса оценки после креша");
    Input appReviewIntervalCrash = new Input(By.id("appReview.appReviewIntervalCrash"), "Инпут интвервала запроса оценки после креша");
    Element appReviewIntervalErrorLabel = new Element(By.xpath("//label[@for='appReview.appReviewIntervalNetworkError']"), "Лейбл интвервала запроса оценки после ошибки");
    Input appReviewIntervalError = new Input(By.id("appReview.appReviewIntervalNetworkError"), "Инпут интвервала запроса оценки после ошибки");
    Element appstoreLinkLabel = new Element(By.xpath("//label[@for='appReview.appReviewAppStoreLink']"), "Лейбл ссылки на аппстор");
    Input appstoreLink = new Input(By.id("appReview.appReviewAppStoreLink"), "Инпут ссылки на аппстор");
    Element googlePlayLinkLabel = new Element(By.xpath("//label[@for='appReview.appReviewGooglePlayLink']"), "Лейбл ссылки на гугл плей");
    Input googlePlayLink = new Input(By.id("appReview.appReviewGooglePlayLink"), "Инпут ссылки на гугл плей");
    Element appGalleryLinkLabel = new Element(By.xpath("//label[@for='appReview.appReviewAppGalleryLink']"), "Лейбл ссылки на app gallery");
    Input appGalleryLink = new Input(By.id("appReview.appReviewAppGalleryLink"), "Инпут ссылки на app gallery");

    Element mobileAppVersionSection  = new Element(By.xpath("//h5[contains(text(), 'Версии мобильных приложений')]"),"Лейбл раздела версии моб приложений");
    Element minimalIosVersionLabel = new Element(By.xpath("//label[@for='appVersions.appIosMinVersion']"), "Лейбл минимальной версии айос");
    Input minimalIosVersion = new Input(By.id("appVersions.appIosMinVersion"), "Инпут минимальной версии айос");
    Element recommendIosVersionLabel = new Element(By.xpath("//label[@for='appVersions.appIosRecommendedVersion']"), "Лейбл рекомендуемой версии айос");
    Input recommendIosVersion = new Input(By.id("appVersions.appIosRecommendedVersion"), "Инпут рекомендуемой версии айос");
    Element minimalAndroidVersionLabel = new Element(By.xpath("//label[@for='appVersions.appAndroidMinVersion']"), "Лейбл минимальной версии андроида");
    Input minimalAndroidVersion = new Input(By.id("appVersions.appAndroidMinVersion"), "Инпут минимальной версии андроида");
    Element recommendAndroidVersionLabel = new Element(By.xpath("//label[@for='appVersions.appAndroidRecommendedVersion']"), "Лейбл рекомендуемой версии андроид");
    Input recommendAndroidVersion = new Input(By.id("appVersions.appAndroidRecommendedVersion"), "Инпут рекомендуемой версии андроид");

    Element mobileAnalyticsSection  = new Element(By.xpath("//h5[contains(text(), 'Аналитика в мобильных приложениях')]"),"Лейбл раздела аналитики в мобильных приложениях");
    Element analyticsUrlIosLabel = new Element(By.xpath("//label[@for='appAnalytics.appIosAnalyticsUrl']"), "Лейбл урла аналитики на айос");
    Input analyticsUrlIos = new Input(By.id("appAnalytics.appIosAnalyticsUrl"), "Инпут урла аналитики на айос");
    Element analyticsKeyIosLabel = new Element(By.xpath("//label[@for='appAnalytics.appIosAnalyticsKey']"), "Лейбл ключа аналитики на айос");
    Input analyticsKeyIos = new Input(By.id("appAnalytics.appIosAnalyticsKey"), "Инпут ключа аналитики на айос");
    Element analyticsUrlAndroidLabel = new Element(By.xpath("//label[@for='appAnalytics.appAndroidAnalyticsUrl']"), "Лейбл урла аналитики на андроид");
    Input analyticsUrlAndroid = new Input(By.id("appAnalytics.appAndroidAnalyticsUrl"), "Инпут урла аналитики на андроид");
    Element analyticsKeyAndroidLabel = new Element(By.xpath("//label[@for='appAnalytics.appAndroidAnalyticsKey']"), "Лейбл ключа аналитики на андроид");
    Input analyticsKeyAndroid = new Input(By.id("appAnalytics.appAndroidAnalyticsKey"), "Инпут ключа аналитики на андроид");

    Element mobileAppDomenWhitelistSection  = new Element(By.xpath("//h5[contains(text(), 'Белый список доменов для мобильных приложений')]"),"Лейбл раздела вайтлиста доменов для моб приложений");
    Element domensLabel = new Element(By.xpath("//label[@for='appWebViewWhitelist.appWebViewWhitelist']"), "Лейбл доменов для моб приложений");
    Input domens = new Input(By.id("appWebViewWhitelist.appWebViewWhitelist"), "Инпут доменов для моб приложений");

    Element fraudModerationSection  = new Element(By.xpath("//h5[contains(text(), 'Модерация потенциального фрода заказов')]"),"Лейбл раздела фрод модерации");
    Element moderationLimitLabel = new Element(By.xpath("//label[@for='orderFraudModeration.orderFraudModerationLimit']"), "Лейбл лимита для премодерации");
    Input moderationLimit = new Input(By.id("orderFraudModeration.orderFraudModerationLimit"), "Инпут лимита для премодерации");

    Element currencySettingsSection  = new Element(By.xpath("//h5[contains(text(), 'Настройки валюты')]"),"Лейбл заголовка настроек валюты");
    Element currencyLabel = new Element(By.xpath("//label[contains(text(), 'Выбрать валюту')]"), "Лейбл выбора валюты");
    Selector currencyDropDown = new Selector(By.xpath("//h5[contains(text(), 'Настройки валюты')]/following-sibling::div//input[@type='search']"), "Селектор валюты");

    Button saveButton = new Button(By.xpath("//button[@type='submit']/span[text()='Сохранить']"), "Кнопка применения изменений");
    Link cancelButton = new Link(By.xpath("//button[@type='submit']/span[text()='Отмена']"), "Кнопка отменить");
}
