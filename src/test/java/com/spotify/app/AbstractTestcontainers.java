package com.spotify.app;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Testcontainers
@Slf4j
public abstract class AbstractTestcontainers {


    @BeforeAll
    static void beforeAll() {
        Flyway flyway = Flyway
                .configure()
                .dataSource(
                        mySQLContainer.getJdbcUrl(),
                        mySQLContainer.getUsername(),
                        mySQLContainer.getPassword()
                ).load();
        flyway.migrate();
    }
    @Container
    protected static final MySQLContainer<?> mySQLContainer =
            new MySQLContainer<>("mysql:latest")
                    .withDatabaseName("spotify-dao-unit-test")
                    .withUsername("kai")
                    .withPassword("password")
            ;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

//    @BeforeAll
//    static void setUp() {
//        mySQLContainer.withInitScript("schema.sql");
//        mySQLContainer.start();
//    }

    @Test
    void connectionEstablished() {
        assertThat(mySQLContainer.isCreated()).isTrue();
        assertThat(mySQLContainer.isRunning()).isTrue();
    }
}
