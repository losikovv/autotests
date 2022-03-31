
package ru.instamart.api.response.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.MetaV1;
import ru.instamart.api.model.v1.TicketV1;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class TicketsV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private MetaV1 meta;

    @NotEmpty
    @JsonSchema(required = true)
    private List<TicketV1> tickets;
}
