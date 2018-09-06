package dymn.demo.base;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("baseDao")
public class BaseDao extends SqlSessionDaoSupport implements InitializingBean {
	
	private static  Logger LOGGER = LoggerFactory.getLogger(BaseDao.class);
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	/**
	 * 
	 *<pre>
	 * 1.Description: Insert Data
	 * 2.Biz Logic:
	 * 3.Author : LGCNS
	 *</pre>
	 * @param sqlId
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public <T> int insert(String sqlId, T params) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Excuted SQL ID :: {}", sqlId);
		}
		return getSqlSession().insert(sqlId, params);
	}
	
	/**
	 * 
	 *<pre>
	 * 1.Description: Update Data
	 * 2.Biz Logic:
	 * 3.Author : LGCNS
	 *</pre>
	 * @param sqlId
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public <T> int update(String sqlId, T params) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Excuted SQL ID :: {}", sqlId);
		}
		return getSqlSession().update(sqlId, params);
	}

	/**
	 * 
	 *<pre>
	 * 1.Description: Delete Data
	 * 2.Biz Logic:
	 * 3.Author : LGCNS
	 *</pre>
	 * @param sqlId
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public <T> int delete(String sqlId, T params) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Excuted SQL ID :: {}", sqlId);
		}
		return getSqlSession().delete(sqlId, params);
	}

	/**
	 * 
	 *<pre>
	 * 1.Description: Select data for one row
	 * 2.Biz Logic:
	 * 3.Author : LGCNS
	 *</pre>
	 * @param sqlId
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public <T, V> T select(String sqlId, V param) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Excuted SQL ID :: {}", sqlId);
		}
		return getSqlSession().selectOne(sqlId, param);
	}

	/**
	 * 
	 *<pre>
	 * 1.Description: select data for one row without parameter
	 * 2.Biz Logic:
	 * 3.Author : LGCNS
	 *</pre>
	 * @param sqlId
	 * @return
	 * @throws Exception
	 */
	public <T, V> T select(String sqlId) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Excuted SQL ID :: {}", sqlId);
		}
		return getSqlSession().selectOne(sqlId);
	}

	/**
	 * 
	 *<pre>
	 * 1.Description: Select data list with parameter 
	 * 2.Biz Logic:
	 * 3.Author : LGCNS
	 *</pre>
	 * @param sqlId
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <T, V> T selectList(String sqlId, V param) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Excuted SQL ID :: {}", sqlId);
		}
		return (T) getSqlSession().selectList(sqlId, param);
	}
	
	/**
	 * 
	 *<pre>
	 * 1.Description: Select list without parameter
	 * 2.Biz Logic:
	 * 3.Author : LGCNS
	 *</pre>
	 * @param sqlId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <T, V> T selectList(String sqlId) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Excuted SQL ID :: {}", sqlId);
		}
		return (T) getSqlSession().selectList(sqlId);
	}
	
	
	/** 
	 * 
	 *<pre>
	 * 1.Description: When using spring boot, SqlSessionFactory should be set in SqlSessionDaoSupport
	 *                If you use spring MVC, no need to set sqlsessionfactory, because baseDao should be set bean 
	 * 2.Biz Logic:
	 * 3.Author : LGCNS
	 *</pre>
	 * @throws Exception
	 */
	@PostConstruct
	public void postConstruct() throws Exception {
	  super.setSqlSessionFactory(sqlSessionFactory);
	}
}
