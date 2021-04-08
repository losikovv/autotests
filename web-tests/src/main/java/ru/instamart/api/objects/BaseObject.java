package ru.instamart.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.instamart.core.settings.Config;

@JsonIgnoreProperties(ignoreUnknown = Config.REST_IGNORE_PROPERTIES)
public abstract class BaseObject {
}
