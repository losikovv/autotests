package ru.instamart.api.model.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServiceRateV2 extends BaseObject {
    private Double total;
    private List<RateV2> rates;
}