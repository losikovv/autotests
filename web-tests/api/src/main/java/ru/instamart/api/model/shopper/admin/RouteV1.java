package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class RouteV1 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private Integer position;

    @JsonSchema(required = true)
    private String state;

    @JsonSchema(required = true)
    private String humanStateName;

    @Null
    @JsonSchema(required = true)
    private String planLoadingStartsAt;

    @Null
    @JsonSchema(required = true)
    private String factLoadingStartsAt;

    @Null
    @JsonSchema(required = true)
    private String planDepartureAt;

    @Null
    @JsonSchema(required = true)
    private String factDepartureAt;

    @Null
    @JsonSchema(required = true)
    private String planArrivalAt;

    @Null
    @JsonSchema(required = true)
    private String factArrivalAt;

    @Null
    @JsonSchema(required = true)
    private Object planDistance;

    @JsonSchema(required = true)
    private Integer shipmentsCount;

    @JsonSchema(required = true)
    private Integer assembledShipmentsCount;

    @JsonSchema(required = true)
    private Boolean isVisible;

    @JsonSchema(required = true)
    private Boolean lockedForAutomaticChange;

    @Null
    @JsonSchema(required = true)
    private String warningMessage;

    @JsonSchema(required = true)
    private Boolean manualManagementAvailable;

    @JsonSchema(required = true)
    private Boolean destroyAvailable;

    @JsonSchema(required = true)
    private List<RoutePointV1> routePoints = null;

    @JsonSchema(required = true)
    private DriverV1 driver;
}
