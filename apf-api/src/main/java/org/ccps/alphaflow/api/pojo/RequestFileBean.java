package org.ccps.alphaflow.api.pojo;

import java.util.Date;
import java.util.Vector;

public class RequestFileBean {
	private int id;
	private int flag;
	private int group_id;
	private int subgroup_id;
	private int requestId;
	private String requestDesc;
	private String requestType;

	private String title;
	private String fileName;
	private String oldFileName;
	private String pathStr;
	private String desc;
	private String oldDesc;
	private int order_id;
	// news or notices etc
	private int category;
	// map to FileTypeDef class
	private int type;
	// the following will be used as file extension name, or no longer needed
	private String url;

	private String fileType;
	private String fileSize;
	private String oldFileSize;
	private long sizeLong;
	private int fileLevel;
	private int parentId;

	private String firstuser = "";
	private java.util.Date firstdate;
	private String firstflag = "";

	private String filePath;
	private int versionNum;
	private String fileDir;
	// these three for portal display management
	private String display_flag;
	private int display_order;
	private String rootName;
	private int attachment_num;

	private String imp_flag;
	private int clickNum;

	private String str_att_1;
	private int int_att_1;

	// for
	private int workflowId;
	private String canDownload;
	private String lock_flag;
	private String pub_flag;
	private Date pub_date;
	private int realFileId;

	// Read, Write
	private String opr_flag;

	private String check_uld;
	private String uld_status;

	//
	private Vector v1;
	private Vector v2;
	private Vector v3;
	private int statusId;
	private String img_flag;
	private int wp_id;

	private Vector opr_flag_v;
	private String keyword;
	private String file_tab_key_id_str;
	private int click_count;
	private int comment_count;

	private int original_id;
	private int original_req_id;

	public String summary;
	public String searchKeyIndex;

	private int filePathFlag;
	private String set_preview_flag;
	private String set_fppre_flag;
	
	private String createUser;
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getSet_fppre_flag() {
		return set_fppre_flag == null ? "N" : set_fppre_flag;
	}

	public void setSet_fppre_flag(String set_fppre_flag) {
		this.set_fppre_flag = set_fppre_flag;
	}

	public String getSet_preview_flag() {
		return set_preview_flag == null ? "N" : set_preview_flag;
	}

