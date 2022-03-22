package ru.instamart.reforged.sber_payments;

import ru.instamart.reforged.core.page.Router;
import ru.instamart.reforged.sber_payments.rbsuat_payments.RbsuatPaymentsPage;

public final class SberPaymentsPageRouter extends Router {

    public static RbsuatPaymentsPage sberPayments() {
        return (RbsuatPaymentsPage) getPage(RbsuatPaymentsPage.class);
    }
}
