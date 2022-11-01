package ru.instamart.reforged.hr_ops_partners.page.vacancy;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.hr_ops_partners.block.cities.Cities;
import ru.instamart.reforged.hr_ops_partners.block.faq.Faq;
import ru.instamart.reforged.hr_ops_partners.block.footer.Footer;
import ru.instamart.reforged.hr_ops_partners.block.header.Header;
import ru.instamart.reforged.hr_ops_partners.block.main_banner.MainBanner;
import ru.instamart.reforged.hr_ops_partners.block.vacancy_small_cards.collector.Collector;
import ru.instamart.reforged.hr_ops_partners.block.vacancy_small_cards.collector_courier.CollectorCourier;
import ru.instamart.reforged.hr_ops_partners.block.vacancy_small_cards.collector_courier_avto.CollectorCourierAvto;
import ru.instamart.reforged.hr_ops_partners.block.vacancy_small_cards.driver_courier.DriverCourier;
import ru.instamart.reforged.hr_ops_partners.frame.apply_form_modal.ApplyFormModal;

public interface VacancyElement {

    Header header = new Header();
    MainBanner mainBanner = new MainBanner();
    ApplyFormModal applyForm = new ApplyFormModal();
    Collector collector = new Collector();
    CollectorCourier collectorCourier = new CollectorCourier();
    CollectorCourierAvto collectorCourierAvto = new CollectorCourierAvto();
    DriverCourier driverCourier = new DriverCourier();
    Cities cities = new Cities();
    Faq faq = new Faq();
    Footer footer = new Footer();

    Element advantagesBlockTitle = new Element(By.xpath("//div[contains(@class,'SectionWrapper_title_')][.='Почему быть в команде СберМаркета — круто?']"), "Заголовок блока преимуществ");
    ElementCollection advantageTitles = new ElementCollection(By.xpath("//div[contains(@class,'Advantage_title_')]"), "Заголовки преимуществ");
    ElementCollection advantageDescriptions = new ElementCollection(By.xpath("//div[contains(@class,'Advantage_description_')]"), "Описания преимуществ");
    Element applyFormBlockTitle = new Element(By.xpath("//div[contains(@class,'VacancyApply_title_')][.='Чтобы начать зарабатывать']"), "Заголовок блока отклика на вакансию");
    ElementCollection applyFormSteps = new ElementCollection(By.xpath("//div[contains(@class,'VacancyApply_bulletPoint_')]"), "Шаги 'Чтобы начать зарабатывать'");
    Element anotherVacanciesBlockTitle = new Element(By.xpath("//div[contains(@class,'SectionWrapper_title_')][.='Ещё вы можете работать']"), "Заголовок блока 'Ещё можете работать'");
    ElementCollection anotherVacancyCards = new ElementCollection(By.xpath("//div[contains(@class,'AnotherVacancy_root')]"), "Карточки других вакансий");
}

