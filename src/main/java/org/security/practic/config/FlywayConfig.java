package org.security.practic.config;

import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Autowired
    private Flyway flyway;

    @PostConstruct
    public void init(){
        // Выполняет очистку перед применением миграций
        flyway.clean();
        flyway.migrate();
    }
}
