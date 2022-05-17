package ru.instamart.jdbc.entity.shifts;

import lombok.Data;

@Data
public class PlanningPeriodCustomEntity {
    private Long planningPeriodId;
    private Long peopleCountCapacity;
    private Long peopleCountAvailable;
}
