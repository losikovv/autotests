package ru.instamart.api.response.shopper.shifts;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.shifts.PlanningAreaShiftsItemSHP;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlanningAreaShiftsResponse extends BaseResponseObject {
    private List<PlanningAreaShiftsItemSHP> planningAreaShiftsItemsList;
}