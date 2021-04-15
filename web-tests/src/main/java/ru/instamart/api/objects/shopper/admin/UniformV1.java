package ru.instamart.api.objects.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class UniformV1 extends BaseObject {
    private Integer id;
    private Integer shopperId;
    private String size;
    private String suppliedAt;
    private String replaceAt;
}
