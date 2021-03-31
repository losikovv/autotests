package instamart.api.responses.deliveryclub;

import instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderDCResponse extends BaseResponseObject {
    public String id;
    public String status;
}
