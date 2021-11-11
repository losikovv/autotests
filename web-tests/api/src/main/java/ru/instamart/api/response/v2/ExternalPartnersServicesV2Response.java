
package ru.instamart.api.response.v2;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.ServicesV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ExternalPartnersServicesV2Response extends BaseResponseObject {

    private List<ServicesV2> services;
}
