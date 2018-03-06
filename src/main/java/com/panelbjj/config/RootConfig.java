package com.panelbjj.config;


import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import javax.sql.DataSource;
import java.util.Properties;

@EnableTransactionManagement
@Configuration
@PropertySource("classpath:emailAndDatabase.properties")
@ComponentScan(basePackages = {"com.panelbjj"},
        excludeFilters = {
                @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
        })
public class RootConfig implements EnvironmentAware {


    private Environment environment;

    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(environment.getProperty("emailSmtp"));
        mailSender.setPort(Integer.parseInt(environment.getProperty("emailPort")));
        mailSender.setUsername(environment.getProperty("emailAccount"));
        mailSender.setPassword(environment.getProperty("emailPassword"));
        mailSender.setDefaultEncoding("UTF-8");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }

    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl(environment.getProperty("databaseUrl"));
        ds.setUsername(environment.getProperty("databaseUsername"));
        ds.setPassword(environment.getProperty("databasePassword"));
        ds.addConnectionProperty("useUnicode", "yes");
        ds.addConnectionProperty("characterEncoding", "UTF-8");
        ds.setInitialSize(5);
        ds.setMaxActive(30);
        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory(dataSource, jpaVendorAdapter).getObject());
        return transactionManager;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL57Dialect");
        return adapter;
    }

    @Bean
    public PersistenceAnnotationBeanPostProcessor paPostProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean emfb =
                new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource);
        emfb.setJpaVendorAdapter(jpaVendorAdapter);
        emfb.setPackagesToScan("com.panelbjj");
        return emfb;
    }

}