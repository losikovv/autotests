package ru.instamart.test.api.on_demand.orderservice;

import io.grpc.StatusRuntimeException;
import order_redispatch.OrderGrpc;
import order_redispatch.OrderRedispatch;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ru.instamart.grpc.common.GrpcContentHosts.PAAS_CONTENT_OPERATIONS_ORDER_SERVICE;
import static ru.instamart.kraken.util.TimeUtil.getTimestamp;
import static ru.instamart.kraken.util.TimeUtil.getTimestampFromString;

public class OrderRedispatchTest extends RestBase {

    private OrderGrpc.OrderBlockingStub clientOrder;

    @BeforeClass(alwaysRun = true)
    public void preconditions () {
        clientOrder = OrderGrpc.newBlockingStub(grpc.createChannel(PAAS_CONTENT_OPERATIONS_ORDER_SERVICE));
    }
    @CaseId(160)
    @Test(description = "Редиспатч работает для заказа в статусе Ручная диспатчеризация",
            groups = "kafka-instamart-regress")
    public void redispatchForAManualOrder() {

        var requestBody= OrderRedispatch.RedispatchRequest.newBuilder()
                .setShipmentUuid("a9c96b7d-d6ad-4298-982f-077ccf8cfadf")
                .setPlaceUuid("684609ad-6360-4bae-9556-03918c1e41c1")
                .setUpperDtm(getTimestampFromString("2022-07-16T15:00:00.999+03:00"))
                .setLowerDtm(getTimestampFromString("2022-07-16T14:30:00.999+03:00"))

                .build();
        OrderRedispatch.RedispatchReply redispatch = clientOrder.redispatch(requestBody);
        assertEquals(redispatch.getShipmentStatusValue(), 1, "Редиспатч не сработал");
    }

    @CaseId(160)
    @Test(description = "Редиспатч не работает для магазина с выключенным диспатчем",
            groups = "kafka-instamart-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "UNKNOWN: ScheduleType != 'dispatch' for PlaceUUID a9c96b7d-d6ad-4298-982f-077ccf8cfadf")
    public void redispatchForAManualOrderNegativeTest() {
        String shipmentUUID = UUID.randomUUID().toString();
        var requestBody= OrderRedispatch.RedispatchRequest.newBuilder()
                .setShipmentUuid(shipmentUUID)
                .setPlaceUuid("a9c96b7d-d6ad-4298-982f-077ccf8cfadf")
                .setUpperDtm(getTimestamp())
                .setLowerDtm(getTimestamp())

                .build();

        clientOrder.redispatch(requestBody);

    }

}
