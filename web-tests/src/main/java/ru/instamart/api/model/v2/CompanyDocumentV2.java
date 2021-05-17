package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanyDocumentV2 extends BaseObject {
    private Integer id;
    private String name;
    private String inn;
    private String kpp;
    private String bik;
    private String bank;
    @JsonProperty(value = "correspondent_account")
    private String correspondentAccount;
    @JsonProperty(value = "operating_account")
    private String operatingAccount;
    private String address;
}
