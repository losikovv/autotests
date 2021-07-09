package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;


@Data
@EqualsAndHashCode(callSuper = false)
public class DeliveryWindowsItemV2 extends BaseObject {
    private String presentation;
    @JsonProperty("starts_at")
    private String startsAt;
    private double price;
    private boolean active;
    private int id;
    @JsonProperty("ends_at")
    private String endsAt;
}