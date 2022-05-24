package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeliveryWindowV2 extends BaseObject {

    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    @JsonProperty(value = "starts_at")
    private String startsAt;

    @JsonSchema(required = true)
    @JsonProperty(value = "ends_at")
    private String endsAt;

    @JsonProperty(value = "weight_balance")
    private Object weightBalance;

    @JsonProperty(value = "items_count_balance")
    private Object itemsCountBalance;

    private String kind;

    @JsonProperty("delivery_forecast_text")
    private Object deliveryForecastText;

    private Object lifetime;

    private String date;

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
