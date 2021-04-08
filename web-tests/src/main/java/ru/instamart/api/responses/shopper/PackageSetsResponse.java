package ru.instamart.api.responses.shopper;

import ru.instamart.api.objects.shopper.PackageSetData;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class PackageSetsResponse extends BaseResponseObject {
    private List<PackageSetData> data = null;
}
