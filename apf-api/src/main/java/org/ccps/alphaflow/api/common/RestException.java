package org.ccps.alphaflow.api.common;

public class RestException extends RuntimeException{

	private int code;
	private String errmsg;
	
	private static String getMessage(int code){
		switch(code){
			case -1		:return "系统错误";
			case 0		:return "请求成功";
			case 100	:return "参数为空";
			case 101	:return "用户不存在";
			case 102    :return "缺少系统参数";
			//流程
			//表单
			case 20001	:return "未找到标识匹配的表单组件";
			//流程实例
			case 30001	:return "流程发起失败";
			case 30002  :return "流程不存在";
			case 30003	:return "流程撤回未找到当前用户办理并已提交的环节";
			case 30004	:return "流程撤回未找到待办中的后续办理人员";
			case 30005	:return "当前流程状态，不允许此操作";
			//流程附件
			case 30101  :return "流程附件创建用户为空";
			//流程环节
			case 40001	:return "流程环节不存在";
			case 40002	:return "流程环节已存在相同的经办人";
			case 40003	:return "当前环节状态，不允许此操作";
			default:
				return  null;
		}
	}
	
	public int getCode() {
		return  code;
	}
	public RestException(Exception e) {
		super(e.getMessage());
		if(e instanceof RestException){
			this.code = ((RestException) e).getCode();
		}else
			this.code = -1;		
	}
	public RestException(int code) {
		super(getMessage(code));
		this.code = code;
	}
}
