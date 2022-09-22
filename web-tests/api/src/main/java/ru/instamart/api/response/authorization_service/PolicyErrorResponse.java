package ru.instamart.api.response.authorization_service;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PolicyErrorResponse extends BaseResponseObject {

    private RealmPutErrorResponse.Error error;

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class Error extends BaseObject {
        @JsonSchema(required = true)
        private String detail;
        @JsonSchema(required = true)
        private int status;
        @JsonSchema(required = true)
        private String title;
        @JsonSchema(required = true)
        private String type;
    }
}
