package ru.instamart.api.helper;

import com.google.protobuf.Timestamp;
import estimator.Estimator;
import io.qameta.allure.Step;

public class RouteEstimatorHelper {
    @Step("Создаем новый запроса на расчёт сегмента")
    public static Estimator.GetRouteEstimationRequest getEstimateRequest(final String performerUuid, final Estimator.PerformerType performerType, final Estimator.PerformerVehicle performerVehicle, final double latStart, final double lonStart, final Timestamp timeToCalc, Estimator.SegmentType segmentType, final double latEnd, final double lonEnd, final String placeUUID, final int numberOfPositionsInOrder) {
        return Estimator.GetRouteEstimationRequest.newBuilder()
                .addPerformers(Estimator.EstimatePerformer.newBuilder()
                        .setUuid(performerUuid)
                        .setType(performerType)
                        .setVehicle(performerVehicle)
                        .setStartLocation(Estimator.Location.newBuilder()
                                .setLat(latStart)
                                .setLon(lonStart)
                                .build())
                        .setTimeToCalc(timeToCalc)
                        .addSegments(Estimator.Segment.newBuilder()
                                .setType(segmentType)
                                .setOrder(0)
                                .setEndLocation(Estimator.Location.newBuilder()
                                        .setLat(latEnd)
                                        .setLon(lonEnd)
                                        .build())
                                .setPlaceUuid(placeUUID)
                                .setNumberOfPositionsInOrder(numberOfPositionsInOrder)
                                .setOrderWeightGramms(2000)
                                .build())
                        .build())
                .build();
    }
}
