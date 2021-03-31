package instamart.api.responses.shopper;

import instamart.api.objects.shopper.PackageSetData;
import instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class PackageSetsResponse extends BaseResponseObject {
    private List<PackageSetData> data = null;
}
