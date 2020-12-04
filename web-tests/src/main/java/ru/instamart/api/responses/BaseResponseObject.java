package instamart.api.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import instamart.core.settings.Config;

@JsonIgnoreProperties(ignoreUnknown = Config.REST_IGNORE_PROPERTIES)
public abstract class BaseResponseObject {
}
