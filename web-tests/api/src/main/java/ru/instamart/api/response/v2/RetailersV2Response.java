package ru.instamart.api.response.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class RetailersV2Response extends BaseResponseObject {
    private List<RetailerV2> retailers = null;
}
