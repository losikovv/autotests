package ru.instamart.api.checkpoint;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.testng.asserts.SoftAssert;
import ru.instamart.jdbc.dao.candidates.CandidatesDao;
import ru.instamart.jdbc.dao.workflow.ChangelogsDao;
import ru.instamart.jdbc.dao.workflow.SegmentsDao;
import ru.instamart.jdbc.dao.workflow.WorkflowsDao;
import ru.instamart.jdbc.entity.candidates.CandidatesEntity;
import ru.instamart.jdbc.entity.workflow.AssignmentsEntity;
import ru.instamart.jdbc.entity.workflow.SegmentsEntity;
import ru.instamart.jdbc.entity.workflow.WorkflowsEntity;
import ru.instamart.kafka.helper.KafkaHelper;
import ru.instamart.kraken.data.user.UserManager;
import workflow.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;

@Slf4j
public class WorkflowCheckpoints {

    @Step("Проверяем сегменты маршрутного листа из БД и кафки")
    public static void checkSegments(List<SegmentsEntity> segments, List<SegmentChangedOuterClass.SegmentChanged> segmentsFromKafka, int firstSegment, int secondSegment, WorkflowEnums.SegmentType segmentType) {
        final SoftAssert softAssert = new SoftAssert();
        checkFieldIsNotEmpty(segments.get(firstSegment).getFactStartedAt(), "время начала маршрута", softAssert);
        checkFieldIsNotEmpty(segments.get(firstSegment).getFactEndedAt(), "время окончания маршрута", softAssert);
        checkFieldIsNotEmpty(segments.get(secondSegment).getFactStartedAt(), "время начала маршрута", softAssert);
        compareTwoObjects(segmentsFromKafka.get(segmentsFromKafka.size() - 1).getType(), segmentType, softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем запись об отклоненном маршрутном листе в кафке")
    public static void checkCanceledWorkflow(String childWorkflowUuid, Long workflowId, AssignmentChangedOuterClass.AssignmentChanged.Status assignmentStatus, KafkaHelper kafka) {
        CandidatesEntity candidate = CandidatesDao.INSTANCE.getCandidateByUuid(UserManager.getDefaultShopper().getUuid());
        List<AssignmentChangedOuterClass.AssignmentChanged> assignments = kafka.waitDataInKafkaTopicWorkflowAssignment(childWorkflowUuid);
        List<WorkflowChangedOuterClass.WorkflowChanged> workflows = kafka.waitDataInKafkaTopicWorkflow(workflowId);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(workflows.get(workflows.size() - 1).getStatus(), WorkflowChangedOuterClass.WorkflowChanged.Status.CANCELED, softAssert);
        compareTwoObjects(assignments.get(assignments.size() - 1).getStatus(), assignmentStatus, softAssert);
        compareTwoObjects(candidate.getActive(), true, softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем текст ошибки в ответе")
    public static void checkGrpcError(WorkflowOuterClass.CreateWorkflowsResponse response, String errorText, String errorKind) {
        final SoftAssert softAssert = new SoftAssert();
        for (WorkflowOuterClass.CreateWorkflowsResponse.Result result : response.getResultsMap().values()) {
            compareTwoObjects(result.getStatus().toString(), "FAILURE", softAssert);
            compareTwoObjects(result.getErrorText(), errorText, softAssert);
            compareTwoObjects(result.getErrorKind().toString(), errorKind, softAssert);
        }
        softAssert.assertAll();
    }

    @Step("Проверяем статусы назначения и маршрутного листа")
    public static void checkStatuses(List<AssignmentChangedOuterClass.AssignmentChanged> assignments, List<WorkflowChangedOuterClass.WorkflowChanged> workflows, AssignmentChangedOuterClass.AssignmentChanged.Status status) {
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(workflows.get(workflows.size() - 1).getStatus(), WorkflowChangedOuterClass.WorkflowChanged.Status.CANCELED, softAssert);
        compareTwoObjects(assignments.get(assignments.size() - 1).getStatus(), status, softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем изменения статусов маршрутного листа и сегментов после обновления таймингов")
    public static void checkWorkflowChanges(String workflowUuid, AssignmentsEntity assignmentsEntity, WorkflowsEntity workflowsEntity, List<SegmentsEntity> segmentsEntities, boolean isQueued) {
        WorkflowsEntity updatedWorkflowsEntity = WorkflowsDao.INSTANCE.findById(assignmentsEntity.getWorkflowId()).get();
        List<SegmentsEntity> updatedSegmentsEntities = SegmentsDao.INSTANCE.getSegmentsByWorkflowUuid(workflowUuid).stream()
                .sorted(Comparator.comparing(SegmentsEntity::getPosition)).collect(Collectors.toList());

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(ChangelogsDao.INSTANCE.getChangelogByWorkflowId(assignmentsEntity.getWorkflowId()), "Не пришли изменения");
        softAssert.assertNotEquals(updatedWorkflowsEntity.getPlanEndedAt(), workflowsEntity.getPlanEndedAt());
        if(isQueued) softAssert.assertNotEquals(updatedSegmentsEntities.get(0).getPlanStartedAt(), segmentsEntities.get(0).getPlanStartedAt());
        softAssert.assertNotEquals(updatedSegmentsEntities.get(0).getPlanEndedAt(), segmentsEntities.get(0).getPlanEndedAt());
        softAssert.assertNotEquals(updatedSegmentsEntities.get(1).getPlanStartedAt(), segmentsEntities.get(1).getPlanStartedAt());
        softAssert.assertNotEquals(updatedSegmentsEntities.get(1).getPlanEndedAt(), segmentsEntities.get(1).getPlanEndedAt());
        softAssert.assertNotEquals(updatedSegmentsEntities.get(2).getPlanEndedAt(), segmentsEntities.get(2).getPlanEndedAt());
        softAssert.assertNotEquals(updatedSegmentsEntities.get(2).getPlanStartedAt(), segmentsEntities.get(2).getPlanStartedAt());
        softAssert.assertAll();
    }
}
