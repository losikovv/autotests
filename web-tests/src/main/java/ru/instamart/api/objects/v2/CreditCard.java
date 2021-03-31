package instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CreditCard extends BaseObject {
    private Integer id;
    private Object title;
    private String name;
    private String year;
    private String month;
    @JsonProperty(value = "last_digits")
    private String lastDigits;
    private String state;
    @JsonProperty(value = "cc_type")
    private String ccType;
    private Boolean eligible;
}
