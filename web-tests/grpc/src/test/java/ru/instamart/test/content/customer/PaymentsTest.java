package ru.instamart.test.content.customer;

import com.google.type.Money;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payments.PaymentsGrpc;
import payments.PaymentsOuterClass;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.customer_payments.InvoicesDao;
import ru.instamart.jdbc.dao.customer_payments.PaymentToolsDao;
import ru.instamart.jdbc.dao.customer_payments.TransactionsDao;

import java.util.UUID;

@Epic("Customer Payments Tests")
@Feature("gRPC ручки сервиса платежей")
public class PaymentsTest extends GrpcBase {
    private PaymentsGrpc.PaymentsBlockingStub client;
    private final String paymentToolUuid = PaymentToolsDao.INSTANCE.getActivePaymentTool().getUuid();
    private final String orderBundle = "{\"customerDetails\": {\"email\": \"asfgdfgdgs@igvuiydfvdf.su\"}, \"cartItems\": {\"items\": [{\"positionId\": 1, \"name\": \"Лоток Интерпак пластиковый для яиц\", \"quantity\": {\"value\": 1, \"measure\": 0}, \"itemCode\": \"18425742\", \"tax\": {\"taxType\": 6}, \"itemPrice\": 120000, \"itemAttributes\": {\"attributes\": [{\"name\": \"paymentMethod\", \"value\": 1}, {\"name\": \"paymentObject\", \"value\": 1}, {\"name\": \"agent_info.type\", \"value\": 7}, {\"name\": \"supplier_info.phones\", \"value\": \"+79999999999\"}, {\"name\": \"supplier_info.inn\", \"value\": \"4101095172\"}, {\"name\": \"supplier_info.name\", \"value\": \"ООО Шамса-Холдинг\"}]}}, {\"positionId\": 2, \"name\": \"Лоток Интерпак2 пластиковый для яиц\", \"quantity\": {\"value\": 1, \"measure\": 0}, \"itemCode\": \"18425743\", \"tax\": {\"taxType\": 6}, \"itemPrice\": 120000, \"itemAttributes\": {\"attributes\": [{\"name\": \"paymentMethod\", \"value\": 1}, {\"name\": \"paymentObject\", \"value\": 1}, {\"name\": \"agent_info.type\", \"value\": 7}, {\"name\": \"supplier_info.phones\", \"value\": \"+79999999999\"}, {\"name\": \"supplier_info.inn\", \"value\": \"4101095172\"}, {\"name\": \"supplier_info.name\", \"value\": \"ООО Шамса-Холдинг\"}]}}]}}";

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_CUSTOMER_PAYMENTS);
        client = PaymentsGrpc.newBlockingStub(channel);
    }

    @Story("CreatePayment")
    @TmsLink("30")
    @Test(description = "Создание платежа", groups = "grpc-customer-payments")
    public void createPayment() {
        var shipmentUuid = UUID.randomUUID().toString();
        var request = PaymentsOuterClass.CreatePaymentRequest.newBuilder()
                .setPaymentToolUuid(paymentToolUuid)
                .setShipmentUuid(shipmentUuid)
                .setMerchantId("instamart")
                .setAmount(Money.newBuilder()
                        .setUnits(2400L)
                        .build())
                .setPayload(PaymentsOuterClass.Payload.newBuilder()
                        .setOrderBundle(orderBundle)
                        .build()
                )
                .build();
        var response = client.createPayment(request);
        Allure.step("Проверка статуса в ответе");
        Assert.assertEquals(response.getState().toString(), PaymentsOuterClass.PaymentState.PAYMENT_STATE_SUCCESS.toString());

        Allure.step("Проверка данных в БД таблица 'transactions' ", () -> {
            var transactionEntity = TransactionsDao.INSTANCE.getTransactionByUuid(response.getUuid());
            Assert.assertNotNull(transactionEntity, "Не найдена запись в таблице 'transactions' c uuid = " + response.getUuid());
            Assert.assertEquals(transactionEntity.getState(), "purchase_success", "Значение 'state' в таблице 'transactions' отличается от ожидаемого");
            Assert.assertEquals(transactionEntity.getAmount(), 2400, "Значение 'amount' в таблице 'transactions' отличается от ожидаемого");
            Assert.assertEquals(transactionEntity.getRefundAmount(), 0, "Значение 'refund_amount' в таблице 'transactions' отличается от ожидаемого");
        });

        Allure.step("Проверка данных в БД таблица 'invoices' ", () -> {
            var invoiceEntity = InvoicesDao.INSTANCE.getInvoiceByShipmentUuid(shipmentUuid);
            Assert.assertNotNull(invoiceEntity, "Не найдена запись в таблице 'invoices' c shipment_uuid = " + shipmentUuid);
            Assert.assertEquals(invoiceEntity.getState(), "purchase_success", "Значение 'state' в таблице 'invoices' отличается от ожидаемого");
            Assert.assertEquals(invoiceEntity.getTotalAmount(), 2400, "Значение 'total_amount' в таблице 'invoices' отличается от ожидаемого");
            Assert.assertEquals(invoiceEntity.getTotalRefundAmount(), 0, "Значение 'total_refund_amount' в таблице 'invoices' отличается от ожидаемого");
        });
    }
}
