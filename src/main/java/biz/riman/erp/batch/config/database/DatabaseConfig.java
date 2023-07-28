package biz.riman.erp.batch.config.database;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import biz.riman.erp.batch.config.database.annotation.MapperConnection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@MapperScan(value = "biz.riman.erp.batch", annotationClass = MapperConnection.class, sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement 
@ConfigurationProperties
public class DatabaseConfig {
    @Value("${spring.erpapi.datasource.dbms}")
    private String dbms;

    @Bean(name = "dataSource", destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.erpapi.datasource")
    public DataSource DataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource DataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(DataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:sqlmap/sqlmap-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:sqlmap/" + dbms + "/**/*.xml")); 

        log.info("#########################################");
        log.info("#       defaultSqlSessionFactory        #");
        log.info("#########################################");
        
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory firstSqlSessionFactory) {
        return new SqlSessionTemplate(firstSqlSessionFactory);
    }

    @Bean(name = "defaultTransactionManager")
    @Primary   
    public PlatformTransactionManager defaultTransactionManager(@Qualifier("dataSource") DataSource defaultDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(defaultDataSource);
        return transactionManager;
    }
}
