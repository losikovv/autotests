package ru.instamart.api.response.v1.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.AdminOrderDeliveryWindowsV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderDeliveryWindowsV1Response extends BaseResponseObject {

    @JsonProperty("delivery_windows")
    private AdminOrderDeliveryWindowsV1 deliveryWindows;
}