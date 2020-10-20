package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class ShopperRelationships extends BaseObject {

    private Vehicles vehicles;
    private Equipment equipment;
    private Uniforms uniforms;
    private Store store;
    private Roles roles;

    public Vehicles getVehicles() {
        return vehicles;
    }

    public void setVehicles(Vehicles vehicles) {
        this.vehicles = vehicles;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Uniforms getUniforms() {
        return uniforms;
    }

    public void setUniforms(Uniforms uniforms) {
        this.uniforms = uniforms;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

}
