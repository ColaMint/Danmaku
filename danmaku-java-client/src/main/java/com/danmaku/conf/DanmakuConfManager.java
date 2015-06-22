package com.danmaku.conf;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DanmakuConfManager implements ConfManager {
	private final static Logger logger = LoggerFactory.getLogger(DanmakuConfManager.class);
	private static DanmakuConfManager instance;

	private final String PROPERTY_FILE_PATH = "danmaku.properties";
	private Map<String, String> props;

	public synchronized static DanmakuConfManager getInstance() {
		if (instance == null) {
			instance = new DanmakuConfManager();
		}
		return instance;
	}

	private DanmakuConfManager() {
		props = new HashMap<String, String>();
		loadProperties();
	}

	@SuppressWarnings("unchecked")
	private void loadProperties() {
		try {
			InputStream in = ClassLoader.getSystemResource(PROPERTY_FILE_PATH).openStream();
			Properties pps = new Properties();
			pps.load(in);
			Enumeration<String> enum1 = (Enumeration<String>) pps
					.propertyNames();
			logger.debug("Loading danmaku.properties");
			while (enum1.hasMoreElements()) {
				String strKey = enum1.nextElement();
				String strValue = pps.getProperty(strKey);
				props.put(strKey, strValue);
				logger.debug("{} : {}", strKey, strValue);
			}
			in.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getProperty(String key, String defaultValue) {
		String strValue = props.get(key);
		return strValue == null ? defaultValue : strValue;
	}
}
