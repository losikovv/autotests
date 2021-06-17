package ru.instamart.api.model.v1.b2b;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanyV1 extends BaseObject {
    private Boolean prepay;
    private Boolean postpay;
    private String inn;
    private String name;
    private Boolean deposit;
    private Integer id;
    @JsonProperty(value = "sales_contract")
    private SalesContractV1 salesContract;
    private ErrorsV1 errors;
    @JsonProperty(value = "owner_name")
    private String ownerName;
    private Boolean approved;
    @JsonProperty(value = "manager_comment")
    private String managerComment;
    @JsonProperty(value = "payment_account")
    private String paymentAccount;
    @JsonProperty(value = "link_to_crm")
    private String linkToCrm;
    private List<EmployeeV1> employees;
    @JsonProperty(value = "company_documents")
    private List<CompanyDocumentV1> companyDocuments;
    @JsonProperty(value = "sales_contracts")
    private List<SalesContractV1> salesContracts;
}
