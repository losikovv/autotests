package ru.instamart.kafka.log_model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseObject {
}
