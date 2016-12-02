package org.ccps.alphaflow.api.config;


import org.ccps.alphaflow.api.controller.AlphaflowInstanceController;
import org.ccps.alphaflow.api.controller.AlphaflowTaskController;
import org.ccps.alphaflow.api.controller.AlphaflowTemplateController;
import org.ccps.alphaflow.api.controller.HtmlPageController;
import org.ccps.alphaflow.api.controller.OASSOTokenConntroller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration 
//@Import(ServiceConfiguration.class)
@ComponentScan(basePackages={"org.ccps.alphaflow.api.controller","org.ccps.alphaflow.api.service"})
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
	
	/*@Bean
	public AlphaflowInstanceController alphaflowInstanceController(){
		return new AlphaflowInstanceController();
	}
	
	@Bean
	public AlphaflowTaskController alphaflowTaskController(){
		return new AlphaflowTaskController();
	}
	
	@Bean
	public AlphaflowTemplateController alphaflowTemplateController(){
		return new AlphaflowTemplateController();
	}
	
	@Bean
	public HtmlPageController htmlPageController(){
		return new HtmlPageController();
	}
	
	@Bean
	public OASSOTokenConntroller oassoTokenConntroller(){
		return new OASSOTokenConntroller();
	}*/

}
