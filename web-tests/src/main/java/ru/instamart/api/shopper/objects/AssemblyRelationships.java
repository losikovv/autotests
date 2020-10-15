package instamart.api.shopper.objects;

import instamart.api.common.BaseObject;

public class AssemblyRelationships extends BaseObject {

    private Shipment shipment;
    private Items items;
    private PackageSets packageSets;

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public PackageSets getPackageSets() {
        return packageSets;
    }

    public void setPackageSets(PackageSets packageSets) {
        this.packageSets = packageSets;
    }

}
