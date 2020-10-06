package instamart.api.v2.objects;

import instamart.api.common.BaseObject;

public class CompanyDocument extends BaseObject {

    private Integer id;
    private String name;
    private String inn;
    private String kpp;
    private String bik;
    private String bank;
    private String correspondent_account;
    private String operating_account;
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getBik() {
        return bik;
    }

    public void setBik(String bik) {
        this.bik = bik;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCorrespondent_account() {
        return correspondent_account;
    }

    public void setCorrespondent_account(String correspondent_account) {
        this.correspondent_account = correspondent_account;
    }

    public String getOperating_account() {
        return operating_account;
    }

    public void setOperating_account(String operating_account) {
        this.operating_account = operating_account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
