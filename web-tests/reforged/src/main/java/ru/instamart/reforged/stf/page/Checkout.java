package ru.instamart.reforged.stf.page;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.reforged.stf.frame.AddCompanyModal;
import ru.instamart.reforged.stf.frame.EditPhoneNumber;
import ru.instamart.reforged.stf.page.checkout.ContactsStep;
import ru.instamart.reforged.stf.page.checkout.DeliveryOptionsStep;
import ru.instamart.reforged.stf.page.checkout.ReplacementPolicyStep;
import ru.instamart.reforged.stf.page.checkout.SlotStep;

@Slf4j
public final class Checkout implements StfPage {

    private static final String PAGE = "checkout/edit";

    private final DeliveryOptionsStep deliveryOptionsStep = new DeliveryOptionsStep();
    private final AddCompanyModal addCompanyModal = new AddCompanyModal();
    private final ContactsStep contactsStep = new ContactsStep();
    private final EditPhoneNumber editPhoneNumberModal = new EditPhoneNumber();
    private final ReplacementPolicyStep replacementPolicy = new ReplacementPolicyStep();
    private final SlotStep slot = new SlotStep();

    public DeliveryOptionsStep setDeliveryOptions() {
        return deliveryOptionsStep;
    }

    public ContactsStep setContacts() {
        return contactsStep;
    }

    public ReplacementPolicyStep setReplacementPolicy() {
        return replacementPolicy;
    }

    public SlotStep setSlot() {
        return slot;
    }

    public AddCompanyModal interactAddCompanyModal() {
        return addCompanyModal;
    }

    public EditPhoneNumber interactEditPhoneNumberModal() {
        return editPhoneNumberModal;
    }

    @Override
    @Step("Открыть старницу чекаута")
    public String pageUrl() {
        return PAGE;
    }
}