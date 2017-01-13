package org.ccps.common.db.util;

import java.util.Properties;

import com.github.pagehelper.PageHelper;

public class PageUtil {
	/*private static final PageHelper ORACLE = oracle();
	private static final PageHelper SQLSERVER = sqlserver();
	private static final PageHelper MYSQL = mysql();*/
	
	public static PageHelper dialect(String name){
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		if("oracle".equalsIgnoreCase(name)){
			properties.setProperty("dialect", "oracle");
		}else if("sqlserver".equalsIgnoreCase(name)){
			properties.setProperty("dialect", "sqlserver");
		}else if("mysql".equalsIgnoreCase(name)){
			properties.setProperty("dialect", "mysql");
		}else
			properties.setProperty("dialect", "oracle");
		properties.setProperty("pageSizeZero", "true");
		properties.setProperty("offsetAsPageNum", "true");
		properties.setProperty("rowBoundsWithCount", "true");
		properties.setProperty("reasonable", "true");
		pageHelper.setProperties(properties);

		return pageHelper;
		//return oracle();
	}
	
	public static PageHelper oracle() {
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("dialect", "oracle");
		// properties.setProperty("reasonable", "false");
		properties.setProperty("pageSizeZero", "true");
		properties.setProperty("offsetAsPageNum", "true");
		properties.setProperty("rowBoundsWithCount", "true");
		properties.setProperty("reasonable", "true");
		pageHelper.setProperties(properties);

		return pageHelper;

	}

	private static PageHelper mysql() {
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("dialect", "mysql");
		// properties.setProperty("reasonable", "false");
		properties.setProperty("pageSizeZero", "true");
		properties.setProperty("offsetAsPageNum", "true");
		properties.setProperty("rowBoundsWithCount", "true");
		properties.setProperty("reasonable", "true");
		pageHelper.setProperties(properties);

		return pageHelper;
	}
	
	private static PageHelper sqlserver() {
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("dialect", "sqlserver");
		// properties.setProperty("reasonable", "false");
		properties.setProperty("pageSizeZero", "true");
		properties.setProperty("offsetAsPageNum", "true");
		properties.setProperty("rowBoundsWithCount", "true");
		properties.setProperty("reasonable", "true");
		pageHelper.setProperties(properties);

		return pageHelper;
	}
}
