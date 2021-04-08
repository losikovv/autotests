package ru.instamart.api.responses.shopper;

import ru.instamart.api.responses.BaseResponseObject;
import ru.instamart.api.objects.shopper.Error;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorResponse extends BaseResponseObject {
    private List<Error> errors = null;
}
