package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper = false)
public class ActiveExternalPartnersSubscriptionsV1 extends BaseObject {

    @Null
    @JsonProperty("packet_kind")
    private String packetKind;

    private ServiceV1 service;

    @JsonProperty("free_deliveries_info")
    private FreeDeliveriesInfoV1 freeDeliveriesInfo;

    private int id;

    @JsonProperty("expired_date")
    private String expiredDate;
}