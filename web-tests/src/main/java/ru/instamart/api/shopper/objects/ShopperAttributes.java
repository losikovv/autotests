package instamart.api.shopper.objects;

import instamart.api.common.BaseObject;

public class ShopperAttributes extends BaseObject {

    private String name;
    private String login;
    private String phone;
    private String status;
    private String inn;
    private String employmentType;
    private Object contractNumber;
    private Object contractDate;
    private Integer vehiclesCount;
    private Integer activeVehiclesCount;
    private Boolean phoneNeedsConfirmation;
    private Object phoneConfirmedAt;
    private Object firebaseToken;
    private Boolean tipsEnabled;
    private Object tipsLinkUrl;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public Object getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(Object contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Object getContractDate() {
        return contractDate;
    }

    public void setContractDate(Object contractDate) {
        this.contractDate = contractDate;
    }

    public Integer getVehiclesCount() {
        return vehiclesCount;
    }

    public void setVehiclesCount(Integer vehiclesCount) {
        this.vehiclesCount = vehiclesCount;
    }

    public Integer getActiveVehiclesCount() {
        return activeVehiclesCount;
    }

    public void setActiveVehiclesCount(Integer activeVehiclesCount) {
        this.activeVehiclesCount = activeVehiclesCount;
    }

    public Boolean getPhoneNeedsConfirmation() {
        return phoneNeedsConfirmation;
    }

    public void setPhoneNeedsConfirmation(Boolean phoneNeedsConfirmation) {
        this.phoneNeedsConfirmation = phoneNeedsConfirmation;
    }

    public Object getPhoneConfirmedAt() {
        return phoneConfirmedAt;
    }

    public void setPhoneConfirmedAt(Object phoneConfirmedAt) {
        this.phoneConfirmedAt = phoneConfirmedAt;
    }

    public Object getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(Object firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public Boolean getTipsEnabled() {
        return tipsEnabled;
    }

    public void setTipsEnabled(Boolean tipsEnabled) {
        this.tipsEnabled = tipsEnabled;
    }

    public Object getTipsLinkUrl() {
        return tipsLinkUrl;
    }

    public void setTipsLinkUrl(Object tipsLinkUrl) {
        this.tipsLinkUrl = tipsLinkUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
