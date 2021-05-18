package ru.instamart.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.instamart.kraken.setting.Config;

@JsonIgnoreProperties(ignoreUnknown = Config.REST_IGNORE_PROPERTIES)
public abstract class BaseResponseObject {
}
