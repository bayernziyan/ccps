package org.ccps.alphaflow.api.common;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 调用接口之后返回普通数据格式
 * 
 * @author BLUE
 * @date 2016年4月11日 下午3:44:02
 * @Description: TODO
 * @version V1.0
 */
public class RestResult {
	// 定义jackson对象
	private static final ObjectMapper MAPPER = new ObjectMapper();

	// 响应业务状态
	private Integer status;

	// 响应消息
	private String msg; 

	// 响应中的数据
	private Object data;

	public static RestResult build(Integer status, String msg, Object data) {
		return new RestResult(status, msg, data);
	}

	public static RestResult success(Object data) {
		return new RestResult(data);
	}

	public static RestResult success() {
		return new RestResult(null);
	}

	public RestResult() {

	}

	public static RestResult build(Integer status, String msg) {
		return new RestResult(status, msg, null);
	}

	public static RestResult execption(Throwable t){
		return new RestResult(ExceptionUtil.HAS_EXCEPTION, ExceptionUtil.getStackTrace(t), null);
	}
	
	public RestResult(Integer status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public RestResult(Object data) {
		this.status = ExceptionUtil.SUCCESS;
		this.msg = "OK";
		this.data = data;
	}

	// public Boolean isOK() {
	// return this.status == 200;
	// }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 将json结果集转化为RestResult对象
	 * @param jsonData json数据
	 * @param clazz RestResult中的object类型
	 * @return
	 */
	public static RestResult formatToPojo(String jsonData, Class<?> clazz) {
		try {
			if (clazz == null) {
				return MAPPER.readValue(jsonData, RestResult.class);
			}
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			Object obj = null;
			if (clazz != null) {
				if (data.isObject()) {
					obj = MAPPER.readValue(data.traverse(), clazz);
				} else if (data.isTextual()) {
					obj = MAPPER.readValue(data.asText(), clazz);
				}
			}
			return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 没有object对象的转化
	 * 
	 * @param json
	 * @return
	 */
	public static RestResult format(String json) {
		try {
			return MAPPER.readValue(json, RestResult.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Object是集合转化
	 * 
	 * @param jsonData json数据
	 * @param clazz 集合中的类型
	 * @return
	 */
	public static RestResult formatToList(String jsonData, Class<?> clazz) {
		try {
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			Object obj = null;
			if (data.isArray() && data.size() > 0) {
				obj = MAPPER.readValue(data.traverse(),
						MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
			}
			return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
		} catch (Exception e) {
			return null;
		}
	}
}
