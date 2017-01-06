package org.ccps.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.ccps.common.ObjectMappingCustomer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class JSONUtil {
        private ObjectMappingCustomer objectMapper;  
      
        public static JSONUtil json() {  
            return new JSONUtil();  
        }  
      
        private JSONUtil() {  
            objectMapper = new ObjectMappingCustomer();  
            // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性  
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);  
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);  
      
            //objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));  
        }  
      
        
        
        public JSONUtil filterIn(String filterName, String... properties) {  
            FilterProvider filterProvider = new SimpleFilterProvider().addFilter(filterName,  
                    SimpleBeanPropertyFilter.filterOutAllExcept(properties)); 
            
           // SimpleBeanPropertyFilter.filterOutAllExcept("");//这里需要填需要留下的属性
            //SimpleBeanPropertyFilter.serializeAllExcept("");//这里需要真需要排除的属性
            objectMapper.setFilters(filterProvider);  
            return this;  
        }  
        public JSONUtil filterOut(String filterName, String... properties) {  
            FilterProvider filterProvider = new SimpleFilterProvider().addFilter(filterName,  
                    SimpleBeanPropertyFilter.serializeAllExcept(properties)); 
            
            // SimpleBeanPropertyFilter.filterOutAllExcept("");//这里需要填需要留下的属性
            //SimpleBeanPropertyFilter.serializeAllExcept("");//这里需要真需要排除的属性
            objectMapper.setFilters(filterProvider);  
            return this;  
        }  
       
        public JSONUtil setDateFormate(DateFormat dateFormat) {  
            objectMapper.setDateFormat(dateFormat);  
            return this;  
        }  
      
        public <T> T json2Obj(String json, Class<T> clazz) {  
            try {  
                return objectMapper.readValue(json, clazz);  
            } catch (Exception e) {  
                e.printStackTrace();  
                throw new RuntimeException("解析json错误");  
            }  
        }  
      
        public String readAsString(Object obj) {  
            try {  
                return objectMapper.writeValueAsString(obj);  
            } catch (Exception e) {  
                e.printStackTrace();  
                throw new RuntimeException("解析对象错误");  
            }  
        }  
      
        /**
         * 将json数据转换成pojo对象list
         * <p>Title: jsonToList</p>
         * <p>Description: </p>
         * @param jsonData
         * @param beanType
         * @return
         */
        public  <T>List<T> json2List(String jsonData, Class<T> beanType) {
        	JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, beanType);
        	try {
        		List<T> list = objectMapper.readValue(jsonData, javaType);
        		return list;
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        	
        	return null;
        }
        
        @SuppressWarnings("unchecked")  
        public List<Map<String, Object>> json2List(String json) {  
            try {  
                return objectMapper.readValue(json, List.class);  
            } catch (Exception e) {  
                e.printStackTrace();  
                throw new RuntimeException("解析json错误");  
            }  
        }  
    
}
