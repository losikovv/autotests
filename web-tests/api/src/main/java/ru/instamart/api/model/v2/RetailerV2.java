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

    @Null
    @JsonProperty(value = "short_name")
    private String shortName;

    @JsonSchema(required = true)
    private String slug;

    @Null
    @JsonProperty(value = "created_at")
    private String createdAt;

    @Null
    private String phone;

    @EqualsAndHashCode.Exclude
    private String color;

    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "logo_background_color")
    private String logoBackgroundColor;

    @EqualsAndHashCode.Exclude
    private String logo;

    @EqualsAndHashCode.Exclude
    @JsonSchema(required = true)
    private Boolean available;

    @Null
    @EqualsAndHashCode.Exclude
    private String environment;

    @EqualsAndHashCode.Exclude
    private String key;

    @Null
    @EqualsAndHashCode.Exclude
    private List<RequirementV2> requirements = null;

    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "retailer_agreement")
    private Object retailerAgreement;

    @EqualsAndHashCode.Exclude
    @JsonSchema(required = true)
    private AppearanceV2 appearance;

    @EqualsAndHashCode.Exclude
    private String services;

    @EqualsAndHashCode.Exclude
    private String description;

    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "retailer_services")
    private List<Object> retailerServices = null;

    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "operational_times")
    private List<Object> operationalTimes = null;

    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "next_delivery")
    private NextDeliveryV2 nextDelivery;

    @EqualsAndHashCode.Exclude
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
