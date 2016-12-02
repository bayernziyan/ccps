package org.ccps.common.db;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(-1)
@Aspect
public class MultipleDataSourceAspectAdvice {
	@Around("execution(* org.ccps.*.*.dbmapper..*.*(..))")
	public Object doAround(ProceedingJoinPoint jp) throws Throwable {
		 if (jp.getTarget() instanceof OpDbMapper) {
			System.out.println("op db ===========");
			DatabaseContextHolder.setDatabaseType(DatabaseType.opdb);
			// MultipleDataSource.setDataSourceKey("wxDataSource");
		}else if (jp.getTarget() instanceof WhDbMapper) {
			System.out.println("wh db ===========");
			DatabaseContextHolder.setDatabaseType(DatabaseType.whdb);
		} else if (jp.getTarget() instanceof Wh1DbMapper) {
			System.out.println("wh1 db ===========");
			DatabaseContextHolder.setDatabaseType(DatabaseType.wh1db);
			// MultipleDataSource.setDataSourceKey("wxDataSource");
		}
		return jp.proceed();
	}

	

}
