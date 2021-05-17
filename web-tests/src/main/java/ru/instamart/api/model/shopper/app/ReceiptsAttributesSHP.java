package ru.instamart.api.model.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReceiptsAttributesSHP extends BaseObject {
    private String total;
    private String paidAt;
    private String fiscalSecret;
    private String fiscalChecksum;
    private String fiscalDocumentNumber;
    private String transactionDetails;
}
