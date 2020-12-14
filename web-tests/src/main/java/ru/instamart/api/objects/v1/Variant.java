package instamart.api.objects.v1;

import instamart.api.objects.BaseObject;
import instamart.api.objects.v2.Image;

import java.util.List;

public class Variant extends BaseObject {

    private Integer items_per_pack;
    private Double weight;
    private String displayed_volume;
    private String sku;
    private List<Image> images = null;

    public Integer getItems_per_pack() {
        return items_per_pack;
    }

    public void setItems_per_pack(Integer items_per_pack) {
        this.items_per_pack = items_per_pack;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getDisplayed_volume() {
        return displayed_volume;
    }

    public void setDisplayed_volume(String displayed_volume) {
        this.displayed_volume = displayed_volume;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

}
