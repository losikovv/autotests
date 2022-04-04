package ru.instamart.api.response.shopper.shifts;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;
import ru.instamart.api.request.shifts.PlanningPeriodsSHPItem;

@Data
@EqualsAndHashCode(callSuper=false)
public class PlanningPeriodsShiftsResponse extends BaseResponseObject {

	@JsonProperty("PlanningPeriodsShiftsResponse")
	private List<PlanningPeriodsSHPItem> planningPeriods;
}