package org.ccps.common;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author BLUE
 * @date 2016年4月18日 下午6:46:55
 * @Description: TODO
 * @version V1.0
 */
public class ObjectMappingCustomer extends ObjectMapper {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObjectMappingCustomer() {
		super();
		// 空值处理为空串
		this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {

			@Override
			public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider)
					throws IOException, JsonProcessingException {
				jgen.writeString("");

			}
		});

	}
}
