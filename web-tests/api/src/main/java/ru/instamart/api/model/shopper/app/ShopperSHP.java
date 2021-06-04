package ru.instamart.api.model.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShopperSHP extends BaseObject {

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    public static class Data extends BaseObject {
        private String id;
        private String type;
        private Attributes attributes;
        private Relationships relationships;

        @lombok.Data
        @EqualsAndHashCode(callSuper=false)
        public static class Attributes extends BaseObject {
            private String name;
            private String login;
            private String phone;
            private String status;
            private String inn;
            private String employmentType;
            private Object contractNumber;
            private String contractDate;
            private Integer vehiclesCount;
            private Integer activeVehiclesCount;
            private Boolean phoneNeedsConfirmation;
            private String phoneConfirmedAt;
            private String firebaseToken;
            private Boolean tipsEnabled;
            private String tipsLinkUrl;
            private String type;
            private Object avgScore;
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
