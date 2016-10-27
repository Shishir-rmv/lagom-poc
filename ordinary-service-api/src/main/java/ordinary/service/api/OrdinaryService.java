package ordinary.service.api;

import com.lightbend.lagom.javadsl.api.*;
import com.lightbend.lagom.javadsl.api.transport.Method;

import static com.lightbend.lagom.javadsl.api.Service.*;

public interface OrdinaryService extends Service {
	ServiceCall<String, String> sayHello(String id);

	ServiceCall<String, String> getStates(String countryCode);

	default Descriptor descriptor() {
		return named("ordinary").withCalls(
				restCall(Method.GET, "/order/:id", this::sayHello),
				restCall(Method.GET, "/getStates/:countryCode",
						this::getStates)).withAutoAcl(true);
	}
}
