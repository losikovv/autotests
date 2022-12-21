package ru.instamart.api.model.shadowcat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.instamart.api.model.BaseObject;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerSC extends BaseObject {

    private String id;

    @JsonProperty("orders_count")
    private int ordersCount;

    private String subscription;

    @JsonProperty("registered_at")
    private String registeredAt;

    private String birthday;

    @JsonProperty("sber_loyalty")
    private boolean sberLoyalty;
}
