package org.ccps.alphaflow.api.dbmapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ccps.alphaflow.api.pojo.AlphaflowTemplateBean;
import org.ccps.alphaflow.api.pojo.SearchListExample;
import org.ccps.common.db.WhDbMapper;

public interface AlphaflowTemplateMapper extends WhDbMapper {
	
	public Integer getAlphaflowGroupIdByName(@Param("name") String name) throws SQLException;
	
	public Integer getAlphaflowSubGroupIdByName(@Param("name") String name) throws SQLException;
	/**
	 * 查询模板列表
	 * @param schEmp
	 * @return
	 * @throws SQLException
	 */
	public List<AlphaflowTemplateBean> getAlphaflowTemplateList(SearchListExample schEmp) throws SQLException;
}
