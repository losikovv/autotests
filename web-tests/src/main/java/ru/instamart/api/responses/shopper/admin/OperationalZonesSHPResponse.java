package ru.instamart.api.responses.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.admin.MetaV1;
import ru.instamart.api.objects.v1.OperationalZoneV1;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationalZonesSHPResponse extends BaseResponseObject {
    private List<OperationalZoneV1> operationalZones = null;
    private MetaV1 meta;
}
