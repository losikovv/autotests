package ru.instamart.api.model.shopper.app;

import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class EquipmentSHP extends BaseObject {
    private List<DataSHP> data = null;
}
