package ru.instamart.api.response.surge;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorSurgeResponse extends BaseResponseObject {
    private String message;
}