	public void setSet_preview_flag(String set_preview_flag) {
		this.set_preview_flag = set_preview_flag;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getSearchKeyIndex() {
		return searchKeyIndex;
	}

	public void setSearchKeyIndex(String searchKeyIndex) {
		this.searchKeyIndex = searchKeyIndex;
	}

	public RequestFileBean() {
		super();
		this.requestId = 0;
		// default is root level
		this.fileLevel = 0;
		// default is none,which is at root level
		this.parentId = 0;
		this.display_flag = "N";
		this.opr_flag = "";
		opr_flag_v = new Vector();
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Vector getOpr_flag_v() {
		return opr_flag_v;
	}

	public void setRequestId(int id) {
		this.requestId = id;
	}

	public void setRequestDesc(String desc) {
		this.requestDesc = desc;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public void setRequestType(String containerType) {
		this.requestType = containerType;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setFileName(String name) {
		this.fileName = name;
	}

	public void setType(int typeId) {
		this.type = typeId;
	}

	public void setVersionNum(int versionNum) {
		this.versionNum = versionNum;
	}

	public void setClickNum(int clickNum) {
		this.clickNum = clickNum;
	}

	public int getClickNum() {
		return this.clickNum;
	}

	public String getTitle() {
		return this.title;
	}

	public int getCategory() {
		return this.category;
	}

	public int getType() {
		return this.type;
	}

	public void setURL(String url) {
		this.url = url;
	}

	public String getURL() {
		return this.url;
	}

	public void setFilePath(String path) {
		this.filePath = path;
	}

	public void setFileType(String type) {
		this.fileType = type;
	}

	public void setFileSize(String size) {
		this.fileSize = size;
	}

	public void setSizeLong(long sizeLong) {
		this.sizeLong = sizeLong;
	}

	public void setFileLevel(int level) {
		this.fileLevel = level;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public void setFirstUser(String setUser) {
		this.firstuser = setUser;
	}

	public void setFirstDate(java.util.Date setDate) {
		this.firstdate = setDate;
	}

	public void setFirstFlag(String setFlag) {
		this.firstflag = setFlag;
	}

	public void setDirName(String dirname) {
		this.fileDir = dirname;
	}

	public void setDisplayFlag(String display_flag) {
		this.display_flag = display_flag;
	}

	public void setDisplayOrder(int display_order) {
		this.display_order = display_order;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public void setImpFlag(String imp_flag) {
		this.imp_flag = imp_flag;
	}

	public int getRequestId() {
		return this.requestId;
	}

	public String getRequestDesc() {
		return this.requestDesc;
	}

	public String getRequestType() {
		return this.requestType;
	}

	public String getDesc() {
		return this.desc;
	}

	public String getFileName() {
		return this.fileName;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public String getFileType() {
		return this.fileType;
	}

	public String getFileSize() {
		return this.fileSize;
	}

	public long getSizeLong() {
		return this.sizeLong;
	}

	public int getFileLevel() {
		return this.fileLevel;
	}

	public int getParentId() {
		return this.parentId;
	}

	public String getFirstUser() {
		return this.firstuser;
	}

	public java.util.Date getFirstDate() {
		return this.firstdate;
	}

	public String getFirstFlag() {
		return this.firstflag;
	}

	public int getVersionNum() {
		return this.versionNum;
	}

	public String getDirName() {
		return this.fileDir;
	}

	public String getDisplayFlag() {
		return this.display_flag;
	}

	public int getDisplayOrder() {
		return this.display_order;
	}

	public String getRootName() {
		return this.rootName;
	}

	public String getImpFlag() {
		return this.imp_flag;
	}

	public int getWorkflowId() {
		return this.workflowId;
	}

	public String getCanDownload() {
		return canDownload == null ? "N" : canDownload;
	}

	public String getLock_flag() {
		return lock_flag == null ? "Y" : lock_flag;
	}

	public String getPub_flag() {
		return pub_flag;
	}

	public Date getPub_date() {
		return pub_date;
	}

	public String getStr_att_1() {
		return str_att_1;
	}

	public int getInt_att_1() {
		return int_att_1;
	}

	public String getOpr_flag() {
		return opr_flag;
	}

	public int getRealFileId() {
		return realFileId;
	}

	public Vector getV1() {
		return v1;
	}

	public Vector getV2() {
		return v2;
	}

	public Vector getV3() {
		return v3;
	}

	public String getCheck_uld() {
		return check_uld;
	}

	public String getUld_status() {
		return uld_status;
	}

	public int getStatusId() {
		return statusId;
	}

	public String getImg_flag() {
		return img_flag;
	}

	public int getWp_id() {
		return wp_id;
	}

	public void setWorkflowId(int workflowId) {
		this.workflowId = workflowId;
	}

	public void setCanDownload(String canDownload) {
		this.canDownload = canDownload;
	}

	public void setLock_flag(String lock_flag) {
		this.lock_flag = lock_flag;
	}

	public void setPub_flag(String pub_flag) {
		this.pub_flag = pub_flag;
	}

	public void setPub_date(Date pub_date) {
		this.pub_date = pub_date;
	}

	public void setStr_att_1(String str_att_1) {
		this.str_att_1 = str_att_1;
	}

	public void setInt_att_1(int int_att_1) {
		this.int_att_1 = int_att_1;
	}

	public void setOpr_flag(String opr_flag) {
		this.opr_flag = opr_flag;
	}

	public void setRealFileId(int realFileId) {
		this.realFileId = realFileId;
	}

	public void setV1(Vector v1) {
		this.v1 = v1;
	}

	public void setV2(Vector v2) {
		this.v2 = v2;
	}

	public void setV3(Vector v3) {
		this.v3 = v3;
	}

	public void setCheck_uld(String check_uld) {
		this.check_uld = check_uld;
	}

	public void setUld_status(String uld_status) {
		this.uld_status = uld_status;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public void setImg_flag(String img_flag) {
		this.img_flag = img_flag;
	}

	public void setWp_id(int wp_id) {
		this.wp_id = wp_id;
	}

	public int getAttachmentNum() {
		return this.attachment_num;
	}

	public void setAttachmentNum(int attachment_num) {
		this.attachment_num = attachment_num;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int groupId) {
		group_id = groupId;
	}

	public int getSubgroup_id() {
		return subgroup_id;
	}

	public void setSubgroup_id(int subgroupId) {
		subgroup_id = subgroupId;
	}

	public String getOldFileName() {
		return oldFileName;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getFile_tab_key_id_str() {
		return file_tab_key_id_str;
	}

	public int getClick_count() {
		return click_count;
	}

	public int getComment_count() {
		return comment_count;
	}

	public void setOldFileName(String oldFileName) {
		this.oldFileName = oldFileName;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setFile_tab_key_id_str(String file_tab_key_id_str) {
		this.file_tab_key_id_str = file_tab_key_id_str;
	}

	public void setClick_count(int click_count) {
		this.click_count = click_count;
	}

	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}

	public String getPathStr() {
		return pathStr;
	}

	public void setPathStr(String pathStr) {
		this.pathStr = pathStr;
	}

	public int getOriginal_id() {
		return original_id;
	}

	public void setOriginal_id(int original_id) {
		this.original_id = original_id;
	}

	public int getOriginal_req_id() {
		return original_req_id;
	}

	public void setOriginal_req_id(int original_req_id) {
		this.original_req_id = original_req_id;
	}

	public String getOldFileSize() {
		return oldFileSize;
	}

	public void setOldFileSize(String oldFileSize) {
		this.oldFileSize = oldFileSize;
	}

	public String getOldDesc() {
		return oldDesc;
	}

	public void setOldDesc(String oldDesc) {
		this.oldDesc = oldDesc;
	}

	public int getFilePathFlag() {
		return filePathFlag;
	}

	public void setFilePathFlag(int filePathFlag) {
		this.filePathFlag = filePathFlag;
	}

	// 流程表单附件上传 by zc 2013/1/6
	private int related_item_id;

	public int getRelated_item_id() {
		return related_item_id;
	}

	public void setRelated_item_id(int relatedItemId) {
		related_item_id = relatedItemId;
	}

}
