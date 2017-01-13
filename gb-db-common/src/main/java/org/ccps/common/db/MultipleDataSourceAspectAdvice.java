package org.ccps.common.db;

import java.lang.annotation.Annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.ccps.common.db.repository.Wh1DbRepository;
import org.slf4j.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(-1)
@Aspect
public class MultipleDataSourceAspectAdvice {
	private static final Logger logger = LoggerFactory.getLogger(MultipleDataSourceAspectAdvice.class);
	
	@Around("execution(* org.ccps.*.*.dbmapper..*.*(..))")
	public Object doAround(ProceedingJoinPoint jp) throws Throwable {
		String dbtypeName = ""; 
		 for(Annotation annt : jp.getSignature().getDeclaringType().getDeclaredAnnotations()){
			 String annName = annt.annotationType().getSimpleName();
			 if(annName.contains("Repository")){
				 dbtypeName = annName.replace("Repository", "").toLowerCase();
				 break;
			 }
		 }
		 if(dbtypeName.isEmpty())return jp.proceed();
		 logger.info("db type change to " + dbtypeName + " when execute " + jp.getSignature().getName());
		 for(DatabaseType dbtype : DatabaseType.values()){
			 if(dbtype.name().equals(dbtypeName)){
				 DatabaseContextHolder.setDatabaseType(dbtype);
				 break;
			 }
		 }
		 
		 return jp.proceed();
		/*
		if (jp.getTarget().getClass().geta instanceof OpDbMapper) {
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
		return jp.proceed();*/
	}
	public static void main(String[] args) {
		System.out.println(Wh1DbRepository.class.getSimpleName());
	}
	

}
