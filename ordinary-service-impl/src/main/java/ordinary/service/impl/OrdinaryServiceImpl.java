package ordinary.service.impl;

import java.util.concurrent.CompletionStage;

import com.google.inject.Inject;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import ordinary.service.api.OrdinaryService;
import sample.helloworld.api.HelloService;
import sample.stateservice.StateTerritoryService;
import sample.stateservice.vo.CountryInfoResponse;

public class OrdinaryServiceImpl implements OrdinaryService {
	private final HelloService helloService;
	private final StateTerritoryService stateTerritoryService;

	@Inject
	public OrdinaryServiceImpl(HelloService helloService,
			StateTerritoryService stateTerritoryService) {
		this.helloService = helloService;
		this.stateTerritoryService = stateTerritoryService;
	}

	@Override
	public ServiceCall<String, String> sayHello(String name) {
		return msg -> {
			CompletionStage<String> response = helloService.hello(name)
					.invoke();
			return response
					.thenApply(answer -> "Hello service said: " + answer);
		};
	}

	@Override
	public ServiceCall<String, String> getStates(String countryCode) {
		return msg -> {
			CompletionStage<CountryInfoResponse> response = stateTerritoryService
					.getStates(countryCode).invoke(countryCode);
			return response
					.thenApply(answer -> "State-Territory service response: "
							+ answer);
		};
	}

}
