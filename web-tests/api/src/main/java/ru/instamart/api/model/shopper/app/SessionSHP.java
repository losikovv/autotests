package ru.instamart.api.model.shopper.app;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class SessionSHP extends BaseObject {

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    public static class Data extends BaseObject {
        @JsonSchema(required = true)
        private String id;
        @JsonSchema(required = true)
        private String type;
        @JsonSchema(required = true)
        private Attributes attributes;

        @lombok.Data
        @EqualsAndHashCode(callSuper=false)
        public static class Attributes extends BaseObject {
            @JsonSchema(required = true)
            private String accessToken;
            @JsonSchema(required = true)
            private String refreshToken;
            @JsonSchema(required = true)
            private String expiresAt;
        }
    }
}
