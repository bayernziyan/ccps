package org.ccps.alphaflow.api.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ccps.common.ObjectMappingCustomer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;

import net.sf.json.JSONObject;


public class JsonUtils {

    // 定义jackson对象
    private static final ObjectMappingCustomer MAPPER = new ObjectMappingCustomer();
    
    public static String ObjectToJsonStr(Object data) throws JsonProcessingException{
    	String string = MAPPER.writeValueAsString(data);
		return string;
    }
    
    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
    	try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 将json结果集转化为对象
     * 
     * @param jsonData json数据
     * @param clazz 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    public static <T> T objectToPojo(Object obj,Class<T> beanType){
        try {
            T t = MAPPER.convertValue(obj, beanType);
            return t;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		List<T> list = MAPPER.readValue(jsonData, javaType);
    		return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    /**
     * 把json字符串转成map
     * @param JsonData
     * @return
     */
    public static Map<String, String> jsonToMap(JSONObject object){
    	Map<String, String> datamap = new HashMap<>();
    	Iterator it = object.keys();
    	while(it.hasNext()){
    		String key = (String)it.next();
    		String value = object.getString(key);
    		if(!org.ccps.common.util.StringUtil.isBlank(value)) datamap.put(key, value);
    	}
    	return datamap;
    }
    
    /**
     * 把一个 object对象的属性转换成map
     * @param data
     * @return
     */
    public static Map<String, String> objectToMap(Object data){
    	JSONObject object = JSONObject.fromObject(data);
    	return jsonToMap(object);
    }
    
}
