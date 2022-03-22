
package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.AddressV1;
import ru.instamart.api.model.v1.StoreV1;
import ru.instamart.api.model.v1.UserV1;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class NextSessionV1Response extends BaseResponseObject {

    @Null
    @JsonSchema(required = true)
    private AddressV1 address;

    @JsonSchema(required = true)
    @JsonProperty("current_store")
    private StoreV1 currentStore;

    @JsonSchema(required = true)
    @JsonProperty("is_adult_user")
    private Boolean isAdultUser;

    @JsonSchema(required = true)
    @JsonProperty("is_store_selected")
    private Boolean isStoreSelected;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("sber_sub_id")
    private Object sberSubId;

    @Null
    @JsonSchema(required = true)
    private UserV1 user;
}
