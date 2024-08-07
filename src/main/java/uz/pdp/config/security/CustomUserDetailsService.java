package uz.pdp.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.model.AuthUser;
import uz.pdp.repository.UserRepo;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthUser> optional = userRepo.getByUsername(username);
        AuthUser authUser = optional.orElseThrow(() -> new BadCredentialsException("Username or password incorrect"));
        String roles = authUser.getRoles(); // ADMIN,USER
        String[] split = roles.split(",");
        UserDetails build = User
                .withUsername(authUser.getUsername())
                .password(authUser.getPassword())
                .roles(split)
                .build();
        return build;
    }
}
