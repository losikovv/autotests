package instamart.api.objects.v1;

import instamart.api.objects.BaseObject;

public class LineItem extends BaseObject {

    private String human_volume;
    private Integer id;
    private String image;
    private String name;
    private Integer offer_id;
    private String product_permalink;
    private Integer quantity;
    private Integer retailer_id;
    private Integer shipment_id;
    private String shipment_number;
    private String small_image;
    private String sku;
    private Object assembly_issue;
    private Integer items_per_pack;
    private Integer packs;
    private Double price;
    private String price_type;
    private Double total;
    private Double unit_price;
    private Double unit_quantity;
    private String updated_at;
    private String uuid;
    private Integer variant_id;
    private Integer vat_rate;
    private Offer offer;
    private Object master_category;

    public String getHuman_volume() {
        return human_volume;
    }

    public void setHuman_volume(String human_volume) {
        this.human_volume = human_volume;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(Integer offer_id) {
        this.offer_id = offer_id;
    }

    public String getProduct_permalink() {
        return product_permalink;
    }

    public void setProduct_permalink(String product_permalink) {
        this.product_permalink = product_permalink;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getRetailer_id() {
        return retailer_id;
    }

    public void setRetailer_id(Integer retailer_id) {
        this.retailer_id = retailer_id;
    }

    public Integer getShipment_id() {
        return shipment_id;
    }

    public void setShipment_id(Integer shipment_id) {
        this.shipment_id = shipment_id;
    }

    public String getShipment_number() {
        return shipment_number;
    }

    public void setShipment_number(String shipment_number) {
        this.shipment_number = shipment_number;
    }

    public String getSmall_image() {
        return small_image;
    }

    public void setSmall_image(String small_image) {
        this.small_image = small_image;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Object getAssembly_issue() {
        return assembly_issue;
    }

    public void setAssembly_issue(Object assembly_issue) {
        this.assembly_issue = assembly_issue;
    }

    public Integer getItems_per_pack() {
        return items_per_pack;
    }

    public void setItems_per_pack(Integer items_per_pack) {
        this.items_per_pack = items_per_pack;
    }

    public Integer getPacks() {
        return packs;
    }

    public void setPacks(Integer packs) {
        this.packs = packs;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPrice_type() {
        return price_type;
    }

    public void setPrice_type(String price_type) {
        this.price_type = price_type;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }

    public Double getUnit_quantity() {
        return unit_quantity;
    }

    public void setUnit_quantity(Double unit_quantity) {
        this.unit_quantity = unit_quantity;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getVariant_id() {
        return variant_id;
    }

    public void setVariant_id(Integer variant_id) {
        this.variant_id = variant_id;
    }

    public Integer getVat_rate() {
        return vat_rate;
    }

    public void setVat_rate(Integer vat_rate) {
        this.vat_rate = vat_rate;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Object getMaster_category() {
        return master_category;
    }

    public void setMaster_category(Object master_category) {
        this.master_category = master_category;
    }

}
