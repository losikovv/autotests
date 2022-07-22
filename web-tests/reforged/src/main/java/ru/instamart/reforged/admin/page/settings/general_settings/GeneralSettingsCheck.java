package ru.instamart.reforged.admin.page.settings.general_settings;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface GeneralSettingsCheck extends Check, GeneralSettingsElements {

    @Step("Проверяем, что заголовок страницы общих настроек отображается")
    default void checkGeneralSettingsPageHeaderVisible() {
        waitAction().shouldBeVisible(generalSettingsPageHeader);
    }

    @Step("Проверяем, что лейбл раздела seo отображается")
    default void checkSeoSettingsSectionVisible() {
        waitAction().shouldBeVisible(seoSettingsSection);
    }

    @Step("Проверяем, что заголовок лейбл Seo-заголовка по умолчанию отображается")
    default void checkDefaultSeoHeaderLabelVisible() {
        waitAction().shouldBeVisible(defaultSeoHeaderLabel);
    }

    @Step("Проверяем, что инпут Seo-заголовка по умолчанию отображается")
    default void checkDefaultSeoHeaderVisible() {
        waitAction().shouldBeVisible(defaultSeoHeader);
    }

    @Step("Проверяем, что лейбл мета-описания по умолчанию отображается")
    default void checkDefaultMetaDescriptionLabelVisible() {
        waitAction().shouldBeVisible(defaultMetaDescriptionLabel);
    }

    @Step("Проверяем, что инпут мета-описания по умолчанию отображается")
    default void checkDefaultMetaDescriptionVisible() {
        waitAction().shouldBeVisible(defaultMetaDescription);
    }

    @Step("Проверяем, что лейбл раздела email отображается")
    default void checkEmailSettingsSectionVisible() {
        waitAction().shouldBeVisible(emailSettingsSection);
    }

    @Step("Проверяем, что лейбл емейла для обратной связи отображается")
    default void checkFeedbackEmailLabelVisible() {
        waitAction().shouldBeVisible(feedbackEmailLabel);
    }

    @Step("Проверяем, что инпут емейла для обратной связи отображается")
    default void checkFeedbackEmailVisible() {
        waitAction().shouldBeVisible(feedbackEmail);
    }

    @Step("Проверяем, что лейбл емейла пресс-службы отображается")
    default void checkPressEmailLabelVisible() {
        waitAction().shouldBeVisible(pressEmailLabel);
    }

    @Step("Проверяем, что инпут емейла пресс-службы отображается")
    default void checkPressEmailVisible() {
        waitAction().shouldBeVisible(pressEmail);
    }

    @Step("Проверяем, что лейбл емейла отдела контента отображается")
    default void checkContentEmailLabelVisible() {
        waitAction().shouldBeVisible(contentEmailLabel);
    }

    @Step("Проверяем, что инпут емейла отдела контента отображается")
    default void checkContentEmailVisible() {
        waitAction().shouldBeVisible(contentEmail);
    }

    @Step("Проверяем, что лейбл Б2Б почты отображается")
    default void checkB2bEmailLabelVisible() {
        waitAction().shouldBeVisible(b2bEmailLabel);
    }

    @Step("Проверяем, что инпут Б2Б почты отображается")
    default void checkB2bEmailVisible() {
        waitAction().shouldBeVisible(b2bEmail);
    }

    @Step("Проверяем, что лейбл емейла для писем с заказами отображается")
    default void checkOrdersEmailLabelVisible() {
        waitAction().shouldBeVisible(ordersEmailLabel);
    }

    @Step("Проверяем, что инпут емейла для писем с заказами отображается")
    default void checkOrdersEmailVisible() {
        waitAction().shouldBeVisible(ordersEmail);
    }

    @Step("Проверяем, что лейбл раздела ссылок на соцсети отображается")
    default void checkSocialsSectionVisible() {
        waitAction().shouldBeVisible(socialsSection);
    }

    @Step("Проверяем, что лейбл страницы в фейсбук отображается")
    default void checkFacebookPageLabelVisible() {
        waitAction().shouldBeVisible(facebookPageLabel);
    }

    @Step("Проверяем, что инпут страницы в фейсбук отображается")
    default void checkFacebookPageVisible() {
        waitAction().shouldBeVisible(facebookPage);
    }

    @Step("Проверяем, что лейбл страницы во вконтакте отображается")
    default void checkVkontaktePageLabelVisible() {
        waitAction().shouldBeVisible(vkontaktePageLabel);
    }

    @Step("Проверяем, что инпут страницы во вконтакте отображается")
    default void checkVkontaktePageVisible() {
        waitAction().shouldBeVisible(vkontaktePage);
    }

    @Step("Проверяем, что лейбл страницы в твиттере отображается")
    default void checkTwitterPageLabelVisible() {
        waitAction().shouldBeVisible(twitterPageLabel);
    }

    @Step("Проверяем, что инпут страницы в твиттере отображается")
    default void checkTwitterPageVisible() {
        waitAction().shouldBeVisible(twitterPage);
    }

    @Step("Проверяем, что лейбл страницы в одноклассниках отображается")
    default void checkOkPageLabelVisible() {
        waitAction().shouldBeVisible(okPageLabel);
    }

    @Step("Проверяем, что инпут страницы в одноклассниках отображается")
    default void checkOkPageVisible() {
        waitAction().shouldBeVisible(okPage);
    }

    @Step("Проверяем, что лейбл страницы в инстаграмме отображается")
    default void checkInstagramPageLabelVisible() {
        waitAction().shouldBeVisible(instagramPageLabel);
    }

    @Step("Проверяем, что инпут страницы в инстаграмме отображается")
    default void checkInstagramPageVisible() {
        waitAction().shouldBeVisible(instagramPage);
    }

    @Step("Проверяем, что лейбл ссылки саппорта в телеграм отображается")
    default void checkSupportTelegramLabelVisible() {
        waitAction().shouldBeVisible(supportTelegramLabel);
    }

    @Step("Проверяем, что лейбл ссылки саппорта в телеграм отображается")
    default void checkSupportTelegramVisible() {
        waitAction().shouldBeVisible(supportTelegram);
    }

    @Step("Проверяем, что инпут ссылки саппорта в телеграм отображается")
    default void checkSupportWhatsAppLabelVisible() {
        waitAction().shouldBeVisible(supportWhatsAppLabel);
    }

    @Step("Проверяем, что лейбл ссылки саппорта в ватсапе отображается")
    default void checkSupportWhatsAppVisible() {
        waitAction().shouldBeVisible(supportWhatsApp);
    }

    @Step("Проверяем, что лейбл раздела ссылок 'О сервисе' отображается")
    default void checkAboutServiceMobSectionVisible() {
        waitAction().shouldBeVisible(aboutServiceMobSection);
    }

    @Step("Проверяем, что лейбл ссылки 'О компании' отображается")
    default void checkLinqFaqLabelVisible() {
        waitAction().shouldBeVisible(linqFaqLabel);
    }

    @Step("Проверяем, что инпут ссылки 'О компании' отображается")
    default void checkLinqFaqVisible() {
        waitAction().shouldBeVisible(linqFaq);
    }

    @Step("Проверяем, что лейбл ссылки 'Правила работы' отображается")
    default void checkLinkRulesLabelVisible() {
        waitAction().shouldBeVisible(linkRulesLabel);
    }

    @Step("Проверяем, что инпут ссылки 'Правила работы' отображается")
    default void checkLinkRulesVisible() {
        waitAction().shouldBeVisible(linkRules);
    }

    @Step("Проверяем, что лейбл ссылки 'Официальное уведомление' отображается")
    default void checkLinkTermsLabelVisible() {
        waitAction().shouldBeVisible(linkTermsLabel);
    }

    @Step("Проверяем, что инпут ссылки 'Официальное уведомление' отображается")
    default void checkLinkTermsVisible() {
        waitAction().shouldBeVisible(linkTerms);
    }

    @Step("Проверяем, что лейбл ссылки 'О персональных данных' отображается")
    default void checkPersonalPolicyLinkLabelVisible() {
        waitAction().shouldBeVisible(linkPersonalPolicy);
    }

    @Step("Проверяем, что инпут ссылки 'О персональных данных' отображается")
    default void checkPersonalPolicyLinkVisible() {
        waitAction().shouldBeVisible(personalPolicy);
    }

    @Step("Проверяем, что лейбл ссылки 'Сберпрайм faq' отображается")
    default void checkLinkSberprimeFaqLabelVisible() {
        waitAction().shouldBeVisible(linkSberprimeFaqLabel);
    }

    @Step("Проверяем, что инпут ссылки 'Сберпрайм faq' отображается")
    default void checkLinkSberprimeFaqVisible() {
        waitAction().shouldBeVisible(linkSberprimeFaq);
    }

    @Step("Проверяем, что что лейбл раздела 'Запрос оценки' отображается")
    default void checkAppReviewSectionVisible() {
        waitAction().shouldBeVisible(appReviewSection);
    }

    @Step("Проверяем, что лейбл режима запроса оценки отображается")
    default void checkAppReviewModeLabelVisible() {
        waitAction().shouldBeVisible(appReviewModeLabel);
    }

    @Step("Проверяем, что инпут режима запроса оценки отображается")
    default void checkAppReviewModeVisible() {
        waitAction().shouldBeVisible(appReviewMode);
    }

    @Step("Проверяем, что лейбл интервала запроса оценки отображается")
    default void checkAppReviewIntervalLabelVisible() {
        waitAction().shouldBeVisible(appReviewIntervalLabel);
    }

    @Step("Проверяем, что инпут интервала запроса оценки отображается")
    default void checkAppReviewIntervalVisible() {
        waitAction().shouldBeVisible(appReviewInterval);
    }

    @Step("Проверяем, что лейбл интервала запроса оценки после креша отображается")
    default void checkAppReviewIntervalCrashLabelVisible() {
        waitAction().shouldBeVisible(appReviewIntervalCrashLabel);
    }

    @Step("Проверяем, что инпут интвервала запроса оценки после креша отображается")
    default void checkAppReviewIntervalCrashVisible() {
        waitAction().shouldBeVisible(appReviewIntervalCrash);
    }

    @Step("Проверяем, что лейбл интвервала запроса оценки после ошибки отображается")
    default void checkAppReviewIntervalErrorLabelVisible() {
        waitAction().shouldBeVisible(appReviewIntervalErrorLabel);
    }

    @Step("Проверяем, что инпут интвервала запроса оценки после ошибки отображается")
    default void checkAppReviewIntervalErrorVisible() {
        waitAction().shouldBeVisible(appReviewIntervalError);
    }

    @Step("Проверяем, что лейбл ссылки на аппстор отображается")
    default void checkAppstoreLinkLabelVisible() {
        waitAction().shouldBeVisible(appstoreLinkLabel);
    }

    @Step("Проверяем, что инпут ссылки на аппстор отображается")
    default void checkAppstoreLinkVisible() {
        waitAction().shouldBeVisible(appstoreLink);
    }

    @Step("Проверяем, что лейбл ссылки на гугл плей отображается")
    default void checkGooglePlayLinkLabelVisible() {
        waitAction().shouldBeVisible(googlePlayLinkLabel);
    }

    @Step("Проверяем, что инпут ссылки на гугл плей отображается")
    default void checkGooglePlayLinkVisible() {
        waitAction().shouldBeVisible(googlePlayLink);
    }

    @Step("Проверяем, что лейбл ссылки на app gallery отображается")
    default void checkAppGalleryLinkLabelVisible() {
        waitAction().shouldBeVisible(appGalleryLinkLabel);
    }

    @Step("Проверяем, что инпут ссылки на app gallery отображается")
    default void checkAppGalleryLinkVisible() {
        waitAction().shouldBeVisible(appGalleryLink);
    }

    @Step("Проверяем, что лейбл раздела версии моб приложений отображается")
    default void checkMobileAppVersionSectionVisible() {
        waitAction().shouldBeVisible(mobileAppVersionSection);
    }

    @Step("Проверяем, что лейбл минимальной версии айос отображается")
    default void checkMinimalIosVersionLabelVisible() {
        waitAction().shouldBeVisible(minimalIosVersionLabel);
    }

    @Step("Проверяем, что инпут минимальной версии айос отображается")
    default void checkMinimalIosVersionVisible() {
        waitAction().shouldBeVisible(minimalIosVersion);
    }

    @Step("Проверяем, что лейбл рекомендуемой версии айос отображается")
    default void checkRecommendIosVersionLabelVisible() {
        waitAction().shouldBeVisible(recommendIosVersionLabel);
    }

    @Step("Проверяем, что инпут рекомендуемой версии айос отображается")
    default void checkRecommendIosVersionVisible() {
        waitAction().shouldBeVisible(recommendIosVersion);
    }

    @Step("Проверяем, что лейбл минимальной версии андроида отображается")
    default void checkMinimalAndroidVersionLabelVisible() {
        waitAction().shouldBeVisible(minimalAndroidVersionLabel);
    }

    @Step("Проверяем, что инпут минимальной версии андроида отображается")
    default void checkMinimalAndroidVersionVisible() {
        waitAction().shouldBeVisible(minimalAndroidVersion);
    }

    @Step("Проверяем, что лейбл рекомендуемой версии андроид отображается")
    default void checkRecommendAndroidVersionLabelVisible() {
        waitAction().shouldBeVisible(recommendAndroidVersionLabel);
    }

    @Step("Проверяем, что инпут рекомендуемой версии андроид отображается")
    default void checkRecommendAndroidVersionVisible() {
        waitAction().shouldBeVisible(recommendAndroidVersion);
    }

    @Step("Проверяем, что лейбл раздела аналитики в мобильных приложениях отображается")
    default void checkMobileAnalyticsSectionVisible() {
        waitAction().shouldBeVisible(mobileAnalyticsSection);
    }

    @Step("Проверяем, что лейбл урла аналитики на айос отображается")
    default void checkAnalyticsUrlIosLabelVisible() {
        waitAction().shouldBeVisible(analyticsUrlIosLabel);
    }

    @Step("Проверяем, что инпут урла аналитики на айос отображается")
    default void checkAnalyticsUrlIosVisible() {
        waitAction().shouldBeVisible(analyticsUrlIos);
    }

    @Step("Проверяем, что лейбл ключа аналитики на айос отображается")
    default void checkAnalyticsKeyIosLabelVisible() {
        waitAction().shouldBeVisible(analyticsKeyIosLabel);
    }

    @Step("Проверяем, что инпут ключа аналитики на айос отображается")
    default void checkAnalyticsKeyIosVisible() {
        waitAction().shouldBeVisible(analyticsKeyIos);
    }

    @Step("Проверяем, что лейбл урла аналитики на андроид отображается")
    default void checkAnalyticsUrlAndroidLabelVisible() {
        waitAction().shouldBeVisible(analyticsUrlAndroidLabel);
    }

    @Step("Проверяем, что инпут урла аналитики на андроид отображается")
    default void checkAnalyticsUrlAndroidVisible() {
        waitAction().shouldBeVisible(analyticsUrlAndroid);
    }

    @Step("Проверяем, что лейбл ключа аналитики на андроид отображается")
    default void checkAnalyticsKeyAndroidLabelVisible() {
        waitAction().shouldBeVisible(analyticsKeyAndroidLabel);
    }

    @Step("Проверяем, что инпут ключа аналитики на андроид отображается")
    default void checkAnalyticsKeyAndroidVisible() {
        waitAction().shouldBeVisible(analyticsKeyAndroid);
    }

    @Step("Проверяем, что лейбл раздела вайтлиста доменов для моб приложений отображается")
    default void checkMobileAppDomenWhitelistSectionVisible() {
        waitAction().shouldBeVisible(mobileAppDomenWhitelistSection);
    }

    @Step("Проверяем, что лейбл доменов для моб приложений отображается")
    default void checkDomensLabelVisible() {
        waitAction().shouldBeVisible(domensLabel);
    }

    @Step("Проверяем, что инпут доменов для моб приложений отображается")
    default void checkDomensVisible() {
        waitAction().shouldBeVisible(domens);
    }

    @Step("Проверяем, что лейбл раздела фрод модерации отображается")
    default void checkFraudModerationSectionVisible() {
        waitAction().shouldBeVisible(fraudModerationSection);
    }

    @Step("Проверяем, что лейбл лимита для премодерации отображается")
    default void checkModerationLimitLabelVisible() {
        waitAction().shouldBeVisible(moderationLimitLabel);
    }

    @Step("Проверяем, что инпут лимита для премодерации отображается")
    default void checkModerationLimitVisible() {
        waitAction().shouldBeVisible(moderationLimit);
    }

    @Step("Проверяем, что лейбл заголовка настроек валюты отображается")
    default void checkCurrencySettingsSectionVisible() {
        waitAction().shouldBeVisible(currencySettingsSection);
    }

    @Step("Проверяем, что лейбл выбора валюты отображается")
    default void checkCurrencyLabelVisible() {
        waitAction().shouldBeVisible(currencyLabel);
    }

    @Step("Проверяем, что селектор валюты отображается")
    default void checkCurrencyDropDownVisible() {
        waitAction().shouldBeVisible(currencyDropDown);
    }

    @Step("Проверяем, что кнопка применения изменений отображается")
    default void checkSaveButtonVisible() {
        waitAction().shouldBeVisible(saveButton);
    }

    @Step("Проверяем, что кнопка отменить отображается")
    default void checkCancelButtonVisible() {
        waitAction().shouldBeVisible(cancelButton);
    }
}
