package dev.hash.airplaneAPI.customCamelCase;

import org.springframework.jdbc.support.JdbcUtils;

import java.util.HashMap;

public class CustomMap extends HashMap<String, Object>{
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	@Override
	public Object put(String key, Object value) {
		return super.put(JdbcUtils.convertUnderscoreNameToPropertyName(key), value);
	}
}