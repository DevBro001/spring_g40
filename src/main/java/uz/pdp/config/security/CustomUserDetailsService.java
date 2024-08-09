package uz.pdp.config.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.model.AuthUser;
import uz.pdp.model.Permission;
import uz.pdp.model.Role;
import uz.pdp.repository.PermissionRepo;
import uz.pdp.repository.RoleRepo;
import uz.pdp.repository.UserRepo;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PermissionRepo permissionRepo;

    public CustomUserDetailsService(UserRepo userRepo, RoleRepo roleRepo, PermissionRepo permissionRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.permissionRepo = permissionRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthUser> optional = userRepo.getByUsername(username);
        AuthUser authUser = optional.orElseThrow(() -> new BadCredentialsException("Username or password incorrect"));

        Integer id = authUser.getId();

        List<Role> roles = roleRepo.getByUser(id);

        Set<String> authorities  = new HashSet<>();
        for (Role role : roles) {
            authorities.add("ROLE_"+role.getCode());
            List<Permission> permissions = permissionRepo.getByRole(role.getId());
            for (Permission permission : permissions) {
                authorities.add(permission.getCode());
            }
        }


        String[] array = authorities.toArray(new String[authorities.size()]);
        UserDetails build = User
                .withUsername(authUser.getUsername())
                .password(authUser.getPassword())
                .authorities(array)
                .build();
        return build;
    }
}
