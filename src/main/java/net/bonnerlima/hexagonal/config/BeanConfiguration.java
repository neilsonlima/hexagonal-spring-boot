package net.bonnerlima.hexagonal.config;

import net.bonnerlima.hexagonal.adapters.out.persistence.MyRepositoryImpl;
import net.bonnerlima.hexagonal.core.ports.out.MyRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public MyRepositoryPort myRepositoryPort(MyRepositoryImpl myRepositoryImpl) {
        return myRepositoryImpl;
    }
}
