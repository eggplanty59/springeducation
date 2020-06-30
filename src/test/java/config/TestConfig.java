package config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"ru.education.jpa"})
@ComponentScan(basePackages = {"ru.education.service"})
@EntityScan(basePackages = {"ru.education.entity"})
public class TestConfig {
}
