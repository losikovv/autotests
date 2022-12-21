package ru.instamart.api.response.shadowcat;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.*;
import ru.instamart.api.model.shadowcat.Promocode;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class PromocodesResponse extends BaseResponseObject {
    private int count;
    @JsonSchema(required = true)
    private List<Promocode> items;
}

