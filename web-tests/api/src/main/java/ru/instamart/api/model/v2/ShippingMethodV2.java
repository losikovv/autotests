package ru.instamart.api.model.v2;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingMethodV2 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private String kind;

    @Override
    public String toString() {
        return new StringJoiner(
                ", ",
                "Получена информация о способе доставки:\n",
                "\n")
                .add(name)
                .add("id: " + id)
                .toString();
    }
}
