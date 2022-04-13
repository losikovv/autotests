package ru.instamart.jdbc.entity.shifts;

import lombok.Data;

@Data
public class PlanningPeriodEntity {
    private Long planningPeriodId;
    private Long peopleCountCapacity;
    private Long peopleCountAvailable;
}
