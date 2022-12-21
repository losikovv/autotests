package ru.instamart.api.response.shadowcat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorShadowcatResponse extends BaseResponseObject {
    private String type;
    private String title;
    private Integer status;
    private String detail;
}
