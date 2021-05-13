package ru.instamart.api.response.delivery_club;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderDCResponse extends BaseResponseObject {
    private String id;
    private String status;
}
