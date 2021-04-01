package instamart.api.responses.v2;

import instamart.api.objects.v2.Retailer;
import instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class RetailersResponse extends BaseResponseObject {
    private List<Retailer> retailers = null;
}
