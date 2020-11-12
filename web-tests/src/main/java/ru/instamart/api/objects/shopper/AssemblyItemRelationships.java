package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class AssemblyItemRelationships extends BaseObject {
    private Cancellation cancellation;
    private ItemReturn itemReturn;

    public Cancellation getCancellation() {
        return cancellation;
    }

    public void setCancellation(Cancellation cancellation) {
        this.cancellation = cancellation;
    }

    public ItemReturn getItemReturn() {
        return itemReturn;
    }

    public void setItemReturn(ItemReturn itemReturn) {
        this.itemReturn = itemReturn;
    }

}
