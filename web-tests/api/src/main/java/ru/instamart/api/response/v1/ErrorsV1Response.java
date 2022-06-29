package ru.instamart.api.response.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorsV1Response {
    private List<String> errors;
}
