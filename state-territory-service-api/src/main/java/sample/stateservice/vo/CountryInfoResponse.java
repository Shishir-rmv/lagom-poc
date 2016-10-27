package sample.stateservice.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryInfoResponse {

	@JsonProperty("RestResponse")
	private RestResponse response;

	public RestResponse getResponse() {
		return response;
	}

	public void setResponse(RestResponse response) {
		this.response = response;
	}

}
