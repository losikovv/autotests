package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class CompanyDocumentPaymentToolsV2 extends BaseObject {
    @JsonProperty("payment_tool")
    private PaymentToolTypesV2 paymentTool;
    private Object bank;
    private Object address;
    private String bik;
    @JsonProperty("operating_account")
    private String operatingAccount;
    @JsonProperty("correspondent_account")
    private String correspondentAccount;
    private String name;
    private String inn;
    private String kpp;
    private int id;
}