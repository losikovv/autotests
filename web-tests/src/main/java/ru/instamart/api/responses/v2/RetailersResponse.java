package instamart.api.responses.v2;

import instamart.api.objects.v2.Retailer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class RetailersResponse {
    private List<Retailer> retailers = null;
}
