package ru.instamart.api.model.ris_exporter;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.delivery_club.CategoryDC;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class CategoriesDataRis extends BaseObject {
    @JsonSchema(required = true)
    private List<CategoryDC> categories;
}
