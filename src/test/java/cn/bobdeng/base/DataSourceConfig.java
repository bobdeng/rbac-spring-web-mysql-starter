package cn.bobdeng.base;

import org.junit.Rule;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Rule
    public GenericContainer redis = new GenericContainer("redis:3.0.6")
            .withExposedPorts(6379);

    @Bean
    public DataSource dataSource() {
        DockerImageName dockerImageName = DockerImageName.parse("mysql:5.7.28");
        MySQLContainer mySQLContainer = new MySQLContainer(dockerImageName);
        mySQLContainer.start();
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName(mySQLContainer.getDriverClassName())
                .username(mySQLContainer.getUsername())
                .password(mySQLContainer.getPassword())
                .url(mySQLContainer.getJdbcUrl() + "?characterEncoding=utf-8")
                .build();
        return dataSource;
    }

}
