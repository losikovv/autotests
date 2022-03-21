package ru.instamart.kraken.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class PaymentCardData {

    private final String cardNumber;
    private final String expiryMonth;
    private final String expiryYear;
    private final String cardholderName;
    private final String cvvNumber;
    private final String password;
    private final boolean secure;
}
