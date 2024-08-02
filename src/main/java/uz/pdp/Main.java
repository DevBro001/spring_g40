package uz.pdp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.pdp.config.JdbcConfig;
import uz.pdp.model.User;
import uz.pdp.repository.UserRepoNamedParameter;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfig.class);
        UserRepoNamedParameter repo = context.getBean(UserRepoNamedParameter.class);

//        repo.save(User.builder().name("ahmad").username("boy").build());
//        List<User> all = repo.getAll();

//        all.forEach(System.out::println);
       /* User user = repo.get(1);
        user.setUsername("boy");
        user.setName("ahmad");
        repo.update(user);
        repo.delete(3);*/


        Integer i = repo.saveReturnId(User.builder().name("Sanobar").username("sanobar123").build());
        System.out.println(i);
//        System.out.println(repo.get(2));
    }
}
