package ru.instamart.api.model.v1.b2b;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper = false)
public class CompanyDocumentV1 extends BaseObject {

    @Null
    private String bank;

    @Null
    private String address;

    @Null
    private String bik;

    @Null
    @JsonProperty(value = "operating_account")
    private String operatingAccount;

    @Null
    @JsonProperty(value = "correspondent_account")
    private String correspondentAccount;

    private String name;

    @JsonSchema(required = true)
    private String inn;

    @Null
    private String kpp;

    @JsonSchema(required = true)
    private Integer id;

    @JsonProperty(value = "user_id")
    private Integer userId;

    private Boolean approved;
}
