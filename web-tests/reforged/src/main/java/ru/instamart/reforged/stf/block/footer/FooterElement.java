package ru.instamart.reforged.stf.block.footer;

import org.openqa.selenium.By;
import ru.instamart.kraken.data.TestVariables;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;

public interface FooterElement {

    Element container = new Element(By.id("new-home-footer"), "блок футера");
    Element logo = new Element(By.xpath("//footer//div[@class='footer__logo']"), "логотип 'Сбермаркет' в футере");

    Element sbermarketTitle = new Element(By.xpath("//footer//div[@class='footer__title' and text()='СберМаркет']"),
            "подзаголовок 'Сбермаркет' в футере");
    Link aboutCompanyLink = new Link(By.xpath("//footer//a[@href='/about']"),
            "ссылка 'О компании' в футере");
    Link contactsLink = new Link(By.xpath("//footer//a[@href='/contacts_2']"),
            "ссылка 'Контакты' в футере");
    Link vacanciesLink = new Link(By.xpath("//footer//a[@href='https://job.sbermarket.ru/']"),
            "ссылка 'Вакансии' в футере");
    Link documentsLink = new Link(By.xpath("//footer//a[@href='/terms-sbermarket']"),
            "ссылка 'Документы' в футере");
    //Изменили в рамках https://instamart.atlassian.net/browse/STF-9748
    Link partnersLink = new Link(By.xpath("//a[text()='Стать партнером']"),
            "ссылка 'Стать партнером' в футере");


    Element customerHelpTitle = new Element(By.xpath("//footer//div[@class='footer__title' and contains(text(),'Помощь')]"),
            "подзаголовок 'Помощь покупателю' в футере");
    Link howWeWork = new Link(By.xpath("//footer//a[@href='/how-we-work-video']"),
            "ссылка 'Как мы работаем' в футере");
    Link deliveryZone = new Link(By.xpath("//footer//button[text()='Зоны доставки']"),
            "ссылка 'Зоны доставки' в футере");
    Link deliveryAndPayment = new Link(By.xpath("//footer//a[@href='/delivery2']"),
            "ссылка 'Доставка и оплата' в футере");
    Link help = new Link(By.xpath("//footer//a[@href='/faq-sbermarket']"),
            "ссылка 'Помощь' в футере");


    Link hotlinePhoneNumber = new Link(By.xpath("//footer//a[text()='" + TestVariables.CompanyParams.companyHotlinePhoneNumber + "']"),
            "телефон-ссылка горячей линии в футере");
    Element hotlineWorkHoursText = new Element(By.xpath("//footer//span[text()='" + TestVariables.CompanyParams.companyHotlineWorkhours + "']"),
            "время работы горячей линии в футере");

    Button facebookButton = new Button(By.xpath("//footer//div[@class='footer__networks']//a[@href='" + TestVariables.CompanyParams.companyFacebookLink + "']"),
            "кнопка Facebook в футере");
    Button vkontakteButton = new Button(By.xpath("//footer//div[@class='footer__networks']//a[@href='" + TestVariables.CompanyParams.companyVkontakteLink + "']"),
            "кнопка Вконтакте в футере");
    Button instagramButton = new Button(By.xpath("//footer//div[@class='footer__networks']//a[contains(@href,'" + TestVariables.CompanyParams.companyInstagramLink + "')]"),
            "кнопка Instagram в футере");
    Button twitterButton = new Button(By.xpath("//footer//div[@class='footer__networks']//a[@href='" + TestVariables.CompanyParams.companyTwitterLink + "']"),
            "кнопка Twitter в футере");

    Button googlePlayButton = new Button(By.xpath("//a[@data-qa='home_landing_google_play_footer']"),
            "кнопка GooglePlay в футере");
    Button appstoreButton = new Button(By.xpath("//a[@data-qa='home_landing_app_store_footer']"),
            "кнопка Appstore в футере");
    Button huaweiButton = new Button(By.xpath("//a[@data-qa='home_landing_huawei_store_footer']"),
            "кнопка huaweiStore в футере");


    Link returnsPolicyLink = new Link(By.xpath("//footer//a[@href='/rules-sbermarket']"),
            "ссылка на политику возвратов в футере");
    Link personalDataPolicyLink = new Link(By.xpath("//footer//a[@href='/docs/personal_data_processing_policy.pdf']"),
            "ссылка на политику обработки персональных данных в футере");
    Link publicOfferLink = new Link(By.xpath("//footer//a[@href='/terms-sbermarket']"),
            "ссылка на Официальное уведомление в футере");
}
