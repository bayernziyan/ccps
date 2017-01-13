package org.ccps.common.db;

public enum DatabaseType {
	 whdb,opdb,wh1db;
	 
	 private String dbtype;
	 public void setDbType(String dbtype){
		 this.dbtype = dbtype;
	 }
	 public String getDbType(){
		 return dbtype;
	 }
}
