package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeliveryWindowV2 extends BaseObject {
    private Integer id;
    @JsonProperty(value = "starts_at")
    private String startsAt;
    @JsonProperty(value = "ends_at")
    private String endsAt;
    @JsonProperty(value = "weight_balance")
    private String weightBalance;
    @JsonProperty(value = "items_count_balance")
    private String itemsCountBalance;
    private String kind;

    @Override
    public String toString() {
        return new StringJoiner(
                "\n",
                "Получена информация о слоте:\n",
                "\n")
                .add("                 id: " + id)
                .add("          starts_at: " + startsAt)
                .add("            ends_at: " + endsAt)
                .add("     weight_balance: " + weightBalance)
                .add("items_count_balance: " + itemsCountBalance)
                .add("               kind: " + kind)
                .toString();
    }
}
