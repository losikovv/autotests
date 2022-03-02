package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProfileConfigV2 extends BaseObject {

	@Null
	@JsonProperty("default_bonus_card_id")
	private String defaultBonusCardId;

	@JsonProperty("send_emails")
	private Boolean sendEmails;

	@JsonProperty("send_push")
	private Boolean sendPush;

	@JsonProperty("send_sms")
	private Boolean sendSms;
}