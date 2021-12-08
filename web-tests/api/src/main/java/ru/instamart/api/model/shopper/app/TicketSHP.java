package ru.instamart.api.model.shopper.app;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class TicketSHP extends BaseObject {

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    public static class Data extends BaseObject {
        @JsonSchema(required = true)
        private String id;
        @JsonSchema(required = true)
        private String type;
        @JsonSchema(required = true)
        private Attributes attributes;
        @JsonSchema(ignore = true)
        private Relationships relationships;

        @lombok.Data
        @EqualsAndHashCode(callSuper=false)
        public static class Attributes extends BaseObject {
            @JsonSchema(required = true)
            private String title;
            @Null
            @JsonSchema(required = true)
            private String status;
            @JsonSchema(required = true)
            private String uuid;
            @JsonSchema(required = true)
            private Boolean synced;
            @JsonSchema(required = true)
            private Integer unreadCount;
        }

        @lombok.Data
        @EqualsAndHashCode(callSuper=false)
        public static class Relationships extends BaseObject {
            private MessagesSHP messages;
        }
    }
}
