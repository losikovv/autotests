package ru.instamart.reforged.hr_ops_partners.page.vacancy;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.page.Window;
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
import ru.instamart.reforged.hr_ops_partners.page.HRPartnersPage;

import java.util.Arrays;

public final class VacancyPage implements HRPartnersPage, Window, VacancyCheck {

    public Header interactHeader() {
        return header;
    }

    public MainBanner interactMainBanner() {
        return mainBanner;
    }

    public ApplyFormModal interactApplyForm() {
        return applyForm;
    }

    public Collector interactCollector() {
        return collector;
    }

    public CollectorCourier interactCollectorCourier() {
        return collectorCourier;
    }

    public CollectorCourierAvto interactCollectorCourierAvto() {
        return collectorCourierAvto;
    }

    public DriverCourier interactDriverCourier() {
        return driverCourier;
    }

    public Cities interactCities() {
        return cities;
    }

    public Faq interactFaq() {
        return faq;
    }

    public Footer interactFooter() {
        return footer;
    }

    @Step("Прокручиваем страницу до блока преимуществ")
    public void scrollToAdvantagesBlock() {
        advantagesBlockTitle.scrollTo();
    }

    @Step("Прокручиваем страницу до блока отклика на вакансию")
    public void scrollToApplyBlock() {
        applyFormBlockTitle.scrollTo();
    }

    @Step("Прокручиваем страницу до блока других вакансий")
    public void scrollToAnotherVacanciesBlock() {
        anotherVacanciesBlockTitle.scrollTo();
    }

    @Override
    public String pageUrl() {
        return "";
    }

    @Override
    public void goToPage() {
        throw new RuntimeException("Выберите метод с параметрами или для перехода на страницу конкретной вакансии");
    }

    @Step("Переходим на страницу вакансии 'Сборщик заказов'")
    public void goToPageCollector(final Object... args) {
        goToPage("sborshchik-zakazov" + Arrays.toString(args));
    }

    @Step("Переходим на страницу вакансии 'Сборщик-курьер'")
    public void goToPageCollectorCourier() {
        goToPage("sborshchik-kurer");
    }

    @Step("Переходим на страницу вакансии 'Сборщик-курьер на авто'")
    public void goToPageCollectorCourierAuto() {
        goToPage("sborshchik-kurer-avto");
    }

    @Step("Переходим на страницу вакансии 'Водитель-курьер'")
    public void goToPageDriverCourier() {
        goToPage("voditel-kurer");
    }

    @Step("Переходим на страницу вакансии 'Оператор контактного центра'")
    public void goToPageCallCenterOperator() {
        goToPage("operator-kontaktnogo-centra");
    }
}
