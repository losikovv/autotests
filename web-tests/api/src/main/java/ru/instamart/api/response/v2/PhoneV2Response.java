package ru.instamart.api.response.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.PhoneV2;

@EqualsAndHashCode(callSuper = true)
@Data
public class PhoneV2Response  extends BaseObject {
    private PhoneV2 phone;
}