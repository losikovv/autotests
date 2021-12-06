package ru.instamart.api.response.ris_exporter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public final class TokenRisResponse extends BaseResponseObject {
    private String token;
    private String expiresAt;
}
