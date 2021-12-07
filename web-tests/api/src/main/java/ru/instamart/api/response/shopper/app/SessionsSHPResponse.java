package ru.instamart.api.response.shopper.app;


import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.app.SessionSHP;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SessionsSHPResponse extends BaseResponseObject {
    @JsonSchema(required = true)
    private SessionSHP.Data data;
}
