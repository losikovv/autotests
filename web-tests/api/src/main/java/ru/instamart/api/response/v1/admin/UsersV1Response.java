
package ru.instamart.api.response.v1.admin;

import java.util.List;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.MetaV1;
import ru.instamart.api.response.BaseResponseObject;
import ru.instamart.api.model.v1.AdminUserV1;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper=false)
public class UsersV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private MetaV1 meta;

    @NotEmpty
    @JsonSchema(required = true)
    private List<AdminUserV1> users;
}
