package org.ccps.common.db;

import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.ccps.common.db.util.PageUtil;

public class PageInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation jp) throws Throwable {
		return PageUtil.dialect(DatabaseContextHolder.getDatabaseType().getDbType()).intercept(jp);
	}

	@Override
	public Object plugin(Object arg0) {
		return PageUtil.dialect(DatabaseContextHolder.getDatabaseType().getDbType()).plugin(arg0);
	}

	@Override
	public void setProperties(Properties arg0) {
		PageUtil.dialect(DatabaseContextHolder.getDatabaseType().getDbType()).setProperties(arg0);
	}

}
