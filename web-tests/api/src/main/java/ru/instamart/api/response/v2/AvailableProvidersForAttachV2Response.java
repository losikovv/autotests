
package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.AvailableProviderV2;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AvailableProvidersForAttachV2Response extends BaseResponseObject {

    @NotEmpty
    @JsonSchema(required = true)
    @JsonProperty("available_providers")
    private List<AvailableProviderV2> availableProviders;
}
