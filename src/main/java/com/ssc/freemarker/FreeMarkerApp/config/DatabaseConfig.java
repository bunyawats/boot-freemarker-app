package com.ssc.freemarker.FreeMarkerApp.config;

import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider ( new TransactionAwareDataSourceProxy ( dataSource ) );
    }


    @Bean
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public DefaultDSLContext dsl() {
        return new DefaultDSLContext ( configuration ( ) );
    }

    public DefaultConfiguration configuration() {

        System.getProperties ( ).setProperty ( "org.jooq.no-logo", "true" );

        DefaultConfiguration jooqConfiguration = new DefaultConfiguration ( );
        jooqConfiguration.set ( SQLDialect.MYSQL );
        jooqConfiguration.set ( connectionProvider ( ) );
        jooqConfiguration.set ( new DefaultExecuteListenerProvider ( new ExceptionTranslator ( ) ) );

        return jooqConfiguration;
    }


    public class ExceptionTranslator extends DefaultExecuteListener {

        @Override
        public void exception(ExecuteContext context) {
            SQLDialect dialect = context.configuration ( ).dialect ( );
            SQLExceptionTranslator translator = new SQLErrorCodeSQLExceptionTranslator ( dialect.name ( ) );

            context.exception ( translator.translate ( "Access database using jOOQ", context.sql ( ), context.sqlException ( ) ) );
        }
    }
}
