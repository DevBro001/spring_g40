package uz.pdp.config.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.model.User;
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
        Optional<User> optional = userRepo.getByUsername(username);
        User user = optional.orElseThrow(() -> new BadCredentialsException("Username or password incorrect"));
        Integer id = user.getId();
        List<Role> roles = roleRepo.getByUser(id);
        Set<SimpleGrantedAuthority> authorities  = new HashSet<>();
        for (Role role : roles) {
            authorities.add( new SimpleGrantedAuthority("ROLE_" + role.getCode()));
            List<Permission> permissions = permissionRepo.getByRole(role.getId());
            for (Permission permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission.getCode()));
            }
        }
        AuthUser authUser = AuthUser.builder()
                .id(user.getId())
                .fullName(user.getName())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
        return authUser;
    }
}
