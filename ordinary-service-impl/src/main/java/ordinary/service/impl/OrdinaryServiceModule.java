package ordinary.service.impl;

import ordinary.service.api.OrdinaryService;
import sample.helloworld.api.HelloService;
import sample.stateservice.StateTerritoryService;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

import play.Environment;
import play.Configuration;
import play.Mode;

import com.google.inject.Inject;

public class OrdinaryServiceModule extends AbstractModule implements
		ServiceGuiceSupport {

	private final Environment environment;
	ConsulClient client;
	NewService service;

	@Inject
	public OrdinaryServiceModule(Environment environment,
			Configuration configuration) {
		this.environment = environment;
	}

	@Override
	protected void configure() {
		bindClient(HelloService.class);
		bindClient(StateTerritoryService.class);
		bindServices(serviceBinding(OrdinaryService.class,
				OrdinaryServiceImpl.class));

		if (environment.mode() == Mode.PROD) {
			String consulHostname = "localhost";
			String serviceName = "ordinary";
			String serviceId = "ordinaryService";
			String serviceAddress = "localhost";
			int servicePort = 9000;

			service = new NewService();
			service.setId(serviceId);
			service.setName(serviceName);
			service.setPort(servicePort);
			service.setAddress(serviceAddress);

			client = new ConsulClient(consulHostname);
			client.agentServiceRegister(service);
		}
	}

}
