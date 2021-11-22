package ru.instamart.reforged.admin.page.settings.general_settings;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;

public interface GeneralSettingsElements {
    Element generalSettingsPageHeader =  new Element(By.xpath("//h1[@class='page-title ≈']"), "Заголовок страницы общих настроек");

    Element seoSettingsSection  = new Element(By.xpath("//h5[@class='sidebar-title']/span[contains(text(), 'SEO')]"),"Лейбл раздела seo");
    Element defaultSeoHeaderLabel = new Element(By.xpath("//label[@for='default_seo_title']"), "Лэйбл Seo-заголовка по умолчанию");
    Input defaultSeoHeader = new Input(By.id("default_seo_title"), "Инпут Seo-заголовка по умолчанию");
    Element defaultMetaDescriptionLabel = new Element(By.xpath("//label[@for='default_meta_description']"), "Лейбл мета-описания по умолчанию");
    Input defaultMetaDescription = new Input(By.id("default_meta_description"), "Инпут мета-описания по умолчанию");

    Element emailSettingsSection  = new Element(By.xpath("//h5[@class='sidebar-title']/span[contains(text(), 'Emails')]"),"Лейбл раздела email");
    Element feedbackEmailLabel = new Element(By.xpath("//label[@for='feedback_email']"), "Лейбл емейла для обратной связи");
    Input feedbackEmail = new Input(By.id("feedback_email"), "Инпут емейла для обратной связи");
    Element pressEmailLabel = new Element(By.xpath("//label[@for='press_email']"), "Лейбл емейла пресс-службы");
    Input pressEmail = new Input(By.id("press_email"), "Инпут емейла пресс-службы");
    Element contentEmailLabel = new Element(By.xpath("//label[@for='content_email']"), "Лейбл емейла отдела контента");
    Input contentEmail = new Input(By.id("content_email"), "Инпут емейла отдела контента");
    Element b2bEmailLabel = new Element(By.xpath("//label[@for='b2b_email']"), "Лейбл Б2Б почты");
    Input b2bEmail = new Input(By.id("b2b_email"), "Инпут Б2Б почты");
    Element ordersEmailLabel = new Element(By.xpath("//label[@for='orders_email']"), "Лейбл емейла для писем с заказами");
    Input ordersEmail = new Input(By.id("orders_email"), "Инпут емейла для писем с заказами");

    Element socialsSection  = new Element(By.xpath("//h5[@class='sidebar-title']/span[contains(text(), 'Ссылки на соцсети')]"),"Лейбл раздела ссылок на соцсети");
    Element facebookPageLabel = new Element(By.xpath("//label[@for='facebook_page']"), "Лейбл страницы в фейсбук");
    Input facebookPage = new Input(By.id("facebook_page"), "Инпут страницы в фейсбук");
    Element vkontaktePageLabel = new Element(By.xpath("//label[@for='vkontakte_page']"), "Лейбл страницы во вконтакте");
    Input vkontaktePage = new Input(By.id("vkontakte_page"), "Инпут страницы во вконтакте");
    Element twitterPageLabel = new Element(By.xpath("//label[@for='twitter_page']"), "Лейбл страницы в твиттере");
    Input twitterPage = new Input(By.id("twitter_page"), "Инпут страницы в твиттере");
    Element okPageLabel = new Element(By.xpath("//label[@for='ok_page']"), "Лейбл страницы в одноклассниках");
    Input okPage = new Input(By.id("ok_page"), "Инпут страницы в одноклассниках");
    Element instagramPageLabel = new Element(By.xpath("//label[@for='instagram_page']"), "Лейбл страницы в инстаграмме");
    Input instagramPage = new Input(By.id("instagram_page"), "Инпут страницы в инстаграмме");
    Element supportTelegramLabel = new Element(By.xpath("//label[@for='support_telegram']"), "Лейбл ссылки саппорта в телеграмме");
    Input supportTelegram = new Input(By.id("support_telegram"), "Инпут ссылки саппорта в телеграм");
    Element supportWhatsAppLabel = new Element(By.xpath("//label[@for='support_whatsapp']"), "Лейбл ссылки саппорта в ватсапеп");
    Input supportWhatsApp = new Input(By.id("support_whatsapp"), "Инпут ссылки саппорта в ватсапп");

