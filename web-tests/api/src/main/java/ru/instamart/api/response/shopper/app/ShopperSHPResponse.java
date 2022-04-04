package ru.instamart.api.response.shopper.app;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.shopper.app.AddressSHP;
import ru.instamart.api.model.shopper.app.ShopperSHP;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShopperSHPResponse extends BaseResponseObject {
    @JsonSchema(required = true)
    private ShopperSHP.Data data;
    @Null
    private List<Included> included;

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class Included extends BaseObject {
        @JsonSchema(required = true)
        private String id;
        @JsonSchema(required = true)
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
            @Null
            private Boolean fastPayment;
            @Null
            private Boolean fastPaymentCashless;
            @Null
            private String fastPaymentMetroStoreDns;
            private Boolean expressDelivery;
            @Null
            private Integer secondsForAssemblyItem;
            @Null
            private Integer additionalSecondsForAssembly;
            private Boolean autoRouting;
            @Null
            private Boolean boxScanning;
            @Null
            private Object deliveryAreaId;
            @JsonSchema(ignore = true)
            private AddressSHP location;
            private String scheduleType;
            private String scheduleTypeHumanName;
            @Null
            private String fastPaymentMetroBarcodeMasked;
            /**
             * Equipment attributes
             */
            private Integer shopperId;
            @Null
            private String serial;
            @Null
            private String suppliedAt;
            @Null
            private Object kind;
            /**
             * Role attributes
             */
            private String humanName;
        }
    }
}
