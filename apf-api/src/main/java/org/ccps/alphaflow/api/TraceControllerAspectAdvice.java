package org.ccps.alphaflow.api;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.ccps.alphaflow.api.common.JsonUtils;
import org.ccps.alphaflow.api.common.RestResult;
import org.ccps.alphaflow.api.common.ServiceExcuteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.TraceRepository;
import org.springframework.stereotype.Component;

@Component    
@Aspect
public class TraceControllerAspectAdvice {
	@Autowired
	private TraceRepository traceRepository;
	
	@Around("execution(public * org.ccps.*.*.controller.*.*(..))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
	   

        // 调用方法的参数
        Object[] args = pjp.getArgs();
        // 调用的方法名
        String method = pjp.getSignature().getName();
        // 获取目标对象(形如：com.action.admin.LoginAction@1a2467a)
        Object target = pjp.getTarget();
        //获取目标对象的类名(形如：com.action.admin.LoginAction)
        String targetName = pjp.getTarget().getClass().getName();
        Object result = new ServiceExcuteResult();
        result = pjp.proceed();//result的值就是被拦截方法的返回值
        
      /* String inp = "";
 	   try{
 		  inp = JsonUtils.ObjectToJsonStr(args);
 	   }catch(Exception e1){};*/
        
 	  Map<String,Object> trace = new HashMap<String,Object>();
 	  trace.put("requestBody", args);
 	  //(result!=null&&result instanceof RestResult)?JsonUtils.ObjectToJsonStr(result).replaceAll("\\r", String.valueOf((char) 13)).replaceAll("\\n", String.valueOf((char) 10)):""
 	  trace.put("reponseBody", result);
 	  traceRepository.add(trace);
 	 
      return result;
    }
   
   @AfterThrowing(value = "execution(public * org.ccps.*.*.controller.*.*(..))", throwing = "e")
    public void doThrow(JoinPoint jp, Throwable e) {
	   String args = "";
	   try{
		   args = JsonUtils.ObjectToJsonStr(jp.getArgs()).replaceAll("\\r", "\r").replaceAll("\\n", "\n");
	   }catch(Exception e1){};
	   Map<String,Object> trace = new HashMap<String,Object>();
	   trace.put("error", args);	 
	   traceRepository.add(trace);	   
    }
}
