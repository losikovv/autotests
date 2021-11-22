package ru.instamart.api.response.delivery_club;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.delivery_club.SlotDC;
import ru.instamart.api.model.delivery_club.ZoneDC;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class SlotsDCResponse extends BaseResponseObject {
    @JsonSchema(required = true)
    private ZoneDC zone;
    @JsonSchema(required = true)
    private List<SlotDC> slots;
}
