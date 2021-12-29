package ru.instamart.api.response.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public final class PricerV1Response extends BaseResponseObject {

    private PricersV1Response.Pricer pricer;
}
