package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class ReceiptsAttributes extends BaseObject {
    private String total;
    private String paidAt;
    private String fiscalSecret;
    private String fiscalChecksum;
    private String fiscalDocumentNumber;
    private String transactionDetails;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(String paidAt) {
        this.paidAt = paidAt;
    }

    public String getFiscalSecret() {
        return fiscalSecret;
    }

    public void setFiscalSecret(String fiscalSecret) {
        this.fiscalSecret = fiscalSecret;
    }

    public String getFiscalChecksum() {
        return fiscalChecksum;
    }

    public void setFiscalChecksum(String fiscalChecksum) {
        this.fiscalChecksum = fiscalChecksum;
    }

    public String getFiscalDocumentNumber() {
        return fiscalDocumentNumber;
    }

    public void setFiscalDocumentNumber(String fiscalDocumentNumber) {
        this.fiscalDocumentNumber = fiscalDocumentNumber;
    }

    public String getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(String transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

}
