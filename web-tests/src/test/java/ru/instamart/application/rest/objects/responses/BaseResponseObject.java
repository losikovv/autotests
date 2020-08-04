package ru.instamart.application.rest.objects.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.instamart.application.Config;

@JsonIgnoreProperties(ignoreUnknown = Config.CoreSettings.restIgnoreProperties)
abstract class BaseResponseObject {
}
