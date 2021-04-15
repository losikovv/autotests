package ru.instamart.api.objects.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class DriverV1 extends BaseObject {
    private Integer id;
    private String name;
    private String login;
    private String phone;
    private String status;
    private String inn;
    private String employmentType;
    private String contractNumber;
    private String contractDate;
    private Integer vehiclesCount;
    private Integer activeVehiclesCount;
    private Boolean phoneNeedsConfirmation;
    private String phoneConfirmedAt;
    private String firebaseToken;
    private Boolean tipsEnabled;
    private String tipsLinkUrl;
    private String type;
    private Object avgScore;
    private Object isTopShopper;
}
