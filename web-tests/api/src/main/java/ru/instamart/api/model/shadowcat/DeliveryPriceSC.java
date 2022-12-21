package ru.instamart.api.model.shadowcat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.instamart.api.model.BaseObject;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeliveryPriceSC extends BaseObject {

    @JsonProperty("delivery_price")
    private double deliveryPrice;

    @JsonProperty("delivery_surge")
    private double deliverySurge;

    @JsonProperty("assembly_price")
    private double assemblyPrice;

    @JsonProperty("bags_price")
    private double bagsPrice;
}
