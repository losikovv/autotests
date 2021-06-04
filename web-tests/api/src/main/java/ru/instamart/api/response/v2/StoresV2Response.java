package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.MetaV2;
import ru.instamart.api.model.v2.StoreV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoresV2Response extends BaseResponseObject {
    private List<StoreV2> stores = null;
    private MetaV2 meta;
    @JsonProperty(value = "store_labels")
    private List<Object> storeLabels = null;
}
