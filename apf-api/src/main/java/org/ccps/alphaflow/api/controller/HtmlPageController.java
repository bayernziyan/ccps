package org.ccps.alphaflow.api.controller;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**   
* @author BLUE  
* @date 2016年5月20日 上午11:52:32 
* @Description: TODO 
* @version V1.0   
*/

@Controller
public class HtmlPageController {

	@RequestMapping("/html/{page}")
	public String showHtml(@PathVariable String page){
		return "/html/"+page;
	}
	public static void main(String[] args) {
		
			ApplicationContext factory = new FileSystemXmlApplicationContext("D:/msproject/econ-apis-rest/econ-apis-rest-web/src/main/resources/spring/*.xml"); 
			String projectcode = "ata";
			String mappername = "com.apis.mapper.AlphaflowTaskMapper";
			String  mappername1 = mappername.substring(16)+projectcode.toUpperCase();
			System.out.println(mappername1);
			Object  a  = null;
			String[]  b = factory.getBeanDefinitionNames();
			for(String n : b){
				System.out.println(n);
			}

			try {
				Thread.sleep(10000l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			if(factory.containsBean(mappername1))
				a =  factory.getBean(mappername1);
			else
				a = factory.getBean(mappername.substring(16));
			System.out.println("a1:"+(a==null));
			
		
	}
	
}
