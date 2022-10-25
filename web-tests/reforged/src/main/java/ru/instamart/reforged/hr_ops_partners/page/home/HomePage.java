package ru.instamart.reforged.hr_ops_partners.page.home;

import ru.instamart.reforged.core.page.Window;
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
import ru.instamart.reforged.hr_ops_partners.page.HRPartnersPage;

public final class HomePage implements HRPartnersPage, Window, HomeCheck {

    public Header interactHeader() {
        return header;
    }

    public VacancyNotAvailableModal interactNotAvailableModal() {
        return notAvailableModal;
    }

    public MainBanner interactMainBanner() {
        return mainBanner;
    }

    public Collector interactCollector() {
        return collector;
    }

    public CollectorCourier interactCollectorCourier() {
        return collectorCourier;
    }

    public CollectorCourierAuto interactCollectorCourierWithAuto() {
        return collectorCourierAuto;
    }

    public DriverCourier interactDriverCourier() {
        return driverCourier;
    }

    public CallCenterOperator interactCallCenterOperator() {
        return callCenterOperator;
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

    public ApplyFormModal interactApplyForm() {
        return applyFormModal;
    }

    public ConfirmSMSModal interactConfirmSMS(){
        return confirmSMSModal;
    }

    @Override
    public String pageUrl() {
        return "";
    }

    @Override
    public void goToPage() {
        goToPage(pageUrl());
    }
}
