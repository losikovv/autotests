package ru.instamart.test.api;

import org.testng.annotations.Test;

import static ru.instamart.api.enums.v1.PaymentStatusV1.OVERPAID;
import static ru.instamart.api.helper.K8sHelper.reindexShipmentsByPaymentState;

public class PrepareStageTest {

    @Test(groups = "generate-data")
    public void reindexShipmentByPaymentState() {
        reindexShipmentsByPaymentState(OVERPAID.getValue());
    }
}
