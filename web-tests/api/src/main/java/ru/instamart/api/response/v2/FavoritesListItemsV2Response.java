package ru.instamart.api.response.v2;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.ItemV2;
import ru.instamart.api.model.v2.MetaV2;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class FavoritesListItemsV2Response extends BaseResponseObject {
    @NotEmpty
    @JsonSchema(required = true)
    private List<ItemV2> items = null;
    @JsonSchema(required = true)
    private MetaV2 meta;
}
