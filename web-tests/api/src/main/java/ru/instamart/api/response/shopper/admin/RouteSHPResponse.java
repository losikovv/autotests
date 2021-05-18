package ru.instamart.api.response.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.RouteV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class RouteSHPResponse extends BaseResponseObject {
    private RouteV1 route;
}
