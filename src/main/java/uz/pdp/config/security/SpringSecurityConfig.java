package uz.pdp.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import uz.pdp.handler.AuthEntryPointHandler;
import uz.pdp.handler.LoginFailureHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {

    private String[]  WITH_LIST = {
            "/static/**",
            "/auth/signup",
            "/auth/unAuth",
            "/main/home"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   LoginFailureHandler loginFailureHandler,
                                                   UserDetailsService userDetailsService,
                                                   AuthenticationEntryPoint authenticationEntryPoint,
                                                   AccessDeniedHandler accessDeniedHandler
                                                   ) throws Exception {

        http.csrf(csrf->csrf.disable());
        http
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/admin/**").hasAuthority("UPDATE_USER")
                        .requestMatchers(WITH_LIST).permitAll()
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/auth/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .failureHandler(loginFailureHandler)
                        .defaultSuccessUrl("/main/about",false)
                        .permitAll()
                )
                .logout(logout-> logout
                                .logoutUrl("/auth/logout")
                                .deleteCookies("JSESSIONID")
                                .clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout","POST"))
                                .permitAll()
                        )
                .rememberMe(remMep->remMep
                        .rememberMeParameter("remMe")
                        .rememberMeCookieName("r-me")
                        .key("SECRET_KEYvcqievbaIUABVFA;EIUYFBIAUBF439FBHASVBQF39GFB;SAIHB")
                        .userDetailsService(userDetailsService)
                        .tokenValiditySeconds(24*60*60*7)
                )
                .exceptionHandling(handler->handler
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)

                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
