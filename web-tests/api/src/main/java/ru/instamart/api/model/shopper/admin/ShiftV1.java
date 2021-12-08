package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShiftV1 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private String date;

    @JsonSchema(required = true)
    private Integer storeId;

    @JsonSchema(required = true)
    private Integer tariffId;

    @JsonSchema(required = true)
    private Integer position;

    @Null
    @JsonSchema(required = true)
    private TariffV1 tariff;

    @JsonSchema(required = true)
    private List<ShiftAssignmentV1> shiftAssignments = null;

    @JsonSchema(required = true)
    private Boolean forFlexSupply;
}
