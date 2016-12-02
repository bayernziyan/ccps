package org.ccps.alphaflow.api.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExceptionUtil {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);
	public static RestResult exceptionWapper(Exception e){
		logger.error(e.getMessage());
		if(e instanceof RestException){
			RestException e1 = (RestException)e;
			return new RestResult(e1.getCode(),e1.getMessage(),null);
		}else if(e instanceof InvocationTargetException){
			InvocationTargetException e1 = (InvocationTargetException)e;
			Throwable t = e1.getTargetException();
			if(t instanceof RestException){
				RestException e2 = (RestException)t;
				return new RestResult(e2.getCode(),e2.getMessage(),null);
			}else
				return new RestResult(-1,t.getMessage(),null);
		}else
			return new RestResult(-1,e.getMessage(),null);
	}
	
	
	

	public final static int SUCCESS = 200;
	public final static int NOT_FIND = 404;
	public final static int HAS_EXCEPTION = 502;

	//自定义错误代码100-199
	//用户登录代码
	
	public final static int LOGIN_FAILED = 101;
	public final static int USER_LOCK_FAILED = 102;
	
	//用户已经存在
	public final static int USER_EXISTS = 112;
	
	/**
	 * 获取异常的堆栈信息
	 * 
	 * @param t
	 * @return
	 */
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		try {
			t.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
		}
	}
	
}
