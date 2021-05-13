package ru.instamart.api.model.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class TariffV1 extends BaseObject {
    private Integer id;
    private String title;
    private Integer roleId;
    private Integer operationalZoneId;
    private Integer retailerId;
    private String shiftSchedule;
    private String shiftTimeOfDay;
    private String shiftStartsAt;
    private String shiftEndsAt;
    private String shiftPayroll;
    private Integer shiftPerPositionThreshold;
    private Double shiftPerPositionPayroll;
    private Integer shiftPerOrderThreshold;
    private String shiftPerOrderPayroll;
    private Integer shiftWeightThreshold;
    private Double shiftWeightPayroll;
    private Double shiftWeightPerKiloPayroll;
    private String createdAt;
    private String updatedAt;
    private String discardedAt;
}
