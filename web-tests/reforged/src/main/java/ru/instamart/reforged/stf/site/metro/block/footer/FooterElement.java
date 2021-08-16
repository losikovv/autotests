package ru.instamart.reforged.stf.site.metro.block.footer;

import org.openqa.selenium.By;
import ru.instamart.kraken.testdata.TestVariables;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;

public interface FooterElement {

    Element footer = new Element(By.xpath("//footer"),
            "контейнер для футера");
    Element logo = new Element(By.xpath("//footer//*[@class='logo-default']"),
            "лого сбермаркета в футере");

    Element copyrightText = new Element(By.xpath("//footer//p[@class='tenant-footer__copyright-text']"),
            "текст о партнерстве в футере");
    Element partnershipLogo = new Element(By.xpath("//footer//*[@class='logo-partner-metro']"),
            "лого компании-партнера в футере");
    Element copyrightShopName = new Element(By.xpath("//footer//p[@class='tenant-footer__copyright']"),
            "полное название компании-партнера в футере");

    Element hotlineText = new Element(By.xpath("//footer//p[@class='tenant-footer__cell-header']"),
            "текст-заголовок о работе службы поддержки в футере");
    Element hotlineWorkHoursText = new Element(By.xpath("//footer//span[text()='" + TestVariables.CompanyParams.companyHotlineWorkhoursShort + "']"),
            "время работы горячей линии в футере");
    Link hotlinePhoneNumber = new Link(By.xpath("//footer//a[text()='" + TestVariables.CompanyParams.companyHotlinePhoneNumber + "']"),
            "номер телефона горячей линии в футере");

    Element disclaimer = new Element(By.xpath("//footer//div[@class='tenant-footer__disclaimer']"),
            "дисклеймер об оказании услуг в футере");

    Link customerHelp = new Link(By.xpath("//footer//a[contains(@href, '/faq-metro')]"),
            "раздел 'Помощь покупателю' в футере");
    Link returnsPolicyLink = new Link(By.xpath("//footer//a[contains(@href, '/rules-metro')]"),
            "раздел 'Политика возвратов' в футере");
    Link publicOfferLink = new Link(By.xpath("//footer//a[contains(@href, '/terms-metro')]"),
            "раздел 'Официальное уведомление' в футере");
    Link personalDataPolicyLink = new Link(By.xpath("//footer//a[contains(@href, '/docs/personal_data_processing_policy.pdf')]"),
            "раздел 'Политика обработки данных' в футере");

    Link aboutCompany = new Link(By.xpath("//footer//a[contains(@href, '/about-metro')]"),
            "раздел 'О компании' в футере");
    Button deliveryZone = new Button(By.xpath("//footer//button[contains(@data-url,'/stores/1/shipping_methods')]"),
            "раздел 'Зоны доставки' в футере");
    Link deliveryAndPayment = new Link(By.xpath("//footer//a[contains(@href, '/delivery-metro')]"),
            "раздел 'Доставка и оплата в футере'");
    Button paymentInfo = new Button(By.xpath("//footer//button[contains(@data-url, '/payment_methods')]"),
            "раздел 'Оплата' в футере");
}
