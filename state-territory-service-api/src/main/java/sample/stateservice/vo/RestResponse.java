package sample.stateservice.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({ "messages" })
public class RestResponse {

	@JsonProperty("result")
	private List<State> states;

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	@Override
	public String toString() {
		return "RestResponse [states=" + states + "]";
	}

}
