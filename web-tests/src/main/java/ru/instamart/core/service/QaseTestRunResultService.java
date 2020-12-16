package instamart.core.service;

import io.qase.api.QaseApiClient;
import io.qase.api.enums.RunResultStatus;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.testrunresults.*;
import io.qase.api.services.TestRunResultService;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;

public class QaseTestRunResultService implements TestRunResultService {
    private final QaseApiClient qaseApiClient;

    public QaseTestRunResultService(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    @Override
    public TestRunResults getAll(String projectCode, int limit, int offset, RouteFilter filter) {
        return qaseApiClient.get(TestRunResults.class, "/result/{code}", singletonMap("code", projectCode), filter, limit, offset);
    }

    @Override
    public TestRunResults getAll(String projectCode) {
        return this.getAll(projectCode, 100, 0, filter());
    }

    @Override
    public TestRunResult get(String projectCode, String hash) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("hash", hash);
        return qaseApiClient.get(TestRunResult.class, "/result/{code}/{hash}", routeParams);
    }

    @Override
    public String create(String projectCode, long runId, long caseId, RunResultStatus status, Duration timeSpent,
                         Integer memberId, String comment, Boolean isDefect, Step... steps) {
        return create(projectCode, runId, caseId, status, timeSpent, memberId, comment, null, isDefect, steps);
    }

    @Override
    public String create(String projectCode, long runId, long caseId, RunResultStatus status, Duration timeSpent,
                         Integer memberId, String comment, String stacktrace, Boolean isDefect, Step... steps) {
        NewTestRunResults newTestRunResults = NewTestRunResults.builder()
                .caseId(caseId)
                .status(status)
                .time(timeSpent == null ? null : timeSpent.getSeconds())
                .memberId(memberId)
                .comment(comment)
                .stacktrace(stacktrace)
                .defect(isDefect)
                .steps(Arrays.asList(steps))
                .build();
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("run_id", runId);
        return qaseApiClient.post(TestRunResult.class, "/result/{code}/{run_id}", routeParams, newTestRunResults).getHash();
    }

    public String create(String projectCode, long runId, long caseId, RunResultStatus status, Duration timeSpent,
                         Integer memberId, String comment, String stacktrace, Boolean isDefect, List<String> attachments,
                         Step... steps) {
        NewTestRunResults newTestRunResults = NewTestRunResults.builder()
                .caseId(caseId)
                .status(status)
                .time(timeSpent == null ? null : timeSpent.getSeconds())
                .memberId(memberId)
                .comment(comment)
                .stacktrace(stacktrace)
                .defect(isDefect)
                .attachments(isDefect ? attachments : null)
                .steps(Arrays.asList(steps))
                .build();
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("run_id", runId);
        return qaseApiClient.post(TestRunResult.class, "/result/{code}/{run_id}", routeParams, newTestRunResults).getHash();
    }

    @Override
    public String create(String projectCode, long runId, long caseId, RunResultStatus status) {
        return this.create(projectCode, runId, caseId, status, null, null, null, null);
    }

    @Override
    public String update(String projectCode, long runId, String hash, RunResultStatus status, Duration timeSpent,
                         Integer memberId, String comment, Boolean isDefect, Step... steps) {
        NewTestRunResults newTestRunResults = NewTestRunResults.builder()
                .status(status)
                .time(timeSpent == null ? null : timeSpent.getSeconds())
                .memberId(memberId)
                .comment(comment)
                .defect(isDefect)
                .steps(steps == null ? null : Arrays.asList(steps))
                .build();
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("run_id", runId);
        routeParams.put("hash", hash);
        return qaseApiClient.patch(TestRunResult.class, "/result/{code}/{run_id}/{hash}", routeParams, newTestRunResults)
                .getHash();
    }

    public String update(String projectCode, long runId, String hash, RunResultStatus status, Duration timeSpent,
                         Integer memberId, String comment, Boolean isDefect, List<String> attachments, Step... steps) {
        NewTestRunResults newTestRunResults = NewTestRunResults.builder()
                .status(status)
                .time(timeSpent == null ? null : timeSpent.getSeconds())
                .memberId(memberId)
                .comment(comment)
                .defect(isDefect)
                .attachments(attachments)
                .steps(steps == null ? null : Arrays.asList(steps))
                .build();
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("run_id", runId);
        routeParams.put("hash", hash);
        return qaseApiClient.patch(TestRunResult.class, "/result/{code}/{run_id}/{hash}", routeParams, newTestRunResults)
                .getHash();
    }

    @Override
    public String update(String projectCode, long runId, String hash, RunResultStatus status, Duration timeSpent) {
        return this.update(projectCode, runId, hash, status, timeSpent, null, null, null);
    }

    @Override
    public String update(String projectCode, long runId, String hash, RunResultStatus status) {
        return this.update(projectCode, runId, hash, status, null, null, null, null);
    }

    @Override
    public boolean delete(String projectCode, long runId, String hash) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("run_id", runId);
        routeParams.put("hash", hash);
        return (boolean) qaseApiClient.delete("/result/{code}/{run_id}/{hash}", routeParams).get("status");
    }
}
