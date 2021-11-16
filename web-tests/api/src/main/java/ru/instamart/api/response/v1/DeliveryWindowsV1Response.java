
package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.DeliveryWindowV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeliveryWindowsV1Response extends BaseResponseObject {

    @JsonProperty("delivery_windows")
    private List<DeliveryWindowV1> deliveryWindows;
}
