package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper = false)
public class MergeShipmentPossibilityV1 extends BaseObject {

    @Null
    @JsonProperty("impossibility_reason")
    private ImpossibilityReason impossibilityReason;

    private boolean possible;
}
