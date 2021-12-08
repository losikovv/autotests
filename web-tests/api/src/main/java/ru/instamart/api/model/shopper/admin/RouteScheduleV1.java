package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class RouteScheduleV1 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private String date;

    @JsonSchema(required = true)
    private String status;

    @Null
    @JsonSchema(required = true)
    private String warningMessage;

    @JsonSchema(required = true)
    private String scheduleType;

    @JsonSchema(required = true)
    private String scheduleTypeHumanName;

    @JsonSchema(required = true)
    private List<RouteV1> routes = null;

    private List<UnscheduledShipmentV1> unscheduledShipments = null;

    @JsonSchema(required = true)
    private StoreV1 store;

    @Null
    private Object lastRoutingTask;
}
