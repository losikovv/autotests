package ru.instamart.api.response.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.shopper.app.AddressSHP;
import ru.instamart.api.model.shopper.app.ShopperSHP;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShopperSHPResponse extends BaseResponseObject {
    private ShopperSHP.Data data;
    private List<Included> included = null;

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class Included extends BaseObject {
        private String id;
        private String type;
        private Attributes attributes;

        @Data
        @EqualsAndHashCode(callSuper=false)
        public static class Attributes extends BaseObject {
            /**
             * Base attributes
             */
            private String name;
            /**
             * Store attributes
             */
            private String uuid;
            private Integer operationalZoneId;
            private Integer retailerId;
            private Boolean active;
            private Boolean hasConveyor;
            private Boolean fastPayment;
            private Boolean fastPaymentCashless;
            private Boolean fastPaymentMetroStoreDns;
            private Boolean expressDelivery;
            private Integer secondsForAssemblyItem;
            private Integer additionalSecondsForAssembly;
            private Boolean autoRouting;
            private Boolean boxScanning;
            private Object deliveryAreaId;
            private AddressSHP location;
            private String scheduleType;
            private String scheduleTypeHumanName;
            private String fastPaymentMetroBarcodeMasked;
            /**
             * Equipment attributes
             */
            private Integer shopperId;
            private String serial;
            private String suppliedAt;
            private Object kind;
            /**
             * Role attributes
             */
            private String humanName;
        }
    }
}
