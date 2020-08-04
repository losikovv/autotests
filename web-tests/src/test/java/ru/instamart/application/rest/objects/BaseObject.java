package ru.instamart.application.rest.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.instamart.application.Config;

@JsonIgnoreProperties(ignoreUnknown = Config.CoreSettings.restIgnoreProperties)
abstract class BaseObject {
}
