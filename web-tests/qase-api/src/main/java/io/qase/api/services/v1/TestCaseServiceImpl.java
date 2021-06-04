package io.qase.api.services.v1;

import io.qase.api.QaseApiClient;
import io.qase.api.enums.Automation;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.testcases.NewTestCase;
import io.qase.api.models.v1.testcases.TestCase;
import io.qase.api.models.v1.testcases.TestCases;
import io.qase.api.services.TestCaseService;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

public final class TestCaseServiceImpl implements TestCaseService {
    private final QaseApiClient qaseApiClient;

    public TestCaseServiceImpl(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    @Override
    public TestCases getAll(String projectCode, int limit, int offset, RouteFilter filter) {
        return qaseApiClient.get(TestCases.class, "/case/{code}",
                singletonMap("code", projectCode), filter, limit, offset);
    }

    @Override
    public TestCases getAll(String projectCode, int limit, int offset) {
        return getAll(projectCode, limit, offset, filter());
    }

    @Override
    public TestCases getAll(String projectCode, RouteFilter filter) {
        return getAll(projectCode, 100, 0, filter);
    }

    @Override
    public TestCases getAll(String projectCode) {
        return getAll(projectCode, 100, 0, filter());
    }

    @Override
    public TestCase get(String projectCode, int caseId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", caseId);
        return qaseApiClient.get(TestCase.class, "/case/{code}/{id}", routeParams);
    }

    @Override
    public boolean delete(String projectCode, int caseId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", caseId);
        return (boolean) qaseApiClient.delete("/case/{code}/{id}", routeParams).get("status");
    }

    @Override
    public boolean update(String projectCode, int caseId, Automation automation) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", caseId);

        NewTestCase newTestCase = new NewTestCase();
        switch (automation) {
            case automated:
                newTestCase.setAutomation(2);
                break;
            case to_be_automated:
                newTestCase.setAutomation(1);
                break;
            case is_not_automated:
                newTestCase.setAutomation(0);
                break;
        }
        return (boolean) qaseApiClient.patch("/case/{code}/{id}", routeParams, newTestCase).get("status");
    }
}
