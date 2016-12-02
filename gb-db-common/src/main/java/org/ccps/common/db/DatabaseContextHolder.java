package org.ccps.common.db;

public class DatabaseContextHolder {
	private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();

	public static void setDatabaseType(DatabaseType type) {
		contextHolder.set(type);
	}

	public static DatabaseType getDatabaseType() {
		return contextHolder.get();
	}
	
	public static void clearDataSourceType() {
        contextHolder.remove();
    }
}
