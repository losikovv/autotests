package ru.instamart.api.objects.v2;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingMethod extends BaseObject {
    private Integer id;
    private String name;

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
