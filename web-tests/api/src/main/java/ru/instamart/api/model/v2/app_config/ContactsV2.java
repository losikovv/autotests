package ru.instamart.api.model.v2.app_config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContactsV2 extends BaseObject {
    private String phone;
    private String email;
    private String telegram;
    private String whatsapp;
}
