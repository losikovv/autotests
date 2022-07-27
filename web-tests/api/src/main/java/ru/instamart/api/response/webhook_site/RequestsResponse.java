package ru.instamart.api.response.webhook_site;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class RequestsResponse extends BaseResponseObject {

    @JsonProperty("per_page")
    private int perPage;

    @JsonProperty("total")
    private int total;

    @JsonProperty("data")
    private List<DataItem> data;

    @JsonProperty("from")
    private int from;

    @JsonProperty("is_last_page")
    private boolean isLastPage;

    @JsonProperty("to")
    private int to;

    @JsonProperty("current_page")
    private int currentPage;

	@Data
	@EqualsAndHashCode(callSuper = false)
    public class DataItem extends BaseResponseObject{

        @JsonProperty("request")
        private Request request;

        @JsonProperty("headers")
        private Headers headers;

        @JsonProperty("method")
        private String method;

        @JsonProperty("ip")
        private String ip;

        @JsonProperty("query")
        private Query query;

        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("custom_action_output")
        private List<Object> customActionOutput;

        @JsonProperty("uuid")
        private String uuid;

        @JsonProperty("content")
        private String content;

        @JsonProperty("url")
        private String url;

        @JsonProperty("hostname")
        private String hostname;

        @JsonProperty("token_id")
        private String tokenId;

        @JsonProperty("updated_at")
        private String updatedAt;

        @JsonProperty("files")
        private Files files;

        @JsonProperty("user_agent")
        private String userAgent;
    }

	@Data
	@EqualsAndHashCode(callSuper = false)
	public class Request extends BaseResponseObject{

		@JsonProperty("status")
		private String status;
	}

	@Data
	@EqualsAndHashCode(callSuper = false)
	public class Headers extends BaseResponseObject{

		@JsonProperty("content-length")
		private List<String> contentLength;

		@JsonProperty("user-agent")
		private List<String> userAgent;
	}
	@Data
	@EqualsAndHashCode(callSuper = false)
	public class File extends BaseResponseObject{

		@JsonProperty("filename")
		private String filename;

		@JsonProperty("size")
		private int size;

		@JsonProperty("content_type")
		private String contentType;

		@JsonProperty("id")
		private String id;
	}
	@Data
	@EqualsAndHashCode(callSuper = false)
	public class Files extends BaseResponseObject{

		@JsonProperty("file")
		private File file;
	}
	@Data
	@EqualsAndHashCode(callSuper = false)
	public class Query extends BaseResponseObject{

		@JsonProperty("action")
		private String action;
	}
}