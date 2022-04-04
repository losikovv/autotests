package ru.instamart.api.endpoint;

public final class ShiftsV1Endpoints {
    public static final String SHIFTS = "v1/shifts";

    public static final class Shifts {
        public static final String BY_ID = "v1/shifts/{id}";
        public static final String START = "v1/shifts/{id}/start";
        public static final String STOP = "v1/shifts/{id}/stop";

    }

    public static final class Regions {
        public static final String PLANNING_AREAS = "v1/regions/{id}/planning_areas";
    }

    public static final class PlanningAreas {
        public static final String PLANNING_PERIODS = "v1/planning_areas/{id}/planning_periods";
    }
}
