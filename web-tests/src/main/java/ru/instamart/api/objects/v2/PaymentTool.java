package instamart.api.objects.v2;

import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentTool extends BaseObject {
    private Integer id;
    private String name;
    private String type;
    private Source source;

    @Override
    public String toString() {
        return new StringJoiner(
                ", ",
                "",
                "")
                .add(name)
                .add("id: " + id)
                .add("type: " + type)
                .toString();
    }
}