    Element aboutServiceMobSection  = new Element(By.xpath("//h5[@class='sidebar-title']/span[contains(text(), 'Ссылки \"О сервисе\"')]"),"Лейбл раздела ссылки 'О сервисе'");
    Element linqFaqLabel = new Element(By.xpath("//label[@for='link_faq']"), "Лейбл ссылки 'О компании'");
    Input linqFaq = new Input(By.id("link_faq"), "Инпут ссылки 'О компании'");
    Element linkRulesLabel = new Element(By.xpath("//label[@for='link_rules']"), "Лейбл ссылки 'Правила работы'");
    Input linkRules = new Input(By.id("link_rules"), "Инпут ссылки 'Правила работы'");
    Element linkTermsLabel = new Element(By.xpath("//label[@for='link_terms']"), "Лейбл ссылки 'Официальное уведомление'");
    Input linkTerms = new Input(By.id("link_terms"), "Инпут ссылки 'Официальное уведомление'");
    Element linkPersonalPolicy = new Element(By.xpath("//label[@for='link_personal_data_processing_policy']"), "Лейбл ссылки 'О персональных данных'");
    Input personalPolicy = new Input(By.id("link_personal_data_processing_policy"), "Инпут ссылки 'О персональных данных'");
    Element linkSberprimeFaqLabel = new Element(By.xpath("//label[@for='link_sberprime_faq']"), "Лейбл ссылки 'Сберпрайм faq'");
    Input linkSberprimeFaq = new Input(By.id("link_sberprime_faq"), "Инпут ссылки 'Сберпрайм faq'");

    Element appReviewSection  = new Element(By.xpath("//h5[@class='sidebar-title']/span[contains(text(), 'Запрос оценки')]"),"Лейбл раздела 'Запрос оценки'");
    Element appReviewModeLabel = new Element(By.xpath("//label[@for='app_review_mode']"), "Лейбл режима запроса оценки");
    Input appReviewMode = new Input(By.id("app_review_mode"), "Инпут режима запроса оценки");
    Element appReviewIntervalLabel = new Element(By.xpath("//label[@for='app_review_interval_request']"), "Лейбл интервала запроса оценки");
    Input appReviewInterval = new Input(By.id("app_review_interval_request"), "Инпут интервала запроса оценки");
    Element appReviewIntervalCrashLabel = new Element(By.xpath("//label[@for='app_review_interval_crash']"), "Лейбл интервала запроса оценки после креша");
    Input appReviewIntervalCrash = new Input(By.id("app_review_interval_crash"), "Инпут интвервала запроса оценки после креша");
    Element appReviewIntervalErrorLabel = new Element(By.xpath("//label[@for='app_review_interval_network_error']"), "Лейбл интвервала запроса оценки после ошибки");
    Input appReviewIntervalError = new Input(By.id("app_review_interval_network_error"), "Инпут интвервала запроса оценки после ошибки");
    Element appstoreLinkLabel = new Element(By.xpath("//label[@for='app_review_app_store_link']"), "Лейбл ссылки на аппстор");
    Input appstoreLink = new Input(By.id("app_review_app_store_link"), "Инпут ссылки на аппстор");
    Element googlePlayLinkLabel = new Element(By.xpath("//label[@for='app_review_google_play_link']"), "Лейбл ссылки на гугл плей");
    Input googlePlayLink = new Input(By.id("app_review_google_play_link"), "Инпут ссылки на гугл плей");
    Element appGalleryLinkLabel = new Element(By.xpath("//label[@for='app_review_app_gallery_link']"), "Лейбл ссылки на app gallery");
    Input appGalleryLink = new Input(By.id("app_review_app_gallery_link"), "Инпут ссылки на app gallery");

    Element currencySettingsSection  = new Element(By.xpath("//legend[contains(text(), 'Настройки валюты')]"),"Лейбл заголовка настроек валюты");
    Element currencyLabel = new Element(By.xpath("//label[contains(text(), 'Выбрать валюту')]"), "Лейбл выбора валюты");
    Selector currenccyDropDown = new Selector(By.id("currency"), "Селектор валюты");
    Button saveButton = new Button(By.xpath("//button[contains(@class, 'refresh')]"), "Кнопка применения изменений");
    Link cancelButton = new Link(By.xpath("//a[contains(@class, 'remove')]"), "Кнопка отменить");
}
