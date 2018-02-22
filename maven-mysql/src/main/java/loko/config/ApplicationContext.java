package loko.config;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.DriverManagerDataSource;
//import org.springframework.core.env.Environment;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages="loko")
public class ApplicationContext {
	@Autowired
//	private Environment env;
	
	//Step 1: Define Database DataSource / connection pool
  @Bean
  public DriverManagerDataSource dataSource(){
     DriverManagerDataSource dataSource = new DriverManagerDataSource();
     dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
     dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/loko?useSSL=false");
     dataSource.setUser( "hbstudent" );
     dataSource.setPassword( "hbstudent" );
     return dataSource;
  }
  
  //Step 2: Setup Hibernate session factory
  @Bean
  public LocalSessionFactoryBean sessionFactory() {
  	final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
  	sessionFactory.setDataSource(dataSource());
  	sessionFactory.setPackagesToScan(new String [] {"loko.entity"});
  	sessionFactory.setHibernateProperties(hibernateProperties());
  	
  	return sessionFactory;
  }
 
  //Step 3: Setup Hibernate transaction manager
  @Bean
  public HibernateTransactionManager transactionManager(final SessionFactory sessionFactory) {
  	final HibernateTransactionManager txManager = new HibernateTransactionManager();
  	txManager.setSessionFactory(sessionFactory);
  	
  	return txManager;
  	
  }
  
  final Properties hibernateProperties() {
  	final Properties hibernateProperties = new Properties();
  	hibernateProperties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
//  	hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
  	hibernateProperties.setProperty("hibernate.show_sql", "true");
  	
  	return hibernateProperties;
  }
}
