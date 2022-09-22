package ru.instamart.api.response.authorization_service;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class RealmPostResponse extends BaseResponseObject {
    @JsonSchema(required = true)
    private String data;
}
