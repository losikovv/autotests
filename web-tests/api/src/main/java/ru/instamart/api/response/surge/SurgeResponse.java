package ru.instamart.api.response.surge;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SurgeResponse extends BaseResponseObject {
    private Data data;

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    public static class Data extends BaseResponseObject {
        @JsonProperty(value = "Active")
        private Boolean active;
        @JsonProperty(value = "CreatedAt")
        private String createdAt;
        @JsonProperty(value = "DeliveryAreaBaseStoreUUID")
        private String deliveryAreaBaseStoreUUID;
        @JsonProperty(value = "ExpiresAt")
        private String expiresAt;
        @JsonProperty(value = "UpdatedAt")
        private String updatedAt;
    }
}
