package ru.instamart.application.rest.objects.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class BaseResponseObject {
}
