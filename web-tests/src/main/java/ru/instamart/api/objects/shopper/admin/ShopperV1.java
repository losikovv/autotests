package ru.instamart.api.objects.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;
import ru.instamart.api.objects.v1.RoleV1;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShopperV1 extends BaseObject {
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
    private StoreV1 store;
    private List<RoleV1> roles = null;
    private List<VehicleV1> vehicles = null;
    private List<EquipmentV1> equipment = null;
    private List<UniformV1> uniforms = null;
}
