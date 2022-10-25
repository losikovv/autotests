package ru.instamart.reforged.hr_ops_partners.page.home;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.hr_ops_partners.block.cities.Cities;
import ru.instamart.reforged.hr_ops_partners.block.faq.Faq;
import ru.instamart.reforged.hr_ops_partners.block.footer.Footer;
import ru.instamart.reforged.hr_ops_partners.block.header.Header;
import ru.instamart.reforged.hr_ops_partners.block.main_banner.MainBanner;
import ru.instamart.reforged.hr_ops_partners.block.vacancy_cards.call_center_operator.CallCenterOperator;
import ru.instamart.reforged.hr_ops_partners.block.vacancy_cards.collector.Collector;
import ru.instamart.reforged.hr_ops_partners.block.vacancy_cards.collector_courier.CollectorCourier;
import ru.instamart.reforged.hr_ops_partners.block.vacancy_cards.collector_courier_avto.CollectorCourierAuto;
import ru.instamart.reforged.hr_ops_partners.block.vacancy_cards.driver_courier.DriverCourier;
import ru.instamart.reforged.hr_ops_partners.frame.VacancyNotAvailableModal;
import ru.instamart.reforged.hr_ops_partners.frame.apply_form_modal.ApplyFormModal;
import ru.instamart.reforged.hr_ops_partners.frame.confirn_SMS_form_modal.ConfirmSMSModal;

public interface HomeElement {

    Header header = new Header();
    VacancyNotAvailableModal notAvailableModal = new VacancyNotAvailableModal();
    MainBanner mainBanner = new MainBanner();
    Collector collector = new Collector();
    CollectorCourier collectorCourier = new CollectorCourier();
    CollectorCourierAuto collectorCourierAuto = new CollectorCourierAuto();
    DriverCourier driverCourier = new DriverCourier();
    CallCenterOperator callCenterOperator = new CallCenterOperator();
    Cities cities = new Cities();
    Faq faq = new Faq();
    Footer footer = new Footer();
    ApplyFormModal applyFormModal = new ApplyFormModal();
    ConfirmSMSModal confirmSMSModal = new ConfirmSMSModal();

    Element pageLoaderRoot = new Element(By.xpath("//div[contains(@class,'PageLoader_root')]"), "Индикатор загрузки станицы");

    ElementCollection vacancyCards = new ElementCollection(By.xpath("//div[contains(@class,'VacancyCart_wrapper')]"), "Карточки вакансий");
    ElementCollection vacancyCardTitles = new ElementCollection(By.xpath("//div[contains(@class,'VacancyCart_wrapper')]//div[contains(@class,'VacancyCart_title')]"), "Названия карточек вакансий");
}

