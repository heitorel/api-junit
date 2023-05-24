package br.com.heitor.apijunit.config;

import br.com.heitor.apijunit.domain.User;
import br.com.heitor.apijunit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository repository;

    @Bean
    public void StartDB(){
        User u1 = new User(null, "Jesse", "jesse@email.com", "123");
        User u2 = new User(null, "Friendo", "friendo@email.com", "456");
        User u3 = new User(null, "Moreno", "moreno@email.com", "789");

        repository.saveAll(List.of(u1,u2,u3));
    }
}