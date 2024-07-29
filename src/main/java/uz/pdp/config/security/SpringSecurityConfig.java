package uz.pdp.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf->csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/static/**","/auth/login","/login").permitAll()
                        .anyRequest().authenticated()    // 403
                );
        http.formLogin(formLogin ->
                formLogin
                        .loginPage("/auth/login")
                        .usernameParameter("uname")
                        .passwordParameter("pword")
//                        .failureForwardUrl("/auth/login")
                        .defaultSuccessUrl("/main/about",true)
                );
//        http.logout(Customizer.withDefaults());

        return http.build();
    }
/*
    @Bean
    public UserDetailsService userDetailsService() {
    }*/

        @Bean
    public UserDetailsService userDetailsService(){
        UserDetails ali = User.withUsername("ali")
                .password("{noop}root")
                .roles("USER").build();
        UserDetails ahmad = User.withUsername("ahmad")
                .password("{noop}root")
                .roles("USER").build();
        UserDetails salim = User.withUsername("salim")
                .password("{noop}root")
                .roles("USER").build();

        return new InMemoryUserDetailsManager(ali, ahmad, salim);
    }
}
