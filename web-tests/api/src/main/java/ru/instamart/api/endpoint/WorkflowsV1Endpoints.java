package ru.instamart.api.endpoint;

public class WorkflowsV1Endpoints {

    public static final String ASSIGNMENTS = "v1/assignments";
    public static final String WORKFLOWS = "v1/workflows";

    public static class Workflows {
        public static class Segments {
            public static final String NEXT = "v1/workflows/{workflowId}/segments/next";
        }
    }

    public static class Assignments {
        public static final String SEEN = "v1/assignments/{assignmentId}/seen";
        public static final String ACCEPT = "v1/assignments/{assignmentId}/accept";
        public static final String DECLINE = "v1/assignments/{assignmentId}/decline";
    }
}
