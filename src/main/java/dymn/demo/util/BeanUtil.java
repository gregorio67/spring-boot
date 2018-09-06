package dymn.demo.util;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class BeanUtil{

	
	private static ApplicationContext appContext;
	
	public static void setAppContext(ApplicationContext appContext) {
		BeanUtil.appContext = appContext;
	}

	public static ApplicationContext getAppContext() {
		return appContext;
	}

	/**
	 * 
	 *<pre>
	 * Spring Bean return with bean name
	 *</pre>
	 * @param beanName String 
	 * @return
	 * @throws Exception
	 */
	public static <T> T getBean(String beanName) throws Exception {
//		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();

		@SuppressWarnings("unchecked")
		T bean = (T) getAppContext().getBean(beanName);

		return bean;
	}
	
	/**
	 * 
	 *<pre>
	 * 1.Description: Return WebApplicationContext
	 * 2.Biz Logic:
	 * 3.Author : LGCNS
	 *</pre>
	 * @return WebApplicationContext
	 * @throws Exception
	 */
	
	public static ApplicationContext getContext() throws Exception {
//		return ContextLoader.getCurrentWebApplicationContext();
		return getAppContext();
	}
	
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	public static HttpServletResponse getResponse() {
		 HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		return response;
	}
	
    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        return request.getSession();
    }
	
    public static ServletContext getServletContext(){
        return ContextLoader.getCurrentWebApplicationContext().getServletContext();
    }
    
    public static boolean isAjax(){

        String requestType = getRequest().getHeader("X-Requested-With");
        if (requestType == null || "".equals(requestType)){
            return false;
        }else{
            return true;
        }
    }    
	/**
	 * 
	 *<pre>
	 * 1.Description: Return Current Active profiles
	 * 2.Biz Logic:
	 * 3.Author : LGCNS
	 *</pre>
	 * @return String[]
	 * @throws Exception
	 */
	public static String[] getActiveProfile() throws Exception {
		return getContext().getEnvironment().getActiveProfiles();
	}
	
	/**
	 * 
	 *<pre>
	 * 1.Description: Return FileInputStream form resource name
	 * 2.Biz Logic:
	 * 3.Author : LGCNS
	 *</pre>
	 * @param resourceName resource name 
	 * @return FileFinputStream from resource name
	 * @throws Exception
	 */
	public static InputStream getResource(String resourceName) throws Exception {
		if (resourceName == null) {
			return null;
		}
		
		if (!resourceName.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX)) {
			resourceName = ResourceUtils.CLASSPATH_URL_PREFIX + resourceName;
		}
		return new FileInputStream(ResourceUtils.getFile(resourceName));
	}
}
