package org.ccps.alphaflow.api;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.ccps.alphaflow.api.common.JsonUtils;
import org.ccps.alphaflow.api.common.ServiceExcuteResult;
import org.ccps.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component    
@Aspect
public class ServiceAspectAdvice {
	private static final Logger logger = LoggerFactory.getLogger(ServiceAspectAdvice.class);
	private static ObjectMapper mapper = new ObjectMapper();  
  
	@Autowired
	private BeanFactory beanFactory;
	@Value("${oa.systemcode}")
	private String systemcode;
	
	@Pointcut("execution(public * org.ccps.alphaflow.api.service..*.*(..))")
    public void anyMethod() {
		System.out.println("anyMethod");
    }
	
	 //前置通知
	   //在切点方法集合执行前，执行前置通知
	   @Before("execution(public * org.ccps.alphaflow.api.service..*.*(..))")
	    public void doBefore(JoinPoint jp) {
		   String args = "";
		   try{
			   args = JsonUtils.ObjectToJsonStr(jp.getArgs());
		   }catch(Exception e){};
		   logger.debug(systemcode+"  #doBefore advice# className:" + jp.getTarget().getClass() +"\t methodName:"+jp.getSignature().getName()+"\t arguments:"+ args );
	      
	    }
	   // 后置通知  
	   @AfterReturning(value = "anyMethod()", returning = "result")
	      public void doAfter(JoinPoint jp, Object result) {
			   String args = "";
			   try{
				   args = JsonUtils.ObjectToJsonStr(jp.getArgs());
			   }catch(Exception e){};
		   	  logger.debug(systemcode+"  #doAfter advice#  className:" + jp.getTarget().getClass() +"\t methodName:"+jp.getSignature().getName()+"\t arguments:"+ args );
		      logger.info(systemcode+"  #doAfter advice#  return:"+ JsonUtils.objectToJson( result));
		   /*   ServiceExcuteResult res = (ServiceExcuteResult)result;
				JSONObject js = new JSONObject();
				js.put("bb", "2");
				res.setResult(js);*/
		      
	      }
	   @Around("execution(public * org.ccps.alphaflow.api.service..*.*(..))")
	    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		   	logger.debug(systemcode+ "  #doAround advice# start");

	        // 调用方法的参数
	        Object[] args = pjp.getArgs();
	        // 调用的方法名
	        String method = pjp.getSignature().getName();
	        // 获取目标对象(形如：com.action.admin.LoginAction@1a2467a)
	        Object target = pjp.getTarget();
	        //获取目标对象的类名(形如：com.action.admin.LoginAction)
	        String targetName = pjp.getTarget().getClass().getName();
	        Object result = new ServiceExcuteResult();
	        if(StringUtil.isBlank(systemcode)){
	        	result = pjp.proceed();//result的值就是被拦截方法的返回值
	        }else{
	        	  String realtargetName = targetName.substring(targetName.lastIndexOf(".")+1)+systemcode.toUpperCase();
	        	  String fchar = String.valueOf(realtargetName.charAt(0));	        	
	        	  realtargetName = realtargetName.replaceFirst(fchar, fchar.toLowerCase());
	        	  int argsize = args==null?0:args.length;
	        	  boolean b = beanFactory.containsBean(realtargetName);
	        	  if(b){
	        		  Object svrobj = beanFactory.getBean(realtargetName);
	        		  if(svrobj!=null){
	        			  boolean isValidMethod = false;
			        	  Method[] bms = svrobj.getClass().getDeclaredMethods();
			        	  for(Method md : bms){
			        		  if(md.getName().equals(method)&&md.getParameterTypes().length==argsize){
			        			  isValidMethod = true;
			        			  result = md.invoke(svrobj,args);
			        			  break;
			        		  }
			        	  }
			        	  if(!isValidMethod)pjp.proceed();
	        		  }else logger.warn(" cant find "+realtargetName+" ");
	        	  }else
	        		 result = pjp.proceed();
	        }
	    	logger.debug(systemcode+ "  #doAround advice# end");
	    	logger.debug(systemcode+ "  输出："+ method + ";" + target + ";" + JsonUtils.objectToJson(result) );
	      
	      return result;
	    }
	   
	   @AfterThrowing(value = "execution(public * org.ccps.alphaflow.api.service..*.*(..))", throwing = "e")
	    public void doThrow(JoinPoint jp, Throwable e) {
		   String args = "";
		   try{
			   args = JsonUtils.ObjectToJsonStr(jp.getArgs());
		   }catch(Exception e1){};
		   logger.error(e.getMessage()+"\n"+systemcode+"  #className:" + jp.getTarget().getClass() +"\t methodName:"+jp.getSignature().getName()+"\t arguments:"+ args );
	    }

}
