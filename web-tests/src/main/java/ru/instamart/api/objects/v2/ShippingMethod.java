package instamart.api.objects.v2;

import instamart.api.objects.BaseObject;

import java.util.StringJoiner;

public class ShippingMethod extends BaseObject {

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
