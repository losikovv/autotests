
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MarketingSampleV1 extends BaseObject {

    private String comment;
    private String description;
    @JsonProperty("expires_at")
    private String expiresAt;
    private Long id;
    @JsonProperty("image_url")
    private String imageUrl;
    private String name;
    @JsonProperty("starts_at")
    private String startsAt;
    private List<MarketingStoreV1> stores;
    private List<TenantV1> tenants;
}
