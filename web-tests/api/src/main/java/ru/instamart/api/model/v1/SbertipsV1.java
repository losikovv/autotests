
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SbertipsV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("sbertips_sms_link_template")
    private String sbertipsSmsLinkTemplate;

    @JsonSchema(required = true)
    @JsonProperty("sbertips_sms_testing_store_ids")
    private String sbertipsSmsTestingStoreIds;

    @JsonSchema(required = true)
    @JsonProperty("send_sbertips_sms")
    private Boolean sendSbertipsSms;
}
