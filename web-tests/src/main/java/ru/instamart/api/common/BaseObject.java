package instamart.api.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import instamart.core.settings.Config;

@JsonIgnoreProperties(ignoreUnknown = Config.CoreSettings.restIgnoreProperties)
public abstract class BaseObject {
}
