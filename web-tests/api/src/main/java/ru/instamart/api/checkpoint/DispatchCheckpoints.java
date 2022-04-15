package ru.instamart.api.checkpoint;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.testng.asserts.SoftAssert;
import ru.instamart.jdbc.entity.workflow.SegmentsEntity;
import workflow.SegmentChangedOuterClass;
import workflow.WorkflowEnums;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;

@Slf4j
public class DispatchCheckpoints {

    @Step("Проверяем сегменты маршрутного листа из БД и кафки")
    public static void checkSegments(List<SegmentsEntity> segments, List<SegmentChangedOuterClass.SegmentChanged> segmentsFromKafka, int firstSegment, int secondSegment, WorkflowEnums.SegmentType segmentType) {
        final SoftAssert softAssert = new SoftAssert();
        checkFieldIsNotEmpty(segments.get(firstSegment).getFactStartedAt(), "время начала маршрута", softAssert);
        checkFieldIsNotEmpty(segments.get(firstSegment).getFactEndedAt(), "время окончания маршрута", softAssert);
        checkFieldIsNotEmpty(segments.get(secondSegment).getFactStartedAt(), "время начала маршрута", softAssert);
        compareTwoObjects(segmentsFromKafka.get(segmentsFromKafka.size() - 1).getType(), segmentType, softAssert);
        softAssert.assertAll();
    }
}
