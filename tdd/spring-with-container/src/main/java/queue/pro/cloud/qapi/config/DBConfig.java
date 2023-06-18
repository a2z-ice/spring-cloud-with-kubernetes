package queue.pro.cloud.qapi.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DBConfig {

   /* @Autowired
    private DataSourceProperties dataSourceProperties;

    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .username(dataSourceProperties.getLogin())
                .password(dataSourceProperties.getPassword())
                .url(dataSourceProperties.getUrl())
                .build();
    }

    @LiquibaseDataSource
    @Bean
    public DataSource liquibaseDataSource() {
        DataSource ds = DataSourceBuilder.create()
                .username(dataSourceProperties.getLiquibase().getLogin())
                .password(dataSourceProperties.getLiquibase().getPassword())
                .url(dataSourceProperties.getUrl())
                .build();
        if (ds instanceof HikariDataSource) {
            ((HikariDataSource) ds).setMaximumPoolSize(2);
            ((HikariDataSource) ds).setPoolName("Liquibase Pool");
        }
        return ds;
    }*/

}
