package ru.instamart.api.objects.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShiftAssignmentV1 {
    private Integer id;
    private Integer shiftId;
    private Integer shopperId;
    private String startsAt;
    private String endsAt;
    private ShopperV1 shopper;
    private List<ShiftAssignmentIncentiveV1> shiftAssignmentIncentives = null;
}
