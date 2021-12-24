package ru.instamart.kafka.log_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.k8s.rails_response.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class Response extends BaseObject {
	private String msg;
	@JsonProperty("trace_id")
	private String traceId;
	private String level;
	@JsonProperty("span_id")
	private String spanId;
	private String timestamp;
	@JsonProperty("grpc.service")
	private String grpcService;
	private String method;
	private String request;
	private String response;
	private String error;

}