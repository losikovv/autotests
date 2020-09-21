package instamart.api.objects.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import instamart.core.settings.Config;

@JsonIgnoreProperties(ignoreUnknown = Config.CoreSettings.restIgnoreProperties)
abstract class BaseResponseObject {
}
