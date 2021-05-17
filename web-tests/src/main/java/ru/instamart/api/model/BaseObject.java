package ru.instamart.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.instamart.core.setting.Config;

@JsonIgnoreProperties(ignoreUnknown = Config.REST_IGNORE_PROPERTIES)
public abstract class BaseObject {
}
