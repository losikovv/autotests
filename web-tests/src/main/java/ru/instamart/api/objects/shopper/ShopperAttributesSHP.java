package ru.instamart.api.objects.shopper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShopperAttributesSHP extends BaseObject {
    private String name;
    private String login;
    private String phone;
    private String status;
    private String inn;
    private String employmentType;
    private Object contractNumber;
    private Object contractDate;
    private Integer vehiclesCount;
    private Integer activeVehiclesCount;
    private Boolean phoneNeedsConfirmation;
    private Object phoneConfirmedAt;
    private Object firebaseToken;
    private Boolean tipsEnabled;
    private Object tipsLinkUrl;
    private String type;
}
