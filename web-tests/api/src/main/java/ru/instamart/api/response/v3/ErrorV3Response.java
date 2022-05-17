package ru.instamart.api.response.v3;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorV3Response extends BaseResponseObject {
    private String type;
    private String title;
    private Integer status;
}
