package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class RetailerV2 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private String slug;

    private String color;

    @JsonProperty(value = "logo_background_color")
    private String logoBackgroundColor;

    private String logo;

    @JsonSchema(required = true)
    private Boolean available;

    @Null
    private String environment;

    private String key;

    @Null
    private List<RequirementV2> requirements = null;

    @JsonProperty(value = "retailer_agreement")
    private Object retailerAgreement;

    @JsonSchema(required = true)
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
