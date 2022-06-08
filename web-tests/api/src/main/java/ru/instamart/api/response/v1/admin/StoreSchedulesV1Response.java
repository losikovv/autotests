package ru.instamart.api.response.v1.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.StoreScheduleV1;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.Null;

@EqualsAndHashCode(callSuper = true)
@Data
public class StoreSchedulesV1Response extends BaseResponseObject {

    @Null
	@JsonProperty("store_schedule")
    private StoreScheduleV1 storeSchedule;
}
