package dymn.demo.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;


@Component
public class PropertiesUtil extends PropertyPlaceholderConfigurer implements InitializingBean{
	/**LOGGER SET **/
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

	private static Map<String, String> propertiesMap;


	   @Override
	    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
	        super.processProperties(beanFactory, props);
//	        String[] activeProfiles = environment.getActiveProfiles();
	//
//	        if (LOGGER.isDebugEnabled()) {
//	        	for (String activeProfile : activeProfiles) {
//	        		LOGGER.debug("Active Profile :: {}", activeProfile);
//	        	}
//	        }
	        propertiesMap = new HashMap<String, String>();
	        for (Object key : props.keySet()) {
	            String keyStr = String.valueOf(key);
	            String valueStr = resolvePlaceholder(keyStr, props, SYSTEM_PROPERTIES_MODE_OVERRIDE);
	            propertiesMap.put(keyStr, valueStr);
	            if (LOGGER.isDebugEnabled()) {
	            	LOGGER.debug("Load Properties :" +  keyStr + ":" + valueStr);
	            }
	        }
	    }

	    /**
	     * This method return value with the name from properties map
	     * @param name propertiy name
	     * @return
	     */
	    public static String getString(String name)  {
	        return propertiesMap.get(name) != null ? propertiesMap.get(name).toString() : "";
	    }

	    public static int getInt(String name) {
	    	return propertiesMap.get(name) != null ? Integer.parseInt(String.valueOf(propertiesMap.get(name))) : 0;
	    }

	    public static long getLong(String name) {
	    	return propertiesMap.get(name) != null ? Long.parseLong(String.valueOf(propertiesMap.get(name))) : 0L;
	    }

		@Override
		public void afterPropertiesSet() throws Exception {
			Resource[] locations  = new PathMatchingResourcePatternResolver().getResources("classpath:/properties/*.properties");
			LOGGER.debug("Locations :: {}", locations.toString());
			super.setLocations(locations);
			
		}
}
