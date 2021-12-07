package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v1.RoleV1;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShopperV1 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private String login;

    @Null
    @JsonSchema(required = true)
    private String phone;

    @JsonSchema(required = true)
    private String status;

    @Null
    @JsonSchema(required = true)
    private String inn;

    @Null
    @JsonSchema(required = true)
    private String employmentType;

    @Null
    @JsonSchema(required = true)
    private String contractNumber;

    @Null
    @JsonSchema(required = true)
    private String contractDate;

    @JsonSchema(required = true)
    private Integer vehiclesCount;

    @JsonSchema(required = true)
    private Integer activeVehiclesCount;

    @JsonSchema(required = true)
    private Boolean phoneNeedsConfirmation;

    @Null
    @JsonSchema(required = true)
    private String phoneConfirmedAt;

    @Null
    @JsonSchema(required = true)
    private String firebaseToken;

    @JsonSchema(required = true)
    private Boolean tipsEnabled;

    @Null
    @JsonSchema(required = true)
    private String tipsLinkUrl;

    @JsonSchema(required = true)
    private String type;

    @Null
    @JsonSchema(required = true)
    private Object avgScore;

    @Null
    @JsonSchema(required = true)
    private Object isTopShopper;

    @JsonSchema(required = true)
    private StoreV1 store;

    private List<RoleV1> roles = null;

    private List<VehicleV1> vehicles = null;

    private List<EquipmentV1> equipment = null;

    private List<UniformV1> uniforms = null;
}
