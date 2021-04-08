package ru.instamart.api.objects.shopper;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReceiptsAttributes extends BaseObject {
    private String total;
    private String paidAt;
    private String fiscalSecret;
    private String fiscalChecksum;
    private String fiscalDocumentNumber;
    private String transactionDetails;
}
