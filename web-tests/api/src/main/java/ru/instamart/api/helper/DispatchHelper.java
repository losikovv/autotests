package ru.instamart.api.helper;

import com.google.protobuf.Duration;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import io.qameta.allure.Step;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.kraken.data.user.UserManager;
import workflow.ServiceGrpc;
import workflow.WorkflowEnums;
import workflow.WorkflowOuterClass;

public class DispatchHelper {

    @Step("Подготавливаем запрос для создания маршрутного листа")
    public static WorkflowOuterClass.CreateWorkflowsRequest getCreateWorkflowsRequest(OrderV2 order, String shipmentUuid, Timestamp time, WorkflowEnums.DeliveryType deliveryType) {
        return WorkflowOuterClass.CreateWorkflowsRequest
                .newBuilder()
                .addWorkflows(WorkflowOuterClass.Workflow.newBuilder()
                        .addAssignments(WorkflowOuterClass.Assignment.newBuilder()
                                .setPerformerUuid(UserManager.getDefaultShopper().getUuid())
                                .setSourceTypeValue(WorkflowEnums.SourceType.DISPATCH.getNumber())
                                .setPerformerVehicleValue(WorkflowEnums.PerformerVehicle.PEDESTRIAN_VALUE)
                                .setDeliveryTypeValue(deliveryType.getNumber())
                                .build())
                        .addSegments(WorkflowOuterClass.Segment.newBuilder()
                                .setTypeValue(WorkflowEnums.SegmentType.ARRIVE_VALUE)
                                .setPosition(0)
                                .addShipments(WorkflowEnums.Shipment.newBuilder()
                                        .setNumber(order.getShipments().get(0).getNumber())
                                        .setUuid(shipmentUuid)
                                        .setItemsCount(1500)
                                        .setItemsTotalAmount(2539.5f)
                                        .setWeightKg(10f)
                                        .setStoreUuid("6bc4dc40-37a0-45fe-ac7f-d4185c29da63")
                                        .setIsHeavy(false)
                                        .setIsNew(false)
                                        .setStoreName("METRO, Дорожная")
                                        .setStoreAddress("Москва, Дорожная,  д. 1, корп. 1")
                                        .build())
                                .setLocationStart(WorkflowEnums.Location.newBuilder()
                                        .setLat(55.63533180125311)
                                        .setLon(37.62462253918757)
                                        .build())
                                .setPlanStartedAt(time)
                                .setLocationEnd(WorkflowEnums.Location.newBuilder()
                                        .setLat(55.60166514312233)
                                        .setLon(37.62097454092305)
                                        .build())
                                .setPlanEndedAt(time)
                                .setDuration(Duration.newBuilder().setSeconds(10).build())
                                .setDistance(25L)
                                .build())
                        .addSegments(WorkflowOuterClass.Segment.newBuilder()
                                .setTypeValue(WorkflowEnums.SegmentType.DELIVERY_VALUE)
                                .setPosition(2)
                                .addShipments(WorkflowEnums.Shipment.newBuilder()
                                        .setNumber(order.getShipments().get(0).getNumber())
                                        .setUuid(shipmentUuid)
                                        .setItemsCount(1500)
                                        .setItemsTotalAmount(2539.5f)
                                        .setWeightKg(10f)
                                        .setStoreUuid("6bc4dc40-37a0-45fe-ac7f-d4185c29da63")
                                        .setIsHeavy(false)
                                        .setIsNew(false)
                                        .setStoreName("METRO, Дорожная")
                                        .setStoreAddress("Москва, Дорожная,  д. 1, корп. 1")
                                        .build())
                                .setLocationStart(WorkflowEnums.Location.newBuilder()
                                        .setLat(55.63533180125311)
                                        .setLon(37.62462253918757)
                                        .build())
                                .setPlanStartedAt(time)
                                .setLocationEnd(WorkflowEnums.Location.newBuilder()
                                        .setLat(55.60166514312233)
                                        .setLon(37.62097454092305)
                                        .build())
                                .setPlanEndedAt(time)
                                .setDuration(Duration.newBuilder().setSeconds(10).build())
                                .setDistance(25L)
                                .build())
                        .addSegments(WorkflowOuterClass.Segment.newBuilder()
                                .setTypeValue(WorkflowEnums.SegmentType.PASS_TO_CLIENT_VALUE)
                                .setPosition(3)
                                .addShipments(WorkflowEnums.Shipment.newBuilder()
                                        .setNumber(order.getShipments().get(0).getNumber())
                                        .setUuid(shipmentUuid)
                                        .setItemsCount(1500)
                                        .setItemsTotalAmount(2539.5f)
                                        .setWeightKg(10f)
                                        .setStoreUuid("6bc4dc40-37a0-45fe-ac7f-d4185c29da63")
                                        .setIsHeavy(false)
                                        .setIsNew(false)
                                        .setStoreName("METRO, Дорожная")
                                        .setStoreAddress("Москва, Дорожная,  д. 1, корп. 1")
                                        .build())
                                .setLocationStart(WorkflowEnums.Location.newBuilder()
                                        .setLat(55.63533180125311)
                                        .setLon(37.62462253918757)
                                        .build())
                                .setPlanStartedAt(time)
                                .setLocationEnd(WorkflowEnums.Location.newBuilder()
                                        .setLat(55.60166514312233)
                                        .setLon(37.62097454092305)
                                        .build())
                                .setPlanEndedAt(time)
                                .setDuration(Duration.newBuilder().setSeconds(10).build())
                                .setDistance(25L)
                                .build())
                        .build())
                .build();
    }

    @Step("Отменяем маршрутный листа")
    public static void cancelWorkflow(ServiceGrpc.ServiceBlockingStub clientWorkflow, String shipmentUuid) {
        var request = WorkflowOuterClass.RejectWorkflowByShipmentUuidRequest
                .newBuilder()
                .setShipmentUuid(shipmentUuid)
                .build();

        var response = clientWorkflow.rejectWorkflowByShipmentUUID(request);
    }
}
