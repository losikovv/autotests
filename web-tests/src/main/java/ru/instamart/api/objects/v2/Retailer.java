package instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class Retailer extends BaseObject {
    private Integer id;
    private String name;
    private String slug;
    private String color;
    @JsonProperty(value = "logo_background_color")
    private String logoBackgroundColor;
    private String logo;
    private Boolean available;
    private String environment;
    private String key;
    private List<Object> requirements = null;
    @JsonProperty(value = "retailer_agreement")
    private Object retailerAgreement;

    @Override
    public String toString() {
        return new StringJoiner(
                ", ",
                "",
                "")
                .add(name)
                .add("id: " + id)
                .toString();
    }
}
