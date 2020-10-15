package instamart.api.shopper.objects;

import instamart.api.common.BaseObject;

public class StoreOrRoleAttributes extends BaseObject {

    /**
     * Both
     */
    private String name;
    /**
     * Store
     */
    private String uuid;
    private Integer operationalZoneId;
    private Integer retailerId;
    private Boolean active;
    private Boolean hasConveyor;
    private Boolean fastPayment;
    private Object fastPaymentMetroStoreDns;
    private Boolean expressDelivery;
    private Integer secondsForAssemblyItem;
    private Integer additionalSecondsForAssembly;
    private Boolean autoRouting;
    private Boolean boxScanning;
    private Object deliveryAreaId;
    private Address location;
    /**
     * Role
     */
    private String humanName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getOperationalZoneId() {
        return operationalZoneId;
    }

    public void setOperationalZoneId(Integer operationalZoneId) {
        this.operationalZoneId = operationalZoneId;
    }

    public Integer getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Integer retailerId) {
        this.retailerId = retailerId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getHasConveyor() {
        return hasConveyor;
    }

    public void setHasConveyor(Boolean hasConveyor) {
        this.hasConveyor = hasConveyor;
    }

    public Boolean getFastPayment() {
        return fastPayment;
    }

    public void setFastPayment(Boolean fastPayment) {
        this.fastPayment = fastPayment;
    }

    public Object getFastPaymentMetroStoreDns() {
        return fastPaymentMetroStoreDns;
    }

    public void setFastPaymentMetroStoreDns(Object fastPaymentMetroStoreDns) {
        this.fastPaymentMetroStoreDns = fastPaymentMetroStoreDns;
    }

    public Boolean getExpressDelivery() {
        return expressDelivery;
    }

    public void setExpressDelivery(Boolean expressDelivery) {
        this.expressDelivery = expressDelivery;
    }

    public Integer getSecondsForAssemblyItem() {
        return secondsForAssemblyItem;
    }

    public void setSecondsForAssemblyItem(Integer secondsForAssemblyItem) {
        this.secondsForAssemblyItem = secondsForAssemblyItem;
    }

    public Integer getAdditionalSecondsForAssembly() {
        return additionalSecondsForAssembly;
    }

    public void setAdditionalSecondsForAssembly(Integer additionalSecondsForAssembly) {
        this.additionalSecondsForAssembly = additionalSecondsForAssembly;
    }

    public Boolean getAutoRouting() {
        return autoRouting;
    }

    public void setAutoRouting(Boolean autoRouting) {
        this.autoRouting = autoRouting;
    }

    public Boolean getBoxScanning() {
        return boxScanning;
    }

    public void setBoxScanning(Boolean boxScanning) {
        this.boxScanning = boxScanning;
    }

    public Object getDeliveryAreaId() {
        return deliveryAreaId;
    }

    public void setDeliveryAreaId(Object deliveryAreaId) {
        this.deliveryAreaId = deliveryAreaId;
    }

    public Address getLocation() {
        return location;
    }

    public void setLocation(Address location) {
        this.location = location;
    }

    public String getHumanName() {
        return humanName;
    }

    public void setHumanName(String humanName) {
        this.humanName = humanName;
    }

}
