package DanmakuConf;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DanmakuConfManager {
	private static DanmakuConfManager instance;

	private final String propertyFilePath = "danmaku.properties";
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
	public void loadProperties() {
		try {
			InputStream in = new FileInputStream(new File(propertyFilePath));
			Properties pps = new Properties();
			pps.load(in);
			Enumeration<String> enum1 = (Enumeration<String>) pps
					.propertyNames();
			System.out.println("==loading danmaku.properties==");
			while (enum1.hasMoreElements()) {
				String strKey = enum1.nextElement();
				String strValue = pps.getProperty(strKey);
				props.put(strKey, strValue);
				System.out.println(strKey + "=" + strValue);
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
