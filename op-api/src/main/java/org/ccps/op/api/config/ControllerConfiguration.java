package org.ccps.op.api.config;


import org.ccps.op.api.controller.TestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration 
//@Import(ServiceConfiguration.class)
@ComponentScan(basePackages={"org.ccps.op.api.controller","org.ccps.op.api.service"})
public class ControllerConfiguration {
	
	@Value("${navigation.url.base}")
	private String navigationBaseUrl;
	
	@Value("${server.context-path}")
	private String serverContextPath;
	
/*	@Autowired
	private ServiceConfiguration serviceConfiguration;*/
	

	/*@Bean
	public OpApiResourceAssembler actorResourceAssembler(){
		return new OpApiResourceAssembler();
	}
	@Bean
	public ActorController actorController(){
		return new ActorController(serviceConfiguration.actorService(), serverContextPath, actorResourceAssembler(), navigationBaseUrl);
	}

	@Bean
	public PartialActorController partialActorController(){
		return new PartialActorController(serviceConfiguration.actorService(), serverContextPath, actorResourceAssembler(), navigationBaseUrl);
	}*/
	
	@Bean
	public TestController testController(){
		return new TestController();
	}
	
	

}
