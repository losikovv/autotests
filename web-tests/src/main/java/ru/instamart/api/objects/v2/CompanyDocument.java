package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanyDocument extends BaseObject {
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
