package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class PaymentMethodStoresEntity {
    private Long id;
    private Long paymentMethodId;
    private Integer storeId;
    private String tenantId;
    private Long shippingMethodKindId;
}
