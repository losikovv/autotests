
package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SubscriptionV2 extends BaseObject {

    private Long id;
    private Boolean active;
    @JsonProperty("begins_date")
    private String beginsDate;
    @JsonProperty("expired_date")
    private String expiredDate;
}
