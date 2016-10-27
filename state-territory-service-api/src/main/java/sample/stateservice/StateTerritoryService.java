package sample.stateservice;

import static com.lightbend.lagom.javadsl.api.Service.named;
import sample.stateservice.serializers.ResponseSerializer;
import sample.stateservice.vo.CountryInfoResponse;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import static com.lightbend.lagom.javadsl.api.Service.*;

public interface StateTerritoryService extends Service {
	ServiceCall<String, CountryInfoResponse> getStates(String countryCode);

	@Override
	default Descriptor descriptor() {
		return named("stateTerritory").withCalls(
				restCall(Method.GET, "/state/get/:countryCode/all",
						this::getStates).withResponseSerializer(
						new ResponseSerializer())).withAutoAcl(true);
	}

}
