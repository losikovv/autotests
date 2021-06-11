package ru.instamart.reforged.stf.page;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.reforged.stf.page.StfPage;
import ru.instamart.reforged.stf.page.checkoutSteps.DeliveryOptionsStep;

@Slf4j
public final class Checkout implements StfPage {

    private static final String PAGE = "checkout/edit";
    private final DeliveryOptionsStep deliveryOptionsStep = new DeliveryOptionsStep();

    public DeliveryOptionsStep interactDeliveryOptionsStep() {
        return deliveryOptionsStep;
    }

    @Override
    @Step("Открыть старницу чекаута")
    public String pageUrl() {
        return PAGE;
    }
}