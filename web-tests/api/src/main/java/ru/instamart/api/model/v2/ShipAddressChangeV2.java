package ru.instamart.api.model.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipAddressChangeV2 extends BaseObject {
    private OrderV2 order;
    private List<Object> losses = null;
}