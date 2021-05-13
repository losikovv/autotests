package ru.instamart.api.response.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.MetaV1;
import ru.instamart.api.model.v1.OperationalZoneV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationalZonesSHPResponse extends BaseResponseObject {
    private List<OperationalZoneV1> operationalZones = null;
    private MetaV1 meta;
}
