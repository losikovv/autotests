
package ru.instamart.api.response.v1.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.AdminUserV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private AdminUserV1 user;
}
