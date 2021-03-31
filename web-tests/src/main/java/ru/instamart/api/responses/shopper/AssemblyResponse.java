package instamart.api.responses.shopper;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.shopper.AssemblyData;
import instamart.api.objects.shopper.AssemblyItemData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblyResponse extends BaseResponseObject {
    private AssemblyData data;
    private List<AssemblyItemData> included = null;
}
