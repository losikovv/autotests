package instamart.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import instamart.core.settings.Config;

@JsonIgnoreProperties(ignoreUnknown = Config.CoreSettings.restIgnoreProperties)
abstract class BaseObject {
}
