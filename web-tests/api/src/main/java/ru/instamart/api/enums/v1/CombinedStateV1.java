package ru.instamart.api.enums.v1;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum CombinedStateV1 {
    SHIPMENT_READY("shipment_ready"),
    SHIPMENT_PENDING("shipment_pending"),
    DISPATCH_POSTPONED("dispatch_postponed"),
    DISPATCH_NEW("dispatch_new"),
    DISPATCH_AUTOMATIC_ROUTING("dispatch_automatic_routing"),
    DISPATCH_OFFERING("dispatch_offering"),
    DISPATCH_MANUAL_ROUTING("dispatch_manual_routing"),
    DISPATCH_OFFERED("dispatch_offered");

    private final String value;
}
