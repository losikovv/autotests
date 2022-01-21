package ru.instamart.api.model.shopper.app;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShopperSHP extends BaseObject {

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    public static class Data extends BaseObject {
        @JsonSchema(required = true)
        private String id;
        @JsonSchema(required = true)
        private String type;
        @JsonSchema(required = true)
        private Attributes attributes;
        private Relationships relationships;

        @lombok.Data
        @EqualsAndHashCode(callSuper=false)
        public static class Attributes extends BaseObject {
            @JsonSchema(required = true)
            private String name;
            @JsonSchema(required = true)
            private String login;
            @JsonSchema(required = true)
            @Null
            private String phone;
            @JsonSchema(required = true)
            private String status;
            @Null
            private String inn;
            @Null
            private String employmentType;
            @Null
            private Object contractNumber;
            @Null
            private String contractDate;
            @Null
            private Integer vehiclesCount;
            @Null
            private Integer activeVehiclesCount;
            @Null
            private Boolean phoneNeedsConfirmation;
            @Null
            private String phoneConfirmedAt;
            @Null
            private String firebaseToken;
            @Null
            private Boolean tipsEnabled;
            @Null
            private String tipsLinkUrl;
            @Null
            private String type;
            @Null
            private Object avgScore;
            @Null
            private Boolean isTopShopper;
        }

        @lombok.Data
        @EqualsAndHashCode(callSuper=false)
        public static class Relationships extends BaseObject {
            private VehiclesSHP vehicles;
            private EquipmentSHP equipment;
            private UniformsSHP uniforms;
            private StoreSHP store;
            private RolesSHP roles;
            private DeliveryAreaStoresSHP deliveryAreaStores;
            private EnabledFeaturesSHP enabledFeatures;
        }
    }
}
