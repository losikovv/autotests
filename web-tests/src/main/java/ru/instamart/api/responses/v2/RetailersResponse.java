package ru.instamart.api.responses.v2;

import ru.instamart.api.objects.v2.Retailer;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class RetailersResponse extends BaseResponseObject {
    private List<Retailer> retailers = null;
}
