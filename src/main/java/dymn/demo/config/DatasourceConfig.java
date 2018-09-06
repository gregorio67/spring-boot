/**
 * @Project :  스마트톨링정보시스템 구축
 * @Class : DatasourceConfiguration.java
 * @Description : 
 *
 * @Author : LGCNS
 * @Since : 2017. 4. 20.
 *
 * @Copyright (c) 2018 EX All rights reserved.
 *-------------------------------------------------------------
 *              Modification Information
 *-------------------------------------------------------------
 * 날짜            수정자             변경사유 
 *-------------------------------------------------------------
 * 2018. 8. 6.        LGCNS             최초작성
 *-------------------------------------------------------------
 */

package dymn.demo.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
//@MapperScan(value ="dynm.demo.mapper1", sqlSessionFactoryRef="db1SqlSessionFactory")
//@EnableTransactionManagement
public class DatasourceConfig {

	@Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
		Resource[] sqlResources = new PathMatchingResourcePatternResolver().getResources("classpath:sqlmap/**/*SQL.xml");
		Resource configLocation = new PathMatchingResourcePatternResolver().getResource("classpath:sqlmap/config/mybatis-config.xml");
		
		factoryBean.setConfigLocation(configLocation);
		
		factoryBean.setMapperLocations(sqlResources);

        return factoryBean.getObject();
    }


    @Bean(name="sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
	
}
