package ru.instamart.api.response.authorization_service;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = false)
public class AuthorizationResponse extends BaseResponseObject {
    @JsonSchema(required = true)
    private ArrayList<String> data;
}
