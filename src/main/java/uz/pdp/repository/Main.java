package uz.pdp.repository;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.pdp.config.JdbcConfig;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfig.class);
        UserRepo repo = context.getBean(UserRepo.class);

//        repo.save(User.builder().name("ahmad").username("boy").build());
        List<User> all = repo.getAll();

        all.forEach(System.out::println);
    }
}
