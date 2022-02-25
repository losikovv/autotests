package ru.instamart.reforged.cloud_payments.page;

import ru.instamart.reforged.cloud_payments.page.cloud_payments.DemoCloudPaymentsPage;
import ru.instamart.reforged.core.page.Router;

public final class CloudPaymentsPageRouter extends Router {

    public static DemoCloudPaymentsPage cloudPayments() {
        return (DemoCloudPaymentsPage) getPage(DemoCloudPaymentsPage.class);
    }
}
