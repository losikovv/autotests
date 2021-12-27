package ru.instamart.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.ErrorMessageV2;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorsListResponse extends BaseResponseObject {
    private List<ErrorMessageV2> errors;
}