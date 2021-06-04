package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;
import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class RetailerV2 extends BaseObject {
    private Integer id;
    private String name;
    private String slug;
    private String color;
    @JsonProperty(value = "logo_background_color")
    private String logoBackgroundColor;
    private String logo;
    private Boolean available;
    private String environment;
    private String key;
    private List<RequirementV2> requirements = null;
    @JsonProperty(value = "retailer_agreement")
    private Object retailerAgreement;
    private AppearanceV2 appearance;
    private String services;
    private String description;
    @JsonProperty(value = "retailer_services")
    private List<Object> retailerServices = null;
    @JsonProperty(value = "operational_times")
    private List<Object> operationalTimes = null;
    @JsonProperty(value = "next_delivery")
    private NextDeliveryV2 nextDelivery;
    private String icon;

    @Override
    public String toString() {
        return new StringJoiner(
                ", ",
                "",
                "")
                .add(name)
                .add("id: " + id)
                .toString();
    }
}
