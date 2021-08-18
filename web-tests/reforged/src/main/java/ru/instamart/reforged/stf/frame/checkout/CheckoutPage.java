package ru.instamart.reforged.stf.frame.checkout;

import ru.instamart.reforged.stf.page.StfPage;

public final class CheckoutPage implements StfPage, CheckoutCheck {

    @Override
    public String pageUrl() {
        return "checkout/edit";
    }
}
