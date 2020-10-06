package instamart.api.v2.objects;

import instamart.api.common.BaseObject;

public class Source extends BaseObject {

    private String type;
    private CompanyDocument company_document;
    private CreditCard credit_card;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CompanyDocument getCompany_document() {
        return company_document;
    }

    public void setCompany_document(CompanyDocument company_document) {
        this.company_document = company_document;
    }

    public CreditCard getCredit_card() {
        return credit_card;
    }

    public void setCredit_card(CreditCard credit_card) {
        this.credit_card = credit_card;
    }

}
